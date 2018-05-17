package cn.person.musicspider.mq.consumers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.person.musicspider.mq.enums.RoutingKey;
import cn.person.musicspider.mq.service.impl.ConsumerServiceImpl;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.service.SingerService;

@Service
public class SingerConsumer {

	private ConsumerServiceImpl consumerService;
	@Autowired
	private SingerService singerService;
	
	@Autowired
	public SingerConsumer(ConsumerServiceImpl consumerService){
		this.consumerService = consumerService;
		init();
	}
	
	public void init(){
		createSinger();
		updateSinger();
	}
	
	public void createSinger(){
		String receiveKey = RoutingKey.SPIDER_SINGER_CREATE.getRoutingKey();
		String queueName = RoutingKey.SPIDER_SINGER_CREATE.getQueueName();
		consumerService.receiveMsg(receiveKey, queueName, new ConsumerServiceImpl.Executer(){

			@Override
			public void execute(String message) {
				Singer singer = JSONObject.parseObject(message, Singer.class);
				Long id = singer.getSingerId();
				singer.setUpdateTime(new Date());
				if(id!=null){
					Singer s = singerService.findSingerById(id);
					if(s!=null){
						singerService.updateSingerById(singer);
					}else{
						singerService.addSinger(singer);
					}
				}
			}

			@Override
			public void runConsumer() {
				
			}}
		);
	}
	
	public void updateSinger(){
		String receiveKey = RoutingKey.SPIDER_SINGER_UPDATE.getRoutingKey();
		String queueName = RoutingKey.SPIDER_SINGER_UPDATE.getQueueName();
		consumerService.receiveMsg(receiveKey, queueName, new ConsumerServiceImpl.Executer(){

			@Override
			public void execute(String message) {
			}

			@Override
			public void runConsumer() {
				
			}
		});
	}
}
