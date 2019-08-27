<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
</div>
	
<div role="main" class="ui-content">	
	
	<form id="employerListFrm" class="searchForm">
		<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
	</form>
	
	<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("User", "employeeGridAjax") %>" searchForm="#employerListFrm"></table>
<!-- 	<div id="gridpager"></div> -->

	<div class="form-group form-actions">
 		<a data-theme="b" data-role="button" data-inline="true" class="btn btn-primary btn-fullwidth" data-iconpos="left" href="<%= BaseController.getCmdUrl("User", "employeeForm") %>">
 			<qc:message code="system.common.btn.new" />
 		</a>
	</div>	
		
</div>