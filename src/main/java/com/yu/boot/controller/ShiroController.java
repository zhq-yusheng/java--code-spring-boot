package com.yu.boot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shiro")
public class ShiroController {

    @GetMapping({"/index","/","index.html"})
    @CrossOrigin //跨域注解
    public String index(){
        return "shiro/index";
    }
    @GetMapping("/add")
    public String toAdd(){
        return "shiro/user/add";
    }
    @GetMapping("/update")
    public String toUpdate(){
        return "shiro/user/update";
    }
    @GetMapping("/tologin")
    public String tologin(){
        return "shiro/login";
    }
    @GetMapping("/login")
    public String login(String user, String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user, password);
        try {
            subject.login(token);
             return "shiro/index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","账号不存在");
            return "shiro/login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg","密码错误");
            return "shiro/login";
        }
    }

    @GetMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "未授权不能访问此页面！";
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "shiro/index";
    }

}
