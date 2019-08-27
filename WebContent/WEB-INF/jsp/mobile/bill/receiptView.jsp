<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var cartGridData = ${cartGridData};
	var isDraft = ${isDraft};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a data-theme="b" data-role="button" data-icon="delete" data-inline="true" class="ui-btn-right" data-iconpos="left" href="javascript:window.close();"> 
    	<qc:message code="system.common.btn.close" /> 
    </a>
</div>

<div role="main" class="ui-content">
	<form class="form-horizontal admin-form" role="form" id="billForm" class="searchForm" method="post">
		<input type="hidden" name="billId" id="billId" value="${bill.billId}" />
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
						<td>${bill.billId}</td>
					</tr>
					<tr>
						<th>入库日期</th>
						<td>${bill.arriveDate}</td>
					</tr>
					<tr>
						<th>仓库</th>
						<td>${bill.storeName}</td>
					</tr>
					<tr>
						<th>付款方式</th>
						<td>${bill.paytypeName}</td>
					</tr>
					<tr>
						<th>制单</th>
						<td>${bill.inputorName}</td>
					</tr>
					<tr>
						<th>单据状态</th>
						<td>${bill.stateName}</td>
					</tr>
					<tr>
						<th>供货方</th>
						<td>${bill.hostUserName}</td>
					</tr>
					<tr>
						<th>供货方联系人</th>
						<td>${bill.hostContactName}</td>
					</tr>
					<tr>
						<th>供货方电话</th>
						<td>${bill.hostTelNo}&nbsp;&nbsp;${bill.hostMobileNo}</td>
					</tr>
					<tr>
						<th>单据金额(元)</th>
						<td>
							<c:if test="${bill.itemAmt == bill.itemamount2}"> ${bill.itemAmt }</c:if>
							<c:if test="${bill.itemAmt != bill.itemamount2}"> <span class="old_price">(${bill.itemAmt })</span>${bill.itemamount2}</c:if>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td>${bill.note}</td>
					</tr>
				</tbody>
			</table>				
			</div>	
		</div>
		
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Receipt", "billItemsGridAjax") %>" searchForm="#billForm"></table>
		</div>
		
		<c:if test="${isDraft !=  true}">
		<div class="clear">
			<table id="procGrid" ajaxUrl="<%= BaseController.getCmdUrl("Receipt", "billProcGridAjax") %>"></table>
		</div>
		</c:if>
	</form>
</div>