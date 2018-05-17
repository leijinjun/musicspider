package cn.person.musicspider.mq.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import cn.person.musicspider.mq.service.RabbitMQService;

@Service
public class ProducerServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);
	@Autowired
	private RabbitMQService mqService;
	
	public void sendMsg(String sendKey,String msg){
		Channel channel = mqService.getChannel();
		try {
			channel.basicPublish(RabbitMQService.MQ_EXCHANGE, sendKey, null, msg.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			LOGGER.error("rabbitmq send message error key:{},msg:{}",sendKey,msg);
			e.printStackTrace();
		}finally {
			try {
				if(channel!=null){
					channel.close();
				}
			} catch (Exception e) {
				channel=null;
				e.printStackTrace();
			}
			channel=null;
		}
	}
	
}
