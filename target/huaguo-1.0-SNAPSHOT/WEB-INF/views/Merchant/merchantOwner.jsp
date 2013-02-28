<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String merchantId = request.getParameter("merchantId");
	String sourceType = request.getParameter("sourceType");
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
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/share.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<script type="text/javascript" src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jquery.fixed.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/specialScrollEvents.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jqueryReturnTo-Bottom.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/kindeditor-min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/area.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
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

 .unchecked{  
   border: 1px solid gray;  
   padding: 5px;  
 }  
 .checked{  
   border: 2px solid orange;  
   padding: 5px;  
 }  
</style>

<script type="text/javascript">
	jQuery.ajaxSetup({
		cache : false
	});//ajax不缓存
	
	
	
		    
		    function checkImgType(imgValue){
		    	if(imgValue.match(/^.*(\.jpg|\.JPG|\.gif|\.GIF|\.png|\.PNG|\.JPEG|\.jpeg)$/i)){
		    		return true;
		    	}
		    	return false;
		    }
		    
		    
		    
		    function uploadMerchantOwnerImg(imgObj)
			 {		 
					 $('#ownerForm').form('submit',{   
					        url: '${ctx}/merchant/uploadMerchantOwnerImg',
					        onSubmit: function(){		        	
					        	if(checkImgType(imgObj.value)){
					        		return true;
					        	}
					        	else{
					        		$('#popup_message').popup({'html': '您上传的好像不是图片文件！'});
					        		imgObj.outerHTML += "";
					    			imgObj.value="";
					    			$("#imgDiv").hide();
					        		return false;
					        	}		            
					        },
					        success: function(data){
					        	$("#picUrl").val(data);
					        	$("#picDiv").show();
					        	$("#pic_preview").attr('src','${ctx}/'+data);
					        }   
				    }); 	
					
				}
		    
		    
		    function saveMerchantOwner()	    
		    {  
		    	
		    	$("#merchantTelMsg").html("");
		    	$("#linkManMsg").html("");
		    	$("#picMsg").html("");
		    	if($("#merchantId").val()=="")
		    	{
		    		$('#popup_message').popup({'html': '请先填写餐厅信息后再申请掌柜'});
		    		return;
		    	}
		    	 if($("#linkMan").val()=="")
		    	 {
		    		 $("#linkManMsg").html("请填写联系人");
		    		 return;
		    	 }
		    	 if($("#linkMan").val().length>19)
		    	 {
		    		 $("#linkManMsg").html("联系人须小于25个字符");
		    		 return;
		    	 }
		    	 var isMan = checkNameVal($("#linkMan").val());
		    	 if(isMan!="")
		    	 {
		    		 $("#linkManMsg").html("联系人只支持汉字、数字、字母和下划线");
		    		 return;
		    	 }
		    	 if($("#merchantTel").val()=="")
		    	 {
		    		 $("#merchantTelMsg").html("请填写掌柜电话");
		    		 return;
		    	 }
		    	 if(!checkTel($("#merchantTel").val()))
		    	 {
		    		 $("#merchantTelMsg").html("请正确填写掌柜电话");
		    		 return;
		    	 }
		    	 if($("#picUrl").val()=="")
		    	 {
		    		 $("#picMsg").html("请上传营业执照");
		    		 return;
		    	 }
		    	 var fields = $("#ownerForm").serializeArray();	
		 			$.post("${ctx}/merchant/saveMerchantOwner?isOpen=1",fields,function(data){			
		 				$('#popup_message').popup({'html': data.msg});
		 			//history.go(-1);
		 			var sourceType='<%=sourceType%>';
		 			var tempMerchantId='<%=merchantId%>';
		 			changepage('${ctx }/merchant/success?returnType=2&sourceType='+sourceType+'&merchantId='+tempMerchantId);
		 		});
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
           <h1 class="slogan">我要成为掌柜</h1>
         </header>

       </div>
     </div>
   </div>
   <div id="content-wrapper">
     <div class="container">
      
         <form method="post" id="ownerForm" enctype="multipart/form-data">
           <section id="content2" class="">
             <div class="Registerform DiningRoom support">
               <header class="introduce-title">
                 <h1 class="introduce">请在这边填写掌柜申请的信息，提交餐厅营业执照信息</h1>
                 <h2 class="supplemental">提示：成为餐厅掌柜后，上线后餐厅所有信息由掌柜单独打理。若不申请成为掌柜，餐厅信息由花果用户共同维护。</h2>
               </header>
               <div class="interlaced">
                 <label for="">联系人:<span class="small"></span></label>
                 <div class="scapegoat" style="border:none"><input type="text" name="linkMan" id="linkMan" style="width:200px"></input></div>
                 <span id="linkManMsg" class="lowrong"></span></div>
               <div class="iisi"></div>
               <div class="interlaced">
                 <label for="">联系电话:<span class="small"></span></label>
                 <div class="scapegoat" style="border:none"><input type="text" name="merchantTel" id="merchantTel" style="width:200px"></input></div>
                 <span id="merchantTelMsg" class="lowrong"></span></div>
               <div class="iisi"></div>
               <div class="interlaced">
                 <label class="pass">营业执照:<span class="small"></span></label>
                 <div class="scapegoat" style="border:none">
                 	<input style="width:200px" type="file" id="uploadPicImg" name="uploadPicImg" onchange="uploadMerchantOwnerImg(this);"/>
					    <input type="hidden" id="picUrl" name="picUrl" value=""/>
                 		<input type="hidden" id="merchantId" name="merchantId" value="<%=merchantId%>"/>
                 </div>
                 <span id="picMsg" class="lowrong">支持jpg、jpeg、png、gif格式。不超过5MB</span> </div>
                 <div class="proxy" id="picDiv" style="border:none;display: none;width:320px;height:183px;">
								<img id="pic_preview" name="pic_preview" style="width: 320px; height: 183px;" />
				</div>
               <div class="iisi"></div>
              
               <div class="interlaced determine">
                 <button class="btnOrng" type="button" onclick="saveMerchantOwner()">提交申请</button>
                 
                 <button class="btnOrng" type="button" onclick="history.go(-1);">返回</button>
               </div>
             </div>
           </section>
         </form>
       
     </div>
   </div>
   <%@ include file="/static/page/footer.jsp"%>
   <div id="popup_message"> </div>
</body>
</html>