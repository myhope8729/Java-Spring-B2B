<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	<form name="departmentListFrm" id="departmentListFrm" onsubmit="reloadGrid1();return false;">
		<input id="usr0" name="usr0" value="" type="hidden"/>
	</form>
	<h3 class="page_title">部门资料
		<div class="action_bar text-right">
			<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("Dept", "departmentForm") %>"><qc:message code="system.common.btn.new"/></a>
		</div>
	</h3>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
	<!-- list end -->
</div>