package cn.person.mq.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import cn.person.mq.service.RabbitMQService;

@Service
public class ConsumerServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerServiceImpl.class);
	
	@Autowired
	private RabbitMQService mqService;
	@Autowired
	private ProducerServiceImpl mqProducerService;
	
	public void receiveMsg(String receiveKey,String queueName,Executer executer){
		Channel channel = mqService.getChannel();
		try {
			//声明队列，参数:队列名称,消息持久化,对于多个connection都可以连接,队列不自动删除，属性参数
			channel.queueDeclare(queueName, true, false, false, null);
			//绑定路由到队列
			channel.queueBind(queueName, RabbitMQService.MQ_EXCHANGE, receiveKey);
			Consumer consumer = new DefaultConsumer(channel){

				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					long tag = 0L;
					String msg = "";
					try {
						msg = new String(body, "utf-8");
						LOGGER.info("receive message success, key:{},message:{}",receiveKey,msg);
						executer.execute(msg);
						tag = envelope.getDeliveryTag();
						//消息确认
						channel.basicAck(tag, RabbitMQService.MQ_AUTOACK);
					}catch (Exception e) {
						e.printStackTrace();
						LOGGER.error("execute message error, key:{},message:{}",receiveKey,msg);
						channel.basicCancel(consumerTag);
						//mqProducerService.sendMsg(RoutingKey.EXCEPTION.getSendKey(), msg);
						executer.runConsumer();
					}
				}
				
			};
			//绑定消费者
			channel.basicConsume(queueName, RabbitMQService.MQ_AUTOACK, consumer);
			LOGGER.info("create queue {},key:{}",queueName,receiveKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static interface Executer{
		
		public void execute(String message);
		
		public void runConsumer();
	}
}
