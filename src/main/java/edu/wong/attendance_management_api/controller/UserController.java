package edu.wong.attendance_management_api.controller;

import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.lang.ResponseFormat;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @RequiresAuthentication
    @GetMapping("/index")
    @ResponseBody
    public ResponseFormat index() {
        User byId = service.getById(1);
        return ResponseFormat.successful(byId);
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseFormat save(@Validated @RequestBody User user) {
        return ResponseFormat.successful(user);
    }
}
