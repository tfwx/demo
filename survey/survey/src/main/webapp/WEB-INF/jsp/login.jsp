<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>问卷网</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="data statistics">
<link rel="shortcut icon" href="static/image/favicon.ico"
	type="image/x-icon" />

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/cookie.js"></script>
</head>

<style>

* {
	margin: 0;
	padding: 0;
}

</style>

<body>


<iframe style="width: 100%;height:100%;margin:0;padding:0;display: block;" src="index" frameborder="0" border="0"></iframe> 

<div id="parentDiv" hidden>123</div>
</body>
<script type="text/javascript">

$("#parentDiv").on("click", function() {
	connect();
});
</script>



<script type="text/javascript">
var webSocket = null;


function connect() {
	
	if ('WebSocket' in window) {
		try {
			webSocket = new WebSocket('ws://www.survey.com/webSocket');
		} catch(e){}
		
	} else {
		alert('不支持WebSocket');
	}

	webSocket.onopen = function(event) {
		console.log('webSocket建立连接');
	}

	webSocket.onclose = function(event) {
		console.log('webSocket连接关闭');
	}

	webSocket.onmessage = function(event) {
		alert(event.data);
		window.location.href = '/login';
	}

	webSocket.onerror = function() {
		alert('通信发生错误');
	}
	
	window.onbeforeunload = function() {
		webSocket.close();
		webSocket = null;
		console.log('重连webSocket');
		connect();
	}
	
}



</script>

</html>





