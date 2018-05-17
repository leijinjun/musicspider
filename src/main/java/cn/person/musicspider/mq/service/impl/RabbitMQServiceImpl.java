package cn.person.musicspider.mq.service.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import cn.person.musicspider.mq.service.RabbitMQService;

public class RabbitMQServiceImpl implements RabbitMQService{

	private static ConnectionFactory factory;
	private static Connection connection;
	protected static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQServiceImpl.class);
	public RabbitMQServiceImpl(String host,Integer port,String username,String password) throws IOException, TimeoutException{
		init(host,port,username,password);
	}
	
	protected static void init(String host,Integer port,String username,String password) throws IOException, TimeoutException {
		factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setVirtualHost("/");
		//自动重连
		factory.setAutomaticRecoveryEnabled(true);
		connection = factory.newConnection();
	}
	
	public Channel getChannel(){
		Channel channel = null;
		try {
			if(connection!=null&&connection.isOpen()){
					channel = connection.createChannel();
					channel.exchangeDeclare(MQ_EXCHANGE, MQ_TYPE, true);
					return channel;
			}
		} catch (IOException e) {
			LOGGER.error("get Channel error :{}",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel;
	}
}
