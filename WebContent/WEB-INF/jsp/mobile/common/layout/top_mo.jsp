<%@page import="com.kpc.eos.core.Constants"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript" src="<c:url value="/js/mobile/userPage/qty.js"/>"></script>
<script>
	var ckEosCart = "<%= Constants.COOKIE_KEY_EOS_CART %>";
	var ckEosCartVal = "${ckEosCartVal}";
	var hostUserId = "<%= request.getParameter("hostUserId") == null? request.getParameter("hostId") : request.getParameter("hostUserId") %>" ;
	var isMultiHostMode = "${isMultiHostMode}";
	var messages = {
		add_to_cart_failed : "<qc:message code='up.cart.add.failed' />",
		number_only : "<qc:message code='system.invalid.number' />"
	};
	<c:if test="${not empty sc && not empty sc.page}">
	gridPager = { page : "${sc.page.page}",sortname : "${sc.page.sidx}", sortorder : "${sc.page.sord}",rowNum : "${sc.page.rows}"};
	CommonGrid.defaultOption.page = gridPager.page;
	CommonGrid.defaultOption.sortname = gridPager.sortname;
	CommonGrid.defaultOption.sortorder = gridPager.sortorder;
	CommonGrid.defaultOption.rowNum = gridPager.rowNum;
	</c:if>
	$(document).ready(function(){
		if (ckEosCartVal != "") {
			setCookie(ckEosCart, ckEosCartVal, 3);
		}
	});
</script>

<jsp:include page="topInner.jsp" />