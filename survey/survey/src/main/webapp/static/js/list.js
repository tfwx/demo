//查询参数(默认值不可随意改动)
var queryParameter = {
	keyWord : "",
	startTime : "",
	endTime : "",
	status : "",
	parentCategory : "",
	pageNum : 1,
	pageSize : "",
	order : 0,
	isScroll: 0
}


initDefaultStyle();

$(function() {
	initListStyle();
	initUserNav();
	initSearch();
	initToolTip();
	initBottomWrapper();
	initRecycle();
	initPage();
})



// 初始化搜索事件
function initSearch() {
	$("#search").on("mouseup", function() {
		queryParameter.isScroll = 0;
		queryParameter.pageNum = 1;
	});
	
	$("#search").on("click", function () {
		showLoading();
		queryParameter.keyWord = $("#searchContent").val().replace(/^\s*$/g, '');
		$('#listContent').load('naire/list #listContent>div', queryParameter, function(response, status, xhr) {
			initPage();
			if (queryParameter.isScroll == 1) {
				$('body').animate({scrollTop:$('#top').offset().top}, 500, function() {
					hideLoading();
				});
			} else {
				hideLoading();
			}
		});
	});
	
	$("#category").on("change", function() {
		queryParameter.parentCategory = $(this).val();
		$("#search").trigger("mouseup");
		$("#search").trigger("click");
	});
	
	$("#order").on("change", function() {
		queryParameter.order = $(this).val();
		$("#search").trigger("mouseup");
		$("#search").trigger("click");
	});

}


// 改变问卷状态
function changeStatus(naireId) {
	
	var statusMap = new Map();
	statusMap.set("1", {
		desc: "未发布",
		style: "unpublished"
	});
	statusMap.set("2", {
		desc: "收集中",
		style: "collecting"
	});
	statusMap.set("3", {
		desc: "已结束",
		style: "over"
	});
	
	// 拼装json数据
	var data = {
		id : naireId,
		status : parseInt($("#status"+naireId).val())
	};

	// 发出请求
	$.ajax({
		url : 'naire/update',
		type : 'post',
		timeout : 2000,
		data : data,
		dataType:"json",
		error : function() {
			infoTip("网络错误！");
		},
		success : function(result) {
			if (result.status == 200) {
				// 刷新最后一次修改时间
				var date = new Date();
				var d = date.getFullYear() 
				 	+ "-" 
				 	+ (date.getMonth()+1) 
				 	+ "-" 
				 	+ date.getDate();
				$("span[name='endTime"+naireId+"']").html(d);
				
				// 刷新box视图
				var status = statusMap.get(data.status+"")
				var tdIndex = $("#content"+naireId).index()+1;
				var boxItem = $("td").eq(tdIndex).find(".box_bottom");
				boxItem.find("div:eq(0)").attr("class", status.style).find("span:eq(0)").html(status.desc);
				
				infoTip("操作成功！");
			} else {
				infoTip("操作失败！");
			}
		}
	});
}

//删除问卷(移至回收站)
function recycleNaire(naireId) {
	var dblChoseAlert = simpleAlert({
		content : '确定要移入回收站么?',
		buttons : {
			'确定' : function () {
				// 发出请求
				$.ajax({
					url : 'naire/recycle',
					type : 'post',
					timeout : 2000,
					data : {id : naireId},
					dataType:"json",
					error : function() {
						errorTip("网络错误！");
					},
					success : function(data) {
						if (data.status == 200) {
							infoTip("删除成功！");
							// 界面刷新
							refreshListView(naireId);
						} else {
							infoTip("删除失败！");
						}
					},
					beforeSend: function() {
						showLoading();
					},
					complete: function() {
						hideLoading();
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

// 删除问卷(彻底删除)
function deleteNaire(naireId) {	
	var dblChoseAlert = simpleAlert({
		content : '确定要永久删除么?',
		buttons : {
			'确定' : function () {
				// 发出请求
				$.ajax({
					url : 'naire/delete',
					type : 'post',
					timeout : 2000,
					data : {id : naireId},
					dataType:"json",
					error : function() {
						errorTip("网络错误！");
					},
					success : function(data) {
						if (data.status == 200) {
							infoTip("删除成功！");
							// 界面刷新
							refreshListView(naireId);
						} else {
							infoTip("删除失败！");
						}
					},
					beforeSend: function() {
						showLoading();
					},
					complete:function() {
						hideLoading();
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

// 刷新列表视图
function refreshListView(naireId) {
	// 获取目标td元素
	var index = $("#content"+naireId).index()+1;
	var td = $(".list_content_style_box table td:eq("+index+")");
	var tdParent = td.parent();
	var tdBrothers = td.siblings();
	// 删除元素
	td.remove();
	$("#content"+naireId).remove();
	
	// 后序td元素元素前移
	tdParent.append(tdParent.next().children(":eq(0)"));
	var tdParentNext = tdParent.nextAll();
	$.each(tdParentNext, function(i, tr) {
		var $tr = $(tr);
		$tr.append($tr.next().children(":eq(0)"));
	});
	
	// 当第一行元素不够时，填补空缺
	if (tdParent.index() == 0 && tdBrothers.length < 4 && $(".list_content_style_box td").length < 4) {
		tdParent.append($('<td style="visibility: hidden;"></td>'));
	}
}

// 复制问卷地址
function copyAddress(address) {
	var dblChoseAlert = simpleAlert({
		content : address,
		buttons : {
			'点我复制' : function () {
				infoTip("快去分享问卷链接吧！");
				dblChoseAlert.close();
			}
		}
	});
}

// 初始化页面控件与列表控件之间wrapper的高度
function initBottomWrapper() {
	var h = $(".list_head").css("height");
	h = parseInt(h.substring(0, h.length-2));
	var m = $(".main").css("height");
	m = parseInt(m.substring(0, m.length-2));
	var s = $(".list_sel").css("height");
	s = parseInt(s.substring(0, s.length-2));
	var w = (m-s-h-600) + 'px';
	
	//$(".wrapper").css("height", w);
}

// 初始化提示tip
function initToolTip() {
	$('.icon').tooltipster({
		theme: 'tooltipster-shadow' //使用shadow主题
	});
}


// 初始化显示风格事件
function initListStyle() {	
	$(".list_icon").on("click", function() {
		$(".list_content_style_box").removeClass("show");
		$(".list_content_style_list").addClass("show");
		$(".new_create").show();
		$(".box_icon").removeClass("iconActive");
		$(this).addClass("iconActive");
		setCookie("listStyle", "list");
	});
	
	$(".box_icon").on("click", function() {
		$(".list_content_style_box").addClass("show");
		$(".list_content_style_list").removeClass("show");
		$(".new_create").hide();
		$(".list_icon").removeClass("iconActive");
		$(this).addClass("iconActive");
		setCookie("listStyle", "box");
	});
	
	
	$("body").on("mouseenter", ".list_content_style_box td", function() {
		$(this).css("box-shadow", "0px 0px 20px 5px #BBB")
		.find(".box_bottom").css("background", "rgb(248, 248, 248)").children().toggle();
	}).on("mouseleave", ".list_content_style_box td", function() {
		$(this).css("box-shadow", "0px 0px 20px 5px #dddedf")
		.find(".box_bottom").css("background", "#fff").children().toggle();
	});
	if (getCookie("listStyle") == 'list') {
		$(".new_create").show();
	}
}

// 初始化默认列表显示风格
function initDefaultStyle() {
	// 默认box列表风格
	var listStyle = getCookie("listStyle");
	if (listStyle == null) {
		setCookie("listStyle", "box");
	}
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

function initRecycle() {
	$(".btn_recycle").on("click", function() {
		showRecycleView();		
	});
	//初始化全选事件
	$("#selectAll").on("change", function() {
		$("input[name='recycleBox']").attr("checked", $(this).is(":checked"));
	});
	
	// 存放所选记录的id，以逗号分隔
	var naireIdArray = [];
	$(".bottomBtn input").on("click", function() {
		naireIdArray.length = 0;
		if ($("input[name='recycleBox']:checked").length == 0) {
			warnTip("请先选择数据");
			return;
		}
		$("input[name='recycleBox']:checked").each(function() {
			var id = $(this).attr("id");
			if (id != undefined && id != "") {
				id = id.split("recycle_")[1];
			}
			naireIdArray.push(parseInt(id));
		});
		restoreOrDelete(naireIdArray, $(this).attr("operation"), $(this));
	});
}


// 显示回收站界面
function showRecycleView() {
	$(".mask .content table tr:gt(0)").remove();	//清除缓存
	$(".mask").show();
	// 请求数据
	$.ajax({
		url : "naire/getRecycledData",
		type : 'post',
		timeout : 3000,
		dataType:"json",		
		success : function(result) {
			if (result.status == 200) {
				var recycledList = result.msg;
				var table = $(".mask .content table");
				$.each(recycledList, function(i, e) {
					var tr = ''
						+ '<tr>'
						+ '<td><input type="checkbox" name="recycleBox" id="recycle_' + e.id + '">'+ e.title + '</td>'
						+ '<td>' + e.collectCount +  '</td>'
						+ '<td>' + new Date(e.createTime).format("yyyy-MM-dd") + '</td>'
						+ '<td>' + new Date(e.endTime).format("yyyy-MM-dd") + '</td>'
						+ '</tr>';
					table.append(tr);
				});
			} else {
				warnTip("数据获取失败！");
			}
		}, 
		error: function() {
			errorTip("网络错误！");
		},
		beforeSend: function() {
			showLoading();
		},
		complete:function() {
			hideLoading();
		}
	});
}

// 执行恢复或删除操作
function doRestoreOrDelete(url, data, btn) {
	$.ajax({
		url : url,
		type : 'post',
		timeout : 2000,
		data : data,
		dataType:"json",		
		success : function(result) {
			if (result.status == 200) {
				// 回收站界面更新元素
				for (var i = 0; i < data.naireIds.length; i++) {
					var id = "recycle_" + data.naireIds[i];
					$("#" + id).parents("tr").remove();
				}
				if (url == "naire/restore") {
					// 刷新页面
					$("#category").trigger("change");
				}
				infoTip("操作成功！");
			} else {
				infoTip("操作失败！");
			}
		},
		error : function() {
			infoTip("网络错误！");
		},
		beforeSend:function() {
			btn.attr("disabled", true);
		},
		complete: function() {
			btn.attr("disabled", false);
		}
	});
}

// 恢复或删除记录
function restoreOrDelete(naireIdArray, operation, btn) {
	var url;
	var data = {naireIds: naireIdArray};
	if (operation == "restore") {
		url = "naire/restore";
		doRestoreOrDelete(url, data, btn);
	} else if (operation == "delete") {
		url = "naire/delete";
		var dblChoseAlert = simpleAlert({
			content : '你确定要永久删除吗？删除后无法恢复',
			buttons : {
				'确定' : function () {
					doRestoreOrDelete(url, data, btn);
					dblChoseAlert.close();
				},
				'取消' : function () {
					dblChoseAlert.close();
				}
			}
		});
	}
}

//初始化分页栏
function initPage() {
	// 显示页面按钮
	if ($("#p_total").val() != "0") {
		$("#page").show();
		$("#page").Page({
		  	liNums: 4,
		  	totalPages: parseInt($("#p_pages").val()),
		  	curPage: parseInt($("#p_pageNum").val()),
		  	pageSize: parseInt($("#p_pageSize").val()),
		  	activeClass: 'activP', //active 类样式定义
		  	/* 	showCount: 20,		// 展示条数 */
		 	callBack : function(page, pageSize) {
		 		// 禁止重复点击相同页面
		 		if (page == parseInt($("#p_pageNum").val()) && pageSize == queryParameter.pageSize) {
		 			return;
		 		}
		 		// 显示加载提示
		 		queryParameter.isScroll = 1;
		 		queryParameter.pageNum = page;
		 		queryParameter.pageSize = pageSize;
		 		$("#search").trigger("click");
		  	}
	 	});
	} else {
		$("#page").hide();
	}
	
	
}


