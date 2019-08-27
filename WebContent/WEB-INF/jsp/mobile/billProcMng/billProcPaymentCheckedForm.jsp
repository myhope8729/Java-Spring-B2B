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
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">货款单详细</h3>
    <a data-theme="b" data-role="button" data-icon="delete" data-inline="true" class="ui-btn-right" data-iconpos="left" href="javascript:window.close();"> 
    	<qc:message code="system.common.btn.close" /> 
    </a>
</div>

<div role="main" class="ui-content">
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="false" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3>单据详情</h3>	
		<div>
			<table class="table table-bordered dataTable01">
				<colgroup>
					<col width="35%" />
					<col width="65%" />
		 		</colgroup>
				<tbody>
					<tr>
						<th>单据编号</th>
						<td>${billProc.billId}</td>
					</tr>
					<tr>
						<th>制单人</th>
						<td>${billProc.billHead.inputorName}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td>${billProc.billHead.note}</td>
					</tr>
					<tr>
						<th>付款方</th>
						<td>${billProc.billHead.custUserName}</td>
					</tr>
					<tr>
						<th>付款方联系人</th>
						<td>${billProc.billHead.custContactName}</td>
					</tr>
					<tr>
						<th>付款方电话</th>
						<td>${billProc.billHead.custTelNo}&nbsp;&nbsp;${billProc.billHead.custMobileNo}</td>
					</tr>
					<tr>
						<th>预付款名称</th>
						<td>${billProc.billHead.paytypeName}</td>
					</tr>
					<tr>
						<th>收款金额(元)</th>
						<td>
							<c:if test="${billProc.billHead.total2 == billProc.billHead.totalAmt}">${billProc.billHead.total2}</c:if>
							<c:if test="${billProc.billHead.total2 != billProc.billHead.totalAmt}">
								<font color='#808080'>(${billProc.billHead.totalAmt})</font> ${billProc.billHead.total2}
							</c:if>
						</td>
					</tr>
					<tr>
						<th>到账日期</th>
						<td>${billProc.billHead.arriveDate}</td>
					</tr>
				</tbody>
			</table>				
		</div>	
	</div>
	
	<form class="form-horizontal admin-form" id="billProcPaymentForm" name="billProcPriceForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
	</form>
	
	<div class="clear">
		<table id="paymentGrid"></table>
	</div>
			
	<div class="clear">
		<table id="gridProc"></table>
	</div>
</div>