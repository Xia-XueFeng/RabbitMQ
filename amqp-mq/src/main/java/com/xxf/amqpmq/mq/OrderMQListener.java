package com.xxf.amqpmq.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author:rooten
 * @Date:2021/3/8
 * @Description:
 */
@Component
//@RabbitListener(queues="order_queue")
public class OrderMQListener {
    /**
     * RabbitHandler 会自动匹配 消息类型（消息自动确认）
     * Change
     * @param msg
     * @param message
     * @throws IOException
     */
    //@RabbitHandler
    public void releaseCouponRecord(String msg, Message message, Channel channel) throws IOException {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("msgTag="+msgTag);
        System.out.println("message="+message.toString());
        System.out.println("监听到消息：消息内容:"+message.getBody());
        System.out.println("msg"+msg);
        //成功确认，使用此回执方法后，消息会被 rabbitmq broker 删除
        //channel.basicAck(msgTag,false);
        channel.basicNack(msgTag,false,true);
    }

}
