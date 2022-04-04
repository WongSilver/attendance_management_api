package edu.wong.attendance_management_api.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.wong.attendance_management_api.entity.dto.LoginDto;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.mapper.AccountMapper;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.shiro.JwtToken;
import edu.wong.attendance_management_api.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Resource
    IUserService service;

    @Resource
    JwtUtil jwtUtil;
    private String jwt;

    @Resource
    AccountMapper mapper;

    @PostMapping("/login")
    public ResponseFormat login(@Validated @RequestBody(required = false) LoginDto dto, HttpServletResponse response) {
//        查询数据库是否存在该用户
        User user = service.getOne(new QueryWrapper<User>().eq("name", dto.getName()));
        if (user == null) {
            return ResponseFormat.fail("用户不存在");
        }
        if (!user.getPassword().equals(dto.getPassword())) {
            return ResponseFormat.fail("密码不正确");
        }
//       用户名密码正确 token不存在或者失效 生成Token返回给前端
        if (jwt == null || jwtUtil.isTokenExpired(jwtUtil.getClaimByToken(jwt).getExpiration())) {
            jwt = jwtUtil.generateToken(user);
        }
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
//        执行登录
        SecurityUtils.getSubject().login(new JwtToken(jwt));

        return ResponseFormat.successful(MapUtil.builder()
                .put("name", user.getName())
                .put("id", user.getId())
                .put("groupId", user.getGroupId())
                .put("mail", user.getMail())
                .put("token", jwt)
                .map());
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public ResponseFormat logout() {
        SecurityUtils.getSubject().logout();
        return ResponseFormat.successful(null);
    }

    @GetMapping("/info/{id}")
    public ResponseFormat Info(@PathVariable int id) {
        System.out.println("---------------------");
        return ResponseFormat.successful(mapper.selectInfoByUserId(id));
    }

}
