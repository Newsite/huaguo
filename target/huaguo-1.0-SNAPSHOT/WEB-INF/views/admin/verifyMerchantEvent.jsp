<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/page/admin/jqueryeasy.jsp"%>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body>
<div style="overflow: auto; padding-bottom: 5px; position: relative;">
  <input type="hidden" name="recordId" id="recordId">
  <fieldset style="padding:10px;margin-bottom:5px;border:solid 1px #328EE0;">
    <legend style="font-weight:bold;color:black;">信息详情</legend>
    <table cellspacing="2" class="text03">
      <tr>
        <td height="5" align="right"></td>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td   class="textr" height="5"> 用户昵称：</td>
        <td><span id="nickNameText"></span></td>
        <td class="textr">&nbsp;&nbsp;&nbsp;&nbsp;注册邮箱：</td>
        <td><span id="email"></span></td>
      </tr>
      <tr>
        <td  height="5"  class="textr"> 提交时间：</td>
        <td><span id="createDate"></span></td>
        <td class="textr">&nbsp;&nbsp;&nbsp;&nbsp;审核状态：</td>
        <td><span id="status"></span></td>
      </tr>
    </table>
  </fieldset>
  <fieldset  style="padding:10px;margin-bottom:5px;border:solid 1px #328EE0;">
    <legend style="font-weight:bold;color:black;">修改内容</legend>
    <table  align="" cellspacing="2" class="text03">
      <tr>
        <td height="10" align="right"></td>
        <td></td>
      </tr>
      <tr id="_merchant"> </tr>
      <tr>
        <td  width="65"  class="textr" height="5">所属菜系：</td>
        <td><span id="merchantStyle"></span></td>
      </tr>
      <tr >
        <td class="textr" height="5" > 所在地区：</td>
        <td><span id="cityName"></span></td>
      </tr>
      <tr>
        <td class="textr" height="5" > 详细地址：</td>
        <td><span id="addressText"></span></td>
      </tr>
      <tr>
        <td class="textr" height="5" > 餐厅简介：</td>
        <td><span id="descriptionText"></span></td>
      </tr>
      <tr id="_merchantEvent" style="margin-top: 5;height: 5px;"> </tr>
    </table>
  </fieldset>
</div>
<div style="display:block; text-align:center; width:100%; height:50px;"> <a href="#" id="btn-verifyMerchant" onClick="verifyMerchantEventSubmmit('1');" class="easyui-linkbutton" iconcls="icon-save">接受申请</a> &nbsp;&nbsp;&nbsp; <a href="#" id="btn-unVerifyMerchant" onClick="verifyMerchantEventSubmmit('2');" class="easyui-linkbutton" iconcls="icon-undo">拒绝申请</a> &nbsp;&nbsp;&nbsp; <a href="#" id="btn-back" onClick="closeWindow();" class="easyui-linkbutton" iconcls="icon-cancel">关闭</a> </div>
</body>
</html>
