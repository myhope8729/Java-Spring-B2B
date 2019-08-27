<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var itemGridData = ${gridModel};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">销售单明细</h3>
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
						<td>
							<c:if test="${not empty billProc.billHead.bnoUser}">${billProc.billHead.bnoUser}</c:if>
							<c:if test="${empty billProc.billHead.bnoUser}">${billProc.billId}</c:if>
						</td>
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
						<th>订货方</th>
						<td>${billProc.billHead.custUserName}</td>
					</tr>
					<tr>
						<th>订货人</th>
						<td>${billProc.billHead.custContactName}</td>
					</tr>
					<tr>
						<th>订货人电话</th>
						<td>${billProc.billHead.custTelNo}&nbsp;&nbsp;${billProc.billHead.custMobileNo}</td>
					</tr>
					<tr>
						<th>收货地址</th>
						<td>${billProc.billHead.addrLocationName}</td>
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
							<c:if test="${billProc.lineTotal1 == billProc.lineTotal2}">${billProc.lineTotal2}</c:if>
							<c:if test="${billProc.lineTotal1 != billProc.lineTotal2}">
								<font color='#808080'>(${billProc.lineTotal1})</font> ${billProc.lineTotal2}
							</c:if>
						</td>
					</tr>
					<tr>
						<th>运费</th>
						<td>0</td>
					</tr>
					<tr>
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
		</div>	
	</div>
	
	<form class="form-inline admin-form" id="billProcSaleCheckedForm" name="billProcSaleCheckedForm" role="form">
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