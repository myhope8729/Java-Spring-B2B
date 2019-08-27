<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var cartGridData = ${cartGridData};
</script>

<form class="form-horizontal admin-form" role="form" id="billForm" class="searchForm" method="post">
	<input type="hidden" name="billId" id="billId" value="${bill.billId}" />
	<div class="admin_body">
		<h3 class="page_title">${pageTitle}
			<div class="action_bar text-right">
				<a class="btn btn-default" href="javascript:window.close();"><qc:message code="system.common.btn.close"/></a>
			</div>
		</h3>
		<table class="table table-bordered dataTable01">
			<colgroup>
				<col width="14%" />
				<col width="20%" />
				<col width="13%" />
				<col width="20%" />
				<col width="13%" />
				<col width="20%" />
	 		</colgroup>
			<tbody>
				<tr>
					<th>单据编号</th>
					<td>${bill.billId}</td>
					<th>退货日期</th>
					<td>${bill.arriveDate}</td>
					<th>退货仓库</th>
					<td>${bill.storeName}</td>
				</tr>
				<tr>
					<th>原单据编号</th>
					<td>${bill.rbillId}</td>
					<th>原单据类型</th>
					<td>${bill.rbillTypeName}</td>
					<th>单据状态</th>
					<td>${bill.stateName}</td>
				</tr>
				<tr>
					<th>对方单位</th>
					<td>
						<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostUserName}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.custUserName}</c:if>
					</td>
					<th>对方单位联系人</th>
					<td>
						<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostContactName}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.custContactName}</c:if>
					</td>
					<th>对方单位电话</th>
					<td>
						<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostTelNo}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.custTelNo}</c:if>
					</td>
				</tr>
				
				<tr>
					<th>单据金额(元)</th>
					<td>
						<c:if test="${bill.itemAmt == bill.itemamount2}"> ${bill.itemAmt }</c:if>
						<c:if test="${bill.itemAmt != bill.itemamount2}"> <span class="old_price">(${bill.itemAmt })</span>${bill.itemamount2}</c:if>
					</td>
					<th>退货原因</th>
					<td>${bill.note}</td>
					<th>制单</th>
					<td>${bill.inputorName}</td>
				</tr>
			</tbody>
		</table>
		
		<div class="row">
			<div class="col-lg-12">
				<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Return", "billItemsGridAjax") %>" searchForm="#billForm"></table>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<table id="procGrid" ajaxUrl="<%= BaseController.getCmdUrl("Return", "billProcGridAjax") %>"></table>
			</div>
		</div>
	</div>
</form>