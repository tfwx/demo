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

<title>收集数据</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="data statistics">
<link rel="shortcut icon" href="static/image/favicon.ico"
	type="image/x-icon" />


<link rel="stylesheet" type="text/css" href="static/css/music/music.css">
<link rel="stylesheet" href="static/css/music/animate.css">
<link rel="stylesheet" href="static/fonts/fontCss.css">
<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/music.js"></script>

</head>

<style>

* {
	margin: 0;
	padding: 0;
}

video {
	width: 50%;
	height: 50%;
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%); 
	border: #009dff 1px solid;;
}

</style>

<body>

<video src="naire/video" controls="controls"></video>













<!-- 
<div class="music-bg" style="height: 0;filter: blur(100px);transition:all 0.3s;" id="music-bg">
	<div class="music-mask"></div>
</div>
 -->
<!-- 
<iframe style="width: 100%;height:100%;margin:0;padding:0;display: block;" src="index" frameborder="0" border="0"></iframe> 

 -->
 
 
 
</body>
<script type="text/javascript">

</script>


<!-- <script type="text/javascript">
window.onload = function(){
	MC.music({
		hasAjax:false,
		left:'10px',
		bottom:'30px',
		musicChanged:function(ret){
			 console.log(ret.url);
			// getMusic_buffer(ret.url);
			// return;
			var data = ret.data;
			var index = ret.index;
			var imageUrl = data[index].img_url;
		/* 	var music_bg = document.getElementById('music-bg');
			music_bg.style.background = 'url('+imageUrl+')no-repeat';
			music_bg.style.backgroundSize = 'cover';
			music_bg.style.backgroundPosition = 'center 30%'; */
		},

		getMusicInfo:function(data){
			
		},

		musicPlayByWebAudio:function(ret){
			
		},
	});
}

</script> -->
<!-- 
<script>

var webSocket = null;
if ('WebSocket' in window) {
	try {
		webSocket = new WebSocket('ws://www.survey.com/webSocket');
	} catch(e){}
	
	alert('yes');
} else {
	alert('不支持WebSocket');
}

webSocket.onopen = function(event) {
	alert('webSocket建立连接');
}

webSocket.onclose = function(event) {
	alert('webSocket连接关闭');
}

webSocket.onmessage = function(event) {
	alert('webSocket收到消息' + event.data);
	window.location.href = '/';
}

webSocket.onerror = function() {
	alert('通信发生错误');
}

window.onbeforeunload = function() {
	webSocket.close();
}
</script>
 -->
</html>





