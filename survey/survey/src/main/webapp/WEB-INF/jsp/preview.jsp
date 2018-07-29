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

<title>${empty requestScope.naire.category.parentNode.categoryDesc ? '问卷' : requestScope.naire.category.parentNode.categoryDesc} - ${requestScope.naire.title}</title>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="description" content="问卷网">
<meta name="referrer" content="never">

<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/message.css" />
<link rel="stylesheet" type="text/css" href="static/css/tooltipster/tooltipster.bundle.min.css" />
<link rel="stylesheet" type="text/css" href="static/css/tooltipster/themes/tooltipster-sideTip-shadow.min.css" />
<link rel="stylesheet" type="text/css" href="static/css/simpleAlert.css" />
<link rel="stylesheet" type="text/css" href="static/css/pretty.css"/>
<link href="static/css/mobile/preview.css?u=<%=Math.random()%>" rel="stylesheet" type="text/css" media="screen and (max-width: 980px)">
<link href="static/css/preview.css?u=<%=Math.random()%>" rel="stylesheet" type="text/css" media="screen and (min-width: 981px)">

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="static/js/tooltipster/tooltipster.bundle.min.js"></script>
<script type="text/javascript" src="static/js/simpleAlert.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/localStorage.js"></script>
<script type="text/javascript" src="static/js/preview.js?u=<%=Math.random()%>"></script>



<style type="text/css">

</style>
</head>

<body>
	<div class="bg" >
		
		<header>
			<div class="progress shadow" hidden>
				<div id="progress" qCount="${fn:length(requestScope.naire.questionList)}"></div>
			</div>
		
			<div class="header shadow" style="margin-top:30px;">
				<div class="content">
					<span>请勿将当前页发送给他人填写!</span>
					<div class="tool">
						<div><i></i>导出问卷</div>
						<div data-tooltip-content="#qrcode"><j></j>手机预览</div>
						<!-- <a href="javascript:void(0);" download="二维码海报.png" title="点我下载二维码海报"></a> -->
					</div>
				</div>
			</div>
		
			<div id="qrcode" class="qrcode">
				<div id="code" style="margin-top: 10px;margin-bottom: 20px;"></div>
				<span>扫描二维码，<br/>访问手机答题预览页</span>
			</div>
		</header>
		
		<c:set var="phoneStyles" value="${fn:split('beach,city,office', ',')}" />
		<div class="cover ${phoneStyles[requestScope.naire.phoneStyle]}">
			<div class="wj_title">
				<h1>${requestScope.naire.title}</h1>
				<h5>${requestScope.naire.naireDesc}</h5>
				<i></i>
			</div>
		</div>
		
		<div class="content">
			<div class="tip" align="center">
				请选择一个选项
			</div>
			<div>
				<c:if test="${not empty requestScope.naire}">
					<input id="naireId" type="hidden" value="${requestScope.naire.id}">
					<input id="naireStatus" type="hidden" value="${requestScope.naire.status}">
					<input id="naireSubject" type="hidden" value="${requestScope.naire.subject}">
	
					<div id="contentHeader">
						<h1>${requestScope.naire.title}</h1>
						<div class="desc">${requestScope.naire.naireDesc}</div>
						<hr />
					</div>
					<c:forEach items="${requestScope.naire.questionList}" var="question" varStatus="vs">
						<div index="${vs.index+1}" type="${question.category.categoryDesc}">
							<span hidden>0</span>
							<div class="question">${vs.index+1}.${question.questionDesc}</div>
							<c:if test="${question.category.id == 3}">
								<textarea wjw name="${question.id}"></textarea>
							</c:if>
							<c:if test="${question.category.id != 3}">
								<div class="box">
									<c:forEach items="${question.optionList}" var="option" varStatus="vs2">
										<div class="option pretty">
											<input wjw id="rc${vs.index}${vs2.index}" <c:if test="${fn:length(question.optionList) == 1}">checked="checked"</c:if> type="${question.category.categoryDesc}" name="${question.id}" value="${option.id}"> 
										  	<label for="rc${vs.index}${vs2.index}"><i class="default"></i><div style="float: right;white-space: pre-line;word-break: break-all;line-height: 1.1;">${option.optionDesc}</div></label>
										</div>
									</c:forEach>
								</div>
							</c:if>
						</div>
					</c:forEach>
					<c:if test="${requestScope.isSubmit == 1}">
						<button class="sub_btn shadow">提 交</button>
					</c:if>
				</c:if>
			</div>
		</div>
		<a href="javascript:void(0);" class="report">举报</a>
		<div class="footer">
			<a href=""><img alt="" src="static/image/logo.png"></a>
		</div>
	</div>
</body>


<script type="text/javascript">  
/* function quickHideAddressBar() {
	setTimeout(function() {
		if(window.pageYOffset !== 0) return;
		window.scrollTo(0, window.pageYOffset + 1);

	}, 1000);

} */



</script>  


</html>
