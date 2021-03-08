package com.xxf.topic;


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
    private final static String EXCHANGE_NAME = "exchange_topic";

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
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            String error = "我是错误日志";
            String info = "我是info日志";
            String debug = "我是debug日志";
            channel.basicPublish(EXCHANGE_NAME, "order.log.error", null, error.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "order.log.info", null, info.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "product.log.debug", null, debug.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功");

        }
    }

}
