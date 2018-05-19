package cn.person.mq.consumers;

import org.springframework.beans.factory.annotation.Autowired;

import cn.person.mq.service.impl.ConsumerServiceImpl;

public class ExceptionConsumer {

	private ConsumerServiceImpl consumerService;
	
	@Autowired
	public ExceptionConsumer(ConsumerServiceImpl consumerService){
		this.consumerService = consumerService;
		init();
	}
	
	public void init(){
		createException();
	}
	
	public void createException(){
		
	}
}
