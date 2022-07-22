package com.example.demo.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class AmqpSender implements RabbitTemplate.ConfirmCallback{

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public AmqpSender(RabbitTemplate rabbitTemplate){
        rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * broker确认的生产者回调
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }

    /**
     * 向指定交换机/队列发送即时消息
     * @param exchange
     * @param routeKey
     * @param content json格式
     */
    public void sendMsg(String exchange, String routeKey, JSONObject content){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                exchange,
                routeKey,
                content,
                correlationId
        );
    }

    /**
     * 发送即时消息
     * @param exchange
     * @param routeKey
     * @param content
     */
    public void sendMsg(String exchange,String routeKey,String content){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        try {
            rabbitTemplate.convertAndSend(
                    exchange,
                    routeKey,
                    content.getBytes(StandardCharsets.UTF_8),
                    correlationId
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送延迟消息
     * @param exchange
     * @param routeKey
     * @param msg
     * @param timeOut 单位毫秒
     */
    public void sendDelayedMsg(String exchange,String routeKey,String msg,Long timeOut){
        rabbitTemplate.convertAndSend(
                exchange,
                routeKey,
                msg,
                message -> {
                    message.getMessageProperties().setHeader("x-delay",timeOut);
                    return message;
                });
    }
}
