package com.example.demo;

//import com.example.demo.config.DelayRabbitConfig;
import com.example.demo.rabbitmq.AmqpSender;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = RabbitmqTestApplication.class)
@RunWith(value = SpringRunner.class)
class RabbitmqTestApplicationTests {

    @Autowired
    private AmqpSender amqpSender;

    @Test
    void contextLoads() {
    }

    @Test
    public void sendMsg2Mq(){
        for (int i = 0; i < 100; i++) {
            amqpSender.sendMsg("TestDirectExchange","TestDirectRouting",String.valueOf(UUID.randomUUID()).replaceAll("-",""));
        }
        System.out.println("ok");
    }

    /*@Test
    public void sendDelayMsg2Mq(){
        String msg = "测试延迟队列消息,当前时间戳: " + System.currentTimeMillis()/1000;
        amqpSender.sendDelayedMsg(DelayRabbitConfig.TestDelayExchange,DelayRabbitConfig.TestDelayRouting,msg,10*1000L);
        System.out.println("ok");
    }*/
}
