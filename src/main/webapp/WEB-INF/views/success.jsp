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


		<div id="content-wrapper">
			<div class="container">
				<section id="content" class="clearfix">
				<div id="stylized" class="Registerform">
					<h1>修改密码成功</h1>
					<center>将在<span id='myspan'></span>秒内自动跳转到<a href='${ctx}/index'>首页</a></center>
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
			changepage('${ctx }/index');
		}
	}
	mytime = window.setInterval("index()", 1000);
	</script>
</body>
</html>