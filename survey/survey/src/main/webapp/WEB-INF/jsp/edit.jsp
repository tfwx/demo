<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>问卷编辑</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="new">
<link rel="shortcut icon" href="static/image/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="static/css/public.css">
<link rel="stylesheet" type="text/css" href="static/css/edit.css">
<link type="text/css" rel="stylesheet" href="static/css/wenjuan_ht.css">
<link rel="stylesheet" type="text/css" href="static/css/message.css">
<link rel="stylesheet" type="text/css" href="static/css/simpleAlert.css" />
<link rel="stylesheet" type="text/css" href="static/css/l_nav.css">

<script type="text/javascript" src="static/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="static/js/localStorage.js"></script>
<script type="text/javascript" src="static/js/simpleAlert.js"></script>
<script type="text/javascript" src="static/js/edit.js"></script>
<script type="text/javascript" src="static/js/message.js"></script>
<script type="text/javascript" src="static/js/public.js"></script>


<link rel="stylesheet" type="text/css" href="static/css/tooltipster/tooltipster.bundle.min.css" />
<link rel="stylesheet" type="text/css" href="static/css/tooltipster/themes/tooltipster-sideTip-shadow.min.css" />
<script type="text/javascript" src="static/js/tooltipster/tooltipster.bundle.min.js"></script>



<c:if test="${requestScope.isEditNaire == 1}">
<link rel="stylesheet" type="text/css" href="static/css/poster.css" />
<script type="text/javascript" src="static/js/editnaire.js"></script>
<script type="text/javascript" src="static/js/poster.js"></script>
<script type="text/javascript" src="static/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="static/js/clipboard.min.js"></script>
</c:if>

</head>

<body>

	<div class="mask">
		<div class="fore">
			<div class="title">
				<h2>主题</h2>
				<span onclick="$('.mask').hide();">&times;</span>
			</div>
			<hr/>
			<div class="nav">
				<a>电脑</a>
				<a>手机</a>
			</div>
			<div class="content pc">
				<div class="single defaultSubject" subject="default">
					<!-- <i></i> -->
					<div class="fc">
						<h4>标题</h4>
						<div class="cblock"></div>
						<div class="cblock"></div>
					</div>
				</div>
				<div class="single graySubject" subject="gray">
					<!-- <i></i> -->
					<div class="fc">
						<h4>标题</h4>
						<div class="cblock"></div>
						<div class="cblock"></div>
					</div>
				</div>
				<div class="single redSubject" subject="red">
					<!-- <i></i> -->
					<div class="fc">
						<h4>标题</h4>
						<div class="cblock"></div>
						<div class="cblock"></div>
					</div>
				</div>
				<div class="single blackSubject" subject="black">
					<!-- <i></i> -->
					<div class="fc">
						<h4>标题</h4>
						<div class="cblock"></div>
						<div class="cblock"></div>
					</div>
				</div>
			</div>
			
			
			<div class="content phone">
				<div value="0">
					<img alt="" src="static/image/cover/cover_img4.jpg"><!-- <i></i> -->
					<div class="wj_title beach">
						问卷调研
						<span>感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！</span>
					</div>
				</div>
				<div value="1">
					<img alt="" src="static/image/cover/cover_img8.jpg"><!-- <i></i> -->
					<div class="wj_title city">
						问卷调研
						<span>感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！</span>
					</div>
				</div>
				<div value="2">
					<img alt="" src="static/image/cover/cover_img10.jpg"><!-- <i></i> -->
					<div class="wj_title office">
						问卷调研
						<span>感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！</span>
					</div>
				</div>
			</div>
			
			<hr/>
			<div class="bottomBtn">
				<input type="button" value="确定" operation="restore">
				<input type="button" value="取消" operation="delete">
			</div>
		</div>	
	</div>
	
	
	
	<div class="mask2">
		<div class="fore poster" style="width: 70%;height: 90%;">
			<div class="title">
				<h2>分享</h2>
				<span onclick="$('.mask2').hide();">&times;</span>
			</div>
			<hr/>
			<div style="height: 90%;display: flex">
				<div style="flex:3;border-right: #eee 1px solid;">
					<div class="link">
						<input class="naire_link" readonly="readonly">
						<a href="javascript:void(0);" data-clipboard-target='.naire_link'>复制</a>
					</div>
					<table id="posterTable" class="posterTable" cellspacing="15">
						<tbody>
							<tr>
								<td class="theme_active">
									<img src="static/image/poster/poster_bg_1.png">
								</td>
								<td>
									<img src="static/image/poster/poster_bg_2.png">
								</td>
								<td>
									<img src="static/image/poster/poster_bg_3.png">
								</td>
								<td>
									<img src="static/image/poster/poster_bg_4.png">
								</td>
								<td>
									<img src="static/image/poster/poster_bg_5.png">
								</td>
							</tr>
							<tr>
								<td>
									<img src="static/image/poster/poster_bg_6.png">
								</td>
								<td>
									<img src="static/image/poster/poster_bg_7.png">
								</td>
								<td>
									<img src="static/image/poster/poster_bg_8.jpg">
								</td>
								<td>
									<a href="javascript:void(0);" class="upload" title="从本地选择"></a>
									<input type="file" id="upload_poster" hidden accept="image/*">
								</td>
							</tr>
						</tbody>
					</table>
					<a href="javascript:void(0);" class="download_btn shadow">下载</a>
					<div class="share_box">
						<img src="static/image/poster/poster_qq.png">
						<img src="static/image/poster/poster_weibo.png">
						<img src="static/image/poster/poster_qq_zone.png">
						<img id="wx" src="static/image/poster/poster_weixin.png" data-tooltip-content="#qrcode">
					</div>
				</div>
				<div class="poster_view">
					<img class="preview" src="static/image/poster/bgc.png">
					<p class="wj_ntitle">问卷网调研</p>
					<p class="logo1">倾听你在乎的</p>
					<p class="logo2">Listen to what you care</p>
					<div class="code">
						<div id="code"></div>
						<!-- <img src="static/image/poster/code.png"> -->
						<span>长按图片扫码</span>
					</div>
				</div>
			</div>
		</div>
	</div>	
	

	<div class="left_nav">
		<a href="naire/list"><div><img src="static/image/back.png" /></div></a>
		<a class="active" href="javascript:void(0);"><div><img src="static/image/editnaire.png" /><span>编辑问卷</span></div></a>		
		<a href="javascript:void(0);"><div><img src="static/image/datareport.png" /><span>数据报表</span></div></a>
		<a href="javascript:void(0);" onclick="<c:if test='${requestScope.isEditNaire == 1}'>$('.mask2').show();</c:if><c:if test='${requestScope.isEditNaire != 1}'>infoTip('请先发布问卷');</c:if>"><div><img src="static/image/collect.png" /><span>收集问卷</span></div></a>
		<a href="javascript:void(0);"><div><img src="static/image/import.png" /><span>问卷导入</span></div></a>
	</div>
	

	<div class="edit_content">
		<div class="top">
			<input type="text" id="naireTitle1" placeholder="问卷标题" />
			<div class="btn_member">
				<c:if test="${requestScope.isEditNaire == 0}">
					<a href="javascript:void(0);" id="saveNaire">保存问卷</a>
				</c:if>
				<a href="javascript:void(0);" id="previewNaire">预览问卷</a>
			</div>
		</div>
		
		<div class="subject">
			<i></i><a>外观设置</a>
		</div>
		
		<div class="center">
		
			<div class="left">
				<div class="question_nav shadow">
					<div class="qtitle">
						常用题型<span>-</span>
					</div>
					<div>
						<a href="javascript:void(0);" onclick="addQuestion(0)">单选题</a>
						<a href="javascript:void(0);" onclick="addQuestion(1)">多选题</a>
						<a href="javascript:void(0);" onclick="addQuestion(2)">简答题</a>
					</div>
					<input hidden type="button" id="show" value="showNaireData" />
				</div>
				
				<div class="category_nav shadow">
					<div class="qtitle">
						${requestScope.naireCategoryMap[fn:trim(requestScope.parentCategory)].categoryDesc}类型<span>-</span>
					</div>
					<div id="category">
						<c:forEach items="${requestScope.naireCategoryMap}" var="mapItem">
							<c:set var="category" value="${mapItem.value}"></c:set>
							<c:if test="${category.parentId == parentCategory}">
								<a href="javascript:void(0);" <c:if test="${category.id == requestScope.childCategory}">class="active"</c:if> value="${category.id}">${category.categoryDesc}</a>
							</c:if>
						</c:forEach>
					</div>
				</div>
				
				<div class="share shadow">
					<input type="checkbox" id="share" checked="checked"><label for="share">共享至问卷库</label>
				</div>
				
				<div class="share shadow">
					<input id="isNeedPwd" type="checkbox"><label for="isNeedPwd">启用访问密码</label>
					<input id="password" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" placeholder="请输入访问密码" autocomplete="off">
				</div>
			</div>
	
			<div class="question_content shadow">
				<input type="text" class="title" placeholder="问卷标题" id="naireTitle2" /> 
				<input type="text" class="desc" placeholder="欢迎参加本次答题" />
					
				<div>
					<div class="yd_box"></div>
	
					<!--选项卡区域  模板区域-->
					<div class="xxk_box">
						<div class="xxk_conn hide">
							<!--单选-->
							<div class="xxk_xzqh_box dxuan ">
								<textarea class="input_wenbk btwen_text btwen_text_dx" placeholder="单选题目"></textarea>
								<div class="title_itram">
									<div class="kzjxx_iteam">
										<input type="radio" class="dxk"> 
										<input type="text" class="input_wenbk" placeholder="选项"> 
										<label hidden>
											<input type="checkbox" value="" class="fxk"> <span>可填空</span>
										</label> 
										<a href="javascript:void(0);" class="del_xm">删除</a>
									</div>
								</div>
								<a href="javascript:void(0)" class="zjxx">增加选项</a>
								<!--完成编辑-->
								<div class="bjqxwc_box">
									<a href="javascript:void(0);" class="qxbj_but">取消编辑</a> 
									<a href="javascript:void(0);" class="swcbj_but"> 完成编辑</a>
								</div>
							</div>
							<!--多选-->
							<div class="xxk_xzqh_box duoxuan hide">
								<textarea class="input_wenbk btwen_text btwen_text_duox" placeholder="多选题目"></textarea>
								<div class="title_itram">
									<div class="kzjxx_iteam">
										<input type="checkbox" class="dxk"> 
										<input type="text" class="input_wenbk" placeholder="选项">
										<label hidden> 
											<input type="checkbox" class="fxk"> <span>可填空</span>
										</label>
										<a href="javascript:void(0);" class="del_xm">删除</a>
									</div>
								</div>
								<a href="javascript:void(0)" class="zjxx">增加选项</a>
								<!--完成编辑-->
								<div class="bjqxwc_box">
									<a href="javascript:void(0);" class="qxbj_but">取消编辑</a> 
									<a href="javascript:void(0);" class="swcbj_but"> 完成编辑</a>
								</div>
							</div>
							<!-- 填空-->
							<div class="xxk_xzqh_box tktm hide">
								<textarea class="input_wenbk btwen_text btwen_text_tk" placeholder="答题区"></textarea>
								<!--完成编辑-->
								<div class="bjqxwc_box">
									<a href="javascript:void(0);" class="qxbj_but">取消编辑</a>
									<a href="javascript:void(0);" class="swcbj_but"> 完成编辑</a>
								</div>
							</div>
							<!--矩阵-->
							<div class="xxk_xzqh_box  hide">
								<div class="line_dl"></div>
								<div class="jztm">
									<textarea name="" cols="" rows="" class="input_wenbk btwen_text"
										placeholder="题目"></textarea>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tbody>
											<tr valign="top">
												<td width="135">
													<h4 class="ritwenz_xx">左标题</h4> <textarea name="" cols=""
														rows="" class="leftbtwen_text"
														placeholder="例子：CCTV1，CCTV2，CCTV3"></textarea>
												</td>
												<td>
													<h4 class="ritwenz_xx  ">
														右侧选项文字 <input type="radio" name="xz" value="0"
															checked="checked" class="xzqk">单选<input
															type="radio" value="1" name="xz" class="xzqk">多选
													</h4>
													<div class="title_itram">
														<div class="kzjxx_iteam">
															<input name="" type="text"
																class="input_wenbk jzwent_input" value="选项"
																onblur="if(!this.value)this.value='选项'"
																onclick="if(this.value&amp&ampthis.value=='选项' )  this.value=''">
															<label hidden> <input name="" type="checkbox" value=""
																class="fxk"> <span>可填空</span></label> <a
																href="javascript:void(0);" class="del_xm">删除</a>
														</div>
														<div class="kzjxx_iteam">
															<input name="" type="text"
																class="input_wenbk jzwent_input" value="选项"
																onblur="if(!this.value)this.value='选项'"
																onclick="if(this.value&amp&ampthis.value=='选项' )  this.value=''">
															<label hidden> <input name="" type="checkbox" value=""
																class="fxk"> <span>可填空</span></label> <a
																href="javascript:void(0);" class="del_xm">删除</a>
														</div>
														<div class="kzjxx_iteam">
															<input name="" type="text"
																class="input_wenbk jzwent_input" value="选项"
																onblur="if(!this.value)this.value='选项'"
																onclick="if(this.value&amp&ampthis.value=='选项' )  this.value=''">
															<label> <input name="" type="checkbox" value=""
																class="fxk"> <span>可填空</span></label> <a
																href="javascript:void(0);" class="del_xm">删除</a>
														</div>
													</div> <a href="javascript:void(0)" class="zjxx"
													style="margin-left: 0;">增加选项</a>
												</td>
											</tr>
										</tbody>
									</table>
									<!--完成编辑-->
									<div class="bjqxwc_box">
										<a href="javascript:void(0);" class="qxbj_but">取消编辑</a> <a
											href="javascript:void(0);" class="swcbj_but"> 完成编辑</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="qrcode" class="wxTip">
		<div>
			<img src="static/image/poster/loading.gif"/>
			<div id="posterCode"></div>
		</div>
		<span style="font-size:10px;">扫码后，长按图片分享给好友</span>
	</div>
</body>


<script type="text/javascript">

	// 记录分类id(若有)
	if (${not empty requestScope.childCategory}) {
		naire.categoryId = '${requestScope.childCategory}';
	} 

	
	if (${requestScope.isEditNaire == 1}) {
		// 编辑问卷
		// 获取问卷数据
		if (${not empty requestScope.naireData}) {
			naire = JSON.parse('${requestScope.naireData}');
		}
	} else {
		if (${not empty requestScope.naireData}) {
			// 生成新增
			// 由生成页面传过来的数据
			restoreData(JSON.parse('${requestScope.naireData}'));
		} else {
			// 普通新增
			// 恢复上次编辑问卷时意外丢失的数据
			readUnsavedNaireData();
		}
	}

</script>


</html>
