package cn.person.mq.service;

import com.rabbitmq.client.Channel;

public interface RabbitMQService {
	
	//交换机名称
	public static final String MQ_EXCHANGE = "LEI2J";
	//交换机类型，这里使用topic类型的交换机
	public static final String MQ_TYPE = "topic";
	
	//消息手动确认
	public static final boolean MQ_AUTOACK = false;
	
	public Channel getChannel();
}
