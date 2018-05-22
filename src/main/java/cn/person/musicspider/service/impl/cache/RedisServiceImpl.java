package cn.person.musicspider.service.impl.cache;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.ibatis.cache.NullCacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.person.musicspider.service.cache.RedisService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.swing.*;

@Service
public class RedisServiceImpl implements RedisService{

	@Autowired
	private JedisPool jedisPool;
	
	public RedisServiceImpl(){
	}
	
	@Override
	public void set(String key,String value){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			//返回资源
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(jedis);
		}
	}

	public void remove(String key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(jedis);
		}
	}

	@Override
	public void rpush(String key, List<String> value) {
		if(value==null||value.isEmpty()){
			return;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.rpush(key, value.toArray(new String[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(jedis);
		}
	}

	@Override
	public String rpop(String key) {
		Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				String value = jedis.rpop(key);
				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				close(jedis);
			}
		return null;
	}

	@Override
	public List<String> lRange(String key,Long start,Long end){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> list = jedis.lrange(key, start, end);
			return  list;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(jedis);
		}
		return  null;
	}

	@Override
	public void rpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.rpush(key,value);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			close(jedis);
		}
	}

	@Override
	public void sadd(String key, String... members) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.sadd(key,members);
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			close(jedis);
		}
	}

	@Override
	public Long srem(String key, String... member) {
		Jedis jedis = null;
		Long rLen = 0L;
		try {
			jedis = jedisPool.getResource();
			rLen = jedis.srem(key,member);
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			close(jedis);
		}
		return rLen;
	}

	@Override
	public String spop(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.spop(key);
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			close(jedis);
		}
		return null;
	}

	@Override
	public Set<String> sinter(String... keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.sinter(keys);
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			close(jedis);
		}
		return null;
	}
}
