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

<script type="text/javascript" src="${ctx}/static/scripts/easyui1.2.4/jquery-1.6.min.js"></script>

<!-- easyUI插件 -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/scripts/easyui1.2.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/scripts/easyui1.2.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/admin/content.css" />
<script type="text/javascript" src="${ctx}/static/scripts/easyui1.2.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/easyui1.2.4/ext/validator.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/easyui1.2.4/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${ctx}/static/scripts/common/util/GeneralUtil.js"></script>
<script type="text/javascript" src="${ctx}/static/scripts/common/util/popwindow.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
	var fileSys = '${fileSys}';
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
