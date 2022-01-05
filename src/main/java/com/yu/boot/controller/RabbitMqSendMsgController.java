package com.yu.boot.controller;
import com.rabbitmq.client.AMQP;
import com.yu.boot.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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


    @GetMapping("/senMsg/{msg}")
    private String sendMsg(@PathVariable("msg") String msg){
        log.info("收到消息：{},当前系统时间：{}",msg,new Date().toLocaleString());

        Random random = new Random();
        int randomInt = random.nextInt(10); // 随机生成优先级 约大越先执行
        String msg1 = msg + "+" + randomInt;
        CorrelationData corre = new CorrelationData();

        Message message1 = new Message(msg1.getBytes(), null);

        ReturnedMessage returnedMessage = new ReturnedMessage(message1, 1, "发布确认", RabbitMqConfig.DELAYED_EXCHANGE, "delayed-key");
        corre.setReturned(returnedMessage);
        corre.setId(UUID.randomUUID().toString());


        rabbitTemplate.convertAndSend("ordinary_exchange","ordinaryOne", msg1,message -> {
            message.getMessageProperties().setPriority(randomInt);
            return message;
        }, corre);
        //rabbitTemplate.convertAndSend("ordinary_exchange","ordinaryTwo", msg, corre);
        return "消息发送成功";
    }

    // 通过死信来做延迟队列 存在问题 第一个进队列的是10秒 后面在进一个延迟5秒 他会一直执行先进先出原则 第一个不处理的话第二个也不会提前进入死信
    @GetMapping("/senTTLMsg/{msg}/{ttl}")
    private String senTTLMsg(@PathVariable("msg") String msg,@PathVariable("ttl")String ttlTime){
        log.info("死信队列做的延迟队列收到消息：{},当前系统时间：{}",msg,new Date().toLocaleString());
        rabbitTemplate.convertAndSend("ordinary_exchange","ordinaryOne",msg,message -> {
            message.getMessageProperties().setExpiration(ttlTime); // 设置消息存活时间 时间一过就进入死信队列
            return message;
        });
        return "消息发送成功";
    }


    //通过延迟插件来解决上面死信队列做延迟队列的问题 这个插件交换机他会将消息放在交换机中来进行延迟 延迟时间一到就将消息放入队列中
    @GetMapping("/sendDelayedMsg/{msg}/{delayedTime}")
    private String senTTLMsg(@PathVariable("msg") String msg,@PathVariable("delayedTime")Integer delayedTime){

        CorrelationData corre = new CorrelationData();

        Message message1 = new Message(msg.getBytes(), null);

        ReturnedMessage returnedMessage = new ReturnedMessage(message1, 1, "发布确认", RabbitMqConfig.DELAYED_EXCHANGE, "delayed-key");
        corre.setReturned(returnedMessage);
        corre.setId(UUID.randomUUID().toString());

        log.info("延迟队列收到消息：{},延迟时间：{} 当前系统时间：{}",msg, delayedTime, new Date().toLocaleString());

        rabbitTemplate.convertAndSend(RabbitMqConfig.DELAYED_EXCHANGE,"delayed-key",msg, message -> {
            message.getMessageProperties().setDelay(delayedTime); // 设置延迟时间
            return message;
        },corre);
        return "消息发送成功";
    }

}
