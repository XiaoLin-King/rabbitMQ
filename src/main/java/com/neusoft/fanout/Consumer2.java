package com.neusoft.fanout;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) {
        System.out.println("消费者2 启动");
        try {
            Connection conn = RabbitMQUtil.getConnection();
            Channel channel = conn.createChannel();
            //通道绑定交换机
            /**
             * 参数一：声明交换机名称  具体自己定义 看它需要实现什么业务 参数二：交换机类型 fanout 广播类型
             */
            channel.exchangeDeclare("logs", "fanout");
            //临时队列
            String queue = channel.queueDeclare().getQueue();
            //消费消息
            /**
             * 参数一：队列名字 参数二：exchange 交换机名字 参数三 routingKey： 路由Key
             */
            channel.queueBind(queue, "logs", "");
            //消费消息
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费者2  消费消息：" + new String(body));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}