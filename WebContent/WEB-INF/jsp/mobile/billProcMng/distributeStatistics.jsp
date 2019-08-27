<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var gridData = ${gridModel};
</script>

<h3 class="page_title">拣货单统计</h3>

<div class="admin_body">
	<form id="distributeStatistic" name="distributeStatistic" onsubmit="reloadGrid();return false;" class="form-inline">
		<input type="hidden" id="createDateFrom" name="createDateFrom" value="${sc.createDateFrom}" />
		<input type="hidden" id="createDateTo" name="createDateTo" value="${sc.createDateTo}" />
		<input type="hidden" id="itemColNo" name="itemColNo" value="${sc.itemColNo}" />
		<input type="hidden" id="itemColName" name="itemColName" value="${sc.itemColName}" />
		
		<div class="action_bar row">
			<div class="col-md-3">
				订单日期 : ${sc.createDateFrom} 至 ${sc.createDateTo}
			</div>
			<div class="col-md-3">
				<label class="control-label">拣货组</label>
				<qc:htmlSelect items="${distributeList}" itemValue="seqNo" itemLabel="bizDataName" selValue='${sc.distributeSeqNo}'
					cssClass="form-control" name="distributeSeqNo" customAttr='onchange="reloadGrid();"' />
			</div>
			<div class="col-md-6 text-right">
				<button type="button" class="btn btn-primary" id="btnPrint"><qc:message code="system.common.btn.print"/></button>
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
		
		<div class="clear">
			<table id="grid"></table>
		</div>
	</form>
</div>