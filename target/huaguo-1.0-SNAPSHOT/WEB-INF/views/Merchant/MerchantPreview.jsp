<%@page import="com.touco.huaguo.domain.UserEntity"%>
<%@page import="com.touco.huaguo.domain.MerchantEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	MerchantEntity merchant = new MerchantEntity();
	if (request.getAttribute("merchant") != null) {
		merchant = (MerchantEntity) request.getAttribute("merchant");
	}
	
	UserEntity userSession = new UserEntity();
	if(request.getSession() != null && request.getSession().getAttribute(Constants.USER_SESSION_INFO)!=null){
		userSession = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
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
<script type="text/javascript">
	document.createElement('header');
	document.createElement('nav');
	document.createElement('article');
	document.createElement('footer');
	document.createElement('aside');
	document.createElement('figcaption');
	document.createElement('figure');
	document.createElement('hgroup');
	document.createElement('section');
	document.createElement('details');

 </script>

<script type="text/javascript"
	src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jquery.fixed.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/specialScrollEvents.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>

<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存

	
	var index_imgs=$('#index_content').find('img');
	for(var _i=0;_i<index_imgs.length;_i++){
		newDrawImageWithWidth(index_imgs[_i], 588);
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
					<img src="${ctx}/<%=merchant.getImageUrl() %>" width="588" height="375" class="attachment-featured" alt="" title="">
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
									<img src="${ctx}/static/images/defaultHeadMin.png" alt="掌柜头像" />掌柜：暂无								
							</h3>
							
							
							<ul class="Contact">
								<li class="site"><span class="mapicon"></span><%=merchant.getAddress()%></li>
								<li class="telephone"><span class="phoneicon"></span><%=merchant.getTel()%></li>
								<li class="consumption"><span class="ptionicon"></span>人均消费:<%=merchant.getPriceRegion().getRegion()%>元
								</li>
							</ul>
						</div>
					</div>
					
					<div id="index_content" class="profile clearfix" style="padding: 10px 0;">
						<%=merchant.getContent()==null?"":merchant.getContent()%>
										
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
						<div class="head">
							<a id="iLike" href="javascript:void(0);" style= "cursor:default" class="I-like">我喜欢</a> <a
								id="saySth" href="javascript:void(0);"  style= "cursor:default" class="Said">说两句</a>
						</div>
						<div class="sentences">
							<div class="timeline-left-bottom"></div>
							<div class="SaidTwo-box-title">
								<div class="Said-box-title">
									<form accept-charset="UTF-8" action="" data-remote="true"
										method="post">
										<div class="Said-textarea">
											<div class="Said-textarea-border">
												<textarea id="userComment" name="userComment"
													placeholder="发表评论"></textarea>
												
											</div>
										</div>
										<div class="Said-box-textarea-b">
											<div class="Said-button Said-button-green">
												<span>
													<button id="userCommentBtn" type="button" onclick="javascript:void(0);">发送</button>
												</span>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					</section>
				</div>
				<div id="commentList" class="IssuedNotice">--暂无评论--</div>
				</aside>
			</div>
		</div>
		
		<%@ include file="/static/page/footer.jsp"%>
		<!--<div class="ReturnTo" id="ReturnTo"><a href="#home" title="返回顶部">返回顶部</a></div>-->

		<div id="topBottomBar" class="hidden">
			<a href="javascript:void(0)" id="goToTop" class="goToTop">回到顶部</a>
			<!-- <a href="javascript:void(0)" id="goToBottom" class="goToBottom">到达底部</a> -->
		</div>
	</div>
</body>
</html>