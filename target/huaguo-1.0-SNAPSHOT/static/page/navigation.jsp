<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.touco.huaguo.domain.UserEntity"%>
<%@page import="com.touco.huaguo.common.Constants"%>
<%
String isLogin = "";
UserEntity tempuserSession = (UserEntity)request.getSession().getAttribute(Constants.USER_SESSION_INFO);
if(null!=tempuserSession)
{
	isLogin="1";
}
%>
<script type="text/javascript">
	function goToCreateMerchant()
	{
		var isLogin = '<%=isLogin%>';
		if(isLogin=="1")
		{
			window.location='${ctx}/merchant/createMerchant';
		}else
		{
			changepage('${ctx}/login?from=merchant/createMerchant');
		}
		
	}
</script>
<nav id="primary-nav" class="">
	<div class="container">
		<ul class="nav-listing clearfix">
			<li class="Interval"><a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/index';">首页</a></li>
			<li class="Interval"><a href="javascript:void(0);" onclick="javascript:window.location='${ctx}/MerchantView/MerchantFound';">发现</a></li>
			<li><a href="javascript:void(0);" onclick="goToCreateMerchant()">分享我爱的餐厅</a></li>
		</ul>
		<hgroup>
			<div class="slogan">
				<span>花果网,是一个发起美食引导消费的平台,来到花果,开花结果！</span>
			</div>
		</hgroup>
	</div>
</nav>