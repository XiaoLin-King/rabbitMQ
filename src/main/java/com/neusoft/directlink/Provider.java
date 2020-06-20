package com.neusoft.directlink;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

public class Provider {

    //生产消息
    @Test
    public void testSendMessage() throws Exception {
        //获取连接对象
        Connection conn = RabbitMQUtil.getConnection();
        System.out.println("Provider  获取到的连接对象" + conn);
        //通过连接获取通道
        Channel channel = conn.createChannel();
        //通道绑定对应消息队列
        /**
         * 参数一 queue:队列名称，如果队里不存在自动创建
         * 参数二 durable:用来定义队列特性是否要持久化  true持久化队列  false 非持久化   重新启动RMQ服务队列会被删除
         * 参数三 exclusive:exclusive 是否独占队列  true 独占队列 只能被当前channel 所绑定，不能被其他连接绑定。  一般都为false 希望多个连接同用一个队列
         * 参数四 autoDelete:autoDelete 是否在消费完成后自动删除队列  true 自动删除   消费者与连接断开之后会进行删除
         * 参数五 arguments:额外附加参数
         */
        channel.queueDeclare("hello", false, false, false, null);
        //通过通道发布消息
        /**
         * 参数一 exchange：交换机名称  参数二 routingKey：队列名称  参数三 props：传递消息的额外设置，可以进行消息持久化等操作 参数四 ：消息的具体内容
         */
        String message = "hello RabbitMQ";
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("发送  message = " + message);
        //关闭通道和连接
        RabbitMQUtil.closeConnAndChanel(channel, conn);
    }
}
