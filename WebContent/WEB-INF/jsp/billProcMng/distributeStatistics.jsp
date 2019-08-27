<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var gridData = ${gridModel};
</script>

<h3 class="page_title">拣货单</h3>

<div class="admin_body">
	<form id="distributeStatistic" name="distributeStatistic" onsubmit="reloadGrid();return false;" class="form-inline">
		<input type="hidden" id="createDateFrom1" name="createDateFrom1" value="${sc.createDateFrom1}" />
		<input type="hidden" id="createDateTo1" name="createDateTo1" value="${sc.createDateTo1}" />
		<input type="hidden" id="itemColNo" name="itemColNo" value="${sc.itemColNo}" />
		<input type="hidden" id="itemColName" name="itemColName" value="${sc.itemColName}" />
		
		<div class="action_bar row">
			<div class="col-xs-3">
				<label class="control-label mgt10"><strong>订单日期 : ${sc.createDateFrom1} 至 ${sc.createDateTo1}</strong></label>
			</div>
			<div class="col-xs-3">
				<label class="control-label">拣货组</label>
				<qc:htmlSelect items="${distributeList}" itemValue="seqNo" itemLabel="bizDataName" selValue='${sc.distributeSeqNo}'
					cssClass="form-control" id="distributeSeqNo" name="distributeSeqNo" customAttr='onchange="reloadGrid();"' />
			</div>
			<div class="col-xs-6 text-right">
				<a target="_blank" class="btn btn-primary" onclick='printForm()'><qc:message code="system.common.btn.print"/></a>
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
		
		<div class="clear">
			<table id="grid"></table>
		</div>
	</form>
</div>