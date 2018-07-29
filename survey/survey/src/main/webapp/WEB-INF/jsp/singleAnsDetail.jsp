<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<title>答卷详情</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="description" content="问卷网">

<link rel="shortcut icon" type="image/x-icon" href="static/image/favicon.ico" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/singleAns.css">

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/pdf/html2canvas.js"></script>
<script type="text/javascript" src="static/js/pdf/jspdf.debug.js"></script>
<script type="text/javascript" src="static/js/pdf/jspdf.min.js"></script>
<script type="text/javascript" src="static/js/singleAns.js"></script>

<style type="text/css">

</style>
</head>

<body>

	<div class="content shadow">
		<h1>答卷详情<a title="点我下载PDF"></a></h1>
		<div class="item info">
			<h3>答题信息</h3>
			<table>
				<tbody>
					<tr>
						<td>答题序号：${index}</td>
						<td id="address">区域：${respondentInfo.address}</td>
						<td>来源：${(fn:indexOf(fn:toLowerCase(respondentInfo.system), 'android') != '-1' || fn:indexOf(fn:toLowerCase(respondentInfo.system), 'ios') != '-1') ? '手机' : '电脑'}</td>
					</tr>
					<tr>
						<td>开始时间：<fmt:formatDate type="date" pattern="yyyy-MM-dd HH:mm:ss" value="${respondentInfo.startTime}" /></td>
						<td>结束时间：<fmt:formatDate type="date" pattern="yyyy-MM-dd HH:mm:ss" value="${respondentInfo.endTime}" /></td>
						<td>答题时长：${respondentInfo.duration}</td>
					</tr>
					<tr>
						<td>浏览器：${respondentInfo.browser}</td>
						<td>操作系统：${respondentInfo.system}</td>
						<td>用户IP：${respondentInfo.ip}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="item detail">
			<h3>答题详情</h3>
			<c:forEach items="${answerList}" var="question" varStatus="vs">
				<div class="qItem">
					<div>Q${vs.count}：${question.questionDesc}【${question.categoryId==1?'单选题':(question.categoryId==2?'多选题':'简答题')}】</div>
					<c:choose>					
						<c:when test="${fn:length(question.optionList) == 0}">
							<div class="error">数据缺失</div>
						</c:when>
						<c:otherwise>
							<c:forEach var="option" items="${question.optionList}">
								<div>${option.optionDesc}</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
		</div>
	</div>
	
	
	
</body>


<script type="text/javascript">

/* $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=${respondentInfo.ip}',function(){
	var address = remote_ip_info.province == remote_ip_info.city ? remote_ip_info.province : remote_ip_info.province + remote_ip_info.city;
	$('#address').html('区域：' + address);
}); */

</script>  

</html>
