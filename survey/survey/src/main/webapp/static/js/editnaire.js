$(function(){
	
	// 数据报表链接
	$(".left_nav").children(":eq(2)").attr("href", "naire/statistics/"+naire.id);
	
	// 对正在收集的问卷进行提示
	if (naire.status == 2) {
		var contentTip = "该问卷正在收集数据, 您确定要现在编辑问卷么？";
		if (naire.collectCount > 0) {
			contentTip = "该问卷已收集到" + naire.collectCount + "份数据, 您确定要现在编辑问卷么？";
		}
		var dblChoseAlert = simpleAlert({
			content : contentTip,
			buttons : {
				'是' : function () {
					dblChoseAlert.close();
				},
				'否' : function () {
					window.location.href = "naire/list";
				}
			}
		});	
	}
	
	
	// 问卷填值
	$("#naireTitle1, #naireTitle2").val(naire.title);
	$(".desc").val(naire.naireDesc);
//	$("#category a[value='"+naire.categoryId+"']").addClass("active");
	
	$("#category a").unbind("click").on("click", function(){
		// 所选问卷类型
		var categoryId = parseInt($(this).attr("value"));
		
		// 防止重复更新
		if (naire.categoryId == categoryId) {
			return;
		}
		// 界面更新
		$(this).removeClass("default").addClass("active").siblings().removeClass("active").addClass("default");
		
		// 封装数据
		var data = {
			id : naire.id,
			categoryId : categoryId
		};
		var category = {
			id : categoryId,
			categoryDesc : $(this).html()
		};
		
		// 发出请求
		$.ajax({
			url : 'naire/update',
			type : 'post',
			timeout : 2000,
			data : data,
			dataType : "json",
			error : function() {
				infoTip("服务器异常！！！");
				// 问卷类型还原
				$("#category a[value='"+naire.categoryId+"']").removeClass("default").addClass("active").siblings().removeClass("active").addClass("default");
			},
			success : function(data) {
				if (data.status == 200) {
					successTip('修改成功！');
					naire.categoryId = categoryId;
					naire.category = category;
				} else {
					// 问卷类型还原
					$("#category a[value='"+naire.categoryId+"']").removeClass("default").addClass("active").siblings().removeClass("active").addClass("default");
					errorTip(data.msg);
				}
			}
		});
		
	});
	
	
	$("#share").on('change', function() {
		// 封装数据
		var data = {
			id : naire.id,
			share : naire.share
		};
		// 发出请求
		$.ajax({
			url : 'naire/update',
			type : 'post',
			timeout : 2000,
			data : data,
			dataType : "json",
			error : function() {
				infoTip("服务器异常！！！");
				// 还原状态
				$("#share").attr("checked", !naire.share);
			},
			success : function(data) {
				if (data.status == 200) {
					successTip('修改成功！');
				} else {
					// 还原状态
					$("#share").attr("checked", !naire.share);
					errorTip(data.msg);
				}
			}
		});
		
	});
	
	$("#isNeedPwd").on('change', function() {
		if (!$(this).is(":checked")) {
			// 清空密码
			changePwd('');
		}
	});
	
	$('#password').on('blur', function() {
		if (/^\s*$/.test($(this).val())) {
			$("#isNeedPwd").removeAttr('checked');
			return;
		}
		changePwd($('#password').val());
	});
	
	$(".mask .bottomBtn input:eq(0)").on("click", function() {
		// 封装数据
		var data = {
			id : naire.id,
			subject : naire.subject,
			phoneStyle : naire.phoneStyle
		};
		// 发出请求
		$.ajax({
			url : 'naire/update',
			type : 'post',
			timeout : 2000,
			data : data,
			dataType : "json",
			error : function(XMLHttpRequest) {
				// 还原皮肤
				naire.subject = lastSubject;
				naire.phoneStyle = lastPhoneStyle;
				errorTip("网络错误！");
			},
			success : function(data) {
				if (data.status == 200) {
					successTip('应用主题成功！');
					// 记录新皮肤
					lastSubject = naire.subject;
					lastPhoneStyle = naire.lastPhoneStyle;
				} else {
					// 还原皮肤
					naire.subject = lastSubject;
					naire.lastPhoneStyle = lastPhoneStyle;
					infoTip(data.msg);
				}
			}
		});
	});
	


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
		switch(question.categoryId-1){
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

	// 重写删除事件
	$(".del").die().live("click", function() {
		// 当前问题插件
		var questionPlugin = $(this).parent(".kzqy_czbut").parent(".movie_box");

		// 获取问题编号
		var index = parseInt($(this).parent().parent().find(".nmb").html());
		
		// 对于没保存的问题直接删除控件
		if (naire.questionList[index-1] == undefined) {
			successTip('删除成功！');
			// 删除当前问题插件
			questionPlugin.remove();
			// 给所有问题重新编号
			index = 1;		
			$(".nmb").each(function(){
				$(this).html(index++);
			});
			return;
		}
		// 取出问题ID
		var questionId = naire.questionList[index-1].id;
		// 发出请求
		$.ajax({
			url : 'question/delete',
			type : 'post',
			timeout : 2000,
			data : {questionId: questionId, naireId: naire.id},
			dataType : "json",
			error : function() {
				infoTip("服务器异常！！！");
			},
			success : function(data) {
				if (data.status == 200) {
					successTip('删除成功！');
					// 删除数据
					naire.questionList.splice(index-1, 1);
					// 删除当前问题插件
					questionPlugin.remove();
					// 给所有问题重新编号
					index = 1;		
					$(".nmb").each(function(){
						$(this).html(index++);
					});
				} else {
					errorTip(data.msg);
				}
			}
		});

	});

	// 重写删除选项事件
	$(".del_xm").die().live("click", function() {
		// 所有选项的个数包括已存在的和新增的
		var zuxxs_num = $(this).parents(".title_itram").children(".kzjxx_iteam").length;
		// 选项序号(从0开始)
		var optionIndex = $(this).parent(".kzjxx_iteam").index();		
		// 当前题目编号(从1开始)
		var questionIndex = parseInt($(this).parent().parent().parent().parent().find(".nmb").html());
		var question = naire.questionList[questionIndex-1];
		
		if (question.optionList[optionIndex] != undefined) {
			// 已存在选项的删除逻辑
			if(question.optionList.length > 1) {
				var $this = $(this);
				// 根据选项编号获取选项ID
				var optionId = question.optionList[optionIndex].id;
				// 发出请求
				$.ajax({
					url : 'option/delete',
					type : 'post',
					timeout : 2000,
					data : {optionId: optionId, naireId: naire.id},
					dataType : "json",
					error : function() {
						infoTip("服务器异常！！！");
					},
					success : function(data) {
						if (data.status == 200) {
							successTip('删除成功！');
							// 删除数据
							question.optionList.splice(optionIndex, 1);
							// 删除选项插件
							$this.parent().parent().parent().parent().find(".wjdc_list").children("li").eq(optionIndex+1).remove();
							$this.parent(".kzjxx_iteam").remove();
						} else {
							errorTip(data.msg);
						}
					}
				});
			} else {
				infoTip("手下留情");
			}
		} else {
			// 新增选项的删除逻辑
			if(zuxxs_num > 1) {
				$(this).parent(".kzjxx_iteam").remove();
			} else {
				infoTip("手下留情");
			}
		}
	});



	// 重写完成编辑事件
	$(".swcbj_but").die().live("click", function() {
		var $this = $(this);
		var jcxxxx = $(this).parent(".bjqxwc_box").parent(".dx_box"); //编辑题目区
		var index = parseInt($(this).parent().parent().parent().find(".nmb").html());

		// 如果当前问题的编号比已存在的问题数量还大，说明为新增问题
		// 以下为新增逻辑...
		if (index > naire.questionList.length) {
			// 获取题目类型
			var questionType = jcxxxx.attr("data-t");
			// 定义新问题
			var newQuestion = {
				naireId : naire.id,
				questionDesc : jcxxxx.find(".btwen_text").val(),
				categoryId : parseInt(questionType)+1,
				optionList : [],
				orderNumber : index
			}

			var inputType = "";
			switch (parseInt(questionType)) {
				case 0:inputType = "radio";		break;
				case 1:inputType = "checkbox";	break;
				case 2:inputType = "textarea";	break;
			}
			

			// 遍历选项，生成新选项插件及数据
			jcxxxx.children(".title_itram").children(".kzjxx_iteam").each(function(i) {
				// 获取选项内容
				var texte_val_bj = $(this).find(".input_wenbk").val();
				// 定义选项体
				var option = {
					optionDesc : texte_val_bj,
					selectCount : 0
				}
				// 添加进问题集合
				newQuestion.optionList.push(option);
			});

			// 发出请求
			$.ajax({
				url : 'question/save',
				type : 'post',
				timeout : 2000,
				data : JSON.stringify(newQuestion),
				dataType : "json",
				contentType : 'application/json',
				error : function() {
					infoTip("服务器异常！！！");
				},
				success : function(data) {
					if (data.status == 200) {
						successTip('保存成功！');
						var q = JSON.parse(data.msg);
						// 更新问卷数据
						naire.questionList.push(q);

						// 更新题目插件
						jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text(q.questionDesc);
						// 添加选项插件
						$.each(q.optionList, function(i, option) {							
							var li = '<li><label><input name="a' + index + '" type="' + inputType + '" value=""><span>' + option.optionDesc + '</span></label></li>';
							jcxxxx.parent(".movie_box").children(".wjdc_list").append(li);
						});
						//	console.log(JSON.stringify(naire));

						// 隐藏编辑面板
						$this.parent(".bjqxwc_box").parent(".dx_box").empty().hide();
					} else {
						errorTip(data.msg);
					}
				}
			});
		} else { 		// 以下为修改逻辑...

			// 获取问题数据
			var question = naire.questionList[index-1];

			// 更新题目数据
			question.questionDesc = jcxxxx.find(".btwen_text").val();
			// 显示修改过后的题目
			jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(0).find(".tm_btitlt").children(".btwenzi").text(question.questionDesc);

			// 删除原来的选项插件
			var xmtit_length = jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").length - 1;
			for(var toljs = xmtit_length; toljs > 0; toljs--) {
				jcxxxx.parent(".movie_box").children(".wjdc_list").children("li").eq(toljs).remove();
			}

			// 遍历选项，生成新选项插件及数据
			jcxxxx.children(".title_itram").children(".kzjxx_iteam").each(function(i) {
				// 题目选项
				var texte_val_bj = $(this).find(".input_wenbk").val(); //获取填写信息
				// 更新选项数据
				var option = question.optionList[i];
				// 对于已存在的选项直接更新
				if (option != undefined) {
					option.optionDesc = texte_val_bj;
				} else {
					// 对于新添加的选项，需添加
					option = {
						optionDesc: texte_val_bj,
						questionId: question.id,
						selectCount: 0
					}
					question.optionList.push(option);
				}
				
				// 添加修改后的选项插件
				var li = '<li><label><input name="a' + index + '" type="' + question.category.categoryDesc + '" value=""><span>' + texte_val_bj + '</span></label></li>';
				jcxxxx.parent(".movie_box").children(".wjdc_list").append(li);
			});

			// 隐藏编辑面板
			$(this).parent(".bjqxwc_box").parent(".dx_box").empty().hide();


			
			var data = {
				id: question.id,
				naireId: naire.id,
				questionDesc: question.questionDesc,
				optionList: question.optionList
			}
		//	console.log(JSON.stringify(data));
			// 发出请求
			$.ajax({
				url : 'question/update',
				type : 'post',
				timeout : 2000,
				data : JSON.stringify(data),
				dataType : "json",
				contentType : 'application/json',
				error : function() {
					infoTip("服务器异常！！！");
				},
				success : function(data) {
					if (data.status == 200) {
						successTip('修改成功！');
						if (data.msg != null) {
							// 将原来的问题数据替换为最新的数据
							naire.questionList.splice(index-1, 1, data.msg);
						}
					} else {
						errorTip(data.msg);
					}
				}
			});
		}
	});

	var original;
	$("#naireTitle1, #naireTitle2").on("click", function(){
		original = $(this).val();
	});

	// 实时更新标题
	$("#naireTitle1, #naireTitle2").blur(function(){
		// 当标题没变化时不更新
		if (original == $(this).val()) {
			return;
		}

		// 封装数据
		var data = {
			id : naire.id,
			title : $(this).val()
		}
		// 发出请求
		$.ajax({
			url : 'naire/update',
			type : 'post',
			timeout : 2000,
			data : data,
			dataType : "json",
			error : function() {
				infoTip("服务器异常！！！");
			},
			success : function(data) {
				if (data.status == 200) {
					successTip('修改成功！');
				} else {
					errorTip(data.msg);
				}
			}
		});
	});


	$(".desc").on("click", function(){
		original = $(this).val();
	});

	// 实时更新问卷描述
	$(".desc").blur(function(){
		// 没有变化不更新
		if (original == $(this).val()) {
			return;
		}
		// 封装数据
		var data = {
				id : naire.id,
				naireDesc : $(this).val()
		}
		// 发出请求
		$.ajax({
			url : 'naire/update',
			type : 'post',
			timeout : 2000,
			data : data,
			dataType : "json",
			error : function() {
				infoTip("服务器异常！！！");
			},
			success : function(data) {
				if (data.status == 200) {
					successTip('修改成功！');
				} else {
					errorTip(data.msg);
				}
			}
		});
	});

});

/**
 * 改变题目顺序
 * @param index1(题目序号)
 * @param index2(题目序号)
 * @returns
 */
function changeQuestionOrder(index1, index2) {
	updateQuestionOrder(index1);
	updateQuestionOrder(index2);
}

function updateQuestionOrder(index) {
	var data = {
		id : naire.questionList[index].id,
		naireId: naire.id,
		orderNumber : naire.questionList[index].orderNumber
	};
	$.ajax({
		url : 'question/update',
		type : 'post',
		timeout : 2000,
		data : JSON.stringify(data),
		dataType : "json",
		contentType : 'application/json',
		success : function(result) {
			if (result.status == 200) {
				successTip('修改成功！');
			} else {
				infoTip(result.msg);
			}
		},
		error : function() {
			errorTip("服务器异常！！！");
		}
	});
}


function changePwd(pwd) {
	// 发出请求
	$.ajax({
		url : 'naire/update',
		type : 'post',
		timeout : 2000,
		data : {id: naire.id, password: pwd},
		dataType : "json",
		error : function() {
			infoTip("网络错误！");
		},
		success : function(data) {
			if (data.status == 200) {
				successTip('修改成功！');
				if (pwd == '') {
					$("#isNeedPwd").removeAttr('checked');
				}
			} else {
				errorTip(data.msg);
			}
		}
	});
	
}