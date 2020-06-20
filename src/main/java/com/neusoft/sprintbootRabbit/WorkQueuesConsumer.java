package com.neusoft.sprintbootRabbit;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener  也可以放在方法上
public class WorkQueuesConsumer {
    //会监听 队列中的信息
    @RabbitListener(queuesToDeclare = @Queue(value = "hallo", durable = "true", autoDelete = "false"))
    public void receive1(String message) {
        System.out.println("workQueues 1 工作模型的消息：" + message);
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "work", durable = "true", autoDelete = "false"))
    public void receive2(String message) {
        System.out.println("workQueues 2 工作模型的消息：" + message);
    }
}
