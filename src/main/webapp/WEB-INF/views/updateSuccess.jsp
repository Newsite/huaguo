<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐" name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开花结果！" name="description" />
<meta content="all" name="robots" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/found.css" />
</head>
<body>

	<div id="found">

		<%@ include file="/static/page/header.jsp"%>

		<%@ include file="/static/page/navigation.jsp"%>

		<%
			String merchantId = request.getParameter("merchantId");
			String value = "";
			String href=path+"/index";
			String sourceType = request.getParameter("sourceType");
			String returnType = request.getParameter("returnType");
			if("1".equals(returnType))//修改餐厅信息
			{
				value = "餐厅修改成功!如您不是掌柜，餐厅信息将于系统审核后更新";
			}else if("2".equals(returnType))//申请掌柜
			{
				value = "申请掌柜成功";
			}
			if("1".equals(sourceType))//从我的餐厅里来
			{				
				href = path+"/merchant/myMerchant?type=m";
			}else if("2".equals(sourceType))//从餐厅主页过来
			{
				href = path+"/MerchantView/MerchantIndex/"+merchantId;
			}
		%>
		<div id="content-wrapper">
			<div class="container">
				<section id="content" class="clearfix">
				<div id="stylized" class="Registerform">
					<%=value %>
					<center>将在<span id='myspan'>5</span>秒内自动跳转到<a href='<%=href %>'>上一页</a></center>
				</div>
				</section>
			</div>
		</div>
		
		<%@ include file="/static/page/footer.jsp"%>
		
	</div>

	<script type="text/javascript">
	var mytime;
	var i = 5;
	function index() {
		var myspan = document.getElementById("myspan");
		myspan.innerText = i;
		if (--i == 0) {
			window.clearInterval(mytime);
			changepage('<%=href%>');
		}
	}
	mytime = window.setInterval("index()", 1000);
	</script>
</body>
</html>