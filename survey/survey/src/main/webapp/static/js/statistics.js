var naireId;
var barOptionArray = [];
var pieOptionArray = [];
var chartArray = [];
var eLoading;
var collectCount;

$(function() {
	naireId = Number($("#naireId").val());
	collectCount = Number($('#collectCount').val());
	
	initResetAnimation();
	
	// 收集份数为0时，不请求数据
	if (collectCount == 0) {
		$('.data_chart').show().append('<div class="tip">还未收集到任何数据！</div>');
		$('#export, .bar .option a, .reset a').on('click', function() {
			infoTip('还未收集到任何数据');
		});
		return;
	}
	
	loadData();
	initReset();
	initExport();
	initVersion();
	//loadAnimation();
	
});


//初始化加载动画
function initEhartLoading() {
	$("body").prepend('<div class="eLoading"><div></div></div>');
	var option = {
	    series: [{
	        type: 'liquidFill',
	        data: [0.6, 0.55, 0.4, 0.25],
	        radius: '90%',
	        outline: {
	            show: false
	        },
	        backgroundStyle: {
	            borderColor: '#009dff',
	            borderWidth: 1,
	            shadowColor: 'rgba(0, 0, 0, 0.4)',
	            shadowBlur: 20
	        },
	        shape: 'path://M367.855,428.202c-3.674-1.385-7.452-1.966-11.146-1.794c0.659-2.922,0.844-5.85,0.58-8.719 c-0.937-10.407-7.663-19.864-18.063-23.834c-10.697-4.043-22.298-1.168-29.902,6.403c3.015,0.026,6.074,0.594,9.035,1.728 c13.626,5.151,20.465,20.379,15.32,34.004c-1.905,5.02-5.177,9.115-9.22,12.05c-6.951,4.992-16.19,6.536-24.777,3.271 c-13.625-5.137-20.471-20.371-15.32-34.004c0.673-1.768,1.523-3.423,2.526-4.992h-0.014c0,0,0,0,0,0.014 c4.386-6.853,8.145-14.279,11.146-22.187c23.294-61.505-7.689-130.278-69.215-153.579c-61.532-23.293-130.279,7.69-153.579,69.202 c-6.371,16.785-8.679,34.097-7.426,50.901c0.026,0.554,0.079,1.121,0.132,1.688c4.973,57.107,41.767,109.148,98.945,130.793 c58.162,22.008,121.303,6.529,162.839-34.465c7.103-6.893,17.826-9.444,27.679-5.719c11.858,4.491,18.565,16.6,16.719,28.643 c4.438-3.126,8.033-7.564,10.117-13.045C389.751,449.992,382.411,433.709,367.855,428.202z',
	        label: {
	            normal: {
	                position: ['35%', '50%'],
	                formatter: function() {
	                    return 'Loading...';
	                },
	                textStyle: {
	                    fontSize: 30,
	                    color: '#fff'
	                }
	            }
	        }
	    }]
	};
	eLoading = echartsl.init($('.eLoading>div')[0]);
	eLoading.setOption(option);
	/*setTimeout(function() {
		loading.dispose();
		$('.eLoading').remove();
		fillDataChart();
	}, 5000);*/
}

// 销毁Echart加载动画
function destoryEchartLoading() {
	// 移除加载动画
	eLoading.dispose();
	$('.eLoading').remove();
}

// 初始化切换图表事件
function initVersion() {
	$('.bar .option a').on('click', function(e) {
		if ($(this).hasClass('active')) {
			// 阻止后序追加事件的执行（不是冒泡）
			e.stopImmediatePropagation();
			return false;
		} else if ($(this).attr('isLoad') == '1') {
			e.stopImmediatePropagation();
		}
		
		$(this).addClass('active').siblings('a').removeClass('active');
		$('.content').hide();
		$('.' + $(this).attr('content')).show();
	});
	$('#pie').on('click', function() {
		changeChart(pieOptionArray);
	});
	$('#bar').on('click', function() {
		changeChart(barOptionArray);
	});
	$('#ansDetail').on('click', function() {
		getAllSingleAns();
	});
	$('#area').on('click', function() {
		getAreaData();
	});

}

// 设置图表属性
function changeChart(options) {
	for (var i = 0; i < chartArray.length; i++) {
		var chart = chartArray[i];
		chart.clear();
		chart.setOption(options[i]);
	}
}



/**
 * 加载波浪动画(此方法被EChartsLoading代替)
 * @returns
 */
function loadAnimation() {
	$('body').append('<div class="mask"><div id="wave" class="wave"><span>0%</span></div></div>');
	var wave = (function () {
		var ctx;
		var waveImage;
		var canvasWidth;
		var canvasHeight;
		var needAnimate = false;

		function init (callback) {
			var wave = document.getElementById('wave');
			var canvas = document.createElement('canvas');
			if (!canvas.getContext) return;
			ctx = canvas.getContext('2d');
			canvasWidth = wave.offsetWidth;
			canvasHeight = wave.offsetHeight;
			canvas.setAttribute('width', canvasWidth);
			canvas.setAttribute('height', canvasHeight);
			wave.appendChild(canvas);
			waveImage = new Image();
			waveImage.onload = function () {
				waveImage.onload = null;
				callback();
			}
			waveImage.src = 'static/image/wave.png';
		}
		var count = 0;
		var stop = parseInt(Math.random() * 100 + 50) * 2;
		var flag = false;
		var speed = 0.65;
		function animate () {
			var waveX = 0;
			var waveY = 0;
			var waveX_min = -203;
			var waveY_max = canvasHeight * 1;
			var requestAnimationFrame = 
				window.requestAnimationFrame || 
				window.mozRequestAnimationFrame || 
				window.webkitRequestAnimationFrame || 
				window.msRequestAnimationFrame ||
				function (callback) { window.setTimeout(callback, 1000 / 60); };
			function loop () {
				ctx.clearRect(0, 0, canvasWidth, canvasHeight);
				if (!needAnimate) return;
				
				count++;
				if(count > stop && count < stop + 100) {
					waveY -= speed;
				}
				
				
				if (waveY < waveY_max) {
					waveY += speed;
				} else {
					if (!flag) {
						if (naire == undefined) {
							$(".mask").remove();
						} else {
							setTimeout(function() {
								$(".mask").remove();
								needAnimate = false;
								fillDataChart();
							}, 1500);
						}
					}
					flag = true;
				};
				if (waveX < waveX_min) waveX = 0; else waveX -= 3;
				var percent = parseInt(waveY)/2;
				
				if (percent%2==0) {
					$(".wave span").html(percent + "%");
				}
				ctx.globalCompositeOperation = 'source-over';
				ctx.beginPath();
				ctx.arc(canvasWidth/2, canvasHeight/2, canvasHeight/2, 0, Math.PI*2, true);
				ctx.closePath();
				ctx.fill();

				ctx.globalCompositeOperation = 'source-in';
				ctx.drawImage(waveImage, waveX, canvasHeight - waveY);
				
				
				requestAnimationFrame(loop);
			}
			loop();
		}

		function start () {
			if (!ctx) return init(start);
			needAnimate = true;
			setTimeout(function () {
				if (needAnimate) animate();
			}, 500);
		}
		function stop () {
			needAnimate = false;
		}
		return {start: start, stop: stop};
	}());
	wave.start();
}




/**
 * 初始化答案统计图表
 * @param naire
 * @returns
 */
function fillDataChart(answerStatisticsList) {
	$('#pie').addClass('active');
	// 父容器
	var parentWidget = $(".data_chart").show();
	parentWidget.empty();
	
	$.each(answerStatisticsList, function(i, question) {
		var barOption = {
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'直接访问',
		            type:'bar',
		            barWidth: '30%',
		            data:[10, 52, 200, 334, 390, 330, 220]
		        }
		    ]
		};
		
		// 指定图表的配置项和数据
		var pieOption = {
			backgroundColor : '#fff',		//#2c343c

			title : {
				text : 'title',
				left : 'center',
				top : 20,
				textStyle : {
					color : '#ccc'
				}
			},

			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},

			visualMap : {
				show : false,
				min : 1,
				max : 10,
				inRange : {
					colorLightness : [ 0, 1 ]
				}
			},
			series : [ {
				name : '选项',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '50%' ],
				data : [ {
					value : 1,
					name : '直接访问'
				}, {
					value : 2,
					name : '邮件营销'
				}, {
					value : 3,
					name : '联盟广告'
				}, {
					value : 4,
					name : '视频广告'
				}, {
					value : 5,
					name : '搜索引擎'
				} ].sort(function(a, b) {
					return a.value - b.value;
				}),
				roseType : 'radius',
				label : {
					normal : {
						textStyle : {
							color : '#3eb7ff'
						}
					}
				},
				labelLine : {
					normal : {
						lineStyle : {
							color : '#3eb7ff'
						},
						smooth : 0.2,
						length : 10,
						length2 : 20
					}
				},
				itemStyle : {
					normal : {
						color : '#009DFF',
						shadowBlur : 200,
						shadowColor : 'rgba(0, 0, 0, 0.5)'
					}
				},

				animationType : 'scale',
				animationEasing : 'elasticOut',
				animationDelay : function(idx) {
					return Math.random() * 200;
				}
			} ]
		};
		
		// 问题类型
		var qcDesc;
		if (question.categoryId == 1) {
			qcDesc = '【单选】';
		} else if (question.categoryId == 2) {
			qcDesc = '【多选】';
		}
		
	
		barOption.xAxis[0].data.length = 0;
		barOption.series[0].name = question.questionDesc+qcDesc;
		barOption.series[0].data.length = 0;
		
		pieOption.title.text = question.questionDesc+qcDesc;
		pieOption.series[0].data.length = 0;
		
		// 数据容器
		var dataWidget = $('<div></div>');
		parentWidget.append(dataWidget);
		
		// 图表控件
		var chartWidget = $('<div class="chart" ></div>');
		dataWidget.append(chartWidget);
		
		// 问题控件
		var questionWidget = $('<div class="questionBox"><div class="questionData">'+
					'<div>'+(i+1)+' '+question.questionDesc+qcDesc+'</div>'+
					'</div></div>');
		dataWidget.append(questionWidget);
		
		$.each(question.optionList, function(i, option) {
			var data = {
				name : option.optionDesc,
				value : option.selectCount
			};
			barOption.xAxis[0].data.push(String.fromCharCode(65+i));
			barOption.series[0].data.push(data);
			pieOption.series[0].data.push(data);
			
			// 选项控件
			var optionWidget =$('<div class="option">'+String.fromCharCode(65+i)+' '+option.optionDesc+'</div>');
			questionWidget.children().append(optionWidget);
		});
		
		// 初始化图表数据
		var eChart = echarts.init(chartWidget.get(0));
		eChart.setOption(pieOption);
		
		chartArray.push(eChart);
		barOptionArray.push(barOption);
		pieOptionArray.push(pieOption);
	});
}

// 加载数据
function loadData() {
	$.ajax({
		url : 'naire/answer/chart',
		type : 'post',
		timeout : 2000,
		data : {nid: naireId},
		dataType : "json",
		error : function(XMLHttpRequest) {
			console.log(JSON.stringify(XMLHttpRequest));
			errorTip('网络错误');
			destoryEchartLoading();
		},
		success : function(result) {
			if (result.status == 200) {
				setTimeout(function() {
					fillDataChart(result.msg);
					destoryEchartLoading();
				}, 3000);
			} else {
				console.log(result.msg);
				warnTip('加载失败');
				destoryEchartLoading();
			}
		},
		beforeSend: function() {
			initEhartLoading();
		}
	});
}

//复制问卷地址
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

// 初始化重置按钮的动画特效
function initResetAnimation() {
	var width = -58;
	var intervalTime = 50;
	var i = 0;
	var intervalIn, intervalOut;
	var reset = $('.reset i');
	var flag = false;
	var hoverIn = function() {
		$(this).css('transform', 'scale(0.6)');
		if (intervalOut != undefined) {
			clearInterval(intervalOut);
		}
		
		intervalIn = setInterval(function() {
			var position = (i++ * width) + 'px 0px';
			reset.css('background-position', position);
			if (i >= 15) {
				i = 15;
				clearInterval(intervalIn);
				intervalIn = undefined;
			}
		}, intervalTime);
	};
	var hoverOut = function() {
		$(this).css('transform', 'scale(1)');
		if (intervalIn != undefined) {
			clearInterval(intervalIn);
		}
		intervalOut = setInterval(function() {			
			var position = (i-- * width) + 'px 0px';
			reset.css('background-position', position);
			if (i <= 0) {
				i = 0;
				clearInterval(intervalOut);
				intervalOut = undefined;
			}
		}, intervalTime);
	};
	$('.reset a').hover(hoverIn, hoverOut);
}

// 初始化重置数据操作
function initReset() {
	var reset = $('.reset i');
	
	var click = function() {
		var $this = $(this);
		var dblChoseAlert = simpleAlert({
			content : '确定重置数据么（您所收集的数据都将会删除）?',
			buttons : {
				'确定' : function () {
					$.ajax({
						url: 'naire/answer/reset',
						type: 'post',
						data: {nid: naireId},
						dataType: 'json',
						timeout: 3000,
						success: function(result) {
							if (result.status == 200) {
								clearChart();
								loadOk($this);
								infoTip('重置数据成功');
							} else {
								infoTip('重置数据失败');
							}
						},
						error: function(XMLHttpRequest) {
							console.log(JSON.stringify(XMLHttpRequest));
							infoTip('网络错误');
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
	
	
	var loadOk = function ($this) {
		// 加载重置完成的动画
		$this.css('transform', 'scale(0)');
		reset.css({
			'background-image': 'url("static/image/over_ok.png")',
			'background-position': '-1450px 0px'
		});
		i = 25;
		var okInterval = setInterval(function() {
			var position = -(i-- * 58) + 'px 0px';
			reset.css('background-position', position);
			if (i <= 0) {
				i = 0;
				clearInterval(okInterval);
				setTimeout(function() {
					reset.css('background-image', 'url("static/image/reset.png")');
					$this.css('transform', 'scale(1)');
				}, 800);
			}
		}, 50);
	}
	
	$('.reset a').on('click', click);
}


// 重置图表及本页面其它数据
function clearChart() {
	collectCount = 0;
	$('.data_chart').empty();
	$('.data_chart').show().append('<div class="tip">还未收集到任何数据！</div>').siblings('.content').remove();
	$('.num span').html(0 + '份');
	var date = new Date();
	var d = ''
		+ date.getFullYear() 
	 	+ "-" 
	 	+ (date.getMonth()+1) 
	 	+ "-" 
	 	+ date.getDate();
	$('.lastTime').html("最后更新时间：" + d);
	
	$('.bar .option a').removeClass('active');
	$('#export, .bar .option a, .reset a').unbind('click').on('click', function() {
		infoTip('还未收集到任何数据');
	});
	return;
	
}


// 初始化导出事件
function initExport() {
	$('#export').on('click', function() {
		window.open('naire/answer/download/data/' + naireId);
	});
}



// 获取分布图数据
function getAreaData() {
	$.ajax({
		url : 'naire/area/statistics',
		type : 'post',
		timeout : 3000,
		data : {nid: naireId},
		dataType : "json",
		success: function(result) {
			$('#area').attr('isLoad', '1');
			$('body').animate({scrollTop: $(document).height()}, 800);
			if (result.status == 200) {
				initAreaChart(result.msg);
			} else {
				console.log(result.msg);
				infoTip('查询失败');
			}
		},
		error : function(XMLHttpRequest) {
			errorTip('网络错误');
		},
		beforeSend: function() {
			showLoading();
		},
		complete: function() {
			hideLoading();
		}
	});
}


// 初始化地区分布图
function initAreaChart(data) {
	if ($('#areaChart').children().length != 0) {
		//return;
	}
	
	/*
	
	
	var data = [
        {name: '北京',value: 30 },
        {name: '天津',value: 30 },
        {name: '上海',value: 30 },
        {name: '重庆',value: 30 },
        {name: '河北',value: 30 },
        {name: '河南',value: 30 },
        {name: '云南',value: 30 },
        {name: '辽宁',value: 30 },
        {name: '黑龙江',value: 30 },
        {name: '湖南',value: 30 },
        {name: '安徽',value: 30 },
        {name: '山东',value: 30 },
        {name: '新疆',value: 30 },
        {name: '江苏',value: 30 },
        {name: '浙江',value: 30 },
        {name: '江西',value: 30 },
        {name: '湖北',value: 30 },
        {name: '广西',value: 30 },
        {name: '甘肃',value: 30 },
        {name: '山西',value: 30 },
        {name: '内蒙古',value: 30 },
        {name: '陕西',value: 30 },
        {name: '吉林',value: 30 },
        {name: '福建',value: 30 },
        {name: '贵州',value: 30 },
        {name: '广东',value: 30 },
        {name: '青海',value: 30 },
        {name: '西藏',value: 30 },
        {name: '四川',value: 30 },
        {name: '宁夏',value: 30 },
        {name: '海南',value: 30 },
        {name: '台湾',value: 30 },
        {name: '香港',value: 30 },
        {name: '澳门',value: 30 }
    ];
   
	var geoCoordMap = {
		'北京':[116.46,39.92],
		'天津':[117.2,39.13],
		'上海':[121.48,31.22],
		'重庆':[106.54,29.59],
		'河北':[116.7,39.53],
		'河南':[113.29,33.75],
		'云南':[102.73,25.04],
		'辽宁':[123.38,41.8],
		'黑龙江':[126.63,45.75],
		'湖南':[113,28.21],
		'安徽':[117.27,31.86],
		'山东':[117.67,36.19],
		'新疆':[87.68,43.77],
		'江苏':[119.15,33.5],
		'浙江':[120.58,30.01],
		'江西':[115.89,28.68],
		'湖北':[114.31,30.52],
		'广西':[110.28,25.29],
		'甘肃':[103.73,36.03],
		'山西':[112.53,37.87],
		'内蒙古':[110,40.58],
		'陕西':[108.95,34.27],
		'吉林':[126.57,43.87],
		'福建':[119.3,26.08],
		'贵州':[106.71,26.57],
		'广东':[113.11,23.05],
		'青海':[101.74,36.56],
		'西藏':[91.11,29.97],
		'四川':[104.06,30.67],
		'宁夏':[106.27,38.47],
		'海南':[110.35,20.02],
		'台湾':[121.52,25.03],
		'香港':[114.16, 22.27],
		'澳门':[113.54,22.19]
	};
	

	var convertData = function (data) {
	    var res = [];
	    for (var i = 0; i < data.length; i++) {
	        var geoCoord = geoCoordMap[data[i].name];
	        if (geoCoord) {
	            res.push({
	                name: data[i].name,
	                value: geoCoord.concat(data[i].value)
	            });
	        }
	    }
	    return res;
	};

	data = convertData(data.sort(function (a, b) {
        return b.value - a.value;
    }));
	$.each(data, function(i, e) {
		e['index'] = i+1;
	});
	*/
	
	/*
	var ratio = 20;
	$.each(data, function(i, e) {
		
		e.value[2] = e.value[2] * ratio;
	});*/
//	var data = [{"rownum":1,"name":"恩施土家族苗族自治州","value":[30.2899218,109.4823432,100]}];
	var topScale = 5;
	var defaultScale = 5;
	var option = {
	    backgroundColor: '#404a59',
	    title: {
	        text: '地域分布',
	        subtext: 'data from wenjuanwang',
	        sublink: 'http://www.wenjuanwang.com',
	        left: 'center',
	        textStyle: {
	            color: '#fff'
	        }
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter:function(params) {
	        	if (params.data.rownum <= 5) {
	        		return 'Top'+params.data.rownum + '：' + params.name
	        		+'<br/>'+"人数: "+(params.value[2]);
	        	} else {
	        		return params.name+'<br/>'+
		            params.seriesName+": "+(params.value[2]);
	        	}
	        }
	    },
	    legend: {
	        orient: 'vertical',
	        y: 'bottom',
	        x:'right',
	        data:['人数'],
	        textStyle: {
	            color: '#fff'
	        }
	    },
	    geo: {
	        map: 'china',
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        roam: true,
	        itemStyle: {
	            normal: {
	                areaColor: '#323c48',
	                borderColor: '#111'
	            },
	            emphasis: {
	                areaColor: '#2a333d'
	            }
	        }
	    },
	    series : [
	        {
	            name: '人数',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: data,
	            symbolSize: function (val) {
	                return val[2] * defaultScale;
	            },
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: false
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#009dff'
	                }
	            }
	        },
	        {
	            name: 'Top',
	            type: 'effectScatter',
	            coordinateSystem: 'geo',
	            data: data.slice(0, 5),
	            symbolSize: function (val) {
	                return val[2] * topScale;
	            },
	            showEffectOn: 'render',
	            rippleEffect: {
	                brushType: 'stroke'
	            },
	            hoverAnimation: true,
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: true
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: '#3eb7ff',
	                    shadowBlur: 10,
	                    shadowColor: '#333'
	                }
	            },
	            zlevel: 1
	        }
	    ]
	};

	echarts3.init($('#areaChart')[0]).setOption(option);
}




//获取问卷分条数据
function getAllSingleAns() {
	var naireId = $('#naireId').val();
	$.ajax({
		url: "naire/respondent/list",
		type: 'post',
		data: {nid: naireId},
		timeout: 3000,
		success : function (result) {
			$('#ansDetail').attr('isLoad', '1');
			if (result.status == 200) {
				fillRespondentList(result.msg)
			} else {
				infoTip('加载失败！');
				console.log(result.msg);
			}
		},
		error: function(XMLHttpRequest) {
			console.log(JSON.stringify(XMLHttpRequest));
			errorTip('网络错误');
		},
		beforeSend: function() {
			showLoading();
		},
		complete: function() {
			hideLoading();
		}
	});
}

//解释问卷分条数据，并显示至界面
function fillRespondentList(data) {
	var naireId = $('#naireId').val();
	$.each(data, function(i, e) {
		var system = e.system.toLowerCase();
		var source = (system.indexOf("android") != -1 || system.indexOf("ios") != -1) ? '手机' : '电脑';
		var td = ''
			+ '<tr id="ans' + e.id + '">'
			+ '<td>' + (i+1) + '</td>'
			+ '<td>' + new Date(e.startTime).format("yyyy-MM-dd hh:mm:ss") + '</td>'
			+ '<td>' + new Date(e.endTime).format("yyyy-MM-dd hh:mm:ss") + '</td>'
			+ '<td>' + e.duration + '</td>'
			+ '<td>' + source + '</td>'
			+ '<td>'
			+ '<a href="naire/answer/view?index=' + (i+1) + '&nid=' + naireId + '&rid=' + e.id + '" target="_blank">查看</a>'
			+ '<a href="javascript:void(0);" onclick="deleteAnswer('+ e.id +')">删除</a>'
			+ '<a href="javascript:void(0);" >导出</a>'
			+ '</td>'
			+ '</tr>';
	
		$('tbody').append(td);
	});
}

// 删除问卷单条数据
function deleteAnswer(rid) {
	var dblChoseAlert = simpleAlert({
		content : "您确定要删除该数据么?",
		buttons : {
			'是' : function () {
				dblChoseAlert.close();
				var data = {
					nid: $('#naireId').val(),
					rid: rid
				}
				$.ajax({
					url: "naire/respondent/delete",
					type: 'post',
					data: data,
					timeout: 3000,
					success : function (result) {
						if (result.status == 200) {
							$('#ans' + rid).remove();
							infoTip('删除成功');
							setTimeout(function() {
								window.location.reload();
							}, 1000);
						} else {
							infoTip('删除失败！');
							console.log(result.msg);
						}
					},
					error: function(XMLHttpRequest) {
						console.log(JSON.stringify(XMLHttpRequest));
						errorTip('网络错误');
					},
					beforeSend: function() {
						showLoading();
					},
					complete: function() {
						hideLoading();
					}
				});
			},
			'否': function() {
				dblChoseAlert.close();
			}
		}
	});
}