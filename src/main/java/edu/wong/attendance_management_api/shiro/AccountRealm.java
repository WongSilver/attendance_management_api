package edu.wong.attendance_management_api.shiro;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.entity.dto.UserDTO;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {

    @Resource
    private JwtUtil util;
    @Resource
    private IUserService userService;

    //    根据token判断此Authenticator是否使用该realm
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取token对应的用户
        User user = (User) principalCollection.getPrimaryPrincipal();
        try {
            UserDTO userInfo = userService.getUserInfo(user.getId());
            info.addRole(userInfo.getRoleName());
            List<String> url = new ArrayList<>();
            userInfo.getRights().forEach(right -> url.add(right.getUrl()));
            info.addStringPermissions(url);

        } catch (Exception e) {
            ResponseFormat.fail("权限查询失败");
        }

        return info;
    }

    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("-------------认证--------------");
        JwtToken jwtToken = (JwtToken) token;
        String subject = util.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(subject);
//        如果用户为空抛出用户不存在异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        } else if (user.getStatus() == 0) {
            throw new LockedAccountException("用户被锁定，请联系管理员");
        }
//        否则返回SimpleAuthenticationInfo
//        包装SimpleAuthenticationInfo第一个参数，用户一些非重要的信息
        return new SimpleAuthenticationInfo(user, jwtToken.getCredentials(), getName());
    }
}
