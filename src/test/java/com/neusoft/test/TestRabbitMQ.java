package com.neusoft.test;

import com.neusoft.RabbitmqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RabbitmqApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {
    //注入RabbitMQTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //直连模型
    @Test
    public void directLink() {
        //只运行此代码是不会创建队列的，只有存在了消费者了之后才会进行队列创建，没有消费者就没有任何意义
        //公平消费
        rabbitTemplate.convertAndSend("hallo", "Hello RabbitMQ Test");
    }

    //workqueues
    @Test
    public void workQueues(){
        rabbitTemplate.convertAndSend("work","workQueues 模型");
    }

    //fanout 广播模式
    @Test
    public void fanout(){
        /**
         * 参数一：交换机
         * 参数二：路由键
         * 参数三:消息
         */
        rabbitTemplate.convertAndSend("logs","","fanout 模型发送的Rabbit 消息");
    }

    //route 路由模式
    @Test
    public void testRoute(){
        rabbitTemplate.convertAndSend("directs","error","路由模式下进行RabbitMQ 测试info 的路由键的路有消息");
    }

    //topic 动态路由/订阅模式
    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("topics","user.save.add","user.save   路由信息");
    }

}
