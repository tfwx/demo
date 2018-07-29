/* 
*page plugin 1.0   2016-09-29 by cary
*/
(function ($) {
  //默认参数
  var defaults = {
    totalPages: 9,//总页数
    liNums: 9,//分页的数字按钮数(建议取偶数)
    activeClass: 'active' ,//active类
    curPage: 1,	//当前页码
    pageSize: 6, // 页面大小
    firstPage: '首页',//首页按钮名称
    lastPage: '末页',//末页按钮名称
    prv: '«',//前一页按钮名称
    next: '»',//后一页按钮名称
    hasFirstPage: true,//是否有首页按钮
    hasLastPage: true,//是否有末页按钮
    hasPrv: true,//是否有前一页按钮
    hasNext: true,//是否有后一页按钮
    showCount: 20,
    callBack : function(page){
        //回掉，page选中页数
    }
  };

  //插件名称
  $.fn.Page = function (options) {
    //覆盖默认参数
    var opts = $.extend(defaults, options);
    //主函数
    return this.each(function () {
      var obj = $(this);
      obj.empty();
      var l = opts.totalPages;
      var n = opts.liNums;
      var c = opts.curPage;
      var pageSize = opts.pageSize;
      var active = opts.activeClass;
      var str = '';
      var str1 = '<li><a href="javascript:" class="'+ active +'">1</a></li>';
      str1 = "";
      if (l>1 && l<n+1) {    	
        for (i = 1; i <= l; i++) {
          if (i == c) {
        	  str += '<li><a href="javascript:" class="'+ active +'">' + i + '</a></li>';
          } else {
        	  str += '<li><a href="javascript:">' + i + '</a></li>';
          }
        }
      }else if(l > n){
    	  var start = c-parseInt(n/2);    	  
    	  if (start <= 0) {
    		  start = 1;
    	  }
    	  var end = start+n;
    	  if (end >= l) {
    		  end = l;
    		  start = end - n;
    	  }
        for (i = start; i <= end; i++) {
	      if (i == c) {
	      	str += '<li><a href="javascript:" class="'+ active +'">' + i + '</a></li>';
	      } else {
	      	str += '<li><a href="javascript:">' + i + '</a></li>';
	      }
        }
      }
      
      var dataHtml = '';
      dataHtml += '<select class="pagingUl" id="pageSize">';
      for(i=1;i<=opts.showCount;i++){
    	  if (i == pageSize) {
    		  dataHtml += '<option selected="selected">'+i+'</option>';
    	  } else {
    		  dataHtml += '<option>'+i+'</option>';
    	  }
      }
      dataHtml += '</select>';
      dataHtml += '<div class="pageSize fr" style="width:auto;">展示条数:</div>';      
      
      if(opts.hasNext){
          dataHtml += '<div class="next fr">' + opts.next + '</div>';
      }
      if(opts.hasLastPage){
          dataHtml += '<div class="last fr">' + opts.lastPage + '</div>';
      }
          dataHtml += '<ul class="pagingUl">' + str1 + str + '</ul>';
      if(opts.hasFirstPage){
          dataHtml += '<div class="first fr">' + opts.firstPage + '</div>';
      }
      if(opts.hasPrv){
          dataHtml += '<div class="prv fr">' + opts.prv + '</div>';
      }
      
      obj.html(dataHtml).off("click");//防止插件重复调用时，重复绑定事件
      
     
      $("#pageSize").change(function() {
    	  var pageshow = 1;
    	  var pageSize = parseInt($("#pageSize").val());
    	  opts.callBack(pageshow ,pageSize);
      });
      
      obj.on('click', '.next', function () {
        var pageshow = parseInt($('.' + active).html());
        if (isNaN(pageshow)) {
        	return;
        }
        
        var nums,flag;
        var a = n % 2;
        if(a == 0){
          //偶数
          nums = n;
          flag = true;
        }else if(a == 1){
          //奇数
          nums = (n+1);
          flag = false;
        }
        if(pageshow >= l) {
          return;
        }else if(pageshow > 0&&pageshow <= nums/2){
          //最前几项
          $('.' + active).removeClass(active).parent().next().find('a').addClass(active);
        }else if((pageshow > l-nums/2&&pageshow < l&&flag==false)||(pageshow > l-nums/2-1&&pageshow < l&&flag==true)){
          //最后几项
          $('.' + active).removeClass(active).parent().next().find('a').addClass(active);
        }else{
          $('.' + active).removeClass(active).parent().next().find('a').addClass(active);
          fpageShow(pageshow+1);
        }
        opts.callBack(pageshow+1, pageSize);
      });
      obj.on('click', '.prv', function () {    	
        var pageshow = parseInt($('.' + active).html());
        if (isNaN(pageshow)) {
        	return;
        }
    	
        var nums = odevity(n);
        if (pageshow <= 1) {
            return;
        }else if((pageshow > 1&&pageshow <= nums/2)||(pageshow > l-nums/2&&pageshow < l+1)){
          //最前几项或最后几项
          $('.' + active).removeClass(active).parent().prev().find('a').addClass(active);
        }else {
          $('.' + active).removeClass(active).parent().prev().find('a').addClass(active);
          fpageShow(pageshow-1);
        }
        opts.callBack(pageshow-1, pageSize);
      });

      obj.on('click', '.first', function(){
        var activepage = parseInt($('.' + active).html());
        if (activepage <= 1 || isNaN(activepage)){
          return
        }//当前第一页
        opts.callBack(1, pageSize);
        fpagePrv(0);
      });
      obj.on('click', '.last', function(){
        var activepage = parseInt($('.' + active).html());
        if (activepage >= l){
          return;
        }//当前最后一页
        opts.callBack(l, pageSize);
        // 此处代码会影响显示
       /* if(l>n){
          fpageNext(n-1);
        }else{
          fpageNext(l-1);
        }*/
      });

      obj.on('click', 'li', function(){/*alert(1);*/
        var $this = $(this);
        var pageshow = parseInt($this.find('a').html());
        var nums = odevity(n);
        opts.callBack(pageshow, pageSize);
        if(l>n){
          if(pageshow > l-nums/2&&pageshow < l+1){
            //最后几项
            fpageNext((n-1)-(l-pageshow));
          }else if(pageshow > 0&&pageshow < nums/2){
            //最前几项
            fpagePrv(pageshow-1);
          }else{
            fpageShow(pageshow);
          }
        }else{
          $('.' + active).removeClass(active);
          $this.find('a').addClass(active);
        }
      });
      
           	

          //重新渲染结构
        /*activePage 当前项*/
        function fpageShow(activePage){
          var nums = odevity(n);
          var pageStart = activePage - (nums/2-1);
          var str1 = '';
          for(i=0;i<n;i++){
            str1 += '<li><a href="javascript:" class="">' + (pageStart+i) + '</a></li>'
          }
          obj.find('ul').html(str1);
          obj.find('ul li').eq(nums/2-1).find('a').addClass(active);
        }
        /*index 选中项索引*/
        function fpagePrv(index){
          var str1 = '';
          if(l>n-1){
            for(i=0;i<n;i++){
              str1 += '<li><a href="javascript:" class="">' + (i+1) + '</a></li>'
            }
          }else{
            for(i=0;i<l;i++){
              str1 += '<li><a href="javascript:" class="">' + (i+1) + '</a></li>'
            }
          }
          obj.find('ul').html(str1);
          obj.find('ul li').eq(index).find('a').addClass(active);
        }
        /*index 选中项索引*/
        function fpageNext(index){
          var str1 = '';
          if(l>n-1){
            for(i=l-(n-1);i<l+1;i++){
              str1 += '<li><a href="javascript:" class="">' + i + '</a></li>'
            }
           obj.find('ul').html(str1);
           obj.find('ul li').eq(index).find('a').addClass(active);
          }else{
            for(i=0;i<l;i++){
              str1 += '<li><a href="javascript:" class="">' + (i+1) + '</a></li>'
            }
           obj.find('ul').html(str1);
           obj.find('ul li').eq(index).find('a').addClass(active);
          }
        }
        //判断liNums的奇偶性
        function odevity(n){
          var a = n % 2;
          if(a == 0){
            //偶数
            return n;
          }else if(a == 1){
            //奇数
            return (n+1);
          }
        }
    });
  }
})(jQuery);

