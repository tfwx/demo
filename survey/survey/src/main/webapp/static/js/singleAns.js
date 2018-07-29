$(function() {

	$('.content h1 a').on('click', function() {
		exportPDF();
	});
	
});








/* $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=${respondentInfo.ip}',function(){
	var address = remote_ip_info.province == remote_ip_info.city ? remote_ip_info.province : remote_ip_info.province + remote_ip_info.city;
	$('#address').html('区域：' + address);
}); */

/**
 * 获取显示器DPI
 */
function js_getDPI() {
    var arrDPI = new Array();
    if (window.screen.deviceXDPI != undefined) {
        arrDPI[0] = window.screen.deviceXDPI;
        arrDPI[1] = window.screen.deviceYDPI;
    }
    else {
        var tmpNode = document.createElement("DIV");
        tmpNode.style.cssText = "width:1in;height:1in;position:absolute;left:0px;top:0px;z-index:99;visibility:hidden";
        document.body.appendChild(tmpNode);
        arrDPI[0] = parseInt(tmpNode.offsetWidth);
        arrDPI[1] = parseInt(tmpNode.offsetHeight);
        tmpNode.parentNode.removeChild(tmpNode);    
    }
    return arrDPI;
}

/**
 * 根据html生成PDF并下载
 * @returns
 */
function exportPDF() {
	var icon = $('.content h1 a').hide();
	html2canvas(document.body, {  
        onrendered: function(canvas) {
        	icon.show();
            var imgData = canvas.toDataURL('image/jpeg', 1.0); 
            var canWidth = canvas.width;  
            var canHeight = canvas.height;  
            var arrDPI = js_getDPI();
            var dpiX = 96;  
            var dpiY = 96;  
            if(arrDPI.length>0){  
                dpiX = arrDPI[0];  
                dpiY = arrDPI[1];  
            }
            // 根据画布大小，自动判断横向或纵向
            var directon = canWidth > canHeight ? 'l' : 'p';
            //l:横向， p：纵向；单位： in:英寸，mm毫米；画布大小：a3,a4,leter,[]（当内容为数组时，为自定义大小）  
            var doc = new jsPDF(directon, 'in', [canWidth/dpiX, canHeight/dpiY]);//设置PDF宽高为要显示的元素的宽高，将像素转化为英寸  
            doc.addImage(imgData, 'JPEG', 0, 0);

            doc.save('答卷详情.pdf');  
        },    
        background: "#EEEFF1", //这里给生成的图片默认背景，不然的话，如果html根节点没有设置背景的话，会用黑色填充。  
        taintTest: false,  
        allowTaint: true //避免一些不识别的图片干扰，默认为false，遇到不识别的图片干扰则会停止处理html2canvas  
    });  
	
}  
