package edu.wong.attendance_management_api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.Right;
import edu.wong.attendance_management_api.service.IRightService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping("/list")
    public ResponseFormat findAll(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String rightName) {
        IPage<Right> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Right> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.like(Strings.isNotEmpty(rightName), Right::getName, rightName);

        IPage<Right> pageData = service.page(page, lambdaQueryWrapper);

        return ResponseFormat.successful(pageData);
    }

    @PostMapping("/add")
    public ResponseFormat add(@RequestBody Right right) {
        service.saveOrUpdate(right);
        return ResponseFormat.successful("添加成功");
    }


    @GetMapping("/all")
    public ResponseFormat all() {
        return ResponseFormat.successful(service.list());
    }
}
