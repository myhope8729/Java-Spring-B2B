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
	.ui-jqgrid td .chPrice{width:45px;text-align:center;margin-right:10px;}
	.ui-jqgrid td .oldPrice{background-color:transparent;border-color:transparent;}
</style>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">调价单详细</h3>
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
					<c:if test="${not empty rBillHead}">
					<tr>
						<th>入库单编号</th>
						<td>${rBillHead.billId}</td>
					</tr>
					<tr>
						<th>入库日期</th>
						<td>${rBillHead.arriveDate}</td>
					</tr>
					<tr>
						<th>备注</th>
						<td>${rBillHead.note}</td>
					</tr>
					<tr>
						<th>供货方</th>
						<td>${rBillHead.hostUserName}</td>
					</tr>
					<tr>
						<th>供货方联系人</th>
						<td>${rBillHead.hostContactName}</td>
					</tr>
					<tr>
						<th>供货方电话</th>
						<td>${rBillHead.hostTelNo}&nbsp;&nbsp;${rBillHead.hostMobileNo}</td>
					</tr>
					</c:if>
					<tr>
						<th>调价单编号</th>
						<td>${billProc.billHead.billId}</td>
					</tr>
					<tr>
						<th>制单人</th>
						<td>${billProc.billHead.inputorName}</td>
					</tr>
					<tr>
						<th>调价说明</th>
						<td>${billProc.billHead.custUserName}</td>
					</tr>
				</tbody>
			</table>				
		</div>	
	</div>
	
	
	<form class="form-horizontal admin-form" id="billProcPriceForm" name="billProcPriceForm" role="form" action="<%= BaseController.getCmdUrl("BillProc", "saveBillProc") %>" method="POST">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
		<input type="hidden" id="distributeYn" name="distributeYn" value="${billProc.distributeYn}" />
		<input type="hidden" id="priceCols" name="priceCols" value=""/>
		<input type="hidden" id="saveFlag" name="saveFlag" />
		
		<div class="col-xs-12 text-right" >
			<div class="row">
				<div class="custom_block2">
					<div class="ui-block-a">			
						<c:if test="${billProc.priceYn eq 'Y'}">
							<button type="button" data-theme="a" class="btn btn-orange" data-mini="true" data-role="button" data-icon="check" data-inline="true" data-iconpos="left" onclick="saveBillProc('Y');"><qc:message code="system.common.btn.save"/></button>
						</c:if>
					</div>
					<div class="ui-block-b">	
						<button type="button" data-theme="b" data-role="button" data-mini="true" data-icon="check" data-inline="true" data-iconpos="left" onclick="saveBillProc('N');"><qc:message code="system.common.btn.confirm" /></button>			
					</div>
				</div>
			</div>
		</div>
		
		<div class="table-responsive">
			<table id="gridItem"></table>
		</div>
		<div class="clear">
			<table id="gridProc"></table>
		</div>
	</form>
</div>