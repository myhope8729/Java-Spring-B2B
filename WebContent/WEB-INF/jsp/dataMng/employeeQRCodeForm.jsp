<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}
		<div class="action_bar text-right">
			<a href="<%= BaseController.getCmdUrl("User", "employeeList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		</div>
	</h3>
	
	<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("User", "saveEmployee") %>" id="userForm" method="post">
		<input type="hidden" name="empId" value="${emp.empId}" />		
		<div class="form-group">
			<label class="col-lg-4 control-label">${emp.empName} (${emp.empNo})</label>
		</div>
	</form>
</div>