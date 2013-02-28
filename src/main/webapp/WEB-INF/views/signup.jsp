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
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/found.css" media="all" />
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
							<h1>注册</h1>
							<div class="interlaced">
								<label for="email">Email<span class="small">邮箱</span></label> <input
									type="email" name="email" id="email" onBlur="checkEmail();hidedesc(this);"
									onfocus="showdesc(this);" /> 
									<span id="msgEmail" class="lowrong"> 
									</span>
							</div>
							
							<div class="iisi"></div>  
							
							<div class="interlaced">
								<label class="nick" for="nickname">nickName <span
									class="small">昵称</span>
								</label> <input type="text" name="nickName" id="nickName"
									onBlur="checkNickName();hidedesc(this);" onfocus="showdesc(this);" />
								<span class="lowrong" id="msgNickName"> 
								</span>
							</div>
							
							<div class="iisi"></div>					
	 						
	 						<div class="interlaced">
								<label class="pass" for="dpasswd">Passwrod <span
									class="small">密码</span></label> <input type="password" name="password"
									id="password" onBlur="checkUserPassword();hidedesc(this);"
									onfocus="showdesc(this);"  maxlength="16"/> <span id="msgPassword"
									class="lowrong"> 
									<!-- <img src="static/images/warning.png" />6-16位字母、数字混合组合 -->
								</span>
	
							</div>
							
							<div class="iisi"></div>
							
							<div class="interlaced">
								<div class="registerBtn">
<button class="btnOrng" type="submit"  onfocus="this.blur()" onclick="javascript:registerSubmit();">注册</button>
								</div>
	
								<div class="spacer">
									<span class="lxieyi"> 我已阅读并且同意
									<a href="${ctx}/faq?anchor=用户协议" target="_blank" title="用户协议">花果网使用协议</a></span>
								</div>
							</div>
					</div>
				</section>
				
				<aside id="registerRight" class="register-right" role="">
					<div class="IssuedNotice">
						<section id="" class="profile clearfix"> 
							<header class="widget-title"> 
								<h2 class="Logins">
									已经是花果的用户啦，
									<a class="immediately" title="马上登录" href="javascript:void(0)" onclick="signupOrLogin('login')">马上登录</a>
								</h2>
		
								<p>使用微博账号登录:</p>
								<p>
									<a id="sinaWeiboLogin" href="javascript:void(0);" title="使用微博账号登录">
										<img  src="${ctx}/static/images/weibo-btn.png" width="173" height="32" alt="" />
									</a>
								</p>
								
								<p>
									<a id="TencentWeiboLogin" href="javascript:void(0);" title="使用微博账号登录">
										<img src="${ctx}/static/images/tencentweibo.png" width="173" height="32" alt="" />
									</a>
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
		function showdesc(obj){
			obj = $(obj);
			obj.parents('.interlaced').next().css({'visibility':'visible'});
		}
		
		function hidedesc(obj){
			obj = $(obj);
			obj.parents('.interlaced').next().css({'visibility':'hidden'});
		}
		
	
		// 注册时 验证邮箱
		function checkEmail() {
		    var email = jQuery("#email").val();
		    var reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/g;
		    // 邮箱必须
		    if (null==email||email.length == 0) {
		        jQuery("#msgEmail").css("color", "#f00");
		        jQuery("#msgEmail").html("请输入常用的邮箱");
		        return false;
		    }
		    // 邮箱格式
		    if (!reg.test(email)) {
		        jQuery("#msgEmail").css("color", "#f00");
		        jQuery("#msgEmail").html("邮箱格式不正确");
		        return false;
		    }
		
		    jQuery("#msgEmail").html("");
		    return true;
		}
		
		
		
		function checkNickName() {
		    var nickName = jQuery("#nickName").val();
		    // 昵称必须
		    if (null==nickName||nickName.length == 0) {
		        jQuery("#msgNickName").css("color", "#f00");
		        jQuery("#msgNickName").html("请输入昵称");
		        return false;
		    }
		/* 
		    if (nickName.length < 2 || nickName.length > 16) {
		        jQuery("#msgNickName").css("color", "#f00");
		        jQuery("#msgNickName").html("昵称长度不能");
		        return false;
		    } */
		    
		    jQuery("#msgNickName").html("");
		    return true;
		}
		
		// 密码验证
		function checkUserPassword() {
		    var userPassword = jQuery("#password").val();
		    // 密码必须
		    if (null==userPassword||userPassword.length == 0) {
		        jQuery("#msgPassword").css("color", "#f00");
		        jQuery("#msgPassword").html("请输入密码");
		        return false;
		    }
		    // 密码长度只能在6-16位字符
		    var strlength = userPassword.length;
		    if (strlength < 6 || strlength > 16) {
		        jQuery("#msgPassword").css("color", "#f00");
		        jQuery("#msgPassword").html("6-16位字母、数字混合组合");
		        return false;
		    }

		    jQuery("#msgPassword").html("");
		    return true;
		}
		
		
		// 提交表单
		function registerSubmit() {
			/* checkEmail();
		    checkNickName(); 
		    checkUserPassword();
		     */
		     var tempnickname = $("#nickName").val();
		     if(checkTxtVal(tempnickname)!="")
		     {
		    	 jQuery("#msgNickName").css("color", "#f00");
				 jQuery("#msgNickName").html("只支持汉字、数字、字母和下划线");
		    	 return;
		     }
		     var nickNameLength = namelen(tempnickname);
		     if(nickNameLength>32)
		     {
		    	 jQuery("#msgNickName").css("color", "#f00");
				 jQuery("#msgNickName").html("只能输入16个汉字或32个字符");
		    	 return;
		     }
		    if (checkEmail()&&checkNickName&& checkUserPassword()) {
			    var param = {};
		    	var email = jQuery("#email").val();
		    	var nickName = jQuery("#nickName").val();
		    	var password = jQuery("#password").val();
		    	param["email"] = email;
				param["nickName"] = nickName;
				param["password"] = password;
				param["regType"] ="0";
		    	
		    	$.post("${ctx}/register",param,function(data){
					if(data.emailIsExist=="true"){
						 jQuery("#msgEmail").css("color", "#f00");
			             jQuery("#msgEmail").html(data.emailMsg);
					}else if(data.nickNameIsExist == "true"){
						 jQuery("#msgNickName").css("color", "#f00");
						 jQuery("#msgNickName").html(data.nickNameMsg);
			             //jQuery("#msgNickName").text("昵称已经被占用了，重新起一个吧");
					}else if(data.result=="true"){
						if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login') != -1) {
        					changepage('${ctx }/index');
        				}else{
       						window.location.href = location.href;
        				}
					} 
				});
		    }
		}
		
		//
		function checkTxtVal(txtValue) {
			  var forbidChar = new Array("@", "#", "$", "%", "^", "&", "*",
            		  "……", "“", "'", "￥", "×", "\"", "<", ">", "’",
            		  "”","~","·","（）","？","/",";",".",":","{","}","[","]","|","\\","+","=","`","(",")","?","、","；","：","【","】");

              for (var i = 0; i < forbidChar.length; i++) {
                  if (txtValue.indexOf(forbidChar[i]) >= 0) {
                      return "昵称只支持汉字、数字、字母和下划线";
                  }
              }
              return "";
        }
		
		
		//判断字符长度，汉字算2个字节,7个汉字或14个字符
		function namelen(s) {
			var l = 0;
			var a = s.split("");
			for (var i=0;i<a.length;i++) {
	 			if (a[i].charCodeAt(0)<299) {
	  				l++;
	 			} else {
	  				l+=2;
	 			}
			}
			return l;
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
		});
		
		
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
	</script>
	
	
	<script defer="defer">
	    $("#TencentWeiboLogin").click(function(){
	    	T.login(function (loginStatus) {
	    		T.api("/user/info")
	            .success(function (response){
	                //birth_day birth_month birth_year  city_code  comp email sex homepage //sex 1 MAN 2 FEMAN 
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
	               //  alert(message);
	                 alert("授权失败或未授权");
	             });
	    	},function (loginError) {
	    		//alert(loginError.message);
	    	});
	    });
	</script>
	
	</body>
</html>