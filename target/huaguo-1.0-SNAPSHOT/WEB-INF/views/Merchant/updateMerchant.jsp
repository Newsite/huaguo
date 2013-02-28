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

<script type="text/javascript" src="${ctx}/static/scripts/common/area.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/kindeditor-min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/editor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.form.js"></script>
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
   border: 2px solid #d7d7d7;  
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
	
	
	var editor;
	KindEditor.ready(function(K) 
	{
		var item =['source', '|', 'undo', 'redo', '|', 'preview', 'print', 'code', 'cut', 'copy', 'paste',
		           'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		           'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		           'superscript', 'clearhtml', 'quickformat', '/',
		           'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		           'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
		           'table', 'hr', 'emoticons', , 'pagebreak',
		           'anchor', 'link', 'unlink', '|', 'about']
		editor = K.create('#editor_id', {
	        width : '600px',
	        height:'416px',
	        items :item,
	        resizeType:0,
	        uploadJson:'${ctx}/merchant/uploadEditorImg'  
		});
		
	});
	
	
	$(document).ready(function() {
		var merchantId='<%=merchantId%>';
		$.post("${ctx}/merchant/getMerchant?merchantId="+merchantId,null,function(data)
		{
			if(null!=data.error)
			{
				$('#popup_message').popup({'html': data.msg});
				return;
			}
			$('#merchantForm').form('load',data);
			$("#nameShow").html(data.name);
			if(data.imageUrl!="" && data.imageUrl!=null){
				//$("#imgDiv").show();
	 			$("#img_preview").attr('src',ctx+data.imageUrl);
			}
 			
 			initMerchantStyle(data.merchantStyle);
 			initArea(data.cityName,data.area);

 			$("#priceId").val(data.priceId);
 			editor.html(data.content);
 			if(null!=data.ownerId&&data.ownerId!="")
 			{
 				$("#ownerDiv").hide();
 			}
 			previewMerchantSmall();
 		});
	});
	
	
	function initMerchantStyle(styleName)
	{
		$('span[name="colorSpan"]').each(function(){  
            if(this.innerHTML==styleName)  
            {  
                this.className="checked";  
                this.checked=true;  
            }                 
  		});  
	}
	
	
	var obj={  
		    colorSpan:"",  
		      sizeSpan:""  
		};  
		function change(span)  
		{  
			   $('span[name="'+$(span).attr('name')+'"]').each(function(){  
			             if(this.checked&&this!=span)  
			             {  
			                 this.className="unchecked";  
			                 this.checked=false;  
			             }                 
			   });  
			   obj[$(span).attr('name')]=span.innerHTML;  
			   span.className="checked";  
			   span.checked=true;
			   $('#merchantStyle').val(span.innerHTML);
			   
		 }  
	

		function initArea(city,area)
		{
		
			if(city=="无锡")
			{
				for(i=0;i<wuxiarea.length;i++)
				{
					if(wuxiarea[i]==area)
					{
						$("#area").append("<option value='"+wuxiarea[i]+"' selected>"+wuxiarea[i]+"</option>"); 
					}
					else
					{
						$("#area").append("<option value='"+wuxiarea[i]+"'>"+wuxiarea[i]+"</option>"); 
					}
				}
				  
			}else if(city=="北京")
			{
				for(i=0;i<beijingarea.length;i++)
				{
					if(beijingarea[i]==area)
					{
						$("#area").append("<option value='"+beijingarea[i]+"' selected>"+beijingarea[i]+"</option>"); 
					}else
					{
						$("#area").append("<option value='"+beijingarea[i]+"'>"+beijingarea[i]+"</option>"); 
					}
				}
			}else if(city=="")
			{
				$("#area").empty();
			}
		}
		
		function changeMerchantCity()
		{
			var id=$("#cityName").val();
			$("#area").empty();
			if(id=="无锡")
			{
				for(i=0;i<wuxiarea.length;i++)
				{
					$("#area").append("<option value='"+wuxiarea[i]+"'>"+wuxiarea[i]+"</option>"); 
				}
				  
			}else if(id=="北京")
			{
				for(i=0;i<beijingarea.length;i++)
				{
					$("#area").append("<option value='"+beijingarea[i]+"'>"+beijingarea[i]+"</option>"); 
				}
			}else if(id=="")
			{
				$("#area").empty();
			}
		}
		
		
		 function uploadMerchantImg(imgObj)
		 {		 
				 $('#merchantForm').form('submit',{   
				        url: '${ctx}/merchant/uploadMerchantImg',
				        onSubmit: function(){		        	
				        	if(checkImgType(imgObj.value)){
				        		return true;
				        	}
				        	else{
				        		$('#popup_message').popup({'html': '您上传的好像不是图片文件'});
				        		imgObj.outerHTML += "";
				    			imgObj.value="";
				    			$("#imgDiv").hide();
				        		return false;
				        	}		            
				        },
				        success: function(data){
				        	$("#imageUrl").val(data);
				        	//$("#imgDiv").show();
				        	$("#previewImage").attr('src','${ctx}/'+data);
				        }   
			    }); 	
				
			}
		    
		    
		    function checkImgType(imgValue){
		    	if(imgValue.match(/^.*(\.jpg|\.JPG|\.gif|\.GIF|\.png|\.PNG|\.JPEG|\.jpeg)$/i)){
		    		return true;
		    	}
		    	return false;
		    }
		    
		    function saveMerchant()	    
		    {
		    	 resetMsg();
		    	 if($("#name").val()=="")
		    	 {
		    		 $("#nameMsg").html("请填写餐厅名称");
		    		 return;
		    	 }
		    	 if($("#name").val().length>24)
		    	 {
		    		 $("#nameMsg").html("餐厅名称须小于25个字符");
		    		 return;
		    	 }
		    	 if($("#merchantStyle").val()=="")
		    	 {
		    		 $("#styleMsg").html("请选择一个菜系");
		    		 return;
		    	 }
		    	 if($("#cityName").val()=="请选择"||$("#cityName").val()=="")
		    	 {
		    		 $("#areaMsg").html("请选择所在城市");
		    		 return;
		    	 }
		    	 if($("#area").val()==""||$("#area").val()=="请选择")
		    	 {
		    		 $("#areaMsg").html("请选择所在区域");
		    		 return;
		    	 }
		    	 if($("#address").val()=="")
		    	 {
		    		 $("#addressMsg").html("请填写餐厅详细地址");
		    		 return;
		    	 }
		    	 if($("#address").val().length>200)
		    	 {
		    		 $("#addressMsg").html("餐厅地址须小于200个字符");
		    		 return;
		    	 }
		    	 if($("#imageUrl").val()=="")
		    	 {
		    		 $("#imageMsg").html("请上传餐厅招牌图片");
		    		 return;
		    	 }
		    	 if($("#description").val()=="")
		    	 {
		    		 $("#descriptionMsg").html("请简单介绍下餐厅");
		    		 return;
		    	 }
		    	 var descLength = namelen($("#description").val());
		    	 if(descLength>280)
		    	 {
		    		 $("#descriptionMsg").html("餐厅介绍须小于140个汉字或者280个字符");
		    		 return;
		    	 }
		    	 if($("#tel").val()=="")
		    	 {
		    		 $("#telMsg").html("请填写餐厅电话");
		    		 return;
		    	 }
		    	 var telLength = namelen($("#tel").val());
		    	 if(telLength>100)
		    	 {
		    		 $("#telMsg").html("餐厅电话须小于100个字符");
		    		 return;
		    	 }  
		    	 editor.sync();
		    	 var content = $('#editor_id').val();
		    	 $('#content').val(content);
		    	 if(content=="")
		    	 {
		    		 $("#contentMsg").html("请填写餐厅详细信息");
		    		 return;
		    	 }
		    	 var fields = $("#merchantForm").serializeArray();
		    	 var postUrl = "";
		    	 if($('#merchantStatus').val()==3)
		    	 {
		    		 postUrl="${ctx}/merchant/saveOrUpdate";
		    	 }
		    	 else
		    	 {
		    		 postUrl="${ctx}/merchant/update";
		    	 }
		 			$.post(postUrl,fields,function(data){			
		 			if(data.error!=null)
		 			{
		 				if(data.error=="1")
		 				{
		 					$('#popup_message').popup({'html': data.msg});
		 					changepage('${ctx}/login');
		 				}
		 				if(data.error=="2")
		 				{
		 					$("#nameMsg").html("已经有这个餐厅了，请分享一个新的");
		 				}
		 				if(data.error=="3")
		 				{
		 					$('#popup_message').popup({'html': data.msg});
		 					changepage('${ctx}/index');
		 				}
		 			}
		 			else
		 			{
		 				var tempMerchantId = $("#merchantId").val();
		 				var sourceType = '<%=sourceType%>';
		 				changepage('${ctx }/merchant/success?returnType=1&sourceType='+sourceType+'&merchantId='+tempMerchantId);
		 				//history.go(-1);
		 			}
		 		});
		    }
		    
		    function resetMsg()
		    {
		    	 $("#nameMsg").html("");
		    	 $("#styleMsg").html("");
		    	 $("#areaMsg").html("");
		    	 $("#addressMsg").html("");
		    	 $("#imageMsg").html("");
		    	 $("#descriptionMsg").html("");
		    	 $("#telMsg").html("");
		    }
		    
		    
		    function previewMerchantSmall()
		    {
		    	$("#previewImage").attr('src',$("#img_preview").attr('src'));
		    	$("#namePreview").html($("#name").val());
		    	$("#cuisinesPreview").html($("#merchantStyle").val());
		    	$("#consumptionPreview").html($("#priceId").find("option:selected").text()+"元");
		    	$("#descriptionPreview").html($("#description").val());
		    	$("#telPreview").html($("#tel").val());
		    	$("#areaPreview").html($("#cityName").val()+","+$("#area").val());               
		    }		    
		    function doPreview()
		    {
		    	if(null!=$("#merchantId").val()&&$("#merchantId").val()=="")
		    	{
		    		$('#popup_message').popup({'html': '请先保存餐厅信息后再预览'});
		    		return;
		    	}
		    	window.open("/merchantPreview/"+$("#merchantId").val());
		    }
		    
		    
		    function gotoOwner()
		    {
		    	var merchantId = $("#merchantId").val();
		    	changepage('${ctx }/merchant/merchantOwner?merchantId='+merchantId);
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
           <h1 class="slogan">
				更新餐厅信息
			</h1>
         </header>
        
       </div>
     </div>
   </div>
   <div id="content-wrapper">
     <div class="container">
		  <div id="createDiv" class="Formlist">
         <form method="post" id="merchantForm" enctype="multipart/form-data">
         <input type="hidden" id="merchantId" name="merchantId" value="<%=merchantId%>"/>
         <input type="hidden" id="merchantStatus" name="merchantStatus" value=""/>
           <section id="contentSection" class="clearfix">
             <div class="Registerform DiningRoom support">
               <div class="interlaced">
                 <label for="RestaurantName">餐厅名称:<span class="small"></span></label>
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="nameShow"></span>
                 <input class="neutral" type="hidden" name="name" id="name" />
               </div>
               <div id="nameMsg" class="iisi"></div>
               <div class="interlaced wild">
                 <label class="pass" for="">所属菜系:<span class="small"></span></label>
                 <div class="proxy" style="border: none">
									<span class='unchecked' name='colorSpan' checked='true'
										onclick='change(this);'>江浙菜</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>川菜</span>
									<span class='unchecked' name='colorSpan' checked='false'
										onclick='change(this);'>湘菜</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>粤港菜</span>
									<span class='unchecked' name='colorSpan' checked='false'
										onclick='change(this);'>鲁菜</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>北京菜</span>
									<span class='unchecked' name='colorSpan' checked='false'
										onclick='change(this);'>东南亚菜</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>日韩料理</span>
									<span class='unchecked' name='colorSpan' checked='false'
										onclick='change(this);'>自助餐</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>海鲜</span>
									<span class='unchecked' name='colorSpan' checked='false'
										onclick='change(this);'>小吃快餐</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>火锅</span>
									<span class='unchecked' name='colorSpan' checked='false'
										onclick='change(this);'>西餐</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>烧烤烤肉</span>
									<span class='unchecked' name='colorSpan' checked='false'
										onclick='change(this);'>面包甜点</span> <span class='unchecked'
										name='colorSpan' checked='false' onclick='change(this);'>其他</span>
								</div>
               </div>
               <div id="styleMsg" class="iisi"></div>
               <div class="interlaced">
                 <label for="area">所在地区:<span class="small"></span></label>
                 <div class="scapegoat" style="border:none">
                 	<select style="width:70px" id="cityName" name="cityName" onchange="changeMerchantCity();">
								<option id="">请选择</option>
								<option id="beijing">北京</option>
								<option id="wuxi">无锡 </option>
							</select>							
					<select id="area" name="area" style="width:140px"></select>
                 </div>
                 <span id="areaMsg" class="lowrong"></span> </div>
               <div class="iisi"></div>
               <div class="interlaced address">
                 <label class="pass" for="dpasswd">详细地址:<span class="small"></span></label>
                 	<textarea class="neutral" id="address" name="address" style="resize: none;height:auto!important; width:404px;height:70px; min-height:60px"></textarea>          	
                 </div>
               <div id="addressMsg" class="iisi"></div>
               <div class="interlaced">
                 <label for="area">餐厅招牌:<span class="small"></span></label>
                 <div class="scapegoat" style="border:none">
                 		<input style="width:200px; display:inline-block; float:left;" type="file" id="uploadImg" name="uploadImg" onchange="uploadMerchantImg(this);"/>
					    <input type="hidden" id="imageUrl" name="imageUrl"/>
				</div>
                 <span class="lowrong">支持jpg、jpeg、png、gif格式。不超过5MB</span>
                 </div>
                 <div class="proxy" id="imgDiv" style="width:320px;height:183px;display:none;">
			      <center><img id="img_preview" name="img_preview" style="width: 320px; height: 183px;" /></center>
				</div>	
               <div id="imageMsg" class="iisi"></div>
               <div class="interlaced address">
                 <label class="pass" for="dpasswd">餐厅简介:<span class="small"></span></label>
          <textarea class="neutral" id="description" name="description" style="resize: none;height:auto!important; width:404px;height:70px; min-height:60px"></textarea>   
               </div>
               <div id="descriptionMsg" class="iisi"></div>
               <div class="interlaced">
                 <label for="area">餐厅电话:<span class="small"></span></label>
                 <input class="neutral" type="text" name="tel" id="tel" />
               </div>
               <div id="telMsg" class="iisi"></div>
               <div class="interlaced">
                 <label for="area">人均消费:<span class="small"></span></label>
                 <div class="scapegoat" style="border:none">
                 			<select id="priceId" name="priceRegion.priceId" >
								<option value="1">30以内</option>
								<option value="2" selected>30-50</option>
								<option value="3">50-80 </option>
								<option value="4">80-120 </option>
								<option value="5">120-180 </option>
								<option value="6">180-250 </option>
								<option value="7">250以上 </option>
							</select>
                 </div></div>
               <div class="iisi"></div>
               <header class="introduce-title">
                 <h1 class="introduce">详细介绍</h1>
                 <h2 class="supplemental">请在这边分享给大家关于餐厅的故事、菜式的制作口味等</h2>
               </header>
               <div class="interlaced editor">
                 <div class="editor-inside">
                 	<textarea id="editor_id" style="width:404px;height:70px"></textarea>
					<input type="hidden" id="content" name="content" value=""/>
					<input type="hidden" id="merchantStyle" name="merchantStyle" value=""/>               
                 </div>
               </div>
               <div id="contentMsg" class="iisi"></div>
               
                <div id="ownerDiv" class="proxy">
                	提示: 你还不是这个餐厅的掌柜，分享的信息需要在花果客服审核后才可见哦。想单独打理餐厅，赶紧~
                	<a href="javascript:void(0);" onclick="gotoOwner()"><font color="blue">申请成为掌柜</font></a>！                	
                </div>
               <div class="interlaced determine">
                 <button class="btnOrng" type="button" onclick="saveMerchant()">确认更新</button>
             	  <button class="btnOrng" type="button" onclick="history.go(-1);">返回</button>
               </div>
             </div>
           </section>
           <aside id="registerRight" class="register-right" role="">
             <div class="IssuedNotice">
               <section id="" class="profile clearfix">
                 <header class="widget-title">
                   <ul class="Logins clearfix">
                     <li class="selected" onclick="previewMerchantSmall()">刷新预览</li>
                     <li>范例</li>
                   </ul>
                 </header>
               </section>
               <div class="previewlist">
                 <div>
                   <section id="" class="profile clearfix">
                     <div class="featured-img"> <a href="javascript:void(0);" class="thumbnail" title=""><img id="previewImage" width="302" height="183" src="" class="" alt="" title=""></a>
                       <div class="Name">
                         <h3><span id="namePreview"></span></h3>
                         <a class="referrer" href="javascript:void(0);"><img src="${ctx}/static/images/defaultHeadMin.png" alt=""> </a></div>
                       <div class="Namebg">&nbsp;</div>
                     </div>
                     <section class="listCuisines"><span id="cuisinesPreview" class="cuisines"></span><span id="consumptionPreview" class="consumption"><em></em></span> </section>
                     <p id="descriptionPreview" class="content"></p>
                     <ul class="ContactInfo">
                       <li id="telPreview" class="telephone"></li>
                       <li id="areaPreview" class="regional"></li>
                     </ul>
                     <section class="comments"> <a href="javascript:void(0);" style= "cursor:default" class="likeBtn" title="喜欢">0</a> <a href="javascript:void(0);" style= "cursor:default" class="commentsBtn" title="评论">0</a> </section>
                   </section>
                 </div>
                 <div class="hidden">
                   <section id="" class="profile clearfix">
                     <div class="featured-img"> <a href="javascript:void(0);" class="thumbnail" title=""><img width="302" height="183" src="${ctx}/static/images/defaultMerchant.png" class="" alt="" title=""></a>
                       <div class="Name">
                         <h3><a href="javascript:void(0);">申汇轩百姓厨房</a></h3>
                         <a class="referrer" href="javascript:void(0);"><img src="${ctx}/static/images/defaultHeadMin.png" alt=""> </a></div>
                       <div class="Namebg">&nbsp;</div>
                     </div>
                     <section class="listCuisines"><span class="cuisines">江浙菜</span><span class="consumption">人均消费:80-120</span> </section>
                     <p class="content">出品以江浙菜为主，卖相“精致”，口味“清淡”。环境“清幽舒服”，“很适合”喜欢“安静”的朋友。服务也“不错”，添茶添酒“蛮及时”。只是价格“贵了点”，自掏腰包会“很心疼”...</p>
                     <ul class="ContactInfo">
                       <li class="telephone">0510-82329779</li>
                       <li class="regional">崇安区--保利广场</li>
                     </ul>
                     <section class="comments"> <a href="javascript:void(0);" style= "cursor:default" class="likeBtn" title="喜欢">100人喜欢</a> <a href="javascript:void(0);" style= "cursor:default" class="commentsBtn" title="评论">100条评论</a> </section>
                   </section>
                 </div>
               </div>
             </div>
           </aside>
         </form>
       
       </div>
     </div>
   </div>
   <%@ include file="/static/page/footer.jsp"%>
   <div id="popup_message"> </div>
</body>
</html>