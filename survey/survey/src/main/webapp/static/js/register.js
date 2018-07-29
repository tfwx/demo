// 电话号码验证规则
var phonePattern = /^0?1[3|4|5|8][0-9]\d{8}$/;

$(function(){
	
	initVerifyCode();
	initRegister();
	
});

// 注册信息验证
function check() {
	var flag = true;
	
	// 空格正则表达式
	var spaceRegexp = /^\s*$/;

	var nick = $("#nick").val();
	var phone = $("#phone").val();
	var password = $("#password").val();


	if (spaceRegexp.test(nick)) {
		$("#wNick").css("visibility", "visible");
		$("#nick").trigger("focus");
		flag = false;
	} else {
		$("#wNick").css("visibility", "hidden");
	}

	if (phonePattern.test(phone)) {
		$("#wPhone").css("visibility", "hidden");
	} else {
		$("#wPhone").css("visibility", "visible");
		if (flag) {
			$("#phone").trigger("focus");
		}
		flag = false;
	}

	if (spaceRegexp.test(password)) {
		$("#wPassword").attr("style", "visibility: visible;");
		if (flag) {
			$("#password").trigger("focus");
		}
		flag = false;
	} else {
		$("#wPassword").attr("style", "visibility: hidden;");
	}
	
	if (!flag) {
		warnTip('请正确填写注册信息！');
	}
	
	if (!/^\d{6}$/.test($("#verifyCode").val())) {
		warnTip("验证码格式错误！");
		flag = false;
	}

	return flag;
}

// 初始化验注册事件
function initRegister() {
	$(".register_btn").on("click", function(){
		if (!check()) {
			return;
		}

		// 封装数据
		var data = {
			phone : $("#phone").val(),
			nick : $("#nick").val(),
			password : $("#password").val(),
		};

		// 注册请求
		$.ajax({
			url : 'user/register/' + $("#verifyCode").val(),
			type : 'post',
			timeout : 2000,
			data : JSON.stringify(data),
			dataType : "json",
			contentType : 'application/json',
			success : function(data) {
				if (data.status == 200) {
					successTip("注册成功！");
				    setTimeout(function(){
				    	window.close();
				    }, 1000);
				} else {
					warnTip(data.msg);
				}
			},
			error : function() {
				errorTip('服务器异常！');
			},
			beforeSend : function() {
				$(".register_btn").attr("disabled", true);
			},
			complete: function() {
				$(".register_btn").attr("disabled", false);
			}
		});
	});
}

// 标记是否已经成功申请了验证码
var isReqCode = false;

// 初始化验证码
function initVerifyCode() {
	// 正确输入手机号码之后，才可以发送验证码
	$("#phone").on("blur", function() {
		if (phonePattern.test($("#phone").val())) {
			if (isReqCode) {
				// 如果没申请验证码且用户输入的手机号码格式正确，才可以申请验证码
				warnTip("验证码已发送！");
			}
		} else {
			warnTip("手机号码格式错误！");
		}
	});
	
	$(".code").on("click", function() {
		// 验证手机号
		var phone = $("#phone").val();
		if (!phonePattern.test(phone)) {
			warnTip("请正确输入手机号码！");
			$("#phone").trigger("focus");
			return;
		}
		$.ajax({
			url : 'user/sendcode',
			type : 'post',
			timeout : 3000,
			data : {phone: phone},
			dataType : "json",
			error : function(XMLHttpRequest) {
				errorTip('服务器异常！');
			},
			success : function(result) {
				if (result.status == 200) {
					// 申请验证码成功
					setTimer();
					successTip("验证码已发送，请查收！");					
				} else {
					warnTip(result.msg);
				}
			}
		});
	});
	
}

// 60s后可重新发送验证码
function setTimer() {
	isReqCode = true;
	var code = $(".code");
	code.attr("disabled", true);
	var s = 60;
	code.val("重新发送" + --s);
	var timer = setInterval(function() {
		code.val("重新发送" + --s);
		if (s == 0) {
			clearInterval(timer);
			code.attr("disabled", false);
			code.val("获取验证码");
			isReqCode = false;
		}
	}, 1000);
}
