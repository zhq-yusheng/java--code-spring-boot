package com.yu.boot.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsynService {

    @Async // 要异步执行的方法加上@Async注解在在启动类上加@EnableAsync 来开启异步处理
    public void ssleep(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据处理完毕！！");
    }
}
