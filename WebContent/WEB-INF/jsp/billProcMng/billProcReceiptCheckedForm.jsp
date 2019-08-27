<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var itemGridData = ${gridModel};
</script>

<h3 class="page_title">入库单明细
	<div class="action_bar text-right">
		<a class="btn btn-default" href="javascript:window.close();"><qc:message code="system.common.btn.close"/></a>
	</div>
</h3>

<div class="admin_body">
	<table class="table table-bordered dataTable01">
		<colgroup>
			<col width="15%" />
			<col width="25%" />
			<col width="10%" />
			<col width="20%" />
			<col width="10%" />
			<col width="20%" />
 		</colgroup>
		<tbody>
			<tr>
				<th>单据编号</th>
				<td>${billProc.billId}</td>
				<th>入库日期</th>
				<td>${billProc.billHead.arriveDate}</td>
				<th>仓库</th>
				<td>${billProc.billHead.storeName}</td>
			</tr>
			<tr>
				<th>付款方式</th>
				<td>${billProc.billHead.paytypeName}</td>
				<th>制单</th>
				<td>${billProc.billHead.inputorName}</td>
				<th>单据状态</th>
				<td>${billProc.billHead.stateName}</td>
			</tr>
			<tr>
				<th>供货方</th>
				<td>${billProc.billHead.hostUserName}</td>
				<th>供货方联系人</th>
				<td>${billProc.billHead.hostContactName}</td>
				<th>供货方电话</th>
				<td>${billProc.billHead.hostTelNo}&nbsp;&nbsp;${billProc.billHead.hostMobileNo}</td>
			</tr>
			<tr>
				<th>单据金额(元)</th>
				<td>
					<c:if test="${billProc.billHead.itemAmt == billProc.billHead.itemamount2}">${billProc.billHead.itemamount2}</c:if>
					<c:if test="${billProc.billHead.itemAmt != billProc.billHead.itemamount2}">
						<font color='#808080'>(${billProc.billHead.itemAmt})</font> ${billProc.billHead.itemamount2}
					</c:if>
				</td>
				<th>备注</th>
				<td colspan="3">${billProc.billHead.note}</td>
			</tr>
		</tbody>
	</table>
	
	<form class="form-inline admin-form" id="billProcReceiptCheckedForm" name="billProcReceiptCheckedForm" role="form">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
	</form>
	
	<table id="gridItem"></table>
	<table id="gridProc"></table>
	
</div>