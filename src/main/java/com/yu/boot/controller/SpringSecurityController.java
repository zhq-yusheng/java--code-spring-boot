package com.yu.boot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/springSecurity")
public class SpringSecurityController {

    @RequestMapping({"/","/index","/index.html"})
    public String index(){
        return "springSecurity/index";
    }
    @RequestMapping("/tolog")
    public String login(){
        return "springSecurity/login";
    }

    @GetMapping("/level1/{id}")
    public String level1(@PathVariable("id") int id){
        return "springSecurity/level1/"+id;
    }
    @RequestMapping("/level2/{id}")
    public String level2(@PathVariable("id")int id){
        return "springSecurity/level2/"+id;
    }

    @RequestMapping("/level3/{id}")
    public String level3(@PathVariable("id")int id){
        System.out.println(id);
        return "springSecurity/level3/"+id;
    }

}
