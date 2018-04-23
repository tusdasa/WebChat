package net.tusdasa.webchat.service;

import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import net.tusdasa.webchat.database.db_Redis;

public class UserAdmin {
	
	private UserOnline userOnline=new UserOnline() ;
	private DBCache dbCache=new DBCache();
	/*
	public String[] getOnlineUserinfo() throws SQLException{
		db_Redis redis=dbCache.getRedis();
		db_MySQL mySQL=dbCache.getMysql();
		String sql="SELECT uid, username, nickname,firsttime,status FROM webchat WHERE uid=?";
		Set<String> set= redis.getSet(1, "userlogin");
		Iterator<String> it=set.iterator();
		
		//List<Map> =mySQL.select(sql,1,);
		
		return null;
		
	}
	*/
	public void logOut(String uid){
		db_Redis redis=dbCache.getRedis();
		Set<String> set= redis.getSet(1, "usertoken");
		Iterator<String> it=set.iterator();
		String[] tokens=new String[set.size()];
		String[] uid1=new String[set.size()];
		int i=0;
		while (it.hasNext()) {
			tokens[i]=it.next();
			i++;
		}
		
		for(int j=0;j<tokens.length;j++){
			uid1[j]=redis.getDate(1, tokens[j]);
			//System.out.println(uid[j]+"------"+tokens[j]);
		}
		
		for(int k=0;k<tokens.length;k++){
			if(uid1[k].toString().equals(uid)){
				userOnline.userLogOut(tokens[k]);
			}
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
	/*
	public static void main(String[] args) throws SQLException{
		UserAdmin userAdmin=new UserAdmin();
		String[] arr=userAdmin.getOnlineUserinfo();
		for(int j=0;j<arr.length;j++){
			System.out.println(arr[j]);
		}
		
	}
	*/
}
