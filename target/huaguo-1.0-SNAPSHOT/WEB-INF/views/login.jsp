<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐" name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开花结果！" name="description" />
<meta content="all" name="robots" />
<meta http-equiv="refresh" content="200" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/found.css" />
</head>
<body>
	<%
		Cookie[] cookies = request.getCookies();
		String email = "";
		String passwd = "";
		boolean ischk = false;
		if (cookies != null) {
			for(Cookie cookie : cookies){
				if(Constants.COOKIE_SERVER_UNAME.equals(cookie.getName())){
					email = cookie.getValue();
				}else if(Constants.COOKIE_SERVER_UPWD.equals(cookie.getName())){
					passwd = cookie.getValue();
				}
			}
		}
		if(StringUtils.isNotBlank(email) && StringUtils.isNotBlank(passwd) ){
			ischk = true;
		}
		
		request.setAttribute("ischk", ischk);
	%>

	<div id="found">

		<%@ include file="/static/page/header.jsp"%>

		<%@ include file="/static/page/navigation.jsp"%>

		<div id="content-wrapper">
			<div class="container">
				<section id="content" class="clearfix">
					<div id="stylized" class="Registerform">
							<h1>登录</h1>
							
							<div class="interlaced">
								<label for="email">Email<span class="small">邮箱</span></label> 
								<input type="email" name="email" id="email" onBlur="checkEmail();" onfocus="showEditEmailInfo();" value="<%=email %>" /> 
								<span id="msgEmailPassword" class="lowrong"> 
									
								</span>
							</div>
	
							<div class="interlaced">
								<label class="pass" for="dpasswd">Passwrod 
									<span class="small">密码</span>
								</label> 
								<input type="password" name="password" id="password" value="<%=passwd %>"/> 
								<span class="lowrong"><a href="${ctx}/recovery" class="lostpasswd">忘记密码？</a></span>
							</div>
							
							<div class="iisi iisiShow">
								<span class="jizme"> 
									
									<c:if test="${ischk==true}">
						              	<input name="isRemember" type="checkbox" id="isRemember" checked="checked" class="czl">
						            </c:if>
						            <c:if test="${ischk==false}">
						              	<input name="isRemember" type="checkbox" id="isRemember"   class="czl">
						            </c:if>
										记住我&nbsp;
								</span>
							</div>
							
							<div class="interlaced">
								<div class="registerBtn">
									<button id="loginBtn" class="btnOrng" type="button"   onclick="loginSubmit();">
										登录
									</button>
								</div>
							</div>
					</div>
				</section>
				
				
				<aside id="registerRight" class="register-right" role="">
					<div class="IssuedNotice">
						<section id="" class="profile clearfix"> 
							<header class="widget-title">
							<h2 class="Logins"> 还没有花果账号？
								<a class="immediately green" title="马上注册" href="javascript:void(0)" onclick="signupOrLogin('signup')">马上注册</a>
							</h2>
							<p>使用微博账号登录:</p>
							<p>
								<a id="sinaWeiboLogin" href="javascript:void(0);"   title="使用微博账号登录">
									<img src="${ctx }/static/images/weibo-btn.png" width="173" height="32" alt="使用微博账号登录" />
								</a>
							</p>
							
							<p>
								<a id="tencentWeiboLogin" href="javascript:void(0);"   title="使用微博账号登录">
								<img src="${ctx }/static/images/tencentweibo.png" width="173" height="32" alt="使用微博账号登录" /></a>
							</p> 
							</header> 
						</section>
					</div>
				</aside>
			</div>
		</div>


		<%@ include file="/static/page/footer.jsp"%>
		
	</div>


	<script type="text/javascript">
		
	$(function(){
		document.onkeydown = function(e){ 
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	loginSubmit();
		     }
			}
		});   
	
		function showEditEmailInfo() {
			jQuery("#msgEmailPassword").css("color", "#f00");
		    jQuery("#msgEmailPassword").html("请输入常用的邮箱");
		}
	
		function checkEmail(){
			 var email = jQuery("#email").val();
		    var reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/g;
		    // 邮箱必须
		    if (null==email||email.length == 0) {
		        jQuery("#msgEmailPassword").css("color", "#f00");
		        jQuery("#msgEmailPassword").html("请输入常用的邮箱");
		        return false;
		    }
		    // 邮箱格式
		    if (!reg.test(email)) {
		        jQuery("#msgEmailPassword").css("color", "#f00");
		        jQuery("#msgEmailPassword").html("邮箱格式不正确");
		        return false;
		    }
			
		    jQuery("#msgEmailPassword").html("");
		    return true;
		}
	
		// 提交表单
		function loginSubmit() {
			var email = jQuery.trim($('#email').val());
			
			var password = jQuery.trim($('#password').val());
			
			var isRemember = $("#isRemember").attr("value");
			
			var str="";
            $("input[name='isRemember']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    str += $(this).val()+","
                }
            })
           	isRemember= str.split(",")[0];
            
			if(checkEmail()){
				var param = "email=" + email + "&password=" + password+ "&isRemember=" + isRemember;
				$.post("${ctx}/login", param, function(data) {
					if (data.result == "true") {
						if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login') != -1) {
        					changepage('${ctx }/index');
        				}else{
       						window.location.href = location.href;
        				}
						//alert(data.messageCount);
						//jQuery("#msgEmailPassword").html(data.messageCount);
					} else if (data.emailIsExist == "true") {
						jQuery("#msgEmailPassword").css("color", "#f00");
						jQuery("#msgEmailPassword").html(data.emailMsg);
					} else {
						jQuery("#msgEmailPassword").css("color", "#f00");
						jQuery("#msgEmailPassword").html("邮箱密码不匹配");
					}
				});
			}
		}
		
		
		//微博登录
		$("#sinaWeiboLogin").click(function(){
			WB2.login(function(){ 
				/***授权成功后回调***/
				getWbUserData(function(user){ 
					/***o是/users/show.json接口返回的json对象***/
					$.post("${ctx}/WeiboAuth?screenName="+encodeURIComponent(user.screen_name)
	            			+"&profileImageUrl="+user.profile_image_url+"&gender="
	            			+user.gender+"&webPage="+user.url+"&regType=1"
	            			,null,function(data){
	            		
	        			if(data.result=="true"){
	        				if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login') != -1) {
	        					changepage('${ctx }/index');
	        				}else{
	       						window.location.href = location.href;
	        				}
	        			}else if(data.result=="false"){
	        				WB2.logout(function(){
	        				});
	        			}
	        		}); 
					
				}); 
			}); 
		})
		
		
		function getWbUserData(callback) {
			WB2.anyWhere(function(W) {
				/***获取授权用户id***/
				W.parseCMD("/account/get_uid.json", function(sResult, bStatus) {
					if (!!bStatus) {
						/**请求uid成功后调用以获取用户数据**/
						getData(W, sResult);
					} else {
						/*** 这里只是简单处理出错***/
						alert("授权失败或错误");
					}
				}, {}, {
					method : 'GET'
				});
		});
			
			
		/***请求用户数据，并执行回调***/
		function getData(W, User) {
				W.parseCMD("/users/show.json", function(sResult, bStatus) {
					if (!!bStatus && !!callback) {
						callback.call(this, sResult);
					}
				}, {
					'uid' : User.uid
				}, {
					method : 'GET'
				});
			}
		};
	
	
		//检查是否自动登录
		jQuery(document).ready(function(){
			var ischk ="<%=ischk%>"
			var email = "<%=email %>";
			var passwd = "<%=passwd %>";
			if(!ischk){
				if(email != null && passwd!=null){
					var param = "email="+email +"&passwd="+passwd;
					$.post("${ctx}/autologon",param,function(data){						
						if(data.result=="true"){
							if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login') != -1) {
	        					changepage('${ctx }/index');
	        				}else{
	       						window.location.href = location.href;
	        				}
						}
					});
				}
			}
		});
	</script>
	
	<script defer="defer">
	    $("#tencentWeiboLogin").click(function(){
	    	T.login(function (loginStatus) {
	    		T.api("/user/info")
	            .success(function (response){
	                //birth_day birth_month birth_year  city_code  comp email sex homepage
	                //sex 1 MAN 2 FEMAN 
	            	$.post("${ctx}/WeiboAuth?screenName="+encodeURIComponent(response.data.nick)
	           			+"&profileImageUrl="+response.data.head+"&gender="
	           			+response.data.sex+"&homepage="+response.data.homepage+"&regType=2"
	    				,null,function(data){
							if(data.result=="true"){
								if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login') != -1) {
		        					changepage('${ctx }/index');
		        				}else{
		       						window.location.href = location.href;
		        				}
							}else if(data.result=="false"){
								T.logout(function (loginStatus){});
							}
					}); 
	             })
	            .error(function (code, message) {
	                 alert(message);
	             });
	    	},function (loginError) {
	    		//alert(loginError.message);
	    	});
	    });
	</script>
	
	</body>
</html>