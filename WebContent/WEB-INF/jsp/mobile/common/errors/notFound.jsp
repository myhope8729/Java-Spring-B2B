<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>EOS BackOffice</title>
<LINK href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
</head>

<body>
<div style="width:980px; margin:0 auto;">
	<%@ include file="/WEB-INF/jsp/common/layout/kpcCommonTop.jsp" %>

	<div class="ip_content">
		<div class="error_common">
			<p class="img_txt"><img src="<c:url value="/images/common/error_img_txt.gif"/>" alt="<qc:message code = "system.common.notFoundPage1"/>" /></p>
			<p class="txt"><qc:message code = "system.common.sorry"/><br />
			<qc:message code = "system.common.notFoundPage1"/><br />
			<qc:message code = "system.common.notFoundPage2"/></p>
		</div>
	</div>
</div>
</body>
</html>
