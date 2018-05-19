package cn.person.mq.consumers;

import java.util.Date;

import cn.person.musicspider.web.vo.SongVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.person.mq.enums.RoutingKey;
import cn.person.mq.service.impl.ConsumerServiceImpl;
import cn.person.musicspider.service.SongService;

@Service
public class SongConsumer {
	
	private ConsumerServiceImpl consumerService;
	@Autowired
	private SongService songService;
	
	@Autowired
	public SongConsumer(ConsumerServiceImpl consumerService){
		this.consumerService = consumerService;
		init();
	}
	
	public void init(){
		createSong();
	}
	
	public void createSong(){
		String queueName = RoutingKey.SPIDER_SONG_CREATE.getQueueName();
		String receiveKey = RoutingKey.SPIDER_SONG_CREATE.getRoutingKey();
		consumerService.receiveMsg(receiveKey, queueName, new ConsumerServiceImpl.Executer(){

			@Override
			public void execute(String message) {
				SongVo song = JSONObject.parseObject(message, SongVo.class);
				Long songId = song.getSongId();
				song.setUpdateTime(new Date());
				if(songId!=null){
					SongVo s = songService.findSongById(songId);
					if(s!=null){
						songService.updateSong(song);
					}else{
						songService.addSong(song);
					}
				}
			}

			@Override
			public void runConsumer() {
				
			}
			
		});
	}
}
