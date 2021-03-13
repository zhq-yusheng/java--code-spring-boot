package com.yu.boot.controller;

import com.yu.boot.service.AsynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AsynController {


    @Autowired
    AsynService asynService;

    @GetMapping("/asyn")//异步请求先返回数据数据 在后台开线程处理数据
    @ResponseBody
    public String asynTest(){

        asynService.ssleep(); //方法会睡眠3秒 但是页面会直接返回ok不会让前端等着三秒

        return "ok";
    }
}
