package cn.person.musicspider.web.rest.controller;

import java.util.*;

import cn.person.musicspider.result.Response;
import cn.person.musicspider.result.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SingerService;
import cn.person.musicspider.service.SongService;
import cn.person.musicspider.service.cache.RedisService;

@RestController("singerResource")
@RequestMapping("/api/singer")
public class SingerResource extends BaseController {

	
	@Autowired
	private SingerService singerService;
	@Autowired
	private SongService songService;
	@Autowired
	private RedisService redisService;
	
	@RequestMapping(value="",method={RequestMethod.GET})
	public Response singerList(Pagination pagination, Model model){
		singerService.findSingerList(pagination);
		return new Response(ResponseCode.OK,pagination);
	}
	
	@RequestMapping(value="",method={RequestMethod.POST})
	public Response deleteBatchById(@RequestParam("ids")List<Long> singerIds){
		return Response.OK;
	}

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
		Set<String> set = new HashSet<>();
		set.addAll(list);
		System.out.println("set:"+set.size());
		ArrayList<String> list1 = new ArrayList<>();
		list1.addAll(set);
		redisService.remove(key);
		redisService.rpush(key,list1);
		list.clear();
		set.clear();
	}

}
