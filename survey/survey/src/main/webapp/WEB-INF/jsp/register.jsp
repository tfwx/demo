<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>用户注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="list">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/register.css">
<link rel="stylesheet" type="text/css" href="static/css/popup.css">
<link rel="stylesheet" type="text/css" href="static/css/message.css">

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/register.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>


</head>

<body>
	<div class="bg">
		<div class="center">
			<div class="rTitle">注 册</div>
			<input type="text" placeholder="昵称" class="txt" id="nick" autocomplete="off">
			<span id="wNick" class="warning">*</span>
			<input type="text" placeholder="手机号码" class="txt" id="phone" autocomplete="off">
			<span id="wPhone" class="warning">*</span>
			<input type="text" class="txt" style="width: 59%;" placeholder="输入验证码" id="verifyCode" autocomplete="off">
			<input type="button" class="code" value="获取验证码">
			<span class="warning">*</span>
			<input type="password" placeholder="密码" class="txt" id="password" >
			<span id="wPassword" class="warning">*</span>
			<div style="margin-right: 4%;">
				<button class="login_btn register_btn"><b>马上注册</b></button>
			</div>
		</div>
	</div>
</body>

<script>


</script>

</html>
