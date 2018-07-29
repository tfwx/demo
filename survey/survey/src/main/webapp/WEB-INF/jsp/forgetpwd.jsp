<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<title>找回密码</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="问卷">
<meta http-equiv="description" content="问卷网(www.wenjuanwang.com)">


<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/forgetpwd.css" />
<link rel="stylesheet" href="static/css/message.css" />

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/forgetpwd.js"></script>


<style type="text/css">
</style>

</head>


<body>

	<header>
		<a href="" class="logo">问卷调查</a>
		<div class="menu">
			<a href="">使用场景</a>
			<a href="naire/lib">模板库</a>
			<a href="javascript:void(0);">帮助中心</a>		
		</div>
		<div class="operation">
			<a href="">登录</a>
			<a href="register">注册</a>
		</div>
	</header>
	<div class="content">
		<div>
			<h2>手机找回</h2>
			<form action="resetPwd" method="post">
				<input type="text" id="phone" name="phone" placeholder="手机号码" autocomplete="off">
				<input type="text" id="verifyCode" placeholder="验证码" style="display: inline;width: 235px;" autocomplete="off">
				<a href="javascript:void(0);" class="sendcode">发送验证码</a>
				<input type="password" id="password" name="password" placeholder="输入新密码" >
				<input type="submit" value="找回密码">			
			</form>
		</div>
	</div>
	
	<footer>
		<img src="static/image/logo.png">	
	</footer>

</body>

<script type="text/javascript">

// not complete

</script>


</html>
