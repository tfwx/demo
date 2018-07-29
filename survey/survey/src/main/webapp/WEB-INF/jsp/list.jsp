<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/jquery.page.css">
<link rel="stylesheet" type="text/css" href="static/css/list.css">
<link rel="stylesheet" type="text/css" href="static/css/message.css" />
<link rel="stylesheet" type="text/css" href="static/css/simpleAlert.css" />
<link rel="stylesheet" type="text/css" href="static/css/tooltipster/tooltipster.bundle.min.css" />
<link rel="stylesheet" type="text/css" href="static/css/tooltipster/themes/tooltipster-sideTip-shadow.min.css" />


<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/jquery.page.js"></script>
<script type="text/javascript" src="static/js/cookie.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="static/js/list.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/simpleAlert.js"></script>
<script type="text/javascript" src="static/js/clipboard.min.js"></script>
<script type="text/javascript" src="static/js/tooltipster/tooltipster.bundle.min.js"></script>
<script type="text/javascript" src="static/js/public.js"></script>
</head>

<body>
	<div class="main">
		<div class="list_head">
			<div class="list_title">
				<h1>
					<a href="">问卷调查</a>
				</h1>
				<div class="head_nav">
					<a class="active" href="naire/list">我的</a>
					<a href="naire/lib">模板库</a>
					<a href="javascript:void(0);">帮助</a>
				</div>
				
				<div class="userHead">
					<c:choose>
						<c:when test="${empty sessionScope.user.icon}">
							<img class="img" src="static/image/index1.jpg" alt="头像">
						</c:when>
						<c:otherwise>
							<img class="img" src="${sessionScope.user.icon}" alt="头像">
						</c:otherwise>
					</c:choose>
					<a title="${sessionScope.user.nick}">${sessionScope.user.nick}</a>
				</div>
			</div>
		</div>
		<div class="userNav">
			<a href="user/settings">我的账户</a>
			<a href="user/logout">退出</a>
		</div>
		<marquee scrolldelay="100" onmouseover="this.stop();" onmouseout="this.start();" >
			<span><i></i>快速制作网络问卷、收集数据并生成精美报表，助你完成客户满意度调查、员工满意度调查、360度员工测评、用户态度与行为研究、产品测试、投票评选...&nbsp;&nbsp;&nbsp;&nbsp;<a href="naire/new">立即前往</a></span>
			<span><i></i>报名表、登记表、反馈表、邀请函、网络订单等各类表单都能用问卷网便捷地制作，发布后还能助你快速完成数据收集和分析...</span>
			<span><i></i>轻松制作一份能自动算分的试卷，适用于培训考试、教学测验、员工考核等等，趣味测试、心理测试也不在话下...</span>
		</marquee>

		<div class="list_sel">

			<div class="list_sear" id="top">				
				<a href="javascript:void(0);" class="show_style"><i class="box_icon <c:if test="${empty cookie.listStyle.value or cookie.listStyle.value == 'box'}">iconActive</c:if>"></i></a>
				<a href="javascript:void(0);" class="show_style"><i class="list_icon <c:if test="${cookie.listStyle.value == 'list'}">iconActive</c:if>"></i></a>
				<select id="category">
					<option value="">全部类型</option>
					<c:forEach items="${requestScope.naireCategoryList}" var="category">
					<c:if test="${category.parentId == 0}">
					<option value="${category.id}">${category.categoryDesc}</option>
					</c:if>
					</c:forEach>
				</select>
				<select id="order">
					<option value="-1">从新到旧</option>
					<option value="1">从旧到新</option>
				</select>					
				<a href="naire/new" class="new_create shadow">+ 新建</a>
				
				<div class="search">
					<input type="text" id="searchContent" class="txt" placeholder=" 输入标题搜索" onkeypress="if(event.keyCode==13){$('#search').trigger('click');}" />
					<a href="javascript:void(0);" class="search_btn" id="search"></a>
				</div>
				<a class="btn_recycle">回收站</a>
			</div>
			
			<div id="listContent">
				<div>
					<c:choose>
						<c:when test="${requestScope.pageInfo.total==0}">
							<div class="list_content_style_box <c:if test="${empty cookie.listStyle.value or cookie.listStyle.value == 'box'}">show</c:if>">
								<table>
									<tr>
										<td>
											<a href="naire/new">
												<j></j><span>新 建</span>
											</a>
										</td>
										<td style="visibility: hidden;"></td>
										<td style="visibility: hidden;"></td>
										<td style="visibility: hidden;"></td>
									</tr>
								</table>
							</div>
								
							<!-- <div class="tip">
								还未发布任何问卷
							</div> -->
						</c:when>
						<c:otherwise>
							<div class="list_content_style_box <c:if test="${empty cookie.listStyle.value or cookie.listStyle.value == 'box'}">show</c:if>">
								<table>
									<tr>
										<td>
											<a href="naire/new">
												<j></j><span>新 建</span>
											</a>
										</td>
										
										<c:forEach items="${requestScope.pageInfo.list}" var="naire" varStatus="vs">
										
										<td>
											<c:set var="cpId" value="${naire.category.parentNode.id}"></c:set>
											<i <c:if test="${cpId == 1}">class="blue"</c:if><c:if test="${cpId == 10}">class="green"</c:if><c:if test="${cpId == 18}">class="purple"</c:if>>${naire.category.parentNode.categoryDesc}</i>									
											<a href="naire/edit/${naire.id}" class="box_top" title="${naire.title}">
												<span class="title">${naire.title}</span>
												<span class="date"><fmt:formatDate value="${naire.createTime}" type="date" /></span>
											</a>									
											<div class="box_bottom">
											
												<div style="height:100%;" <c:if test="${naire.status==1}">class="unpublished"</c:if><c:if test="${naire.status==2}">class="collecting"</c:if><c:if test="${naire.status==3}">class="over"</c:if>>
													<b class="vCenter"></b>
													<span class="status vCenter">${naire.status==1?'未发布':(naire.status==2?'收集中':'已结束')}</span>
													<span class="count vCenter">${naire.collectCount}份数据</span>
												</div>
												
												<div class="tool">
													<a href="naire/preview/${naire.id}" target="_blank" class="icon preview vCenter" title="预览"></a>
													<a href="javascript:void(0);" onclick="copyAddress('<%=basePath%>naire/preview/${naire.id}')" class="icon copy vCenter" title="收集"></a>
													<a href="naire/statistics/${naire.id}" class="icon statistics vCenter" title="数据报表"></a>
													<a href="naire/download/${naire.id}" target="_blank" class="icon download vCenter" title="下载"></a>
													<a href="javascript:void(0);" onclick="recycleNaire(${naire.id})" class="icon delete vCenter" title="删除"></a>
												</div>
											</div>
										</td>
										
										<c:if test="${(vs.index+2)%4==0}">
											</tr><tr>
										</c:if>
										</c:forEach>
										
										<c:if test="${fn:length(requestScope.pageInfo.list) < 3}">
											<c:forEach begin="1" end="${4-fn:length(requestScope.pageInfo.list)}">
												<td style="visibility: hidden;"></td>
											</c:forEach>
										</c:if>
									</tr>
								</table>					
							</div>
						
						
							<div class="list_content_style_list shadow <c:if test="${cookie.listStyle.value == 'list'}">show</c:if>">
								<span style="margin-left: -60px;max-width: 30%; text-align: left;padding-left: 10px;">标题</span> <span>类型</span>
								<span>收集状态</span> <span>收到数据</span> <span>创建时间</span> <span>修改时间</span>
								<span style="text-align: left;">操作</span>
								<hr />
								<div style="width: 100%;" id="naireList">
									<c:forEach items="${requestScope.pageInfo.list}" var="naire">
										<div id="content${naire.id}" class="single">
											<span style="max-width: 30%; text-align: left;padding-left: 10px;">
												<a href="naire/edit/${naire.id}">
													${naire.title}
												</a>
											</span>
											<span>${naire.category.categoryDesc}</span> 
											<span>
												<select id="status${naire.id}" onchange="changeStatus(${naire.id})">
													<option value="1" <c:if test="${naire.status == 1}">selected="selected"</c:if>>未发布</option>
													<option value="2" <c:if test="${naire.status == 2}">selected="selected"</c:if>>收集中</option>
													<option value="3" <c:if test="${naire.status == 3}">selected="selected"</c:if>>已结束</option>
												</select>
											</span> 
											<span>${naire.collectCount}</span> 
											<span>
												<fmt:formatDate value="${naire.createTime}" type="date" />
											</span> 
											<span name="endTime${naire.id}"><fmt:formatDate value="${naire.endTime}" type="date" /></span>
											<span style="max-width: 15%;text-align: left;">
												<a href="naire/preview/${naire.id}" target="_blank" class="icon preview" title="预览"></a>
												<a href="javascript:void(0);" onclick="copyAddress('<%=basePath%>naire/preview/${naire.id}')" class="icon copy" title="收集"></a>
												<a href="naire/statistics/${naire.id}" class="icon statistics" title="数据报表"></a>
												<a href="naire/download/${naire.id}" target="_blank" class="icon download" title="下载"></a>
												<a href="javascript:void(0);" onclick="recycleNaire(${naire.id})" class="icon delete" title="删除"></a>
											</span>
											<hr />
										</div>
									</c:forEach>
								</div>						
							</div>
						</c:otherwise>
					</c:choose>
					<input type="hidden" id="p_total" value="${requestScope.pageInfo.total}">
					<input type="hidden" id="p_pages" value="${requestScope.pageInfo.pages}">
					<input type="hidden" id="p_pageNum" value="${requestScope.pageInfo.pageNum}">
					<input type="hidden" id="p_pageSize" value="${requestScope.pageInfo.pageSize}">
				</div>
			</div>
		</div>
	</div>
	
	<footer>
		<div id="page"></div>
		<a href=""><img style="" alt="" src="static/image/logo.png"></a>
	</footer>
	
	<div class="mask">
		<div class="fore">
			<div class="title">
				<h2>回收站</h2>
				<span onclick="$('.mask').hide();">&times;</span>
			</div>
			<hr/>
			<div class="content">
				<table>
					<tr>
						<th style="width: 45%;"><input id="selectAll" type="checkbox">标题</th>
						<th>收集数据</th>
						<th>创建时间</th>
						<th>删除时间</th>
					</tr>
					<!-- <tr>
						<td><input type="checkbox" name="recycleBox" id="recycle_">普通话调查</td>
						<td>100</td>
						<td>2017-9-18</td>
						<td>2018-2-3</td>
					</tr> -->
				</table>
			</div>
			<hr/>
			<div class="bottomBtn">
				<input type="button" value="恢复" operation="restore">
				<input type="button" value="删除" operation="delete">
			</div>
		</div>
	</div>
	
</body>


<script type="text/javascript">
	
	
</script>

</html>
