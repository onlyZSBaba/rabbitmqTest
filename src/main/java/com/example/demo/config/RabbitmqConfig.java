package com.example.demo.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;

/**
 * @Author 36569
 * @Date 2022-07-20 13:40
 * @Version 1.0
 */
@SpringBootConfiguration //包含@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    //@Value("${spring.rabbitmq.virtual-host}")
    private String virtualhost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        //connectionFactory.setVirtualHost(virtualhost);
        //connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /**
     *在springboot 整合rabbitmq下  rabbitTemplate 默认是单例形式
     * 如果仅是发送队列和接受队列消息 该单例模式就足够使用了
     * 如果想要 对于 发布端进行消息推送确认，那么单例模式是无法满足的
     * 如果我们有多个队列，并需要对每个队列发送是否成功的消息进行确认
     * 这种情况下，如果是单例模式，那么整个项目中，仅有个一confirm 和 returncallback 实现接口
     * 对于所有的队列消息的确认也只能在这一个接口中得到回复，这样就很难辨别确认的消息响应是具体哪个队列的。
     *
     * 所以这样的情况下，单例是无法满足的，因此需要使用多例模式
     * 如果非要这样时候怎么办，那就不要使用@Autowired 这种方式去获取server的实例，这种获取其实也是单例的。
     * applicationContent.getBean(RabbitTemplate.class);
     * @return
     */
    @Bean
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    /**
     * 配置消费者线程池工厂
     * @return
     */
    @Bean(name = "customContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(10);  //设置线程数
        factory.setMaxConcurrentConsumers(10); //最大线程数
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}