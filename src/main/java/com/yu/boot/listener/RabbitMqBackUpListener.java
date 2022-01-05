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
 * @Description: boot中所有的队列都是通过监听来获取消息的 备份交换机队列消费内容
 * @Date: 2021/12/30 16:27
 * @Version: 1.0
 */
@Component
@Slf4j
public class RabbitMqBackUpListener {

    @RabbitListener(queues = "backup_queue1")
    private void DeadLetterQueue(Message msg, Channel channel){
        //这个可以存日志
        log.info("备份交换机队列1收到消息:{} 当前时间:{}", new String(msg.getBody()),new Date().toLocaleString());

    }

    @RabbitListener(queues = "backup_queue2")
    private void BackUpQueue(Message msg, Channel channel){
        //这个可以给警告说原备份有问题
        log.info("备份交换机队列2收到消息:{} 当前时间:{}", new String(msg.getBody()),new Date().toLocaleString());

    }
}
