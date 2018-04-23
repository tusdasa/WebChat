package net.tusdasa.webchat.service;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import net.tusdasa.webchat.database.db_MySQL;
import net.tusdasa.webchat.database.db_Redis;
import net.tusdasa.webchat.security.PasswordSafe;

public class UserLogin {
	//用户登录有效期
	private int EXPLET_TIME = 1800;
	//已登录用户UID表
	private String LOGIN_USER = "userlogin";
	//登录用户的token
	private String LOGIN_USER_TOKEN="usertoken";
	//登录用户的用户名
	private String LOGIN_USERNAME="username";
	//用户的UID
	private String uid;
	//用户名
	private String username;
	//密码
	private String password;
	//账号状态
	private String status;
	//数据库实例
	//private db_MySQL db = new db_MySQL();
	//private db_Redis redis = new db_Redis();
	private db_MySQL db;
	private db_Redis redis;

	/**
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return Login
	 * 
	 * 返回的字符串
	 * 
	 * ----------------------------------------------
	 * +         返回的字符串             +           处理结果        +
	 * ----------------------------------------------
	 * +      account_login    +  账号正常登录                        +
	 * +      name_disabled    +  账号被禁用                            +
	 * +      password_wrong   +  密码错误/用户名正确           +
	 * +    username_not_exist +  用户名不存在                         +
	 * +      config_error     +  xml文件读取异常                 +
	 * +      sql_error        +  SQL查询异常                         +
	 * -----------------------------------------------
	 */
	public String Login(String username, String password) {
		try {
			DBCache dbCache=new DBCache();
			db=dbCache.getMySQL();
			redis=dbCache.getRedis();
			
			String sql0 = "SELECT uid FROM webchat  WHERE username=?";
			String[] arr = { username };
			if (db.select_exist(sql0, 1, arr) != 0) {
				String sql1 = "SELECT uid,username,password,status FROM webchat WHERE username=?";
				List<Map> li = db.select(sql1, 1, arr);

				for (int i = 0; i < li.size(); i++) {
					Map map = li.get(i);
					Iterator<Entry<String, String>> it = map.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, String> entry = it.next();
						switch (entry.getKey()) {
						case "uid":
							this.setUid(entry.getValue());
							break;
						case "username":
							this.setUsername(entry.getValue());
							break;
						case "password":
							this.setPassword(entry.getValue());
							break;
						case "status":
							this.setStatus(entry.getValue());
							break;
						default:
							break;
						}
					}
				}
				// 判断账号密码是否正确
				if (this.getPassword().equals(PasswordSafe.encode(password))) {
					// 判断账号是否被禁用
					if (this.getStatus()) {
						// 未被禁用
						return "account_login";
					} else {
						// 被禁用
						return "name_disabled";
					}

				} else {
					// 用户名存在但密码错误
					return "password_wrong";
				}
			} else {
				// 用户名不存在
				return "username_not_exist";
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			return "config_error";
		} catch (SQLException e) {
			e.printStackTrace();
			return "sql_error";
		}
	}

	/**
	 * 
	 * @return uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * 
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	/**
	 * 
	 * @return username
	 */

	public String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @param username
	 */

	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 
	 * @return password
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @return true/false
	 */

	public boolean getStatus() {

		if (this.status.equals("1")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param status
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	public void setRedis(String token, String uid,String usaername) {
		redis.setDate(1, token, uid, EXPLET_TIME);
		redis.setSet(1, LOGIN_USER, uid);
		redis.setSet(1,LOGIN_USER_TOKEN,token);
		redis.setSet(1,LOGIN_USERNAME,usaername);
		redis.setTTL(1, LOGIN_USER, EXPLET_TIME);
		redis.setTTL(1, LOGIN_USER_TOKEN, EXPLET_TIME);
		redis.setTTL(1, LOGIN_USERNAME, EXPLET_TIME);
	}

	public String getRedis(String token) {
		return redis.getDate(1, token);
	}

	public boolean exRedis(String token) {
		Boolean f=false;
		if (redis.existDate(1, token)) {
			// 始终为假
			Long k = redis.setSet(1, LOGIN_USER, redis.getDate(1, token).toString());
			if (k == 0) {
				f=true;
				return f;
			} else {
				return f;
			}
		} else {
			return f;
		}
	}

	public Boolean exUid(String uid) {

		Long i = redis.setSet(1, LOGIN_USER, uid);
		// 不等于零 不是已经登录的用户登录
		if (i != 0) {
			return false;
		} else {
			// 等于零,已经登录的用户再次登录
			return true;
		}
	}

	public String getToken(HttpServletRequest request){
		String user_token = "";
		
		if(request.getCookies()!=null){
			Cookie[] cookies = request.getCookies();
			Boolean cook = false;
			for (Cookie cookie1 : cookies) {
				cook =cookie1.getName().equals("token");
				user_token = cookie1.getValue();
			}
			if(cook){
				return user_token;
			}else {
				return null;
			}
		}else{
			return null;
		}
		
		
		
	}
}