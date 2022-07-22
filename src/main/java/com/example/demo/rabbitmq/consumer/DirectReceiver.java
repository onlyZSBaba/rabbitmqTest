package com.example.demo.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author 36569
 * @Date 2022-07-20 11:01
 * @Version 1.0
 */
@Component
public class DirectReceiver {

    //监听的队列名称 TestDirectQueue
    //@RabbitListener(queues = "TestDirectQueue",containerFactory = "customContainerFactory")
    public void directQueueProcess(Message testMessage) {
        String message = new String(testMessage.getBody(), StandardCharsets.UTF_8);
        System.out.println("DirectReceiver消费者收到消息  : " + message);
    }

    /*@RabbitListener(queues = "TestDelayQueue",containerFactory = "customContainerFactory")
    public void delayQueueProcess(Message testMessage) {
        String message = new String(testMessage.getBody(), StandardCharsets.UTF_8);
        System.out.println("DelayReceiver消费者收到消息  : " + message + " 当前时间戳: " + System.currentTimeMillis()/1000);
    }*/

}
