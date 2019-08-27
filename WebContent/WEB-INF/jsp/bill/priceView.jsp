<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var gridData = ${gridData}; 
	var billItems = ${itemList};
</script>

<style>
	.ui-jqgrid td .chPrice{width:50%;text-align:center;margin-right:10px;font-size:12px;}
	.ui-jqgrid td .oldPrice{background-color:transparent;border-color:transparent;font-size:12px;}
</style>

<div id="" class="admin_body">

	<h3 class="page_title">${pageTitle}
		<div class="action_bar text-right">
			<a class="btn btn-default" href="javascript:window.close();"><qc:message code="system.common.btn.close"/></a>
		</div>
	</h3>
	
	
	<form class="form-horizontal admin-form" role="form" 
		action="<%= BaseController.getCmdUrl("Price", "savePrice") %>" id="userItemsForm" class="searchForm" method="post">
		<input type="hidden" name="billId" value="${bill.billId }"/>
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
					<th>调价单编号</th>
					<td>${bill.billId}</td>
					<th>单据状态</th>
					<td>${bill.stateName}</td>
					<th>调价说明</th>
					<td>${bill.custUserName}</td>
				</tr>
			</tbody>
		</table>
		
		<div class="row">
			<div class="col-lg-12">
				<table id="grid"></table>
			</div>
		</div>
		
		<div class="row">
			<div class="col-lg-12">
				<table id="procGrid"></table>
			</div>
		</div>
	</form>
</div>
