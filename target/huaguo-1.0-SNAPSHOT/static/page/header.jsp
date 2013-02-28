<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.touco.huaguo.common.Constants"%>

<%
	String objUName = (String)session.getAttribute(Constants.UNICKNAME_SESSION);
	/**String tempfileSys = Constants.DEFAULT_FILE_SYS;
	if (session.getAttribute(Constants.FILE_SYS_DOMAIN) != null) {
		tempfileSys = (String) session.getAttribute(Constants.FILE_SYS_DOMAIN);
	} **/
	Object profileImageurl = session.getAttribute(Constants.PROFILE_ImageUrl_SESSION);
	if (null != profileImageurl &&( profileImageurl.toString().indexOf("sinaimg") > -1
					|| profileImageurl.toString().indexOf("qlogo") > -1
					|| profileImageurl.toString().indexOf("gtimg") > -1		
			)) {
		profileImageurl= profileImageurl.toString();
	} else if (null != profileImageurl && !"".equals(profileImageurl.toString())) {
		profileImageurl =  profileImageurl.toString();
	}
	Object messageCount = session.getAttribute(Constants.PRIVATE_LETTER_COUNT);
	
	String regType = (String)session.getAttribute(Constants.REG_TYPE);
	
	request.setCharacterEncoding("UTF-8");
%>
	
<script type="text/javascript">
	function changeCity(ct){
		var param = {};
		param['city'] = ct;
		$.post(ctx+'/user/setCitySession', param, function(ret){
			if(ret == 'SUCCESS'){
				var local = window.location.pathname;
				/* var local = window.location.href;
				local = local.replace("#", ""); */
				window.location = local;
			}
		});
	}
	
	function signupOrLogin(s){
		if(generalUtil.isBlank(s)){
			s='login';
		}
		var _from = location.pathname;
		if(_from.indexOf('/signup')!=-1 || _from.indexOf('/login')!=-1){
			_from=getQueryString('from');
		}
		var _url = '${ctx }/'+s+'?from='+_from;
		window.location=_url;
	}
	
	$(document).ready(function() {
		$('#hsearch').placeholder();
		
		$.post(ctx+'/user/getCitySession', null, function(ret){
			$('#header_city').html(ret.city);
		});
		
		$('#searchsubmit').click(function(){
			if($('#hsearch').val()!=$('#hsearch').attr('placeholder1') && generalUtil.isNotBlank($('#hsearch').val())){
				window.location = '${ctx}/MerchantView/MerchantFound?hsearch='+encodeURI($.trim($('#hsearch').val()));
			}else{
				window.location = '${ctx}/MerchantView/MerchantFound';
			}
		});
	});
</script>

<header id="header">
	<div class="container">
		<hgroup class="logo">
			<h1>
				<a href="${ctx }/index" class="brand" title="花果网-发现美食" rel="home"
					id="logo-area">花果网-发现美食</a>
			</h1>
			<div class="logo-side">
				<a class="logo-side-current" href="javascript:void(0);" id="regional" hidefocus=""><span id="header_city">无锡</span>站
					<span class="ddarrow"><span class="arrow arrow-down"></span></span>
				</a>
				<div class="dropdown-menu clearfix">
					<table>
						<tbody>
							<tr>
								<td>切换城市：</td>
								<td><a href="javascript:changeCity('北京')">北京</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:changeCity('无锡')">无锡</a></td>
							</tr>
						</tbody>
					</table>
					<!-- <table>
						<tbody>
							<tr>
								<td>华北东北：</td>
								<td><a href="javascript:changeCity('北京')">北京</a> <a href="javascript:void(0);">沈阳</a> <a href="javascript:void(0);">大连</a>
									<a href="javascript:void(0);">石家庄</a> <a href="javascript:void(0);">长春</a> <a href="javascript:void(0);">吉林</a></td>
							</tr>
							<tr>
								<td>华东地区：</td>
								<td><a href="javascript:void(0);">上海</a> <a href="javascript:void(0);">苏州</a> <a href="javascript:changeCity('无锡')">无锡</a>
									<a href="javascript:void(0);">杭州</a> <a href="javascript:void(0);">青岛</a> <a href="javascript:void(0);">淄博</a></td>
							</tr>
							<tr>
								<td>华南地区：</td>
								<td><a href="javascript:void(0);">广州</a> <a href="javascript:void(0);">深圳</a> <a href="javascript:void(0);">东莞</a>
									<a href="javascript:void(0);">中山</a> <a href="javascript:void(0);">南宁</a> <a href="javascript:void(0);">佛山</a></td>
							</tr>
							<tr>
								<td>中部西部：</td>
								<td><a href="javascript:void(0);">郑州</a> <a href="javascript:void(0);">西安</a> <a href="javascript:void(0);">成都</a>
									<a href="javascript:void(0);">武汉</a> <a href="javascript:void(0);">长沙</a> <a href="javascript:void(0);">重庆</a></td>
							</tr>
						</tbody>
					</table>
					<a class="more-city" rel="nofollow" href="javascript:void(0);">更多城市</a> -->
				</div>

			</div>
		</hgroup>

		<hgroup class="search">
			<form method="get" id="searchform" class="clearfix" action="">
				<label class="hidden" for="hsearch">Search for:</label> <input type="text"
					value="" name="hsearch" id="hsearch" class="text-field" placeholder="搜索餐厅" placeholder1="搜索餐厅">
				<input type="button" id="searchsubmit" value="Search">
			</form>
		</hgroup>

		<%
			if (objUName != null) {
		%>
			<nav id="top-nav">
				<ul id="nav" class="nav">
					<li id="usernemeaa" class="ShortcutInfo">
						<%if(regType=="0"){%>
							<a class="usernemeaa" title="" href="${ctx }/myAccount"> 
								<%
									if (profileImageurl != null && !"".equals(profileImageurl)) {
								%>
									<img  id="userHeadImage" class="touxiang" src="${ctx}/<%=profileImageurl %>" alt="用户头像" height="28" width="28" />  
								<%
	 								} else {
	 							%>
									<img id="userHeadImage" class="touxiang" src="${ctx}/static/images/defaultHeadMi.png" height="28" width="28"  alt="用户头像" /> 
								<%
									}
								%>
								<span id="nickNameSpan"> <%
								 		int i = objUName.length();
							 		if (i > 6) {
							 			objUName = objUName.substring(0, 6); //字符串截取0-7共8位 
							 			out.print(objUName + "...");
							 		} else {
							 			out.print(objUName);
							 		}
							 	%>
							 	</span>
							</a>
							
						<%} else { %>
							<a class="usernemeaa" title="" style="cursor:text">
								<%
									if (profileImageurl != null && !"".equals(profileImageurl)) {
								%>
									<img  id="userHeadImage" class="touxiang" src="${ctx}/ <%=profileImageurl %>" alt="用户头像" height="28" width="28" />  
								<%
	 								} else {
	 							%>
									<img id="userHeadImage" class="touxiang" src="${ctx}/static/images/defaultHeadMi.png" height="28" width="28"  alt="用户头像" /> 
								<%
									}
								%>
								<span id="nickNameSpan"> <%
								 		int i = objUName.length();
							 		if (i > 6) {
							 			objUName = objUName.substring(0, 6); //字符串截取0-7共8位 
							 			out.print(objUName + "...");
							 		} else {
							 			out.print(objUName);
							 		}
							 	%>
							 	</span>
							</a>
						<%}%> 
							
						<div class="ShortcutBtn">
							<ul>
								<li><a href="${ctx }/merchant/myMerchant?type=f">我的喜欢</a></li>
								<li><a href="${ctx }/merchant/myMerchant?type=m">我的餐厅</a></li>
								
								<%if(regType=="0"){%> <!-- 站内用户 -->
									<li><a href="${ctx }/myAccount">我的账号</a></li>
								<%} else { %> <!-- 站外用户 -->
										
								<%}%>
								
								<li>
									<a href="${ctx }/notifications">我的通知</a>
								</li>
								<li>
									<a id="logoutBtn" href="javascript:void(0);" >退出</a>
								</li>
							</ul>
						</div>
					</li>
					
					<li>
						<a class="Usually" href="${ctx}/messages">私信
							<span class="number" id="myMessageheadCount">(<%=messageCount%>)</span>
						</a>
					</li>
				</ul>
			</nav>
		<%
			} else {
		%>
			<nav id="top-nav">
				<ul id="nav" class="nav">
					<li class="active">
						<a title="注册账号" href="javascript:void(0)" onclick="signupOrLogin('signup')">注册</a>
					</li>
					<li class="">
						<a title="登录" href="javascript:void(0)" onclick="signupOrLogin('login')">登录</a></li>
					<li>
						<a title="微博账号登录" id="SinaWeiboLogin" href="javascript:void(0);"> 
							<img src="${ctx}/static/images/WeiboLogoLogin.png" alt="微博账号登录" />
						</a> 
					</li>
					<li>
						<a title="QQ微博登录" id="QQWeiboLogin" href="javascript:void(0);"> 
							<img src="${ctx}/static/images/weiboicon24.png" alt="QQ微博登录" />
						</a> 
					</li>
				</ul>
			</nav>
		<%
			}
		%>
	</div>
</header>

<script type="text/javascript">
	$("#logoutBtn").click(function(){
		$.post("${ctx }/logout",null,function(data){
			if(data.result=="true"){
				if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login')!= -1) {
					changepage('${ctx }/index');
				}else{
					window.location.href = location.href;
				}
			}else if(data.result=="false"){
				
			}
		}); 
	});
</script>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=4252929548" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$("#SinaWeiboLogin").click(function(){
		WB2.login(function(){ 
			/***授权成功后回调***/
			getWbUserData(function(user){ 
				
				/***o是/users/show.json接口返回的json对象***/
				$.post("${ctx}/WeiboAuth?screenName="+encodeURIComponent(user.screen_name)
            			+"&profileImageUrl="+user.profile_image_url+"&gender="
            			+user.gender+"&homepage="+user.url+"&regType=1"
            			,null,function(data){
            		
        			if(data.result=="true"){
        				if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login')!= -1) {
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

<script src="//mat1.gtimg.com/app/openjs/openjs.js#debug=no"></script>
<script defer="defer">
    T.init({
        appkey:801236585,
        autoclose : true
        /* , callbackurl: ['http://open.t.qq.com/open-js/doc/snippet/code/callback.html?' // 通用方案html文件网址
                      ,'return_to=',encodeURIComponent('http://www.huaguo.com') // 你的应用网址
                      ,'&appkey=801236585' // 你的AppKey
                      ].join(''),

        samewindow:false */
    });
	 
    $("#QQWeiboLogin").click(function(){
    	T.login(function (loginStatus) {
    		T.api("/user/info")
            .success(function (response){
                //birth_day birth_month birth_year  city_code  comp email sex homepage sex 1 MAN 2 FEMAN 
                
                // 腾讯微博没头像时，取出来的是空串--史中营
                if(generalUtil.isBlank(response.data.head)) {
                	response.data.head='http://mat1.gtimg.com/www/mb/img/p1/head_normal_30.png';
                }
                //----------------------------------------------
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
            	alert("授权失败或授权");
             });
    	},function (loginError) {
    		//alert(loginError.message);
    	});
    });
</script>