<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">审批组目录</h3>
    <a data-theme="b" data-role="button" data-icon="plus" data-inline="true" class="ui-btn-left" data-iconpos="left" href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowGroupForm&workFlowId=" + request.getParameter("workFlowId")) %>"> 
    	<qc:message code="system.common.btn.new" /> 
    </a>     
    <a data-theme="b" data-role="button" data-icon="back" class="ui-btn-right" data-iconpos="notext" href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowList") %>"> 
    	<qc:message code="system.common.btn.back" /> 
    </a>         
</div>
	
<div role="main" class="ui-content">
	
	<form id="workFlowGroupSearchForm" name="workFlowGroupSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlow.workFlowId}" />
	</form>
	
	<div class="action_bar row mgt10 alignC">
		<label class="control-label">类型 :序号:名称 - ${workFlow.workFlowTypeName}:${workFlow.seqNo}:${workFlow.workFlowName}</label>
	</div>
	
	<table id="grid"></table>
	<div id="gridpager"></div>
</div>
