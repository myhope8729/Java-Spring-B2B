<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	
</script>
<div class="admin_body">
	
	<h3 class="page_title">${pageTitle}
		<div class="action_bar text-right">
			<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("User", "employeeForm") %>"><qc:message code="system.common.btn.new"/></a>
		</div>
	</h3>
	
	<form id="employerListFrm" class="searchForm">
		<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
	</form>
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("User", "employeeGridAjax") %>" searchForm="#employerListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>