<%@page import="com.alibaba.druid.support.json.JSONUtils"%>
<%@page import="com.ylsh.survey.util.JSONUtil"%>
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

<title>模板库</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="list">
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/jquery.page.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/simpleAlert.js"></script>
<script type="text/javascript" src="static/js/nairelibrary.js"></script>
<script type="text/javascript" src="static/js/public.js"></script>

<link rel="stylesheet" type="text/css" href="static/css/public.css" />
<link rel="stylesheet" type="text/css" href="static/css/jquery.page.css">
<link rel="stylesheet" type="text/css" href="static/css/list.css">
<link rel="stylesheet" type="text/css" href="static/css/message.css" />
<link rel="stylesheet" type="text/css" href="static/css/simpleAlert.css" />
<link rel="stylesheet" type="text/css" href="static/css/nairelibrary.css" />
<style>

</style>
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
		
		<div class="search">
			<select>
				<c:forEach items="${requestScope.naireCategoryMap}" var="mapItem">
					<c:set var="category" value="${mapItem.value}"></c:set>
					<c:if test="${category.parentId == 0}">
						<option value="${category.id}" <c:if test="${category.id == requestScope.queryParameter.parentCategory}">selected="selected"</c:if>>${category.categoryDesc}</option>
					</c:if>
				</c:forEach>
			</select><input type="text" class="searchTxt" placeholder="输入关键字，搜索你想要的${requestScope.naireCategoryMap[fn:trim(requestScope.queryParameter.parentCategory)].categoryDesc}模板" onkeypress="if(event.keyCode==13){$('.searchBtn').trigger('click');}"><input id="search" type="button" class="searchBtn" value=" ">
			
			<div class="hotWord">
				热门搜索：
				<a href="javascript:void(0);">满意度</a>
				<a href="javascript:void(0);">大学生消费</a>
				<a href="javascript:void(0);">培训需求</a>
				<a href="javascript:void(0);">兼职</a>
				<a href="javascript:void(0);">消费者</a>
				<a href="javascript:void(0);">就业</a>
				<a href="javascript:void(0);">新生</a>
				<a href="javascript:void(0);">市场调查</a>
				<a href="javascript:void(0);">顾客</a>
				<a href="javascript:void(0);">用户</a>
				<a href="javascript:void(0);">客户</a>
			</div>
		</div>
		

		<div class="content">
			<div>
				<div class="naireCategory">
					<div class="category shadow">
						<div class="title">功能分类</div>
						<div class="cc">
							<span value="" <c:if test="${empty requestScope.queryParameter.category or requestScope.queryParameter.category == ''}">class="cActive"</c:if>>全部</span>						
							<c:forEach items="${requestScope.naireCategoryMap}" var="mapItem">
								<c:set var="category" value="${mapItem.value}"></c:set>
								<c:if test="${category.parentId == requestScope.queryParameter.parentCategory}">
									<span <c:if test="${requestScope.queryParameter.category == category.id}">class="cActive"</c:if> value="${category.id}">${category.categoryDesc}</span>
								</c:if>
							</c:forEach>
						</div>
					</div>
				
					<div class="categoryTip shadow">
						<c:choose>
							<c:when test="${empty requestScope.queryParameter.category or requestScope.queryParameter.category == ''}">
								<p>问卷网，倾听你在乎的！<br>免费的「在线调研·报名表单·考试测评」平台</p>
							</c:when>
							<c:otherwise>
								<p>${requestScope.naireCategoryMap[requestScope.queryParameter.category].categoryBrief}</p>
							</c:otherwise>
						</c:choose>					
					</div>
				</div>
				
				<div class="naire shadow">
					<div class="pCategory">
						<c:forEach items="${requestScope.naireCategoryMap}" var="categoryItem" varStatus="vs">
							<c:set var="category" value="${categoryItem.value}"></c:set>
							<c:if test="${category.parentId == 0}">
								<a href="javascript:void(0);" <c:if test="${category.id == requestScope.queryParameter.parentCategory}">class="pActive"</c:if> value="${category.id}">${category.categoryDesc}模板</a>
							</c:if>
						</c:forEach>
					</div>
					<c:choose>
						<c:when test="${empty requestScope.pageInfo.list}">
							<div class="tip2" style="width:100%;">暂无共享模板</div>
						</c:when>
						<c:otherwise>
							<div class="naireContent">
								<span class="mbbg">推荐模板</span><a href="javascript:void(0);" class="more">更多</a>
								<div class="rTemplate">
									<c:forEach items="${requestScope.recommendedList}" var="rTemplate">
										<div class="single">
											<a href="naire/preview/${rTemplate.id}" target="_blank">${rTemplate.title}</a>
											<span>分类：${rTemplate.category.categoryDesc}</span>
											<span>|</span>
											<span>引用次数：${rTemplate.citationsCount}</span>
											<span>|</span>
											<span>共${fn:length(rTemplate.questionList)}个问题</span>
											<span class="useNaire" value="${rTemplate.id}">使用该模板</span>
										</div>
									</c:forEach>
								</div>
								<hr/>
								<span id="gxmb" class="mbbg">用户共享${requestScope.naireCategoryMap[fn:trim(requestScope.queryParameter.parentCategory)].categoryDesc}，共<font color="#ebb678">${requestScope.pageInfo.total}</font>份数据</span><a href="javascript:void(0);" class="more">更多</a>
								<c:forEach items="${requestScope.pageInfo.list}" var="naire">
									<div class="naireBrief">
										<div>
											<a href="naire/preview/${naire.id}" target="_blank" class="nTitle">${naire.title}</a>
											<span class="qNum">共${fn:length(naire.questionList)}个问题</span>
										</div>
										<div class="naireS">
											<span>分类：${naire.category.categoryDesc}</span>
											<span>|</span>
											<span>作者：${naire.user.nick}</span>
											<span>|</span>
											<span>被引用次数：${naire.citationsCount}</span>
											<span class="useNaire" value="${naire.id}">使用该问卷</span>
										</div>
										<div style="margin-right: 10%;">${naire.naireDesc}</div>
									</div>					
								</c:forEach>
							</div>
							<input type="hidden" id="p_total" value="${requestScope.pageInfo.total}">
							<input type="hidden" id="p_pages" value="${requestScope.pageInfo.pages}">
							<input type="hidden" id="p_pageNum" value="${requestScope.pageInfo.pageNum}">
							<input type="hidden" id="p_pageSize" value="${requestScope.pageInfo.pageSize}">
							<div id="page"></div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		
		<a href="javascript:void(0);" class="goTop" onclick="$('body').animate({scrollTop:0},800);"></a>
		<div class="footer">
			<a href=""><img alt="logo" src="static/image/logo.png"></a>
		</div>
		
	</div>
	
</body>

<script type="text/javascript">
//alert($(".search select option:selected").html());
 
</script>

</html>


