<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>问卷列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="list">
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="static/css/public.css" />
<link rel="stylesheet" type="text/css" href="static/css/list.css">
<link rel="stylesheet" type="text/css" href="static/css/help.css">

<link rel="stylesheet" type="text/css" href="static/css/bootstrap.css"  />
<link rel="stylesheet" type="text/css" href="static/css/flow.css" />

</head>

<body>

	<div class="main">
		<div class="list_head">
			<div class="list_title">
				<h1>
					<a href="">问卷调查</a>
				</h1>
				<div class="head_nav">
					<a href="naire/list">我的</a>
					<a class="active" href="naire/lib">模板库</a>
					<a href="javascript:void(0);">帮助</a>
				</div>
	
				<div class="userHead" id="top">
					<c:choose>
						<c:when test="${empty sessionScope.user}">
							<a href="">登录</a> <a>|</a> <a href="register">注册</a>
						</c:when>
						<c:otherwise>
							<img class="img" src="${empty sessionScope.user.icon?'static/image/index1.jpg':sessionScope.user.icon}" alt="头像">
							<a title="${sessionScope.user.nick}" class="userName">${sessionScope.user.nick}</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="userNav">
				<a href="user/settings">我的账户</a>
				<a href="user/logout">退出</a>
			</div>
		</div>
		<marquee scrolldelay="100" onmouseover="this.stop();" onmouseout="this.start();" >
			<span><i></i>快速制作网络问卷、收集数据并生成精美报表，助你完成客户满意度调查、员工满意度调查、360度员工测评、用户态度与行为研究、产品测试、投票评选...&nbsp;&nbsp;&nbsp;&nbsp;<a href="naire/edit">立即前往</a></span>
			<span><i></i>报名表、登记表、反馈表、邀请函、网络订单等各类表单都能用问卷网便捷地制作，发布后还能助你快速完成数据收集和分析...</span>
			<span><i></i>轻松制作一份能自动算分的试卷，适用于培训考试、教学测验、员工考核等等，趣味测试、心理测试也不在话下...</span>
		</marquee>
		<div class="content">
			<h2>问卷调查的流程十分简便，基本流程如下：</h2>
			<img src="static/image/help_1.png"></img>
			<h2>下面我们来介绍一下具体使用方法：</h2>
			<h3>第一步：点击页面中心的创建第一份问卷按钮，开始创建您的问卷</h3>
			<img style="width: 50%; height: auto;" src="static/image/help_2.png"></img>
			<h3>第二步：进入设计界面编写问卷，完成编写后，点击发布问卷</h3>
			<img style="width: 50%; height: auto;" src="static/image/help_3.png"></img>
			<h3>第三步：发布后系统将生成唯一的一个访问链接和二维码，可将该链接或二维码发送给受访者回答</h3>
			<img style="width: 50%; height: auto;" src="static/image/help_4.png"></img>
			<img style="width: 50%; height: auto;" src="static/image/help_5.png"></img>
			<h3>第四步：进入分析结果页面查看你收到的数据，点击导出按钮可以下载数据和报表</h3>
			<img style="width: 50%; height: auto;" src="static/image/help_6.png"></img>
			
			<!-- <div class="lPage"><i></i></div>
			<div class="cc">
			<h2>下面我们来介绍一下具体使用方法：</h2>
			<h3>第一步：点击页面中心的创建第一份问卷按钮，开始创建您的问卷</h3>
			<img style="width: 50%; height: auto;" src="static/image/help_2.png"></img>
			
			</div>
			<div class="rPage"><i></i></div> -->
		</div>
		
	</div>
	
	
</body>

<script type="text/javascript">
</script>

</html>


