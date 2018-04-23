<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Admin</title>
    <script type="text/javascript" src="static/js/jquery-3.2.1.min.js" ></script>
    <script type="text/javascript">
		//window.setInterval("getjson()",5000);
		window.setInterval("getnumber()",5000);
    $(function() {
			getjson();
			getnumber();
		});
    
    function send() {
			$.post("${pageContext.request.contextPath}/Admin",
			{
				token:document.getElementById("form1").token.value,
				uid:document.getElementById("form1").uid.value
			},
			function(data){
			});
			console.log("test");
		};
		
    function getjson(){
    	$.getJSON("${pageContext.request.contextPath}/Admin?type=admin",function(json){
    for(var i=0;i<json.length;i++){	
    	$("#tmp").append("<tr id="+"'"+ "u"+i+"'"+"><td id="+"'"+ "name"+i+"'"+"></td>"+"<td id="+"'"+ "uid"+i+"'"+"></td>"+"<td id="+"'"+ "token"+i+"'"+"></td>"+"</tr>");
   		$("#name"+i).text(json[i].username);
    	$("#uid"+i).text(json[i].uid);
    	$("#token"+i).text(json[i].token)
   
   }	
});
    
    };
    
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
			
			};
    
    </script>
  </head>
  
  <body>
  <p align="center">当前在线人数:<span id="onlinenumber"></span></p>
<table width="800" height="517" border="1" align="center" cellpadding="0" cellspacing="0" id="tmp">
  <tr>
  
    <th width="200" scope="col" >用户名</th>
    <th width="200"  scope="col">UID</th>
    <th width="200"  scope="col">token</th>
  </tr>
</table>
<table width="500" height="200" border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <th align="center" valign="middle" scope="col">
    <form id="form1" name="form1" method="post" action="">
    <p>禁言请输入UID，踢下线请输入Token</p>
  <p>
  	
    Token<input type="text" name="token" id="token" value="NULL" /><br />
   &nbsp;UID&nbsp;&nbsp;<input type="text" name="uid" id="uid" value="NULL" /><br />
    <input type="submit" id="button" onclick="check()" value="提交" />
  </p>
</form></th>
  </tr>
</table>
<script type="text/javascript">
function check(){
		if(document.getElementById("form1").token.value==""){
			if(document.getElementById("form1").uid.value==""){
			alert("必须输入一个！");
			document.getElementById("form1").uid.focus();
			return false;
		}
		}
		send();
		
	}

</script>
  </body>
</html>
