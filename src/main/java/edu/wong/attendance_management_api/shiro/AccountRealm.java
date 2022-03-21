package edu.wong.attendance_management_api.shiro;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.*;
import edu.wong.attendance_management_api.mapper.RightMapper;
import edu.wong.attendance_management_api.mapper.RoleMapper;
import edu.wong.attendance_management_api.mapper.RoleRightMapper;
import edu.wong.attendance_management_api.mapper.UserRoleMapper;
import edu.wong.attendance_management_api.service.IUserService;
import edu.wong.attendance_management_api.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Resource
    private JwtUtil util;
    @Resource
    private IUserService userService;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleRightMapper roleRightMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RightMapper rightMapper;
    private List<Role> roles;
    private List<Right> rights;

    //    根据token判断此Authenticator是否使用该realm
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        获取token对应的用户
        User user = (User) principalCollection.getPrimaryPrincipal();

        try {
//            通过 用户ID 查询 角色ID
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("user_id", user.getId());
            List<UserRole> userRoles = userRoleMapper.selectByMap(userMap);

//            通过角色ID 查询 角色名
            HashMap<String, Object> roleMap = new HashMap<>();
            userRoles.forEach(role -> roleMap.put(role.getRoleId().toString(), role.getRoleId()));
            roles = roleMapper.selectByMap(roleMap);


//            通过 角色ID 查询 权限ID
            HashMap<String, Object> rightMap = new HashMap<>();
            userRoles.forEach(o -> rightMap.put(o.getRoleId().toString(), o.getRoleId()));
            List<RoleRight> roleRights = roleRightMapper.selectByMap(rightMap);

//            通过 权限ID 查询 可操作url
            HashMap<String, Object> urlMap = new HashMap<>();
            roleRights.forEach((o -> urlMap.put(o.getRightId().toString(), o.getRightId())));
            rights = rightMapper.selectByMap(urlMap);

            rights.forEach(o -> System.out.println(o.getUrl()));
        } catch (Exception e) {
            ResponseFormat.fail("权限查询失败");
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        添加角色
        roles.forEach(role -> info.addRole(role.getName()));
//        添加权限
        rights.forEach(url -> info.addRole(url.getUrl()));

//        test
        System.out.println("--------角色---------");
        roles.forEach(System.out::println);
        System.out.println("--------权限---------");
        rights.forEach(System.out::println);

        return info;
    }

    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String subject = util.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        User user = userService.getById(subject);
//        如果用户为空抛出用户不存在异常
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        } else if (user.getStatus() == 0) {
            throw new LockedAccountException("账户被锁定");
        }
//        否则返回SimpleAuthenticationInfo
//        包装SimpleAuthenticationInfo第一个参数，用户一些非重要的信息
        return new SimpleAuthenticationInfo(user, jwtToken.getCredentials(), getName());
//        return new SimpleAuthenticationInfo();
    }
}
