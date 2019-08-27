<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">部门资料</h3>
</div>
	
<div role="main" class="ui-content">	

	<form name="departmentListFrm" id="departmentListFrm" onsubmit="reloadGrid();return false;">
		<input id="usr0" name="usr0" value="" type="hidden"/>
	</form>
	
	<table id="grid"></table>
	
	<div class="form-group form-actions">
 		<a data-theme="b" data-role="button" class="btn btn-primary" data-iconpos="left" href="<%= BaseController.getCmdUrl("Dept", "departmentForm") %>">
 			<qc:message code="system.common.btn.new" />
 		</a>
	</div>	
</div>