package com.neusoft.routing;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) {
        System.out.println("消费者2   启动");
        try {
            String exchangeName = "logs_direct";
            Connection conn = RabbitMQUtil.getConnection();
            Channel channel = conn.createChannel();
            //通道声明交换机以及交换机类型
            channel.exchangeDeclare(exchangeName, "direct");
            //创建临时队列
            String queue = channel.queueDeclare().getQueue();
            //基于路由Key绑定交换机和队列
            channel.queueBind(queue, exchangeName, "info");
            channel.queueBind(queue, exchangeName, "error");
            channel.queueBind(queue, exchangeName, "warning");
            //获取消费者的消息
            /**
             * 参数一：队列 参数二：Ack 参数三：匿名内部类进行消息处理
             */
            channel.basicConsume(queue, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费者2 进行消息消费" + new String(body));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
