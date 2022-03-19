package edu.wong.attendance_management_api.shiro;

import cn.hutool.core.bean.BeanUtil;
import edu.wong.attendance_management_api.entity.Student;
import edu.wong.attendance_management_api.service.IStudentService;
import edu.wong.attendance_management_api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtil util;

    @Autowired
    IStudentService service;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String subject = util.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        Student student = service.getById(subject);
//        如果用户为空抛出用户不存在异常
        if (student == null) {
            throw new UnknownAccountException();
        }
//        否则返回SimpleAuthenticationInfo
//        包装SimpleAuthenticationInfo第一个参数，用户一些非重要的信息
        AccountProfile accountProfile = new AccountProfile();
        BeanUtil.copyProperties(student, accountProfile);


        return new SimpleAuthenticationInfo(accountProfile, jwtToken.getCredentials(), getName());
    }
}
