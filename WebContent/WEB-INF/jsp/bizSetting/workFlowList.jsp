<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin_body">
	<h3 class="page_title">业务流程目录</h3>
	<form id="workFlowSearchForm" name="workFlowSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar row">
			<div class="text-left col-md-4">
				<qc:codeList var="workFlowType" codeGroup="BT0000" />
				<qc:htmlSelect items="${workFlowType}" itemValue="codeId" itemLabel="codeName"
					isEmpty="true" emptyLabel='sc.please.biztype.select' cssClass="form-control" id="workFlowType" name="workFlowType"
					customAttr="onchange='reloadGrid();return false;'" selValue='${sc.workFlowType}' />
			</div>
			<div class="col-md-8 text-right">
				<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowForm") %>"><qc:message code="system.common.btn.new" /> </a>
			</div>
		</div>
	</form>
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>
