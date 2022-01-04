package com.yu.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: boot
 * @Package: com.yu.boot.config
 * @ClassName: RabbitMqConfig
 * @Author: 钟洪强
 * @Description: rabbitMq配置交换机 与配置队列的创建于配置类
 * @Date: 2021/12/30 15:15
 * @Version: 1.0
 */

@Configuration
public class RabbitMqConfig {
    // 普通队列
    public static final String ORDINARY_QUEUEA = "ordinary_queueA";
    public static final String ORDINARY_QUEUEB = "ordinary_queueB";
    // 死信队列
    public static final String DEADLETTER_QUEUE = "deadLetter_queue";

    // 普通交换机
    public static final String ORDINARY_EXCHANGE = "ordinary_exchange";
    // 死信交换机
    public static final String DEADLETTER_EXCHANGE = "deadLetter_exchange";

    //延迟交换机
    public static final String DELAYED_EXCHANGE = "delayed_exchange";

    //延迟队列 其实就是一个简单的队列
    public static final String DELAYED_QUEUE = "delayed_queue";


    // 创建基于插件的延迟交换机 java没有直接提供这个的实现类 所以得自定义这个交换机
    @Bean("delayedExchange")
    public CustomExchange delayedExchange(){
        Map<String,Object> mps = new HashMap<>();
        mps.put("x-delayed-type","direct"); // 将延迟交换机设置直接交换机

        //这个就和原生一样 交换机名字 交换机类型 是否持久化 是否自动删除 还有一个其他自定义参数
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", false, true, mps);
    }


    // 创建延迟的队列
    @Bean("delayedQueue")
    public Queue delayedQueue(){
        return QueueBuilder.durable(DELAYED_QUEUE).build();
    }

    // 死信交换机绑定死信队列
    @Bean
    public Binding bingDelayed(@Qualifier("delayedExchange") CustomExchange delayedExchange,
                            @Qualifier("delayedQueue") Queue delayedQueue){ // 这要多一步构建
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with("delayed-key").noargs();
    }


    // 创建普通交换机
    @Bean("ordinaryExchange")
    public DirectExchange ordinaryExchange(){
        return new DirectExchange(ORDINARY_EXCHANGE);
    }


    // 创建死信交换机
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DEADLETTER_EXCHANGE);
    }

    // 创建普通队列1
    @Bean("ordinaryQueueA")
    public Queue ordinaryQueueA(){
        Map<String, Object> maps = new HashMap<>();
        maps.put("x-dead-letter-exchange", DEADLETTER_EXCHANGE); //设置死信交换机
        maps.put("x-dead-letter-routing-key", "dead-key"); // 设置死信交换机的key
        //maps.put("x-message-ttl",10000); // 设置消息存活的时间 消息发送的时候来设置消息存活时间

        return QueueBuilder.durable(ORDINARY_QUEUEA).withArguments(maps).build();
    }

    // 创建普通队列2
    @Bean("ordinaryQueueB")
    public Queue ordinaryQueueB(){
        Map<String, Object> maps = new HashMap<>();
        maps.put("x-dead-letter-exchange", DEADLETTER_EXCHANGE); //设置死信交换机
        maps.put("x-dead-letter-routing-key", "dead-key"); // 设置死信交换机的key
        maps.put("x-message-ttl",40000); // 设置消息存活的时间

        return QueueBuilder.durable(ORDINARY_QUEUEB).withArguments(maps).build();
    }

    // 创建死信队列
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(DEADLETTER_QUEUE).build();
    }


    // 普通交换机绑定队列1
    @Bean
    public Binding bingDingOrdinaryOne(@Qualifier("ordinaryExchange") DirectExchange ordinaryExchange,
                            @Qualifier("ordinaryQueueA") Queue ordinaryQueueA){
        return BindingBuilder.bind(ordinaryQueueA).to(ordinaryExchange).with("ordinaryOne");
    }

    // 普通交换机绑定队列2
    @Bean
    public Binding bingDingOrdinaryTwo(@Qualifier("ordinaryExchange") DirectExchange ordinaryExchange,
                            @Qualifier("ordinaryQueueB") Queue ordinaryQueueB){
        return BindingBuilder.bind(ordinaryQueueB).to(ordinaryExchange).with("ordinaryTwo");
    }

    // 死信交换机绑定死信队列
    @Bean
    public Binding bingDead(@Qualifier("deadLetterExchange") DirectExchange deadLetterExchange,
                                       @Qualifier("deadLetterQueue") Queue deadLetterQueue){
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("dead-key");
    }

}
