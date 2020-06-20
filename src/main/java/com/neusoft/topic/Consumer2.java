package com.neusoft.topic;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.*;
import java.io.IOException;

public class Consumer2 {

    public static void main(String[] args) {
        System.out.println("消费者2 启动");
        try {
            Connection conn = RabbitMQUtil.getConnection();
            Channel channel = conn.createChannel();
            //通道声明交换机及交换机类型
            String exchangeName = "testTopics";
            channel.exchangeDeclare("exchangeName","topic");
            //创建临时队列
            String queue = channel.queueDeclare().getQueue();
            //绑定交换机和队列，动态通配符的形式配置 routingKey
            channel.queueBind(queue, exchangeName, "user.#");

            //消费消息
            channel.basicConsume(queue,true,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("消费者2 进行消息消费 " + new String(body));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}