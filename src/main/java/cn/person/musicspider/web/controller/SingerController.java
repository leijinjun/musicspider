package cn.person.musicspider.web.controller;

import java.io.IOException;
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

import cn.person.musicspider.result.Response;
import cn.person.musicspider.web.vo.SongVo;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.pojo.Singer;
import cn.person.musicspider.result.Pagination;
import cn.person.musicspider.service.SingerService;
import cn.person.musicspider.service.SongService;
import cn.person.musicspider.service.cache.RedisService;

@Controller
@RequestMapping("/singer")
public class SingerController extends BaseController {

	
	@Autowired
	private SingerService singerService;
	@Autowired
	private SongService songService;
	@Autowired
	private RedisService redisService;
	LinkedBlockingDeque<Runnable> blockingDeque = new LinkedBlockingDeque<>();
	ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, Integer.MAX_VALUE, TimeUnit.SECONDS, blockingDeque);
	
	/**获取歌手信息及其歌手主页url
	 * @param url:http://music.163.com/discover/artist
	 * @return
	 */
	@RequestMapping("/cat")
	@ResponseBody
	public Response singerAll(String url){
		if(url==null){
			return Response.OK;
		}
		try {
			Connection.Response result = Jsoup.connect(url).method(Method.GET).execute();
			List<String> urlList = new LinkedList<>();
			Elements els = result.parse().body().select("#singer-cat-nav").select("div[class='blk']");
			Iterator<Element> it = els.iterator();
			while (it.hasNext()) {
				Element next = it.next();
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
						List<String> childUrlList = new LinkedList<String>();
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
							Set<String> singerurlLists = new HashSet<String>();
							while(it2.hasNext()){
								Element next2 = it2.next();
								Elements img = next2.getElementsByTag("img");
								Map<String, Object> obj = new HashMap<String, Object>();
								if(img!=null&&img.size()>0){
									String src = img.get(0).attr("src");
									obj.put("photoURL", src);
								}else{
									obj.put("photoURL", "");
								}
								obj.put("descipt", "");
								Elements el = next2.getElementsByTag("a");
								Element tempEl = new Element("a");
								if(el!=null&&el.size()>1){
									tempEl = el.get(1);
									
								}else{
									tempEl = el.get(0);
								}
								obj.put("singerName", tempEl.text());
								String href = tempEl.attr("href");
								obj.put("singerId", href.substring(href.indexOf("id=")+3));
								obj.put("isHot", n1.contains("initial=-1")?0:1);
							    String msg = JSONObject.toJSONString(obj);
							    Singer singer = JSONObject.parseObject(msg, Singer.class);
//								producerService.sendMsg(RoutingKey.SPIDER_SINGER_CREATE.getSendKey(), msg);
							    Thread t = new Thread(){

									@Override
									public void run() {
										try {
											singerService.addSinger(singer);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
							    	
							    };
							    executor.execute(t);
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
		HashSet<String> set = new HashSet<>();
		String value = redisService.rpop("spider-singer-url-list");
			while(value!=null){
				LOGGER.info("获取歌曲URL:{}",value);
			try {
			 if(!set.contains(value)){
				Connection.Response result = Jsoup.connect(value).method(Method.GET).execute();
				//更新歌曲信息
				Element body = null;
					body = result.parse().body();
					Elements els = body.select("#song-list-pre-cache");
					if (els != null && els.size() > 0) {
						Elements textarea = els.get(0).getElementsByTag("textarea");
						if (textarea != null && textarea.size() > 0) {
							String text = textarea.get(0).text();
							JSONArray array = JSONArray.parseArray(text);
							if (array != null && array.size() > 0) {
								Set<String> songURLLists = new HashSet<String>();
								Set<String> commentURLLists = new HashSet<String>();
								for (int i = 0; i < array.size(); i++) {
									JSONObject itemJSON = array.getJSONObject(i);
									SongVo song = new SongVo();
									song.setSongId(itemJSON.getLong("id"));
									song.setSongName(itemJSON.getString("name"));
									song.setDuration(itemJSON.getInteger("duration"));
									song.setScore(itemJSON.getInteger("score"));
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("commentURL", "http://music.163.com/weapi/v1/resource/comments/"
											+ itemJSON.getString("commentThreadId"));
									map.put("musicId", song.getSongId());
									songURLLists.add("http://music.163.com/song?id=" + itemJSON.getLong("id"));
									commentURLLists.add(JSONObject.toJSONString(map));
//									String msg = JSONObject.toJSONString(song);
//									producerService.sendMsg(RoutingKey.SPIDER_SONG_CREATE.getSendKey(), msg);
									Thread t = new Thread(){
										@Override
										public void run() {
											try {
												songService.addSong(song);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										
									};
									executor.execute(t);
									redisService.rpush("spider.song.url.list", new ArrayList<String>(songURLLists));
									redisService.rpush("spider.comment.url.list", new ArrayList<String>(commentURLLists));
								}
							}
						}
					}
			 }
			}catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				set.add(value);
				value = redisService.rpop("spider-singer-url-list");
			}
			}
			set.clear();
		return Response.OK;
	}
	
	@RequestMapping(value="/list",method={RequestMethod.GET})
	public String singerList(Integer limit,Integer pageNum,Model model){
		Pagination<Singer> pagination = new Pagination<Singer>();
		pagination.setLimit(limit);
		pagination.setPageNum(pageNum);
		singerService.findSingerList(pagination);
		model.addAttribute("pageVo", pagination);
		return "singer-list";
	}

}