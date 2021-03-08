package com.xxf.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

/**
 * @Author:rooten
 * @Date:2021/3/7
 * @Description:
 */
public class Recv2 {
    private final static String QUEUE_NAME = "work_mq_rr";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("182.92.236.141");
        factory.setUsername("admin");
        factory.setPassword("password");
        factory.setVirtualHost("/dev");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*]  Waiting for messages. To exit press CTRL+C");
        //策略消费完一个在消费一个
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //模拟消费缓慢
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            //手工确认消息消费，不是多条确认
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        //关闭自动确认消息
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

}
