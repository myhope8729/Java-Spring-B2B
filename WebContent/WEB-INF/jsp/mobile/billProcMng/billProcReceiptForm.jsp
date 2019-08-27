<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var itemGridData = ${gridModel};
	var jsCol = '${jsCol}';
	var jsYn = '${jsYn}';
	var afterSavingUrl = "<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>";
	var invalidJSQtyMsg = "<qc:message code='system.invalid.js.qty' />";
	var invalidNumber="<qc:message code='system.invalid.number' />"
	var wrongPrice="<qc:message code='system.common.validate.wrong.price' />";
	var wrongQty="<qc:message code='system.common.validate.wrong.qty' />";
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">入库单详细</h3>
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
						<th>供货方</th>
						<td>${billProc.billHead.hostUserName}</td>
					</tr>
					<tr>
						<th>供货方联系人</th>
						<td>${billProc.billHead.hostContactName}</td>
					</tr>
					<tr>
						<th>供货方电话</th>
						<td>${billProc.billHead.hostTelNo}&nbsp;&nbsp;${billProc.billHead.hostMobileNo}</td>
					</tr>
					<tr>
						<th>付款方式/类别</th>
						<td>
							${billProc.billHead.paytypeName}
							<c:if test="${not empty billProc.billHead.paymentType}"> / ${billProc.billHead.paymentType}</c:if>
						</td>
					</tr>
					<tr>
						<th>制单</th>
						<td>${billProc.billHead.inputorName}</td>
					</tr>
					<tr>
						<th>单据状态</th>
						<td>${billProc.billHead.stateName}</td>
					</tr>
					<tr>
						<th>商品金额(元)</th>
						<td>
							<c:if test="${empty billProc.billHead.total2}">0</c:if>
							<c:if test="${not empty billProc.billHead.total2}">${billProc.billHead.total2}</c:if>
						</td>
					</tr>
					<tr>
						<th>仓库</th>
						<td>${billProc.billHead.storeName}</td>
					</tr>
					<tr>
						<th>合计金额(元)</th>
						<td>${billProc.billHead.total2}</td>
					</tr>
				</tbody>
			</table>				
		</div>	
	</div>
	
	<form class="form-inline admin-form" id="billProcReceiptForm" name="billProcReceiptForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
		<input type="hidden" id="distributeYn" name="distributeYn" value="${billProc.distributeYn}" />
		
		<div class="custom_block_37">
			<div class="ui-grid-a">
				<div class="ui-block-a">				
					<label for="procNote" class="control-label mgl20">处理意见</label>
				</div>
				<div class="ui-block-b">					
					<input type="text" class="form-control" id="procNote" name="procNote"/>
				</div>
			</div>
		</div>
		
		<div class="col-xs-12 text-right" >
			<div class="row">
				<div data-role="controlgroup" data-type="horizontal" data-mini="true">
					<c:if test="${billProc.priceYn eq 'Y' or billProc.qtyYn eq 'Y'}">
						<button type="button" data-theme="a" data-role="button" data-icon="check" class="btn btn-orange" data-inline="true" data-iconpos="left" onclick="saveBillProc('Y');"><qc:message code="system.common.btn.save"/></button>
					</c:if>
					<button type="button" data-theme="b" data-role="button" data-icon="check" data-inline="true" data-iconpos="left" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>
				</div>
			</div>
		</div>
	</form>

	<div class="table-responsive">
		<table id="gridItem"></table>
	</div>
	<div class="clear">
		<table id="gridProc"></table>
	</div>
</div>