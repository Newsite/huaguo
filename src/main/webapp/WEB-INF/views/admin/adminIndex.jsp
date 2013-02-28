<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/page/admin/jqueryeasy.jsp" %>
<%@ page import="com.touco.huaguo.domain.AdminEntity,com.touco.huaguo.common.Constants"%>
<%
	AdminEntity admin = (AdminEntity) session.getAttribute(Constants.ADMIN_SESSION_INFO);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

	<title>花果管理后台</title>
	
	<script type="text/javascript">
		jQuery.ajaxSetup({cache:false});//ajax不缓存
		
		function showTab(url, title) {
			var tab = $('#tab');
			if (tab.tabs('exists', title)) {
				tab.tabs('select', title);
			} else {
				tab.tabs('add',
						{title : title, content : "<iframe scrolling='yes' frameborder='0' src='${ctx}/" + url
											+ "' style='width:100%;height:100%;'/>",
									closable : true
						});
			}
		}
		
		//注销
		function doLogout() {
	    	$.messager.confirm('提示', '您确定注销系统?', 
	    		function(result){
		    		if (result) {
		    			$.post("${ctx}/adminLogout",function(data){
		    				window.location.replace(window.location.protocol+'//' + window.location.host+'${ctx}/admin');
		    			});
		    		}
		    	});
	    }
		
		
		function modifyAdminPwd() {
			showWindow({
				title : '修改密码',
				href : '${ctx}/modifyAdminPwd',
				width : 340,
				height : 260,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				resizable : false,
				draggable : false,
				onLoad : function() {
					$("#userId").val('<%=admin.getUserId()%>');
					$("#userName").val('<%=admin.getUserName()%>');
				}
			});
		}
		
		function updateAdminPassword() {
			var r = $('#viewForm').form('validate');
			if (!r) {
				return false;
			}

			var params = {};
			var oldpassword = "";
			var password = "";
			var confPassword = "";
			var fields = $("#viewForm").serializeArray();
			$.each(fields, function(i, field) {
				params[field.name] = field.value;
				if (field.name == "oldPassword") {
					oldpassword = field.value;
				}
				if (field.name == "password") {
					password = field.value;
				}
				if (field.name == "confPassword") {
					confPassword = field.value;
				}
			});

			if (password != confPassword) {
				$.messager.alert('提示', '两次密码不一致!', 'info');
				return false;
			}

			params["password"] = oldpassword;
			$.post("${ctx}/getAccount", params, function(data) {
				if (!data) {
					$.messager.alert('提示', '旧密码不正确!', 'info');
				} else {
					params["password"] = confPassword;
					$.post("${ctx}/admin/modifyAdminPwd", params, 
						function(data) {
							$('#MyPopWindow').window('close');
							$.messager.alert('提示', data.msg, 'info');
					});
				}
			});
		}
		
		
	</script>
</head>
<body class="easyui-layout" id="mainBody">
	<div region="north"  title="" split="false" style="height:74px;text-align:left;overflow:hidden;">
		<div class="head_tt">
		  	<h1 class="logo"><a href="##" title="花果管理后台"> 花果管理后台</a></h1>
		  
		  	<strong class="Welcome">Hi， 
		  	 	<span id="trueName"><% if(null!=admin){%><%=admin.getUserName()%><% }%>
		  		</span>
		  	</strong>
		  
		  <div class="SystemSettings">
		    <ul class="SettingItem">
		       <li class="SettingItem2"><a href="#" onClick="modifyAdminPwd()" title="修改密码">修改密码</a></li>
		      <li class="SettingItem4"><a href="##" title="Exit" onClick="doLogout();" title="退出登录">退出登录</a></li>
		    </ul>
		  </div>
		</div>
	</div>
		
	<div region="west" split="false" title="系统模块"  style="width:220px;padding1:1px;overflow:hidden;">
		<div class="easyui-accordion" fit="true" border="false">
		   <div  id="MenuHead"  title="花果管理平台" selected="true"  style="overflow:auto;">
					<div id="menuDiv" class="easyui-accordion" fit="true" border="false" animate="false" style="overflow-x:hidden;overflow-y:auto;">
						<ul  style="width:210px;overflow:visible;">
							<li>
								<a title="餐厅信息" onclick="showTab('admin/merchant/merchantInfoList', '餐厅信息');">餐厅信息</a>
							</li>
							<li>
								<a title="餐厅信息审核" onclick="showTab('admin/merchant/merchantList', '餐厅信息审核');">餐厅信息审核</a>
							</li>
							<li>
								<a title="餐厅动态审核" onclick="showTab('admin/merchantEvent/merchantEventList', '餐厅动态审核');">餐厅动态审核</a>
							</li>
							<li>
								<a title="掌柜审核" onclick="showTab('admin/merchantOwner/merchantOwnerList', '掌柜审核');">掌柜审核</a>
							</li>
						</ul>
						
						<ul  style="width:210px;overflow:visible;" title="用户管理">
							<li>
								<a title="用户列表" onclick="showTab('admin/user/userList', '用户列表');">用户管理</a>
							</li>
							<li>
								<a title="管理员列表" onclick="showTab('admin/adminList', '管理员列表');">管理员管理</a>
							</li>
						</ul>
						
					</div>
			</div>

		</div>
	</div>
	
	<div region="center" class="maindiv" title="" style="overflow:hidden;">
		<div id="tab" class="easyui-tabs" fit="true" border="false">
		</div>
	</div>	
		
		
</body>

<div id="MyPopWindow" modal="true" shadow="false" cache="false" style="margin: 0px;padding: 0px;overflow: auto;"></div>
</html>