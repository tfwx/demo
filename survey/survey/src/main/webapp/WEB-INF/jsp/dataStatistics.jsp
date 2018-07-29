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

<title>数据报表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="data statistics">
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />


<link rel="stylesheet" type="text/css" href="static/css/statistics.css">
<link rel="stylesheet" type="text/css" href="static/css/message.css">
<link rel="stylesheet" type="text/css" href="static/css/simpleAlert.css" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/l_nav.css">

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/public.js"></script>
<script type="text/javascript" src="static/js/statistics.js?i=<%=Math.random()%>"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/simpleAlert.js"></script>
<script type="text/javascript" src="static/js/clipboard.min.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>

<script type="text/javascript" src="static/js/echarts/echarts3.8.min.js"></script>
<script type="text/javascript" src="static/js/echarts/china.js"></script>
<script type="text/javascript" src="static/js/echarts/echarts.common.min.js"></script>
<script type="text/javascript" src="static/js/echarts/echarts-l.js"></script>
<script type="text/javascript" src="static/js/echarts/echarts-liquidfill.js"></script>

<script type="text/javascript" src="static/js/util.js"></script>

<style>


</style>

</head>

<body>

	<input type="hidden" id="naireId" value="${naire.id}">
	<input type="hidden" id="collectCount" value="${naire.collectCount}">

	<div class="left_nav">
		<a href="naire/list"><div><img src="static/image/back.png" /></div></a>
		<a href="naire/edit/${naire.id}"><div><img src="static/image/editnaire.png" /><span>编辑问卷</span></div></a>		
		<a class="active" href="javascript:void(0);"><div><img src="static/image/datareport.png" /><span>数据报表</span></div></a>
		<a href="javascript:void(0);"><div><img src="static/image/collect.png" /><span>收集问卷</span></div></a>
	</div>

	<div class="main">
		<div class="header">
			<div class="title">${naire.title}</div>
			<div class="operation">
				<a id="export" href="javascript:void(0);" class="shadow" target="_blank">导出数据</a>
				<a href="javascript:void(0);" class="shadow" onclick="copyAddress('<%=basePath%>naire/preview/${naire.id}')">开始收集</a>
			</div>
			<div class="num">
				当前已收集问卷<span>${naire.collectCount}份</span>
			</div>
		</div>
		<div class="bar">
			<label class="lastTime">最后更新时间：<fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${naire.endTime}" /></label>
			<a href="naire/preview/121" target="_blank">倾听您的声音，点击参与用户调研！</a>
			<div class="option"><span>|</span><a id=pie content="data_chart">玫瑰图</a><span>|</span><a id="bar" content="data_chart">柱状图</a><span>|</span><a id=ansDetail content="ans_list">答卷详情</a><span>|</span><a id="area" content="area_chart">来源概览</a></div>
		</div>
		<div class="data_chart content"></div>
		<div class="area_chart content">
			<div class="chart" id="areaChart"></div>
		</div>
		<div class="ans_list content">
			<table border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td>答题序号</td>
						<td>开始时间</td>
						<td>结束时间</td>
						<td>答题时长</td>
						<td>来源</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<%-- <c:forEach begin="1" end="50" varStatus="vs">
					<tr>
						<td>${vs.count}</td>
						<td>2018-4-4 12:50</td>
						<td>2018-4-5 13:00</td>
						<td>10分钟</td>
						<td>电脑</td>
						<td><a href="javascript:void(0);">查看</a><a href="javascript:void(0);">删除</a></td>
					</tr>
					</c:forEach> --%>
				</tbody>
			</table>
		</div>
	</div>
	
	
	<div class="reset">
		<i></i>
		<a>重置</a>
	</div>

</body>

<script>

</script>
</html>





