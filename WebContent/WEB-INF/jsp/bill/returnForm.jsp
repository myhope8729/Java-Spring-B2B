<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var cartGridData = ${cartGridData}; 
	var billItems = ${billItemList};
</script>
<div id="" class="admin_body">

	<h3 class="page_title">${pageTitle}</h3>
	<div class="action_bar text-right">
		<button class="btn btn-primary" id="btnSave"><qc:message code="system.common.btn.submit"/></button>
		<a class="btn btn-default" href="Return.do?cmd=billList"><qc:message code="system.common.btn.back"/></a>
	</div>
	
	<form class="form-horizontal admin-form" role="form" 
		action="<%= BaseController.getCmdUrl("Return", "saveReturn") %>" id="userItemsForm" class="searchForm" method="post">
		
		<input type="hidden" name="originBillId" id="originBillId" value="${originBill.billId}" />
		<input type="hidden" name="hostUserId" id="hostUserId" value="${originBill.hostUserId }" />
		<input type="hidden" name="custUserId" id="custUserId" value="${originBill.custUserId }" />
		<input type="hidden" name="originBillType" id="originBillType" value="${originBill.billType}" />
		
		<div class="info-box">
			<div class="box-title">
				<label class="title">原单据 (单据类型：${originBill.billTypeName })</label>
			</div>
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
						<td>${originBill.billId}</td>
						<th>单据金额(元)</th>
						<td>${originBill.totalAmt}</td>
						<th>单据状态	</th>
						<td>${originBill.stateName}</td>
					</tr>
					<tr>
						<th>付款方式</th>
						<td>${originBill.paytypeName}</td>
						<th>制单</th>
						<td>${originBill.inputorName}</td>
						<th>仓库</th>
						<td>${originBill.storeName}</td>
					</tr>
					
					<tr>
						<th>供货方</th>
						<td>${originBill.hostUserName}</td>
						<th>供货方联系人</th>
						<td>${originBill.hostContactName}</td>
						<th>供货方电话</th>
						<td>${originBill.hostTelNo}&nbsp;&nbsp;${originBill.hostMobileNo}</td>
					</tr>
					<tr>
						<th>订货方</th>
						<td>${originBill.custUserName }</td>
						<th>订货方联系人</th>
						<td>${originBill.custContactName}</td>
						<th>订货方电话</th>
						<td>${originBill.custTelNo}&nbsp;&nbsp;${originBill.custMobileNo}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="row">
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-6 text-left">仓库</label>
					<div class="col-sm-6">
						<qc:htmlSelect items="${storeList}" itemValue="storeId" itemLabel="storeName" selValue=''
						isEmpty="true" emptyLabel="==请选择仓库==" cssClass="form-control wd200" id="storeId" name="storeId" customAttr="validate='required: true' message='仓库'"/>
					</div>
				</div>
			</div>
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-6 col-sm-offset-0 col-md-offset-1 col-xs-12 text-left">退货日期</label>
					<div class="col-sm-6 col-xs-12">
						<input type="text" class="form-control date-picker" validate="required:true" name="returnDate" message="退货日期" data-date-format="yyyy-mm-dd" value="" >
					</div>
				</div>
			</div>
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-6 col-md-offset-3 col-sm-offset-0 col-xs-12 text-left">退货原因</label>
					<div class="input-large  col-sm-6 col-xs-12">
						<input type="text" class="form-control" validate="required:true" name="returnReason" message="退货原因" value="" >
					</div>
				</div>
			</div>
		</div>
		
		<div class="info-box border">
			<div class="box-title">
				<div class="row">
					<label class="title col-lg-8">退货商品</label>
					<label class="title col-lg-4">
						<c:if test="${originBill.billType == 'DT0002'}">报损合计金额(元):</c:if>
						<c:if test="${originBill.billType != 'DT0002'}">退货金额(元)合计:</c:if> <span id="total-amt"></span></label>
					<input type="hidden" name="total_price" id="total_price" value=""/>
				</div>
			</div>
			<div class="box-body no-padding">
				<div class="row">
					<div class="col-lg-12">
						<table id="cartGrid"></table>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
