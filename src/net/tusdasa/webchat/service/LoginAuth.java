package net.tusdasa.webchat.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.tusdasa.webchat.database.db_Redis;

public class LoginAuth {
	db_Redis redis=new DBCache().getRedis();
	
	public Boolean Auth(HttpServletRequest request){
		String user_token = "";
		Boolean cook = false;
		if(request.getCookies()!=null){
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie1 : cookies) {
				cook =cookie1.getName().equals("token");
				user_token = cookie1.getValue();
			}
			
			if(cook){
				if(redis.existDate(1, user_token)){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}


}
