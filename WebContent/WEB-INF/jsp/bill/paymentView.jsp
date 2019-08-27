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
<div id="sale-form-wrap" class="admin_body">
	<h3 class="page_title">${pageTitle}
		<div class="action_bar text-right">
			<a class="btn btn-default" href="javascript:window.close();"><qc:message code="system.common.btn.close"/></a>
		</div>
	</h3>
	<div class="info-box">
		<div class="box-body no-padding">
			<table class="table table-bordered dataTable01">
				<tbody>
					<tr>
						<th width="11%">单据编号</th>
						<td width="37%" align="left">${payment.billId }</td>
						<th width="9%" align="right">到账日期</th>
						<td width="18%">${payment.arriveDate }</td>
						<th width="10%" align="right">单据状态</th>
						<td width="39%" align="left">${payment.stateName}</td>
					</tr>
					<tr>
						<th width="11%" align="right">付款方</th>
						<td width="37%" align="left">${payment.custUserName}</td>
						<th width="10%" align="right">付款方联系人</th>
						<td width="18%" align="left">${payment.custContactName}</td>
						<th width="12%" align="left">付款方电话</th>
						<td width="18%" align="left">${payment.custMobileNo}</td>
					</tr>
					<tr>
						<th width="11%" align="right">预付款名称</th>
						<td width="37%" align="left">${payment.paytypeName}</td>
						<th width="10%" align="right">收款金额(元)</th>
						<td width="12%" align="left">${payment.totalAmt }</td>
						<th width="9%" align="right">备注</th>
						<td width="18%" align="left">${payment.note }</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="info-box">
		<div class="box-body no-padding">
			<table id="cartGrid"></table>
		</div>
	</div>
	
	<div class="info-box">
		<div class="box-body no-padding">
			<table id="bpGrid"></table>
		</div>
	</div>
		
</div>
