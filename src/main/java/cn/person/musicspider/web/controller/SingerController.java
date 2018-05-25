package cn.person.musicspider.web.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.person.musicspider.encrypt.util.EncryptTools;
import cn.person.musicspider.enums.AuthType;
import cn.person.musicspider.enums.Gender;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.pojo.User;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.result.Response;
import cn.person.musicspider.service.SingerService;
import cn.person.musicspider.service.UserService;
import cn.person.musicspider.web.vo.SongVo;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.service.SongService;
import cn.person.musicspider.service.cache.RedisService;

@Controller
@RequestMapping("/singer")
public class SingerController extends BaseController {

	
	@Autowired
	private UserService userService;
	@Autowired
	private SongService songService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SingerService singerService;
	LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
	ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, Integer.MAX_VALUE, TimeUnit.SECONDS, blockingDeque);
	
	/**init,获取所有歌手信息及其歌手主页url
	 *
	 * @return
	 */
	@RequestMapping("/cat")
	@ResponseBody
	public Response singerAll(@RequestParam("limit") Integer limit){
		String url = "http://music.163.com/discover/artist";
		try {
			Connection.Response result = Jsoup.connect(url).method(Method.GET).execute();
			List<String> urlList = new LinkedList<>();
			Elements els = result.parse().body().select("#singer-cat-nav").select("div[class='blk']");
			if(limit!=null){
				Element element = els.get(limit);
				els.clear();
				els.add(element);
			}
			Iterator<Element> it = els.iterator();
			while (it.hasNext()) {
				Element next = it.next();
				if("其他".equals(next.select("h2[class='tit']").get(0).text().trim())){
					continue;
				}
				Elements elsTag = next.getElementsByTag("li");
				Iterator<Element> itTag = elsTag.iterator();
				while(itTag.hasNext()){
					Element nx = itTag.next();
					String attr = nx.child(0).attr("href");
					urlList.add("http://music.163.com"+attr);
				}
			}
			Iterator<String> iterator = urlList.iterator();
			LOGGER.info("歌手地区分类url :{}", urlList);
			while (iterator.hasNext()) {
				String singeruUrl = iterator.next();
				Connection.Response response = Jsoup.connect(singeruUrl).method(Method.GET).execute();
				Elements els2 = null;
					try {
						els2 = response.parse().body().select("#initial-selector").select("li");
						Iterator<Element> iterator2 = els2.iterator();
						List<String> childUrlList = new LinkedList<>();
						while (iterator2.hasNext()) {
							Element next2 = iterator2.next();
							Elements a = next2.getElementsByTag("a");
							String attr = a.attr("href");
							childUrlList.add("http://music.163.com"+attr);
						}
						Iterator<String> iterator3 = childUrlList.iterator();
						while(iterator3.hasNext()){
							String n1 = iterator3.next();
							Connection.Response res = Jsoup.connect(n1).method(Method.GET).execute();
							Elements els3 = res.parse().body().select("#m-artist-box").select("li");
							Iterator<Element> it2 = els3.iterator();
							Set<String> singerurlLists = new HashSet<>();
							while(it2.hasNext()){
								Element next2 = it2.next();
								Elements img = next2.getElementsByTag("img");
								Map<String, Object> obj = new HashMap<>();
								if(img!=null&&img.size()>0){
									String src = img.get(0).attr("src");
									obj.put("photoUrl", src);
								}else{
									obj.put("photoUrl", "");
								}
								obj.put("descipt", "");
								Elements el = next2.getElementsByTag("a");
								Element tempEl = new Element("a");
								if(el!=null&&el.size()>1){
									tempEl = el.get(1);
								}else{
									tempEl = el.get(0);
								}
								obj.put("name", tempEl.text());
								String href = tempEl.attr("href");
								obj.put("singerId", href.substring(href.indexOf("id=")+3));
								String msg = JSONObject.toJSONString(obj);
								Singer singer = JSONObject.parseObject(msg, Singer.class);
							    executor.execute(()->{
									try {
										singerService.addSinger(singer);
									} catch (Exception e) {
										e.printStackTrace();
									}
								});
								Object id = obj.get("singerId");
								singerurlLists.add("http://music.163.com/artist?id="+id);
							}
							redisService.rpush("spider-singer-url-list", new ArrayList<>(singerurlLists));
						}
					} catch (IOException e) {
						e.printStackTrace();
						LOGGER.debug("歌手列表为---->{}", els2);
					}catch (Exception e) {
						e.printStackTrace();
					}
//				new Thread(new SingerThread(url)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			LOGGER.debug("获取歌手信息出错", e.getMessage());
			e.printStackTrace();
		}
		return Response.OK;
	}
	
	/**获取歌手热门单曲
	 * @return
	 */
	@RequestMapping(value="/hotSongs")
	@ResponseBody
	public Response getHotSongs(){
		String value = redisService.lpop("spider-singer-url-list");
			while(value!=null){
				LOGGER.info("获取歌曲URL:{}",value);
				Long singerId = Long.parseLong(value.substring(value.indexOf("=")+1));
			try {
				Connection.Response result = Jsoup.connect(value).method(Method.GET).execute();
				//更新歌曲信息
				Element body = result.parse().body();
					Elements els = body.select("#song-list-pre-cache");
					if (els != null && els.size() > 0) {
						Elements textarea = els.get(0).getElementsByTag("textarea");
						if (textarea != null && textarea.size() > 0) {
							String text = textarea.get(0).text();
							JSONArray array = JSONArray.parseArray(text);
							if (array != null && array.size() > 0) {
								Set<String> songURLLists = new HashSet<>();
								Set<String> commentURLLists = new HashSet<>();
								for (int i = 0; i < array.size(); i++) {
									JSONObject itemJSON = array.getJSONObject(i);
									SongVo song = new SongVo();
									song.setSongId(itemJSON.getLong("id"));
									song.setSongName(itemJSON.getString("name"));
									song.setDuration(itemJSON.getInteger("duration"));
									song.setScore(itemJSON.getInteger("score"));
									song.setSingerId(singerId);
									Map<String, Object> map = new HashMap<>();
									map.put("commentURL", "http://music.163.com/weapi/v1/resource/comments/"
											+ itemJSON.getString("commentThreadId"));
									map.put("musicId", song.getSongId());
									songURLLists.add("http://music.163.com/song?id=" + itemJSON.getLong("id"));
									commentURLLists.add(JSONObject.toJSONString(map));
//									String msg = JSONObject.toJSONString(song);
//									producerService.sendMsg(RoutingKey.SPIDER_SONG_CREATE.getSendKey(), msg);
									executor.execute(()->{
										try {
											songService.addSong(song);
										} catch (Exception e) {
											e.printStackTrace();
										}
									});
								}
								redisService.rpush("spider.song.url.list", new ArrayList<>(songURLLists));
								redisService.rpush("spider.comment.url.list", new ArrayList<>(commentURLLists));
							}
						}
					}
			}catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				value = redisService.lpop("spider-singer-url-list");
				try {
					Thread.sleep(3000);
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}
			}
		return Response.OK;
	}

	@RequestMapping(value = "/songs")
	@ResponseBody
	public Response updateSong(){
		Pagination pagination = new Pagination();
		int pageNum = 1;
		int limit = 100;
		pagination.setPageNum(pageNum);
		pagination.setLimit(limit);
		songService.findSongList(pagination);
		List<SongVo> items = pagination.getItems();
		while (!items.isEmpty()){
			for (SongVo songVo:
				 items) {
				Long songId = songVo.getSongId();
				try {
					Connection.Response response = Jsoup.connect("http://music.163.com/song?id=" + songId).method(Method.GET).execute();
					Element body = response.parse().body();
					Elements img = body.select("div[class*='u-cover'][class*='u-cover-6']").select("img");
					String pic_url = img.attr("data-src");
					String href = body.select("div[class='cnt']").select("a[class='s-fc7']").get(0).attr("href");
					Long singerId = Long.parseLong(href.substring(href.indexOf("=")+1));
					songVo.setSingerId(singerId);
					songVo.setPicUrl(pic_url);
					Map<String,Object> map = new HashMap<>();
					map.put("id",554241075);
					map.put("lv",-1);
					Map<String, String> params = EncryptTools.commentAPI(JSONObject.toJSONString(map));
					Connection.Response lyResponse = Jsoup.connect("http://music.163.com/weapi/song/lyric?csrf_token=").method(Method.POST).data(params).execute();
					JSONObject lyricObj = JSONObject.parseObject(lyResponse.body());
					String lrc = lyricObj.getJSONObject("lrc").getString("lyric");
					songVo.setLyric(lrc);
					songService.updateSong(songVo);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			pageNum++;
			pagination.setPageNum(pageNum);
			songService.findSongList(pagination);
			items = pagination.getItems();
		}
		return Response.OK;
	}

	@RequestMapping("/singers")
	@ResponseBody
	public Response updateSingers(){
		Pagination pagination = new Pagination();
		int pageNum = 1;
		int limit = 100;
		pagination.setLimit(limit);
		pagination.setPageNum(pageNum);
		singerService.findSingerList(pagination);
		List<Singer> items = pagination.getItems();
		while (!items.isEmpty()){
			for (Singer singer:
				 items) {
				Long singerId = singer.getSingerId();
				String singerUrl = "http://music.163.com/artist?id="+singerId;
				try {
					Connection.Response sRes = Jsoup.connect(singerUrl).method(Method.GET).execute();
					Element body = sRes.parse().body();
					String photoUrl = body.select("div[class='btm']").next("img").attr("src");
					singer.setPhotoUrl(photoUrl);
					Connection.Response descRes = Jsoup.connect("http://music.163.com/artist/desc?id=" + singerId).method(Method.GET).execute();
					String desc = descRes.parse().body().select("div[class='n-artdesc']").select("p").get(0).text();
					singer.setDescipt(desc);
					singerService.updateSinger(singer);
					Element home = body.getElementById("artist-home");
					if(home!=null){
						String href = home.attr("href");
						Long userId = Long.parseLong(href.substring(href.indexOf("=") + 1));
						User existUser = userService.getUserById(userId);
						User user = getUser(userId);
						if(existUser!=null){
							userService.updateUser(user);
						}else {
							userService.addUser(user);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			pageNum++;
			pagination.setPageNum(pageNum);
			singerService.findSingerList(pagination);
			items = pagination.getItems();
		}
		return Response.OK;
	}

	@RequestMapping(value = "users")
	@ResponseBody
	public Response updateUsers(){
		Pagination pagination = new Pagination();
		int pageNum = 1;
		int limit = 100;
		pagination.setPageNum(pageNum);
		pagination.setLimit(limit);
		userService.findUserList(pagination);
		List<User> items = pagination.getItems();
		while (!items.isEmpty()){
			for (User user:
					items) {
				Long userId = user.getUserId();
				userService.updateUser(getUser(userId));
			}
			pageNum++;
			pagination.setPageNum(pageNum);
			userService.findUserList(pagination);
			items = pagination.getItems();
		}
		return Response.OK;
	}

	private static User getUser(Long userId){
		User user = new User();
		user.setUserId(userId);
		try {
			Connection.Response response = Jsoup.connect("http://music.163.com/user/home?id=" + userId).method(Method.GET).execute();
			Element body = response.parse().body();
			Element headBody = body.getElementById("head-box");
			//用户昵称
			String nickname = headBody.getElementById("j-name-wrap").select("span[class*='tit']").text();
			user.setNickname(nickname);
			//用户等级
			String level = headBody.getElementById("j-name-wrap").select("span[class*='lev']").text();
			user.setLevel(Integer.parseInt(level));
			//用户性别
			Elements genderEle = headBody.getElementById("j-name-wrap").select("i[class*='icn u-icn']");
			if(genderEle.attr("class").contains("u-icn-02")){
				user.setGender(Gender.FEMALE);
			}else if(genderEle.attr("class").contains("u-icn-01")){
				user.setGender(Gender.MALE);
			}else {
				user.setGender(Gender.UNKNOWN);
			}
			//用户认证状态
			Elements authEles = headBody.select("p[class*='djp']");
			if(authEles!=null&&!authEles.isEmpty()){
				Elements authEle = authEles.select("i[class*='tag']");
				if(authEle.attr("class").contains("u-icn2-pfv")){
					user.setAuthType(AuthType.AUTH_V);
				}else if(authEle.attr("class").contains("u-icn2-pfyyr")){
					user.setAuthType(AuthType.AUTH_MUSICIAN);
				}else if (authEle.attr("class").contains("u-icn2-pfdr")){
					user.setAuthType(AuthType.AUTH_PLAYERS);
				}
				else {
					user.setAuthType(AuthType.AUTH_NO);
				}
			}else{
				user.setAuthType(AuthType.AUTH_NO);
			}
			//关注
			String attentionCount = headBody.getElementById("follow_count").text();
			user.setAttentionCount(Integer.parseInt(attentionCount));
			//粉丝
			String fanCount = headBody.getElementById("fan_count").text();
			user.setFansCount(Integer.parseInt(fanCount));
			//用户图片
			String photoUrl = headBody.getElementById("ava").getElementsByTag("img").attr("src");
			user.setPhotoUrl(photoUrl);
			//个人介绍
			Elements infoEles = headBody.select("div[class='inf s-fc3 f-brk']");
			if(infoEles!=null&&!infoEles.isEmpty()){
				user.setTitle(infoEles.text());
			}
			Elements infoSeEles = headBody.select("div[class='inf s-fc3']");
			if(infoSeEles!=null&&!infoSeEles.isEmpty()){
				for (Element e:
						infoSeEles) {
					Elements spanEles = e.getElementsByTag("span");
					if(spanEles!=null&&!spanEles.isEmpty()){
						for (Element el:
								spanEles) {
							if(el.hasAttr("data-age")) {
								user.setBirthDay(new Date(Long.parseLong(el.attr("data-age"))));
								continue;
							}
							if(!el.hasAttr("class")&&el.text().contains("所在地区")){
								user.setArea(el.text().replace("所在地区：",""));
								continue;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

}
