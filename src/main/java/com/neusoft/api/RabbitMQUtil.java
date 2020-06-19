package com.neusoft.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtil {
    private static ConnectionFactory connectionFactory;
    //工厂只创建一次
    static {
        //工厂是重量级资源
        //创建连接mq的连接工厂对象
        connectionFactory = new ConnectionFactory();
        //设置连接RabbitMQ 主机
        connectionFactory.setHost("192.168.137.20");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置具体连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        //设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
    }

    //获取连接
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭通道 连接 的方法
    public static void closeConnAndChanel(Channel channel, Connection connection) {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}