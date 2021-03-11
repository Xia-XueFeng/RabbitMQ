package com.xxf.amqpmq.controller;

import com.sun.javafx.collections.MappingChange;
import com.xxf.amqpmq.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:rooten
 * @Date:2021/3/10
 * @Description:
 */
@RestController
@RequestMapping("/api/admin/merchant")
public class MerchantAccountController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("check")
    public Object check() {
        // 修改数据库得商家账号 TODO
        rabbitTemplate.convertAndSend(RabbitMQConfig.NEW_MERCHANT_EXCHANGE,RabbitMQConfig.NEW_MERCHANT_ROUTIING_KEY,"商家账号审核通过");
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","账号审核通过，请10秒内上传1个商品");
        return map;
    }
}
