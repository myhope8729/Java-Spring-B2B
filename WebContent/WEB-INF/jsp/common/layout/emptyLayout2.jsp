<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><tiles:insertAttribute name="title"/></title>
<LINK href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
<LINK href="<c:url value="/css/"/><tiles:insertAttribute name="css"/>.css" type="text/css" rel="stylesheet">
<%@ include file="/WEB-INF/jsp/common/commonHead.jspf" %>
</head>

<body>
<div id="errorDiv" style="display:none;"><ul></ul></div>
<div class="ip_wrap" style="width: 700px;">

	<%-- Start GNB Area --%>
	<%-- <tiles:insertAttribute name="top"/> --%>
	<%-- End GNB Area --%>

	<%-- Start Content Area --%>
	<tiles:insertAttribute name="content" />
	<%-- End Content Area --%>
	
</div>
<%-- Start Footer Area --%>
<%-- <tiles:insertAttribute name="bottom" /> --%>
<%-- End Footer Area --%>
</body>
</html>
