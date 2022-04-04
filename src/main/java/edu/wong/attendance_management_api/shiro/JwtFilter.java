package edu.wong.attendance_management_api.shiro;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    //    没有登录的状态下会执行
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest servletRequest = WebUtils.toHttp(request);
        String token = servletRequest.getHeader("Authorization");
//        System.out.println("---------Authorization------" +servletRequest.getHeader("authorization"));
//        System.out.println("---------token------" +token);
        //如果没有jwt就交给注解处理，有就进行校验和登陆处理
        if (token == null || "null".equals(token)) {
            return true;
        }
//        System.out.println("---------------" + util.getClaimByToken(token).getExpiration());
//        System.out.println("---------------" + util.isTokenExpired(util.getClaimByToken(token).getExpiration()));
        //校验Jwt
        Claims claimByToken = util.getClaimByToken(token);
        if (claimByToken == null || util.isTokenExpired(claimByToken.getExpiration())) {
            throw new ExpiredCredentialsException("token已失效，请重新登录");
        }
        return executeLogin(request, response);
    }

    //    重写onLoginFailure方法，因为返回值需要固定格式，可以使用ResponseFormat
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
//        获取错误原因
        Throwable throwable = e.getCause() == null ? e : e.getCause();
//        返回登录失败 包装成json
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
