<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page import="com.kpc.eos.model.billProcMng.BillProcModel" %>
<%@page import="org.apache.commons.lang.StringUtils"%>

<jsp:useBean id="billHistory" class="java.util.ArrayList" scope="request"/>

<script>
	var itemGridData = ${gridModel};
	var jsCol = '${jsCol}';
	var jsYn = '${jsYn}';
	var itemNoCol = '${itemNoCol}';
	var itemNameCol = '${itemNameCol}';
	var afterSavingUrl = "<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>";
	var invalidJSQtyMsg = "<qc:message code='system.invalid.js.qty' />";
	var invalidNumber="<qc:message code='system.invalid.number' />";
	var wrongPrice="<qc:message code='system.common.validate.wrong.price' />";
	var wrongQty="<qc:message code='system.common.validate.wrong.qty' />";
	var emptyNote="<qc:message code='billproc.reject.input.note' />";
	var confirmReject="<qc:message code='billproc.reject.confirm' />";
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">待处理单据明细</h3>
    <a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content">
	<table class="table table-bordered dataTable01">
		<colgroup>
			<col width="35%" />
			<col width="65%" />
 		</colgroup>
		<tbody>
			<tr>
				<th>订货方</th>
				<td>${billProc.billHead.custUserName}</td>
			</tr>
			<tr>
				<th>地址</th>
				<td>${billProc.billHead.addrLocationName}${billProc.billHead.addrAddress}</td>
			</tr>
			<tr>
				<th>收货人</th>
				<td>${billProc.billHead.addrContactName}</td>
			</tr>
			<tr>
				<th>电话</th>
				<td>${billProc.billHead.addrTelNo}&nbsp;&nbsp;${billProc.billHead.addrMobile}</td>
			</tr>
			<tr>
				<th>付款方式</th>
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
				<th>合计金额</th>
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
	
	<form class="form-horizontal admin-form" id="billProcOrderForm" name="billProcOrderForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
		<input type="hidden" id="distributeYn" name="distributeYn" value="${billProc.distributeYn}" />
		
		<div class="form-group mgt15">
			<label for="procNote" class="control-label col-xs-4 text-right">处理意见</label>
			<div class="col-xs-8">
				<c:if test="${not empty procEnv}">
					<qc:htmlSelect items="${procEnv}" itemValue="seqNo" itemLabel="c1"
						isEmpty="true" emptyLabel='sc.please.select.s' id="procEnv" name="procEnv" />
				</c:if>
				<c:if test="${empty procEnv}">
					<input type="text" data-clear-btn="true" class="form-control" id="procNote" name="procNote"/>
				</c:if>
			</div>
		</div>
		<c:if test="${not empty procEnv}">
		<div class="form-group">
			<div class="col-xs-8 col-xs-offset-4">
				<input type="text" data-clear-btn="true" class="form-control" id="procNote" name="procNote"/>
			</div>
		</div>
		</c:if>
		
		<div class="form-group">
			<div class="col-xs-5 col-xs-offset-2">
				<button type="button" data-theme="b" data-mini="true" data-role="button" class="btn btn-primary" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>
			</div>
			<div class="col-xs-5">
				<button type="button" data-theme="b" data-mini="true" data-role="button" class="btn btn-primary" onclick="rejectBillProc();"><qc:message code="system.common.btn.return" /></button>
			</div>
			<%-- <div data-role="controlgroup" data-type="horizontal" data-mini="true">
				<c:if test="${billProc.priceYn eq 'Y' or billProc.qtyYn eq 'Y'}">
					<button type="button" data-theme="b" data-role="button" onclick="saveBillProc('Y');"><qc:message code="system.common.btn.save"/></button>
				</c:if>
				<button type="button" data-theme="b" data-role="button" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>
				<c:if test="${checkProcYn eq 'N'}">
					<button type="button" data-theme="a" data-role="button" onclick="rejectBillProc();"><qc:message code="system.common.btn.return" /></button>
				</c:if>
			</div> --%>
		</div>
	</form>
	
	<div class="table-responsive mgt15">
		<table id="gridItem"></table>
	</div>
	
	<%
		
		if ( (billHistory != null) && (billHistory.size() > 0) ){
	%>
		<div class="info-box" style="margin:20px 0;">
			<div class="box-title">
				<label class="title">订单处理记录</label>
			</div>
			<div class="box-body">
	<%
			for (int i = 0; i < billHistory.size(); i++){
				BillProcModel history = (BillProcModel)billHistory.get(i);
	%>
				<table class="table dataTable03">
					<tbody>
						<tr>
							<td><%= StringUtils.isEmpty(history.getUpdateDate())?"---":history.getUpdateDate() %></td>
						</tr>
						<tr>
							<td>
								<%= history.getEmpName() %> &nbsp;&nbsp;
								<%= history.getBillProcFullName() %> &nbsp;&nbsp;
								<%= history.getStateName() %>
							</td>
						</tr>
					</tbody>
				</table>
	<%
				if (i < billHistory.size() - 1){
	%>
				<div style="border-bottom:2px solid #f3f3f3;margin-top:10px;">
					
				</div>
	<%
				}
			}
	%>
			</div>
		</div>
	
	<%
		}
	%>
	
	<div class="form-actions">
		<a class="mgr-no btn btn-default" href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" data-role="button" id="btnBack"><qc:message code="system.common.btn.return" /></a>
	</div>
</div>