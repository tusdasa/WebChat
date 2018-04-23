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
		
	//���ύ��� 
	if(CsrfCheck.Referercheck(request, "login.jsp") || CsrfCheck.Referercheck(request, "login.jsp?Status=success")){
		String username = request.getParameter("username").trim().replace(" ", "");
		String password = request.getParameter("password").trim().replace(" ", "");
		//Σ�չؼ��ּ��
		for (int i = 0; i < KeyWordCheck.getKey().length; i++) {
			if (username.equals(KeyWordCheck.getKey()[i])) {
				response.sendRedirect("error.jsp?Status=11");
			}
		}
		
		UserLogin userLogin=new UserLogin();
		//���cookie
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
			// �û���½
			case "account_login": {
				//����Ƿ��Ѿ�������λ�õ�¼
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
			// �˺ű���
			case "name_disabled":
				response.sendRedirect("error.jsp?Status=0");
				break;
			// �������
			case "password_wrong":
				response.sendRedirect("error.jsp?Status=1");
				break;
			// ���ݿ����ò���ȷ
			case "config_error":
				response.sendRedirect("error.jsp?Status=2");
				break;
			// ���ݿ�����ʧ��
			case "sql_error":
				response.sendRedirect("error.jsp?Status=3");
				break;
			// �˺Ų�����
			case "username_not_exist":
				response.sendRedirect("error.jsp?Status=4");
				break;
			default:
				break;
			}
		}
		
	//�Ǳ��ύ
	}else{
		//ת������ҳ��
		response.sendRedirect("error.jsp?Status=6");
	}	
			
		
	}

}
