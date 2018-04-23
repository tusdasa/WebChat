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
<title>账号注册</title>
<link rel="stylesheet" href="static/assets/css/amazeui.min.css" />
<style>
.header {
	text-align: center;
}

.header h1 {
	font-size: 200%;
	color: #333;
	margin-top: 30px;
}

.header p {
	font-size: 14px;
}
</style>
<script type="text/javascript">
	function check(){
		if(document.getElementById("form1").username.value==""){
			alert("请输入用户名！");
			document.getElementById("form1").username.focus();
			return false;
		}
		
		if(document.getElementById("form1").password.value==""){
			alert("请输入密码！");
			document.getElementById("form1").password.focus();
			return false;
		}
		if(document.getElementById("form1").repassword.value==""){
			alert("请再次输入密码！");
			document.getElementById("form1").password.focus();
			return false;
		}
		
		if(document.getElementById("form1").repassword.value!=document.getElementById("form1").password.value){
			alert("两次的密码不相同");
			document.getElementById("form1").repassword.focus();
			return false;
		}
		
		<%if (request.getParameter("user") != null) {
				if (request.getParameter("user").equals("null")) {
					out.print("alert('您的账号不存在请注册')");
				}
			} else {
				out.print("欢迎注册");
			}%>
	}
	</script>
</head>
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
			<h3>账户注册</h3>

			<form id="form1" method="post" class="am-form"
				action="<%=basePath%>register">
				<label for="username">用户名:</label> <input type="text"
					name="username" id="text"> <br> <label for="password">密码:</label>
				<input type="password" name="password" id="password"> <br>
				<label for="password">再次输入密码:</label> <input type="password"
					name="repassword" id="repassword"> <br> <br />
				<div class="am-cf">
					<input type="submit" name="sumbit" value="注册"
						class="am-btn am-btn-primary am-btn-sm am-fl"
						onclick="return check()"> <input name=""
						value="已有账号? 立即登录" class="am-btn am-btn-default am-btn-sm am-fr"
						onclick="window.open('login.jsp')">
				</div>
			</form>
			<hr>
			<p align="center">©2017 <a href="http://www.tusdasa.net" target="_blank">Tusdasa.net</a> All rights reserved.</p>
		</div>
	</div>
</body>
</html>
