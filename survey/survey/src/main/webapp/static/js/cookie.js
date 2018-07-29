/* cookie的相关存储方法    */

function setCookie(name,value) {
	var Days = 30;	//30天过期
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	// 如果不指定path，不同页面中设置的cookie只有在同目录的页面可用,'/'表示在整个网站下可用
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";
}

function getCookie(name) {
	var arr;
	var reg = new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr = document.cookie.match(reg)) {
		return unescape(arr[2]);
	}
	return null;
}


function delCookie(name) {
	// 删除cookie直接让cookie过期就好了(注意path和domain)
    var exp = new Date();
    exp.setTime(exp.getTime() - 1); 
    var cval = getCookie(name); 
    if(cval != null) {
    	document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/";
    }
} 