<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var cartGridData = ${cartGridData};
	var isDraft = ${isDraft};
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
					<th>入库日期</th>
					<td>${bill.arriveDate}</td>
					<th>仓库</th>
					<td>${bill.storeName}</td>
				</tr>
				<tr>
					<th>付款方式</th>
					<td>${bill.paytypeName}</td>
					<th>制单</th>
					<td>${bill.inputorName}</td>
					<th>单据状态</th>
					<td>${bill.stateName}</td>
				</tr>
				
				<tr>
					<th>供货方</th>
					<td>${bill.hostUserName}</td>
					<th>供货方联系人</th>
					<td>${bill.hostContactName}</td>
					<th>供货方电话</th>
					<td>${bill.hostTelNo}&nbsp;&nbsp;${bill.hostMobileNo}</td>
				</tr>
				<tr>
					<th>单据金额(元)</th>
					<td>
						<c:if test="${bill.itemAmt == bill.itemamount2}"> ${bill.itemAmt }</c:if>
						<c:if test="${bill.itemAmt != bill.itemamount2}"> <span class="old_price">(${bill.itemAmt })</span>${bill.itemamount2}</c:if>
					</td>
					<th>备注</th>
					<td>${bill.note}</td>
					<th></th>
					<td></td>
				</tr>
			</tbody>
		</table>
		
		<div class="row">
			<div class="col-lg-12">
				<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Receipt", "billItemsGridAjax") %>" searchForm="#billForm"></table>
			</div>
		</div>
		
		<c:if test="${isDraft !=  true}">
		<div class="row">
			<div class="col-lg-12">
				<table id="procGrid" ajaxUrl="<%= BaseController.getCmdUrl("Receipt", "billProcGridAjax") %>"></table>
			</div>
		</div>
		</c:if>
	</div>
</form>