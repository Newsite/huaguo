<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String t = request.getParameter("type");
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
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/share.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/pagination.css" media="all" />
<script type="text/javascript"
	src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jquery.fixed.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/specialScrollEvents.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>

<script type="text/javascript"
	src="${ctx}/static/scripts/common/area.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/editor/kindeditor-min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/editor/lang/zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/common/jQuery-plugin/jquery.form.js"></script>
<script type="text/javascript"
	src="${ctx}/static/scripts/index/jquery.pagination.js"></script>
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
	font-size: 20px;
	padding: 5px 10px;
	text-align: center;
	width: 98%;
	margin: 0px auto;
	display: none;
}
.unchecked {
	border: 1px solid gray;
	padding: 5px;
}
.checked {
	border: 2px solid orange;
	padding: 5px;
}
</style>
<script type="text/javascript">
	var t = '<%=t%>';

	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存

	// When document has loaded, initialize pagination and form
	$(document).ready(
			function() {
				// Create pagination element with options from form

				$.post("${ctx}/merchant/getMyMerchantList", null,
						function(data) {
							var optInit = {
								callback : pageselectCallback,
								items_per_page : 5,
								num_display_entries : 5,
								num_edge_entries : 2,
								prev_text : "上页",
								next_text : "下页"
							};
							$("#myPagination").pagination(data.total, optInit);
						});

				$
						.post("${ctx}/merchant/getMyFavoriteMerchantList",
								null, function(data) {
									var optInit = {
										callback : pageselectCallback2,
										items_per_page : 5,
										num_display_entries : 5,
										num_edge_entries : 2,
										prev_text : "上页",
										next_text : "下页"
									};
									$("#myPagination2").pagination(data.total,
											optInit);
								});

				if (t == 'f') {
					$("#myFav").show();
					$("#my").hide();
					$("#myPrefer").addClass("selected");
					$("#myMerchant").removeClass("selected");
				} else {
					$("#my").show();
					$("#myFav").hide();
					$("#myMerchant").addClass("selected");
					$("#myPrefer").removeClass("selected");
				}
			});

	function pageselectCallback(page_index, jq) {
		page_index++;
		var sortType = $("#sortType").val();
		$
				.post(
						"${ctx}/merchant/getMyMerchantList?page=" + page_index
								+ "&sortType=" + sortType,
						null,
						function(data) {
							var merchantData = data.rows;
							$('#merchantTable tbody').empty();
							//动态插入数据

							for (i = 0; i < merchantData.length; i++) {
								var statusStr = "";
								var delStr = "";
								var updateStr = "";
								var approveOwner = "";
								if (merchantData[i].merchantStatus == "3")//可以删除
								{
									statusStr = "准备中";
									delStr = "<a href='javascript:void(0);' onclick='del("
											+ merchantData[i].merchantId
											+ ")'>删除</a>";
									updateStr = "<a href='javascript:void(0);' onclick='update("
											+ merchantData[i].merchantId
											+ ","
											+ merchantData[i].isOpen
											+ ")'>修改</a>";

								} else if (merchantData[i].merchantStatus == "0") {
									statusStr = "审核中";
								} else if (merchantData[i].merchantStatus == "1") {
									statusStr = "已通过";
									updateStr = "<a href='javascript:void(0);' onclick='update("
											+ merchantData[i].merchantId
											+ ","
											+ merchantData[i].isOpen
											+ ")'>修改</a>";
									if (merchantData[i].isMerchantOwner != "掌柜") {
										approveOwner = "<a href='javascript:void(0);' onclick='approveOwner("
												+ merchantData[i].merchantId
												+ ")'>成为掌柜</a>";
									}
								} else if (merchantData[i].merchantStatus == "2")//可以删除
								{
									statusStr = "未通过";
									delStr = "<a href='javascript:void(0);' onclick='del("
											+ merchantData[i].merchantId
											+ ")'>删除</a>";
									updateStr = "<a href='javascript:void(0);' onclick='update("
											+ merchantData[i].merchantId
											+ ","
											+ merchantData[i].isOpen
											+ ")'>修改</a>";
									
								} else {
								}
								var tr = "<tr><td><a href='javascript:void(0);' onclick='doPreview("
										+ merchantData[i].merchantId
										+ ","
										+ merchantData[i].merchantStatus
										+ ")'><img width='120px' height='80px' src='${ctx}/"+merchantData[i].imageUrl+"' alt='' onerror=\"javascript:this.src='${ctx}/static/images/defaultMerchant.png';\"/></a></td><td><a href='javascript:void(0);' onclick='doPreview("
										+ merchantData[i].merchantId
										+ ","
										+ merchantData[i].merchantStatus
										+ ")'>"
										+ merchantData[i].name
										+ "</a></td><td>"
										+ statusStr
										+ "</td><td>"
										+ merchantData[i].isMerchantOwner
										+ "</td><td class='optStyle'>"
										+ updateStr
										+ delStr
										+ approveOwner + "</td></tr>";
								$('#merchantTbody').append(tr);
							}
						});
	}

	function pageselectCallback2(page_index, jq) {
		page_index++;
		$
				.post(
						"${ctx}/merchant/getMyFavoriteMerchantList?page="
								+ page_index,
						null,
						function(data) {
							var merchantData = data.rows;
							$('#merchantTable2 tbody').empty();
							//动态插入数据					 
							for (i = 0; i < merchantData.length; i++) {
								var imageUrl = ctx
										+ merchantData[i].imageUrl;
								var isNew = "";
								if (merchantData[i].isNew == "1") {
									isNew = "<span class='RestNews'>新动态~</span>";
								}
								var support = "0";
								if (merchantData[i].supportNum != null) {
									support = merchantData[i].supportNum;
								}
								var tr = "<tr><td><div class='RestaurantSet clearfix'><a href='javascript:void(0);' onclick='doPreviewDetail("
										+ merchantData[i].merchantId
										+ ")'><img src='${ctx}/"+merchantData[i].imageUrl+"' width='120' height='70' alt='' onerror=\"javascript:this.src='${ctx}/static/images/defaultMerchant.png';\"/></a><h3 class='RestName'><a href='javascript:void(0);' onclick='doPreviewDetail("
										+ merchantData[i].merchantId
										+ ")'>"
										+ merchantData[i].merchantName
										+ "</a></h3>"
										+ isNew
										+ "<div class='listCuisines'><span class='cuisines'>"
										+ merchantData[i].merchantStyle
										+ "</span><span class='hearts'><em>"
										+ support
										+ "</em>人</span> </div></div></td><td>"
										+ merchantData[i].dateShow
										+ "</td><td class='optStyle'><a href='javascript:void(0);' onclick='delLike("
										+ merchantData[i].recordId
										+ ")' title=''>删除</a></td></tr>"
								$('#merchantTbody2').append(tr);
							}
						});
	}

	function delLike(recordId)
	{
		
		if(confirm("确定删除该喜欢的餐厅?"))
		{
			$.post("${ctx}/merchant/delLike?recordId=" + recordId, null, function(
					data) {
				//alert(data.msg);
				var optInit = {
					callback : pageselectCallback2,
					items_per_page : 5,
					num_display_entries : 5,
					num_edge_entries : 2,
					prev_text : "上页",
					next_text : "下页"
				};
				$("#myPagination2").pagination(data.total, optInit);
			});
		}

		
	}

	function doPreviewDetail(id) {
		changepage('${ctx}/MerchantView/MerchantIndex/' + id);
	}

	function del(id) 
	{
		if(confirm("确定删除该餐厅?"))
		{
			$.post("${ctx}/merchant/del?merchantId=" + id, null, function(data) {
				//alert(data.msg);
				sortMerchant(0);
			});
		}
		
	}

	function update(id, isOpen) {
		if (isOpen == "1") {
			changepage('${ctx }/merchant/updateMerchant?sourceType=1&returnType=1&merchantId=' + id);
		} else {
			changepage('${ctx }/merchant/editMyMerchant?merchantId=' + id);
		}

	}

	function approveOwner(id) {
		changepage('${ctx }/merchant/approveMerchantOwner?sourceType=1&returnType=2&merchantId=' + id);
	}

	function doPreview(id, status) {
		if (status == "3")//可以删除
		{
			window.open(ctx + "/merchant/merchantPreview/" + id);
		} else if (status == "0") {
			window.open(ctx + "/merchant/merchantPreview/" + id);
		} else if (status == "1") {
			window.open(ctx + "/MerchantView/MerchantIndex/" + id);

		} else if (status == "2")//可以删除
		{
			window.open(ctx + "/merchant/merchantPreview/" + id);
		} else {
		}

	}

	function sortMerchant(type) {
		$("#sortType").val(type);
		$.post("${ctx}/merchant/getMyMerchantList?sortType=" + type, null,
				function(data) {
					var optInit = {
						callback : pageselectCallback,
						items_per_page : 5,
						num_display_entries : 5,
						num_edge_entries : 2,
						prev_text : "上页",
						next_text : "下页"
					};
					$("#myPagination").pagination(data.total, optInit);
				});
	}
	function changeHead(type)
	{
		if(type==1)
		{
			$("#myMerchantHeader").html("我的餐厅");
		}else if(type==2)
		{
			$("#myMerchantHeader").html("我的喜欢");
		}
	}
</script>
</head>
<body>
<div id="share">
<%@ include file="/static/page/header.jsp"%>
<%@ include file="/static/page/navigation.jsp"%>
<div id="slogan" class="Share">
  <div class="container">
    <div class="switching clearfix">
      <header class="slogan-title">
        <h1 id="myMerchantHeader" class="slogan">我的餐厅</h1>
      </header>
      <div class="Share_menu">
        <ul>
          <li onclick="changeHead(1);" id="myMerchant" class="selected">我的餐厅</li>
          <li onclick="changeHead(2);"id="myPrefer">我的喜欢</li>
        </ul>
      </div>
    </div>
  </div>
</div>
<div id="content-wrapper">
  <div class="container">
    <div id="my" class="Formlist">
      <form action="" id="form2">
        <input type="hidden" name="sortType" id="sortType">
        </input>
        <section id="content2">
          <div class="Registerform DiningRoom support">
            <header class="introduce-title">
              <h1 class="introduce">管理我的餐厅</h1>
            </header>
            <table class="restaurantList" id="merchantTable">
              <thead>
                <tr>
                  <th class="picImg">餐厅</th>
                  <th class="Name"></th>
                  <th class="state">状态</th>
                  <th class="screening"><div class="select_box">
                      <input id="myselect" type="text" value="全部"
													readonly="readonly">
                      <ul class="select_ul">
                        <li onclick="sortMerchant(0)">全部</li>
                        <li onclick="sortMerchant(1)">掌柜</li>
                        <li onclick="sortMerchant(2)">创建者</li>
                      </ul>
                    </div></th>
                  <th class="operation">操作</th>
                </tr>
              </thead>
              <tbody id="merchantTbody">
              </tbody>
             <!-- <tfoot>
                <tr>
                  <td colspan="5"></td>
                </tr>
              </tfoot>-->
            </table>
             <div class="page"><div id="myPagination" class="pagination"></div></div>
          </div>
        </section>
      </form>
    </div>
    <div id="myFav" class="Formlist hidden">
      <section id="content4" class="clearfix">
        <div class="Registerform DiningRoom support">
          <header class="introduce-title">
            <h1 class="introduce">我的喜欢</h1>
          </header>
          <table class="likeList" id="merchantTable2">
            <thead>
              <tr>
                <th class="Name">餐厅</th>
                <th class="state">更新时间</th>
                <th class="operation">操作</th>
              </tr>
            </thead>
            <tbody id="merchantTbody2">
            </tbody>
           <!-- <tfoot>
              <tr>
                <td colspan="5"></td>
              </tr>
            </tfoot>-->
          </table>
          <div class="page"><div id="myPagination2" class="pagination"></div></div>
        </div>
      </section>
      <aside id="registerRight" class="register-right" role="">
        <div class="IssuedNotice">
          <section id="" class="profile clearfix">
            <header
							class="widget-title">
              <h2 class="Logins"> 你可能会感兴趣的 <a class="immediately green" title="换一组" href="javascript:void(0);">换一组</a> </h2>
            </header>
          </section>
          <section id="" class="profile clearfix">
            <ul class="Interested">
            </ul>
          </section>
        </div>
      </aside>
    </div>
  </div>
</div>
<%@ include file="/static/page/footer.jsp"%>
</body>
</html>