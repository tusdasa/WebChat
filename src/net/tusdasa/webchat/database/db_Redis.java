package net.tusdasa.webchat.database;
/**
 * 
 * @author tusdasa
 * 类名:db_Redis
 * 功能:连接 Redis 删 增 改 查
 * 时间: 2017年7月15日15:23:01
 */

import java.util.Set;

import redis.clients.jedis.Jedis;

public class db_Redis {

	private String redis_adder = "192.168.90.135";
	private String redis_port = "6379";

	private Jedis RedisConnect(String adder) {
		Jedis jedis = new Jedis(redis_adder, Integer.valueOf(redis_port));
		return jedis;
	}

	public void setDate(String key, String value) {
		Jedis jedis = this.RedisConnect(this.redis_adder);
		jedis.set(key, value);
		jedis.close();
	}

	public String getDate(String key) {
		Jedis jedis = this.RedisConnect(this.redis_adder);
		String s = jedis.get(key);
		jedis.close();
		return s;
	}

	public String getDate(int db, String key) {
		Jedis jedis = this.selectDB(db);
		String s = jedis.get(key);
		jedis.close();
		return s;
	}

	public void setRedis_adder(String redis_adder) {
		this.redis_adder = redis_adder;
	}

	public void setDate(String key, String value, int ttl) {
		Jedis jedis = this.RedisConnect(this.redis_adder);
		jedis.setex(key, ttl, value);
		jedis.close();
	}

	public void setDate(int db, String key, String value, int ttl) {
		Jedis jedis = this.selectDB(db);
		jedis.setex(key, ttl, value);
		jedis.close();
	}

	public void resetDate() {
		Jedis jedis = this.RedisConnect(this.redis_adder);
		jedis.flushDB();
		jedis.close();
	}

	public void delDate(String key) {
		Jedis jedis = this.RedisConnect(this.redis_adder);
		jedis.del(key);
		jedis.close();
	}

	public void delDate(int db, String key) {
		Jedis jedis = this.selectDB(db);
		jedis.select(db);
		jedis.del(key);
		jedis.close();
	}

	public boolean existDate(String key) {
		Jedis jedis = this.RedisConnect(this.redis_adder);
		Boolean s=false;
		s = jedis.exists(key);
		jedis.close();
		return s;
	}

	public boolean existDate(int db, String key) {
		Jedis jedis = this.selectDB(db);
		Boolean s=false;
		s = jedis.exists(key);
		jedis.close();
		return s;
	}

	public void setRedis_port(String redis_port) {
		this.redis_port = redis_port;
	}

	private Jedis selectDB(int db) {
		Jedis jedis = this.RedisConnect(this.redis_adder);
		jedis.select(db);
		return jedis;
	}

	public Long setSet(int db, String key, String value) {
		Jedis jedis = this.selectDB(db);
		Long i=0L;
		i = jedis.sadd(key, value);
		jedis.close();
		return i;
	}

	public Long setSet(int db, String key, String[] value) {
		Jedis jedis = this.selectDB(db);
		Long i=0L;
		i = (long) 0;
		for (int k = 0; k < value.length; k++) {
			i = i + jedis.sadd(key, value[k]);
		}
		jedis.close();
		return i;
	}

	public Long getSetSize(int db, String key) {
		Jedis jedis = this.selectDB(db);
		Long i = jedis.scard(key);
		jedis.close();
		return i;
	}

	public Boolean removeSetMember(int db, String key, String members) {
		Jedis jedis = this.selectDB(db);
		Long i = jedis.srem(key, members);
		jedis.close();
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}

	public Set<String> getSet(int db, String key) {
		Jedis jedis = this.selectDB(db);
		Set<String> set = jedis.smembers(key);
		jedis.close();
		return set;
	}
	
	public void setTTL(int db,String key, int seconds) {
		Jedis jedis= this.selectDB(db);
		jedis.expire(key, seconds);
		jedis.close();
	}
	

}
