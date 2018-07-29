//定义问卷结构
var naire = {
	title : "",
	naireDesc : "",
	categoryId : 0,
	collectCount : 0,
	questionList : [],
	status : 1,
	share: true,
	subject: 0,
	phoneStyle: 0
}

var lastSubject = 0;
var lastPhoneStyle = 0;

$(function() {
	
	// 初始化折叠事件
	initNaireImport();
	initFolder();
	initSubject();
	initShare();
	
	//同步标题输入框
	$("#naireTitle1").keyup(function(event) {
		$("#naireTitle2").val($("#naireTitle1").val());
		naire.title = $(this).val();
	});
	$("#naireTitle2").keyup(function(event) {
		$("#naireTitle1").val($("#naireTitle2").val());
		naire.title = $(this).val();
	})
	
	// 记录问卷描述
	$(".desc").keyup(function(event){
		naire.naireDesc = $(this).val();
	});
	
	
	// 预览问卷
	$("#previewNaire").on("click", function(){		
		// 预览问卷前先进行问卷内容检查
		if (!check()) {
			return;
		}
		
		var form = $('#preForm');
		if (form == undefined || form.length != 1) {
			form = ''
				+ '<form id="preForm" action="naire/preview" method="post" target="_blank" style="display: none;">'
				+ '<input type="text" name="previewData" />'
				+ '</form>';
			form = $(form);
			$('body').append(form);
			
			/**
			 * 注：HTML标准规定如果form表单没有被添加到document里，那么form表单提交将会被终止。
			 * 在Chrome56之前的版本是不符合标准的，Chrome56修复了这个问题，让form表单提交符合标准要求：
			 * 解决方法就是把form表单添加到document后再提交:
			 */
		}
		form.find('input').val(JSON.stringify(naire));
		form.submit();
	});
	
	// 发布问卷
	$("#saveNaire").on("click", function(){
		
		// 发布问卷前先进行问卷内容检查
		if (!check()) {
			return;
		}
		var $this = $(this);
		
		$.ajax({
			url : 'naire/save',
			type : 'post',
			timeout : 2000,
			data : JSON.stringify(naire),
			dataType : "json",
			contentType : 'application/json',
			error : function(XMLHttpRequest) {
				console.log(JSON.stringify(XMLHttpRequest));
				errorTip('网络错误');
			},
			success : function(result) {
				if (result.status == 200) {
					removeLocalStorage('unsavedNaireData')
					setTimeout(function() {
						window.location.href = 'naire/list';
					}, 1000);
					successTip('保存成功！');
				} else {
					console.log(result.msg);
					errorTip('保存失败！');
				}
			},
			beforeSend:function() {
				showLoading();
				$this.css("pointer-events", "none");
			},
			complete: function() {
				hideLoading();
				$this.css("pointer-events", "auto");
			}
		});
	});
	
	// 问卷类型选择
	$("#category a").on("click", function() {
		// 界面更新
		$(this).addClass("active").siblings().removeClass("active");
		
		// 保存问卷类型
		naire.categoryId = parseInt($(this).attr("value"));
		
		// 只在新增问卷时缓存，编辑时不缓存
		if (naire.id == undefined) {
			setLocalStorage("unsavedNaireData", naire);
		}
	});

	
	
	// 测试专用
	$("#show").on("click", function(){
		console.log(JSON.stringify(naire));
	});
	

	/*	$(".btwen_text").val("题目");
				$(".btwen_text_dx").val("单选题目");
				$(".btwen_text_duox").val("多选题目");
				$(".btwen_text_tk").val("填空题目");

				$(".leftbtwen_text").val("例子：CCTV1，CCTV2，CCTV3");
				$(".xxk_title li").click(function() {
					var xxkjs = $(this).index();
					$(this).addClass("on").siblings().removeClass("on");
					$(".xxk_conn").children(".xxk_xzqh_box").eq(xxkjs).show().siblings().hide();
				});*/

	//鼠标移上去显示按钮
	$(".movie_box").hover(function() {
		var html_cz = "<div class='kzqy_czbut'><a href='javascript:void(0)' class='sy'>上移</a><a href='javascript:void(0)'  class='xy'>下移</a><a href='javascript:void(0)'  class='bianji'>编辑</a><a href='javascript:void(0)' class='del' >删除</a></div>"
			$(this).css({
				"border": "1px solid #0099ff"
			});
		$(this).children(".wjdc_list").after(html_cz);
	}, function() {
		$(this).css({
			"border": "1px solid #fff"
		});
		$(this).children(".kzqy_czbut").remove();
		//$(this).children(".dx_box").hide(); 
	});

	//下移
	$(".xy").live("click", function() {
		var leng = $(".yd_box .movie_box").length;			// 问题控件的数量
		var dqgs = $(this).parents(".movie_box").index();	// 当前序号

		if(dqgs < leng - 1) {
			var czxx = $(this).parent(".kzqy_czbut").parent(".movie_box");
			var xyghtml = czxx.next().html();
			var syghtml = czxx.html();
			czxx.next().html(syghtml);
			czxx.html(xyghtml);
			
			// 改变控件序号
			czxx.children(".wjdc_list").find(".nmb").text(dqgs + 1);
			czxx.next().children(".wjdc_list").find(".nmb").text(dqgs + 2);
			
			// 交换问题
			var qTemp = naire.questionList[dqgs];
			naire.questionList[dqgs] = naire.questionList[dqgs+1];
			naire.questionList[dqgs+1] = qTemp;
			
			// 改变数据题目顺序编号
			naire.questionList[dqgs].orderNumber = dqgs + 1;
			naire.questionList[dqgs+1].orderNumber = dqgs + 2 ;
			
			// 编辑时更新数据，添加时会找不到这个函数
			try {
				changeQuestionOrder(dqgs, dqgs+1);
			} catch(e) {}
		} else {
			infoTip("到底了");
		}
	});
	//上移
	$(".sy").live("click", function() {
		var dqgs = $(this).parents(".movie_box").index();
		if(dqgs > 0) {
			var czxx = $(this).parents(".movie_box");
			var xyghtml = czxx.prev().html();
			var syghtml = czxx.html();
			czxx.prev().html(syghtml);
			czxx.html(xyghtml);
			
			// 改变控件序号
			czxx.children(".wjdc_list").find(".nmb").text(dqgs + 1);
			czxx.prev().children(".wjdc_list").find(".nmb").text(dqgs);
			
			// 交换问题
			var qTemp = naire.questionList[dqgs];
			naire.questionList[dqgs] = naire.questionList[dqgs-1];
			naire.questionList[dqgs-1] = qTemp;
			
			// 改变数据题目顺序编号
			naire.questionList[dqgs].orderNumber = dqgs + 1;
			naire.questionList[dqgs-1].orderNumber = dqgs;
			
			// 编辑时更新数据
			try {
				changeQuestionOrder(dqgs, dqgs-1);
			} catch(e) {}
		} else {
			infoTip("到头了");
		}
	});
	//删除
	$(".del").live("click", function() {
		// 删除自己
		var czxx = $(this).parent(".kzqy_czbut").parent(".movie_box");
		czxx.remove();
		
		// 从问题集合中删除当前问题		
		var index = parseInt($(this).parent().parent().find(".nmb").html());
		naire.questionList.splice(index-1, 1);
		
		// 给所有问题重新编号
		index = 1;		
		$(".nmb").each(function(){
			$(this).html(index++);
		});
	});

	//编辑
	$(".bianji").live("click", function() {
		//编辑的时候禁止其他操作   
		$(this).siblings().hide();
		//$(this).parent(".kzqy_czbut").parent(".movie_box").unbind("hover"); 
		var dxtm = $(".dxuan").html();
		var duoxtm = $(".duoxuan").html();
		var tktm = $(".tktm").html();
		var jztm = $(".jztm").html();
		//接受编辑内容的容器
		var dx_rq = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".dx_box");
		var title = dx_rq.attr("data-t");
		
		//题目选项的个数
		var timlrxm = $(this).parent(".kzqy_czbut").parent(".movie_box").children(".wjdc_list").children("li").length;
		
		//单选题目
		if(title == 0) {
			dx_rq.show().html(dxtm);
			//模具题目选项的个数
			var bjxm_length = dx_rq.find(".title_itram").children(".kzjxx_iteam").length;
			var dxtxx_html = dx_rq.find(".title_itram").children(".kzjxx_iteam").html();
			//添加选项题目
			for(var i_tmxx = bjxm_length; i_tmxx < timlrxm - 1; i_tmxx++) {
				dx_rq.find(".title_itram").append("<div class='kzjxx_iteam'>" + dxtxx_html + "</div>");
			}
			//赋值文本框 
			//题目标题
			var texte_bt_val = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text();
			dx_rq.find(".btwen_text").val(texte_bt_val);
			//遍历题目项目的文字
			var bjjs = 0;
			$(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").each(function() {
				//可选框框
				var ktksfcz = $(this).find("input").hasClass("wenb_input");
				if(ktksfcz) {
					var jsxz_kk = $(this).index();
					dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(jsxz_kk - 1).find("label").remove();
				}
				//题目选项
				var texte_val = $(this).find("span").text();
				dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(bjjs - 1).find(".input_wenbk").val(texte_val);
				bjjs++

			});
		}
		//多选题目  
		if(title == 1) {
			dx_rq.show().html(duoxtm);
			//模具题目选项的个数
			var bjxm_length = dx_rq.find(".title_itram").children(".kzjxx_iteam").length;
			var dxtxx_html = dx_rq.find(".title_itram").children(".kzjxx_iteam").html();
			//添加选项题目
			for(var i_tmxx = bjxm_length; i_tmxx < timlrxm - 1; i_tmxx++) {
				dx_rq.find(".title_itram").append("<div class='kzjxx_iteam'>" + dxtxx_html + "</div>");
				//alert(i_tmxx);
			}
			//赋值文本框 
			//题目标题
			var texte_bt_val = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text();
			dx_rq.find(".btwen_text").val(texte_bt_val);
			//遍历题目项目的文字
			var bjjs = 0;
			$(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").each(function() {
				//可选框框
				var ktksfcz = $(this).find("input").hasClass("wenb_input");
				if(ktksfcz) {
					var jsxz_kk = $(this).index();
					dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(jsxz_kk - 1).find("label").remove();
				}
				//题目选项
				var texte_val = $(this).find("span").text();
				dx_rq.find(".title_itram").children(".kzjxx_iteam").eq(bjjs - 1).find(".input_wenbk").val(texte_val);
				bjjs++

			});
		}
		//填空题目
		if(title == 2) {
			dx_rq.show().html(tktm);
			//赋值文本框 
			//题目标题
			var texte_bt_val = $(this).parent(".kzqy_czbut").parent(".movie_box").find(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text();
			dx_rq.find(".btwen_text").val(texte_bt_val);
		}
		//矩阵题目
		if(title == 3) {
			dx_rq.show().html(jztm);
		}
	});

	//增加选项  
	$(".zjxx").live("click", function() {
		var zjxx_html = $(this).prev(".title_itram").children(".kzjxx_iteam").html();
		$(this).prev(".title_itram").append("<div class='kzjxx_iteam'>" + zjxx_html + "</div>");
	});

	//删除一行 
	$(".del_xm").live("click", function() {
		//获取编辑题目的个数
		var zuxxs_num = $(this).parents(".title_itram").children(".kzjxx_iteam").length;
		if(zuxxs_num > 1) {
			$(this).parent(".kzjxx_iteam").remove();
		} else {
			infoTip("手下留情");
		}
	});
	//取消编辑
	$(".dx_box .qxbj_but").live("click", function() {
		$(this).parent(".bjqxwc_box").parent(".dx_box").empty().hide();
		$(".movie_box").css({
			"border": "1px solid #fff"
		});
		$(".kzqy_czbut").remove();
		//            
	});
	// body...
	//完成编辑（编辑）
	$(".swcbj_but").live("click", function() {
		var jcxxxx = $(this).parent(".bjqxwc_box").parent(".dx_box"); //编辑题目区
		var querstionType = jcxxxx.attr("data-t"); //获取题目类型
		
		// 获取该问题的input标签类型
		var inputType = "";
		switch (parseInt(querstionType)) {
		case 0:inputType = "radio";break;
		case 1:inputType = "checkbox";break;
		case 2:inputType = "textarea";break;
		}
		
		// 当完成编辑时，若是修改问题，会先删除原来的问题，再重新添加保存
		// 若是新问题，则直接保存问题
		var index = parseInt($(this).parent().parent().parent().find(".nmb").html());
		var question = {
			questionDesc : jcxxxx.find(".btwen_text").val(),
			categoryId : parseInt(querstionType)+1,
			category : {
				id : parseInt(querstionType)+1,
				categoryDesc : inputType
			},
			orderNumber: $(this).parents(".movie_box").index()+1,
			optionList : []
		}
		naire.questionList.splice(index-1, 1, question);
		

		switch(querstionType) {
		case "0": //单选
		case "1": //多选	
			
			//编辑题目选项的个数
			var bjtm_xm_length = jcxxxx.find(".title_itram").children(".kzjxx_iteam").length; //编辑选项的 选项个数
			var xmtit_length = jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").length - 1; //题目选择的个数

			//赋值文本框 
			//题目标题
			var texte_bt_val_bj = jcxxxx.find(".btwen_text").val(); //获取问题题目
			jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text(texte_bt_val_bj); //将修改过的问题题目展示
			
			//删除选项
			for(var toljs = xmtit_length; toljs > 0; toljs--) {
				jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(toljs).remove();
			}
			//遍历题目项目的文字
			var bjjs_bj = 0;
			jcxxxx.children(".title_itram").children(".kzjxx_iteam").each(function() {
				//题目选项
				var texte_val_bj = $(this).find(".input_wenbk").val(); //获取填写信息
				var inputType = 'radio';
				//jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(bjjs_bj + 1).find("span").text(texte_val_bj);
				if(querstionType == "1") {
					inputType = 'checkbox';
				}
				var li = '<li><label><input name="a' + index + '" type="' + inputType + '" value=""><span>' + texte_val_bj + '</span></label></li>';
				jcxxxx.parent(".movie_box").children(".wjdc_list").append(li);

				bjjs_bj++;
				//可填空  
				var kxtk_yf = $(this).find(".fxk").is(':checked');
				if(kxtk_yf) {
					//第几个被勾选
					var jsxz = $(this).index();
					//alert(jsxz);
					jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(jsxz + 1).find("span").after("<input name='' type='text' class='wenb_input'>");
				}
				
				// 单选和多选才有选项
				// 保存问题的各个选项
				var option = {
					optionDesc : texte_val_bj,
					selectCount : 0
				}
				// 添加进问题集合
				question.optionList.push(option);
			});
			break;
			
		case "2":
			// 简答题没有选项
			var texte_bt_val_bj = jcxxxx.find(".btwen_text").val(); //获取问题题目
			jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text(texte_bt_val_bj); //将修改过的问题题目展示
			break;

		case "3": //矩阵
			jcxxxx.parent(".movie_box").children(".wjdc_list").children("table").children("tbody").empty();
			var querstionType = jcxxxx.find(".xzqk:checked").val();
			var title = jcxxxx.find("textarea.input_wenbk.btwen_text").val(); //获取标题
			var x_iteam = new Array(); //获取 横向选项	
			var y_iteam = " ," + jcxxxx.find(".leftbtwen_text").val(); //左标题
			jcxxxx.find(".title_itram").children("div.kzjxx_iteam").each(function() {
				var texte_val_bj = $(this).find(".input_wenbk").val(); //获取填写信息
				var checkbox = $(this).find("input.fxk").is(':checked'); //是否可填空
				x_iteam.push({
					name: texte_val_bj,
					checkbox: checkbox
				});

			});
			var y_iteams = y_iteam.split(",");
			for(var item in y_iteams) { //行
				var tr = '<tr>',
				td = '';
				td += '<td>' + y_iteams[item] + '</td>';
				for(var i = 0; i < x_iteam.length; i++) { //列
					if(item != 0) {
						if(x_iteam[i].checkbox) {
							//可填空  
							td += '<td><input name="c1" type="text" value=""></td>';

						} else {
							var inputType = 'radio';
							if(querstionType == "1") {
								inputType = 'checkbox';
							}
							td += '<td><input name="c1" type="' + inputType + '" value=""> </td>';
						}
					} else {
						td += '<td>' + x_iteam[i].name + '</td>';
					}
				}
				jcxxxx.parent(".movie_box").children(".wjdc_list").children("table").children("tbody").append(tr + td);
			}
			break;
		}
		// 隐藏编辑面板
		$(this).parent(".bjqxwc_box").parent(".dx_box").empty().hide();
		
		// 只在新增问卷时缓存，编辑时不缓存
		if (naire.id == undefined) {
			// 缓存数据，避免问卷数据没保存时，本页面被异常关闭
			setLocalStorage("unsavedNaireData", naire);
		}
		
	});
});

// 添加问题控件
function addQuestion(index) {
	index += "";
	if (index == "-1") {
		return;
	}
	
	var movie_box = '<div class="movie_box" style="border: 1px solid #fff;"></div>';
	var Grade = $(".yd_box").find(".movie_box").length + 1;
	
	switch (index) {
	case "0": //单选
	case "1": //多选
	case "2": //问答
		var wjdc_list = '<ul class="wjdc_list"></ul>'; //问答 单选 多选
		var danxuan = "";
		if (index == "0") {
			danxuan = '【单选】';
		} else if (index == "1") {
			danxuan = '【多选】';
		} else if (index == "2") {
			danxuan = '【简答】';
		}

		wjdc_list = $(wjdc_list).append(' <li><div class="tm_btitlt"><i class="nmb">' + Grade + '</i>. <i class="btwenzi">请编辑问题？</i><span class="tip_wz">' + danxuan + '</span></div></li>');
		if (index == "2") {
			wjdc_list = $(wjdc_list).append('<li>  <label> <textarea name="" cols="" rows="" class="input_wenbk btwen_text btwen_text_dx" ></textarea></label> </li>');
		}
		movie_box = $(movie_box).append(wjdc_list);
		movie_box = $(movie_box).append('<div class="dx_box" data-t="' + index + '"></div>');

		break;
	case "3":
		var wjdc_list = ' <div class="wjdc_list"><h4 class="title_wjht"><i class="nmb">' + Grade + '</i>. 请编辑问题!<span class="tip_wz">【矩阵】</span></h4>'
		+' <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tswjdc_table"><tbody></tbody></table></div>'; //问答 单选 多选

		movie_box = $(movie_box).append(wjdc_list);
		movie_box = $(movie_box).append('<div class="dx_box" data-t="' + index + '"></div>');
		break;

	}



	$(movie_box).hover(function() {
		var html_cz = "<div class='kzqy_czbut'><a href='javascript:void(0)' class='sy'>上移</a><a href='javascript:void(0)'  class='xy'>下移</a><a href='javascript:void(0)'  class='bianji'>编辑</a><a href='javascript:void(0)' class='del' >删除</a></div>"
			$(this).css({
				"border": "1px solid #0099ff"
			});
		$(this).children(".wjdc_list").after(html_cz);
	}, function() {
		$(this).css({
			"border": "1px solid #fff"
		});
		$(this).children(".kzqy_czbut").remove();
		//$(this).children(".dx_box").hide(); 
	});
	$(".yd_box").append(movie_box);
}

// 未选项提示缩放效果
function showQTitleTip(qTitle) {
	qTitle.css({
		"background": "#75b6f5",
		"color": "#fff",
		"transform": "scale(1.2)",
		"transition": "0.4s ease-in-out"
	});
	setTimeout(function(){
		qTitle.css({
			"background": "#fff",
			"color": "#000",
			"transform": "scale(1)",
		});
	}, 500);
}

// 问卷检查
function check(){
	// 先检查用户是否填完必填项
	// 空格正则表达式
	var spaceRegexp = /^\s*$/;
	// 检查问卷标题
	if (spaceRegexp.test(naire.title)) {
		warnTip('请填写完毕！');
		$("#naireTitle2").trigger("focus");
		return false;
	}
	// 检查问卷描述
	if (spaceRegexp.test(naire.naireDesc)) {
		warnTip('请填写完毕！');
		$(".desc").trigger("focus");
		return false;
	}
	// 检查问卷分类
	if (naire.categoryId == 0 || naire.categoryId == undefined) {
		warnTip('请选择问卷类型！');
		var qTitle = $(".category_nav .qtitle");
		showQTitleTip(qTitle);
		return false;
	}
	// 检查问卷题目数量
	if(naire.questionList.length == 0) {
		warnTip("你还没有添加任何问题！");
		var qTitle = $(".question_nav .qtitle");
		showQTitleTip(qTitle);
		return false;
	}
	
	return true;
}

// 初始化折叠事件
function initFolder() {
	$(".qtitle").on("click", function() {
		var span = $(this).find("span");
		$(this).next().slideToggle(function() {
			var flag = span.attr("flag");
			if (flag == "1") {
				span.html("-").attr("flag", "0");
			} else {
				span.html("+").attr("flag", "1");
			}
		});
	});
}

// 读取未保存的问卷数据
function readUnsavedNaireData() {
	// 每当问卷数据改变时，将最新的数据缓存
	$(".edit_content").on("change", ":input", function() {
		setLocalStorage("unsavedNaireData", naire);
	});
	// 每次加载本页面时读取缓存
	var naireData = getLocalStorage("unsavedNaireData");
	if (naireData != null) {
		var dblChoseAlert = simpleAlert({
			content : '系统检测到你有未保存的数据，是否恢复？',
			buttons : {
				'是' : function () {
					restoreData(naireData);
					dblChoseAlert.close();
				},
				'否' : function () {
					removeLocalStorage('unsavedNaireData')
					dblChoseAlert.close();
				}
			}
		});		
	}
}

// 恢复数据
function restoreData(naireData) {
	naire = $.extend(naire, naireData);
	// 问卷填值
	$("#naireTitle1, #naireTitle2").val(naire.title);
	$(".desc").val(naire.naireDesc);
	$("#category a[value='"+naire.categoryId+"']").addClass("active").siblings().removeClass("active");
	
	// 添加问题插件
	$.each(naire.questionList, function(i, question){
		addQuestion(question.categoryId-1);
	})
	
	// 填充问题数据
	$(".yd_box").find(".movie_box").each(function(){
		var $this = $(this);

		// 获取编号
		var index = parseInt($this.find(".nmb").html());
		var question = naire.questionList[index-1];

		// 题目填值
		$(this).find(".btwenzi").html(question.questionDesc);

		// 问题类型
		var inputType = question.category.categoryDesc;
		switch(question.categoryId-1) {
		case 0:		// 单选题
		case 1:		// 多选题
			// 遍历问题选项
			$.each(question.optionList, function(i, option){
				// 添加问题
				var li = '<li><label><input name="a' + index + '" type="' + inputType + '" value=""><span>' + option.optionDesc + '</span></label></li>';
				$this.find(".wjdc_list").append(li);
			});
			break;
		case 2:		// 简答题
			break;
		}
	});
}



//初始化主题
function initSubject() {
	lastSubject = naire.subject;
	lastPhoneStyle = naire.phoneStyle;	
	
	// 打开皮肤dialog
	$(".subject a").on("click", function() {
		// 默认先显示pc皮肤区
		$(".mask .content:eq(0)").show();
		$(".mask .content:eq(1)").hide();
		
		// 记录原始皮肤id
		$(".mask .nav").attr("subject", naire.subject);
		$(".mask .nav").attr("phoneStyle", naire.phoneStyle);
		
		// 显示已选pc皮肤
		var subjects = [
			'.defaultSubject', 		// 默认主题
			'.graySubject', 		// 灰色主题
			'.redSubject', 			// 红色主题
			'.blackSubject'			// 黑色主题
		];
		$(".pc .single").removeClass("theme_active");
		$(subjects[naire.subject]).addClass("theme_active");
		// 显示已选phone皮肤
		$(".phone>div").removeClass("theme_active");
		$(".phone>div[value='" + naire.phoneStyle + "']").addClass("theme_active");
		// 显示皮肤dialog
		$(".mask").show();
	});
	
	// 导航切换事件
	$(".mask .nav a").on("click", function() {
		$(".content").eq($(this).index()).show().siblings(".content").hide();
	});
	
	// pc 皮肤选取
	$(".mask .content .single").on("click", function() {
		$(this).siblings().removeClass("theme_active");
		$(this).addClass("theme_active");
		var subject = $(this).attr("subject");
		switch (subject) {
			case 'default': subject = 0; break;
			case 'gray'   : subject = 1; break;
			case 'red'	  : subject = 2; break;
			case 'black'  : subject = 3; break;
			default		  : subject = 0; break;
		}
		$(".mask .nav").attr("subject", subject);
	});
	
	// phone 皮肤	选取
	$(".mask .phone img").on("click", function() {
		$(this).parent().addClass("theme_active").siblings().removeClass("theme_active");
		$(".mask .nav").attr("phoneStyle", $(this).parent().index());
	});
	
	// 确认事件
	$(".mask .bottomBtn input:eq(0)").on('click', function() {
		// 记录问卷原始皮肤
		lastSubject = naire.subject;
		lastPhoneStyle = naire.phoneStyle;
		// 更改新皮肤
		naire.subject = parseInt($(".mask .nav").attr("subject"));
		naire.phoneStyle = parseInt($(".mask .nav").attr("phoneStyle"));
		if (naire.id == undefined) {
			setLocalStorage("unsavedNaireData", naire);
		}
	});

	
	$(".mask .bottomBtn input").on('click', function() {
		// 移除缓存信息并关闭dialog
		$(".mask .nav").removeAttr("subject").removeAttr("phoneStyle");
		$('.mask').hide();
	});
	
}

// 初始化共享事件
function initShare() {
	$("#share").attr("checked", naire.share);
	$("#share").on('change', function() {
		naire.share = $(this).is(":checked");
	});
	
	$('#isNeedPwd').on('change', function() {
		if ($(this).is(":checked")) {
			// 显示密码框
			$(this).hide().next().hide().next().show().focus();
		} else {
			$('#password').val('');
		}
	});
	$('#password').on('blur', function() {
		if (/^\s*$/.test($(this).val())) {
			naire.password = '';
			$('#isNeedPwd').removeAttr('checked');
		} else {
			// 记录密码
			naire.password = $(this).val();			
		}
		$(this).hide().siblings().show();
	});
	if (/^(\d|\a)+$/.test(naire.password)) {
		$('#isNeedPwd').attr('checked', 'checked');
		$('#password').val(naire.password);
	}
}

// 初始化问卷导入事件
function initNaireImport() {
	$('.left_nav a:last-child').on('click', function() {
		var cActive = $('#category a.active');
		if (cActive.length == 1) {
			window.location.href = 'naire/import?c=' + cActive.attr('value');
		} else {
			showQTitleTip($(".category_nav .qtitle"));
			infoTip('请先选择问卷类型！');
		}
	});
}