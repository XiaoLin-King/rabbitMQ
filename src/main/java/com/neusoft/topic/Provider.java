package com.neusoft.topic;

import com.neusoft.api.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式  动态路由绑定路由Key  可以使用通配符，解决路由模式routing 中绑定路由Key 冗余代码的问题！！
 *  * 号匹配一个单词
 *  # 匹配多个单词
 *  例如：
 *      audit.* 只能匹配 audit.irs
 *      audit.# 匹配 audit.irs.corporate 或者 audit.irs 等
 *      *.*.*.
 *      #.。。。。
 */
public class Provider {
    public static void main(String[] args) {
        try {
            String exchangeName = "testTopics";
            Connection conn = RabbitMQUtil.getConnection();
            Channel channel = conn.createChannel();
            //通道绑定交换机及交换机类型
            channel.exchangeDeclare(exchangeName,"topic");
            //发布消息
            String routingKey = "user.save.delete";
            String message = "这是Topic 动态路由模式，routingKey:["+routingKey+"]的消息";
            channel.basicPublish(exchangeName,routingKey,null,message.getBytes());
            //关闭资源
            RabbitMQUtil.closeConnAndChanel(channel,conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}