package com.yu.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 // 开启swagger
public class SwaggerCongfig {

    //配置swagger
    @Bean
    public Docket docket(Environment environment){ //配置多个Docket bean就会在组中切换 利于团队开发
        //面试题：如何在生产环境（dev）中开启swagger 发布的环境（pro）中不适用swagger
        /*
        通过environment对象获得当前环境 在使用Docket的enable方法设置开启关闭
         例如：
        Profiles profiles = Profiles.of("dev","test");//可变参数 可以设置多个
        boolean flg = environment.acceptsProfiles(profiles);
        监听当前环境是否为["dev","test"]中的一个，是就返回true 不是则false 在将flg传Docket的enable(flg)进行设置即可
        */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .groupName("余生1号接口文档")//设置组名
                .host("127.0.0.1:8080")//设置主机地址 改了主机名就得去controller层中加上跨域注解不然测试不了
                .select()
                /*
                RequestHandlerSelectors的方法 扫描指定的接口进swagger页面
                any() 扫描全部的
                basePackage("com.yu.boot.controller") 扫描指定的包
                none() 不扫描
                withClassAnnotation(Controller.class) 只扫描带有Controller注解的类
                withMethodAnnotation(GetMapping.class) 只扫描带有GetMapping注解的方法
                 */
                .apis(RequestHandlerSelectors.basePackage("com.yu.boot.controller"))
                /*
                    paths 过滤器 方法和上面apis的作用一样
                    ant("/shiro/**") 只让/shiro/** 路径的接口扫描
                    regex() 正则
                 */
                //.paths(PathSelectors.ant("/shiro/**"))
                .build()
                .enable(true); //是否开启swagger true为开启(默认) false为关闭
    }
    private ApiInfo getApiInfo(){

        return new ApiInfo(


                "余生的ControllerAPI接口文档",
                "余生学习swagger的测试接口文档",
                "1.0",
                "https://www.baidu.com/",
                //作者信息
                new Contact("余生", "https://www.baidu.com/", "2545763087@qq.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    @Bean
    public Docket getdocket(){  //配置第二个组

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .groupName("Swagger测试文档api文档注释")//设置组名
                .host("127.0.0.1:8080")//设置主机地址 改了主机名就得去controller层中加上跨域注解不然测试不了
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yu.boot.controller"))

                .paths(PathSelectors.ant("/swagger/**"))
                .build()
                .enable(true); //是否开启swagger true为开启(默认) false为关闭
    }

}
