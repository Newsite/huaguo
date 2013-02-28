<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.touco.huaguo.common.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/page/admin/jqueryeasy.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>花果管理平台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script>  
	function vailLogin(){  		
		var userName = jQuery.trim($('#userName').val());
		var password = jQuery.trim($('#password').val());
		var captcha= jQuery.trim($('#captcha').val());
		if(!userName){
			$.messager.alert('提示','用户名不能为空!','info');
			return false; 
		}
		if(!password){
			$.messager.alert('提示','密码不能为空!','info');
			return false; 
		}		
		if(!captcha){
			$.messager.alert('提示','验证码不能为空!','info');
			return false; 
		}
		return true;
	}
	
</script>
</head>

<body id="SignIn">
<%
	Cookie[] cookies = request.getCookies();
	String uname = "";
	String pwd = "";
	String userCust = "";
	boolean ischk = false;
	if (cookies != null) {
		for(Cookie cookie : cookies){
			if(Constants.COOKIE_SERVER_UNAME.equals(cookie.getName())){
				uname = cookie.getValue();
			}
			else if(Constants.COOKIE_SERVER_UPWD.equals(cookie.getName())){
				pwd = cookie.getValue();
			}
			
		}
	}
	
	if(StringUtils.isNotBlank(uname) && StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(userCust)){
		ischk = true;
	}
	
	request.setAttribute("ischk", ischk);
%>
<div id="loginbody">
  <div id="loginwrap">
    <div id="loginmain">
      <div class="Alerts">
        <h3 class="Note">注意：</h3>
        <ul class="Matter">
          <li>1.不要在公共场合保存信息。</li>
          <li>2.尽量避免多人使用同一账号登录。</li>
          <li>3.为保证你的账号安全，退出系统时请注销。</li>
        </ul>
      </div>
      <div class="LoginForm">
        <div class="MessageText"> ${message} </div>
        <form action="${ctx}/adminLogin" method="post" id="loginForm" onsubmit="return vailLogin();">
          <div class="int">
            <label for="userName">用户名:</label>
            <input name="userName" type="text" class="text_frmae" id="userName"  required="true" validType="length[4,20]" value="<%=uname%>">
          </div>
          <div class="int">
            <label for="password">密码:</label>
            <input name="password" type="password" id="password"   class="text_frmae" required="true" validType="length[4,20]" value="<%=pwd%>">
          </div>
          <div class="int yanzheng">
            <label for="Verification">验证码:</label>
            <input type="text" id="captcha" name="captcha" maxlength="4" class="code_frame" required="true">
            <span> <img src="${ctx}/captcha-image" class="code_validate" id="captchaImage" style="cursor: pointer" onClick="document.getElementById('captchaImage').src='${ctx}/captcha-image?update='+Math.random();"/> 
            </span>
            </div>
          <div class="int Save">
            <label for="">&nbsp;</label>
            <c:if test="${ischk==true}">
              <input name="isSave" type="checkbox" checked id="chkSaveUinfo">
            </c:if>
            <c:if test="${ischk==false}">
              <input name="isSave" type="checkbox" id="chkSaveUinfo">
            </c:if>
            <strong>保存信息</strong> </div>
          <div class="sub">
            <input type="submit"  value="登录" id="send" >
            <input type="reset"   value="重置" id="rend">
          </div>
        </form>
      </div>
    </div>
  </div>
  <div id="loginfooter" >
    <p>Copyright © 2012 huaguo.com Touco WuXi. All Rights Reserved.</p>
  </div>
</div>
</body>
</html>
