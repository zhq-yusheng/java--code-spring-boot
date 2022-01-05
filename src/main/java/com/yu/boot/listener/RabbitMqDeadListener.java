package com.yu.boot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: boot
 * @Package: com.yu.boot.listener
 * @ClassName: RabbitMqDeadListener
 * @Author: 钟洪强
 * @Description: boot中所有的队列都是通过监听来获取消息的
 * @Date: 2021/12/30 16:27
 * @Version: 1.0
 */
@Component
@Slf4j
public class RabbitMqDeadListener {

    @RabbitListener(queues = "deadLetter_queue")
    private void DeadLetterQueue(Message msg, Channel channel){

        log.info("死信队列收到消息:{}", new String(msg.getBody()));

    }

    //@RabbitListener(queues = "ordinary_queueA")
    private void ordinaryQueueA(Message msg, Channel channel){

        log.info("普通队列A队列收到消息:{}", new String(msg.getBody()));

    }
}
