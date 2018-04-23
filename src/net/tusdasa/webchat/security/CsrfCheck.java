package net.tusdasa.webchat.security;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class CsrfCheck extends HttpServlet {
	public static boolean Referercheck(HttpServletRequest request, String page) {
		boolean chackreslt = false;
		if (request.getHeader("Referer") != null) {
			// 配为默认首页时 为http://localhost/WebChat/
			String url = CsrfCheck.getWebpath(request) + page;
			String ur=CsrfCheck.getWebpath(request);
			if (request.getHeader("Referer").equals(url) || request.getHeader("Referer").equals(ur)) {
				chackreslt = true;
			}
		}
		return chackreslt;
	}

	public static String getWebpath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
		return basePath;

	}

}
