// JavaScript Document
(function($){
    $.fn.capacityFixed = function(options) {
        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
        var FixedFun = function(element) {
            var top = opts.top;
            var right = ($(window).width()-opts.pageWidth)/2+opts.right;
            //if($.browser.mozilla) right-=8;
            if($.browser.msie) {
            	if($.browser.version.indexOf('8')==0) right+=1;
            	//if($.browser.version.indexOf('7')==0) right-=3;
            }
            element.css({
                "right":right,
                "top":top
            });
            $(window).resize(function(){
                var right = ($(window).width()-opts.pageWidth)/2+opts.right;
                //if($.browser.mozilla) right-=8;
                if($.browser.msie) {
                	if($.browser.version.indexOf('8')==0) right+=1;
                	//if($.browser.version.indexOf('7')==0) right-=3;
                }
                element.css({
                    "right":right
                });
            });
            $(window).scroll(function() {
            	$('#float').offset().left;
                var scrolls = $(this).scrollTop();
                if (scrolls > top) {

                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
                            top: -4							
                        });
                    } else {
                        element.css({
                            top: scrolls
                        });
                    }
                }else {
                    element.css({
                        position:"static",
                        top: top
                    });
                }
            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    $.fn.capacityFixed.deflunt={
		right : 0,//相对于页面宽度的右边定位
        top:468,
        pageWidth : 990
	};
})(jQuery);

(function($){
    $.fn.BapacityFixed = function(options) {
        var opts = $.extend({},$.fn.BapacityFixed.deflunt,options);
        var FixedFun = function(element) {
            var top = opts.top;
            var right = ($(window).width()-opts.pageWidth)/2+opts.right;
            if($.browser.mozilla) right-=8;
            if($.browser.msie) {
            	if($.browser.version.indexOf('8')==0) right+=1;
            	//if($.browser.version.indexOf('7')==0) right-=3;
            }
            element.css({
                "right":right,
                "top":top
            });
            $(window).resize(function(){
                var right = ($(window).width()-opts.pageWidth)/2+opts.right;
                if($.browser.mozilla) right-=8;
                if($.browser.msie) {
                	if($.browser.version.indexOf('8')==0) right+=1;
                	//if($.browser.version.indexOf('7')==0) right-=3;
                }
                element.css({
                    "right":right
                });
            });
            $(window).scroll(function() {
            	$('#Center').offset().left;
                var scrolls = $(this).scrollTop();
                if (scrolls > top) {

                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
                            top: -4							
                        });
                    } else {
                        element.css({
                            top: scrolls
                        });
                    }
                }else {
                    element.css({
                        position:"static",
                        top: top
                    });
                }
            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    $.fn.BapacityFixed.deflunt={
		right:0,//相对于页面宽度的右边定位
        top:142,
        pageWidth:990
	};
})(jQuery);
/*(function($){
    $.fn.BapacityFixed = function(options) {
        var opts = $.extend({},$.fn.BapacityFixed.deflunt,options);
        var FixedFun = function(element) {
            var bottom = opts.bottom;
            var right = ($(window).width()-opts.pageWidth)/2+opts.right;
            element.css({
                "right":right,
                "bottom":bottom
            });
            $(window).resize(function(){
                var right = ($(window).width()-opts.pageWidth)/2+opts.right;
                element.css({
                    "right":right
                });
            });
            $(window).scroll(function() {
                var scrolls = $(this).scrollTop();
                if (scrolls > bottom) {

                    if (window.XMLHttpRequest) {
                        element.css({
                            position: "fixed",
							display:"block",
                            bottom:10						
                        });
                    } else {
                        element.css({
                            bottom: scrolls
                        });
                    }
                }else {
                    element.css({
                        position: "absolute",
						display:"none",
                        bottom:bottom
						
                    });
                }
            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
    $.fn.BapacityFixed.deflunt={
		right :10,//相对于页面宽度的右边定位
        bottom:10,
        pageWidth :1099
	};
})(jQuery);*/