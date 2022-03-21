package edu.wong.attendance_management_api.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.wong.attendance_management_api.common.dto.LoginDto;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.shiro.JwtToken;
import edu.wong.attendance_management_api.util.JwtUtil;
import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Resource
    IUserService service;

    @Resource
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseFormat login(@Validated @RequestBody LoginDto dto, HttpServletResponse response) {

//        查询数据库是否存在该用户
        User user = service.getOne(new QueryWrapper<User>().eq("name", dto.getName()));
        if (user == null) {
            return ResponseFormat.fail("用户不存在");
        }
        if (!user.getPassword().equals(dto.getPassword())) {
            return ResponseFormat.fail("密码不正确");
        }
        String jwt = jwtUtil.generateToken(user);
//        SecurityUtils.getSubject().login(new JwtToken(jwt));
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        try {
            SecurityUtils.getSubject().hasRole(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseFormat.successful(MapUtil.builder()
                .put("name", user.getName())
                .put("id", user.getId())
                .put("groupId", user.getGroupId())
                .put("mail", user.getMail())
                .map());
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public ResponseFormat logout() {
        SecurityUtils.getSubject().logout();
        return ResponseFormat.successful(null);
    }


}
