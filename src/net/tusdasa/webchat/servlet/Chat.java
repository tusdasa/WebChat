package net.tusdasa.webchat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tusdasa.webchat.security.CsrfCheck;
import net.tusdasa.webchat.service.UserChat;
import net.tusdasa.webchat.service.UserOnline;

public class Chat extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		UserChat userChat=new UserChat();
		String user_token="";
		PrintWriter io = response.getWriter();
		if (CsrfCheck.Referercheck(request, "main.jsp") || CsrfCheck.Referercheck(request, "admin.jsp") ) {
			user_token=userChat.getToken(request);
			if(user_token!=null){
				UserOnline userOnline=new UserOnline();
				if(userChat.logined(user_token)){
					String Message=request.getParameter("text");
					String getMessage=request.getParameter("gettext");
					String number=request.getParameter("number");
					String logout=request.getParameter("logout");
					if(getMessage!=null){
						io.print(userChat.getPMessage());	
					}
					if(Message!=null){
						userChat.sendPMessage(user_token,Message);
					}
					
					if(number!=null){
						
						io.print(userOnline.getnumber());
					}
					
					if(logout!=null){
						userOnline.userLogOut(user_token);
					}
					
				}else{
					io.print("用户未认证");
				}
			}else{
				io.print("未登录");
			}
			
		}else{
			io.print("非法提交");
		}
	}


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	
	
}
