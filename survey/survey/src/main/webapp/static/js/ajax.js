
function ajax(url, formName) {
	$.ajax({
		url: url,
		type: 'post',
		timeout: 10000,
		data: $(formName).serialize(),
		error: function () {
			
		},
		success : function () {
			
		}		
	});
}


