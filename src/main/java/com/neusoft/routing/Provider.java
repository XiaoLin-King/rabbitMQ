package com.neusoft.routing;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 路由模型  针对不同的路由Key 不同订阅消费者进行消息消费
 */

public class Provider {
    public static void main(String[] args) {
        System.out.println("生产者 进行生产消息");
        try {
            Connection conn = RabbitMQUtil.getConnection();
            Channel channel = conn.createChannel();
            //通道声明交换机以及交换机类型
            /**
             * 参数一 exchange：交换机名称  参数二 direct： 路由模式
             */
            channel.exchangeDeclare("logs_direct", "direct");
            //发送消息
            String routingKet = "error";
            String message = "这是direct 模型发布的基于routingKey：【" + routingKet + "】发送的消息";
            channel.basicPublish("logs_direct", routingKet, null, message.getBytes());
            RabbitMQUtil.closeConnAndChanel(channel, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}