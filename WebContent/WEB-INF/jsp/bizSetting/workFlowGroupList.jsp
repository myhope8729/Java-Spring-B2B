<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin_body">
	<h3 class="page_title">审批组目录</h3>
	<form id="workFlowGroupSearchForm" name="workFlowGroupSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlow.workFlowId}" />
	</form>
	
	<div class="action_bar row">
		<label class="col-lg-6 control-label">业务类型 = ${workFlow.workFlowTypeName} ;&nbsp;&nbsp;&nbsp;&nbsp;流程序号 = ${workFlow.seqNo} ;&nbsp;&nbsp;&nbsp;&nbsp;流程名称 = ${workFlow.workFlowName}</label>
		<div class="col-lg-6 text-right">
			<a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowGroupForm&workFlowId=" + request.getParameter("workFlowId")) %>" class="btn btn-primary" ><qc:message code="system.common.btn.new" /> </a>
			<a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		</div>
	</div>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>
