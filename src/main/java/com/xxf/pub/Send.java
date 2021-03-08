package com.xxf.pub;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @Author:rooten
 * @Date:2021/3/6
 * @Description:
 */
public class Send {
    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("182.92.236.141"); // localhost
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        //JDK7语法 或自动关闭 connnection和channel

        try (// 创建连接
             Connection connection = factory.newConnection();
             // 创建信道
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String message = "Hello World pub !";
            channel.basicPublish(EXCHANGE_NAME, "", null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");

        }
    }

}
