// JavaScript Document
$(document).ready(function() {
	$("hgroup.search form :input").focus(function() {
		$(this).addClass("focus");
		if ($(this).val() == this.defaultValue) {// 获取原始的value值
			$(this).val("");// 赋于新的value值为空
		}
	}).blur(function() {
		$(this).removeClass("focus");
		if ($(this).val() == "") {
			$(this).val(this.defaultValue);
		}
	})

	$("#regional").click(function() {
		$(this).siblings('.dropdown-menu').fadeToggle(100);
		$(this).toggleClass('selected');
		$(this).children().children().toggleClass('arrow-up');
		$(this).parent("li.login").toggleClass('selected').css("cursor", "pointer");
		$(this).next('strong').toggleClass("Down");

		// if ($(this).hasClass('selected'))
		// $(this).next('strong').addClass("Down")
		// else $(this).next('strong').removeClass("Down")

	});

	$('#regional').parent().bind("mouseleave", function() {
		if ($(this).find('div').css('display') == 'block') {
			$("#regional").click();
			/*
			 * $(this).find('div').css('display', 'none');
			 * $("#regional").toggleClass('selected');
			 * $("#regional").children().children().toggleClass('arrow-up');
			 */
		}
	});

	$('#division').hover(function() {
		$(this).children('.areaList').stop(true, true).slideDown(100);
		$(this).addClass('selected');
	}, function() {
		$(this).children('.areaList').stop(true, true).slideUp(100);
		$(this).removeClass('selected');
	});

	$('#division').hover(function() {
		$(this).children('table').stop(true, true).slideDown(100);
	}, function() {
		$(this).children('table').stop(true, true).slideUp(100);
	});
	
	$("#modification").click(function(){
		$('.camouflage').fadeToggle(100);
	});
	
	
	
	var $rui_li = $("#slogan .container .Share_menu li");// 获取
	$rui_li.click(function() {// 绑定事件
		$(this).addClass("selected") // 当前<li>元素高亮追加样式selected
		.siblings().removeClass("selected");// 同时去掉同辈<li>元素的高亮
		var index = $rui_li.index(this);// 获取当前单击的<li>元素在全部<li>元素中的索引
		$("#content-wrapper .container > div")// 选取子节点
		.eq(index).show()// 显示<li>元素对应的<div>元素
		.siblings().hide();// 隐藏其他几个同辈的<div>
	}).hover(function() { // 添加光标滑动效果
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
	})

	var $xiaopeng_li = $(".IssuedNotice .profile .widget-title ul.Logins li")
	$xiaopeng_li.click(function() {// 绑定事件
		$(this).addClass("selected") // 当前<li>元素高亮追加样式selected
		.siblings().removeClass("selected");// 同时去掉同辈<li>元素的高亮
		var index = $xiaopeng_li.index(this);// 获取当前单击的<li>元素在全部<li>元素中的索引
		$("#registerRight .IssuedNotice .previewlist > div")// 选取子节点
		.eq(index).show()// 显示<li>元素对应的<div>元素
		.siblings().hide();// 隐藏其他几个同辈的<div>
	}).hover(function() { // 添加光标滑动效果
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
	})

	$('#usernemeaa').hover(function() {
		$(this).children('ul').stop(true, true).slideDown(100);
		$(this).children(".usernemeaa").addClass('selected');
	}, function() {
		$(this).children('ul').stop(true, true).slideUp(100);
		$(this).children(".usernemeaa").removeClass('selected');
	});

	$('#usernemeaa').hover(function() {
		$(this).children('div').stop(true, true).slideDown(100);
	}, function() {
		$(this).children('div').stop(true, true).slideUp(100);
	});

	$("#ManagerSaid").click(function() {
		$(this).parents('.address').next().slideToggle(100);
		$(this).parent('span').toggleClass('selected');
		$(this).parents('.noticeBtn').toggleClass('selected');

		// if ($(this).hasClass('selected'))
		// $(this).next('strong').addClass("Down")
		// else $(this).next('strong').removeClass("Down")

	});

	$(".timeline-comment .timeline-box-title textarea").click(function() {
		$(this).parent().addClass('HoldUp');
		$(this).parents('.ui-textarea').next().show();
	});
	/*$("#managerWantSayDiv").bind("mouseleave", function() {
		$(".timeline-comment .timeline-box-title textarea").parent().removeClass("HoldUp");
		$(".timeline-comment .timeline-box-title textarea").parents('.ui-textarea').next().hide();
	})*/

	$(".SaidTwo-box-title .Said-textarea textarea").click(function() {
		$(this).parent().addClass('HoldUp2');
		$(this).addClass('HoldUp2');
		$(this).parents('.Said-textarea').next().show();
	})
	$(".SaidTwo-box-title").bind("mouseleave", function() {
		$(".SaidTwo-box-title .Said-textarea textarea").parent().removeClass("HoldUp2");
		$(".SaidTwo-box-title .Said-textarea textarea").removeClass('HoldUp2');
		$(".SaidTwo-box-title .Said-textarea textarea").parents('.Said-textarea').next().hide();
	})
	
		$(".select_box input").click(function(){
		var thisinput=$(this);
		var thisul=$(this).parent().find("ul");
		if(thisul.css("display")=="none"){
			if(thisul.height()>200){thisul.css({height:"200"+"px","overflow-y":"scroll" })};
			thisul.fadeIn("100");
			thisul.hover(function(){},function(){thisul.fadeOut("100");})
			thisul.find("li").click(function(){thisinput.val($(this).text());thisul.fadeOut("100");}).hover(function(){$(this).addClass("hover");},function(){$(this).removeClass("hover");});
			}
		else{
			thisul.fadeOut("fast");
			}
	})
});


