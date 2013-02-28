<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/page/admin/jqueryeasy.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title></title>
</head>

<body>
	<div style="overflow:auto;">
		<form id="userForm" style="margin:10px;text-align:center;display:inline;">
		<input type="hidden" name="userId" id="userId">

			<table cellspacing="5" class="text03" style="text-align:left; border:none;">
				<tr>
					<td style="text-align:right;">
					<label style="color:red">*</label>用户昵称：</td>
			    <td width="170"><input name="nickName" id="nickName"
						validType="lengthCN[2,32]" class="easyui-validatebox"
						required="true" style="width:150px"></td>
					<td style="text-align:right;"><label style="color: red">*</label>登录密码：</td>
					<td><input name="password" type="password" id="password"
						validtype="length[4,20]" class="easyui-validatebox"
						required="true" style="width:150px"></td>
				</tr>
				<tr>
					<td style="text-align:right;"><label style="color:red">*</label>
						注册邮箱：</td>
			    <td><input class="easyui-validatebox" type="text"
						name="email" id="email" validType="checkEmail" style="width:150px"/></td>
					<td style="text-align:right;">
						<input type="checkbox" id="isDisable" name="isDisable" onclick="isDisableBtn();"/>
					</td>
					<td>显示密码
					</td>
				</tr>
				<div style="height: 10px; left: 12px;position: absolute;top: 114px;width: 538px;' id="u292_line"></div>
				
				<tr>
					<td style="text-align:right;">真实姓名：</td>
					<td><input name="trueName" type="text" id="trueName"
						class="easyui-validatebox"   style="width:150px" maxlength="20"/></td>
					<td style="text-align:right;">性别：</td>
					<td>
						<input type="radio" id="m" name="gender" value="m" checked="checked"> 男 
						<input type="radio" id="f" name="gender" value="f"> 女
						<input type="radio" id="n" name="gender" value="n"> 未知
					</td>
				</tr>
				
                <tr>
					<td style="text-align:right;">所在城市：</td>
					<td colspan="4">
						<select id="province" class="easyui-combobox" name="province.cityId" style="width:118px;" required="false" panelHeight="auto">
						</select>
						<select id="city" class="easyui-combobox" name="city.cityId" style="width:120px;" required="false" panelHeight="auto">
						</select>
					</td>
				</tr>
				
				
			<tr>
					<td style="text-align:right;">个人主页：</td>
					<td colspan="4">
						<input  class="easyui-validatebox" type="text" name="webPage" id="webPage"  validType="isValidaUrl[$('#webPage').val()]"  style="width: 240px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align:center;"><a href="#" id="btn-add"
						onClick="addOrUpdate();" class="easyui-linkbutton"
						iconcls="icon-save">保存</a>&nbsp;&nbsp;&nbsp;<a href="#" id="btn-back"
						onClick="closeWindow();" class="easyui-linkbutton"
						iconcls="icon-cancel">关闭</a>	</td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>
