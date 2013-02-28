<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/found.css" media="all" />

<script type="text/javascript"
	src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jquery.fixed.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/specialScrollEvents.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/scrollpagination.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/waterfall/jquery.wookmark.min.js"></script>
<%-- <script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/waterfall/jquery.isotope.min.js"></script> --%>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/jquery.query.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.lazyload.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/index.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<style type="text/css">
.loading {
	color: #303030;
	font-size: 20px;
	padding: 5px 10px;
	text-align: center;
	width: 98%;
	margin: 0px auto;
	display: none;
	border-radius: 5px;
}
</style>

<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存

	$(document).ready(function() {
		var navlis = $('#primary-nav').find('.nav-listing').find('li');
		for ( var i = 0; i < navlis.length; i++) {
			if ($(navlis[i]).find('a').html() == '发现') {
				$(navlis[i]).addClass('active');
				$(navlis[i]).find('a').addClass('selected');
			} else {
				$(navlis[i]).removeClass('active');
				$(navlis[i]).find('a').removeClass('selected');
			}
		}
	});
</script>
</head>

<body>
	<div id="found">
		<%@ include file="/static/page/header.jsp"%>
		<%@ include file="/static/page/navigation.jsp"%>
		<nav id="Leakage-nav" class="">
		<div class="container" id="Center">
			<div class="columns sixteen">
				<div class="nav-label">
					发现<img src="${ctx}/static/images/ArrowMax.png" alt="" />
				</div>
				<ul class="nav-listing clearfix">
					<li id="hsearch_li"></li>
					<li><a class="" id="search_area" href="javascript:void(0);">所有区域</a>
						<div class="areaList clearfix">
							<table>
								<tbody>
									<tr>
										<td><a href="javascript:void(0);">所有</a></td>
									</tr>
								</tbody>
							</table>
						</div></li>
					<li><a id="search_cuisine" class="" href="javascript:void(0);">所有菜系</a>
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
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=粤港菜';">粤港菜</a>
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=东南亚菜';">东南亚菜</a></td>
									</tr>
									<tr>
										<td><a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=日韩料理';">日韩料理</a> 
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=海鲜';">海鲜</a>
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=自助餐';">自助餐</a>
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=小吃快餐';">小吃快餐</a> 
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=火锅';">火锅</a></td>
									</tr>
									<tr>
										<td class="noneline"><a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=西餐';">西餐</a>
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=烧烤烤肉';">烧烤烤肉</a>
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=面包甜点';">面包甜点</a> 
											<a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound?cuisine=其他';">其他</a></td>
									</tr>
								</tbody>
							</table>
						</div></li>
				</ul>
				<hgroup>
				<div class="sort">
					<dl>
						<dt>排序:</dt>
						<dd class="active">
							<a id="sort_newest" href="javascript:void(0);" title="最新" class="DownJian">最新</a>
						</dd>
						<dd>
							<a id="sort_view" href="javascript:void(0);" title="人气">人气</a>
						</dd>
						<dd>
							<a id="sort_support" href="javascript:void(0);" title="喜欢">喜欢</a>
						</dd>
						<dd>
							<a id="sort_price" href="javascript:void(0);" title="人均价">人均价</a>
						</dd>
					</dl>
				</div>
				</hgroup>
			</div>
		</div>
		</nav>
		<!-- <div id="content-wrapper"> -->
			<div class="container">
				<section id="content" class="clearfix" style="width:100%;">
				<div id="post-listing" class="clearfix isotope"></div>
				<div class="loading" id="loading">...我们正在努力加载中...</div>
				<div class="loading" id="nomoreresults">您所浏览的结果已结束！</div>
				</section>
			</div>
		<!-- </div> -->
		<%@ include file="/static/page/footer.jsp"%>
		<!--<div class="ReturnTo" id="ReturnTo"><a href="#home" title="返回顶部">返回顶部</a></div>-->

		<div id="topBottomBar" class="hidden">
			<a href="javascript:void(0)" id="goToTop" class="goToTop">回到顶部</a>
			<!-- <a href="javascript:void(0)" id="goToBottom" class="goToBottom">到达底部</a> -->
		</div>
	</div>
	
	<div id="ui_popup_message">
	</div>
	
	<script type="text/javascript">
		$("#Center").BapacityFixed();
		//$("#ReturnTo").BapacityFixed();
	</script>
</body>
</html>