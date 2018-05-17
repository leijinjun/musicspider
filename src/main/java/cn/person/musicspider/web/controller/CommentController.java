package cn.person.musicspider.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.person.musicspider.pojo.User;
import cn.person.musicspider.result.Response;
import cn.person.musicspider.service.SongService;
import cn.person.musicspider.service.UserService;
import cn.person.musicspider.web.vo.SongVo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.encrypt.util.EncryptTools;
import cn.person.musicspider.enums.RelationType;
import cn.person.musicspider.pojo.Comment;
import cn.person.musicspider.service.CommentService;
import cn.person.musicspider.service.cache.RedisService;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController{

	@Autowired
	private RedisService redisService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private SongService songService;
	@Autowired
	private UserService userService;

	private static final String REDIS_COMMENT_KEY = "spider.comment.url.list";
	
	@RequestMapping("/all")
	@ResponseBody
	public Response comment(){
		new Thread(()->{
			String map = redisService.rpop(REDIS_COMMENT_KEY);
			LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
			ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, Integer.MAX_VALUE, TimeUnit.SECONDS, blockingDeque);
			while(map!=null){
				try {
					getComments(map,executor);
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					map = redisService.rpop(REDIS_COMMENT_KEY);
				}
			}
		}).start();
		return Response.OK;
	}

	@RequestMapping(value = "/search")
	@ResponseBody
	public Response search(@RequestParam("search") String s,@RequestParam(value = "singerName",required = false)String singerName){
		Map<String, Object> map = new HashMap<>();
		map.put("limit", 8);
		map.put("s",s);
		Map<String, String> data = EncryptTools.commentAPI(JSONObject.toJSONString(map));
		Map<String,String> headers = new HashMap<>();
		headers.put("Content-type","application/x-www-form-urlencoded");
		Connection.Response response = null;
		try {
			response = Jsoup.connect("http://music.163.com/weapi/search/suggest/web?csrf_token=").method(Method.POST).headers(headers).data(data).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(response==null){
			return Response.OK;
		}
		String body = response.body();
		Long singerId = 0L;
		JSONObject var1Obj = JSONObject.parseObject(body);
		if(var1Obj.getInteger("code").intValue()==200){
			JSONObject var2Obj = var1Obj.getJSONObject("result");
			JSONArray var3Arr = var2Obj.getJSONArray("songs");
			for (int i=0;i<var3Arr.size();i++){
				JSONObject var = var3Arr.getJSONObject(i);
				singerId = var.getLong("id");
				if(StringUtils.isBlank(singerName)){
					break;
				}
				JSONArray artists = var.getJSONArray("artists");
				if(!artists.isEmpty()){
					JSONObject var2JSONObj = artists.getJSONObject(0);
					String name = var2JSONObj.getString("name");
					if(name.contains(singerName)){
						break;
					}
				}
			}
		}
		if(singerId!=0L){
			JSONObject param = new JSONObject();
			param.put("commentURL","http://music.163.com/weapi/v1/resource/comments/R_SO_4_"+singerId);
			param.put("musicId",singerId);
			LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
			ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, Integer.MAX_VALUE, TimeUnit.SECONDS, blockingDeque);
			getComments(param.toJSONString(),executor);
		}
		return Response.OK;
	}

	private void getComments(String map,ThreadPoolExecutor executor){
		LOGGER.info("获取评论URL:{}",map);
		try{
			Map<String, Object> param = new HashMap<>();
			int pageNum=1;
			int limit = 100;
			param.put("limit", limit);
			param.put("offset", (pageNum-1)*limit);
			Boolean more = true;
			Boolean moreHot = true;
			Boolean isUpdate = true;
			JSONObject dataObj = JSONObject.parseObject(map);
			String url = dataObj.getString("commentURL");
			Long musicId = dataObj.getLong("musicId");
			while(more){
				Map<String,String> data = EncryptTools.commentAPI(JSONObject.toJSONString(param));
				Connection.Response result = Jsoup.connect(url).data(data).method(Method.POST).execute();
				String body = result.body();
				JSONObject jo = JSONObject.parseObject(body);
				if(jo.getInteger("code").equals(-460)){
					throw new RuntimeException(jo.toJSONString());
				}
				LOGGER.debug("result:{}",jo);
				Integer total = jo.getInteger("total");
				if(total<9999){//过滤冷门歌曲评论
					Thread.sleep(5000);
					break;
				}
				if(isUpdate){
					SongVo song = new SongVo();
					song.setSongId(musicId);
					song.setCommentCount(total);
					songService.updateSong(song);
				}
				more = jo.getBoolean("more")==null?false:jo.getBoolean("more");
				if(!more){
					LOGGER.info("end");
					break;
				}
				isUpdate = false;
				JSONArray comms = jo.getJSONArray("comments");
				moreHot = jo.getBoolean("moreHot")==null?false:jo.getBoolean("moreHot");
				if(moreHot){
					comms.addAll(jo.getJSONArray("hotComments"));
				}
				JSONArray topJSONArr = jo.getJSONArray("topComments");
				if(pageNum==1&&topJSONArr!=null&&!topJSONArr.isEmpty()){
					comms.addAll(topJSONArr);
				}
				if(comms!=null){
					List<Comment> commList = new ArrayList<>();
					List<User> userList = new ArrayList<>();
					for (int i = 0; i < comms.size(); i++) {
						JSONObject JSONReply = comms.getJSONObject(i);
						JSONObject userJSONObj = JSONReply.getJSONObject("user");
						if(userJSONObj!=null){
							User user = JSONObject.toJavaObject(userJSONObj, User.class);
							user.setPhotoUrl(userJSONObj.getString("avatarUrl"));
							userList.add(user);
						}
						String content = JSONReply.getString("content");
						if(StringUtils.isBlank(content)||content.length()<=2){
							LOGGER.info("回复内容小于2个字符，以丢弃:{}",content);
							continue;
						}
						Comment comment = new Comment();
						comment.setRelationId(musicId);
						comment.setCommentId(JSONReply.getLong("commentId"));
						comment.setCommentTime(new Date(JSONReply.getLong("time")));
						comment.setContent(content);
						comment.setPraiseCount(JSONReply.getInteger("likedCount"));
						comment.setIsHotComment(false);
						comment.setRelationType(RelationType.Song);
						comment.setBeReplied(JSONReply.getJSONArray("beReplied").toString());
						JSONObject userJSON = JSONReply.getJSONObject("user");
						comment.setUserPhotoURL(userJSON.getString("avatarUrl"));
						comment.setUserId(userJSON.getLong("userId"));
						comment.setNickname(userJSON.getString("nickname"));
						commList.add(comment);
					}
					if(!commList.isEmpty()){
						executor.execute(new Thread(){
							@Override
							public void run(){
								try {
									LOGGER.info("执行中：{}",commList);
									commentService.addBatchComms(commList);
									userService.addBatchUsers(userList);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
				pageNum++;
				param.put("offset", (pageNum-1)*limit);
				/*if((pageNum-1)*limit>1000&&total<20000){
					break;
				}*/
				//减缓抓取频率
				Thread.sleep(5000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}