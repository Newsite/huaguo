<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/page/jqueryMaster.jsp" %>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="${ctx}/static/css/content.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div style="overflow:auto;">
		<form id="viewForm" style="margin: 10; text-align: center;">
		<input type="hidden" name="userId" id="userId">

			<table border="0" class="text03" style="text-align: left;">
				<tr>
					<td align="right"><label style="color: red">*</label>
						用户名：</td>
				  <td colspan="3"><input id="userName" name="userName"
						class="easyui-validatebox" readonly="readonly" validType="length[1,20]"  style=" width:150px"></td>	
                </tr>
				<tr>
					<td align="right"><label style="color: red">*</label>
						旧密码：</td>
				  <td colspan="3"><input id="oldPassword" name="oldPassword" type="password"
						class="easyui-validatebox"
						required="true" validType="length[4,20]" style=" width:150px"></td>					
				</tr>
				<tr>
					<td align="right"><label style="color: red">*</label>
						新密码：</td>
				  <td colspan="3"><input id="newPassword" name="password" type="password"
						class="easyui-validatebox"
						required="true" validType="length[4,20]" style=" width:150px"></td>					
				</tr>
				
				<tr>
					<td align="right"><label style="color: red">*</label>确认密码：</td>
					<td colspan="3"><input id="confPassword" name="confPassword" type="password" class="easyui-validatebox" validType="length[6,50]" style=" width:150px"></td>
				</tr>
				
				<tr>
				  <td height="10" colspan="4"></td>
			  </tr>
				<tr>
					<td colspan="4" align="center"><a href="javascript:void(0)" id="btn-add"
						onClick="updateAdminPassword();" class="easyui-linkbutton"
						iconcls="icon-save">保存</a>&nbsp;&nbsp;&nbsp;					<a href="javascript:void(0)" id="btn-back"
						onClick="closeWindow();" class="easyui-linkbutton"
						iconcls="icon-cancel">关闭</a></td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>
