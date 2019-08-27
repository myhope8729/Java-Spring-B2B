<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page import="com.kpc.eos.model.billProcMng.BillProcModel" %>
<%@page import="org.apache.commons.lang.StringUtils"%>

<jsp:useBean id="billHistory" class="java.util.ArrayList" scope="request"/>

<script>
	var itemGridData = ${gridModel};
	var itemNameCol = '${itemNameCol}';
	var itemUnitCol = '${itemUnitCol}';
	var itemNoCol = '${itemNoCol}';
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">订单明细</h3>
    <a href="<%= BaseController.getCmdUrl("BillProc", "billProcCheckedList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content">
	<div class="table-responsive">
		<table id="gridItem"></table>
	</div>
	
	<table class="table dataTable03" style="margin-top:10px;">
		<tbody>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;min-width:70px;">
					订单编号:
				</td>
				<td style="padding: 0; font-size:13px;width:100%;">
					<c:if test="${not empty billProc.billHead.bnoUser}">${billProc.billHead.bnoUser}</c:if>
					<c:if test="${empty billProc.billHead.bnoUser}">${billProc.billId}</c:if>
				</td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;">创建时间:</td>
				<td style="padding: 0; font-size:13px;">${billProc.billHead.createDate}</td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;">付款方式:</td>
				<td style="padding: 0; font-size:13px;">${billProc.billHead.paytypeName}&nbsp;<c:if test="${not empty billProc.billHead.paymentType}"> / ${billProc.billHead.paymentType}</c:if></td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;">送货时间:</td>
				<td style="padding: 0; font-size:13px;">${billProc.billHead.arriveDate}</td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;">订货说明:</td>
				<td style="padding: 0; font-size:13px;">${billProc.billHead.note}</td>
			</tr>
		</tbody>
	</table>
	
	<div class="info-box">
		<div class="box-title">
			<label class="title">收货地址</label>
		</div>
		<div class="box-body">
			<table class="table dataTable03" style="margin:0;">
				<tbody>
					<tr>
						<td>${billProc.billHead.addrLocationName}</td>
					</tr>
					<tr>
						<td>${billProc.billHead.addrAddress}</td>
					</tr>
					<tr>
						<td>
							${billProc.billHead.addrContactName}&nbsp;&nbsp;${billProc.billHead.addrTelNo}
							<c:if test="${not empty billProc.billHead.addrMobile}">
								,&nbsp;${billProc.billHead.addrMobile}
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<%
		
		if ( (billHistory != null) && (billHistory.size() > 0) ){
	%>
		<div class="info-box" style="margin-bottom:20px;">
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
				<div style="border-bottom:2px solid #f3f3f3;margin-top:5px;">
					
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
	
	<form class="form-inline admin-form" id="billProcOrderCheckedForm" name="billProcOrderCheckedForm" role="form">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
	</form>
	
	<div class="form-actions">
		<a class="mgr-no btn btn-default" href="<%= BaseController.getCmdUrl("BillProc", "billProcCheckedList") %>" data-role="button" id="btnBack"><qc:message code="system.common.btn.return" /></a>
	</div>
</div>