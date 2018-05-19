package cn.person.mq.consumers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import cn.person.mq.enums.RoutingKey;
import cn.person.mq.service.impl.ConsumerServiceImpl;
import cn.person.musicspider.pojo.Comment;
import cn.person.musicspider.service.CommentService;

@Service
public class CommentConsumer {

	private ConsumerServiceImpl consumerService;
	@Autowired
	private CommentService commentService;
	
	@Autowired
	public CommentConsumer(ConsumerServiceImpl consumerService){
		this.consumerService = consumerService;
		init();
	}
	
	public void init(){
		createComment();
	}
	
	public void createComment(){
		String receiveKey = RoutingKey.SPIDER_COMMENT_CREATE.getRoutingKey();
		String queueName = RoutingKey.SPIDER_COMMENT_CREATE.getQueueName();
		consumerService.receiveMsg(receiveKey, queueName, new ConsumerServiceImpl.Executer(){

			@Override
			public void execute(String message) {
				List<Comment> commentList = JSONArray.parseArray(message, Comment.class);
				commentService.addBatchComms(commentList);
			}

			@Override
			public void runConsumer() {
				
			}}
		);
	}
}
