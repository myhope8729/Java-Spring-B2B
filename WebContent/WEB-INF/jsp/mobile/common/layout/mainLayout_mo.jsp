<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<!DOCTYPE html>
<html>
<head>
	<title><tiles:insertAttribute name="title"/></title>
    <%@ include file="/WEB-INF/jsp/mobile/common/commonHead_mo.jspf" %>
	<LINK href="<c:url value="/css/"/><tiles:insertAttribute name="css"/>.css" type="text/css" rel="stylesheet">
</head>
<body>
		<%-- Start GNB Area --%>
		<tiles:insertAttribute name="top"/>
		<%-- End GNB Area --%>
		
		<%-- Start BreadCrumb Area --%>
		<tiles:insertAttribute name="breadcrumb" />
		<%-- End BreadCrumb Area --%>			
		
		<!-- <div class="page">	 -->
		<div class="content-wrapper">
		<tiles:insertAttribute name="content" />
		</div>
		
		<tiles:insertAttribute name="footer" />
			<qc:importJS />
			
		<!-- </div> -->
		<%-- Start Footer Area --%>
		<tiles:insertAttribute name="bottom" />
			<%-- End Footer Area --%>
			
		<%-- Start LNB Area --%>
		<tiles:insertAttribute name="left"/>
		<%-- End LNB Area --%>       
</body>
</html>
