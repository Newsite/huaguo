<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="eric hui" name="author" />
<!-- 微博认证APPKEY -->
<meta property="wb:webmaster" content="873477835383d4c8" />
<meta property="qc:admins" content="250124640760517576375" />

<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐" name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开发结果！" name="description" />
<meta content="all" name="robots" /> 
<script type="text/javascript" src="${ctx}/static/scripts/index/jquery.featureList-1.0.0.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jquery.fixed.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/specialScrollEvents.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/scrollpagination.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/waterfall/jquery.wookmark.min.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/waterfall/jquery.isotope.min.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.query.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/index.js"></script>

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

	function changeCity(ct) {
		var local = window.location.href;
		local = local.replace("#", "");
		window.location = local + '/' + ct;
	}

	$(document).ready(function() {
		var navlis = $('#primary-nav').find('.nav-listing').find('li');
		for ( var i = 0; i < navlis.length; i++) {
			if ($(navlis[i]).find('a').html() == '首页') {
				$(navlis[i]).addClass('active');
				$(navlis[i]).find('a').addClass('selected');
			} else {
				$(navlis[i]).removeClass('active');
				$(navlis[i]).find('a').removeClass('selected');
			}
		}
		
		function getArea(area, len){
			area = area.replace('—', '，');
			return generalUtil.strForShort(area, len);
		}
		
		function getRecommendMerchantList() {
			var p = {};
			p['order'] = 'desc';
			p['sort'] = 'createDate';
			p['page'] = '1';
			p['rows'] = '5';
			p['recommendStatus'] = '1';
			$.post('${ctx}/merchant/getMerchantList', p, function(ret) {
				if (ret != null && ret.list != null) {
					$('#tabs').html('');
					$('#output').html('');
					/* var tlis = $('#tabs').find('li'); */
					var tlihtml = '';
					var olihtml = '';

					for ( var i = 0; i < ret.list.length; i++) {
						/* $(tlis[i].find('h3')).html(ret.list[i].name);
						$(tlis[i].find('span')).html('('+ret.list[i].supportNum+'人喜欢)'); */
						tlihtml = '';
						tlihtml = '<li><a href="javascript:void(0);"><h3>' + generalUtil.strForShort(ret.list[i].name, 18, false) + '</h3>';
						tlihtml += '<span>(' + ret.list[i].supportNum + '人喜欢)</span></a></li>';
						$('#tabs').append(tlihtml);

						olihtml = '';
						olihtml = '<li><img src="${ctx }/'+ret.list[i].imageUrl+'" onclick="javascript:window.location=\''+ctx;
						olihtml+='/MerchantView/MerchantIndex/'+ret.list[i].merchantId+'\';" style="cursor:pointer;" height="308" width="530" onload="newDrawImage(this,530,308)" />';
						if (ret.list[i].user != null) {
							olihtml += '<a class="shops" style="color: #333333;cursor: default;" href="javascript:void(0);">' + generalUtil.strForShort(ret.list[i].user.nickName, 16) + ' 推荐';
							olihtml+='<img src="'+getUserImageUrl(ret.list[i].user.imageUrl,24)+'" alt="掌柜头像" height="24" width="24" onload="newDrawImage(this,24,24)"> </a>';
						}
						olihtml += '<figure><dl><dd><em class="regional"></em><a href="javascript:void(0);">' + getArea(ret.list[i].area,20) + '</a></dd>';
						olihtml += '<dd><em class="like"></em><a href="javascript:void(0);">' + ret.list[i].supportNum + '人喜欢</a></dd>';
						olihtml += '<dd><em class="telephone"></em><a href="javascript:void(0);">' + ret.list[i].tel + '</a></dd>';
						olihtml += '<dd><em class="comments"></em><a href="javascript:void(0);">' + ret.list[i].commentNum + '条评论</a></dd></dl></figure>';
						olihtml += '<figure class="InfoList">&nbsp;</figure><div class="overview"><section>';
						olihtml += '<h2 class="InnName"><a href="javascript:void(0);" onclick="javascript:window.location=\''+ctx;
						olihtml+='/MerchantView/MerchantIndex/'+ret.list[i].merchantId+'\';">' + ret.list[i].name + '</a></h2>';
						olihtml += '<span class="cuisines">' + ret.list[i].merchantStyle + '</span>';
						olihtml += '<span class="consumption">人均:<em>' + ret.list[i].priceRegion.region + '</em>元</span></section>';
						olihtml += '<p class="content">' + ret.list[i].description + '</p></div></li>';
						$('#output').append(olihtml);
					}

					$.featureList($("#tabs li a"), $("#output li"), {
						start_item : 1
					});
				}
			});
		}

		getRecommendMerchantList();
	});
</script>
</head>
<body>
	<div id="home">
		<%@ include file="/static/page/header.jsp"%>
		<%@ include file="/static/page/navigation.jsp"%>

		<div class="main" id="slides">
			<div class="container">
				<hgroup>
				<div id="feature_list">
					<figure>今日推荐<span><img
						src="${ctx}/static/images/ArrowMax.png" alt="" /></span></figure>
					<figure class="recommend">&nbsp;</figure>
					<ul id="tabs">
						<li><a href="javascript:;">
								<h3>-- 暂无 --</h3> <span>(0人喜欢)</span>
						</a></li>
						<li><a href="javascript:;">
								<h3>-- 暂无 --</h3> <span>(0人喜欢)</span>
						</a></li>
						<li><a href="javascript:;">
								<h3>-- 暂无 --</h3> <span>(0人喜欢)</span>
						</a></li>
						<li><a href="javascript:;">
								<h3>-- 暂无 --</h3> <span>(0人喜欢)</span>
						</a></li>
						<li><a href="javascript:;">
								<h3>-- 暂无 --</h3> <span>(0人喜欢)</span>
						</a></li>
					</ul>
					<ul id="output">
					</ul>
				</div>
				</hgroup>
			</div>
		</div>
		<nav id="Leakage-nav" class="">
		<div class="container" id="float">
			<div class="columns sixteen">
				<div class="nav-label">
					发现<img src="${ctx}/static/images/ArrowMax.png" alt="" />
				</div>
				<ul class="nav-listing clearfix">
					<li><a id="search_area" class="" href="javascript:void(0);">所有区域</a>
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
				<!-- <div class="page" id=""> 
            <span id="J-channel-num">后面还有
              <label class="red" id="">45</label>
              个餐厅等你甄选…</span>
              
              <div class="pageNum">
              <a href="#"><em class="unprve">&nbsp;</em></a><span class="page-red">1</span><a href="#">2</a><a href="#"><em class="next">&nbsp;</em></a>
               </div>
            </div> -->
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
		$("#float").capacityFixed();
		//$("#ReturnTo").BapacityFixed();
	</script>
</body>
</html>