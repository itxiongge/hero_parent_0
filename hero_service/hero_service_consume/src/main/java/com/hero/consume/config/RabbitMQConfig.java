package com.hero.consume.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String SECKILL_ORDER="seckill_order";

    //声明队列，并开启持久化
    @Bean
    public Queue queue(){
        /**
         * 第一个参数：队列名称
         * 第二个参数：是否开启队列持久化
         */
        return new Queue(SECKILL_ORDER,true);
    }
}
