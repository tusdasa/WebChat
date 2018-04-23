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

<title>登录</title>
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
			alert("请输入密码名！");
			document.getElementById("form1").password.focus();
			return false;
		}
	}
	<%if (request.getParameter("Status") != null) {
				if (request.getParameter("Status").equals("success")) {
					out.print("alert('注册成功,请登录')");
				}
			}%>
   
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
			<h3>登录</h3>

			<form id="form1" method="post" class="am-form"
				action="<%=basePath%>login">
				<label for="username">用户名:</label> <input type="text"
					name="username" id="text"> <br> <label for="password">密码:</label>
				<input type="password" name="password" id="password"> <br>
				<label for="remember-me"> <input id="remember-me"
					type="checkbox"> 记住密码
				</label> <br />
				<div class="am-cf">
					<input type="submit" name="" value="登 录"
						class="am-btn am-btn-primary am-btn-sm am-fl"
						onclick="return check()"> <input name=""
						value="没有账号? 立即注册" class="am-btn am-btn-default am-btn-sm am-fr"
						onclick="window.open('register.jsp')">
				</div>
			</form>
			<hr>
			<p align="center">©2017 <a href="http://www.tusdasa.net" target="_blank">Tusdasa.net</a> All rights reserved.</p>
		</div>
	</div>

</body>
</html>
