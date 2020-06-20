package com.neusoft.sprintbootRabbit;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *  RabbitListener 做监听  指定监听接受哪个队列的消息
 *  queuesToDeclare 没有队列的话就回去声明一个队列
 *  value 队列  durable 是否持久化  autoDelete 不是自动删除   非独占
 *
 */

@Component
@RabbitListener(queuesToDeclare = @Queue(value = "hello",durable = "false",autoDelete = "true"))
public class DirectLinkConsumer {
    /**
     * 从队列中取出消息的回调方法去处理消息
     */
    @RabbitHandler
    public void receive(String message){
        System.out.println("消息队列中的消息 " + message);
    }
}
