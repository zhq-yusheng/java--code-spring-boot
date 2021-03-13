package com.yu.boot.config;

import com.yu.boot.pojo.ShiroUser;
import com.yu.boot.service.ShiroUserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    ShiroUserServiceImpl shiroUserService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("进入了doGetAuthorizationInfo=>授权~~");
        Subject subject = SecurityUtils.getSubject(); // 获得当前那对象
        subject.logout();

        // 获得 认证方法传来的数据库查询传来的ShiroUser对象
        ShiroUser user = (ShiroUser) subject.getPrincipal();

        SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo();
        // 把数据库查询到的权限授予这对象
        authorization.addStringPermission(user.getPerms());

        return authorization;
    }


    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进入了doGetAuthenticationInfo=>认证~~");

        /*
        String user = "余生";
        String password= "123456";
        假数据
        */
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        /*
        if(!user.equals(username)){
            return null;
        }
            假数据测试

        return new SimpleAuthenticationInfo("",password,"");

         */
        // 数据库数据测试
        ShiroUser user = shiroUserService.getUserByName(username);
        Subject subject = SecurityUtils.getSubject();
        if(user==null){
            return null;
        }
        Session session = subject.getSession();
        session.setAttribute("loginUser",user);
        //shiro自动帮我们验证密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
