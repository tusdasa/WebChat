package net.tusdasa.webchat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import net.tusdasa.webchat.database.db_Redis;
import net.tusdasa.webchat.security.CsrfCheck;
import net.tusdasa.webchat.service.DBCache;
import net.tusdasa.webchat.service.UserAdmin;
import net.tusdasa.webchat.service.UserOnline;
import net.tusdasa.webchat.service.UsersControl;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		UserAdmin userAdmin=new UserAdmin();
		String token=userAdmin.getToken(request);
		PrintWriter io=response.getWriter();
		DBCache dbCache=new DBCache();
		db_Redis redis=dbCache.getRedis();
		if(token!=null){
			if(redis.existDate(1, token) && redis.getDate(1, token).equals("830932113")){
				if(CsrfCheck.Referercheck(request, "admin.jsp")){
					UserOnline userOnline=new UserOnline();
					//----------------------------------
					// userlogin usertoken username
					String type=request.getParameter("type");
					if(type!=null && type.equals("admin")){
						Set<String> userlogin=redis.getSet(1, "userlogin");
						Set<String> usertoken=redis.getSet(1, "usertoken");
						Set<String> username=redis.getSet(1, "username");
						Iterator<String> userlogin_it =userlogin.iterator();
						Iterator<String> usertoken_it=usertoken.iterator();
						Iterator<String> username_it=username.iterator();
						JSONArray JS=new JSONArray();
						while(userlogin_it.hasNext() && username_it.hasNext() && username_it.hasNext()){
							Map<String, String> map=new HashMap<>();
							map.put("username",username_it.next() );
							map.put("token",usertoken_it.next() );
							map.put("uid",userlogin_it.next() );
							JS.put(map);
						}
						io.print(JS.toString());
						
					}
					
					if(request.getParameter("token")!=null || request.getParameter("uid")!=null){
						String tokens=request.getParameter("token").trim();
						String uid=request.getParameter("uid").trim();
						if(uid!=null && uid.equals("NULL")!=true){
							try {
								UsersControl usersControl=new UsersControl();
								usersControl.resetStatus(false, uid);
							} catch (Exception e) {	
								e.printStackTrace();
							}
						}
						if(tokens!=null && tokens.equals("NULL")!=true){
							
							userOnline.userLogOut(tokens);
						}
						
						
					}	
					
					
					
					//--------------------------------------
					
				}
			}else{
				io.print("没有权限");
			}
		}else{
			io.print("请登录");
		}
		
		
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

}
