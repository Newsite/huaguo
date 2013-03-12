<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.touco.huaguo.domain.UserEntity"%>
<%@page import="com.touco.huaguo.domain.MerchantEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	MerchantEntity merchant = new MerchantEntity();
	if (request.getAttribute("merchant") != null) {
		merchant = (MerchantEntity) request.getAttribute("merchant");
	}

	UserEntity sessionUser = new UserEntity();
	if (request.getSession() != null
			&& request.getSession().getAttribute(
					Constants.USER_SESSION_INFO) != null) {
		sessionUser = (UserEntity) request.getSession().getAttribute(
				Constants.USER_SESSION_INFO);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="eric hui" name="author" />
<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐,"
	name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开发结果！" name="description" />
<meta content="all" name="robots" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/Page.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />

<script type="text/javascript" src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jquery.fixed.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/specialScrollEvents.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/kindeditor-min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/lang/zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/jquery.confirm.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/DatePicker/WdatePicker.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/scripts/common/DatePicker/skin/WdatePicker.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<style type="text/css">
.payAttention {
	border-color: #FF6600;
	border-width: 3px;
}
</style>
<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存

	var editor;
	KindEditor.ready(function(K) {
		var item =['formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		           'lineheight', 'justifyleft', 'justifycenter', 'justifyright',
		           'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', '|', 
		           'image', 'hr', 'emoticons', '/', 'undo', 'redo', '|', 'selectall', 'cut', 'copy', 'paste',
		           'plainpaste', 'wordpaste', '|', 'about']
		editor = K.create('#editor_id', {
	        width : '517px',
	        height:'250px',
	        items :item,
	        resizeType : 0, //2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。默认值: 2
	        uploadJson:'${ctx}/merchant/uploadEditorImg'  
		});
		
	});
	
	var params = {};
	params['order'] = 'desc';
	params['sort'] = 'createDate';
	params['page'] = '1';
	params['rows'] = '20';
	if (location.pathname != '') {
		var _ps = location.pathname.split('/');
		params['merchantId'] = parseInt(_ps[_ps.length-1],10);
	}else{
		params['merchantId'] = 0;
	}
	
	function showReplyArea(i){
		<%if (sessionUser.getUserId() == null) {%>

		<%}else{ %>
		if($('#userReply'+i).parent().css('display')=='none'){
			$('#userReply'+i).parent().show();
			if($.browser.msie) $('#userReply'+i).parent().parent().parent().css('height','235px');
			var as = $('#userReply'+i).parent().parent().find('.DateTime a');
			for(var i=0; i<as.length; i++){
				if($(as[i]).html()=='回复'){
					$(as[i]).html('收起').attr('title', '收起回复');
					break;
				}
			}
		}else{
			$('#userReply'+i).parent().hide();
			if($.browser.msie) $('#userReply'+i).parent().parent().parent().css('height','105px');
			var as = $('#userReply'+i).parent().parent().find('.DateTime a');
			for(var i=0; i<as.length; i++){
				if($(as[i]).html()=='收起'){
					$(as[i]).html('回复').attr('title', '回复');
					break;
				}
			}
		}
		<%} %>
	}
	function setMerchantComment(i, pid){
		var say = $.trim($('#userReply'+i).val());
		if(generalUtil.strLengthCN(say)>280){
			$('#popup_message').popup({'html': '请保持您的内容在280个字符以内！'});
			return false;
		}
		if(say != ''){
			var p = {};
			p['content'] = say;
			p['merchantId'] = params['merchantId'];
			addMerchantComment(p, pid);
		}else{
			$('#popup_message').popup({'html': '请填写您的回复！'});
		}
	}
	
	function addMerchantComment(p, pid){
		p['pid'] = pid;
		$.post(ctx + '/merchant/addMerchantComment', p, function(ret) {
			if(ret == 'SUCCESS'){
				$.post(ctx+'/merchant/getMerchantCommentList', params, function(ret){
					if(ret != null && ret.list != null && ret.list.length != 0){
						$('#userComment').val('');
						initComment(ret);
					}else{
						$('#commentList').html('<section class="profile clearfix">-- 暂无评论 --</section>');
					}
				});
			}else if(ret=='NOTLOGIN'){
				$('#popup_message').popup();
			}
		});
	}
	
	function deleteMerchantComment(rid){
		$('#confirm_message').confirm({
			html:'确认删除这条消息吗？',
			okButtonAction:function(){
				var p = {};
				p['recordId'] = rid;
				$.post(ctx + '/merchant/deleteMerchantComment', p, function(ret) {
					if(ret == 'SUCCESS'){
						$.post(ctx+'/merchant/getMerchantCommentList', params, function(ret){
							if(ret != null && ret.list != null && ret.list.length != 0){
								$('#userComment').val('');
								initComment(ret);
							}else{
								$('#commentList').html('<section class="profile clearfix">-- 暂无评论 --</section>');
							}
						});
					}else if(ret=='NOTLOGIN'){
						$('#popup_message').popup();
					}
				});
			}
		});
	}
	
	function deleteMerchantEvent(rid){
		$('#confirm_message').confirm({
			html:'确认删除这条消息吗？',
			okButtonAction:function(){
				var p = {};
				p['recordId'] = rid;
				$.post(ctx + '/merchant/deleteMerchantEvent', p, function(ret) {
					if(ret == 'SUCCESS'){
						getMerchantEventList();
						getGroupBuyAndPromotionList();
					}else if(ret=='NOTLOGIN'){
						$('#popup_message').popup();
					}
				});
			}
		});
	}
	function initComment(ret){
		$('#commentList').html('');
		var thtml = '';
		for(var i=0; i<ret.list.length; i++){
			if(i%2==0){
				thtml+='<section class="profile clearfix"><div class="AboutRecord bubble"><div class="header" style="text-align:center;">';
				thtml+='<img src="'+getUserImageUrl(ret.list[i].sender.imageUrl)+'" alt="用户头像" height="48" width="48" onload="newDrawImage(this,48,48)">';
				thtml+='<span class="name" style="padding-right:12px;">'+generalUtil.strForShort(ret.list[i].sender.nickName, 9)+'</span></div>';
				thtml+='<div class="TheManager"><div class="timeline-left-gray"></div>';
				thtml+='<div class="timeline-box-title timeline-start"><p class="Contents">'+ret.list[i].content;
				thtml+='</p><p class="Contents">'+new Date(ret.list[i].createDate).format('yyyy-M-d h:m');
				thtml+='</p><p class="DateTime">';
				<%if (sessionUser.getUserId() == null) {%>
				/* thtml+='<a title="登录后可操作回复" href="javascript:void(0);">登录后可操作回复</a>'; */
				<%} else if (merchant.getUser() != null
					&& merchant.getUser().getUserId().longValue() == sessionUser
							.getUserId().longValue()) {%>
				if(ret.list[i].sender.userId != <%=sessionUser.getUserId()%>){
					thtml+='<a href="javascript:void(0);" title="回复" onclick="showReplyArea(\''+i+'\')">回复</a>';
				}
				thtml+='&nbsp;&nbsp;<a title="删除" href="javascript:void(0);" onclick="deleteMerchantComment(\''+ret.list[i].recordId+'\')">删除</a>';
				<%} else {%>
				if(ret.list[i].sender.userId==<%=sessionUser.getUserId()%>){
					thtml+='&nbsp;&nbsp;<a title="删除" href="javascript:void(0);" onclick="deleteMerchantComment(\''+ret.list[i].recordId+'\')">删除</a>';
				}else{
					thtml+='<a href="javascript:void(0);" title="回复" onclick="showReplyArea(\''+i+'\')">回复</a>';
				}
				<%}%>
				thtml+='</p></div></div><div class="userReply" style="display:none;"><textarea id="userReply'+i+'" placeholder="发表回复" placeholder1="发表回复" style="resize:none;"></textarea>';
				thtml+='<div class="ui-button ui-button-green ui-button-ajax"><span><input type="button" value="发送" onclick="setMerchantComment(\''+i+'\', \''+ret.list[i].recordId+'\')">';
				thtml+='</span></div></div></div></section>';
			}else{
				thtml+='<section class="profile clearfix"><div class="AboutRecord balloon"><div class="customer" style="text-align:center;">';
				thtml+='<img src="'+getUserImageUrl(ret.list[i].sender.imageUrl)+'" alt="用户头像" height="48" width="48" onload="newDrawImage(this,48,48)">';
				thtml+='<span class="name" style="padding-left:12px;">'+generalUtil.strForShort(ret.list[i].sender.nickName, 9)+'</span></div>';
				thtml+='<div class="TheManager"><div class="timeline-left-rights"></div>';
				thtml+='<div class="timeline-box-title timeline-start"><p class="Contents">'+ret.list[i].content;
				thtml+='</p><p class="Contents">'+new Date(ret.list[i].createDate).format('yyyy-M-d h:m');
				thtml+='</p><p class="DateTime">';
				<%if (sessionUser.getUserId() == null) {%>
				/* thtml+='<a title="登录后可操作回复" href="javascript:void(0);">登录后可操作回复</a>'; */
				<%} else if (merchant.getUser() != null
					&& merchant.getUser().getUserId().longValue() == sessionUser
							.getUserId().longValue()) {%>
				if(ret.list[i].sender.userId != <%=sessionUser.getUserId()%>){
					thtml+='<a href="javascript:void(0);" title="回复" onclick="showReplyArea(\''+i+'\')">回复</a>';
				}
				thtml+='&nbsp;&nbsp;<a title="删除" href="javascript:void(0);" onclick="deleteMerchantComment(\''+ret.list[i].recordId+'\')">删除</a>';
				<%} else {%>
				if(ret.list[i].sender.userId==<%=sessionUser.getUserId()%>){
					thtml+='&nbsp;&nbsp;<a title="删除" href="javascript:void(0);" onclick="deleteMerchantComment(\''+ret.list[i].recordId+'\')">删除</a>';
				}else{
					thtml+='<a href="javascript:void(0);" title="回复" onclick="showReplyArea(\''+i+'\')">回复</a>';
				}
				<%}%>
				thtml+='</p></div></div><div class="userReply" style="display:none;"><textarea id="userReply'+i+'" placeholder="发表回复" placeholder1="发表回复" style="resize:none;"></textarea>';
				thtml+='<div class="ui-button ui-button-green ui-button-ajax"><span><input type="button" value="发送" onclick="setMerchantComment(\''+i+'\', \''+ret.list[i].recordId+'\')">';
				thtml+='</span></div></div></div></section>';
			}
		}
		$('#commentList').html(thtml);
		var _al = $('#commentList').find('textarea');
		for(var i=0; i<_al.length; i++){
			$(_al[i]).placeholder();
		}
	}
	function changeTab(index){
		var lis = $('#indexTab').find('li');
		for(var i=0; i<lis.length; i++){
			if(i==index){
				$(lis[i]).addClass('selected');
				$('#indexTab'+i).show();
			}else{
				$(lis[i]).removeClass('selected');
				$('#indexTab'+i).hide();
			}
		}
	}
	function getMerchantEventList(){
		$.post(ctx + '/merchant/getMerchantEventList', params, function(ret) {
			$('#managerSaid').html('');
			var thtml = '';
			if (ret != null && ret.list != null && ret.list.length != 0) {
				for ( var i = 0; i < ret.list.length; i++) {
					thtml = '';
					thtml += '<div class="AboutRecord"><div class="header" style="text-align:center;">';
					thtml+='<img src="'+getUserImageUrl(ret.list[i].createUser.imageUrl)+'" alt="用户头像" height="48" width="48" onload="newDrawImage(this,48,48)">';
					thtml += '<span class="name" style="padding-right:12px;">' + ret.list[i].createUser.nickName + '</span></div>';
					thtml += '<div class="TheManager"><div class="timeline-left-gray"></div>';
					thtml += '<div class="timeline-box-title timeline-start">';
					thtml += '<div class="Contents">' + ret.list[i].content + '</div>';
					thtml += '<p class="DateTime"><span>' + new Date(ret.list[i].createDate).format('yyyy-M-d h:m') + '</span></p>';
					<% if(merchant.getUser()!=null && sessionUser.getUserId()!=null && merchant.getUser().getUserId().longValue() == sessionUser.getUserId().longValue()){%>
					thtml+='<p class="DateTime"><a href="javascript:void(0);" onclick="deleteMerchantEvent(\''+ret.list[i].recordId+'\')">删除</a></p>';
					<%} else {%>
					if(ret.list[i].createUser.userId==<%=sessionUser.getUserId() %>){
						thtml+='<p class="DateTime"><a href="javascript:void(0);" onclick="deleteMerchantEvent(\''+ret.list[i].recordId+'\')">删除</a></p>';
					}
					<%} %>
					thtml+='</div></div></div>';

					$('#managerSaid').append(thtml);
				}
			} else {
				thtml = '';
				thtml += '<div class="AboutRecord">该掌柜到现在什么都没有说诶！</div>';
				$('#managerSaid').append(thtml);
			}
		});
	}
	function getGroupBuyAndPromotionList(){
		var p = {};
		for(var key in params){
			p[key]=params[key];
		}
		p['searchType'] = 'GroupBuyAndPromotion';
		$.post(ctx+'/merchant/getMerchantEventList', p, function(ret){
			if(ret != null && ret.list != null && ret.list.length != 0){
				$('#groupBuyAndPromotion').html('');
				var thtml = '';
				for(var i=0; i<ret.list.length; i++){
					thtml='';
					if(ret.list[i].eventType=='1'){
						thtml+='<div class="groupBuy"><div style="padding-top:10px;"><a target="_blank" href="'+ret.list[i].eventlink+'">【团购优惠】'+ret.list[i].content+'</a></div>';
						thtml+='<div class="clearfix" style="width:513px;"><span class="activityDate">时间：'+new Date(ret.list[i].startDate).format('yyyy-M-d');
						thtml+='&nbsp;~&nbsp;'+new Date(ret.list[i].endDate).format('yyyy-M-d')+'</span>';
						<% if(merchant.getUser()!=null && sessionUser.getUserId()!=null && merchant.getUser().getUserId().longValue() == sessionUser.getUserId().longValue()){%>
						thtml+='<span style="float:right;display: inline-block;" class="DateTime"><a href="javascript:void(0);" style="" onclick="deleteMerchantEvent(\''+ret.list[i].recordId+'\')">删除</a></span>';
						<%} else {%>
						if(ret.list[i].createUser.userId==<%=sessionUser.getUserId() %>){
							thtml+='<span style="float:right; display: inline-block;" class="DateTime"><a href="javascript:void(0);" style="" onclick="deleteMerchantEvent(\''+ret.list[i].recordId+'\')">删除</a></span>';
						}
						<%} %>
						thtml+='<span style="float:right;">由&nbsp;'+ret.list[i].createUser.nickName+'&nbsp;发布&nbsp;&nbsp;&nbsp;</span>';
						thtml+='</div></div>';
					}else if(ret.list[i].eventType=='2'){
						thtml+='<div class="promotion"><div style="padding-top:10px;">【促销活动】'+ret.list[i].content+'</div>';
						thtml+='<div class="clearfix" style="width:513px;"><span class="activityDate">时间：'+new Date(ret.list[i].startDate).format('yyyy-M-d');
						thtml+='&nbsp;~&nbsp;'+new Date(ret.list[i].endDate).format('yyyy-M-d')+'</span>';
						<% if(merchant.getUser()!=null && sessionUser.getUserId()!=null && merchant.getUser().getUserId().longValue() == sessionUser.getUserId().longValue()){%>
						thtml+='<span style="float:right;display: inline-block;" class="DateTime"><a href="javascript:void(0);" style="" onclick="deleteMerchantEvent(\''+ret.list[i].recordId+'\')">删除</a></span>';
						<%} else {%>
						if(ret.list[i].createUser.userId==<%=sessionUser.getUserId() %>){
							thtml+='<span style="float:right;display: inline-block;" class="DateTime"><a href="javascript:void(0);" style="" onclick="deleteMerchantEvent(\''+ret.list[i].recordId+'\')">删除</a></span>';
						}
						<%} %>
						thtml+='<span style="float:right;">由&nbsp;'+ret.list[i].createUser.nickName+'&nbsp;发布&nbsp;&nbsp;&nbsp;</span>';
						thtml+='</div></div>';
					}
					$('#groupBuyAndPromotion').append(thtml);
					$('#groupBuyAndPromotion').parent().addClass('profile');
				}
			}else{
				$('#groupBuyAndPromotion').html('');
				$('#groupBuyAndPromotion').parent().removeClass('profile');
			}
		});
	}
	
	function toBeOwner(){
		<%if(sessionUser.getUserId() != null) {%>
		changepage('${ctx }/merchant/approveMerchantOwner?isOpen=1&sourceType=2&returnType=2&merchantId=<%=merchant.getMerchantId() %>');
		<%} else {%>
		$('#popup_message').popup();
		<%} %>
	}
	$(document).ready(function() {
		var navlis = $('#primary-nav').find('.nav-listing').find('li');
		for ( var i = 0; i < navlis.length; i++) {
			$(navlis[i]).removeClass('active');
			$(navlis[i]).find('a').removeClass('selected');
			/* if ($(navlis[i]).find('a').html() == '发现') {
				$(navlis[i]).addClass('active');
				$(navlis[i]).find('a').addClass('selected');
			} else {
				$(navlis[i]).removeClass('active');
				$(navlis[i]).find('a').removeClass('selected');
			} */
		}
		
		$('#tab_merchant').html(generalUtil.strForShort('<%=merchant.getName() %>',30, false));

		function getCityAreaList(){
			$.post(ctx+'/city/getCityAreaList', '', function(ret){
				if(ret != null && ret.list != null && ret.list.length != 0){
					$('#search_area').parent().find('tbody').html('');
					var thtml = '<tr><td><a href="javascript:void(0);" onclick="javascript:window.location=\''+ctx+'/MerchantView/MerchantFound\';">所有</a></td></tr>';
					for(var i=0; i<ret.list.length; i++){
						if(i==0){
							thtml+='<tr><td>';
						}else if(i%5==0){
							if(ret.list.length - i <= 5){
								thtml+='</td></tr><tr><td class="noneline">';
							}else{
								thtml+='</td></tr><tr><td>';
							}
						}
						thtml+='<a href="javascript:void(0);" onclick="javascript:window.location=\''+ctx+'/MerchantView/MerchantFound?area='+ret.list[i].areaName+'\';">'+ret.list[i].areaName+'</a>';
					}
					thtml+='</td></tr>';
					$('#search_area').parent().find('tbody').html(thtml);
				}
			});
		}
		
		getCityAreaList();

		function getMerchantCommentList(){
			$.post(ctx+'/merchant/getMerchantCommentList', params, function(ret){
				if(ret != null && ret.list != null && ret.list.length != 0){
					initComment(ret);
				}else{
					$('#commentList').html('<section class="profile clearfix">-- 暂无评论 --</section>');
				}
			});
		}
		
		getMerchantCommentList();
		
		getGroupBuyAndPromotionList();
		
		function getMerchantLikeList(){
			var p = {};
			p['merchantId'] = params['merchantId'];
			$.post(ctx+'/merchant/getMerchantLikeList', p, function(ret){
				if(ret != null && ret.length != 0){
					var html = '';
					for(var i=0; i<ret.length; i++){
						html+='<li class="cat-item"><a href="javascript:void(0);" style="cursor:default;" title="'+ret[i].nickName+'">';
						html+='<img src="'+getUserImageUrl(ret[i].imageUrl,28)+'" height="28" width="28" onload="newDrawImage(this,28,28)" alt="'+ret[i].nickName+'头像" /></a></li>';
					}
					$('.widget-entry').children().html(html);
				}
			});
		}
		
		getMerchantLikeList();
		
		function setHasLiked(mid){
			<%if(sessionUser.getUserId()!=null && merchant.getUser()!=null && merchant.getUser().getUserId().longValue()==sessionUser.getUserId().longValue()){ %>
			$('#iLike').addClass('notLike');
			<%} else { %>
			var p = {};
			p['merchantId'] = mid;
			$.post(ctx+'/merchant/getUserLikeList', p, function(ret){
				if(ret != null && ret.length != 0){
					$('#iLike').addClass('notLike');
				}
			});
			<%} %>
		}
		
		$("#search_area").click(function() {
			$(this).siblings('.areaList').fadeToggle(100);
			$(this).toggleClass('selected');
			$(this).parent("li.login").toggleClass('selected').css("cursor", "pointer");
			$(this).next('strong').toggleClass("Down");
		});

		$('#search_area').parent().bind("mouseleave", function() {
			if ($(this).find('div').css('display') == 'block') {
				$("#search_area").click();
			}
		});

		$("#search_cuisine").click(function() {
			$(this).siblings('.areaList').fadeToggle(100);
			$(this).toggleClass('selected');
			$(this).parent("li.login").toggleClass('selected').css("cursor", "pointer");
			$(this).next('strong').toggleClass("Down");

			$('#search_cuisine').parent().find('div').css('right', '1px');
		});

		$('#search_cuisine').parent().bind("mouseleave", function() {
			if ($(this).find('div').css('display') == 'block') {
				$("#search_cuisine").click();
			}
		});
		
		<%if (sessionUser.getUserId() == null) {%>
		$('#saySth').click(function(){
			//$('#popup_message').popup();
			$('.Said-textarea').addClass('payAttention');
			var to11=setTimeout(function(){$('.Said-textarea').removeClass('payAttention');clearTimeout(to11);}, 500);
			var to12=setTimeout(function(){$('.Said-textarea').addClass('payAttention');clearTimeout(to12);}, 1000);
			var to13=setTimeout(function(){$('.Said-textarea').removeClass('payAttention');clearTimeout(to13);}, 1500);
		});
		<%} else {%>
		$('#saySth').click(function(){
			//$('#goToTop').click();
			$('#userComment').focus();
			$('#userComment').click();
		});
		<%}%>
		
		$('#saySth2').click(function(){
			//$('#goToTop').click();
			$("html,body").animate({scrollTop:"0px"},800,null,function(){
				<%if (sessionUser.getUserId() == null) {%>
				//$('#popup_message').popup();
				$('.Said-textarea').addClass('payAttention');
				var to21=setTimeout(function(){$('.Said-textarea').removeClass('payAttention');clearTimeout(to21);}, 500);
				var to22=setTimeout(function(){$('.Said-textarea').addClass('payAttention');clearTimeout(to22);}, 1000);
				var to23=setTimeout(function(){$('.Said-textarea').removeClass('payAttention');clearTimeout(to23);}, 1500);
				<%} else {%>
				$('#userComment').focus();
				$('#userComment').click();
				<%}%>
			});
		});
		
		<%if (sessionUser.getUserId() == null) {%>
		$('#iLike').click(function(){
			$('#popup_message').popup();
		});
		<%} else {%>
		$('#iLike').one('click', function(event){
			if($('#iLike').hasClass('notLike')){
				return false;
			}
			$.post(ctx + '/merchant/addMyFavorite', params, function(ret) {
				if(ret == 'SUCCESS'){
					//$(this).unbind("click");
					$('.like .digital').html(parseInt($('.like .digital').html(),10)+1);
					getMerchantLikeList();
					$.post(ctx+'/merchant/getMerchantCommentList', params, function(ret){
						if(ret != null && ret.list != null && ret.list.length != 0){
							$('#userComment').val('');
							initComment(ret);
						}else{
							$('#commentList').html('<section class="profile clearfix">-- 暂无评论 --</section>');
						}
					});
					//$('#popup_message').popup({'html': '感谢您对小店的支持！'});
				}else if(ret=='NOTLOGIN'){
					$('#popup_message').popup();
				}else if(ret=='SAMEUSER'){
					//$(this).unbind("click");
					$('#popup_message').popup({'html': '您已经是本店的掌柜了哦！'});
				}else if(ret == 'HADLIKED'){
					//$(this).unbind("click");
					$('#popup_message').popup({'html': '您已经支持过了，感谢您对小店的支持！'});
				}
				$('#iLike').addClass('notLike');
			});
		});
		<%}%>
		
		$('#indexTabBtn0').click(function(){
			<%if (sessionUser.getUserId() == null) {%>
				$('#popup_message').popup();
			<%} else if (merchant.getUser() != null && merchant.getUser().getUserId().longValue() != sessionUser.getUserId().longValue()) {%>
				
			<%} else {%>
				editor.sync();
				var say = $.trim($('#editor_id').val());
				if(say != ''){
					var p = {};
					p['content'] = say;
					p['eventType'] = '0';
					p['merchantId'] = params['merchantId'];
					$.post(ctx + '/merchant/managerWantSay', p, function(ret) {
						if(ret == 'SUCCESS'){
							/* $('#managerWantSay').val(''); */
							editor.html('');
							$('#editor_id').val('');
							getMerchantEventList();
							getMerchantCommentList();
							$('#ManagerSaid').click();
							<%if (merchant.getUser() == null) {%>
							$('#popup_message').popup({'html': '信息已提交，请耐心等待审核！','buttonText':'确定'});
							<%} else {%>
							$('#popup_message').popup({'html': '信息发布成功！','buttonText':'确定'});
							<%} %>
						}else if(ret=='NOTLOGIN'){
							$('#popup_message').popup();
						}
					});
				}else{
					$('#popup_message').popup({'html': '您要说的还没写呢！','buttonText':'确定'});
				}
			<%}%>
		});
		
		$('#indexTabBtn1').click(function(){
			<%if (sessionUser.getUserId() == null) {%>
				$('#popup_message').popup();
			<%} else if (merchant.getUser() != null && merchant.getUser().getUserId().longValue() != sessionUser.getUserId().longValue()) {%>
				
			<%} else {%>
				var t1sd = generalUtil.getDate($('#tab1_startDate').val());
				var t1ed = generalUtil.getDate($('#tab1_endDate').val());
				if(!isNaN(t1sd) && !isNaN(t1ed) 
						&& generalUtil.isNotBlank($('#tab1_url').val()) 
						&& generalUtil.isNotBlank($('#tab1_description').val())
						&& $('#tab1_description').val()!=$('#tab1_description').attr('placeholder1')){
					
					if(t1ed.format('yyyy-MM-dd')<new Date().format('yyyy-MM-dd')){
						$('#popup_message').popup({'html': '结束时间要在今天或以后哦！'});
						return false;
					}
					
					if(t1sd.format('yyyy-MM-dd')>t1ed.format('yyyy-MM-dd')){
						$('#popup_message').popup({'html': '结束时间要大于等于开始时间哦！'});
						return false;
					}
					
					if(generalUtil.strLengthCN($.trim($('#tab1_description').val()))>200){
						$('#popup_message').popup({'html': '请保持您的内容在200个字符以内！'});
						return false;
					}
					
					var strRegex = "^((https|http|ftp|rtsp|mms)?://)"      
	                    + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" //ftp的user@     
	                    + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184     
	                    + "|" // 允许IP和DOMAIN（域名）     
	                    + "([0-9a-zA-Z_!~*'()-]+\.)*" // 域名- www.     
	                    + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\." // 二级域名     
	                    + "[a-zA-Z]{2,6})" // first level domain- .com or .museum     
	                    + "(:[0-9]{1,4})?" // 端口- :80     
	                    + "((/?)|"      
	                    + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
					var re=new RegExp(strRegex);
					if(!re.test($.trim($('#tab1_url').val()))){
						$('#popup_message').popup({'html': '请输入正确的URL！'});
						return false;
					}
					var p = {};
					p['startDate'] = $('#tab1_startDate').val();
					p['endDate'] = $('#tab1_endDate').val();
					p['eventlink'] = $.trim($('#tab1_url').val());
					p['content'] = $.trim($('#tab1_description').val());
					p['eventType'] = '1';
					p['merchantId'] = params['merchantId'];
					$.post(ctx + '/merchant/managerWantSay', p, function(ret) {
						if(ret == 'SUCCESS'){
							$('#tab1_startDate').val('');
							$('#tab1_endDate').val('');
							$('#tab1_url').val('');
							$('#tab1_description').val('');
							getGroupBuyAndPromotionList();
							getMerchantCommentList();
							$('#ManagerSaid').click();
							<%if (merchant.getUser() == null) {%>
							$('#popup_message').popup({'html': '信息已提交，请耐心等待审核！','buttonText':'确定'});
							<%} else {%>
							$('#popup_message').popup({'html': '信息发布成功！','buttonText':'确定'});
							<%} %>
						}else if(ret=='NOTLOGIN'){
							$('#popup_message').popup();
						}
					});
				}else{
					$('#popup_message').popup({'html': '您还有些没填写呢！','buttonText':'确定'});
				}
			<%}%>
		});
		
		$('#indexTabBtn2').click(function(){
			<%if (sessionUser.getUserId() == null) {%>
				$('#popup_message').popup();
			<%} else if (merchant.getUser() != null && merchant.getUser().getUserId().longValue() != sessionUser.getUserId().longValue()) {%>
				
			<%} else {%>
				var t2sd = generalUtil.getDate($('#tab2_startDate').val());
				var t2ed = generalUtil.getDate($('#tab2_endDate').val());
				if(!isNaN(t2sd) && !isNaN(t2ed) 
						&& generalUtil.isNotBlank($('#tab2_description').val())
						&& $('#tab2_description').val()!=$('#tab2_description').attr('placeholder1')){
					
					if(t2ed.format('yyyy-MM-dd')<new Date().format('yyyy-MM-dd')){
						$('#popup_message').popup({'html': '结束时间要在今天或以后哦！'});
						return false;
					}
					
					if(t2sd.format('yyyy-MM-dd')>t2ed.format('yyyy-MM-dd')){
						$('#popup_message').popup({'html': '结束时间要大于等于开始时间哦！'});
						return false;
					}
					
					if(generalUtil.strLengthCN($.trim($('#tab2_description').val()))>200){
						$('#popup_message').popup({'html': '请保持您的内容在200个字符以内！'});
						return false;
					}
					
					var p = {};
					p['startDate'] = $('#tab2_startDate').val();
					p['endDate'] = $('#tab2_endDate').val();
					p['content'] = $.trim($('#tab2_description').val());
					p['eventType'] = '2';
					p['merchantId'] = params['merchantId'];
					$.post(ctx + '/merchant/managerWantSay', p, function(ret) {
						if(ret == 'SUCCESS'){
							$('#tab2_startDate').val('');
							$('#tab2_endDate').val('');
							$('#tab2_description').val('');
							getGroupBuyAndPromotionList();
							getMerchantCommentList();
							$('#ManagerSaid').click();
							<%if (merchant.getUser() == null) {%>
							$('#popup_message').popup({'html': '信息已提交，请耐心等待审核！','buttonText':'确定'});
							<%} else {%>
							$('#popup_message').popup({'html': '信息发布成功！','buttonText':'确定'});
							<%} %>
						}else if(ret=='NOTLOGIN'){
							$('#popup_message').popup();
						}
					});
				}else{
					$('#popup_message').popup({'html': '您还有些没填写呢！','buttonText':'确定'});
				}
			<%}%>
		});
		
		$('#userCommentBtn').click(function(){
			<%if (sessionUser.getUserId() == null) {%>
				$('#popup_message').popup();
			<%} else {%>
				var say = $.trim($('#userComment').val());

				if(generalUtil.strLengthCN(say)>280){
					$('#popup_message').popup({'html': '请保持您的内容在280个字符以内！','buttonText':'确定'});
					return false;
				}
				if(say != ''){
					var p = {};
					p['content'] = say;
					p['merchantId'] = params['merchantId'];
					addMerchantComment(p, 0);
				}else{
					$('#popup_message').popup({'html': '您还有些没填写呢！','buttonText':'确定'});
				}
			<%}%>
		});
		
		<%-- var merchantArea = '<%=merchant.getArea()%>';
		if(generalUtil.isNotBlank(merchantArea)){
			var areaArr = merchantArea.split('—');
			$('#breadCrumb').html('');
			for(var i=0; i<areaArr.length; i++){
				var hl = '';
				if(i==0){
					hl+='<li><a class="" href="javascript:void(0);" onclick="javascript:window.location=\'${ctx}/MerchantView/MerchantFound\';">';
					hl+=$('#header_city').html()+'站</a></li>';
					$('#search_area').attr('title', areaArr[i]);
					$('#search_area').html('<span class="Map"></span>'+areaArr[i]);
					$('#search_cuisine').attr('title', '<%=merchant.getMerchantStyle() %>');
					$('#search_cuisine').html('<span class="CateringType"></span><%=merchant.getMerchantStyle() %>');
				}
				hl+='<li><a class="" href="javascript:void(0);" onclick="javascript:window.location=\'${ctx}/MerchantView/MerchantFound?area='+areaArr[i]+'\';">';
				hl+=areaArr[i]+'</a></li>';
				if(i==areaArr.length-1){
					hl+='<li><a class="" href="javascript:void(0);">';
					hl+='<%=merchant.getName()%></a></li>';
				}
				$('#breadCrumb').append(hl);
			}
		} --%>

		<%if (sessionUser.getUserId() != null) {%>
			setHasLiked(<%=merchant.getMerchantId() %>);
			getMerchantEventList();
			<%if (merchant.getUser() != null) {%>
				<%if (merchant.getUser().getUserId().longValue() != sessionUser.getUserId().longValue()) {%>
					$('#managerWantSayDiv').css('display', 'none');
				<%}else{%>
					$('#index_content').bind('mouseenter', function(){$('#updateContent').show();});
					$('#index_content').bind('mouseleave', function(){$('#updateContent').hide();});
				<%} %>
			<%} else {%>
				$('#index_content').bind('mouseenter', function(){$('#updateContent').show();});
				$('#index_content').bind('mouseleave', function(){$('#updateContent').hide();});
			/* $('#managerWantSayDiv').css('display', 'none');
			$('#managerSaid').html('<div class="AboutRecord">该店还没有掌柜认领哦！</div>'); */
			<%}%>
		<%} else {%>
			getMerchantEventList();
			$('#managerWantSayDiv').css('display', 'none');
			$('#userCommentNotLoginDiv').show();
			$('#userComment').hide();
			//$('#iLike').addClass('notLike');
			//$('#saySth').addClass('notSay');
		<%}%>
		
		// 限制餐厅内容图片宽度不超边界
		var index_imgs=$('#index_content').find('img');
		for(var _i=0;_i<index_imgs.length;_i++){
			newDrawImageWithWidth(index_imgs[_i], 588);
		}
		
		// 掌柜头像显示
		<%if (merchant.getUser() != null) {%>
		$('.shopkeeper').html('<img src="'+getUserImageUrl('<%=merchant.getUser().getImageUrl() %>',24)+'" alt="掌柜" height="24" width="24" onload="newDrawImage(this,24,24)" />');
		$('.shopkeeper').append('<%=merchant.getUser().getNickName()%>');
		<%}else{%>
		$('.shopkeeper').html('<img src="${ctx}/static/images/defaultHeadMin.png" alt="掌柜" height="24" width="24" onload="newDrawImage(this,24,24)" />');
		$('.shopkeeper').append('暂无掌柜&nbsp;<a herf="javascript:void(0);" onclick="toBeOwner()" style="color: #F60; text-decoration: none;"> 成为掌柜</a>');
		<%}%>
	});
</script>
</head>

<body>
<div id="Page">
  <%@ include file="/static/page/header.jsp"%>
  <%@ include file="/static/page/navigation.jsp"%>
  <nav id="BreadCrumbs-nav" class="">
    <div class="container">
      <div class="columns sixteen">
        <div class="nav-label"> 发现<img src="${ctx}/static/images/ArrowMax.png" alt="" /> </div>
        <ul id="breadCrumb" class="nav-listing clearfix">
        </ul>
        <hgroup>
          <div class="sort">
            <dl>
              <dd class=""> <a href="javascript:void(0);" id="search_area" title=""><span
								class="Map"></span>所有</a>
                <div class="areaList clearfix">
                  <table>
                    <tbody>
                      <tr>
                        <td><a href="javascript:void(0);">所有</a></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </dd>
              <dd> <a id="search_cuisine" href="javascript:void(0);" title=""><span
								class="CateringType"></span>所有</a>
                <div class="areaList clearfix">
                  <table>
                    <tbody>
                      <tr>
                        <td><a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound';">所有</a></td>
                      </tr>
                      <tr>
                        <td><a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=江浙菜';">江浙菜</a>
                        	<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=川菜';">川菜</a> 
                        	<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=湘菜';">湘菜</a> 
                        	<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=鲁菜';">鲁菜</a> 
                        	<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=北京菜';">北京菜</a> </td>
                      </tr>
                      <tr>
                        <td>
                        	<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=粤港菜';">粤港菜</a> 
                        	<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=东南亚菜';">东南亚菜</a>
                        	<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=日韩料理';">日韩料理</a> 
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=海鲜';">海鲜</a> 
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=自助餐';">自助餐</a></td>
                      </tr>
                      <tr>
                        <td> 
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=小吃快餐';">小吃快餐</a> 
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=火锅';">火锅</a>
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=西餐';">西餐</a> 
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=烧烤烤肉';">烧烤烤肉</a> 
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=面包甜点';">面包甜点</a></td> 
                      </tr>
                      <tr>
                        <td class="noneline"> 
                        <a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=其他';">其他</a></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </dd>
            </dl>
          </div>
        </hgroup>
      </div>
    </div>
  </nav>
  <div id="content-wrapper">
    <div class="container">
      <section id="content" class="columns twelve">
        <article
					class="recommend"> <!-- restaurantMaximg.png -->
          <div class="featured-img" style="height:375px;"> <img src="${ctx}/<%=merchant.getImageUrl() %>" onerror="javascript:this.src='${ctx}/static/images/defaultMerchant.png';" width="588"
						height="375" class="attachment-featured" alt="" title="">
            <header class="post-title">
              <h2><%=merchant.getName()%></h2>
            </header>
            <div class="post-header"></div>
          </div>
          <div class="special">
            <div class="profile clearfix">
              <div class="text"><%=merchant.getDescription()%></div>
              <div class="address">
                <h3 class="shopkeeper">
                  
                </h3>
                <%if (sessionUser.getUserId()!=null && merchant.getUser() != null && merchant.getUser().getUserId().longValue() != sessionUser.getUserId().longValue()) {%>
                <!-- 用户登录后，只有餐厅有掌柜，且登录的人不是掌柜时才有发送私信按钮 -->
                <img src="${ctx}/static/images/email.png" alt="发送私信"  id="privateLetter2Owner"  onclick="showPrivateLetter('<%=merchant.getUser().getNickName()%>')" />
                <%}%>
                <ul class="Contact">
                  <li class="site"><span class="mapicon"></span><%=merchant.getAddress()%></li>
                  <li class="telephone"><span class="phoneicon"></span><%=merchant.getTel()%></li>
                  <li class="consumption"><span class="ptionicon"></span>人均消费:<%=merchant.getPriceRegion().getRegion()%>元 </li>
                </ul>
              </div>
            </div>
            <div class="clearfix">
              <div id="groupBuyAndPromotion"></div>
            </div>
            <div id="index_content" class="profile clearfix" style="padding: 10px 0;"> <%=merchant.getContent()%>
              <div id="updateContent" style="display: none; float: right;"> <img alt="修改图标" src="${ctx}/static/images/edit_pen.gif"> <a href="javascript:void(0);" style="font-size: 15px; color:#FF6600; text-decoration: none;"
							onclick="changepage('${ctx }/merchant/updateMerchant?sourceType=2&returnType=1&merchantId=<%=merchant.getMerchantId() %>');"> 修改信息</a></div>
            </div>
            <%if(merchant.getUpdateUser() != null){ %>
            <div style="float: right; padding-right: 20px;">由&nbsp;<%=merchant.getUpdateUser().getNickName() %>&nbsp;更新</div>
            <%} %>
            <div class="buttons">
              <div id="" class="ui-button-blue"> <span><a id="saySth2" href="javascript:void(0);">发表评论 </a></span> </div>
            </div>
          </div>
          
          <!--The manager said hair to inform-->
          <div id="managerWantSayDiv" class="IssuedNotice">
            <div class="profile">
              <header class="post-header">
                <h2 class="post-title">掌柜说</h2>
              </header>
              <div class="address">
                <h3 class="noticeBtn"> <span><a href="javascript:void(0);" id="ManagerSaid">&gt;&gt;发通知</a></span> </h3>
              </div>
              <div class="timeline-comment">
                <div class="timeline-comment-top"></div>
                <div class="switching clearfix">
                  <header class="slogan-title">
                    <h4 class="slogan">近期，在<span id="tab_merchant"></span>有...</h4>
                  </header>
                  <div id="indexTab" class="Share_menu">
                    <ul>
                      <li onclick="changeTab('0');" class="selected" style="cursor: pointer;">餐厅动态</li>
                      <li onclick="changeTab('1');" style="cursor: pointer;">团购优惠</li>
                      <li onclick="changeTab('2');" style="cursor: pointer;">促销活动</li>
                    </ul>
                  </div>
                </div>
                <div id="indexTab0" class="timeline-box-title">
                  <div class="ui-textarea">
                    <div class="interlaced editor">
                      <div class="editor-inside">
                        <textarea id="editor_id" style="width: 380px; height: 70px"></textarea>
                      </div>
                    </div>
                    <!-- <div class="ui-textarea-border">
										<textarea id="managerWantSay" name="managerWantSay"
											placeholder="我有话要说"></textarea>
									</div> --> 
                  </div>
                  <div class="timeline-box-textarea-b" style="display: block;">
                    <div class="ui-button ui-button-green ui-button-ajax"> <span>
                      <button id="indexTabBtn0" type="button">发布</button>
                      </span> </div>
                  </div>
                </div>
                <div id="indexTab1" class="timeline-box-title" style="display: none;">
                  <div style="clear: both;">
                    <div> <span>优惠时间：</span>
                      <input id="tab1_startDate" type="text" style="width:150px;" readonly class="Wdate" 
                      	onFocus="WdatePicker({isShowWeek:true,highLineWeekDay:true,firstDayOfWeek:1,isShowClear:false,readOnly:true})"/>
                      <span>&nbsp;到&nbsp;</span>
                      <input id="tab1_endDate" style="width:150px;" type="text" readonly class="Wdate" 
                      	onFocus="WdatePicker({isShowWeek:true,highLineWeekDay:true,firstDayOfWeek:1,isShowClear:false,readOnly:true})">
                    </div>
                    <div> <span>URL地址：</span>
                      <input id="tab1_url" type="text">
                    </div>
                    <div> <span>描述说明：</span>
                      <textarea id="tab1_description" placeholder="请在这边填写团购优惠的说明描述，在100字以内。" 
                      	placeholder1="请在这边填写团购优惠的说明描述，在100字以内。" style="resize:none;width:510px;max-width:510px;"></textarea>
                    </div>
                  </div>
                  <div class="timeline-box-textarea-b" style="display: block;">
                    <div class="ui-button ui-button-green ui-button-ajax"> <span>
                      <button id="indexTabBtn1" type="button">发布</button>
                      </span> </div>
                  </div>
                </div>
                <div id="indexTab2" class="timeline-box-title" style="display: none;">
                  <div style="clear: both;">
                    <div> <span>促销时间：</span>
                      <input id="tab2_startDate" style="width:150px;" type="text" readonly class="Wdate" 
                      	onFocus="WdatePicker({isShowWeek:true,highLineWeekDay:true,firstDayOfWeek:1,isShowClear:false,readOnly:true})">
                      <span>&nbsp;到&nbsp;</span>
                      <input id="tab2_endDate" style="width:150px;" type="text" readonly class="Wdate" 
                      	onFocus="WdatePicker({isShowWeek:true,highLineWeekDay:true,firstDayOfWeek:1,isShowClear:false,readOnly:true})">
                    </div>
                    <div> <span>描述说明：</span>
                      <textarea id="tab2_description" placeholder="请在这边填写促销活动的说明描述，在100字以内。" 
                      	placeholder1="请在这边填写促销活动的说明描述，在100字以内。" style="resize:none;width:510px;max-width:510px;"></textarea>
                    </div>
                  </div>
                  <div class="timeline-box-textarea-b" style="display: block;">
                    <div class="ui-button ui-button-green ui-button-ajax"> <span>
                      <button id="indexTabBtn2" type="button">发布</button>
                      </span> </div>
                  </div>
                </div>
                <%if (merchant.getUser() == null){ %>
                <div id="addingPrompt"> <span>提示: 你还不是这个餐厅的掌柜，分享的信息需要在花果客服审核后才可见哦。想单独打理餐厅，赶紧&nbsp; <a herf="javascript:void(0);" onclick="toBeOwner()"> 申请成为掌柜</a> &nbsp;~！ </span></div>
                <%} %>
              </div>
            </div>
          </div>
          <!--The manager said hair end of notice--> <!--The manager said record-->
          <div class="IssuedNotice">
            <div id="managerSaid" class="profile clearfix"></div>
          </div>
          <!--The manager said record end--> </article>
      </section>
      <aside id="sidebar" class="sidebar-right" role="">
        <div class="IssuedNotice">
          <section id="" class="profile clearfix">
            <header
						class="widget-title">
              <h2 class="like"> <span class="digital"><%=merchant.getSupportNum()%></span>人喜欢 </h2>
            </header>
            <div class="widget-entry">
              <ul>
              </ul>
            </div>
          </section>
          <section id="" class="profile">
            <div class="AboutRecord clearfix">
              <div class="head"> <a id="iLike" href="javascript:void(0);" class="I-like">我喜欢</a> <a
								id="saySth" href="javascript:void(0);" class="Said">说两句</a> </div>
              <div class="sentences">
                <div class="timeline-left-bottom"></div>
                <div class="SaidTwo-box-title">
                  <div class="Said-box-title">
                    <form accept-charset="UTF-8" action="" data-remote="true"
										method="post">
                      <div class="Said-textarea">
                        <div class="Said-textarea-border" style="width:195px;">
                          <textarea id="userComment" name="userComment"
								placeholder="发表评论" placeholder1="发表评论"></textarea>
                          <div id="userCommentNotLoginDiv" style="display: none;"> 请登录后评论，立即 <a href="javascript:void(0)" onclick="signupOrLogin('login')" title="登录">登录</a> 或 <a
														href="javascript:void(0)" onclick="signupOrLogin('signup')" title="注册账号">注册</a> </div>
                        </div>
                      </div>
                      <div class="Said-box-textarea-b">
                        <div class="Said-button Said-button-green"> <span>
                          <button id="userCommentBtn" type="button">发送</button>
                          </span> </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </div>
        <div id="commentList" class="IssuedNotice"></div>
      </aside>
    </div>
  </div>
  <%@ include file="/static/page/footer.jsp"%>
  <!--<div class="ReturnTo" id="ReturnTo"><a href="#home" title="返回顶部">返回顶部</a></div>-->
  
  <div id="topBottomBar" class="hidden"> <a href="javascript:void(0)" id="goToTop" class="goToTop">回到顶部</a> 
    <!-- <a href="javascript:void(0)" id="goToBottom" class="goToBottom">到达底部</a> --> 
  </div>
  <div id="popup_message"> </div>
  <div id="confirm_message"> </div>
  <div class="ui-popup ui-popup-textarea" id="ui_popup_message" style="display: none;">
    <div class="ui-popup-background">
      <div class="ui-popup-content ui-draggable" style="top: 145px;">
        <div style="margin: 0; padding: 0; display: inline">
          <input type="hidden" value="✓" name="utf8">
        </div>
        <table cellspacing="0" cellpadding="0" border="0" align="center"
							class="ui-popup-table">
          <tbody>
            <tr>
              <td width="25" class="ui-popup-top-l"></td>
              <td class="ui-popup-top"><span class="ui-popup-title" id="uipoptitleNmae"></span></td>
              <td width="25" class="ui-popup-top-r"><a class="ui-popup-close" id="close">关闭</a></td>
            </tr>
            <tr>
              <td class="ui-popup-mid-l"></td>
              <td class="ui-popup-mid"><div class="ui-textarea">
                  <div class="ui-textarea-border">
                    <textarea class="ui-text-fixed" name="content"
													id="message_content"></textarea>
                  </div>
                </div>
                <div class="ui-popup-textarea-b"> <span id="message_content_error"></span>
                  <div class="ui-button ui-button-green ui-button-ajax"> <span>
                    <button type="submit" id="send">发送</button>
                    </span> </div>
                </div></td>
              <td class="ui-popup-mid-r"></td>
            </tr>
            <tr>
              <td class="ui-popup-bottom-l"></td>
              <td class="ui-popup-bottom"></td>
              <td class="ui-popup-bottom-r"></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">

  var result  ;
  function showPrivateLetter(nickName){
	  clearInterval(result) ;
	  $('#ui_popup_message').show();
	  $('#uipoptitleNmae').html('私信给 '+nickName);
	  $('#message_content_error').html('');
  } 
  
  $("#close").click(function(){
	  $('#ui_popup_message').hide();
	  $('#message_content_error').html('');
  });
  
  $(document).ready(function() {
	var merchantArea = '<%=merchant.getArea()%>';
	if(generalUtil.isNotBlank(merchantArea)){
		var areaArr = merchantArea.split('—');
		$('#breadCrumb').html('');
		for(var i=0; i<areaArr.length; i++){
			var hl = '';
			if(i==0){
				hl+='<li><a class="" href="javascript:void(0);" onclick="javascript:window.location=\'${ctx}/MerchantView/MerchantFound\';">';
				hl+=$('#header_city').html()+'站</a></li>';
				$('#search_area').attr('title', areaArr[i]);
				$('#search_area').html('<span class="Map"></span>'+areaArr[i]);
				$('#search_cuisine').attr('title', '<%=merchant.getMerchantStyle() %>');
				$('#search_cuisine').html('<span class="CateringType"></span><%=merchant.getMerchantStyle() %>');
			}
			hl+='<li><a class="" href="javascript:void(0);" onclick="javascript:window.location=\'${ctx}/MerchantView/MerchantFound?area='+areaArr[i]+'\';">';
			hl+=areaArr[i]+'</a></li>';
			if(i==areaArr.length-1){
				hl+='<li style="background:none;"><a style="color: #333333;cursor: default;" href="javascript:void(0);">';
				hl+='<%=merchant.getName()%></a></li>';
			}
			$('#breadCrumb').append(hl);
		}
	}
	
	$('#tab1_description').placeholder();
	$('#tab2_description').placeholder();
	$('#userComment').placeholder();
  });
  
  <%if(merchant.getUser()!=null) {%>
  $("#send").click(function(){
		 var manangerUserId =  <%=merchant.getUser().getUserId()%>;
		 var content = $.trim($('#message_content').val());
		 if(generalUtil.strLengthCN(content)>280){
			$('#message_content_error').html('<font color="red">请保持您的内容在280个字符以内！</font>');
			return false;
		}
		if(content != ''){
			var p = {};
			p['content'] = content;
			p['pid'] = '0';
			p['recordId']='0';
			p['sender.userId'] = manangerUserId;
			
			$.post(ctx + '/messages/addPrivateLetter', p, function(ret) {
				if(ret == 'SUCCESS'){
					document.getElementById("message_content").value='';
					$('#message_content_error').html('<font color="red">发送成功！</font>');
					result = setInterval("$('#ui_popup_message').hide()", 1000); 
					//$('#message_content_error').html('');
				}else if(ret=='NOTLOGIN'){
					$('#popup_message').popup();
				}else if(ret=='FAILURE'){
					$('#message_content_error').html('<font color="red">您已经给他发过私信了哦！</font>');
					document.getElementById("message_content").value='';
					setInterval("$('#message_content_error').html('')", 1000); 
				}else if(ret=="ERROR"){
					$('#message_content_error').html('<font color="red">发过私信失败！</font>');
					setInterval("$('#message_content_error').html('')", 1000); 
				}
			});
		}else{
			$('#message_content_error').html('<font color="red">请输入私信内容！</font>');
			setInterval("$('#message_content_error').html('')", 1000); 
		}
  });
  <%} %>
</script>
</html>