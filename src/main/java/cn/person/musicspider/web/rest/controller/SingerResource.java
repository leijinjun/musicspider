package cn.person.musicspider.web.rest.controller;

import java.util.*;

import cn.person.musicspider.web.controller.CommentController;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.service.SongService;
import cn.person.musicspider.service.cache.RedisService;

@RestController("singerResource")
@RequestMapping("/api/singer")
public class SingerResource extends BaseController {

	@Autowired
	private SongService songService;
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/redis/comment")
	public void redisComment(){
		String key = "spider.comment.url.list";
		List<String> list = redisService.lRange(key,0L,Long.MAX_VALUE);
		Set<String> set = new HashSet<>();
		set.addAll(list);
		ArrayList<String> list1 = new ArrayList<>();
		list1.addAll(set);
		redisService.remove(key);
		redisService.rpush(key,list1);
		list.clear();
		set.clear();
	}

	@RequestMapping("/redis/singer")
	public void redisSinger(){
		String key = "spider.singer.url.list";
		List<String> list = redisService.lRange(key,0L,Long.MAX_VALUE);
		Set<String> set = new HashSet<>();
		set.addAll(list);
		ArrayList<String> list1 = new ArrayList<>();
		list1.addAll(set);
		redisService.remove(key);
		redisService.rpush(key,list1);
		list.clear();
		set.clear();
	}

	@RequestMapping("/redis/song")
	public void redisSong(){
		String key = "spider.song.url.list";
		List<String> list = redisService.lRange(key,0L,Long.MAX_VALUE);
		Set<String> commentURLLists = new HashSet<>();
		Map<String, Object> map = new HashMap<>();
		for (String str:
			 list) {
			String id = str.substring(str.indexOf("=") + 1);
			map.put("commentURL","http://music.163.com/weapi/v1/resource/comments/R_SO_4_"+id);
			map.put("musicId",id);
			commentURLLists.add(JSONObject.toJSONString(map));
			map.clear();
		}
		Set<String> set = new HashSet<>();
		set.addAll(list);
		System.out.println("set:"+set.size());
		ArrayList<String> list1 = new ArrayList<>();
		list1.addAll(set);
		redisService.remove(key);
		redisService.rpush(key,list1);
		List<String> commentList = new ArrayList<>();
		commentList.addAll(commentURLLists);
		redisService.remove(CommentController.REDIS_COMMENT_KEY);
		redisService.rpush(CommentController.REDIS_COMMENT_KEY,commentList);
		list.clear();
		set.clear();
	}

}
