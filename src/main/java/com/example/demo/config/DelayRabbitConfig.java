package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列配置类
 * @Author 36569
 * @Date 2022-07-20 14:06
 * @Version 1.0
 */
/*@Configuration
public class DelayRabbitConfig {

    public static final String TestDelayExchange = "TestDelayExchange";
    public static final String TestDelayQueue = "TestDelayQueue";
    public static final String TestDelayRouting = "TestDelayRouting";

    @Bean
    public CustomExchange TestDelayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(TestDelayExchange, "x-delayed-message",true, false,args);
    }

    @Bean
    public Queue TestDelayQueue() {
        return new Queue(TestDelayQueue,true);
    }

    @Bean
    Binding bindingDelay() {
        return BindingBuilder.bind(TestDelayQueue()).to(TestDelayExchange()).with(TestDelayRouting).noargs();
    }

}*/
