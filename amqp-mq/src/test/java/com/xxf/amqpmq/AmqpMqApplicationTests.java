package com.xxf.amqpmq;

import com.xxf.amqpmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AmqpMqApplicationTests {

	/*@Test
	void contextLoads() {
	}

	@Autowired
	private RabbitTemplate template;
	@Test
	void send() {
		template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"order.new","新订单来啦666");
	}

	@Test
	void testConfirmCallback() {
		template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
			*//**
			 *
			 * @param correlationData 配置
			 * @param ack 交换机是否收到消息，true是成功，false是失败
			 * @param cause 失败的原因
			 *//*
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				System.out.println("confirm=====>");
				System.out.println("confirm==== ack="+ack);
				System.out.println("confirm==== cause="+cause);
				//根据ACK状态做对应的消息更新操作 TODO
			}
		});
		template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"order.new","新订单来啦1");
	}

	@Test
	void testReturnCallBack() {
		template.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
			@Override
			public void returnedMessage(ReturnedMessage returnedMessage) {
				int replyCode = returnedMessage.getReplyCode();
				System.out.println("code="+replyCode);
				System.out.println("returned="+returnedMessage.toString());
			}
		});
		template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"order.new","手机订单");
	}*/
}
