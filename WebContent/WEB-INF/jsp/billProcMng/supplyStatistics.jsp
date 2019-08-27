<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page import="com.kpc.eos.core.util.SessionUtil"%>

<script>
	var gridData = ${gridModel};
</script>

<h3 class="page_title">供货统计</h3>

<div class="admin_body">
	<form id="orderStatistic" name="orderStatistic" onsubmit="reloadGrid();return false;" class="form-inline">
		<input type="hidden" id="createDateFrom1" name="createDateFrom1" value="${sc.createDateFrom1}" />
		<input type="hidden" id="createDateTo1" name="createDateTo1" value="${sc.createDateTo1}" />
		
		<div class="action_bar row">
			<div class="col-xs-4">
				<label class="control-label mgt10"><strong>订单日期 : ${sc.createDateFrom1} 至 ${sc.createDateTo1}</strong></label>
			</div>
			<div class="col-xs-8 text-right">
				<a target="_blank" class="btn btn-primary" href='<%= BaseController.getCmdUrl("BillProc", "supplyStatisticPrintForm") %>&createDateFrom1=${sc.createDateFrom1}&createDateTo1=${sc.createDateTo1}'><qc:message code="system.common.btn.print"/></a>
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
		
	</form>
	
	<div class="clear">
		<table id="grid"></table>
	</div>
</div>