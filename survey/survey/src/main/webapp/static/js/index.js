$(function(){
	
	initBackground();
	initLogin();
	initNav();
	
});


// 登录信息验证
function check() {
	var flag = true;
	// 电话正则表达式（11位数字）
	var phoneRegexp = /^0?1[3|4|5|8][0-9]\d{8}$/;
	// 空格正则表达式
	var spaceRegexp = /^\s*$/;
	
	var phone = $("#phone").val();
	var password = $("#password").val();
	
	if (spaceRegexp.test(phone)) {
		$("#wPhone").attr("style", "visibility: visible;");
		if (flag) {
			$("#phone").trigger("focus");
		}
		flag = false;
	} else {
		if (!phoneRegexp.test(phone)) {
			$("#wPhone").attr("style", "visibility: visible;");
			if (flag) {
				$("#phone").trigger("focus");
			}
			flag = false;
		} else {
			$("#wPhone").attr("style", "visibility: hidden;");
		}
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
		warnTip('请正确填写登陆信息！');
	}
	
	return flag;	
}

// 初始化背景
function initBackground() {
	
	var curImg = $("#img1");
	$("a[href='#img1'] div").css("background", "#fff");
	$("body").animate({scrollTop:curImg.offset().top}, 400);
	// 点击主界面右侧导航条事件
	$("a[href^='#img']").on("click", function() {
		// 如果当前已在此界面，禁止再次触发点击事件
		if (curImg.attr("id") == $(this).attr("href").substring(1)) {
			return;
		}
		var id = $(this).attr("href");
		curImg = $(id);
		$("body").animate({scrollTop:$(id).offset().top}, 400);
		// 更改导航条颜色
		$("a[href^='#'] div").css("background", "rgba(255, 255, 255, 0.2)");
		if ($(this).children().length == 1) {
			$(this).children().css("background", "#fff");
		} else {
			$("a[href^='#'] div").eq($(this).index()+1).css("background", "#fff");
		}
		
		
		fadeInIntroduce();
	});
	
	// 鼠标滚动标记
	var scrollFlag = false;
	// 鼠标滚动事件
	$("body").on('mousewheel', function(event, delta) {
		if (scrollFlag) { return; }
		scrollFlag = true;
		// 清除滚动标记
		setTimeout(function() {
			scrollFlag = false;
		}, 250);
		
		if (delta > 0) {			// 1: 向上
			if (curImg.prevAll("img").length > 0) {
				curImg = curImg.prevAll("img").eq(0);
			} else {
				return;
			}
		} else if (delta < 0) {	// -1: 向下
			if (curImg.nextAll("img").length > 0) {
				curImg = curImg.nextAll("img").eq(0);
			} else {
				return;
			}
		}
		// 更改导航条颜色
		var href = curImg.attr("id");
		$("a[href^='#'] div").css("background", "rgba(255, 255, 255, 0.2)");
		$("a[href='#"+href+"'] div").css("background", "#fff");
		
		// 滚动
		$("body").animate({scrollTop:curImg.offset().top}, 400);
		fadeInIntroduce();
	});
	
	// 设置淡入效果
	function fadeInIntroduce() {		
		var introduce = curImg.prev();
		if (introduce.length == 1) {
			// 先隐藏
			introduce.hide();
			introduce.find("h4").css("margin-top", "10%");
			// 在淡入
			introduce.fadeIn(2000);
			introduce.find("h4").animate({marginTop: "5%"}, 1000);
		}
	}
}

// 初始化登陆事件
function initLogin() {
	// 默认使用上一次的账户作为登录账户
	var userPhone = getLocalStorage("userPhone");
	if (userPhone != null) {
		$("#phone").val(userPhone);
	}
	// 登陆
	$(".login_btn").on("click", function(){
		
		if (!check()) {
			return;
		}
		// 封装数据
		var data = {
			phone : $("#phone").val(),
			password : $("#password").val()
		};

		// 登录请求
		$.ajax({
			url : 'user/login',
			type : 'post',
			timeout : 3000,
			data : data,
			dataType : "json",
			success : function(result) {
				if (result.status == 200) {
					$(".login_btn").html("页面加载中...");
					setLocalStorage("userPhone", data.phone);
					var searchParam = location.search;
					if (searchParam.startsWith('?returnUrl=')) {
						var returnURL = searchParam.split('?returnUrl=')[1];
						window.location.href = returnURL;
					} else {
						window.location.href = "naire/list";
					}
				} else {
					infoTip(result.msg);
					$(".login_btn").html("登 录").attr("disabled", false);
				}
			},
			error : function() {
				$(".login_btn").html("登 录").attr("disabled", false);
				errorTip('网络错误！');
			},
			beforeSend: function() {
				$(".login_btn").html("登录中...").attr("disabled", true);
			}
		});
	});
	
	// 打开登陆面板
	$("#loginPanel").on("click", function(){
		var popLayer = document.getElementById('popLayer');

		var wid = document.body.scrollWidth + "px";
		var hei = document.body.scrollHeight + "px";

		popLayer.style.width = wid;
		popLayer.style.height = hei;

		$('#popLayer').fadeIn(100, function() {
			$('#popBox').show();
			$('#popBox').css({
				'margin-left' : -wid / 2,
				'margin-top' : -hei / 2
			}).animate({
				'opacity' : '1'
			}, 300);
		});
		
		// 避免多次注册事件导致事件累加，注册前先解绑
		$("#phone, #password").unbind("keypress");
		$("#phone, #password").keypress(function(e){
			var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
	        if (eCode == 13){
	        	$(".login_btn").trigger("click");
	        }
		});
	});
	
	// 关闭登陆面板
	$(".close_tc").on("click", function(){
		$('#popBox').animate({
			'opacity' : '0'
		}, 300, function() {
			$('#popBox').hide();
			$('#popLayer').fadeOut(100);
		});
	});
}

// 导航栏提示
function initNav() {
	var tip;
	$(".nav > a").hover(function() {
		var index = $(this).index() + 1;
		tip = $(".tip" + index).parent();

		if (tip.attr("flag") == undefined) {
			tip.offset({
				left: $(this).offset().left,
				top: $(this).offset().top+20-$("body").scrollTop()
			});
			tip.attr("flag", "1");
		}
		tip.show();
	}, function() {
		tip.hide();
	});
	
	$(".tipBox .tip").parent().mouseenter(function() {
		tip.show();
	}).mouseleave(function() {
		tip.hide();
	});
}
