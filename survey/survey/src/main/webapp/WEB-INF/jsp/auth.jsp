<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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

<title>问卷预览</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="description" content="问卷网">


<link rel="shortcut icon" type="image/x-icon" href="static/image/favicon.ico" />
<link rel="stylesheet" type="text/css" href="static/css/public.css?u=<%=Math.random()%>">
<link rel="stylesheet" type="text/css" href="static/css/mobile/naireauth.css?u=<%=Math.random()%>"media="screen and (max-width: 980px)">
<link rel="stylesheet" type="text/css" href="static/css/naireauth.css?u=<%=Math.random()%>" media="screen and (min-width: 981px)">
<script type="text/javascript" src="static/js/jquery.1.7.2.min.js?u=<%=Math.random()%>"></script>

<style type="text/css">

</style>
</head>

<body>
	<div class="cover"></div>
	<div class="shadow"></div>
	<div class="content" >
		<h1>${requestScope.naireTitle}</h1>
		<label>请输入访问密码:</label>
		<input id="password" type="password" placeholder="请输入访问密码" onkeypress="if(event.keyCode==13){$('.content a').trigger('click');}"><br>
		<a href="javascript:void(0);">确认</a>
		<span>密码错误，请重新输入密码</span>
	</div>
	<!-- <footer>
		<a href=""><img style="" alt="" src="static/image/logo.png"></a>
	</footer> -->
	
</body>


<script type="text/javascript">
	var url = window.location.href;
	var index = url.indexOf('?');
	if (index != -1) {
		url = url.substr(0, index);
	}
	$('.content a').on('click', function() {
		$.ajax({
			url : url,
			type : 'post',
			timeout : 3000,
			data : {pwd: $('#password').val()},
			dataType : "json",
			success: function(result) {
				if (result.status == 200) {
					$('.content a').css("pointer-events", "none")
					$('.content span').html('Loading...').css('visibility', 'visible');
					/* window.location.reload();此方法在微信中无效 */
					window.location.href = url + "?u=" + Math.random();
				} else {
					$('.content span').css('visibility', 'visible');
				}
			}, 
			error: function(XMLHttpRequest) {
				alert('网络错误');
			},
		});
	});

	 var screenHeight = document.body.clientHeight;
     var u = navigator.userAgent;
     if (u.indexOf('Android') > -1 || u.indexOf('Adr') > -1) {
         $("body").height(screenHeight);
     }
</script>  

</html>
