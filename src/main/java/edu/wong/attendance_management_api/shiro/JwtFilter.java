package edu.wong.attendance_management_api.shiro;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.json.JSONUtil;
import edu.wong.attendance_management_api.util.JwtUtil;
import edu.wong.attendance_management_api.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtil util;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (StringUtils.hasText(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServerRequest request = (HttpServerRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        //如果没有jwt就交给注解处理，有就进行校验和登陆处理
        if (StringUtils.hasText(jwt)) {
            return true;
        } else {
//          校验Jwt
            Claims claimByToken = util.getClaimByToken(jwt);
            if (claimByToken == null || util.isTokenExpired(claimByToken.getExpiration())) {
                throw new ExpiredCredentialsException("token已失效，请重新登录");
            }
//          执行登录
            return executeLogin(servletRequest, servletResponse);
        }
    }

    //    重写onLoginFailure方法，因为返回值需要固定格式，可以使用ResponseUtil
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        获取错误原因
        Throwable throwable = e.getCause() == null ? e : e.getCause();
//        包装
        ResponseUtil fail = ResponseUtil.fail(throwable.getMessage());

        String json = JSONUtil.toJsonStr(fail);
        try {
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            servletResponse.getWriter().print(json);
        } catch (IOException ex) {
            System.out.println("------------->" + ex);
        }

        return false;
    }
}
