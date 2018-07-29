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

<title>问卷导入</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="data statistics">
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="static/css/pretty.css">
<link rel="stylesheet" type="text/css" href="static/css/message.css">
<link rel="stylesheet" type="text/css" href="static/css/simpleAlert.css" />
<link rel="stylesheet" type="text/css" href="static/css/import.css">
<link rel="stylesheet" type="text/css" href="static/css/public.css">

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/simpleAlert.js"></script>
<script type="text/javascript" src="static/js/import.js"></script>
<script type="text/javascript" src="static/js/public.js"></script>

</head>

<body>
	<input id="c" type="hidden" value="${empty requestScope.category ? 9 : requestScope.category}" />
	<header>
		<a href="javascript:history.go(-1);"><i></i><span>返回</span></a>
	</header>
	
	<div class="content">
		<div class="data">
			<label>请参考示例将题目文本粘贴到下面编辑框中</label>
			<a href="javascript:void(0);" onclick="javascript:$('.mask').show();">查看帮助</a>
			<textarea>
问卷调研

感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！

您经常创建什么类型的项目？[单选题]
问卷调研
表单报名
测评考试

您使用过问卷网哪款应用？[多选题]
二维码海报
第三方平台分享
模板引用
问卷下载
来源统计
自动导入
问卷检索
数据分析

您的姓名：[简答题]

请填写您的问题和建议：[简答题]
			</textarea>
			
			<button id="createNaire" class="shadow">生成问卷</button>
			<input id="file" type="file" accept="text/plain, application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document"/>
			<a href="javascript:void(0);" style="" onclick="$(this).prev().trigger('click');">本地选取</a>
		</div>
		<div class="preview">
			<i></i>
			<div class="previewContent">
				<!-- <h3>问卷网调研</h3>
				<label>感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！</label>
				<div class="box">
					<label>1、您经常创建什么类型的项目</label>
					<div class="item">
						<input type="radio"><label>问卷调研</label>
					</div>
					<div class="item">
						<input type="radio"><label>表单报名</label>
					</div>
					<div class="item">
						<input type="radio"><label>测评考试</label>
					</div>
				</div>
				<div class="box">
					<label>2、您使用过问卷网哪款应用？</label>
					<div class="item">
						<input type="checkbox"><label>抽奖</label>
					</div>
					<div class="item">
						<input type="checkbox"><label>配额</label>
					</div>
					<div class="item">
						<input type="checkbox"><label>投票墙</label>
					</div>
					<div class="item">
						<input type="checkbox"><label>微信签到</label>
					</div>
					<div class="item">
						<input type="checkbox"><label>自定义域名</label>
					</div>
					<div class="item">
						<input type="checkbox"><label>限定手机号答题</label>
					</div>
				</div>
				<div class="box">
					<label>3、您的姓名：</label>
					<div class="item">
						<textarea></textarea>
					</div>
				</div>
				<div class="box">
					<label>4、请填写您的问题和建议：</label>
					<div class="item">
						<textarea></textarea>
					</div>
				</div>
			</div> -->
		</div>
	</div>

	<div class="mask">
		<div class="fore">
			<div class="title">
				<h2>帮助提示</h2>
				<span onclick="$('.mask').hide();">&times;</span>
			</div>
			<hr/>
			<div class="content">
				<h4>一、格式说明</h4>
				<p>1.请在每个题目名称后添加标识。</p>
				<p>2.目前可以识别题目标识为：[单选题]、[多选题]、[简答题]。</p>
				<h4>二、题型说明</h4>
				<table border="1" bordercolor="#ccc">
					<thead>
						<tr>
							<td>题型</td>
							<td>编辑文本</td>
							<td>预览题目</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>单选题</td>
							<td>
								<div class="box" style="text-align: left;margin-top: 0px;">
									<label>1、您经常创建什么类型的项目[单选题]</label>
									<div class="item">
										<label>问卷调研</label>
									</div>
									<div class="item">
										<label>表单报名</label>
									</div>
									<div class="item">
										<label>测评考试</label>
									</div>
								</div>
							</td>
							<td>
								<div class="box" style="text-align: left;margin-top: 0px;">
									<label>1、您经常创建什么类型的项目</label>
									<div class="item pretty">
										<input type="radio" name="radio" id="radio1">
										<label for="radio1"><i class="default"></i>问卷调研</label>
									</div>
									<div class="item  pretty">
										<input type="radio" name="radio" id="radio2">
										<label for="radio2"><i class="default"></i>表单报名</label>
									</div>
									<div class="item pretty">
										<input type="radio" name="radio" id="radio3">
										<label for="radio3"><i class="default"></i>测评考试</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>多选题</td>
							<td>
								<div class="box" style="text-align: left;margin-top: 0px;">
									<label>2、您使用过问卷网哪款应用？[多选题]</label>
									<div class="item">
										<label>二维码海报</label>
									</div>
									<div class="item">
										<label>第三方平台分享</label>
									</div>
									<div class="item">
										<label>模板引用</label>
									</div>
									<div class="item">
										<label>问卷下载</label>
									</div>
									<div class="item">
										<label>来源统计</label>
									</div>
									<div class="item">
										<label>自动导入</label>
									</div>
									<div class="item">
										<label>问卷检索</label>
									</div>
									<div class="item">
										<label>数据分析</label>
									</div>
								</div>
							</td>
							<td>
								<div class="box" style="text-align: left;margin-top: 0px;">
									<label>2、您使用过问卷网哪款应用？</label>
									<div class="item pretty">
										<input type="checkbox" id="checkbox1">
										<label for="checkbox1"><i class="default"></i>二维码海报</label>
									</div>
									<div class="item pretty">
										<input type="checkbox" id="checkbox2">
										<label for="checkbox2"><i class="default"></i>第三方平台分享</label>
									</div>
									<div class="item pretty">
										<input type="checkbox" id="checkbox3">
										<label for="checkbox3"><i class="default"></i>模板引用</label>
									</div>
									<div class="item pretty">
										<input type="checkbox" id="checkbox4">
										<label for="checkbox4"><i class="default"></i>问卷下载</label>
									</div>
									<div class="item pretty">
										<input type="checkbox" id="checkbox5">
										<label for="checkbox5"><i class="default"></i>来源统计</label>
									</div>
									<div class="item pretty">
										<input type="checkbox" id="checkbox6">
										<label for="checkbox6"><i class="default"></i>自动导入</label>
									</div>
									<div class="item pretty">
										<input type="checkbox" id="checkbox7">
										<label for="checkbox7"><i class="default"></i>问卷检索</label>
									</div>
									<div class="item pretty">
										<input type="checkbox" id="checkbox8">
										<label for="checkbox8"><i class="default"></i>数据分析</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>简答题</td>
							<td>
								<div class="box" style="text-align: left;margin-top: 0px;">
									<label>3、您的姓名：[简答题]</label>
								</div>
							</td>
							<td>
								<div class="box" style="text-align: left;margin-top: 0px;">
									<label>3、您的姓名：</label>
									<div class="item">
										<textarea></textarea>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>

<script>







</script>
</html>





