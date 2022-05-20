package edu.wong.attendance_management_api.controller;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.dto.LoginDTO;
import edu.wong.attendance_management_api.entity.dto.UserDTO;
import edu.wong.attendance_management_api.mapper.AccountMapper;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.util.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AccountController {

    @Resource
    private IUserService service;

    @Resource
    private AccountMapper mapper;

    //验证码
    private String code;

    @PostMapping("/login")
    public ResponseFormat login(@Validated @RequestBody(required = false) LoginDTO dto, HttpServletResponse response) {

        //查询数据库是否存在该用户
        UserDTO userInfo = service.userLogin(dto, response);
        System.out.println(code);

        return userInfo == null ? ResponseFormat.fail("用户名或密码错误") : ResponseFormat.successful(userInfo);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public ResponseFormat logout() {
        SecurityUtils.getSubject().logout();
        return ResponseFormat.successful(null);
    }

    @GetMapping("/info/{id}")
    public ResponseFormat Info(@PathVariable int id) {
        return ResponseFormat.successful(mapper.selectInfoByUserId(id));
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");

        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        code = CaptchaUtil.getCaptcha(response.getOutputStream());
    }
}
