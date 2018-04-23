<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>错误</title>
<meta name="keywords" content="错误">
<meta name="description" content="错误状态页面">
<meta name="author" content="tusdasa">

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	font-size: 14px;
	line-height: 1.231;
	color: #555;
	text-align: center;
	font-family: "\5fae\8f6f\96c5\9ed1", "\9ed1\4f53", tahoma, arial,
		sans-serif;
}

a {
	color: #555;
	text-decoration: none;
}

a:hover {
	color: #1abc9c;
}

#container {
	width: 684px;
	height: 315px;
	margin: 10px auto 0px auto;
	border: #2c3e50 solid 6px;
	background-color: #2c3e50;
}

;
#container #title {
	overflow: hidden;
	padding-top: 30px;
}

#container #title h1 {
	font-size: 36px;
	text-align: center;
	color: #FFFFFF;
}

#content p {
	font-size: 18px;
}

#footer {
	width: 100%;
	padding: 20px 0px;
	font-size: 16px;
	color: #555;
	text-align: center;
	.
	header
	{
	text-align
	:
	center;
}

.header h1 {
	font-size: 200%;
	color: #333;
	margin-top: 30px;
}

.header p {
	font-size: 14px;
}
}
</style>
<script type="text/javascript" src="static/js/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" href="static/assets/css/amazeui.min.css" />
<script type="text/javascript"> 
 $(function(){
	
    <%if (request.getParameter("Status") != null) {
				switch (request.getParameter("Status")) {
					case "0" :
						//用户被禁言
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>您已被管理员禁言</h1>" + "\"");
						break;
					case "1" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>您的密码错误</h1>" + "\"");
						break;
					case "2" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>配置文件错误,不能读取</h1>" + "\"");
						break;
					case "3" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>数据库配置出错</h1>" + "\"");
						break;
					case "4" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>账号不存在</h1>" + "\"");
						break;
					case "5" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>账户已存在,请登录</h1>" + "\"");
						break;
					case "6" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>这个请求似乎是恶意的</h1>" + "\"");
						break;
					case "7" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>Redis缓存数据库出现错误</h1>" + "\"");
						break;
					case "8" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>您的浏览器没有启用cookie</h1>" + "\"");
						break;
					case "9" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>用户名已经被使用</h1>" + "\"");
						break;
					case "10" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>您的账号在已在其他位置登录</h1>" + "\"");
						break;
					case "11" :
						out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>输入中有危险关键字, 请尝试其他</h1>" + "\"");
						break;
					default :
					    out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>玩够了没</h1>" + "\"");
						break;
				}
			}

			if (request.getParameter("Status") == null) {
				out.print("document.getElementById(\"title\").innerHTML=\"" + "<h1>没事来这干嘛</h1>" + "\"");
			}%>
     });
     </script>
</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>WebChat</h1>
			<p>简易聊天室</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
			<div id="container">
				<div id="title"></div>
				<div id="content">
					<p>
						<a href="javascript:history.go(-1)" style="color:#F00">尝试返回上一页</a>
					</p>
					<br />
					<p style="font-size:24px;font-weight:bold;color:#1abc9c">WebChat
						错误页面</p>
				</div>
			</div>

			<hr>
			<p align="center">
				©2017 <a href="http://www.tusdasa.net" target="_blank">Tusdasa.net</a>
				All rights reserved.
			</p>
		</div>
	</div>
</body>
</html>
