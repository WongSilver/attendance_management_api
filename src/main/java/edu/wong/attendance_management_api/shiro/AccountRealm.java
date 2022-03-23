package edu.wong.attendance_management_api.shiro;

import edu.wong.attendance_management_api.common.lang.ResponseFormat;
import edu.wong.attendance_management_api.entity.Right;
import edu.wong.attendance_management_api.entity.User;
import edu.wong.attendance_management_api.entity.UserRole;
import edu.wong.attendance_management_api.mapper.RightMapper;
import edu.wong.attendance_management_api.mapper.RoleMapper;
import edu.wong.attendance_management_api.mapper.UserRoleMapper;
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
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {

    @Resource
    private JwtUtil util;
    @Resource
    private IUserService userService;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RightMapper rightMapper;
    private List<Right> rights;

    //    根据token判断此Authenticator是否使用该realm
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        this.clearCachedAuthenticationInfo(principalCollection);
        this.clearCachedAuthorizationInfo(principalCollection);
//        获取token对应的用户
        User user = (User) principalCollection.getPrimaryPrincipal();

        try {
//            通过 用户ID 查询 角色ID
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("user_id", user.getId());
            List<UserRole> userRoles = userRoleMapper.selectByMap(userMap);

//            System.out.println("==================>角色<==================");
//            通过角色ID 查询 角色名
            HashMap<String, Object> roleMap = new HashMap<>();
            ArrayList<Integer> rightList = new ArrayList<>();
            userRoles.forEach(userRole -> {
//            可能会出现一个用户多个角色，查询的权限名放入info
//            查询的角色ID放入List给查询权限使用
                roleMap.put("id", userRole.getRoleId());
                roleMapper.selectByMap(roleMap).forEach(role -> {
                    info.addRole(role.getName());
                    rightList.add(role.getId());
//                    System.out.println("==================>" + role.getName());
                });
            });

//            System.out.println("==================>权限<==================");
//            遍历rightList，把该用户所有的角色ID取出来
//            通过 角色ID 查询 可操作的Url
//            添加权限到Shiro
            rightList.forEach(id -> {
                rights = rightMapper.selectRightUrlByRoleID(id);
                rights.forEach(url -> {
                    info.addStringPermission(url.getUrl());
//                    System.out.println("==================>" + url.getUrl());
                });
            });

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
            throw new LockedAccountException("账户被锁定");
        }
//        否则返回SimpleAuthenticationInfo
//        包装SimpleAuthenticationInfo第一个参数，用户一些非重要的信息
        return new SimpleAuthenticationInfo(user, jwtToken.getCredentials(), getName());
    }
}
