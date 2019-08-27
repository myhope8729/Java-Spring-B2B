<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var gridData = ${gridModel};
	var afterSavingUrl = "<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>";
</script>

<h3 class="page_title">拣货确认</h3>

<div class="admin_body">
	<form class="form-inline" id="distributeConfirm" name="distributeConfirm" role="form" onsubmit="reloadGrid();return false;" method="POST">
		<input type="hidden" id="createDateFrom1" name="createDateFrom1" value="${sc.createDateFrom1}" />
		<input type="hidden" id="createDateTo1" name="createDateTo1" value="${sc.createDateTo1}" />
		<input type="hidden" id="userData" name="userData" />
		
		<div class="action_bar row">
			<div class="col-xs-3">
				<label class="control-label mgt10"><strong>制单日期 : ${sc.createDateFrom1} 至 ${sc.createDateTo1}</strong></label>
			</div>
			<div class="col-xs-6">
				<label for="itemNo" class="control-label mgl20">关键字</label>
			 	<input type="text" class="form-control" id="itemNo" name="itemNo" value="${sc.itemNo}" autofocus />
			</div>
			<div class="col-xs-3 text-right">
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
		<div class="clear">
			<table id="gridReceipt"></table>
		</div>
	</form>
	<div class="table-responsive">
		<table id="gridItem"></table>
	</div>
	<div class="action_bar row">
		<div class="col-xs-12 text-right">
			<button type="button" class="btn btn-primary" onclick="saveBillPrice();"><qc:message code="system.common.btn.submit"/></button>
		</div>
	</div>
</div>