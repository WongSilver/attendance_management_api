package edu.wong.attendance_management_api.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * shiro默认supports的是UsernamePasswordToken，而我们现在采用了jwt的方式，所以这里我们自定义一个JwtToken，来完成shiro的supports方法。
 */
public class JwtToken implements AuthenticationToken {

    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    //    唯一的值（uuid等等）
    @Override
    public Object getPrincipal() {
        return token;
    }

    //   密钥
    @Override
    public Object getCredentials() {
        return token;
    }
}
