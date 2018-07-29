//查询参数(默认值不可随意改动)
var queryParameter = {
	keyWord : "",
	startTime : "",
	endTime : "",
	status : "",
	parentCategory : 1,
	category: "",
	pageNum : 1,
	pageSize : "",
	order : 0,
	isScroll: 0
}


$(function() {
	// 默认不滑动，点击页面按钮时，自动滑动到顶端
	initUserNav();
	initCategory();
	initSearch();
	initUse();
	initHotWord();
	initPage();
	initGotop();
});

// 设置问卷功能分类的点击样式
function initCategory() {
	// select
	$('.search select').on('change', function() {
		$(".pCategory a[value='" + $(this).val() + "']").addClass('pActive').siblings().removeClass('pActive');
		queryParameter.parentCategory = $(this).attr("value");
		queryParameter.category = "";
		$("#search").trigger("mouseup");
		$("#search").trigger("click");
		$(".searchTxt").attr("placeholder", "输入关键字，搜索你想要的" + $(this).find(":selected").html() + "模板");
	});
	
	// 分类导航
	$('.content').on('click', '.pCategory a', function() {
		if ($(this).hasClass("pActive")) {
			// 禁止重复点击
			return;
		}
		queryParameter.parentCategory = $(this).attr("value");
		queryParameter.category = "";
		queryParameter.pageNum = 1;
		$(".search select").val(queryParameter.parentCategory);
		$(".searchTxt").attr("placeholder", "输入关键字，搜索你想要的" + $(".search select :selected").html() + "模板");
		$("#search").trigger("mouseup");
		$("#search").trigger("click");
	});
	
	// span
	$(".content").on("click", ".category .cc span", function() {
		if ($(this).hasClass("cActive")) {
			// 禁止重复点击
			return;
		}
		queryParameter.category = $(this).attr("value");
		$("#search").trigger("mouseup");
		$("#search").trigger("click");
	});
}

// 根据关键字搜索
function initSearch() {
	$("#search").on("mouseup", function() {
		queryParameter.isScroll = 0;
		queryParameter.pageNum = 1;
	});
	$("#search").on("click", function() {
		showLoading();
		queryParameter.keyWord = $(".searchTxt").val().replace(/^\s*$/g, '');
		$('.content').load('naire/lib .content>div', queryParameter, function() {
			initPage();
			if (queryParameter.isScroll == 1) {
				var top = ($('#gxmb').length == 1) ? ($('#gxmb').offset().top-100) : 0;
				$('body').animate({scrollTop:top}, 800, function() {
					hideLoading();
				});
			} else {
				hideLoading();
			}
		});
	});
}

// 使用该问卷
function initUse() {
	$(".content").on("click", ".useNaire", function() {
		if ($(".userName").length == 0) {
			warnTip("请先登录");
		} else {
			var naireId = $(this).attr("value");
			var dblChoseAlert = simpleAlert({
				content : '确定要使用该问卷么?',
				buttons : {
					'确定' : function () {
						$.ajax({
							url: "naire/copy",
							type: 'post',
							data: {naireId: naireId},
							timeout: 2000,
							success : function (data) {
								if (data.status == 200) {
									successTip("引用该模板成功");
								} else {
									warnTip(data.msg);
								}
							},
							error: function (err) {
								errorTip("服务器异常");
							}
						});
						dblChoseAlert.close();
					},
					'取消' : function () {
						dblChoseAlert.close();
					}
				}
			});
		}
	});
}


//初始化用户菜单
function initUserNav() {
	var userIcon = $(".userHead").find("img");
	if (userIcon.length == 1) {
		$(".userHead, .userNav").hover(function() {
			$(".userNav").show();
		}, function() {
			$(".userNav").hide();
		});
	}	
}

// 初始化热词搜索
function initHotWord() {
	$(".hotWord a").on("click", function() {
		category = "";
		$(".searchTxt").val($(this).html());
		$("#search").trigger("click");
	});
}

// 初始化分页
function initPage() {
	// 显示页面按钮
	if ($("#p_total").val() != "0") {
		 $("#page").Page({
			liNums: 4,
		  	totalPages: parseInt($("#p_pages").val()),
		  	curPage: parseInt($("#p_pageNum").val()),
		  	pageSize: parseInt($("#p_pageSize").val()),
		  	activeClass: 'activP', //active 类样式定义
		 	callBack : function(page, pageSize){
		 		// 禁止重复点击相同页面
		 		if (page == parseInt($("#p_pageNum").val()) && pageSize == queryParameter.pageSize) {
		 			return;
		 		}
		 		queryParameter.pageNum = page;
		 		queryParameter.pageSize = pageSize;
		 		queryParameter.isScroll = 1;
		 		$("#search").trigger("click");
		  	}
	 	});
	}
}

// 初始化goTop事件
function initGotop() {
	window.onscroll= function(){
	    //变量t是滚动条滚动时，距离顶部的距离
	    var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
	    var goTop = $('.goTop');
	    if (scrollTop > 200 && goTop.is(':hidden')) {
	    	console.log(2);
	    	goTop.show();
	    } else if (goTop.is(':visible') && scrollTop < 200) {
	    	console.log(1);
	    	goTop.hide();
	    }
	}
}