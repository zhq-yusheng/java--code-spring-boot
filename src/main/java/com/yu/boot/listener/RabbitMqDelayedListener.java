package com.yu.boot.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ProjectName: boot
 * @Package: com.yu.boot.listener
 * @ClassName: RabbitMqDeadListener
 * @Author: 钟洪强
 * @Description: boot中所有的队列都是通过监听来获取消息的 通过插件实现延迟队列
 * @Date: 2021/12/30 16:27
 * @Version: 1.0
 */
@Component
@Slf4j
public class RabbitMqDelayedListener {

    @RabbitListener(queues = "delayed_queue")
    private void DeadLetterQueue(Message msg, Channel channel){

        log.info("延迟队列收到消息:{} 当前时间:{}", new String(msg.getBody()),new Date().toLocaleString());

    }
}
