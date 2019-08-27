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
			<div class="col-md-12 text-right">
				<button type="button" class="btn btn-primary" id="btnPrint"><qc:message code="system.common.btn.print"/></button>
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
		
		<div class="form-group">
			订单日期 : ${sc.createDateFrom} 至 ${sc.createDateTo}
		</div>
	</form>
	
	<div class="clear">
		<table id="grid"></table>
	</div>
</div>