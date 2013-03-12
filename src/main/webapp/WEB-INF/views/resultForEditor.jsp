<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.*" %>
<%
	Object error = request.getAttribute("error");
	Object url = request.getAttribute("url");
	JSONObject obj = new JSONObject();
	obj.put("error", error);
	System.out.println(url);
	obj.put("url", url);
	out.println(obj.toJSONString());
%>
