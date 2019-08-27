<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var bpList = ${bpList}; 
	var detailList = ${detailList}; 
	var billTotalAmt = '' || '${payment.totalAmt}';
</script>
<style>
	table.dataTable02 th {
		text-align: right;
	}
	table.dataTable02 td {
		text-align: left;
	}
</style>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a data-theme="b" data-role="button" data-icon="delete" data-inline="true" class="ui-btn-right" data-iconpos="left" href="javascript:window.close();"> 
    	<qc:message code="system.common.btn.close" /> 
    </a>
</div>

<div role="main" class="ui-content" id="sale-form-wrap" >
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
						<td>${payment.billId}</td>
					</tr>
					<tr>
						<th>到账日期</th>
						<td>${payment.arriveDate }</td>
					</tr>
					<tr>
						<th>单据状态</th>
						<td>${payment.stateName}</td>
					</tr>
					<tr>
						<th>付款方</th>
						<td>${payment.custUserName}</td>
					</tr>
					<tr>
						<th>付款方联系人</th>
						<td>${payment.custContactName}</td>
					</tr>
					<tr>
						<th>付款方电话</th>
						<td>${payment.custMobileNo}</td>
					</tr>
					<tr>
						<th>预付款名称</th>
						<td>${payment.paytypeName}</td>
					</tr>
					<tr>
						<th>收款金额(元)</th>
						<td>${payment.totalAmt }</td>
					</tr>
					<tr>
						<th>备注</th>
						<td>${payment.note }</td>
					</tr>
				</tbody>
			</table>				
		</div>	
	</div>

	<div class="clear">
		<table id="cartGrid"></table>
	</div>
	
	<div class="clear">
		<table id="bpGrid"></table>
	</div>
		
</div>
