package edu.wong.attendance_management_api.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    private String token;

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
