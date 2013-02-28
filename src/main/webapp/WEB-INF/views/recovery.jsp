<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta
	content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐"
	name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开花结果！" name="description" />
<meta content="all" name="robots" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/found.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
</head>
<body>

	<div id="found">
		<!--  头部  -->
		<%@ include file="/static/page/header.jsp"%>
		<!--  导航  -->
		<%@ include file="/static/page/navigation.jsp"%>


		<div id="content-wrapper">
			<div class="container">
				<section id="content" class="clearfix">
				<div id="stylized" class="Registerform">
					<h1>忘记密码</h1>
					<div class="interlaced">
						<label for="email">Email<span class="small">注册邮箱</span></label> <input
							type="email" name="email" id="email" /> <span id="msgEmail"
							class="lowrong">
						</span>
					</div>

					<div class="interlaced">
						<div class="registerBtn">
								<button id="checkemail" class="btnOrng" type="submit" onclick="sendMail()">确定</button> 
						</div>
					</div>
				</div>
				</section>
			</div>
		</div>

		<%@ include file="/static/page/footer.jsp"%>
 <div id="popup_message"> </div>
	</div>
</body>
	<script>
		function sendMail()
		{
			jQuery("#msgEmail").html("");
			var email = $('#email').val();
			if(email=="")
			{
				jQuery("#msgEmail").html("请输入邮箱");
				return;
			}
			var parm = "email=" + email;
			$.ajax({
				type : "post",
				url : "${ctx}/doRecovery",
				data : parm,
				success : function(returnData) {
					 jQuery("#msgEmail").css("color", "#f00");
					 jQuery("#msgEmail").html(""+returnData.msg);
					 $('#email').val();
				},
				error : function() {
					$('#popup_message').popup({'html':"发送失败，请稍后再试！"});
				}
			});
		}
	</script>
</html>