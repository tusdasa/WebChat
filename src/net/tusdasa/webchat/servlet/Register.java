package net.tusdasa.webchat.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import net.tusdasa.webchat.security.CsrfCheck;
import net.tusdasa.webchat.security.KeyWordCheck;
import net.tusdasa.webchat.service.UserRegister;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if (CsrfCheck.Referercheck(request, "register.jsp")) {
			if (request.getParameter("username") != null && request.getParameter("password") != null) {
				String username = request.getParameter("username").trim().replace(" ", "");
				String password = request.getParameter("password").trim().replace(" ", "");
				for (int i = 0; i < KeyWordCheck.getKey().length; i++) {
					if (username.equals(KeyWordCheck.getKey()[i])) {
						response.sendRedirect("error.jsp?Status=11");
					}
				}
				UserRegister userRegister = new UserRegister();
				switch (userRegister.register(username, password)) {
				// �û�ע��ɹ�
				case "regist_success":
					response.sendRedirect("login.jsp?Status=success");
					break;
				// �û��Ѿ�����
				case "username_exist":
					response.sendRedirect("error.jsp?Status=9");
					break;
				// ���ݿ����ò���ȷ
				case "config_error":
					response.sendRedirect("error.jsp?Status=2");
					break;
				// ���ݿ�����ʧ��
				case "sql_error":
					response.sendRedirect("error.jsp?Status=3");
					break;
				default:
					break;
				}
			}
		} else {
			response.sendRedirect("error.jsp?Status=6");
		}
	}

}
