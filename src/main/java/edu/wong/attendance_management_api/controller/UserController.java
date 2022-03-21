package edu.wong.attendance_management_api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.mapper.UserMapper;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@Controller
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
    @GetMapping("/list")
    public ResponseFormat list(@RequestParam(defaultValue = "1") Integer currentPage) {

//        myBatisPlus自带的分页方法
//        参数一：起始页，参数二：多少条数据
        Page<User> page = new Page<>(currentPage, 5);
        IPage<User> pageData = mapper.selectPage(page, new QueryWrapper<>());

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

    //    编辑用户
    @RequiresAuthentication
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
        } else if (!(token.getSubject().equals(principal.getId().toString()))) {
            return ResponseFormat.fail("没有编辑权限");
        } else if (!users.isEmpty()) {
            return ResponseFormat.fail("用户已存在");
        } else {
            b = service.updateById(user);
        }
        return b ? ResponseFormat.successful(null) : ResponseFormat.fail("失败");
    }
}
