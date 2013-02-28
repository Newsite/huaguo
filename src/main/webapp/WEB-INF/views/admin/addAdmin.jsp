<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/static/page/jqueryMaster.jsp"%>
</head>
<body>
	<script type="text/javascript">
	jQuery.ajaxSetup({cache:false});//ajax不缓存
	
	jQuery(function($) {
		$.extend($.fn.validatebox.defaults.rules, {
		    rePassword: {
		        validator: function(value, param){  
		            return value == $('#'+param[0]).val();
		        },
		        message: '请输入相同密码！'
		    }
		});
	});
	</script>
	<form id="adminForm" method="post" >
		<input type="hidden" name="userId" id="userId">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input name="userName" id="userName" validtype="length[2,50]" class="easyui-validatebox" required="true"
					style="width: 150px"></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" name="adminPassword"
					id="adminPassword" validtype="length[6,20]"
					class="easyui-validatebox" required="true" style="width: 150px">
				</td>
			</tr>
			<tr>
				<td>重复密码：</td>
				<td><input type="password" name="adminRePassword" id="adminRePassword"
					validtype="rePassword['adminPassword']" class="easyui-validatebox" required="true"
					style="width: 150px">
				</td>
			</tr>
			
			<tr>
				<td colspan="2" style="text-align: center;"><a href="#"
					id="btn-add" onClick="saveOrUpdateAdmin();"
					class="easyui-linkbutton" iconcls="icon-save">保存</a>&nbsp;&nbsp;&nbsp;<a
					href="#" id="btn-back" onClick="closeWindow();"
					class="easyui-linkbutton" iconcls="icon-cancel">关闭</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>