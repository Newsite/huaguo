<%@page import="com.touco.huaguo.common.Constants"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath();%>
<c:set var="ctx" value="<%=path%>" />
<%
	String fileSys = Constants.DEFAULT_FILE_SYS;
	if(session.getAttribute(Constants.FILE_SYS_DOMAIN) != null){
		fileSys = (String)session.getAttribute(Constants.FILE_SYS_DOMAIN);
	}
%>

<c:set var="fileSys" value="<%=fileSys%>" />

<script type="text/javascript" src="${ctx}/static/scripts/common/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/general.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/home.css" />
<script type="text/javascript" src="${ctx}/static/scripts/common/util/GeneralUtil.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/jQuery-plugin/jquery.placeholder.js"></script>

<script type="text/javascript">
	var ctx = '${ctx}';
	var fileSys = '${fileSys}';
</script>
<script type="text/javascript">
    document.createElement('header');
    document.createElement('nav');
    document.createElement('article');
    document.createElement('footer');
	document.createElement('aside');
	document.createElement('figcaption');
	document.createElement('figure');
	document.createElement('hgroup');
	document.createElement('section');
	document.createElement('details');
 </script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="icon" href="${ctx}/static/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/static/favicon.ico" type="image/x-icon" />