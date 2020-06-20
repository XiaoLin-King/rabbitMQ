package com.neusoft.sprintbootRabbit;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RoutingConsumer {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "directs", type = "direct"),//自定义交换机名称和类型
                    key = {"info", "error", "warn"}
            )
    })
    public void receive1(String message) {
        System.out.println("路由模式 1 接收info  error  warn 信息" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "directs", type = "direct"),//自定义交换机名称和类型
                    key = {"error"}
            )
    })
    public void receive2(String message) {
        System.out.println("路由模式 2 接收error信息" + message);
    }
}
