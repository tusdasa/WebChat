package net.tusdasa.webchat.service;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import net.tusdasa.webchat.database.db_MySQL;
import net.tusdasa.webchat.database.db_Redis;
import net.tusdasa.webchat.service.DBCache;

public class UserOnline {
	private DBCache dbCache = new DBCache();
	private db_Redis dbRedis= dbCache.getRedis();
	private db_MySQL dbMySQL= dbCache.getMysql();
	public Long getnumber() {
		Long muber = dbRedis.getSetSize(1, "userlogin");
		return muber;
	}
	
	public String[] getUsername() throws SQLException {
		Set<String> set=dbRedis.getSet(1, "userlogin");
		Iterator<String> it=set.iterator();
		String[] username=new String[set.size()];
		int i=0;
		while(it.hasNext()){
		    String[] arr={it.next()};
		    String Sql="SELECT username FROM webchat WHERE uid=?";
		    username[i]=dbMySQL.selectString(Sql, 1, arr);
		    i++;
		}
		return username;
		
	}
	
	public String getUsername(String uid) {
		    String[] arr={uid};
		    String Sql="SELECT username FROM webchat WHERE uid=?";
		    String username;
			try {
				username = dbMySQL.selectString(Sql, 1, arr);
			} catch (SQLException e) {
				username=null;
			}
		return username;
		
	}
	
	public void userLogOut(String token){
		String uid=dbRedis.getDate(1, token);
		dbRedis.delDate(1, token);
		dbRedis.removeSetMember(1,"userlogin" , uid);
		dbRedis.removeSetMember(1, "usertoken", token);
		dbRedis.removeSetMember(1, "username", this.getUsername(uid));
		
	}
	
	/*
	public static void main(String[] args){
		UserOnline userOnline=new UserOnline();
		System.out.println(userOnline.getnumber());
		userOnline.userLogOut("233332977");
		
		
		
		try {
			String[] arr=userOnline.getUid();
			for(int j=0;j<arr.length;j++){
				System.out.println(arr[j]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
*/
}
