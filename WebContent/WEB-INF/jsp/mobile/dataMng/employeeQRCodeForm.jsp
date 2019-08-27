<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div data-role="header" data-theme="b" >
   <h3 class="page_title">${pageTitle}</h3>
   <a href="javascript:window.close();" data-role="button" data-icon="delete" data-iconpos="left" data-theme="b" class="ui-btn-right" id="btnBack"><qc:message code="system.common.btn.close" /></a>
</div>

<div role="main" class="ui-content mgt10">	
	
	<form class="admin-form" action="<%= BaseController.getCmdUrl("User", "saveEmployee") %>" id="userForm" method="post">
		<input type="hidden" name="empId" value="${emp.empId}" />		
		<div class="form-group col-md-12 alignC">
			<label class="control-label">${emp.empName} (${emp.empNo})</label>
		</div>
	</form>
</div>