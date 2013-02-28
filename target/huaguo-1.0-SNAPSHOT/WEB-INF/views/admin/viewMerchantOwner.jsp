<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/page/admin/jqueryeasy.jsp"%>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

</head>

<body>
	<div style="overflow: auto; padding-bottom:5px; position: relative;">
		<input type="hidden" name="ownerId" id="ownerId">
		
		<fieldset style="padding:10px;margin-bottom:5px;border:solid 1px #328EE0;">
			<legend style="font-weight:bold;color:black;">掌柜信息</legend>
			<table align="" cellspacing="4" class="text03">
				<tr>
					<td height="5" align="right"></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td   class="textr" height="5"> 用户昵称：</td>
					<td>
						<span id="nickNameText"></span>
					</td>
					<td class="textr">&nbsp;&nbsp;&nbsp;&nbsp;注册邮箱：</td>
					<td>	
						<span id="email"></span>
					</td>
				</tr>
				<tr>
					<td class="textr">真实姓名：</td>
					<td>
						<span id="trueName"></span>
					</td>
					<td  height="5"  class="textr">&nbsp;&nbsp;&nbsp;&nbsp;性别：</td>
					<td>
						<span id="gender"></span>
						
					</td>
				</tr>
				
				<tr>
					<td class="textr">所在区域：</td>
					<td colspan="3">
						<span id="district"></span>
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset  style="padding:10px;margin-bottom:5px;border:solid 1px #328EE0;">
		 	<legend style="font-weight:bold;color:black;">登记信息</legend>
		 		<table  align="" cellspacing="2" class="text03">
					<tr>
						<td height="10" align="right"></td>
						<td></td>
					</tr>
					<tr>
						<td  width="65"  class="textr" height="5">联系人：</td>
						<td>
							<span id="linkMan"></span>
						</td>
					</tr>
					<tr >
						<td class="textr" height="5" > 联系电话：</td>
						<td>
							<span id="telephoneText"></span>
						</td>
					</tr>
					<tr>
						<td class="textr" height="5" > 营业执照：</td>
						<td id="downOrView">
							
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="dns_up_slide dns_up_slidePic2 preview_fake" style="width: 160px; height: 90px;">
								<img id="img_preview" name="img_preview1" style="width: 160px; height: 90px;" />
							</div>
						</td>
					</tr>
			  </table>	
			  
			  <div id="showOriginalImageHtml" style="display:none;position:absolute;z-index:1;border:10px solid #c4c4c4;background: #fff;"></div>
		</fieldset>
	</div>
	
	<div style="display:block; text-align:center; width:100%; height:50px;">
		
			<a href="#" id="btn-back" onClick="closeWindow();" class="easyui-linkbutton" iconcls="icon-cancel">关闭</a>
		
	</div>
</body>
</html>
