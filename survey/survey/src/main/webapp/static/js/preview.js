//问卷记录数据结构
var naireRecord = {
	startTime: new Date(),
	naireId: 0,
	ip: '',
	cityCode: '',
	optionAnswerList: [],
	briefAnswerList: []
};


var phoneFlag = 0;

judge();

$(function() {
	
	// 手机访问
	if (phoneFlag == 1) {
		initPhoneStyle();
		$("header").remove();
    	$(".footer").remove();
    	$("#contentHeader").remove();
    	initAutoAnimate();
	} else {
		// 电脑访问
		initSubject();
		initProgress();
		initWordAndQRCode();
	}
	
	// 如果是答题状态，记录问卷ID
	if ($("#naireId").val() != "") {
		naireRecord.naireId = $("#naireId").val();
		
		// 如果当前用户已经回答了本次本次问卷，禁止其再次回答
		if (isNaireAnswered()) {
			if (phoneFlag) {
				alert("本次问卷你已回答完毕！");
				$(".sub_btn").on("click", function() {
					alert("本次问卷你已回答完毕！");
				});
			} else {
				infoTip("本次问卷你已回答完毕！");
				$(".sub_btn").on("click", function() {
					infoTip("本次问卷你已回答完毕！");
				});
			}
			
		//	return;
		}
	}
	
	
	// 初始化
	if (!init()) {
		return;
	}

	// 提交数据
	$(".sub_btn").on("click", function() {
		
		if (!check()) {
			return;
		}

		collectData();
	//	collectSingleData();

		// 发出请求
		$.ajax({
			url : 'naire/answer/record',
			type : 'post',
			timeout : 3000,
			data : JSON.stringify(naireRecord),
			dataType : "json",
			contentType : 'application/json',
			error : function(XMLHttpRequest) {
				console.log(JSON.stringify(XMLHttpRequest));
				if (phoneFlag) {
					alert('服务器异常！');
				} else {
					errorTip('服务器异常！');
				}
			},
			success : function(result) {
				if (result.status == 200) {
				//	$(".sub_btn").trigger('click');
					recordAnsweredNaire();
					over();
					/*successTip('提交成功！');
					setTimeout(function() {
						window.location.href=""; 
					}, 2000);*/					
				} else {
					console.log(result.msg);
					if (phoneFlag) {
						alert('保存失败');
					} else {
						errorTip('保存失败');
					}
				}
			},
			beforeSend : function() {
				$(".sub_btn").attr("disabled", true);
			},
			complete : function() {
				$(".sub_btn").attr("disabled", false);
			}
		});

	});

});


//收集用户填写数据
function collectData() {
	naireRecord.optionAnswerList.length = 0;
	naireRecord.briefAnswerList.length = 0;
	// 记录选择题
	$("input:checked[wjw]").each(function() {
		var optionAnswer = {
			questionId: Number($(this).attr('name')),
			id: Number($(this).val())
		};
		naireRecord.optionAnswerList.push(optionAnswer);
	});
	// 特殊标记：wjw，由于浏览器插件原因，可能会向页面添加一些html元素（如：textarea等）会影响数据收集，导致收集错误数据
	// 在此特加wjw标记，标记属于自己的html元素
	// 遍历所有填写题
	$("textarea[wjw]").each(function() {
		var briefAnswer = {
			questionId: Number($(this).attr('name')),
			answerContent: $(this).val()
		};
		naireRecord.briefAnswerList.push(briefAnswer);
	});
	
	console.log(JSON.stringify(naireRecord));
}


//检查所有问题是否填完
function check() {
	var flag = true;
	$("span").each(function() {
		if ($(this).html() == "0") {
			var offset;
			if (phoneFlag) {
				offset = 60;
			} else {
				infoTip('请填写完所有问题！');
				offset = $(this).parent().height()/2;
			}
			
			// 计算移动距离
			var top = $(this).parent()[0].offsetTop - offset - 50;
			$(".bg").animate({scrollTop: top}, 500, function() {
				if (phoneFlag) {
					$(".tip").css("visibility", "visible");
				}
			});
			flag = false;
			return false;
		}
	});
	return flag;
}

//初始化
function init() {
	// 单选题和复选题
	$("input").on("click", function() {
		var parent = $(this).parents("div[index]");
		var span = parent.find("span");
		var checkedCount = parent.find("input:checked").length;
		if (checkedCount > 0) {
			span.html(1);
			if (phoneFlag) {
				$(".tip").css("visibility", "hidden");
			}
		} else {
			span.html(0);
		}
		if (phoneFlag) {
			
			if ($(this).attr("type") == "radio") {
				$(this).parent().addClass("oActive").siblings().removeClass("oActive");
			} else {
				if ($(this).attr("checked") == "checked") {
					$(this).parent().addClass("oActive")
				} else {
					$(this).parent().removeClass("oActive")
				}
			}
		}
	});
	// 简答题
	$("textarea").on("blur", function() {		
		if ($(this).val() == "") {
			// 标记此题未回答
			$(this).parent().children("span").html(0);
		} else {
			// 标记此题已回答
			$(this).parent().children("span").html(1);
			if (phoneFlag) {
				$(".tip").css("visibility", "hidden");
			}
		}
	});
	if (isNaireAnswered()) {
		return false;
	}
	// 如果该问卷是结束状态，禁止用户提交
	var naireStatus = $("#naireStatus").val();
	if (naireStatus != "2") {
		var tip;
		switch (naireStatus) {
			case '1': tip = "该问卷还未发布！"; break;
			case '3': tip = "该问卷已结束！"; break;
			default: tip = "问卷状态异常！"; break;
		}
		$(".sub_btn").on("click", function() {
			if (phoneFlag) {
				alert(tip);
			} else {
				infoTip(tip);
			}
		}).trigger("click");
		return false;
	} 
	
	if (naireStatus == "2") {
		getUserIp();
	}
	return true;
}

var codeCanvas;

// 初始化导出问卷和生成二维码功能
function initWordAndQRCode() {
	$(".tool i").parent().on("click", function() {
		if ($("#naireId").length>0 && /^\d+$/.test($("#naireId").val())) {
			var url = "naire/download/" + $("#naireId").val();
			window.open(url, "_blank");
		} else {
			infoTip("请先保存问卷！");
		}
	});
	
	if ($("#naireId").length==1 && /^\d+$/.test($("#naireId").val())) {
		var baseHref = $("base").attr("href").replace(/:80/, "");
		var url = baseHref + "naire/preview/" + $("#naireId").val();
		var qrcode = $("#code").qrcode({
	        render: "canvas",
	        width: 150,
	        height: 150,
	        text: url
	    });
		var canvas = qrcode.find('canvas')[0];
		//$(".tool a").attr("href", canvas.toDataURL('image/png'));
		// 激活tip状态
		$('j').parent().tooltipster({
			trigger: 'click',
			theme: 'tooltipster-shadow' //使用shadow主题
		});
	} else {
		$('j').parent().on("click", function() {
			infoTip("请先保存问卷！");
		});
	}
	
}


// 初始化答题进度条
function initProgress() {
	var qCount = parseInt($("#progress").attr("qCount"));
	var set = new Set();
	$("input").on("click", function() {
		var parent = $(this).parents("div[index]");
		var index = parseInt(parent.attr("index"));
		var checkedCount = parent.find("input:checked").length;
		if (checkedCount > 0) {
			set.add(index);
		} else {
			set.delete(index);
		}
		
		var percent = (set.size / qCount * 100)+"%";
		$("#progress").animate({width: percent}, 1000);
		changePercent(percent);
	});

	$("textarea").on("blur", function() {
		var index = parseInt($(this).parents("div[index]").attr("index"));
		if ($(this).val() != "") {
			set.add(index);
		} else {
			set.delete(index);
		}
		var percent = (set.size / qCount * 100)+"%";
		$("#progress").animate({width: percent}, 1000);
		changePercent(percent);
	})
}

// 改变百分比
function changePercent(newPercent) {
	var progress = $("#progress");
	var oldOercent = progress.html().split("%")[0];
	oldOercent = oldOercent=="" ? 0 : parseInt(oldOercent);
	newPercent = parseInt(newPercent.split("%")[0]);
	var speed = 1000/Math.abs(newPercent-oldOercent);
	if (newPercent > oldOercent) {
		var interval = setInterval(function() {
			progress.html(++oldOercent + "%");
			if (oldOercent == newPercent) {
				clearInterval(interval);
			}
		}, speed);
	} else if (newPercent < oldOercent) {
		var interval = setInterval(function() {
			progress.html(--oldOercent == 0 ? "" : oldOercent + "%");
			if (oldOercent == newPercent) {
				clearInterval(interval);
			}
		}, speed);
	}
	
}



//答题结束界面
function over() {
	$("header").slideToggle(1000);
	var overTip;
	if (phoneFlag) {
		$(".cover").remove();
		overTip = $('<div class="over"><i></i><br><br><span>您已完成本次问卷<br><br>感谢您的帮助与支持</span></div>');
	} else {
		overTip = $('<div class="over"><i></i><span>您已完成本次问卷, 感谢您的帮助与支持</span></div>');
	}
	var content = $(".bg > .content").css("min-height", "auto").css("background", "#fff");
	
	if (phoneFlag == 1) {
		$(".bg").css("background", "#fff");
		content.removeClass("shadow");
	}
	content.animate({height: "80%"}, 2000).children().fadeOut(2000, function() {
		content.append(overTip.fadeIn(1000));
		$(".bg").animate({scrollTop: 0}, 100);
	});
}

//记录已回答问卷的ID
function recordAnsweredNaire() {
	var answerdNaire = getLocalStorage("answerdNaire");
	if (answerdNaire == null) {
		answerdNaire = "";
	}
	answerdNaire += naireRecord.naireId + ",";
	setLocalStorage("answerdNaire", answerdNaire);
}

// 判断本次问卷用户是否已经回答过
function isNaireAnswered() {
	var answerdNaire = getLocalStorage("answerdNaire");
	if (answerdNaire == null) {
		return false;
	}
	return answerdNaire.indexOf(naireRecord.naireId) != -1;
}

// 初始化自动滑动事件
function initAutoAnimate() {
	/*// 自动滑动事件(当选择一题后，自动下滑)
	$("input").on("click", function() {
		var checkedCount = $(this).parents("div[index]").find("input:checked").length;
		if (checkedCount == 0) {
			return;
		}
		var next = $(this).parents("div[index]").next();
		while (next.find("span").html() == "1") {
			next = next.next();
		}
		$(".bg").animate({scrollTop: next[0].offsetTop-50}, 500);
	});*/
	$("textarea").on("click", function() {
		$(".bg").animate({scrollTop: $(this).parents("div[index]")[0].offsetTop-350}, 500);
	});
}


// 初始化主题
function initSubject() {
	$(".bg>.content").addClass("shadow");
	var subject = parseInt($("#naireSubject").val());
	switch (subject) {
		case 0: setSubject("defaultSubject"); break;
		case 1:  // 灰色主题 
			setSubject("graySubject"); break;
		case 2:  // 红色主题
			setSubject("redSubject"); break;
		case 3:  // 黑色主题
			setSubject("blackSubject"); break;
		default: setSubject("defaultSubject"); break;
	}
}

function setSubject(subject) {
	$(".bg").addClass(subject);
}



// 手机分条数据
function collectSingleData() {
	var ansData = '';	
	$('div[index]').each(function() {		
		var questionType = $(this).attr('type');
		var singleData;
		
		if (questionType == 'radio') {
			singleData = 'r' + ($(this).find('input:checked').parent().index()+1);
		} else if (questionType == 'checkbox') {
			singleData = 'c';
			$(this).find('input:checked').each(function() {
				singleData += ($(this).parent().index()+1) + '&';
			});
			singleData = singleData.substr(0, singleData.length-1);
		} else if (questionType == 'textarea') {
			singleData = 't#';
		}
		ansData += singleData + ',';		
	});
	ansData = ansData.substr(0, ansData.length-1);
	naireRecord.singleAns.ansData = ansData;
}

// 记录用户IP
function getUserIp() {
	$.getScript('http://whois.pconline.com.cn/ipJson.jsp');
}

// IP查询回调函数
function IPCallBack(data) {
	naireRecord.ip = data.ip;
	naireRecord.cityCode = data.cityCode;
}



function initPhoneStyle() {
	$(".cover").show();
	$(".cover i").on("click", function(){
		$(".bg>.content").show();
		$(".bg").animate({scrollTop: $(".bg>.content")[0].offsetTop}, 500);
	});
}


function judge() {
	// 判断当前是手机等移动设备访问还是电脑访问
	var userAgent = navigator.userAgent.toLowerCase();
	if (userAgent.indexOf("android") != -1 || userAgent.indexOf("iphone") != -1) {
		// 手机访问
		phoneFlag = 1;
	}
}