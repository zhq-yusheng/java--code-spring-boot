package com.yu.boot.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.HashMap;

@Configuration
public class DruidConfig {


    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource getDataSource(){
        return new DruidDataSource();
    }


    @Bean
    public ServletRegistrationBean aStatViewServlet(){
        ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        HashMap<String, String> initParameters = new HashMap<>();
        //设置登陆账号
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","123456");
        //配置谁能访问 空位全部都能访问localhost为本机能访问
        //访问http://localhost:8080/druid/login.html 登陆
        initParameters.put("allow","");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
