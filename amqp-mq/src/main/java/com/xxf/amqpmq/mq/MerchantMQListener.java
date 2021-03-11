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
@RabbitListener(queues="lock_merchant_dead_queue")
public class MerchantMQListener {
    /**
     * RabbitHandler 会自动匹配 消息类型（消息自动确认）
     * Change
     * @param msg
     * @param message
     * @throws IOException
     */
    @RabbitHandler
    public void messageCouponRecord(String msg, Message message, Channel channel) throws IOException {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("msgTag="+msgTag);
        System.out.println("message="+message.toString());
        System.out.println("监听到消息：消息内容:"+message.getBody());
        System.out.println("msg"+msg);
        // 做复杂业务逻辑 TODO
        channel.basicAck(msgTag,false);
    }

}
