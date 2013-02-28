<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.touco.huaguo.common.Constants"%>
<%@page import="com.touco.huaguo.domain.UserEntity"%>

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
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/share.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<script type="text/javascript" src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
<script type="text/javascript">

	$(document).ready(function()
	{
		$.post("${ctx}/user/getUserInfo",null,function(data)
		{			
			if(data.error)
			{
				changepage('${ctx}/login');
			}
			else
			{
				$('#userForm').form('load',data);
				$('#useremail').val(data.email);
				//加载省份信息 
				getProvince(data.provinceList,data.provinceId);
				getCity(data.cityList,data.cityId);
				//设置头像
				if(data.imageUrl!=null&&data.imageUrl!="")
				{
					$("#img_preview").attr('src',ctx+data.imageUrl);
				}
				else
				{
					$("#img_preview").attr('src',ctx+"/static/images/WaterHead.png");
				}
			}
		});		
	});

	function getProvince(provinceList,provinceId)
	{
		$("#provinceId").empty();
		$("#provinceId").append("<option value=''>请选择</option>");
		for(i=0;i<provinceList.length;i++)
		{
			if(provinceList[i].cityId==provinceId)
			{
				$("#provinceId").append("<option value='"+provinceList[i].cityId+"' selected>"+provinceList[i].cityName+"</option>"); 
			}
			else
			{
				$("#provinceId").append("<option value='"+provinceList[i].cityId+"'>"+provinceList[i].cityName+"</option>"); 
			}
		}
	}
	
	function getCity(cityList,cityId)
	{
		if(null!=cityList)
		{
			$("#cityId").empty();
			$("#cityId").append("<option value=''>请选择</option>");
			for(i=0;i<cityList.length;i++)
			{
				if(cityList[i].cityId==cityId)
				{
					$("#cityId").append("<option value='"+cityList[i].cityId+"' selected>"+cityList[i].cityName+"</option>"); 
				}
				else
				{
					$("#cityId").append("<option value='"+cityList[i].cityId+"'>"+cityList[i].cityName+"</option>"); 
				}
			}
		}		
	}
	
	
	function changeProvince()
	{
		var provinceId=$("#provinceId").val();
		$.post("${ctx}/user/getCityList?provinceId="+provinceId,null,function(data)
		{								
				getCity(data.cityList,null);
		});		
	}
	
	function saveUserInfo()
	{
		 if($("#nickName").val()=="")
    	 {
    		 $("#nickNameMsg").html("请填写昵称");
    		 return;
    	 }
		 var nickNameLength = namelen($("#nickName").val());
	     if(nickNameLength>32)
	     {
	    	 $("#nickNameMsg").html("只能输入16个汉字或32个字符");
	    	 return;
	     }
    	 var fields = $("#userForm").serializeArray();	
 			$.post("${ctx}/user/updateUserInfo",fields,function(data){			
 			if(data.error!=null)
 			{
 				if(data.error=="1")
 				{
 					$('#popup_message').popup({'html':data.msg});
 					changepage('${ctx}/login');
 				}
 				if(data.error=="2")
 				{
 					$("#nickNameMsg").html(data.msg);
 				}
 			}
 			else
 			{
 				$('#popup_message').popup({'html':data.msg});
 				var tempName=$("#nickName").val();
 				if (tempName.length > 6) {
 					tempName = tempName.substring(0, 6)+"..."; //字符串截取0-7共8位 
		 		} else {
		 		}
 				$("#nickNameSpan").html(tempName);
 			}
 		});
	}
	
	
	 function uploadUserImg(imgObj)
	 {		 
			 $('#userForm').form('submit',{   
			        url: '${ctx}/user/uploadUserImg',
			        onSubmit: function(){		        	
			        	if(checkImgType(imgObj.value)){
			        		return true;
			        	}
			        	else{
			        		$('#popup_message').popup({'html':"您上传的好像不是图片文件！"});
			        		imgObj.outerHTML += "";
			    			imgObj.value="";
			        		return false;
			        	}		            
			        },
			        success: function(data){
			        	$("#imageUrl").val(data);
			        	$("#img_preview").attr('src','${ctx}/'+data);
			        	$("#userHeadImage").attr('src','${ctx}/'+data);
			        }   
		    }); 	
			
		}
	    
	    
	    function checkImgType(imgValue){
	    	if(imgValue.match(/^.*(\.jpg|\.JPG|\.gif|\.GIF|\.bmp|\.BMP|\.png|\.PNG|\.JPEG|\.jpeg)$/i)){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    function delUserImgage()
	    {
	    	$("#img_preview").attr('src',ctx+"/static/images/WaterHead.png");
	    	$("#imageUrl").val("");
	    	$("#userHeadImage").attr('src',ctx+"/static/images/WaterHead.png");
	    	 $.post("${ctx}/user/delUserImage",null,function(data){			
		 			if(data.error)
		 			{
		 				$('#popup_message').popup({'html':data.msg});
		 			}
		 		});
	    }
	    
	    function saveEmail()
	    {
	    	var email = $("#useremail").val();
	    	var reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/g;
	    	if($("#useremail").val()=="")
	    	 {
	    		 $("#useremailMsg").html("请填写邮箱");
	    		 return;
	    	 }
	    	 if($("#useremail").val().length>50)
	    	 {
	    		 $("#useremailMsg").html("邮箱须小于50个字符");
	    		 return;
	    	 }
	    	// 邮箱格式
			 if (!reg.test(email)) {
			    $("#useremailMsg").html("邮箱格式不正确");
			        return;
			 }
	    	
			 $.post("${ctx}/user/updateUserEmail?email="+email,null,function(data){			
		 			if(data.error!=null)
		 			{
		 				if(data.error=="1")
		 				{
		 					$('#popup_message').popup({'html':data.msg});
		 					changepage('${ctx}/login');
		 				}
		 				if(data.error=="2")
		 				{
		 					$("#useremailMsg").html(data.msg);
		 				}
		 			}
		 			else
		 			{
		 				$('#popup_message').popup({'html':data.msg});
		 				 $("#useremailMsg").html("");
		 			}
		 		});
	    	
	    }
	    
	    function resetPwd()
	    {
	    	var oldpassword = $("#oldpassword").val();
	    	var newpassword = $("#newpassword").val();
	    	var newpassword2 = $("#newpassword2").val();
	    	if(oldpassword=="")
	    	{
	    		$("#oldpasswordMsg").html("请填写旧密码");
	    		return;
	    	}
	    	if(newpassword=="")
	    	{
	    		$("#newpasswordMsg").html("请填写新密码");
	    		return;
	    	}
	    	if(newpassword2=="")
	    	{
	    		$("#newpassword2Msg").html("请填写确认新密码");
	    		return;
	    	}
	    	if(newpassword!=newpassword2)
	    	{
	    		$("#newpassword2Msg").html("新密码与确认密码不一致");
	    		return;
	    	}
	    	if(!checkUserPassword())
	    	{
	    		return;
	    	}else
	    	{
	    		 jQuery("#newpasswordMsg").html("");
	    	}
	    	$.post("${ctx}/user/resetpassword?oldpassword="+oldpassword+"&newpassword="+newpassword,null,function(data){			
		 			if(data.error!=null)
		 			{
		 				if(data.error=="1")
		 				{
		 					$('#popup_message').popup({'html':data.msg});
		 					changepage('${ctx}/login');
		 				}
		 				if(data.error=="2")
		 				{
		 					$("#oldpasswordMsg").html(data.msg);
		 				}
		 			}
		 			else
		 			{
		 				$("#oldpassword").val("");
		 		    	$("#newpassword").val("");
		 		    	$("#newpassword2").val("");
		 		    	$('.camouflage').fadeToggle(100);
		 		    	$('#popup_message').popup({'html':data.msg});
		 			}
		 		});
	    }
	    
	 // 密码验证
		function checkUserPassword() {
		    var userPassword = jQuery("#newpassword").val();
		    // 密码长度只能在6-16位字符
		    var strlength = userPassword.length;
		    if (strlength < 6 || strlength > 16) {
		        jQuery("#newpasswordMsg").html("6-16位字母、数字混合组合");
		        return false;
		    }
		    return true;
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
</script>


 </head>

 <body>
 <div id="share">
  
  	<%@ include file="/static/page/header.jsp"%>
		<!--  导航  -->
	<%@ include file="/static/page/navigation.jsp"%>
   <div id="slogan" class="Share">
     <div class="container">
       <div class="switching clearfix">
         <header class="slogan-title">
           <h1 class="slogan">个人资料与设置</h1>
         </header>
         <div class="Share_menu">
           <ul>
             <li class="selected">个人资料</li>
             <li>账户设置</li>
             <li>关联账户</li>
           </ul>
         </div>
       </div>
     </div>
   </div>
   <div id="content-wrapper">
     <div class="container">
       <div class="Formlist">
         <form method="post" id="userForm" enctype="multipart/form-data">
           <section id="content4" class="clearfix">
             <div class="Registerform DiningRoom support">
               <header class="introduce-title">
                 <h3 class="introduce">个人信息修改</h3>
               </header>
               <div class="interlaced">
                 <label for="nickname">昵称:<span class="small"></span></label>
                 <input class="neutral" type="text" name="nickName" id="nickName" />
                 <span class="lowrong" id="nickNameMsg"></span> </div>
               <div class="iisi"></div>
               <div class="interlaced">
                 <label for="">所在城市:<span class="small"></span></label>
                 <div class="scapegoat" style="border:none">
                 <select style="width:70px" id="provinceId" name="province.cityId" onchange="changeProvince();">		
				</select>							
				<select id="cityId" name="city.cityId" style="width:140px"></select>
                 </div>
                 <span class="lowrong"></span> </div>
               <div class="iisi"></div>
               <div class="interlaced">
                 <label for="RealName">真实姓名:<span class="small"></span></label>
                 <input class="neutral" type="text" name="trueName" id="trueName" />
               </div>
               <div class="iisi"></div>
               <div class="interlaced">
                 <label for="gender">性别:<span class="small"></span></label>
                 <input type="radio" name="gender" value="m"/>
                 男
                 <input type="radio" name="gender" value="f"/>
                 女</div>
               <div class="iisi"></div>
               <div class="interlaced">
                 <label for="homepage">个人主页:<span class="small"></span></label>
                 <input class="neutral" type="text" name="webPage" id="webPage" />
               </div>
               <div class="iisi"></div>
               <div class="interlaced determine">
                 <button class="btnOrng save" type="button" onclick="saveUserInfo()">保存</button>
               </div>
             </div>
           </section>
           <aside id="registerRight" class="register-right" role="">
             <div class="IssuedNotice">
               <section id="" class="profile clearfix">
                 <header class="widget-title">
                   <h2 class="Logins"> 设置个人头像 </h2>
                 </header>
                 <div class="interlaced">
                   <label for="head">上传图片:</label>
                   <input id="uploadImg" type="file" name="uploadImg" onchange="uploadUserImg(this);"/>
                 </div>
                 <div class="upload"> <a class="personal" href="javascript:void(0);" title="默认头像显示">
                 <img id="img_preview" src="${ctx}/static/images/WaterHead.png" alt="" /></a> 
                 <input type="hidden" id="imageUrl" name="imageUrl" value=""/>
                 </div>
                 <div class="interlaced"> 
                 	<a class="immediately green" title="删除" href="javascript:void(0);" onclick="delUserImgage();">删除</a> 
                 </div>
               </section>
             </div>
           </aside>
         </form>
       </div>
       <div class="Formlist hidden">
         <section id="content2" class="">
           <div class="Registerform DiningRoom support">
             <header class="introduce-title">
               <h3 class="introduce">注册邮箱修改</h3>
             </header>
             <form action="" id="form2">
               <div class="interlaced">
                 <label for="RegisterMail">注册邮箱:</label>
                 <input class="neutral" type="text" value="" name="useremail" id="useremail" />
                 <span class="lowrong">你可以使用这个邮箱登录花果，或者重置密码</span> </div>
               <div class="iisi" id="useremailMsg"></div>
               <div class="interlaced">
                 <button class="btnOrng save" type="button" onclick="saveEmail()">保存</button>
               </div>
             </form>
             <header class="introduce-title">
               <h3 class="introduce">注册密码修改</h3>
             </header>
             <div class="interlaced">
               <label for="">我的密码:</label>
               <a  class="btnOrng" id="modification" href="javascript:void(0);" title="修改登录密码">修改登录密码</a> </div>
             <div class="iisi"></div>
             <form action="" id="form8">
               <div class="camouflage">
                 <div class="interlaced">
                   <label for="OldPass" class="pass">旧密码:</label>
                   <input class="neutral" type="password"  name="oldpassword" id="oldpassword" />
                 </div>
                 <div class="iisi" id="oldpasswordMsg"></div>
                 <div class="interlaced">
                   <label for="newPass" class="pass">新密码:</label>
                   <input class="neutral" type="password"  name="newpassword" id="newpassword" />
                   <span id="" class="lowrong">密码至少为6位，建议使用字母、数字混合组合</span> </div>
                 <div class="iisi" id="newpasswordMsg"></div>
                 <div class="interlaced">
                   <label for="confirm" class="pass">确认新密码:</label>
                   <input class="neutral" type="password"  name="newpassword2" id="newpassword2" />
                 </div>
                 <div class="iisi" id="newpassword2Msg"></div>
                 <div class="interlaced">
                   <button class="btnOrng save" type="button" onclick="resetPwd()">保存</button>
                 </div>
               </div>
             </form>
           </div>
         </section>
       </div>
       <div class="Formlist hidden">
         <section id="content3" class="clearfix">
           <div class="Registerform DiningRoom support">
             <header class="introduce-title">
               <h3 class="introduce">关联微博账号</h3>
             </header>
             <div class="iisi"></div>
             <div class="interlaced" id="_sinaWeibo">
               <!-- <label>新浪微博帐号:</label>
               <a class="btnOrng" title="微博账号登录" id="BindingSinaWeibo" href="javascript:void(0);">绑定</a> -->
             </div>
             <div class="iisi"></div>
             
             <div class="interlaced"  id="_tencentWeibo">
               <!-- <label for="weibo">腾讯微博帐号:</label>
               <input class="neutral" value="yonghu0101" type="text"  name="weibo" id="weibo" />
               <button class="btnOrng" type="submit">取消绑定</button> -->
             </div>
             <div class="iisi clear"></div>
           </div>
         </section>
       </div>
     </div>
   </div>
 </div>
   <%@ include file="/static/page/footer.jsp"%>
   <div id="popup_message"> </div>
   
<script type="text/javascript">  
   $(document).ready(function() {
	   <!-- 解决微博账号登入也可看见我的账号页面  -->
 	   <%if(regType=="0"){%>
	   
	   <%} else { %> <!-- 站外用户 -->
	   		changepage('${ctx }/index');
	   <%}%>
	   
	   $.post("${ctx}/initBuildWeiboAuth",null,function(ret){
		    if(ret.list!= null && ret.list.length != 0){
		    	var sinaTag=false;
		    	var tencentTag = false;
		    	for(var i=0; i<ret.list.length; i++){
		    		var apptype = ret.list[i].apptype ;
		    		if(apptype==1 ){//新浪
		    			$('#_sinaWeibo').empty();
						var _sinaWeiboDetail="<label for='weibo'>新浪微博:</label>"
							+"<input class='neutral' value="+ret.list[i].nickname+" type='text' disabled='disabled' name='sinaNickName' id='sinaNickName' />"
			                +"<a  class='btnOrng' id='cancelBuildingSina'  href='javascript:cancelBuilding("+ret.list[i].refId+",1);'>取消绑定</a>";
						$('#_sinaWeibo').append(_sinaWeiboDetail);
						sinaTag=true;
		    		}
		    		if(apptype==2){
		    			$('#_tencentWeibo').empty();
		    			var _tencentWeiboDetail="<label for='weibo'>腾讯微博:</label>"
							+"<input class='neutral' value="+ret.list[i].nickname+" type='text' disabled='disabled'  name='tcNickName' id='tcNickName' />"
			                +"<a  class='btnOrng' id='cancelBuildingTC' href='javascript:cancelBuilding("+ret.list[i].refId+",2);'>取消绑定</a>";
		    			$('#_tencentWeibo').append(_tencentWeiboDetail);
		    			tencentTag= true;
		    		}	
		    		
		    		if(!sinaTag){
		    			$('#_sinaWeibo').empty();
						var _sinaWeiboDetail="<label>新浪微博:</label>"
							+"<a class='btnOrng' title='新浪微博账号登录' id='BindingSinaWeibo' href='javascript:bindingSinaWeibo();'>绑定</a>";
						$('#_sinaWeibo').append(_sinaWeiboDetail);
		    		}
		    		if(!tencentTag){
		    			$('#_tencentWeibo').empty();
						var _tencentWeiboDetail="<label>腾讯微博:</label>"
							+"<a class='btnOrng' title='腾讯微博账号登录' id='bindingTencentWeibo' href='javascript:bindingTencentWeibo();'>绑定</a>";
						$('#_tencentWeibo').append(_tencentWeiboDetail);
		    		}
		    	}
			}else {
				$('#_sinaWeibo').empty();
				var _sinaWeiboDetail="<label>新浪微博:</label>"
					+"<a class='btnOrng' title='新浪微博账号登录' id='BindingSinaWeibo' href='javascript:bindingSinaWeibo();'>绑定</a>";
				$('#_sinaWeibo').append(_sinaWeiboDetail);
				
				$('#_tencentWeibo').empty();
				var _tencentWeiboDetail="<label>腾讯微博:</label>"
					+"<a class='btnOrng' title='腾讯微博账号登录' id='bindingTencentWeibo' href='javascript:bindingTencentWeibo();'>绑定</a>";
				$('#_tencentWeibo').append(_tencentWeiboDetail);
			}
	   });  
	   
   });

   
   function bindingSinaWeibo(){
		WB2.login(function(){ 
			/***授权成功后回调***/
			getWbUserData(function(user){ 
				/***o是/users/show.json接口返回的json对象***/
				$.post("${ctx}/buildWeiboAuth?screenName="+encodeURIComponent(user.screen_name)
            			+"&regType=1",null,function(data){
            		
        			if(data.result=="true"){
        				$('#_sinaWeibo').empty();
        				
        				var liDetail="<label for='weibo'>新浪微博:</label>"
        					+"<input class='neutral' value="+user.screen_name+" type='text' disabled='disabled' name='nickName' id='sianWeibo' />"
        	                +"<a  class='btnOrng' id='cancelBuildingSina'  href='javascript:cancelBuilding("+data.refId+","+data.regType+");'>取消绑定</a>";
						
        	            $('#_sinaWeibo').append(liDetail);
						
        			}else if(data.result=="false"){
        				WB2.logout(function(){
        				});
        				$('#popup_message').popup({'html':data.msg});
        			}else if(data.result="relogin"){
        				if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login') != -1) {
        					changepage('${ctx }/index');
        				}else{
       						window.location.href = location.href;
        				}
        			}
        		}); 
				
			}); 
		}); 
	}
	
	function getWbUserData(callback) {
		WB2.anyWhere(function(W) {
			/***获取授权用户id***/
			W.parseCMD("/account/get_uid.json", function(sResult, bStatus) {
				if (!!bStatus) {
					/**请求uid成功后调用以获取用户数据**/
					getData(W, sResult);
				} else {
					/*** 这里只是简单处理出错***/
					$('#popup_message').popup({'html':'授权失败或错误'});
					//alert("");
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
	
    function bindingTencentWeibo(){
    	T.login(function (loginStatus) {
    		T.api("/user/info")
            .success(function (response){
            	$.post("${ctx}/buildWeiboAuth?screenName="+encodeURIComponent(response.data.nick)+"&regType=2"
    				,null,function(data){
						if(data.result=="true"){
							$('#_tencentWeibo').empty();
	        				var liDetail="<label for='weibo'>腾讯微博:</label>"
	        					+"<input class='neutral' value="+response.data.nick+" type='text' disabled='disabled' name='nickName' id='tencentSianWeibo' />"
	        	                +"<a  class='btnOrng' id='cancelBuildingTC' href='javascript:cancelBuilding("+data.refId+","+data.regType+");'>取消绑定</a>";
	        	            
	        	            $('#_tencentWeibo').append(liDetail);
	        	            
						}else if(data.result=="false"){
							//alert(data.msg);
							$('#popup_message').popup({'html':data.msg});
							T.logout(function (loginStatus){});
						}else if(data.result="relogin"){
							if (location.pathname.indexOf('/signup') != -1 || location.pathname.indexOf('/login') != -1) {
	        					changepage('${ctx }/index');
	        				}else{
	       						window.location.href = location.href;
	        				}
						}
				}); 
             })
            .error(function (code, message) {
                // alert(message);
                 $('#popup_message').popup({'html':message});
             });
    	},function (loginError) {
    		//alert(loginError.message);
    	});
    }
    
    /**取消微博绑定 **/
    function cancelBuilding(refId,regType){
    	$.post("${ctx}/cancelBuildingWeiboAuth?refId="+refId+"&regType="+regType ,null,
    		function(data){
				if(regType==1){
    				if(data.result=="true"){
						$('#_sinaWeibo').empty();
						var _sinaWeiboDetail="<label>新浪微博:</label>"
							+"<a class='btnOrng' title='新浪微博账号登录' id='bindingSinaWeibo' href='javascript:bindingSinaWeibo();'>绑定</a>";
						$('#_sinaWeibo').append(_sinaWeiboDetail);
					}else if(data.result=="false"){
						//alert(data.msg);
						$('#popup_message').popup({'html':data.msg});
					}
				}else if(regType==2){
					if(data.result=="true"){
						$('#_tencentWeibo').empty();
						var _tencentWeiboDetail="<label>腾讯微博:</label>"
							+"<a class='btnOrng' title='腾讯微博账号登录' id='bindingTencentWeibo' href='javascript:bindingTencentWeibo();'>绑定</a>";
						$('#_tencentWeibo').append(_tencentWeiboDetail);
					}else if(data.result=="false"){
						$('#popup_message').popup({'html':data.msg});
					}
				}
		}); 
    }
    
</script>
</body>

</html>
