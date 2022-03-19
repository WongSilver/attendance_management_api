package edu.wong.attendance_management_api.controller;

import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.util.ResponseUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WongSilver
 * @since 2022-03-20
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService service;

    @GetMapping("/index")
    public ResponseUtil index() {
        return ResponseUtil.successful(service.getById(1));
    }
}
