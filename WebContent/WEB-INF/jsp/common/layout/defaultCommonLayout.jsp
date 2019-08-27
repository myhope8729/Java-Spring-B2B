<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.kpc.eos.core.Constants" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Sample System
</title>
<LINK href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
<LINK href="<c:url value="/css/admin_${fn:toUpperCase(SYSTEMNAME)}"/>.css" type="text/css" rel="stylesheet">
<%@ include file="/WEB-INF/jsp/common/commonHead.jspf" %>
<script type="text/javascript">
//메뉴 height 조절
$(window).load(function () {
	$(".lmenu").height($("#content").height() < 500 ? 500 : $("#content").height());
	if ($.cookie("leftMenuStatus") == "closed")
		toggleLeftMenu();
});
/*
$(window).bind('resize', function() {
	var grid = $('.ui-jqgrid-btable:visible');
       grid.each(function(index) {
           gridId = $(this).attr('id');
           gridParentWidth = $('#gbox_' + gridId).parent().width();
           $('#' + gridId).setGridWidth(gridParentWidth, true);
       });
}).trigger('resize');
*/
</script>
</head>

<body>
<div id="errorDiv" style="display:none;"><ul></ul></div>
<div class="wrapper">

	<%-- Start GNB Area --%>
	<c:set var="topPage" value="/WEB-INF/jsp/backoffice/layout/top.jsp"/>
	<c:import url="${topPage}"/>
	<%-- End GNB Area --%>

	<table cellpadding="0" cellspacing="0" class="adminLayout">
		<tr>
		
			<%-- Start LNB Area --%>
			<tiles:insertAttribute name="left" />
			<%-- End LNB Area --%>
				
			<td class="contWrap">
				<div id="content">
					<dl class="navi">
						<dt>
						<a href="Main.do?cmd=main" class="home">Sample System</a>
						&gt; <a href="${currTopMenu.connUrl}" class="depth1">${currTopMenu.menuName}</a>
						&gt; <c:forEach var="subMenu" items="${currTopMenu.subMenus}"><c:if test="${currMenuId == subMenu.menuId}"><a href="${subMenu.connUrl}&mId=${subMenu.menuId}">${subMenu.menuName}</a></c:if></c:forEach>
						</dt>
					</dl>

					<%-- Start Content Area --%>
					<tiles:insertAttribute name="content" />
					<%-- End Content Area --%>

				</div>
			</td>
		</tr>
	</table>

	<%-- Start Footer Area --%>
	<tiles:insertAttribute name="bottom" />
	<%-- End Footer Area --%>
	
</div>
</body>
</html>
