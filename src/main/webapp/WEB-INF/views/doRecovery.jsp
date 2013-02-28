<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String email = (String)request.getAttribute("email"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐" name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开花结果！" name="description" />
<meta content="all" name="robots" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/found.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
</head>
<body>

	<div id="found">

		<%@ include file="/static/page/header.jsp"%>

		<%@ include file="/static/page/navigation.jsp"%>

		<div id="content-wrapper">
			<div class="container">
				<section id="content" class="clearfix">
				<div id="stylized" class="Registerform">
					<h1>重置密码</h1>
					<div class="interlaced">
						<input type="hidden" name="email" id="email" value="<%=email%>"/> 
						<label class="pass" for="dpasswd">Passwrod 
							<span class="small">新密码 </span>
						</label> 
						
						<input type="password" name="password" id="password" /> 
						
						<span id="passwordMsg" class="lowrong"> </span>
					
					</div>

					<div class="interlaced">
						<label class="pass" for="dpasswd">Passwrod 
							<span class="small">确认密码 </span>
						</label> 
						
						<input type="password" name="password2" id="password2" /> 
						
						<span id="password2Msg" class="lowrong">  </span>
					</div>

					<div class="interlaced">
						<div class="registerBtn">
								<button id="resetpassword" class="btnOrng" type="submit" onclick="resetpassword()">提交</button> 
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

	<script type="text/javascript">
		function resetpassword() {
			var email = $('#email').val();
			var pwd = $('#password').val();
			var pwd2 = $('#password2').val();
			if ("" == pwd || pwd.length < 6) {
				$('#passwordMsg').text("密码至少为6位");
				return;
			}
			if ("" == pwd2 || pwd2.length < 6) {
				$('#password2Msg').text("密码至少为6位");
				return;
			}
			if (pwd != pwd2) {
				$('#password2Msg').text("密码不一致");
				return;
			}
			var parm = "email=" + email + "&password=" + pwd;
			$.ajax({type : "post",
					url : "${ctx}/doResetPassword",
					data : parm,
					success : function(returnData) {
						if(returnData.error=='0')
						{
							changepage('${ctx }/resetPwd/success');
						}
						else
						{
							$('#popup_message').popup({'html': returnData.msg});
						}						
					},
					error : function() {
						$('#popup_message').popup({'html':"发送失败！"});
					}
				});
		}
		
		
	</script>

</html>