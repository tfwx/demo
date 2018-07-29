
/**
 * form验证
 * 参数示例
 * var option = {
 * 		aim: $(this),			// 将在目标元素后追加提示
 * 		content: '此项必填',		// 提示内容
 * 		regExp: /.+/			// 验证规则
 * }; 
 */	
$.fn.extend({
	showErrTip: function (tip) {
		var top = $(this).offset().top + $(this)[0].offsetHeight/2;
		var left = $(this).offset().left + $(this)[0].offsetWidth + 10;
		var tipId = $(this).attr('tipId');
		var errTip = $('#validateTip' + tipId);
		if (errTip.length == 0) {
			errTip = $('<span id="validateTip' + tipId + '" style="color: red">此项必填</span>');
			$('body').append(errTip);
		}
		errTip.html(tip==undefined ? '此项必填' : tip);
		
		errTip.css({
			position: 'absolute',
			top: top - errTip.height()/2,
			left: left
		}).show();
	},
	hideErrTip: function () {
		var errTip = $('#validateTip' + $(this).attr('tipId'));
		if (errTip.length == 1) {
			errTip.hide();
		}
	},
	addTipId: function() {
		if (window.count == undefined) {
			window.count = 0;
		}
		if ((this).attr('tipId') == undefined) {
			(this).attr('tipId', window.count++);
		}
	},
	addCheck: function(params) {
		var option = {
			aim: $(this),
			content: '此项必填',
			regExp: /.+/
		};
		option = $.extend(option, params);
		option.aim.addTipId();
		$(this).on('blur', function(e) {
			if (option.regExp.test($(this).val())) {
				option.aim.hideErrTip();
			} else {
				e.stopImmediatePropagation();
				option.aim.showErrTip(option.content);
			}
		});
		return $(this);
	},
	required: function(params) {
		var option = {
			aim: $(this),
			content: '此项必填'
		};
		option = $.extend(option, params);
		option.regExp = /^.+$/;
		$(this).addCheck(option);
		return $(this);
	},
	valid: function() {
		$(this).find('input').trigger('blur');
		return $('span[id^="validateTip"]:visible').length == 0;
	}
});
	


$(function() {
	var phoneRegexp = /^0?1[3|4|5|8][0-9]\d{8}$/;
	// 添加验证规则
	$('#phone').required({
		content: '请输入手机号码'
	}).addCheck({
		regExp: phoneRegexp,
		content: '手机号码格式错误'
	});
	$('#password').required({
		content: '请输入新密码'
	});
	
	$('#verifyCode').required({
		aim: $('.sendcode'),
		content: '请输入验证码'
	}).addCheck({
		aim: $('.sendcode'),
		regExp: /^(\d|\a){6}$/,
		content: '验证码格式错误'
	});
	
	$('form').on('submit', function(e) {
		e.preventDefault();
		$(this).find('input').trigger('blur');
		if ($(this).valid()) {
			var url = 'user/resetpwd/' + $('#verifyCode').val();
			$.ajax({
				url: url,
				type: 'post',
				data: {password: $('#password').val()},
				dataType: 'json',
				timeout: 3000,
				success: function(result) {
					if (result.status == 200) {
						successTip('重设密码成功');
						setTimeout(function() {
							window.location.href = '/';
						}, 800);
					} else {
						infoTip(result.msg);
					}
				},
				error: function(XMLHttpRequest) {
					console.log(JSON.stringify(XMLHttpRequest));
				},
				beforeSend: function() {
					$('input[type="submit"]').attr('disabled', true);
				},
				complete: function() {
					$('input[type="submit"]').attr('disabled', false);
				}
			});
		}
	});

	
	$('.sendcode').on('click', function() {
		$('#phone').trigger('blur');
		if (!phoneRegexp.test($('#phone').val())) {
			return;
		}
		var data = {
			phone: $('#phone').val(),
			checkRepeat: false
		};
		$.ajax({
			url: 'user/sendcode',
			type: 'post',
			data: data,
			dataType: 'json',
			timeout: 3000,
			success: function(result) {
				if (result.status == 200) {
					successTip('验证码发送成功，请注意查收！');
				} else {
					infoTip(result.msg);
				}
			},
			error: function(XMLHttpRequest) {
				console.log(JSON.stringify(XMLHttpRequest));
			},
			beforeSend: function() {
				$('.sendcode').attr('disabled', true);
			},
			complete: function() {
				$('.sendcode').attr('disabled', false);
			}
		});
	});
	
	
});