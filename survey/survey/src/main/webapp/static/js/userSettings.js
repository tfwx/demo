var spaceRegexp = /^\s*$/;
var phoneRegexp = /^0?1[3|4|5|8][0-9]\d{8}$/;
var timer;

$(function() {
	// 弹出框内容div下标
	var alertIndex = -1;
	// 弹出框事件
	$("#updateNick, #updatePhone, #updatePassword").on("click", function() {
		$(".grayBg").show();
		alertIndex = $(this).parent().index()-2;
		// 先隐藏所有子元素， 再显示目标子元素
		$(".alertContent").children().hide().eq(alertIndex).show();;
		
		// 填充旧数据
		if (alertIndex == 0) {
			$("#newNick").val($("#userNick").html());
		} else if (alertIndex == 1) {
			$("#newPhone").val($("#userPhone").html());
			$("#verifyCode").val("");
		}
	});

	$(".updateAlert .ok").on("click", function() {
		var data;
		switch (alertIndex) {
			case 0:
				data = updateNick();
				break;
			case 1:
				data = updatePhone();
				break;
			case 2:
				data = updatePassword();
				break;
			default:
				return;
		}

		if (data == false) {
			return;
		} else {
			data.id = $("#userId").val();
		}

		// 发出更新请求
		$.ajax({
			url : 'user/update',
			type : 'post',
			timeout : 2000,
			data : data,
			dataType : "json",
			error : function(XMLHttpRequest) {
				console.log(JSON.stringify(XMLHttpRequest));
				errorTip('服务器异常！');
			},
			success : function(result) {
				if (result.status == 200) {
					if (data.phone != undefined) {
						setLocalStorage("userPhone", data.phone);
					}
					infoTip("修改成功！");
					refresh(alertIndex);
				} else {
				//	console.log(result.msg);
					warnTip(result.msg);
				}
			},
			beforeSend:function() {
				showLoading();
			},
			complete:function() {
				hideLoading();
			}
		});

	});


	// 修改框关闭事件
	$(".updateAlert .close").on("click", function() {
		$(".grayBg").hide();
		if (timer != undefined) {
			resetCodeTimer();
		}
	});
	
	
	initVerifyCode();

});

// 重置验证码等待的计时器
function resetCodeTimer() {
	clearInterval(timer);
	var code = $("#code");
	code.attr("disabled", false);
	code.val("获取验证码");
}

//更新昵称
function updateNick() {
	var newNick = $("#newNick").val();
	if (spaceRegexp.test(newNick)) {
		warnTip("请输入新昵称！");
		return false;
	}
	// 封装数据
	var data = {
		nick : newNick
	};
	return data;
}

//更新手机号码
function updatePhone() {
	var newPhone = $("#newPhone").val();
	if (spaceRegexp.test(newPhone)) {
		$("#newPhone").trigger("focus");
		warnTip("请输入手机号码！");
		return false;
	}
	if (!phoneRegexp.test(newPhone)) {
		$("#newPhone").trigger("focus");
		warnTip("手机号码格式不正确！");
		return false;
	}
	
	var oldPhone = $("#userPhone").html();
	if (newPhone == oldPhone) {
		$("#newPhone").trigger("focus");
		warnTip("请输入新的手机号码！");
		return false;
	}
	
	// 验证码格式为6位纯数字
	var verifyCode = $("#verifyCode").val();
	if (!/^\d{6}$/.test(verifyCode)) {
		$("#verifyCode").trigger("focus");
		warnTip("请正确输入验证码！");
		return false;
	}
	
	var data = {
		phone : newPhone,
		password : verifyCode	// 将验证码暂时存放在pwd域中
	}

	return data;
}

//更新密码
function updatePassword() {
	var spaceRegexp = /^\s*$/;
	var pwd1 = $("#newPwd1").val();
	var pwd2 = $("#newPwd2").val();
	if (spaceRegexp.test(pwd1)) {
		warnTip("请输入密码！");
		$("#newPwd1").trigger("focus");
		return false;
	}
	if (spaceRegexp.test(pwd2)) {
		warnTip("请再次输入密码！");
		$("#newPwd2").trigger("focus");
		return false;
	}
	if (pwd1 != pwd2) {
		warnTip("密码不一致！");
		return false;
	}
	var data = {
		password : pwd1
	};
	return data;
}

//界面刷新
function refresh(alertIndex) {
	$(".grayBg").hide();
	switch (alertIndex) {
	case 0:
		$("#userNick").html($("#newNick").val());
		break;
	case 1:
		$("#userPhone").html($("#newPhone").val());
		$("#newPhone").val("");
		if (timer != undefined) {
			resetCodeTimer();
		}
		break;
	case 2:
		// 清空输入的密码
		$("#newPwd1").val("");
		$("#newPwd2").val("");
		break;
	}
}

// 初始化获取验证码
function initVerifyCode() {
	$("#code").on("click", function() {
		var oldPhone = $("#userPhone").html();
		var newPhone = $("#newPhone").val();
		// 如果新手机号码和旧手机号码，不发送验证码
		if (oldPhone == newPhone) {
			warnTip("请输入新的手机号码！");
			return;
		}
		// 如果新手机号码格式不正确
		if (!phoneRegexp.test(newPhone)) {
			warnTip("手机号码格式错误！");
			return;
		}
		
		// 请求发送验证码
		$.ajax({
			url : 'user/sendcode',
			type : 'post',
			timeout : 2000,
			data : {phone: newPhone},
			dataType : "json",
			error : function(err) {
				errorTip('服务器异常！');
			},
			success : function(data) {
				if (data.status == 200) {
					// 申请验证码成功
					setTimer();
					successTip("验证码已发送，请查收！");					
				} else {
					warnTip(data.msg);
				}
			}
		});
		
	});
}

//60s后可重新发送验证码
function setTimer() {
	var code = $("#code");
	code.attr("disabled", true);
	var s = 60;
	code.val("重新发送" + --s);
	timer = setInterval(function() {
		code.val("重新发送" + --s);
		if (s == 0) {
			clearInterval(timer);
			code.attr("disabled", false);
			code.val("获取验证码");
			isReqCode = false;
		}
	}, 1000);
}
