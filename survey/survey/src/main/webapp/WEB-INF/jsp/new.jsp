<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>分类选择</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="data statistics">
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="static/css/message.css">
<link rel="stylesheet" type="text/css" href="static/css/simpleAlert.css" />
<link rel="stylesheet" type="text/css" href="static/css/new.css">
<link rel="stylesheet" type="text/css" href="static/css/public.css">

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/simpleAlert.js"></script>

</head>

<body>
	<header>
		<a href="javascript:history.go(-1);"><i></i><span>返回</span></a>
	</header>
	<div>
		<c:set var="style" value="${fn:split('blue,green,purple', ',')}" />
		<c:set var="index" value="0"></c:set>
		<c:forEach items="${requestScope.naireCategoryList}" var="parentCategory">
			<c:if test="${parentCategory.parentId == 0}">
				<div class="item shadow ${style[index]}">
					<c:set var="index" value="${index+1}"></c:set>
					<div>
						<h1>${parentCategory.categoryDesc}</h1>
					</div>
					<div>
						<c:set var="count" value="0"></c:set>
						<c:set var="isOutput" value="0"></c:set>
						<c:forEach items="${requestScope.naireCategoryList}" var="childCategory">
							<c:if test="${childCategory.parentId == parentCategory.id}">
								<c:set var="count" value="${count+1}"></c:set>
								<c:choose>
									<c:when test="${count < 7}">
										<a href="naire/edit?p=${parentCategory.id}&c=${childCategory.id}">${childCategory.categoryDesc}</a>
									</c:when>
									<c:otherwise>
										<c:if test="${isOutput == 0}">
											<a href="javascript:void(0);">...</a>
										</c:if>
										<c:set var="isOutput" value="1"></c:set>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
					</div>
					<a href="naire/edit?p=${parentCategory.id}">创建</a>
				</div>
			</c:if>
		</c:forEach>
	</div>
	
</body>

<script>
</script>
</html>