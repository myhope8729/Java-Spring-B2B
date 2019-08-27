<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var itemGridData = ${gridModel};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">入库单明细</h3>
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
						<th>入库日期</th>
						<td>${billProc.billHead.arriveDate}</td>
					</tr>
					<tr>
						<th>仓库</th>
						<td>${billProc.billHead.storeName}</td>
					</tr>
					<tr>
						<th>付款方式</th>
						<td>${billProc.billHead.paytypeName}</td>
					</tr>
					<tr>
						<th>制单人</th>
						<td>${billProc.billHead.inputorName}</td>
					</tr>
					<tr>
						<th>单据状态</th>
						<td>${billProc.billHead.stateName}</td>
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
						<th>单据金额(元)</th>
						<td>
							<c:if test="${billProc.billHead.itemAmt == billProc.billHead.itemamount2}">${billProc.billHead.itemamount2}</c:if>
							<c:if test="${billProc.billHead.itemAmt != billProc.billHead.itemamount2}">
								<font color='#808080'>(${billProc.billHead.itemAmt})</font> ${billProc.billHead.itemamount2}
							</c:if>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td>${billProc.billHead.note}</td>
					</tr>
				</tbody>
			</table>				
		</div>	
	</div>
	
	<form class="form-inline admin-form" id="billProcReceiptCheckedForm" name="billProcReceiptCheckedForm" role="form">
		<input type="hidden" id="billType" name="billType" value="${billProc.billHead.billType}" />
		<input type="hidden" id="billId" name="billId" value="${billProc.billId}" />
		<input type="hidden" id="billProcId" name="billProcId" value="${billProc.billProcId}" />
		<input type="hidden" id="itemUserId" name="itemUserId" value="${itemUserId}" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${billProc.workFlowId}" />
	</form>
	
	<div class="table-responsive">
		<table id="gridItem"></table>
	</div>
	<div class="clear">
		<table id="gridProc"></table>
	</div>
</div>