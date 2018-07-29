
var posterImgFlag;

$(function() {
	init();
	initNaireLinkAndQRCode();
	initChangePoster();
	initDownloadCodePoster();
	initShareTo();
	initCopyLink();
	initUploadPoster();
	iniTooltipster();
});

// 初始化问卷访问地址和二维码图片
function initNaireLinkAndQRCode() {
	$('.poster .naire_link').val($('base').attr('href') + 'naire/preview/' + naire.id);
	$("#code").qrcode({
	    render: "canvas",
	    text: $('.poster .naire_link').val()
	});
}

// 初始化切换海报事件
function initChangePoster() {
	$('#posterTable td img').on('click', function() {
		$('#posterTable td').removeClass('theme_active');
		$(this).parent().addClass('theme_active');
		$('.poster_view .preview').css('background-image', 'url(' + $(this).attr('src') + ')');
	}).eq(0).trigger('click');
}

//初始上传海报事件
function initUploadPoster() {
	var file = $('#upload_poster');
	var fileReader = new FileReader();  
	$('a.upload').on('click', function() {
		file.trigger('click');
	});
	file.on('change', function() {
		posterImgFlag = undefined;
		$('#posterTable td').removeClass('theme_active');
        // 将文件读取为DataUrl  
		fileReader.readAsDataURL(file[0].files[0]);  
        fileReader.onload = function(event) {
            $('.poster_view .preview').css('background-image', 'url(' + this.result + ')');
            file.val('');
        }  
	});
}

// 初始化下载事件
function initDownloadCodePoster() {
	$('.poster .download_btn').on('click', function() {
		var url = $('.poster .naire_link').val();
		downloadCodePoster(url);
	});
}



/**
 * 生成二维码海报下载(当isOnlyCreatePoster参数为true时，返回图片的dataURL)
 * @param codeUrl
 * @param isOnlyCreatePoster
 * @param callback
 * @returns
 */
function downloadCodePoster(codeUrl, isOnlyCreatePoster, callback) {
	if ($('canvas[poster]').length == 0) {
		$('body').append("<canvas poster width='400' height='800' style='display:none;' />");
	}
	var canvas = $('canvas[poster]')[0];
	// 清空画布(由于canvas每当高度或宽度被重设时，画布内容就会被清空)
	canvas.width = canvas.width;
	var ctx = canvas.getContext("2d");
	var imagePath = $(".poster_view .preview").css('background-image');
	imagePath = imagePath.replace("url(\"", "").replace("\")", "");
	
	var posterImg = new Image();
	posterImg.src = imagePath;
	// 加载图片
	posterImg.onload = function() {
		
		// 选取图片中心位置
		var zoomRatio = 0.5;
		var height = posterImg.height;
		var width = height * zoomRatio;
		if (posterImg.width < width) {
			width = posterImg.width;
		}
		
		var offsetX = posterImg.width/2.0 - width/2.0;
		var offsetY = 0;
		if (offsetX < 0) {
			offsetX = 0;
		}
		// 绘制海报背景
		ctx.drawImage(posterImg, offsetX, offsetY, width, height, 0, 0, 400, 800);
		
		// 绘制二维码
		ctx.fillStyle = 'white';
		ctx.fillRect(20, 20, 130, 150);
		var codeImage = $("#code").find('canvas')[0];
		ctx.drawImage(codeImage, 35, 35, 100, 100);
		ctx.fillStyle = 'black';
		ctx.font = "12.5pt microsoft yahei";
		ctx.fillText("长按图片扫码", 35, 158);
		
		// 绘制title
		ctx.fillStyle = 'white';
		ctx.font = "30pt microsoft yahei";
		var title = $("#naireTitle1").val();
		title = title.length > 7 ? (title.substr(0, 7)+"···") : title;
		ctx.fillText(title, 35, 700);
		
		// 绘制logo
		ctx.font = "10pt microsoft yahei";
		ctx.fillText("倾听你在乎的", 35, 730);
		ctx.fillText("Listen to what you care", 35, 750);
		
		if (isOnlyCreatePoster == true) {
			callback(canvas.toDataURL('image/png'));
			return;
		}

		// 下载二维码
		$("<a download='二维码海报.png' href=" + canvas.toDataURL('image/png') + "></a>")[0].click();
	//	window.open(canvas.toDataURL('image/png'));
	}
}




// 初始化分享事件
function initShareTo() {
	var platformUrl = [
		'https://connect.qq.com/widget/shareqq/index.html',				 // QQ
		'http://service.weibo.com/share/share.php',						 // SINA
		'https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey'    // QQZone
		
	];
	$('.share_box img').on('click', function() {
		var index = $(this).index();
		if (index < platformUrl.length) {
			shareToOtherPlatform(platformUrl[index]);
		} else if (index == 3) {
			shareToWeixin();
		}
	});
}



function shareToOtherPlatform(url) {
	url += '?';
	var title = '我刚刚通过【问卷网】制作了一份问卷“' + $('#naireTitle2').val() + '”，需要您的回答，您的意见非常重要。请速帮忙回答。';
	var originalUrl = $('.poster .naire_link').val();
	var picPath = $('base').attr('href') + 'static/image/poster/codeposter.png';
	var options = {
		url: originalUrl,	// 分享来源
		title: '',			// 分享标题
	};
	if (url.indexOf('qq') != -1) {
		options.desc = title;
		options.pics = picPath;
	} else if (url.indexOf('weibo') != -1) {
		options.title = title;
		options.pic = picPath;
	}
	var params = [];
	for(var key in options){
		var value = options[key];
		if (value != '') {
			params.push(key + '=' + (value));
		}
	}
	url += params.join('&');
	$("<a target='_blank'></a>").attr('href', url)[0].click();
}


function initCopyLink() {
	var clipboard = new Clipboard(".link a");
	clipboard.on('success', function(e) {
		infoTip('快去分享吧');
	});
	clipboard.on('error', function(e) {
	    warnTip('复制失败');
	});
	
}




function iniTooltipster() {
	// 激活tooltipster
	$('#wx').tooltipster({
		trigger: 'click',
		theme: 'tooltipster-shadow' //使用shadow主题
	});
}


// 微信分享回调函数
function wxCallback(fileNme) {
	var serverHost = 'http://' + location.hostname;
	if (location.port != '') {
		host += ':'+location.port;
	}
	var posterUrl = serverHost + '/poster/' + fileNme;
	$("#posterCode").empty();
	$("#posterCode").qrcode({
        render: "canvas",
        width: 100,
        height: 100,
        text: posterUrl
    });
	$('#posterCode').show().siblings().hide();
	posterImgFlag = $('.theme_active>img').attr('flag');
}
// 初始化
function init() {
	// 设置预览界面的问卷标题
	var title = $("#naireTitle1").val();
	title = title.length > 7 ? (title.substr(0, 7)+"···") : title;
	$('.wj_ntitle').html(title);
	
	// 标记默认海报背景图
	$('#posterTable img').each(function(i, e) {
		$(this).attr('flag', i);
	});
}

/**
 * 分享至微信
 * @returns
 */
function shareToWeixin() {
	// 若上次选的海报图片已经生成二维码，直接显示即可
	if (posterImgFlag != undefined && posterImgFlag == $('.theme_active>img').attr('flag')) {
		setTimeout(function() {
			$('#posterCode').show().siblings().hide();
		}, 50);
		return;
	}
	
	// 延迟50毫秒（自己思考为什么）
	setTimeout(function() {
		$('#posterCode').hide().siblings().show();
	}, 50);
	
	var url = $('.poster .naire_link').val();
	downloadCodePoster(url, true, function(dataURL) {
		var fileName = 'poster' + (Math.random()+'').substr(2) + '.png';
		var file = dataURLtoFile(dataURL, fileName);
		uploadPosterFile(file, function() {
			// 延迟2秒，生成二维码(此处为强制延迟，体现等待效果)
			setTimeout(function() {
				wxCallback(fileName);
			}, 2000);
		});
	});	
}

// 上传二维码海报图片
function uploadPosterFile(file, callback) {
	// ajax上传文件
    var formFile = new FormData();
    formFile.append("file", file);
    
    $.ajax({
        url: "naire/upload/poster",
        data: formFile,
        timeout: 10000,
        type: "post",
        dataType: "json",
        cache: false,
        processData: false,	//用于对data参数进行序列化处理 这里必须false
        contentType: false, //必须
        success: function (result) {
        	if (result.status == 200) {
        		callback();
        	} else {
        		console.log(result.msg);
        		infoTip('二维码生成失败');
        	}
        },
        error: function(XMLHtppRequest) {
        	console.log(JSON.stringify(XMLHtppRequest));
        	errorTip('网络错误');
        }
    });
}

/**
 * dataURLtoFile
 * @param dataurl
 * @param filename
 * @returns
 */
function dataURLtoFile(dataurl, filename) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {type:mime});
}