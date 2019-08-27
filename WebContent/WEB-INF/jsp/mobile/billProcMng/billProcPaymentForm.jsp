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
						<th>制单人</th>
						<td>${billProc.billHead.inputorName}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td>${billProc.billHead.custContactName}</td>
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
						<td>${billProc.billHead.total2}</td>
					</tr>
					<tr>
						<th>到账日期</th>
						<td>${billProc.billHead.arriveDate}</td>
					</tr>
				</tbody>
			</table>				
		</div>	
	</div>
	
	<form class="form-inline admin-form" id="billProcPaymentForm" name="billProcPriceForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
		
		<div class="custom_block_37">
			<div class="ui-grid-a">
				<div class="ui-block-a">				
					<label for="totalAmt" class="control-label mgl20"><span class="required">*</span>金额(元)</label>
				</div>
				<div class="ui-block-b">					
					<c:if test="${billProc.priceYn == 'Y'}">
						<input type="text" class="form-control" name="totalAmt" id="totalAmt" value="${billProc.billHead.total2}" validate="required: true, number:true" />
						<qc:errors items="${formErrors}" path="totalAmt" />
					</c:if>
					<c:if test="${billProc.priceYn != 'Y'}">
						<input type="text" class="form-control" name="totalAmt" id="totalAmt" readonly value="${billProc.billHead.total2}" validate="required: true, number:true" />
					</c:if>
				</div>
			</div>
		</div>
		<div class="col-xs-12 text-right" >
			<div class="row">
				<div class="custom_block2">
					<div class="ui-block-a">			
						<c:if test="${billProc.priceYn eq 'Y' or billProc.qtyYn eq 'Y'}">
							<button type="button" class="btn btn-orange" data-theme="a" data-role="button" data-icon="check" data-inline="true" data-iconpos="left" onclick="saveBillProc('Y');"><qc:message code="system.common.btn.save"/></button>
						</c:if>
					</div>
					<div class="ui-block-b">	
						<button type="button" data-theme="b" data-role="button" data-icon="check" data-inline="true" data-iconpos="left" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>			
					</div>
				</div>
			</div>
		</div>
		
		<div class="clear">
			<table id="paymentGrid"></table>
		</div>
		<div class="clear">
			<table id="gridProc"></table>
		</div>
	</form>
</div>