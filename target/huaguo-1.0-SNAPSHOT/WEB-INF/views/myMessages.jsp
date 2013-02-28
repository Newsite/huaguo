<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.touco.huaguo.common.Constants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.touco.huaguo.domain.UserEntity"%>
<%
	UserEntity userSession = new UserEntity();
	if (request.getSession() != null && request.getSession().getAttribute( Constants.USER_SESSION_INFO) != null) {
		userSession = (UserEntity) request.getSession().getAttribute( 	Constants.USER_SESSION_INFO);
	}
	if (session.getAttribute("senderUserId") != null) {
		Object	senderUserId = session.getAttribute("senderUserId");
	}
	if (session.getAttribute("receiverUserId") != null) {
		Object	receiverUserId = session.getAttribute("receiverUserId");
	}
	if (session.getAttribute("recordId") != null) {
		Object	recordId = session.getAttribute("recordId");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/static/page/jqueryMaster.jsp"%>
<title>花果网-发现美食</title>
<meta content="花果网,美食分享,分享我爱的餐厅,优惠券,发现美食,鲁菜,川菜,粤菜,闽菜,苏菜,浙菜,湘菜,徽菜中餐,自助餐,西餐" name="keywords" />
<meta content="花果网,是一个发起美食引导消费的平台,来到花果,开花结果！" name="description" />
<meta content="all" name="robots" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/share.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<script type="text/javascript" src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/scrollpagination.js"></script>
<style type="">
	A, A:link, A:visited, A:active {
    color: #ff6600;
    cursor: pointer;
    outline: medium none;
    text-decoration: none;
}
A, A:link, A:visited, A:active {
    color: #ff6600;
    cursor: pointer;
    outline: medium none;
    text-decoration: none;
}

.ui-textarea-border backgroundStyle {
	background-image: none;
	background-color: rgb(255, 255, 221);
}
</style>
</head>
<body>

	<div id="share">
		<!--  头部  -->
		<%@ include file="/static/page/header.jsp"%>
		<!--  导航  -->
		<%@ include file="/static/page/navigation.jsp"%>

		<div id="slogan" class="Share">
			<div class="container">
				<div class="switching clearfix">
					<header class="slogan-title">
					<h1 class="slogan">私信</h1>
					</header>
				</div>
			</div>
		</div>

		<div id="content-wrapper">
			<div class="container">
				<!--  私信Start -->
				<div class="Formlist">
					<section id="content3" class="">
					<div class="Registerform DiningRoom support">
						<header class="introduce-title">
						<h1 class="introduce">
							<a href="${ctx}/messages">  << 返回所有私信 </a>
						</h1>
						</header>

						<div class="wrap clearfix">
							<div class="send">
								<div class="private-start">
								
								<div class="ui-textarea">
									<div class="ui-textarea-border backgroundStyle" id="ui-textarea-border"  >
										<textarea name="content" id="content" style="resize: none;overflow: hidden; word-wrap: break-word; margin-bottom:0px; max-height: 97px; min-height: 97px; height: 97px;"></textarea>
									</div>
                                    </div>
								</div>
								
								<div class="private-box-textarea-b">
									<div class="ui-button ui-button-green">
										<span>
											<button type="submit" onclick="setMyPrivateLetter()">发送</button>
										</span>
									</div>
								</div>
							</div>
						</div>

						<ul class="Private clearfix" id="_privateLetterId">

						</ul>
						<!--  私信END  -->
					</div>
					</section>
				</div>
			</div>
		<%@ include file="/static/page/footer.jsp"%>
		</div>
		
		<div id="popup_message"> </div>
  		<div id="confirm_message"> </div>
		
	</div>

	<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
	<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.confirm.js"></script>
	<script type="text/javascript">
		jQuery.ajaxSetup({
			cache : false
		});//ajax不缓存

		$(document).ready(
				function() {
					initLoadMyPrivateLetter();
		});

		function initLoadMyPrivateLetter() {
			$('#_privateLetterId').html('');
			var thtml = '';			
			var p = {};
			var recordId ='${recordId}';
			p['recordId'] = recordId;
			$.post("${ctx}/initMyMessages", p,
				function(initData) {
					if(initData.messageList!=undefined||initData.messageList!=null){
						for(var i=0; i<initData.messageList.length; i++){
							thtml+='<li><div class="wrap clearfix"><div class="RestaurantSet lettersTell clearfix">';
			                
							contentText = initData.messageList[i].content;
							result=contentText;
						 	if (contentText.length > 55){
						 		result = contentText.substring(0, 55) + "...";
						 	}
						 	var isRead=  initData.messageList[i].isRead;
						
							if(initData.messageList[i].sender.userId == <%=userSession.getUserId()%>){
								var headUrl = initData.messageList[i].sender.imageUrl ;
								thtml+='<img src="'+getUserImageUrl(headUrl,48)+'" alt="用户头像" height="48" width="48" onload="newDrawImage(this,48,48)" />';

								thtml+='<h3 class="RestName">我:</h3>' ;
								
								thtml+='<div class="listCuisines"><span class="cuisines">'+initData.messageList[i].showDate+'</span></div></div>' ;
						 		
								if(isRead==1){ //0-未读，1-已读
									thtml+='<span class="RestNews" title = "' + contentText + '">'+result+'</span>';
						 		}else if(isRead==0){
						 			thtml+='<span class="RestNews" title = "' + contentText + '"><font style="font-weight:bold;" color="#006030">'+result+'</font></span>';
						 		}
								
								thtml+=' <div class="dialogue">';
								
								thtml+='<a class="delete" href="javascript:void(0);" onclick="delMyPrivateLetter(\''+ initData.messageList[i].recordId + '\',\'0\')">删除</a>';
								
							}else if(initData.messageList[i].sender.userId!=<%=userSession.getUserId()%>){
								var headUrl = initData.messageList[i].sender.imageUrl;
								thtml+='<img src="'+getUserImageUrl(headUrl,48)+'" alt="用户头像" height="48" width="48" onload="newDrawImage(this,48,48)" />';
								if(generalUtil.isNotBlank(initData.messageList[i].sender.nickName)){
									nickNameText = initData.messageList[i].sender.nickName;
								 	nickNameResult=nickNameText;
								 	if (nickNameText.length > 6){
								 		nickNameResult = nickNameText.substring(0,6) + "...";
								 	}
								 	thtml+='<h3 class="RestName" title = "' + nickNameText + '">'+ nickNameResult +'</h3>' ;
								}
								
								thtml+='<div class="listCuisines"><span class="cuisines">'+initData.messageList[i].showDate+'</span></div></div>' ;
								if(isRead==1){ //0-未读，1-已读
									thtml+='<span class="RestNews" title = "' + contentText + '">'+result+'</span>';
						 		}else if(isRead==0){
						 			thtml+='<span class="RestNews" title = "' + contentText + '"><font style="font-weight:bold;" color="#006030">'+result+'</font></span>';
						 		}
								
								thtml+='<div class="dialogue">';
								thtml+='<a class="reply" onclick="replayBtn(\''+initData.messageList[i].sender.nickName+'\');">回复</a>| ';
								thtml+='<a class="delete" href="javascript:void(0);" onclick="delMyPrivateLetter(\''+ initData.messageList[i].recordId + '\',\'1\')">删除</a>';
							}
						
							thtml+='</div>';
							thtml+='</div>';
							thtml+='</li>';
						}
						$('#_privateLetterId').append(thtml);
					}else{
						changepage('${ctx }/messages');
					}
			});
		}
		
		var allNickName ; 
		function setMyPrivateLetter(){
			var senderUserId = <%=userSession.getUserId()%> ; 
			var receiverUserId ='${senderUserId}';
			var receiverUserId2 ='${receiverUserId}';
			var recordId ='${recordId}';
			
			var content = $.trim($('#content').val());
			contentLen = content.replace(indexContent," "); 
			if(generalUtil.strLengthCN(contentLen)>280){
				$('#popup_message').popup({'html': '请保持您的内容在280个字符以内！'});
				return false;
			}
			var indexContent = "回复 "+allNickName+"：";
			if(contentLen == '' || content==indexContent ){
				$('#popup_message').popup({'html': '请输入内容！'});
			}else{
				var p = {};
				p['content'] = contentLen;
				addPrivateLetter(p,recordId,senderUserId,receiverUserId);
			} 
		}
		

		function addPrivateLetter(p,pid,senderUserId,receiverUserId){
			p['pid'] = pid;
			p['recordId']=pid;
			p['sender.userId'] = senderUserId;
			p['receiver.userId'] =  receiverUserId;
			p['isReadStatus']='1';  //在我的私信里面回复 直接设置为已读
			
			$.post(ctx + '/messages/addPrivateLetter', p, function(ret) {
				if(ret == 'SUCCESS'){
					initLoadMyPrivateLetter();
					$("#ui-textarea-border").removeClass("backgroundStyle");
					$('#content').val("");
				}else if(ret=='NOTLOGIN'){
					$('#popup_message').popup();
				}
			});
		}

		
		function delMyPrivateLetter(messageId,isOwnOrOther) {
			$('#confirm_message').confirm({
				html:'确定要删除此条私信？',
				okButtonAction:function(){
					var params = "";
					params += "messageId=" + messageId+"&isOwnOrOther="+isOwnOrOther;
					$.ajax({
						type: "post",
			            url: "${ctx}/messages/delMyPrivateLetter",
			            data: params,
			            success: function (returnData) {
			            	if(returnData.success=="success"){
			            		initLoadMyPrivateLetter();
			            		setTimeout('window.location.reload()',10000); //指定1秒刷新一次
							}else if(returnData.error==1){
								$('#popup_message').popup();
							}
			            },
			            error: function () {
			            	$('#popup_message').popup();
			            }
					});
				}
			});
		}
		
		 function replayBtn(nickName){
			allNickName = nickName;
			$('#content').focus();
			$('#content').click();
			$('#content').val("回复 "+nickName+"：");
			$("#ui-textarea-border").css("background-image","none");
			$("#ui-textarea-border").css("background-color","rgb(255, 255, 221)");
		 }
	</script>

</body>
</html>