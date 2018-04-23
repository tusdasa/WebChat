<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>聊天室</title>
<meta charset="UTF-8">
<script type="text/javascript" src="static/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="static/js/jquery.cookie-1.4.1.min.js"></script>
<link rel="stylesheet" href="static/assets/css/amazeui.min.css" />
<script type="text/javascript" src="static/js/wangEditor.min.js"></script>
</style>
</head>

<body>

	<table width="90%" height="500px" border="0" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td><span style='font-size:14px; line-height:30px;'>欢迎来到聊天室，请遵守聊天室规则，不要使用不文明用语。</span><br></td>
			<td>在线人数:<span id="onlinenumber">0</span><br></td>
		</tr>
		<tr>
			<td width="90%" height="400px" valign="top" bgcolor="#FFFFFF"
				style="padding:5px; ">
				<div style="height:45px; overflow:hidden"><h3>聊天内容:</h3></div>
				<div style="height:40px; overflow:hidden" id="content"></div>
			</td>
			<td >
				<div id="name"></div>
			</td>
		</tr>
	</table>
	<div class="am-cf">
	<button id="btn1" class="am-btn am-btn-primary am-btn-sm am-fl">发送消息</button>
	</div>
	<div id="editor" ></div>
	<script type="text/javascript">
		var E = window.wangEditor
		var editor = new E('#editor')
		window.setInterval("get();", 1500);
		window.setInterval("getnumber()",5000);
		$(function() {
			get();
			getnumber();
		});
		editor.customConfig.menus = [
			/*'bold', //粗体*/
			/*'italic', // 斜体*/
			'emoticon', //表情
			'undo' //撤销
		]
		editor.create()
		
		document.getElementById('btn1').addEventListener('click', function() {
			send();
	
		}, false)
		
		
	
		function send() {
		
			$.post("${pageContext.request.contextPath}/Chat",{
				text:editor.txt.text(),
				time:new Date().getTime()
			
			});
			
			//$.post("${pageContext.request.contextPath}/Chat?text=" + editor.txt.text() +"&" + "time=" + new Date().getTime());
		}
		
		function logout() {
		$.post("${pageContext.request.contextPath}/Chat",{
				logout:1,
				time:new Date().getTime()
			});
			//$.post("${pageContext.request.contextPath}/Chat?logout=1" +"&" + "time=" + new Date().getTime());
		}
	
		function get() {
		$.post("${pageContext.request.contextPath}/Chat",{
				gettext:1,
				time:new Date().getTime()
			},function(data){
				if (data == "null") {
				} else {
					$("#content").html("<span style=/'line-height:20px;/'>" + data + "</span><br />");
				}
			});
		
		
		/*
			$.post("${pageContext.request.contextPath}/Chat?gettext=1" + "&" + "time=" + new Date().getTime(), function(data) {
				if (data == "null") {
				} else {
					$("#content").html("<span style=/'line-height:20px;/'>" + data + "</span><br />");
				}
			}
			);
			*/
		}
		
		function getnumber() {
		$.post("${pageContext.request.contextPath}/Chat",{
				number:1,
				time:new Date().getTime()
			},function(data){
				if (data == "null") {
				} else {
					document.getElementById("onlinenumber").innerHTML=data;
				}
			});
		
		
		/*
			$.post("${pageContext.request.contextPath}/Chat?number=1" + "&" + "time=" + new Date().getTime(), function(data) {
				if (data == "null") {
				} else {
					document.getElementById("onlinenumber").innerHTML=data;
				}
			}
			);
			*/
		}
	</script>
<div class="am-cf">
	<button id="btn2" class="am-btn am-btn-primary am-btn-sm am-fl">退出聊天室</button>
	</div>
	<script type="text/javascript">
	document.getElementById('btn2').addEventListener('click', function() {
			alert("您已注销");
			logout();
			window.location.href='login.jsp';
		}, false)
	
	
	</script>
</body>
</html>
