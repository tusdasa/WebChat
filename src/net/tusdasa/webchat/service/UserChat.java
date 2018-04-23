package net.tusdasa.webchat.service;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.tusdasa.webchat.database.db_MySQL;
import net.tusdasa.webchat.database.db_Redis;

public class UserChat {
	DBCache dbCache=new DBCache();
	db_Redis redis=dbCache.getRedis();
	db_MySQL mySQL=dbCache.getMysql();
	// s/p Ë½ÃÜ/¹«¹²
	public void sendPMessage(String token,String Message) {
			String name= this.getSenderName(redis.getDate(1, token));
			String ms="<b>"+name+"</b>  :"+Message;
			redis.setDate(0, "PM",ms , 2);
	}

	public void sendSMessage(String Message, String username) {
		String sql="SELECT uid FROM webchat WHERE username=?";
		String[] arr={username};
		try {
			String uid=mySQL.selectString(sql, 1, arr);
			redis.setDate(0, uid, Message, 2);
		} catch (SQLException e) {
			
		}
		
	}
	
	private String getSenderName(String uid){
		String sql="SELECT nickname FROM webchat WHERE uid=?";
		String[] arr={uid};
		try {
			String username=mySQL.selectString(sql, 1, arr);
			return username;
		} catch (SQLException e) {
			return "";
		}
		
	}

	public String getPMessage() {
		return redis.getDate(0, "PM");
	}

	public String getSMessage(String token) {
		return redis.getDate(0, redis.getDate(1, token));
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
	
	public Boolean logined(String token){
		if(redis.existDate(1, token)){
			return true;
		}else{
			return false;
		}
	}
}
