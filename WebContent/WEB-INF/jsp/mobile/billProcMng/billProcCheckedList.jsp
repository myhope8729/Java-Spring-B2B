<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var selRowMsg = "<qc:message code='system.common.empty.delete' />";
	var delSuccessMsg = "<qc:message code='syste.common.success.delete' />";
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">已处理单据</h3>
</div>
	
<div role="main" class="ui-content">
	<!-- search section -->
	<form id="billProcCheckedSearchForm" name="billProcCheckedSearchForm" onsubmit="reloadGrid();return false;" class="form-horizontal"> 
		<input type="hidden" id="userId" name="userId" value="${sc.userId}" />
		<div class="action-bar">
			<div class="form-group">
				<label for="billType" class="control-label col-xs-4 text-right">单据类型</label>
				<div class="col-xs-8">
					<qc:codeList var="billType" codeGroup="DT0000" />
					<qc:htmlSelect items="${billType}" itemValue="codeId" itemLabel="codeName" selValue='${sc.billType }'
					isEmpty="true" emptyLabel="billproc.billtype.select" cssClass="" id="billType" name="billType"/>
				</div>
			</div>
			<div class="form-group">
				<label for="billId" class="control-label col-xs-4 text-right">单据编号</label>
				<div class="col-xs-8">
					<input type="text" data-clear-btn="true" class="form-control" id="billId" name="billId" value="${sc.billId}"/>
				</div>
			</div>
			<div class="form-group">
				<label for="createDateFrom" class="control-label col-xs-4 text-right">业务日期(起)</label>
				<div class="col-xs-8">
					<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
						<input type="text" data-clear-btn="true" id="createDateFrom" name="createDateFrom" value="${sc.createDateFrom}" >
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="createDateTo" class="control-label col-xs-4 text-right">业务日期(止)</label>
				<div class="col-xs-8">
					<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
						<input type="text" data-clear-btn="true" id="createDateTo" name="createDateTo" value="${sc.createDateTo}" >
					</div>
				</div>
			</div>	
			<div class="form-group">
				<label for="compName" class="control-label col-xs-4 text-right">订(供)货方</label>
				<div class="col-xs-8">
					<input type="text" data-clear-btn="true" class="form-control" id="compName" name="compName" value="${sc.compName}"/>
				</div>
			</div>
		</div>
		
		<div class="form-group form-actions">
			<div class="col-xs-5 col-xs-offset-2">
				<button type="submit" data-mini="true" class="btn btn-primary"  data-theme="b" ><qc:message code="system.common.btn.search"/></button>
			</div>
			<div class="col-xs-5">
				<button type="button" data-mini="true" class="btnReset btn btn-primary" data-theme="b"  id="reset"><qc:message code="system.common.btn.reset" /></button>
			</div>
		</div>
	</form>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>	
</div>
