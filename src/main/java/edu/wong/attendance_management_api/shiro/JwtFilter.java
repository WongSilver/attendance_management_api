package edu.wong.attendance_management_api.shiro;

import cn.hutool.json.JSONUtil;
import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {

    @Resource
    JwtUtil util;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("Authorization");
        //如果没有jwt就交给注解处理，有就进行校验和登陆处理
        if (StringUtils.isEmpty(token)) {
            return true;
        } else {
//          校验Jwt
            Claims claimByToken = util.getClaimByToken(token);
            if (claimByToken == null || util.isTokenExpired(claimByToken.getExpiration())) {
                throw new ExpiredCredentialsException("token已失效，请重新登录");
            }
//          执行登录
            return executeLogin(servletRequest, servletResponse);
        }
    }

    //    重写onLoginFailure方法，因为返回值需要固定格式，可以使用ResponseFormat
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        获取错误原因
        Throwable throwable = e.getCause() == null ? e : e.getCause();
//        包装
        ResponseFormat fail = ResponseFormat.fail(throwable.getMessage());

        String json = JSONUtil.toJsonStr(fail);
        try {
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            servletResponse.getWriter().print(json);
        } catch (IOException ex) {
            System.out.println("------------->" + ex);
        }

        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest servletRequest = WebUtils.toHttp(request);
        HttpServletResponse servletResponse = WebUtils.toHttp(response);

        servletResponse.setHeader("Access-control-Allow-Origin", servletRequest.getHeader("Origin"));
        servletResponse.setHeader("Access-control-Allow-Methods", "GET,POST,HEAD,PUT,DELETE,OPTIONS");
        servletResponse.setHeader("Access-control-Allow-Headers", servletRequest.getHeader("Access-control-Request-Headers"));

//        跨域时会首先发一个OPTIONS请求，直接返回一个正常状态
        if (servletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            servletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
