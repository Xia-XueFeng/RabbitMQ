package com.xxf.pub;

import com.rabbitmq.client.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author:rooten
 * @Date:2021/3/7
 * @Description:
 */
public class Recv1 {
    private final static String EXCHANGE_NAME = "exchange_fanout";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("182.92.236.141"); // localhost
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        //消费者一般不增加自动关闭
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //绑定交换机,fanout扇形，即广播类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //获取队列（排它队列）
        String queueName = channel.queueDeclare().getQueue();
        //绑定队列和交换机,fanout交换机不用指定routingkey
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        //自动确认消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
