<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户设置</title>
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/upload/font-awesome.4.6.0.css">
<link rel="stylesheet" type="text/css" href="static/css/upload/amazeui.min.css">
<link rel="stylesheet" type="text/css" href="static/css/upload/amazeui.cropper.css">
<link rel="stylesheet" type="text/css" href="static/css/upload/custom_up_img.css">
<link rel="stylesheet" type="text/css" href="static/css/userSettings.css">
<link rel="stylesheet" href="static/css/message.css" />

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/userSettings.js"></script>
<script type="text/javascript" src="static/js/upload/amazeui.min.js"></script>
<script type="text/javascript" src="static/js/upload/cropper.min.js"></script>
<script type="text/javascript" src="static/js/upload/custom_up_img.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/public.js"></script>
<script type="text/javascript" src="static/js/localStorage.js"></script>


<style type="text/css">
body {
	background-color: #EEEFF1;
}
</style>

</head>
<body>

	<input type="hidden" id="userId" value="${sessionScope.user.id}">
	<div class="header">
		<h1>我的账户</h1>
		<a href="naire/list"><img alt="返回" src="static/image/back.png"></a>
	</div>

 	<div class="content shadow">
		<font size="5">基本信息</font>
		<hr/>
		<div>
			<c:choose>
				<c:when test="${empty sessionScope.user.icon}">
					<img class="headIcon" src="static/image/index1.jpg" alt="头像">
				</c:when>
				<c:otherwise>
					<img class="headIcon" src="${sessionScope.user.icon}" alt="头像">
				</c:otherwise>
			</c:choose>
			<div class="changeIcon" id="up-img-touch">更换头像</div>
		</div>
		<div>
			<span>用户名：</span><c id="userNick">${sessionScope.user.nick}</c>
			<a href="javascript:void(0)" id="updateNick">修改</a>
		</div>
		<div>
			<span>手机号：</span><c id="userPhone">${sessionScope.user.phone}</c>
			<a href="javascript:void(0)" id="updatePhone">修改</a>
		</div>
		<div>
			<span>密码：</span>******
			<a href="javascript:void(0)" id="updatePassword">修改</a>
		</div>
	</div>
	
	<div class="footer">
		<a href=""><img alt="logo" src="static/image/logo.png"></a>
	</div>
	 
	<div class="grayBg">
		<div class="updateAlert">
			<div class="title">修改</div>
			<div class="close">&times;</div>
			<hr/>
			
			<div class="alertContent">
				<div>
					<span>新用户名</span>
					<input type="text" id="newNick">
				</div>
				<div>
					<span>新手机号码</span>
					<input type="text" id="newPhone">
					<br/>
					<span>输入验证码</span>
					<input type="text" id="verifyCode" style="width: 38%;">
					<input id="code" type="button" value="获取验证码" style="margin-left: -10px;width: 20%;padding-right: 10px;background-color: #fff;color: #56a5f1;font-weight: bold;">
				</div>				
				<div>
					<span>新密码</span><input type="password" id="newPwd1">
					<br/>
					<span>重复输入</span><input type="password" id="newPwd2">
				</div>				
			</div>
			<div class="ok">保存</div>
		</div>
	</div>



	<!--图片上传框-->
	<div class="am-modal am-modal-no-btn up-frame-bj" tabindex="-1"
		id="doc-modal-1">
		<div class="am-modal-dialog up-frame-parent up-frame-radius">
			<div class="am-modal-hd up-frame-header">
				<label>修改头像</label> <a href="javascript: void(0)" id="close"
					class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd  up-frame-body">
				<div class="am-g am-fl">
					<div class="am-form-group am-form-file">
						<div class="am-fl">
							<button type="button" class="am-btn am-btn-default am-btn-sm">
								<i class="am-icon-cloud-upload"></i> 选择要上传的文件
							</button>
						</div>
						<input type="file" id="inputImage" accept="image/*">
					</div>
				</div>
				<div class="am-g am-fl">
					<div class="up-pre-before up-frame-radius">
						<img alt="" src="" id="image">
					</div>
					<div id="aim" class="up-pre-after up-frame-radius"></div>
				</div>
				<div class="am-g am-fl">
					<div class="up-control-btns">
						<span class="am-icon-rotate-left" onclick="rotateimgleft()"></span>
						<span class="am-icon-rotate-right" onclick="rotateimgright()"></span>
						<span class="am-icon-check" id="up-btn-ok" url="user/uploadIcon"></span>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!--加载框-->
	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1"
		id="my-modal-loading">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">正在上传...</div>
			<div class="am-modal-bd">
				<span class="am-icon-spinner am-icon-spin"></span>
			</div>
		</div>
	</div>

	<!--警告框-->
	<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">信息</div>
			<div class="am-modal-bd" id="alert_content">成功了</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn">确定</span>
			</div>
		</div>
	</div>

</body>

<script type="text/javascript">
</script>
</html>