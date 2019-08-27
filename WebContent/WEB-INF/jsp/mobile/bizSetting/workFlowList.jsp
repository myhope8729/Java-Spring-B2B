<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">业务流程目录</h3>
    <a data-theme="b" data-role="button" data-icon="plus" data-inline="true" class="ui-btn-right" data-iconpos="left" href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowForm") %>"> 
    	<qc:message code="system.common.btn.new" /> 
    </a>     
</div>
	
<div role="main" class="ui-content">

	<form id="workFlowSearchForm" name="workFlowSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="row">
			<div class="text-left col-md-12">
				<qc:codeList var="workFlowType" codeGroup="BT0000" />
				<qc:htmlSelect items="${workFlowType}" itemValue="codeId" itemLabel="codeName"
					isEmpty="true" emptyLabel='sc.please.biztype.select' id="workFlowType" name="workFlowType"
					customAttr="onchange='reloadGrid();return false;'" selValue='${sc.workFlowType}' />
			</div>
		</div>
	</form>
	
	<div class="table-responsive">	
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>
