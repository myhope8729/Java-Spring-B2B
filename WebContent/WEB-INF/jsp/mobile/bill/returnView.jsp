<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var cartGridData = ${cartGridData};
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
						<th>退货日期</th>
						<td>${bill.arriveDate}</td>
					</tr>
					<tr>
						<th>退货仓库</th>
						<td>${bill.storeName}</td>
					</tr>
					<tr>
						<th>原单据编号</th>
						<td>${bill.rbillId}</td>
					</tr>
					<tr>
						<th>原单据类型</th>
						<td>${bill.rbillTypeName}</td>
					</tr>
					<tr>
						<th>单据状态</th>
						<td>${bill.stateName}</td>
					</tr>
					<tr>
						<th>对方单位</th>
						<td>
							<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostUserName}</c:if>
							<c:if test="${bill.rbillType != 'DT0002'}">${bill.custUserName}</c:if>
						</td>
					</tr>
					<tr>
						<th>对方单位联系人</th>
						<td>
							<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostContactName}</c:if>
							<c:if test="${bill.rbillType != 'DT0002'}">${bill.custContactName}</c:if>
						</td>
					</tr>
					<tr>
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
					</tr>
					<tr>
						<th>退货原因</th>
						<td>${bill.note}</td>
					</tr>
					<tr>
						<th>制单</th>
						<td>${bill.inputorName}</td>
					</tr>
				</tbody>
			</table>				
			</div>	
		</div>
		
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Return", "billItemsGridAjax") %>" searchForm="#billForm"></table>
		</div>
		
		<div class="clear">
			<table id="procGrid" ajaxUrl="<%= BaseController.getCmdUrl("Return", "billProcGridAjax") %>"></table>
		</div>		
	</form>
</div>