package com.yu.boot.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    //拦截
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        // 固定格式：antMatchers 写拦截的路径 permitAll方法代表都能访问 hasAnyRole 设置他要什么身份才能访问
        http.authorizeRequests().antMatchers("/").permitAll()
                                .antMatchers("/level1/**").hasAnyRole("vip1")
                                .antMatchers("/level2/**").hasAnyRole("vip2")
                                .antMatchers("/level3/**").hasAnyRole("vip3");

        //默认跳转到login页面 http.formLogin()
        //loginPage() 是设置自己制定的登陆页面的提交地址
        // usernameParameter是设置表单提交的name参数名 不设置默认的是user password
        http.formLogin().loginPage("/tologin").usernameParameter("user").passwordParameter("pwd");


        //注销功能将cookie删掉 指定注销后返回的地址指定默认返回登陆页面
        http.logout().deleteCookies("remove").invalidateHttpSession(true).logoutSuccessUrl("/");

        //关闭安全防护 不然注销返回不了页面
        http.csrf().disable();


        // http.rememberMe()  增加记住我功能 把cookie和session存进浏览器
        //rememberMeParameter("remember"); 这是自定义的登陆页面中的记住我按钮的name值
        http.rememberMe().rememberMeParameter("remember");//这是自定义的登陆页面中的记住我按钮的name值
    }


    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        //固定格式 withUser用户名 password new一个密码的加密类 roles认证给他什么身份
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and()
                .withUser("yusheng").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2")
                .and()
                .withUser("luren").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");

    }
}
