<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.touco.huaguo.common.Constants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.touco.huaguo.domain.UserEntity"%>
<%
	UserEntity userSession = new UserEntity();
	if (request.getSession() != null && request.getSession().getAttribute(Constants.USER_SESSION_INFO) != null) {
		userSession = (UserEntity) request.getSession().getAttribute(Constants.USER_SESSION_INFO);
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
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/pagination.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/popup.css" media="all" />
<script type="text/javascript" src="${ctx}/static/scripts/index/searchValue.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/scrollpagination.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/index/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.popup.js"></script>
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
					<h1 class="slogan">私信
						(<span class="notice" id="privateLetterCount">  </span>)
					</h1>
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
							<span class="introduce" id="linkManCount"></span>个联系人
						</h1>
						</header>
						<ul class="Private" id="_privateLetterId">

						</ul>
						<!--  私信END  -->
						<center>
							<div id="myPrivateLetterPagination" class="pagination"> </div>
						</center>
					</div>
					</section>
				</div>
		     </div>
		   </div>

		<%@ include file="/static/page/footer.jsp"%>
		
		<div id="popup_message"> </div>
		<div id="confirm_message"> </div>
		
	</div>
	<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.confirm.js"></script>
	<script type="text/javascript">
		jQuery.ajaxSetup({
			cache : false
		});//ajax不缓存

		$(document).ready(
			function() {
				initLoadPrivateLetter();
		});
		
		
		function initLoadPrivateLetter(){
			// 获取私信
			$.post("${ctx}/messages/getMyPrivateLetterList", null,
				function(data) {
					var optInit = {
						callback : privateLetterPageselectCallback,
						items_per_page : 10,
						num_display_entries : 5,
						num_edge_entries : 3,
						prev_text : "上页",
						next_text : "下页"
					};
					
					$("#myPrivateLetterPagination").pagination(data.total,optInit);
					
					$('#privateLetterCount').html(data.messageCount);
					$('#myMessageheadCount').html("("+data.messageCount+")");
					if(data.linkManCount==undefined||data.linkManCount==0){
						data.linkManCount =0;
					}
					$('#linkManCount').html(data.linkManCount);
			});
		}

		
		function privateLetterPageselectCallback(letter_page_index, jq) {
			letter_page_index++;
			$.post("${ctx}/messages/getMyPrivateLetterList?page="+ letter_page_index,null,
				function(data) {
					if(data.rows != null &&  data.rows.length != 0){
						var privateLetterData = data.rows;
						$('#_privateLetterId').empty();
						//动态插入数据	
						var divAppend='';
						for (i = 0; i < privateLetterData.length; i++) {
							divAppend+='<li><div class="wrap clearfix">';
							divAppend+='<div class="RestaurantSet lettersTell clearfix">';
							
							contentText = privateLetterData[i].content;
							result=contentText;
						 	if (contentText.length > 75){
						 		result = contentText.substring(0, 75) + "...";
						 	}
							
						 	isRead = privateLetterData[i].isRead;
						 	
							if(privateLetterData[i].sender.userId == <%=userSession.getUserId()%>){
								var headUrl= privateLetterData[i].sender.imageUrl;
								divAppend+='<img src="'+getUserImageUrl(headUrl,48)+'" alt="用户头像" height="48" width="48" onload="newDrawImage(this,48,48)" />';
								
								
								var nickNameText = privateLetterData[i].receiver.nickName;
								if (nickNameText.length > 6) {
									nicknameResult = nickNameText.substring(0, 6) + "...";
								}else{
									nicknameResult = nickNameText ;
								}
								
								divAppend+='<h3 class="RestName"  title="'+nickNameText+'">发送给'+nicknameResult+':</h3> ' ;
								
								divAppend+='<div class="listCuisines"><span class="cuisines">'+privateLetterData[i].showDate+'</span></div></div>' ;
								if(isRead==1){ //0-未读，1-已读
									divAppend+='<span class="RestNews" title = "' + contentText + '">'+result+'</span>';
						 		}else if(isRead==0){
						 			divAppend+='<span class="RestNews" title = "' + contentText + '"><font style="font-weight:bold;" color="#006030">'+result+'</font></span>';
						 		}
							}else if(privateLetterData[i].sender.userId!=<%=userSession.getUserId()%>){
								var headUrl= privateLetterData[i].sender.imageUrl;
								
								divAppend+='<img src="'+getUserImageUrl(headUrl,48)+'" alt="用户头像" height="48" width="48" onload="newDrawImage(this,48,48)" />';
								
								if(generalUtil.isNotBlank(privateLetterData[i].sender.nickName)){
									nickNameText = privateLetterData[i].sender.nickName;
								 	if (nickNameText.length > 6){
								 		nickNameResult = nickNameText.substring(0,6) + "...";
								 	}else{
									 	nickNameResult=nickNameText;
								 	}
								 	divAppend+='<h3 class="RestName" title = "' + nickNameText + '">'+ nickNameResult +'回复你:</h3>' ;
								}
								
								divAppend+='<div class="listCuisines"><span class="cuisines">'+privateLetterData[i].showDate+'</span></div></div>' ;
						 		
								if(isRead==1){ //0-未读，1-已读
									divAppend+='<span class="RestNews" title = "' + contentText + '">'+result+'</span>';
						 		}else if(isRead==0){
						 			divAppend+='<span class="RestNews" title = "' + contentText + '"><font style="font-weight:bold;" color="#006030">'+result+'</font></span>';
						 		}
							}
							
							var dataList;
							var  parm = {};
							parm['recordId'] = privateLetterData[i].recordId;
							jQuery.ajax({
								type: "post",
					            url: '${ctx}/initMyMessages', 
					            data: parm,
					            cache: false,
					            async: false,
					            global:false,
					            success: function(calbcakData){
					                if(calbcakData){ 
					                	divAppend+='<div class="dialogue"><a id="article_'+privateLetterData[i].recordId+'" class="article" onclick="viewMoreDialogue(\''+i+'\', \''+privateLetterData[i].recordId+'\', \''+privateLetterData[i].sender.userId+'\', \''+privateLetterData[i].receiver.userId+'\')">';
					                	divAppend+='共' + calbcakData.messageList.length +'条对话</a>' ;
					                	dataList = data.list;
					                }
					            }
					        });
							
							<%if ( userSession.getUserId() != null) {%>
													
								if (privateLetterData[i].sender.userId != <%=userSession.getUserId()%>) {
									divAppend += '|&nbsp; <a id="reply_'+ privateLetterData[i].recordId+ '" class="reply" href="javascript:void(0);" title="回复" onclick="showReplyArea(\''+ privateLetterData[i].recordId + '\',\''+privateLetterData[i].sender.nickName+'\')">回复</a>';
								}else if(privateLetterData[i].receiver.userId == <%=userSession.getUserId()%>){
									divAppend += '|&nbsp; <a id="reply_'+ privateLetterData[i].recordId+ '" class="reply" href="javascript:void(0);" title="回复" onclick="showReplyArea(\''+ privateLetterData[i].recordId + '\',\''+privateLetterData[i].receiver.nickName+'\')">回复</a>';
								}
								//删除自己的私信
								if(privateLetterData[i].sender.userId == <%=userSession.getUserId()%>){	
									divAppend += '|&nbsp;<a id="isDelete_'+ privateLetterData[i].recordId+ '" title="确定要删除与'+privateLetterData[i].receiver.nickName+'所有私信？" href="javascript:void(0);" onclick="delPrivateLetter(\'' + privateLetterData[i].recordId + '\',\''+privateLetterData[i].receiver.nickName+'\',\'1\')">删除</a>';
								}else{
									divAppend += '|&nbsp;<a id="isDelete_'+ privateLetterData[i].recordId+ '" title="确定要删除与'+privateLetterData[i].receiver.nickName+'所有私信？" href="javascript:void(0);" onclick="delPrivateLetter(\'' + privateLetterData[i].recordId + '\',\''+privateLetterData[i].sender.nickName+'\',\'0\')">删除</a>';
								}
							<%} else {%>
								
								if (privateLetterData[i].sender.userId == <%=userSession.getUserId()%> ) {
									divAppend += '| &nbsp;<a id="isDelete_'+ privateLetterData[i].recordId+ '" title="确定要删除与'+privateLetterData[i].sender.nickName+'所有私信？"  href="javascript:void(0);" onclick="delPrivateLetter(\''+ privateLetterData[i].recordId + '\',\''+privateLetterData[i].sender.nickName+'\',\'0\')">删除</a>';
								} else{
									divAppend += '| &nbsp;<a id="isDelete_'+ privateLetterData[i].recordId+ '" title="确定要删除与'+privateLetterData[i].sender.nickName+'所有私信？" href="javascript:void(0);" onclick="delPrivateLetter(\'' + privateLetterData[i].recordId + '\',\''+privateLetterData[i].receiver.nickName+'\',\'1\')">删除</a>';
								}
							<%}%> 
							
							divAppend+='</div></div>';
							
							 /* 显示更多   */
							divAppend+='<div class="wrap clearfix">'
							divAppend+='<div class="private-comment" id="private-comment_'+ privateLetterData[i].recordId+ '">';
							divAppend+='<div class="private-comment-top"></div>';
							divAppend+='<div id="" class="private-box-title">';
							divAppend+='<div class="ui-textarea">'
							divAppend+='<div class="ui-textarea-border"> ';
							divAppend+='<textarea id="content_'+privateLetterData[i].recordId+'" name="content_'+privateLetterData[i].recordId+'" style="resize: none;" placeholder="发表私信"></textarea>';
							divAppend+='</div></div>';
							divAppend+='<div class="private-box-textarea-b">';
							divAppend+='<div class="ui-button ui-button-green"><span>';
							divAppend+='<button type="submit" onclick="setPrivateLetter(\''+i+'\', \''+privateLetterData[i].recordId+'\', \''+privateLetterData[i].sender.userId+'\', \''+privateLetterData[i].receiver.userId+'\',\''+privateLetterData[i].sender.nickName+'\')">发送</button>';
							divAppend+='</span></div>';
							divAppend+='</div>';
							divAppend+='</div>';
							divAppend+='</div>';
							divAppend+='</div>';
							divAppend+='</li>';
						}
						$('#_privateLetterId').append(divAppend);
					}else{
						$('#_privateLetterId').html("没有未读私信");
						$('#myPrivateLetterPagination').hide();
					}
				});
		}
		
		function setPrivateLetter(j, pid,senderUserId,receiverUserId,nickName){
			var content = $.trim($('#content_'+pid).val());
			contentLen = content.replace(indexContent," "); 
			if(generalUtil.strLengthCN(contentLen)>280){
				$('#popup_message').popup({'html': '请保持您的内容在280个字符以内！'});
				return false;
			}
			var indexContent = "回复 "+nickName+"：";
			if(contentLen == '' || content==indexContent ){
				$('#popup_message').popup({'html': '请输入内容！'});
			}else{
				var p = {};
				p['content'] = contentLen;
				addPrivateLetter(j,p, pid,senderUserId,receiverUserId);
			} 
		}
		
		function addPrivateLetter(j,p, pid,senderUserId,receiverUserId){
			p['pid'] = pid;
			p['recordId']=pid;
			p['sender.userId'] = receiverUserId;
			p['receiver.userId'] = senderUserId;
			$.post(ctx + '/messages/addPrivateLetter', p, function(ret) {
				if(ret == 'SUCCESS'){
					$("#private-comment_"+pid).slideToggle(100);
					document.getElementById("content_"+pid).value='';
					initLoadPrivateLetter();
				}else if(ret=='NOTLOGIN'){
					$('#popup_message').popup();
				}
			});
		}

		
		function delPrivateLetter(messageId,ncikName,isOwnOrOther) {
			var result=ncikName ;
			if (ncikName.length > 5){
		 		result = ncikName.substring(0, 5) + "...";
		 	}
			
			$('#confirm_message').confirm({
				html:'确定要删除与'+result+'之间的所有私信？',
				okButtonAction:function(){
					var params = "";
					params += "messageId=" + messageId+"&isOwnOrOther="+isOwnOrOther;
					$.ajax({
						type: "post",
			            url: "${ctx}/messages/delPrivateLetter",
			            data: params,
			            success: function (returnData) {
			            	if(returnData.success==1){
								initLoadPrivateLetter();
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
		

		//私信回复
		function showReplyArea(messageId,nickName){
			$("#private-comment_"+messageId).slideToggle(100);
			$('#content_'+messageId).focus();
			$('#content_'+messageId).click();
			$('#content_'+messageId).val("回复 "+nickName+"：");
			$(".ui-textarea-border").css("background-image","none");
			$(".ui-textarea-border").css("background-color","rgb(255, 255, 221)");
		}
		
		function viewMoreDialogue(i, recordId,senderUserId,receiverUserId){
			changepage('${ctx }/messages/'+recordId+"/"+senderUserId+"/"+receiverUserId);
			
			initLoadPrivateLetter();
		}
		
	</script>

</body>
</html>