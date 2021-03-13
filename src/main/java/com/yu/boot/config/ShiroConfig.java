package com.yu.boot.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("Manager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        /*
            添加内置过滤器
            anon ： 无需认证可以访问
            authc ： 必须认证才能访问
            user ： 必须记住我 给你才能访问
            perms ： 拥有某个权限才能访问
            role ： 拥有某个角色才能访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //拦截
        filterChainDefinitionMap.put("/shiro/","anon");
        filterChainDefinitionMap.put("/shiro/login","anon");
        //filterChainDefinitionMap.put("/shiro/**","authc");


        //授权拦截 真正的授权在UserRealm对象中授权
        filterChainDefinitionMap.put("/shiro/update","perms[update]");
        filterChainDefinitionMap.put("/shiro/add","perms[add]");

        //设置登陆页面
         bean.setLoginUrl("/shiro/tologin");
        //设置未授权跳转的页面
         bean.setUnauthorizedUrl("/shiro/unauthorized");

         bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }

    @Bean(name = "Manager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getUserRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

    // shiro整合thymeleaf必须拥有的类
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
