<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var itemGridData = ${gridModel};
</script>

<h3 class="page_title">订货单明细
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
				<th>订货单编号</th>
				<td>
					<c:if test="${not empty billProc.billHead.bnoUser}">${billProc.billHead.bnoUser}</c:if>
					<c:if test="${empty billProc.billHead.bnoUser}">${billProc.billId}</c:if>
				</td>
				<th>单据状态</th>
				<td colspan="3">${billProc.billHead.stateName}</td>
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
				<th>订货方</th>
				<td>${billProc.billHead.custUserName}</td>
				<th>订货人</th>
				<td>${billProc.billHead.custContactName}</td>
				<th>订货人电话</th>
				<td>${billProc.billHead.custTelNo}&nbsp;&nbsp;${billProc.billHead.custMobileNo}</td>
			</tr>
			<tr>
				<th>收货地址</th>
				<td>${billProc.billHead.addrLocationName}</td>
				<th>收货人</th>
				<td>${billProc.billHead.addrContactName}</td>
				<th>收货人电话</th>
				<td>${billProc.billHead.addrTelNo}&nbsp;&nbsp;${billProc.billHead.addrMobile}</td>
			</tr>
			<tr>
				<th>付款方式/类别</th>
				<td>
					${billProc.billHead.paytypeName}
					<c:if test="${not empty billProc.billHead.paymentType}"> / ${billProc.billHead.paymentType}</c:if>
				</td>
				<th>送货日期</th>
				<td>${billProc.billHead.arriveDate}</td>
				<th>备注</th>
				<td>${billProc.billHead.note}</td>
			</tr>
			<tr>
				<th>商品金额(元)</th>
				<td>
					<c:if test="${billProc.lineTotal1 == billProc.lineTotal2}">${billProc.lineTotal2}</c:if>
					<c:if test="${billProc.lineTotal1 != billProc.lineTotal2}">
						<font color='#808080'>(${billProc.lineTotal1})</font> ${billProc.lineTotal2}
					</c:if>
				</td>
				<th>运费</th>
				<td>0</td>
				<th>合计金额(元)</th>
				<td>
					<c:if test="${billProc.lineTotal1 == billProc.lineTotal2}">${billProc.lineTotal2}</c:if>
					<c:if test="${billProc.lineTotal1 != billProc.lineTotal2}">
						<font color='#808080'>(${billProc.lineTotal1})</font> ${billProc.lineTotal2}
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	
	<form class="form-inline admin-form" id="billProcOrderCheckedForm" name="billProcOrderCheckedForm" role="form">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
	</form>
	
	<table id="gridItem"></table>
	<table id="gridProc"></table>
	
</div>