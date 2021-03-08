package com.xxf.simple;
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
    private final static String QUEUE_NAME = "hello";

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
            /**
             * 队列名称
             * 持久化配置：mq重启后还在
             * 是否独占：只能有一个消费者监听队列；当connection关闭是否删除队列，一般是false，发布订阅是独占
             * 自动删除: 当没有消费者的时候，自动删除掉，一般是false
             * 其他参数
             *
             * 队列不存在则会自动创建，如果存在则不会覆盖，所以此时的时候需要注意属性
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            /**
             * 参数说明：
             * 交换机名称：不写则是默认的交换机，那路由健需要和队列名称一样才可以被路由，
             * 路由健名称
             * 配置信息
             * 发送的消息数据：字节数组
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
            }
        }

}
