<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var detailList = ${detailList};
	var billTotalAmt = '' || '${billProc.billHead.total2}';
	var afterSavingUrl = "<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>";
	var invalidNumber="<qc:message code='system.invalid.number' />"
	var wrongPrice="<qc:message code='system.common.validate.wrong.price' />";
	var inputPrice="<qc:message code='system.input.price' />";
	var posValPrice="<qc:message code='system.input.wrong.price' />";
	var emptyNote="<qc:message code='billproc.reject.input.note' />";
	var confirmReject="<qc:message code='billproc.reject.confirm' />";
</script>

<h3 class="page_title">货款单详细</h3>

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
			<tr>
				<th>制单人</th>
				<td>${billProc.billHead.inputorName}</td>
				<th>备注</th>
				<td colspan='3'>${billProc.billHead.note}</td>
			</tr>
			<tr>
				<th>付款方</th>
				<td>${billProc.billHead.custUserName}</td>
				<th>付款方联系人</th>
				<td>${billProc.billHead.custContactName}</td>
				<th>付款方电话</th>
				<td>${billProc.billHead.custTelNo}&nbsp;&nbsp;${billProc.billHead.custMobileNo}</td>
			</tr>
			<tr>
				<th>预付款名称</th>
				<td>${billProc.billHead.paytypeName}</td>
				<th>收款金额(元)</th>
				<td>${billProc.billHead.total2}</td>
				<th>到账日期</th>
				<td>${billProc.billHead.arriveDate}</td>
			</tr>
		</tbody>
	</table>
	
	<form class="form-inline admin-form" id="billProcPaymentForm" name="billProcPriceForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
		
		<div class="action_bar row">
			<div class="col-sm-4 col-xs-12">
				<label class="control-label"><span class="required">*</span>金额(元)</label>
				<c:if test="${billProc.priceYn == 'Y'}">
					<input type="text" class="form-control mgl20" name="totalAmt" id="totalAmt" value="${billProc.billHead.total2}" validate="required: true, number:true" />
					<qc:errors items="${formErrors}" path="totalAmt" />
				</c:if>
				<c:if test="${billProc.priceYn != 'Y'}">
					<input type="text" class="form-control mgl20" name="totalAmt" id="totalAmt" readonly value="${billProc.billHead.total2}" validate="required: true, number:true" />
				</c:if>
			</div>
			<div class="col-sm-4 col-xs-12">
				<label for="procNote" class="control-label">处理意见</label>
				<input type="text" class="form-control mgl20" id="procNote" name="procNote"/>
			</div>
			<div class="col-sm-4 col-xs-12 text-right">
				<c:if test="${billProc.priceYn eq 'Y'}">
					<button type="button" class="btn btn-primary" onclick="saveBillProc('Y');"><qc:message code="system.common.btn.save"/></button>
				</c:if>
				<button type="button" class="btn btn-primary mgl10" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>
				<button type="button" class="btn btn-primary mgl10" onclick="rejectBillProc();"><qc:message code="system.common.btn.return" /></button>
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
		
		<table id="paymentGrid"></table>
		<table id="gridProc"></table>
	</form>
</div>