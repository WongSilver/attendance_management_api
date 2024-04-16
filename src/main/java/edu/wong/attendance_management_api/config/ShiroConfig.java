package edu.wong.attendance_management_api.config;

import edu.wong.attendance_management_api.shiro.AccountRealm;
import edu.wong.attendance_management_api.shiro.JwtFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

    @Resource
    JwtFilter jwtFilter;

    @Bean
    public SessionManager sessionManager(SessionDAO dao) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(dao);
        return sessionManager;
    }

    @Bean
    public SessionsSecurityManager securityManager(AccountRealm accountRealm,
                                                   SessionManager sessionManager) {

//        设置自定义的Realm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);
        securityManager.setSessionManager(sessionManager);
//        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    /**
     * 过滤规则
     * anno，任何人都可以访问；
     * authc：必须是登录之后才能进行访问，不包括remember me；
     * user：登录用户才可以访问，包含remember me；
     * perms：指定过滤规则，这个一般是扩展使用，不会使用原生的
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**", "jwt");
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;
    }

    //    配置shiroFilter拦截器
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroFilterChainDefinition chainDefinition) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setLoginUrl("/login");
        // 必须设置 SecurityManager
        factoryBean.setSecurityManager(securityManager);
        // 设置过滤器
        HashMap<String, Filter> filterHashMap = new HashMap<>();
        filterHashMap.put("jwt", jwtFilter);
        factoryBean.setFilters(filterHashMap);
        // 设置过滤规则
        Map<String, String> filterChainMap = shiroFilterChainDefinition().getFilterChainMap();
        factoryBean.setFilterChainDefinitionMap(filterChainMap);

        return factoryBean;
    }

}