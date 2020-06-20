package com.neusoft.fanout;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 广播形式发布消息
 */

public class Provider {
    public static void main(String[] args) {
        System.out.println("生产者 启动");
        try {
            Connection conn = RabbitMQUtil.getConnection();
            Channel channel = conn.createChannel();

            //将通道声明指定的交换机
            /**
             * 参数一：声明交换机名称  具体自己定义 看它需要实现什么业务 参数二：交换机类型 fanout 广播类型
             * 不存在的时候回去创建这个交换机
             */
            channel.exchangeDeclare("logs", "fanout");
            //发送消息
            String message = "fanout type message  test";
            /**
             * 参数1 exchange：交换机名字  参数2 routingKey：路由key 参数3 props：消息持久化特性  参数4 message：消息
             */
            channel.basicPublish("logs", "", null, message.getBytes());
            //释放资源
            RabbitMQUtil.closeConnAndChanel(channel, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
