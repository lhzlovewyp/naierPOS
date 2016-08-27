package com.joker.core.interceptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/** 
 * TODO spring cache redis
 * 
 * @ClassName: RedisCache 
 * @author 陈奇
 * @date 2016年2月18日
 *  
 */
public class RedisCache implements Cache {

	private RedisTemplate<String, Object> redisTemplate;
//	private String cacheTime = PropertyConfigurer.getContextProperty("redis.cacheTime");
	private String cacheTime;
	private String name;

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(String cacheTime) {
		this.cacheTime = cacheTime;
	}

	public Object getNativeCache() {
		return this.redisTemplate;
	}

	public ValueWrapper get(Object key) {
		final String keyf = key.toString();
		Object object = null;
		try{
			object = redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {

					byte[] key = keyf.getBytes();
					byte[] value = connection.get(key);
					if (value == null) {
						return null;
					}
					return toObject(value);

				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}

		return (object != null ? new SimpleValueWrapper(object) : null);
	}

	public <T> T get(Object o, Class<T> aClass) {
		return null;
	}

	public void put(Object key, Object value) {
		final String keyf = key.toString();
		final Object valuef = value;
		final long liveTime = Long.parseLong(cacheTime);

		try{
			redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					byte[] keyb = keyf.getBytes();
					byte[] valueb = toByteArray(valuef);
					connection.set(keyb, valueb);
					if (liveTime > 0) {
						connection.expire(keyb, liveTime);
					}
					return 1L;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public ValueWrapper putIfAbsent(Object o, Object o1) {
		return null;
	}

	private byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	private Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public void evict(Object key) {
		final String keyf = key.toString();
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.del(keyf.getBytes());
			}
		});
	}

	public void clear() {
		redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

}
