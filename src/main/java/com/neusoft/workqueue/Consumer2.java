package com.neusoft.workqueue;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.*;
import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws Exception {
        //获取连接对象
        Connection conn = RabbitMQUtil.getConnection();
        System.out.println("Consumer获取到的连接对象" + conn);
        //通过连接获取通道
        final Channel channel = conn.createChannel();
        /* 通道绑定对应消息队列 */
        //每次只能消费一个消息
        channel.basicQos(1);
        /**
         * 参数一:队列名称，如果队里不存在自动创建
         * 参数二:用来定义队列特性是否要持久化  true持久化队列  false 非持久化
         * 参数三:exclusive 是否独占队列  true 独占队列
         * 参数四:autoDelete 是否在消费完成后自动删除队列  true 自动删除
         * 参数五:额外附加参数
         */
        channel.queueDeclare("emp", true, false, false, null);
        //消费消息

        //定义队列的消费者
//        DefaultConsumer consumer = new DefaultConsumer(channel);

        /**
         * 参数一：消费哪个队列的消息  队列名称
         * 参数二：开始消息的自动确认机制
         * 参数三：消费时回调的接口
         */


        channel.basicConsume("emp", false, new DefaultConsumer(channel) {
            /**
             *
             * @param consumerTag 标签
             * @param envelope  信封
             * @param properties 属性
             * @param body     消息队列中取出的消息
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2 消费 " + new String(body));
                //参数一:确认队列那个具体消息 参数二：是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(),false);

            }
        });
        //不关的话会一直监听消息
        //channel.close();
        //conn.close();
    }
}