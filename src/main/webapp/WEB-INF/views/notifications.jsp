<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.touco.huaguo.common.Constants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.touco.huaguo.domain.UserEntity"%>


<%
	UserEntity userSession = new UserEntity();
	if (request.getSession() != null && request.getSession().getAttribute(Constants.USER_SESSION_INFO) != null) {
		userSession = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐" name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开花结果！" name="description" />
<meta content="all" name="robots" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/share.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/pagination.css" media="all" />
<script type="text/javascript" src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/scrollpagination.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jquery.pagination.js"></script>
</head>
<body>

	<div id="share">
		<!--  头部  -->
		<%@ include file="/static/page/header.jsp"%>
		<!--  导航  -->
		<%@ include file="/static/page/navigation.jsp"%>

		<div id="slogan" class="Share">
			<div class="container">
				<div class="switching clearfix">
					<header class="slogan-title">
					<h1 class="slogan">通知
					</h1>
					</header>
					<div class="Share_menu">
						<ul>
							<li class="selected">未读通知 (<span class="notice" id="noticeCount"> 
							</span>)
							</li>
							<li>已读通知 (<span class="notice" id="noReadNoticeCount"> 
							</span>)
							</li>
						</ul>
					</div> 
				</div>
			</div>
		</div>



		<div id="content-wrapper">
			<div class="container">
				<!--  未读通知Start  -->
				<div class="Formlist">
					<section id="content2" class="clearfix">
					<div class="Registerform DiningRoom support">
						<ul class="notice" id="_noticeId">
							
						</ul>
					</div>
					<center>
						<div id="myNoticePagination" class="pagination"></div>
					</center>
					 </section>
				</div>
				<!--  未读通知END  -->
				
				<!--  已读通知Start  -->
				<div class="Formlist hidden">
         			<section id="content3" class="clearfix">
					<div class="Registerform DiningRoom support">
						<ul class="notice" id="_noReadNoticeId">
							

						</ul>
					</div>
					<center>
						<div id="myNotReadNoticePagination" class="pagination"></div>
					</center>
					</section>
				</div>
				<!--  已读通知END  -->
			</div>
		</div>

		<%@ include file="/static/page/footer.jsp"%>
	</div>

	<script type="text/javascript">
		jQuery.ajaxSetup({
			cache : false
		});//ajax不缓存

		 //用服务器时间模拟
		$(document).ready(function(){
				setInterval(initReadNotice, 1000);
				setInterval(initNotReadNotice,1000);
		});
		
		// 获取未读通知
		function initReadNotice(){
			$.post("${ctx}/notifications/getMyNoticeList?isRead=0", null, 
				function(data) {
					var optInit = {
						callback : noticePageselectCallback,
						items_per_page : 10,
						num_display_entries :10,
						num_edge_entries : 3,
						prev_text : "上页",
						next_text : "下页"
					};
					$("#myNoticePagination").pagination(data.total, optInit);
					$('#noticeCount').html(data.total);
			});
		}
		
		
		/** 通知信息回调    **/
		function noticePageselectCallback(notice_page_index, jq) {
			notice_page_index++;
			$.post("${ctx}/notifications/getMyNoticeList?isRead=0&page="+ notice_page_index ,null,
				function(data) {
					if(data.rows != null &&  data.rows.length != 0){
						var myNoticeData = data.rows;
						//动态插入数据
						$('#_noticeId').empty();
						
						for (i = 0; i < myNoticeData.length; i++) {
							var contentText,result,resultText;
							if(myNoticeData[i].isVerify==null){
								myNoticeData[i].isVerify = 1;
							}
							var path = "<a href='javascript:void(0);' style='color:blue;' onclick='doPreview("
									+ myNoticeData[i].merchantId
									+ ","
									+ myNoticeData[i].isVerify
									+ ")'>"
									+ myNoticeData[i].merchantName
									+ "</a>";
							
							if(myNoticeData[i].category==0){ //0--餐厅信息审核  1-- 动态审核 2--掌柜审核
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									result ="你好，你分享的"+path+ "餐厅信息审核通过了，已经正式上线了。赶紧去看看吧！";
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									contentText2 = myNoticeData[i].content;
								 	if (contentText2.length > 15){
								 		resultText = contentText2.substring(0, 15) + "...";
								 	}else{
								 		resultText =contentText2;
								 	}
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a target='_blank' href='"+updateLink+"' style='color:blue;'>重新修改</a>";
									result ="很遗憾，你分享的"+path +"餐厅信息审核未通过，原因是："+resultText+"。你可以"+updatePath+"后，再提交审核。";
								}
							}else if(myNoticeData[i].category==1){
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									if(myNoticeData[i].commentType==0){// 0 店长说 1团购优惠  2 促销活动
										result ="恭喜哦！你的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>店长说</font>信息通过审核。";
									}else if(myNoticeData[i].commentType==1){
										result ="恭喜哦！你的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>团购优惠</font>信息通过审核。";
									}else if(myNoticeData[i].commentType==2){
										result ="恭喜哦！你的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>促销活动</font>信息通过审核。";
									}else{
										result ="恭喜哦！你的餐厅"+path+"的动态信息通过审核。";
									}
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a target='_blank' style='color:blue;' href='"+updateLink+"'>重新修改</a>";
									contentText2 = myNoticeData[i].content;
								 	if (contentText2.length > 25){
								 		resultText = contentText2.substring(0, 25) + "...";
								 	}else{
								 		resultText = contentText2;
								 	}
								 	
								 	if(myNoticeData[i].commentType==0){// 0 店长说 1团购优惠  2 促销活动
										result ="很遗憾，你提交的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>店长说</font>信息未通过审核，原因是："+resultText+"。";
									}else if(myNoticeData[i].commentType==1){
										result ="很遗憾，你提交的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>团购优惠</font>信息未通过审核，原因是："+resultText+"。";
									}else if(myNoticeData[i].commentType==2){
										result ="很遗憾，你提交的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>促销活动</font>信息未通过审核，原因是："+resultText+"。";
									}else{
										result ="很遗憾，你提交的餐厅"+path +"的动态未通过审核，原因是："+resultText+"。";
									}
								}
							}else if(myNoticeData[i].category==2){
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									result ="恭喜哦！从现在开始，你是"+path+"的掌柜了。";
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a style='color:blue;' target='_blank'  href='"+updateLink+"'>重新修改</a>";
									contentText2 = myNoticeData[i].content;
									if (contentText2.length > 15){
								 		resultText = contentText2.substring(0, 15) + "...";
								 	}else{
								 		resultText = contentText2 ;
								 	}
									result ="很遗憾，你提交的"+path +"掌柜信息未通过审核，原因是："+resultText+"。你可以"+updatePath+"后，再提交审核。";
								}
							}else if(myNoticeData[i].category==3){ //评价
								contentText2 = myNoticeData[i].content;
								if (contentText2.length > 10){
							 		resultText = contentText2.substring(0, 10) + "...";
							 	}else{
							 		resultText = contentText2 ;
							 	}
								result ="<font style='font-weight:bold; ' color='#006030'>"+myNoticeData[i].sender.nickName+"</font> 在"+path+"回复了你的评论，他说了："+resultText;
								
							}else if(myNoticeData[i].category==4){ //4 掌柜收到评价
								
								result ="<font style='font-weight:bold; ' color='#006030'>"+myNoticeData[i].sender.nickName+"</font> 在"+path+"说了两句。赶紧去看看吧！";
							
							}else if(myNoticeData[i].category==5){//
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									result ="恭喜哦！你创建的"+path+"被"+myNoticeData[i].sender.nickName+"申请成掌柜了。";
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a style='color:blue;' target='_blank'  href='"+updateLink+"'>重新修改</a>";
									contentText2 = myNoticeData[i].content;
									if (contentText2.length > 15){
								 		resultText = contentText2.substring(0, 15) + "...";
								 	}else{
								 		resultText = contentText2 ;
								 	}
									result ="很遗憾，你创建的"+path +"被"+myNoticeData[i].sender.nickName+"申请成掌柜未通过审核，原因是："+resultText+"。你可以"+updatePath+"后，再提交审核。";
								}
							}else{
								contentText = myNoticeData[i].content;
								result=contentText;
							 	if (contentText.length > 75){
							 		result = contentText.substring(0, 75) + "...";
							 		//result = "<label title = '" + contentText + "'>" + result + "</label>";
							 	}
							}
							
							var updateIsRead ='';
							if(myNoticeData[i].isRead==0){
								updateIsRead = "<a style='color:blue' onclick='updataIsRead("+myNoticeData[i].recordId+")'>我知道了 </a>"
							}
							
							var liDetail="<li>"
								+ result
								+"-- <em>("
								+myNoticeData[i].showDate
								+" ) </em>"
								+ updateIsRead
								+"</li>";
							
							$('#_noticeId').append(liDetail);
						}
					}else{
						$('#_noticeId').html("没有未读通知");
						$('#myNoticePagination').hide();
					}
			});
		}
		
		//  获取未读通知
		function initNotReadNotice(){
			$.post("${ctx}/notifications/getMyNoticeList?isRead=1", null, 
				function(data) {
					var optInit = {
						callback : notReadNoticePageselectCallback,
						items_per_page : 10,
						num_display_entries :10,
						num_edge_entries : 3,
						prev_text : "上页",
						next_text : "下页"
					};
					$("#myNotReadNoticePagination").pagination(data.total, optInit);
					$('#noReadNoticeCount').html(data.total);
			});
		}
		
		/** 未读通知信息回调    **/
		function notReadNoticePageselectCallback(notice_page_index, jq) {
			notice_page_index++;
			$.post("${ctx}/notifications/getMyNoticeList?isRead=1&page="+ notice_page_index ,null,
				function(data) {
					if(data.rows != null &&  data.rows.length != 0){
						var myNoticeData = data.rows;
						//动态插入数据
						$('#_noReadNoticeId').empty();
						for (i = 0; i < myNoticeData.length; i++) {
							var contentText,result,resultText;
							if(myNoticeData[i].isVerify==null){
								myNoticeData[i].isVerify = 1;
							}
							var path = "<a href='javascript:void(0);' style='color:blue;' onclick='doPreview("
									+ myNoticeData[i].merchantId
									+ ","
									+ myNoticeData[i].isVerify
									+ ")'>"
									+ myNoticeData[i].merchantName
									+ "</a>";
							
							if(myNoticeData[i].category==0){ //0--餐厅信息审核  1-- 动态审核 2--掌柜审核
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									result ="你好，你分享的"+path+ "餐厅信息审核通过了，已经正式上线了。赶紧去看看吧！";
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									contentText2 = myNoticeData[i].content;
								 	if (contentText2.length > 15){
								 		resultText = contentText2.substring(0, 15) + "...";
								 	}else{
								 		resultText =contentText2;
								 	}
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a target='_blank' href='"+updateLink+"' style='color:blue;'>重新修改</a>";
									result ="很遗憾，你分享的"+path +"餐厅信息审核未通过，原因是："+resultText+"。你可以"+updatePath+"后，再提交审核。";
								}
							}else if(myNoticeData[i].category==1){
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									if(myNoticeData[i].commentType==0){// 0 店长说 1团购优惠  2 促销活动
										result ="恭喜哦！你的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>店长说</font>信息通过审核。";
									}else if(myNoticeData[i].commentType==1){
										result ="恭喜哦！你的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>团购优惠</font>信息通过审核。";
									}else if(myNoticeData[i].commentType==2){
										result ="恭喜哦！你的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>促销活动</font>信息通过审核。";
									}else{
										result ="恭喜哦！你的餐厅"+path+"的动态信息通过审核。";
									}
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a target='_blank' style='color:blue;' href='"+updateLink+"'>重新修改</a>";
									contentText2 = myNoticeData[i].content;
								 	if (contentText2.length > 15){
								 		resultText = contentText2.substring(0, 15) + "...";
								 	}else{
								 		resultText = contentText2;
								 	}
								 	
								 	if(myNoticeData[i].commentType==0){// 0 店长说 1团购优惠  2 促销活动
										result ="很遗憾，你提交的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>店长说</font>信息未通过审核，原因是："+resultText+"。";
									}else if(myNoticeData[i].commentType==1){
										result ="很遗憾，你提交的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>团购优惠</font>信息未通过审核，原因是："+resultText+"。";
									}else if(myNoticeData[i].commentType==2){
										result ="很遗憾，你提交的餐厅"+path+"的<font style='font-weight:bold; ' color='#006030'>促销活动</font>信息未通过审核，原因是："+resultText+"。";
									}else{
										result ="很遗憾，你提交的餐厅"+path +"的动态未通过审核，原因是："+resultText+"。";
									}
								}
							}else if(myNoticeData[i].category==2){
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									result ="恭喜哦！从现在开始，你是"+path+"的掌柜了。";
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a style='color:blue;' target='_blank'  href='"+updateLink+"'>重新修改</a>";
									contentText2 = myNoticeData[i].content;
									if (contentText2.length > 15){
								 		resultText = contentText2.substring(0, 15) + "...";
								 	}else{
								 		resultText = contentText2 ;
								 	}
									result ="很遗憾，你提交的"+path +"掌柜信息未通过审核，原因是："+resultText+"。你可以"+updatePath+"后，再提交审核。";
								}
							}else if(myNoticeData[i].category==3){ //评价
								contentText2 = myNoticeData[i].content;
								if (contentText2.length > 10){
							 		resultText = contentText2.substring(0, 10) + "...";
							 	}else{
							 		resultText = contentText2 ;
							 	}
								result ="<font style='font-weight:bold; ' color='#006030'>"+myNoticeData[i].sender.nickName+"</font> 在"+path+"回复了你的评论，他说了："+resultText;
								
							}else if(myNoticeData[i].category==4){ //4 掌柜收到评价
								
								result ="<font style='font-weight:bold; ' color='#006030'>"+myNoticeData[i].sender.nickName+"</font> 在"+path+"说了两句。赶紧去看看吧！";
							
							}else if(myNoticeData[i].category==5){//
								if(myNoticeData[i].isVerify==1){//1 -- 通过 2--拒绝
									result ="恭喜哦！你创建的"+path+"被"+myNoticeData[i].sender.nickName+"申请成掌柜了。";
								}else if(myNoticeData[i].isVerify==2){//1 -- 通过 2--拒绝
									var updateLink ="${ctx}/merchant/updateMerchant?merchantId="+myNoticeData[i].merchantId;
									var updatePath = "<a style='color:blue;' target='_blank'  href='"+updateLink+"'>重新修改</a>";
									contentText2 = myNoticeData[i].content;
									if (contentText2.length > 15){
								 		resultText = contentText2.substring(0, 15) + "...";
								 	}else{
								 		resultText = contentText2 ;
								 	}
									result ="很遗憾，你创建的"+path +"被"+myNoticeData[i].sender.nickName+"申请成掌柜未通过审核，原因是："+resultText+"。你可以"+updatePath+"后，再提交审核。";
								}
							}else{
								contentText = myNoticeData[i].content;
								result=contentText;
							 	if (contentText.length > 75){
							 		result = contentText.substring(0, 75) + "...";
							 	}
							}
							
							var liDetail="<li>"
								+ result
								+"-- <em>("
								+myNoticeData[i].showDate
								+" ) </em>"
								+"</li>";
							
							$('#_noReadNoticeId').append(liDetail);
						}
					}else{
						$('#_noReadNoticeId').html("没有已读通知");
						
						$('#myNotReadNoticePagination').hide();
					}
			});
		}

		
		function doPreview(id, status) {
			if (status == "3"){//可以删除
				window.open(ctx + "/merchant/merchantPreview/" + id);
			} else if (status == "0") {
				window.open(ctx + "/merchant/merchantPreview/" + id);
			} else if (status == "1") {
				window.open(ctx + "/MerchantView/MerchantIndex/" + id);
			} else if (status == "2"){//可以删除
				window.open(ctx + "/merchant/merchantPreview/" + id);
			} else {
				window.open(ctx + "/merchant/merchantPreview/" + id);
			}
		}
		
		
		function updataIsRead(recordId){
			$.post("${ctx}/notifications/updataIsRead?recordId="+ recordId ,null,
				function(data) {
			 		if(data){
			 			initReadNotice();
			 		}
			});
		}
		
	</script>

</body>
</html>