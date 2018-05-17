package cn.person.musicspider.service.cache;

import redis.clients.jedis.Jedis;

import java.util.List;

public interface RedisService{

	public void set(String key,String value);

	public void remove(String key);
	
	public void rpush(String key,List<String> value);
	
	public String rpop(String key);

	public List<String> lRange(String key,Long start,Long end);

	public void close(Jedis jedis);
}
