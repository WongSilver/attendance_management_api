package edu.wong.attendance_management_api.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.entity.dto.UserDTO;
import edu.wong.attendance_management_api.mapper.UserMapper;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService service;

    @Resource
    private JwtUtil util;

    @Resource
    UserMapper mapper;

    //    查询用户列表
    @RequiresRoles("admin")
    @GetMapping("/list")
    public ResponseFormat list(@RequestParam(defaultValue = "1") Integer currentPage,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String userName,
                               @RequestParam(defaultValue = "") String userMail,
                               @RequestParam(defaultValue = "") String userGroup) {
//        myBatisPlus自带的分页方法 参数一：起始页，参数二：多少条数据
        IPage<User> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(Strings.isNotEmpty(userName), User::getName, userName);
        lambdaQueryWrapper.like(Strings.isNotEmpty(userMail), User::getMail, userMail);
        lambdaQueryWrapper.like(Strings.isNotEmpty(userGroup), User::getGroupId, userGroup);

        IPage<User> pageData = service.page(page, lambdaQueryWrapper);

        return ResponseFormat.successful(pageData);
    }

    //    查询用户详情
    @GetMapping("/{id}")
    public ResponseFormat detail(@PathVariable int id) {
        User user = service.getById(id);
        if (user == null) {
            return ResponseFormat.fail("用户不存在");
        }
        return ResponseFormat.successful(user);
    }

    /**
     * 编辑用户
     */
//    验证用户是否登录
    @RequiresAuthentication
//    检查权限
//    @RequiresPermissions(logical = Logical.OR, value = {"user:edit", "admin:edit"})
    @PostMapping("/edit")
    public ResponseFormat edit(@RequestBody User user, HttpServletRequest request) {
        boolean b;
//        查询Token储存的用户
        Claims token = util.getClaimByToken(request.getHeader("Authorization"));
//         查询shiro中存储的用户
        User principal = (User) SecurityUtils.getSubject().getPrincipal();
//        查询当前用户是否存在，存在 则不允许修改
        QueryWrapper<User> name = new QueryWrapper<User>().eq("name", user.getName());
        List<User> users = mapper.selectList(name);

//        前端传来的ID为空提示未登录
//        前端传来的token中的ID 与 shiro中的ID不一致提示无权限
//        前端传来的ID查询后存在 提示用户名存在的
        if (user.getId() == null) {
            return ResponseFormat.fail("未登录");
        } else if (!token.getSubject().equals(principal.getId().toString()) || !principal.getId().equals(user.getId())) {
            return ResponseFormat.fail("没有编辑权限");
        } else if (!users.isEmpty()) {
            return ResponseFormat.fail("用户已存在");
        } else {
            b = service.updateById(user);
        }
        return b ? ResponseFormat.successful(null) : ResponseFormat.fail("失败");
    }


    /**
     * 添加或修改用户信息
     * 逻辑：
     * 1、判断用户ID是否为空，空添加用户，不为空修改用户信息
     * 1.1、添加用户：判断用户名是否存在，不存在就添加
     * 1.2、修改用户：传入需要修改的用户名，判断传入的用户名ID是否和当前ID是否一致
     * 1.2.1、一致：当前用户名只有当前用户在使用，可以修改
     * 1.2.2、不一致：其他用户在使用，提示用户名已存在
     */

    @RequiresRoles("admin")
    @PostMapping("/add")
    public ResponseFormat add(@RequestBody User user) {

        if (user.getId() != null) {
            User temp = mapper.selectOne(new QueryWrapper<User>().eq("name", user.getName()));
            if (temp == null || user.getId().equals(temp.getId())) {
                return ResponseFormat.successful(service.saveOrUpdate(user));
            }
            return ResponseFormat.fail("用户名已存在");
        }
        if (user.getName() == null || user.getPassword() == null) {
            return ResponseFormat.fail("用户名或密码不能为空");
        }
        if (mapper.selectCount(new QueryWrapper<User>().eq("name", user.getName())) > 0) {
            return ResponseFormat.fail("用户名已存在");
        }
        return ResponseFormat.successful(service.saveOrUpdate(user));
    }

    @DeleteMapping("/delete/{id}")
    @RequiresRoles("admin")
    public ResponseFormat delete(@PathVariable Integer id) {
        return ResponseFormat.successful(mapper.deleteById(id));
    }

    @PostMapping("/delete/batch/")
    public ResponseFormat deleteBatch(@RequestBody List<Integer> ids) {
        if (ids.size() == 0) {
            return ResponseFormat.fail("请选择需要删除的数据");
        }
        return ResponseFormat.successful(mapper.deleteBatchIds(ids));
    }

    @GetMapping("/export")
    public void ExportExcel(HttpServletResponse response) throws IOException {
        List<User> list = service.list();        //查询所有用户数据
//        写到浏览器
//        使用下面注解可以不用起别名
//        @Alias("XXXX")
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

//        通用的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息", "UTF-8") + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public boolean importExcel(MultipartFile file) throws IOException {
        InputStream stream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(stream);
        List<User> maps = reader.readAll(User.class);
        service.saveBatch(maps);
        return true;
    }

    //    获取用户信息
    @GetMapping("/info")
    public ResponseFormat getUserInfo(ServletRequest request) {
        HttpServletRequest servletRequest = WebUtils.toHttp(request);
//        查询Token储存的用户
        Claims token = util.getClaimByToken(servletRequest.getHeader("Authorization"));
        if (token != null) {
            UserDTO userInfo = service.getUserInfo(Integer.valueOf(token.getSubject()));
            return ResponseFormat.successful(userInfo);
        }
        return ResponseFormat.operate(400, "未查询到用户", null);
    }

    @GetMapping("/total")
    public ResponseFormat getTotal() {
        List<User> list = service.list();
        return ResponseFormat.successful(list.size());
    }

}
