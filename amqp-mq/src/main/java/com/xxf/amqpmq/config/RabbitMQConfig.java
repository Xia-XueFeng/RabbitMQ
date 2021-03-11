package com.xxf.amqpmq.config;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;
/**
 * @Author:rooten 普通交换机---普通队列--（绑定）死信交换机---死信队列
 * @Date:2021/3/8
 * @Description:
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 死信队列
     */
    public static final String LOCK_MERCHANT_DEAD_QUEUE = "lock_merchant_dead_queue";
    /**
     * 死信交换机
     */
    public static final String LOCK_MERCHANT_DEAD_EXCHANGE = "lock_merchant_dead_exchange";
    /**
     * 进入死信队列的路由key
     */
    public static final String LOCK_MERCHANT_ROUTING_KEY = "lock_merchant_routing_key";
    /**
     * 创建死信交换机
     * @return
     */
    @Bean
    public Exchange lockMerchantDeadExchange(){
        return new TopicExchange(LOCK_MERCHANT_DEAD_EXCHANGE,true,false);
    }
    /**
     * 创建死信队列
     * @return
     */
    @Bean
    public Queue lockMerchantDeadQueue(){
        return QueueBuilder.durable(LOCK_MERCHANT_DEAD_QUEUE).build();
    }
    /**
     * 绑定死信交换机和死信队列
     * @return
     */
    @Bean
    public Binding lockMerchantBinding(){
        return new Binding(LOCK_MERCHANT_DEAD_QUEUE,Binding.DestinationType.QUEUE,
                LOCK_MERCHANT_DEAD_EXCHANGE,LOCK_MERCHANT_ROUTING_KEY,null);
    }
    /**
     * 普通队列，绑定的个死信交换机
     */
    public static final String NEW_MERCHANT_QUEUE = "new_merchant_queue";
    /**
     * 普通的topic交换机
     */
    public static final String NEW_MERCHANT_EXCHANGE = "new_merchant_exchange";
    /**
     * 路由key
     */
    public static final String NEW_MERCHANT_ROUTIING_KEY = "new_merchant_routing_key";
    /**
     * 创建普通交换机
     * @return
     */
    @Bean
    public Exchange newMerchantExchange(){
        return new TopicExchange(NEW_MERCHANT_EXCHANGE,true,false);
    }
    /**
     * 创建普通队列
     * @return
     */
    @Bean
    public Queue newMerchantQueue(){
        Map<String,Object> args = new HashMap<>(3);
        //消息过期后，进入到死信交换机
        args.put("x-dead-letter-exchange",LOCK_MERCHANT_DEAD_EXCHANGE);
        //消息过期后，进入到死信交换机的路由key
        args.put("x-dead-letter-routing-key",LOCK_MERCHANT_ROUTING_KEY);
        //过期时间，单位毫秒
        args.put("x-message-ttl",10000);
        return QueueBuilder.durable(NEW_MERCHANT_QUEUE).withArguments(args).build();
    }
    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean
    public Binding newMerchantBinding(){
        return new Binding(NEW_MERCHANT_QUEUE,Binding.DestinationType.QUEUE,
                NEW_MERCHANT_EXCHANGE,NEW_MERCHANT_ROUTIING_KEY,null);
    }
    /*//延迟队列消费实战
    public static final String EXCHANGE_NAME = "order_exchange";
    public static final String QUEUE_NAME = "order_queue";
   // 交换机


    @Bean
    public Exchange orderExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
        //return new TopicExchange(EXCHANGE_NAME, true, false);
    }
    //队列

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
        //return new Queue(QUEUE_NAME, true, false, false, null);
    }
    // 交换机和队列绑定关系

    @Bean
    public Binding orderBinding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("order.#").noargs();
        //return new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, EXCHANGE_NAME, "order.#", null);
    }*/
}
