package net.tusdasa.webchat.service;

import org.dom4j.DocumentException;

import net.tusdasa.webchat.configreder.Readconfig;
import net.tusdasa.webchat.database.db_MySQL;
import net.tusdasa.webchat.database.db_Redis;

public class DBCache {
	private db_MySQL mysql;
	private db_Redis redis;

	public db_MySQL getMySQL() throws DocumentException {
		mysql = new db_MySQL();
		Readconfig readconfig = new Readconfig();
		mysql.setDb_adder(readconfig.getDb_adder());
		mysql.setDb_dbname(readconfig.getDb_dbname());
		mysql.setDb_prot(readconfig.getDb_prot());
		mysql.setDb_username(readconfig.getDb_username());
		mysql.setDb_password(readconfig.getDb_password());
		return mysql;
	}

	public db_Redis getRedis() {
		try {
			redis = new db_Redis();
			Readconfig readconfig = new Readconfig();
			redis.setRedis_adder(readconfig.getDb_redis());
			redis.setRedis_port(readconfig.getDb_redis_port());
			return redis;
		} catch (Exception e) {
			return null;
		}

	}

	public void setDate(int db, String key, String value) {
		db_Redis jedis = this.getRedis();
		jedis.setDate(key, value, 2);
	}

	
	//Ã·π©
	public String getDate(String key) {
		try {
			db_Redis jedis = this.getRedis();
			return jedis.getDate(key);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public db_MySQL getMysql() {
		try {
			return this.getMySQL();
		} catch (Exception e) {

			return null;
		}

	}

}
