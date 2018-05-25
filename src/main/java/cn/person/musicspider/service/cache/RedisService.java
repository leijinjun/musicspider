package cn.person.musicspider.service.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public interface RedisService{

	 void set(String key,String value);

	 void remove(String key);
	
	 void rpush(String key,List<String> value);

	 void rpush(String key,String value);
	
	 String lpop(String key);

	 List<String> lRange(String key,Long start,Long end);

	 void sadd(String key,String... members);

	 Long srem(String key,String... member);

	 String spop(String key);

	 Set<String> sinter(String... keys);

	default void close(Jedis jedis){
		if(jedis!=null){
			try {
				jedis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
