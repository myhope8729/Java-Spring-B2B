<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var itemGridData = ${gridModel};
	var jsCol = '${jsCol}';
	var jsYn = '${jsYn}';
	var afterSavingUrl = "<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>";
	var invalidJSQtyMsg = "<qc:message code='system.invalid.js.qty' />";
	var invalidNumber="<qc:message code='system.invalid.number' />";
	var wrongPrice="<qc:message code='system.common.validate.wrong.price' />";
	var wrongQty="<qc:message code='system.common.validate.wrong.qty' />";
	var confirmPrice="<qc:message code='billproc.sale.price.confirm' />";
	var emptyNote="<qc:message code='billproc.reject.input.note' />";
	var confirmReject="<qc:message code='billproc.reject.confirm' />";
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">销售单详细</h3>
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
						<th>客户名称</th>
						<td>${billProc.billHead.custUserName}</td>
					</tr>
					<tr>
						<th>联系人</th>
						<td>${billProc.billHead.custContactName}</td>
					</tr>
					<tr>
						<th>联系电话</th>
						<td>${billProc.billHead.custTelNo}&nbsp;&nbsp;${billProc.billHead.custMobileNo}</td>
					</tr>
					<tr>
						<th>收货地址</th>
						<td>${billProc.billHead.addrLocationName}${billProc.billHead.addrAddress}</td>
					</tr>
					<tr>
						<th>收货人</th>
						<td>${billProc.billHead.addrContactName}</td>
					</tr>
					<tr>
						<th>收货人电话</th>
						<td>${billProc.billHead.addrTelNo}&nbsp;&nbsp;${billProc.billHead.addrMobile}</td>
					</tr>
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
						<th>送货日期</th>
						<td>${billProc.billHead.arriveDate}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td>${billProc.billHead.note}</td>
					</tr>
					<tr>
						<th>商品金额(元)</th>
						<td>
							<c:if test="${empty billProc.lineTotal2}">0</c:if>
							<c:if test="${not empty billProc.lineTotal2}">${billProc.lineTotal2}</c:if>
						</td>
					</tr>
					<tr>
						<th>运费</th>
						<td>${billProc.billHead.freightamount2}</td>
					</tr>
					<tr>
						<th>合计金额(元)</th>
						<td>${billProc.lineTotal2}</td>
					</tr>
					<c:if test="${billProc.billHead.hbmark eq 'Y'}">
					<tr>
						<th>货款余额(元)</th>
						<td>
							<c:if test="${empty prePayList}">无</c:if>
							<c:if test="${not empty prePayList}">
								<c:forEach var="prePay" items="${prePayList}">
								${prePay.payType2} : ${prePay.totalAmt} <br/>
								</c:forEach>
							</c:if>
						</td>
					</tr>
					</c:if>
				</tbody>
			</table>				
		</div>	
	</div>
	
	<form class="form-inline admin-form" id="billProcSaleForm" name="billProcSaleForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
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
						<button type="button" class="btn btn-orange" data-theme="a" data-role="button" data-icon="check" data-inline="true" data-iconpos="left" onclick="saveBillProc('Y');"><qc:message code="system.common.btn.save"/></button>
					</c:if>
					<button type="button" data-theme="b" data-role="button" data-icon="check" data-inline="true" data-iconpos="left" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>
					<c:if test="${checkProcYn eq 'N'}">
						<button type="button" data-theme="a" data-role="button" data-icon="arrow-l" data-inline="true" data-iconpos="left" onclick="rejectBillProc();"><qc:message code="system.common.btn.return" /></button>
					</c:if>
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