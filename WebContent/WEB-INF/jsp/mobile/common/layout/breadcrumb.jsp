<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>


     <!-- Breadcrumb Content -->
     <ol class="breadcrumb">
		<li><a href="Main.do?cmd=main"><i class="fa fa-home"></i></a></li>
     <!-- END Breadcrumb Content -->
		<c:forEach var="breadcrumb" items="${breadcrumb}" varStatus="status">
			<li><a href="${breadcrumb.breadLink}">${breadcrumb.breadName}</a></li> 
		</c:forEach>
	</ol>
