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

<title>问卷调查</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="问卷">
<meta http-equiv="description" content="问卷网(www.wenjuanwang.com)">



<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" href="static/css/index.css" />
<link rel="stylesheet" href="static/css/popup.css" />
<link rel="stylesheet" href="static/css/message.css" />

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/localStorage.js"></script>
<script type="text/javascript" src="static/js/index.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/jquery.mousewheel.js"></script>

<style type="text/css">
.sss {
	font-size: 15px;
	margin-right: 4%;
}
.sss a {
	float: right;
	color: #009dff;
}

.sss label {
	color: #4c4c4c;
}
</style>

</head>


<body>


	<div class="background">
		<img id="img1" class="backgroundImg" src="static/image/index1.jpg" alt="background"">
		<div class="introduce">
			<h1>问 卷</h1>
			<h4>快速制作网络问卷、收集数据并生成精美报表，助你完成客户满意度调查、员工满意度调查、360度员工测评、用户态度与行为研究、产品测试、投票评选...</h4>
		</div> 
		<img id="img2" class="backgroundImg" src="static/image/index2.jpg" alt="background">
		<div class="introduce">
			<h1>表 单</h1>
			<h4>报名表、登记表、反馈表、邀请函、网络订单等各类表单都能用问卷网便捷地制作，发布后还能助你快速完成数据收集和分析...</h4>
		</div> 
		<img id="img3" class="backgroundImg" src="static/image/index3.jpg" alt="background">
		<div class="introduce">
			<h1>评 测</h1>
			<h4>轻松制作一份试卷，适用于培训考试、教学测验、员工考核等等，趣味测试、心理测试也不在话下...</h4>
		</div> 
		<img id="img4" class="backgroundImg" src="static/image/index4.jpg" alt="background">
	</div>
	<div class="foreground">
		<a href="" class="h1">问卷调查</a>
		<div class="nav">
			<a href="javascript:void(0);">使用场景</a>
			<a href="naire/lib">模板库</a>
			<a href="javascript:void(0);">帮助中心</a>
		</div>
		
		<div class="tipBox">
			<div class="tip tip1">
				<a href="#img2" onclick="return false">问卷调研</a>
				<a href="#img3" onclick="return false">表单报名</a>
				<a href="#img4" onclick="return false">测评考试</a>
			</div>
		</div>
		<div class="tipBox">
			<div class="tip tip2">
				<c:forEach items="${requestScope.naireCategoryList}" var="pCategory">
					<c:if test="${pCategory.parentId == 0}">
						<div class="category">
						<a href="naire/lib?parentCategory=${pCategory.id}">${pCategory.categoryDesc}模板</a>
						<c:forEach items="${requestScope.naireCategoryList}" var="cCategory">
							<c:if test="${cCategory.parentId == pCategory.id}">
								<a href="naire/lib?category=${cCategory.id}" >${cCategory.categoryDesc}</a>
							</c:if>
						</c:forEach>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
		
		<div class="btn_member">
			<c:choose>
				<c:when test="${empty sessionScope.user}">
					<a href="javascript:void(0);">QQ登陆</a>
					<a href="javascript:void(0);" rel="nofollow" id="loginPanel">登录</a>
					<a href="register" rel="nofollow" target="_blank">注册</a>
				</c:when>
				<c:otherwise>
					<a href="naire/list" rel="nofollow">进入后台</a>
					<a href="user/logout" rel="nofollow">退出</a>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="imgBox ">
			<img class="img" src="static/image/ys1.png" alt="倾听你在乎的"> 
			<img class="img" src="static/image/ys2.png" alt="Listen to what you care">
		</div>
		<c:choose>
			<c:when test="${empty sessionScope.user}">
				<div class="btn">
					<a href="register" class="zc_btn" target="_blank">免费注册</a>
				</div>
			</c:when>
		</c:choose>
		<div class="number">
			<span><fmt:formatNumber value="${statisticMap.usercount}" pattern="###,###,###,###,###"/></span>个用户使用问卷网收集了<span><fmt:formatNumber value="${statisticMap.datacount}" pattern="###,###,###,###,###"/></span>份数据
		</div>
		
		<div class="scroll">
		  	<a href="#img1" onclick="return false"><div></div></a>
		  	<a href="#img2" onclick="return false"><div></div></a>
		 	<a href="#img3" onclick="return false"><div></div></a>
		 	<a href="#img4" onclick="return false"><div></div></a>
		</div>
	

		<div id="popLayer" class="popLayer"></div>
		<div id="popBox" class="popBox">
			<div>
				<a href="javascript:void(0);" class="close_tc"></a>
			</div>
			<div class="title">
				<b>登 录</b>
			</div>
			<input type="text" class="txt" placeholder="手机号码" id="phone" autocomplete="off"/>
			<span id="wPhone" class="warning">*</span> 
			<input type="password" class="txt" placeholder="密码" id="password" />
			<span id="wPassword" class="warning">*</span>
			
			<div class="sss">
				<input id="remember" type="checkbox" /><label for="remember"> 记住我</label>
				<a href="forgetpwd">忘记密码？</a>
			</div>
			
			
			<div style="margin-right: 6%;">
				<button class="login_btn">登 录</button>
			</div>
		</div>
	</div>
	


</body>

<script type="text/javascript">
/* $('html').on('contextmenu', function (){return false;}); */

</script>


</html>
