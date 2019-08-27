<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><tiles:insertAttribute name="title"/></title>
<%@ include file="/WEB-INF/jsp/mobile/common/commonHead_empty_mo.jspf" %>
<LINK href="<c:url value="/css/"/><tiles:insertAttribute name="css"/>.css" type="text/css" rel="stylesheet">
</head>

<body>
<div id="page" data-role="page" data-theme="a" >
	<%-- Start GNB Area --%>
	<tiles:insertAttribute name="top"/>
	<%-- End GNB Area --%>

	<%-- Start Content Area --%>
	<tiles:insertAttribute name="content" />
	<%-- End Content Area --%>
	
</div>
<%-- Start Footer Area --%>
<tiles:insertAttribute name="bottom" />
<%-- End Footer Area --%>
</body>
</html>
