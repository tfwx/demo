var naireJson;
var flag = false;

$(function() {
	
	init();
	initFileUpload();
	
	window.onbeforeunload = function() {
		if (!flag) {
			return '';
		}
	}
	
});


function init() {
	$('.data textarea').on('input', function() {
		var content = $(this).val();
		content = content.replace(/(\n)+/g, '#').replace(/\s*/g, '').replace(/#+/g, '#').replace(/^#/, '').replace(/【/g, '[').replace(/】/g, ']');
		var naire = parseNaireData(content);		
		createPreview(naire);
		naireJson = naire;
	}).trigger('input');
	
	$('#createNaire').on('click', function() {
		createNaire();
	});
}

// 解析问卷数据，生成json
function parseNaireData(content) {
	var originalData = content.split('#');
	var naire = {
		title: originalData[0] == undefined ? '' : originalData[0],
		naireDesc: originalData[1] == undefined ? '' : originalData[1],
		questionList: []
	};
	var orderNumberCount = 0;
	for (var i = 0; i < originalData.length; i++) {
		if (originalData[i] != '') {
			if (isQuestion(originalData[i])) {
				orderNumberCount++;
				var questionDesc = getQuestion(originalData[i]);
				// 消去前面的数字序号
				if (questionDesc.startsWith(orderNumberCount)) {
					questionDesc = questionDesc.substr((orderNumberCount+'').length);
				}
				var question = {
					questionDesc: questionDesc,
					categoryId: getQuestionType(originalData[i]),
					category: {
						id: getQuestionType(originalData[i]),
						categoryDesc: getQuestionTypeDesc(originalData[i])
					},
					orderNumber: orderNumberCount,
					optionList: []
				}
				
				var index = 65;
				for (var j = i+1; j < originalData.length; j++) {
					if (originalData[j] != '') {
						if (isQuestion(originalData[j])) {
							i = j-1;
							break;
						} else {
							// 消去前面的字母序号
							var optionDesc = originalData[j];
							if (optionDesc.startsWith(String.fromCharCode(index++))) {
								optionDesc = optionDesc.substr(1);
							}
							var option = {
								optionDesc: optionDesc
							};
							question.optionList.push(option);
						}
					}
				}
				naire.questionList.push(question);
			}
		}
	
	}
	return naire;
}

// 生成预览界面
function createPreview(naire) {
	var content = $('.previewContent');
	content.empty();
	var title = '<h3>' + (naire.title == undefined ? '' : naire.title) + '</h3>';
	var desc = '<label>'+ (naire.naireDesc == undefined ? '' : naire.naireDesc) + '</label>';
	content.append(title).append(desc);
	$.each(naire.questionList, function(i, e) {
		var box = $('<div class="box"></div>');
		content.append(box);
		var questionDesc = '<label>' + (i+1) + '&nbsp;&nbsp;' + e.questionDesc + '</label>';
		box.append(questionDesc);
		
		var type;
		switch(e.categoryId) {
			case 1:
				type = 'radio';
			case 2:
				type = (type == undefined) ? 'checkbox' : type;
				var group = type + i;
				$.each(e.optionList, function(i, o) {
					var id = group + i;
					var option = ''
						+ '<div class="item pretty">'
						+ '<input type="'+ type + '" name="'+ group + '" id="' + id + '"><label for="' + id + '"><i class="default"></i>' + o.optionDesc + '</label>'
						+ '</div>'
					box.append(option);
				});
				break;
			case 3:
				var textarea = '<div class="item"><textarea></textarea></div>';
				box.append(textarea);
				break;
		}
		
	}); 
}
	

// 判断数据是否是题目
function isQuestion(data) {
	return (/\[单选题\]$/.test(data)) || (/\[多选题\]$/.test(data)) || (/\[简答题\]$/.test(data));
}

// 获取题目类型
function getQuestionType(data) {
	var qType = data.substr(data.length-5, 5);
	var obj = {
		'[单选题]': 1,
		'[多选题]': 2,
		'[简答题]': 3
	}
	return obj[qType];
}

function getQuestionTypeDesc(data) {
	var qType = data.substr(data.length-5);
	var obj = {
		'[单选题]': 'radio',
		'[多选题]': 'checkbox',
		'[简答题]': 'textarea'
	}
	return obj[qType];
}

// 获取问题
function getQuestion(data) {
	return data.substring(0, data.length-5);
}

// 生成问卷
function createNaire() {
	var naire = naireJson;
	var checkFlag = false;
	if (/^\s*$/.test(naire.title)) {
		infoTip('请正确填写问卷标题');
	} else if (/^\s*$/.test(naire.naireDesc)) {
		infoTip('请正确填写问卷描述');
	} else if (naire.questionList.length == 0) {
		infoTip('请至少添加一个问题');
	} else if (naire.questionList.length != 0) {
		$.each(naire.questionList, function(i, e) {
			if (e.optionList.length == 0 && e.categoryId != 3) {
				infoTip('请至少添加一个选项');
				return false;
			}
			if (i == naire.questionList.length-1) {
				checkFlag = true;
			}
		});
	}
	flag = checkFlag;
	if (!checkFlag) {
		return;
	}
	naire.categoryId = $('#c').val();
	var form = ''
		+ '<form action="naire/create" method="post">'
		+ '<input type="text" name="naireJson" >'
		+ '<input type="text" name="category" value="' + naire.categoryId + '" >'
		+ '</form>';
	form = $(form);
	form.find('input[name="naireJson"]').val(JSON.stringify(naire));
	form.submit();
}



/**
 * 初始化文档上传事件
 * @returns
 */
function initFileUpload() {
	$('#file').on('change', function() {	  
	    var fileObj = $(this)[0].files[0];    
	    $(this).val('');
	    if (fileObj == undefined || fileObj.size < 0) {
	    	return;
	    }
	    
	    var fileName = fileObj.name;
	    if (fileName.endsWith('.txt')) {
	    	readFromTxt(fileObj, function(data) {
	    		$('.data textarea').val(data).trigger('input');
	    	});
	    } else if (fileName.endsWith('.doc') || fileName.endsWith('.docx')) {
	    	readFromWord(fileObj, function(data) {
	    		$('.data textarea').val(data).trigger('input');
	    	});
	    } else {
	    	infoTip('仅支持txt文件和word文档');
	    }
	});
	
}


/**
 * 读取txt文件内容
 */
function readFromTxt(file, callback) {
	showLoading('文件解析中...');
    var reader = new FileReader();//这里是核心！！！读取操作就是由它完成的。
    reader.readAsText(file, 'gbk');//读取文件的内容，注意编码方式
    reader.onload = function(){
    	callback(this.result);
    	hideLoading();
    };
}

/**
 *读取word文档内容（上传文件至服务器解析数据后返回）
 */
function readFromWord(file, callback) {
	// ajax上传文件
    var formFile = new FormData();
    formFile.append("file", file);
    
    $.ajax({
        url: "word/parse",
        data: formFile,
        timeout: 5000,
        type: "post",
        dataType: "json",
        cache: false,
        processData: false,	//用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        success: function (result) {
        	if (result.status == 200) {
        		callback(result.msg);
        	} else {
        		console.log(result.msg);
        		infoTip('Word文档格式错误');
        	}
        },
        error: function(XMLHtppRequest) {
        	console.log(JSON.stringify(XMLHtppRequest));
        	errorTip('网络错误');
        },
        beforeSend: function() {
        	 showLoading('文件解析中...');
        },
        complete: function() {
        	hideLoading();
        }
    });
}
