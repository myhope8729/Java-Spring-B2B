<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var itemGridData = ${gridModel};
	var afterSavingUrl = "<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>";
	var invalidNumber="<qc:message code='system.invalid.number' />"
	var wrongPrice="<qc:message code='system.common.validate.wrong.price' />";
	var inputPrice="<qc:message code='system.input.price' />";
	var posValPrice="<qc:message code='system.input.wrong.price' />";
</script>

<style>
	.ui-jqgrid td .chPrice{width:50%;text-align:center;margin-right:10px;font-size:12px;}
	.ui-jqgrid td .oldPrice{background-color:transparent;border-color:transparent;font-size:12px;}
</style>

<h3 class="page_title">调价单详细</h3>

<div class="admin_body">
	<table class="table table-bordered dataTable01">
		<colgroup>
			<col width="14%" />
			<col width="20%" />
			<col width="13%" />
			<col width="20%" />
			<col width="13%" />
			<col width="20%" />
 		</colgroup>
		<tbody>
			<c:if test="${not empty rBillHead}">
				<tr>
					<th>入库单编号</th>
					<td>${rBillHead.billId}</td>
					<th>入库日期</th>
					<td>${rBillHead.arriveDate}</td>
					<th>备注</th>
					<td>${rBillHead.note}</td>
				</tr>
				<tr>
					<th>供货方</th>
					<td>${rBillHead.hostUserName}</td>
					<th>供货方联系人</th>
					<td>${rBillHead.hostContactName}</td>
					<th>供货方电话</th>
					<td>${rBillHead.hostTelNo}&nbsp;&nbsp;${rBillHead.hostMobileNo}</td>
				</tr>
			</c:if>
			
			<tr>
				<th>调价单编号</th>
				<td>${billProc.billHead.billId}</td>
				<th>制单人</th>
				<td>${billProc.billHead.inputorName}</td>
				<th>调价说明</th>
				<td>${billProc.billHead.custUserName}</td>
			</tr>
		</tbody>
	</table>
	
	<form class="form-horizontal admin-form" id="billProcPriceForm" name="billProcPriceForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
		<input type="hidden" id="distributeYn" name="distributeYn" value="${billProc.distributeYn}" />
		<input type="hidden" id="priceCols" name="priceCols" value=""/>
		<input type="hidden" id="saveFlag" name="saveFlag" />
		
		<div class="action_bar row">
			<div class="col-xs-12 text-right">
				<c:if test="${billProc.priceYn eq 'Y'}">
					<button type="button" class="btn btn-primary" onclick="saveBillProc('Y');"><qc:message code="system.common.btn.save"/></button>
				</c:if>
				<button type="button" class="btn btn-primary mgl10" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
		
		<div class="action_bar">
			<table id="gridItem"></table>
		</div>
		<div class="action_bar">
			<table id="gridProc"></table>
		</div>
	</form>
</div>