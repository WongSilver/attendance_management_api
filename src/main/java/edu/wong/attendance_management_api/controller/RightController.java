package edu.wong.attendance_management_api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.Right;
import edu.wong.attendance_management_api.mapper.RightMapper;
import edu.wong.attendance_management_api.service.IRightService;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/right")
public class RightController {
    @Resource
    IRightService service;
    @Resource
    RightMapper mapper;

    //    分页查询
    @RequiresRoles("admin")
    @GetMapping("/list")
    public ResponseFormat findAll(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String rightName) {
        IPage<Right> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Right> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(Strings.isNotEmpty(rightName), Right::getName, rightName);
        IPage<Right> pageData = service.page(page, lambdaQueryWrapper);

        return ResponseFormat.successful(pageData);
    }

    //    查询所有用户
    @GetMapping("/all")
    public ResponseFormat all() {
        return ResponseFormat.successful(service.list());
    }

    /**
     * 逻辑：同用户
     */
    @PostMapping("/add")
    @RequiresRoles("admin")
    public ResponseFormat add(@RequestBody Right right) {
        if (right.getId() != null) {
            Right temp = mapper.selectOne(new QueryWrapper<Right>().eq("name", right.getName()));
            if (temp == null || right.getId().equals(temp.getId())) {
                return ResponseFormat.successful(service.saveOrUpdate(right));
            }
            return ResponseFormat.fail("权限名已存在");
        }
        if (mapper.selectCount(new QueryWrapper<Right>().eq("name", right.getName())) > 0) {
            return ResponseFormat.fail("权限名已存在");
        }
        return ResponseFormat.successful(service.saveOrUpdate(right));
    }

    @DeleteMapping("/delete/{id}")
    @RequiresRoles("admin")
    public ResponseFormat delete(@PathVariable Integer id) {
        return ResponseFormat.successful(mapper.deleteById(id));
    }

    @PostMapping("/delete/batch/")
    @RequiresRoles("admin")
    public ResponseFormat deleteBatch(@RequestBody List<Integer> ids) {
        if (ids.size() == 0) {
            return ResponseFormat.fail("请选择需要删除的数据");
        }
        return ResponseFormat.successful(mapper.deleteBatchIds(ids));
    }
}
