<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><tiles:insertAttribute name="title"/></title>
<LINK href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
<LINK href="<c:url value="/css/"/><tiles:insertAttribute name="css"/>.css" type="text/css" rel="stylesheet">

<script type="text/javascript">
var _contextPath    = "<%= request.getContextPath() %>";
</script>

<script type="text/javascript" src="<c:url value="/js/lib/jquery/jquery-1.11.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery/ui/jquery-ui-1.10.4.custom.min.js"/>"></script>
<!-- time entry js -->
<script type="text/javascript" src="<c:url value="/js/common/common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/backoffice/boCommon.js"/>"></script>
<%-- validation js --%>
<script type="text/javascript" src="<c:url value="/js/lib/jquery/validation/jquery.validate.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery/validation/jquery.metadata.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery/validation/messages_ko.js"/>"></script>
<!-- grid js -->
<script type="text/javascript" src="<c:url value="/js/lib/jquery/jqGrid/i18n/grid.locale-cn.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery/jqGrid/jquery.jqGrid.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/jquery/jqGrid/plugins/jquery.tablednd.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common/commonGrid.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/common/custom.jquery.plugin.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/lib/plugins/jquery.blockui.min.js"/>"></script>



<qc:importJS />
</head>

<body>
<div class="ip_wrap">
	<%-- Start Content Area --%>
	<tiles:insertAttribute name="content" />
	<%-- End Content Area --%>
</div>
</body>
</html>
