package com.yu.boot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ProjectName: boot
 * @Package: com.yu.boot.service
 * @ClassName: RabbitMqMyCallBack
 * @Author: 钟洪强
 * @Description: rabbitmq发布确认回调接口
 * @Date: 2022/1/4 15:16
 * @Version: 1.0
 */

@Component
@Slf4j
public class RabbitMqMyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct // 当所有代码执行完在执行该方法
    private void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /*
     *@Author 钟洪强
     *@Description:回调的方法
     * 参数1 CorrelationData 这个参数是存放消息数据的东西 里面能放id 和消息体 这个得我们在发送消息的时候自己创建带过来
     * 参数2 flag 返回这个消息是否被交换机确认 成功被接收就为true 失败拒收则false
     * 参数3 reason 当成功时为null 失败则是失败原因
     *@Param:
     *@Ruturn:
     *@Create: 2022/1/4 15:22
     */

    @Override
    public void confirm(CorrelationData correlationData, boolean flag, String reason) {

        String id = correlationData.getId() == null ? "" : correlationData.getId();

            if(flag){
               log.info("消息id为{} 消息体：{} 的消息发送交换机成功",id,new String(correlationData.getReturned().getMessage().getBody()));
            }else {
                log.error("消息id：{} 的消息交换机接收失败，失败原因:{}",id,reason);
            }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error(returned.toString());
    }
}
