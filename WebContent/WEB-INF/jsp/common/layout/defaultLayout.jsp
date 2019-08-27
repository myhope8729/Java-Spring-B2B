<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %><%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%><!DOCTYPE html>
<html>
<head>
<title><tiles:insertAttribute name="title"/></title>
<%@ include file="/WEB-INF/jsp/common/commonHead.jspf" %>
<LINK href="<c:url value="/css/"/><tiles:insertAttribute name="css"/>.css" type="text/css" rel="stylesheet">
</head>
<body>
<div id="errorDiv" style="display:none;"><ul></ul></div>
<div class="wrapper">

	<%-- Start GNB Area --%>
	<tiles:insertAttribute name="top"/>
	<%-- End GNB Area --%>

	<div class="container_body">
		<div class="min_width">
			<div class="f_clear" id="breadcrumb">
				<%-- Start Breadcrumb Area --%>
				<tiles:insertAttribute name="breadcrumb"/>
				<%-- End Breadcrumb Area --%>
			</div>
			<div class="f_clear" id="bodyarea">
				
				<div id="wrapper">
					<div id="content">
						
						<%-- Start Content Area --%>
						<tiles:insertAttribute name="content" />
						<%-- End Content Area --%>
			
					</div>		
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<%-- Start Footer Area --%>
	<tiles:insertAttribute name="bottom" />
	<%-- End Footer Area --%>
</div>

</body>
</html>
