/**
 * 显示加载界面
 * @returns
 */
function showLoading(content) {
	if (content == undefined) {
		content = 'Loading...';
	}
	var loadingHtml = '<div class="loading"><div class="shadow">' + content + '</div></div>';
	$('body').prepend(loadingHtml);
}

/**
 * 清除加载界面
 * @returns
 */
function hideLoading() {
	$('.loading').remove();
}