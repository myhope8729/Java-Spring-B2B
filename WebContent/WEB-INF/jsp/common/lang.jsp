<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// String lang="ko";
    String lang= "";
	if(session == null)
	lang="en";
else
	 lang = (String)session.getAttribute("lang");

if(lang == null)
		lang = "en";
%>