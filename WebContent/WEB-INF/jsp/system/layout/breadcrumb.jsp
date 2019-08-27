<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<div class="bread_wrapper">
	<div class="breads">
		当前位置 : 
		<c:set var="ind" value="1"></c:set>
		<c:forEach var="breadcrumb" items="${breadcrumb}">
			<c:if test="${ind == 0}">&nbsp;&gt;</c:if>
			<c:if test="${ind == 1}">
				<c:set var="ind" value="0"></c:set>
			</c:if>
			<c:if test="${not empty breadcrumb.breadLink}">
				<a href="${breadcrumb.breadLink}">${breadcrumb.breadName}</a>
			</c:if>
			<c:if test="${empty breadcrumb.breadLink}">
				${breadcrumb.breadName}
			</c:if>
		</c:forEach>
	</div>
	<div class="welcome alignR">
		<ul>
			<li>欢迎您！&nbsp;${loginUser.userName}&nbsp;${loginUser.empName}&nbsp;${loginUser.empNo}</li>
		</ul>
	</div>
</div>
