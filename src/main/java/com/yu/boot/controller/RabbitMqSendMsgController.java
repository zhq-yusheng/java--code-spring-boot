package com.yu.boot.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ProjectName: boot
 * @Package: com.yu.boot.controller
 * @ClassName: RabbitMqSendMsgControlller
 * @Author: 钟洪强
 * @Description: 通过controller接收页面的消息来发送信息
 * @Date: 2021/12/30 16:15
 * @Version: 1.0
 */

@RestController
@Slf4j
public class RabbitMqSendMsgController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener

    @GetMapping("/senMsg/{msg}")
    private String sendMsg(@PathVariable("msg") String msg){
        log.info("收到消息：{},当前系统时间：{}",msg,new Date().toLocaleString());
        rabbitTemplate.convertAndSend("ordinary_exchange","ordinaryOne",msg);
        rabbitTemplate.convertAndSend("ordinary_exchange","ordinaryTwo",msg);
        return "消息发送成功";
    }
}
