package net.tusdasa.webchat.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tusdasa.webchat.security.CsrfCheck;
import net.tusdasa.webchat.security.KeyWordCheck;
import net.tusdasa.webchat.service.UserLogin;
import net.tusdasa.webchat.util.Uid;

/**
 * 
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
		
	//表单提交检查 
	if(CsrfCheck.Referercheck(request, "login.jsp") || CsrfCheck.Referercheck(request, "login.jsp?Status=success")){
		String username = request.getParameter("username").trim().replace(" ", "");
		String password = request.getParameter("password").trim().replace(" ", "");
		//危险关键字检查
		for (int i = 0; i < KeyWordCheck.getKey().length; i++) {
			if (username.equals(KeyWordCheck.getKey()[i])) {
				response.sendRedirect("error.jsp?Status=11");
			}
		}
		
		UserLogin userLogin=new UserLogin();
		//检查cookie
		String user_token=null;
		user_token=userLogin.getToken(request);
		if(user_token!=null){
			if(userLogin.exRedis(user_token)){
				response.sendRedirect("main.jsp");
			}else{
				response.sendRedirect("login.jsp");
			}
		}else{
			String s=userLogin.Login(username, password).toString();
			String token = Uid.getuid().toString();
			switch (s) {
			// 用户登陆
			case "account_login": {
				//检查是否已经在其他位置登录
				if (userLogin.exUid(userLogin.getUid())) {
					response.sendRedirect("error.jsp?Status=10");
				} else {
					userLogin.setRedis(token, userLogin.getUid(),userLogin.getUsername());
					Cookie cookie = new Cookie("token", token);
					cookie.setPath("/");
					//cookie.setMaxAge(1800);
					response.addCookie(cookie);
					response.sendRedirect("main.jsp");
				}
			}
				break;
			// 账号被禁
			case "name_disabled":
				response.sendRedirect("error.jsp?Status=0");
				break;
			// 密码错误
			case "password_wrong":
				response.sendRedirect("error.jsp?Status=1");
				break;
			// 数据库配置不正确
			case "config_error":
				response.sendRedirect("error.jsp?Status=2");
				break;
			// 数据库连接失败
			case "sql_error":
				response.sendRedirect("error.jsp?Status=3");
				break;
			// 账号不存在
			case "username_not_exist":
				response.sendRedirect("error.jsp?Status=4");
				break;
			default:
				break;
			}
		}
		
	//非表单提交
	}else{
		//转到错误页面
		response.sendRedirect("error.jsp?Status=6");
	}	
			
		
	}

}
