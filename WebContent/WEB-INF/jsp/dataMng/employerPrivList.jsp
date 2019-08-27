<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	
</script>
<div class="admin_body">
	
	<h3 class="page_title">${pageTitle}</h3>
	
	<form id="employerListFrm" class="searchForm">
		<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
	</form>
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("User", "employerPrivGridAjax") %>" searchForm="#employerListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>