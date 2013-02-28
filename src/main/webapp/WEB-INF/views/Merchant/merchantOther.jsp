<%@page import="com.touco.huaguo.domain.DianPingEntity"%>
<%@page import="com.touco.huaguo.domain.UserEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	DianPingEntity merchant = new DianPingEntity();
	if (request.getAttribute("merchant") != null) {
		merchant = (DianPingEntity) request.getAttribute("merchant");
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
<meta
	content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐,"
	name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开发结果！" name="description" />
<meta content="all" name="robots" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/Page.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />

<script type="text/javascript"
	src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jquery.fixed.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/specialScrollEvents.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/jquery.confirm.js"></script>

<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存
	
	var index_imgs=$('#index_content').find('img');
	for(var _i=0;_i<index_imgs.length;_i++){
		newDrawImageWithWidth(index_imgs[_i], 588);
	}
	
	function newSameMerchant(_id){
		<%if(sessionUser.getUserId()==null || sessionUser.getUserId()==0){ %>
		$('#popup_message').popup();
		<%}else{ %>
		$('#confirm_message').confirm({
			html:'您真的想新建这个餐厅吗？',
			okButtonAction:function(){
				var p = {};
				p['recordId']=_id;
				$.post(ctx+'/merchant/addMerchantFromOther', p, function(ret){
					if(ret.status=='SUCCESS'){
						changepage('${ctx }/merchant/editMyMerchant?merchantId=' + ret.id);
					}else if(ret.status=='NOTLOGIN'){
						$('#popup_message').popup();
					}else if(ret.status=='SAME'){
						$('#popup_message').popup({html:'已经有人先一步创建了这个餐厅了，<br>正在审核中，近期即将上线哦！'});
					}else{
						$('#popup_message').popup({html:'噢，出错了，我们会尽快处理的！<br>您可以先<a href="javascript:void(0);" style="color:#FF6600;text-decoration:none;" onclick="window.location=\'${ctx}/MerchantView/MerchantFound\';">浏览别的餐厅</a>！'});
					}
				});
			}
		});
		<%} %>
	}
</script>
</head>

<body>
	<div id="Page">
		<%@ include file="/static/page/header.jsp"%>
		<%@ include file="/static/page/navigation.jsp"%>
		
		<div id="content-wrapper">
			<div class="container">
				<section id="content" class="columns twelve"> <article
					class="recommend"> <!-- restaurantMaximg.png -->
				<div class="featured-img">
					<%-- <img src="${ctx}/static/images/defaultMerchant.png" width="588" --%>
					<img src="<%=merchant.getPicLink()%>" width="588"
						height="375" class="attachment-featured" alt="" title="">
					<header class="post-title">
					<h2><%=merchant.getName()%></h2>
					</header>
					<div class="post-header"></div>
				</div>
				<div class="special">
					<div class="profile clearfix">
						<div class="text"><%=merchant.getRecommend()==null?"":merchant.getRecommend()%></div>
						<div class="address">
							<h3 class="shopkeeper">								
									<img src="${ctx}/static/images/defaultHeadMin.png" alt="掌柜头像" />掌柜：暂无								
							</h3>
							
							
							<ul class="Contact">
								<li class="site"><span class="mapicon"></span><%=merchant.getAddress()%></li>
								<li class="telephone"><span class="phoneicon"></span><%=merchant.getTel()%></li>
								<li class="consumption"><span class="ptionicon"></span>人均消费:<%=merchant.getAvgPrice()==null?"0":merchant.getAvgPrice()%>元
								</li>
							</ul>
						</div>
					</div>
					
					<div id="index_content" class="profile clearfix" style="padding: 10px 0;">
						<%=merchant.getDescription()%>
					</div>

				<!--The manager said hair to inform-->
				
				<!--The manager said hair end of notice--> <!--The manager said record-->
			
				<!--The manager said record end--> </article> </section>
				<aside id="sidebar" class="sidebar-right" role="">
				<div class="IssuedNotice">
					<section id="" class="profile clearfix"> <header
						class="widget-title">
					<h2 class="like">
						<span class="digital">0</span>人喜欢
					</h2>
					</header>
					<div class="widget-entry">
						<ul>
							<li class="cat-item"><a href="javascript:void(0);" title=""><img
									src="${ctx}/static/images/defaultHeadMi.png" alt="" /></a></li>
							<li class="cat-item"><a href="javascript:void(0);" title=""><img
									src="${ctx}/static/images/defaultHeadMi.png" alt="" /></a></li>
							<li class="cat-item"><a href="javascript:void(0);" title=""><img
									src="${ctx}/static/images/defaultHeadMi.png" alt="" /></a></li>
							<li class="cat-item"><a href="javascript:void(0);" title=""><img
									src="${ctx}/static/images/defaultHeadMi.png" alt="" /></a></li>
							<li class="cat-item"><a href="javascript:void(0);" title=""><img
									src="${ctx}/static/images/defaultHeadMi.png" alt="" /></a></li>
							<li class="cat-item"><a href="javascript:void(0);" title=""><img
									src="${ctx}/static/images/defaultHeadMi.png" alt="" /></a></li>
							<li class="cat-item"><a href="javascript:void(0);" title=""><img
									src="${ctx}/static/images/defaultHeadMi.png" alt="" /></a></li>
						</ul>
					</div>
					</section>
					<section id="" class="profile">
					<div class="AboutRecord clearfix">
						<a href="javascript:void(0);" alt="点击在花果网创建同名餐厅" onclick="newSameMerchant('<%=merchant.getRecordId() %>');">创建同名花果餐厅</a>
					</div>
					</section>
				</div>
				</aside>
			</div>
		</div>
		<%@ include file="/static/page/footer.jsp"%>
		
		<div id="popup_message"></div>
		<div id="confirm_message"></div>

		<div id="topBottomBar" class="hidden">
			<a href="javascript:void(0)" id="goToTop" class="goToTop">回到顶部</a>
		</div>
	</div>
</body>
</html>