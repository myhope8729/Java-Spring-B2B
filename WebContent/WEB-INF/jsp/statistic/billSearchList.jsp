<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var userId = '${userId}';
	var printType = '${printType}';
	var BILL_TYPE_CUSTPAY  	= '<%=Constants.CONST_BILL_TYPE_CUSTPAY%>';
	var BILL_TYPE_RUKU 		= '<%=Constants.CONST_BILL_TYPE_RUKU%>';
	var BILL_TYPE_NEWS 		= '<%=Constants.CONST_BILL_TYPE_NEWS%>';
	var BILL_TYPE_DING 		= '<%=Constants.CONST_BILL_TYPE_DING%>';
	var BILL_TYPE_PRICE 	= '<%=Constants.CONST_BILL_TYPE_PRICE%>';
	var BILL_TYPE_PAYMENT 	= '<%=Constants.CONST_BILL_TYPE_PAYMENT%>';
	var BILL_TYPE_TUI 		= '<%=Constants.CONST_BILL_TYPE_TUI%>';
	var BILL_TYPE_SALE 		= '<%=Constants.CONST_BILL_TYPE_SALE%>';
	var BILL_TYPE_POS		= '<%=Constants.CONST_BILL_TYPE_POS%>';
	var BILL_TYPE_NOTICE	= '<%=Constants.CONST_BILL_TYPE_NOTICE%>';
	var BILL_TYPE_LOSS 		= '<%=Constants.CONST_BILL_TYPE_LOSS%>';
	
	var BILL_STATE_COMPLETED	= '<%=Constants.BILL_STATE_COMPLETED%>';
	
	var PRINT_PAPER_LARGE 	= '<%=BizSetting.PRINT_PAPER_LARGE%>';
	var PRINT_PAPER_SMALL 	= '<%=BizSetting.PRINT_PAPER_SMALL%>';
	
</script>
<div class="admin_body">
	
	<h3 class="page_title">业务单据查询</h3>
	
	<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-horizontal">
		<div class="row">
			<div class="col-md-2 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="billId" class="control-label col-xs-3 visible-sm visible-xs text-left">单据类型</label>
					<div class="col-md-12 col-xs-9">
						<qc:codeList var="billType" codeGroup="DT0000" exceptCode="DT0001,DT0003,DT0009,DT0010,DT0011" />
						<qc:htmlSelect items="${billType}" itemValue="codeId" itemLabel="codeName" selValue='${sc.billType}'
							isEmpty="true" emptyLabel='billproc.billtype.select' cssClass="form-control" id="billType" name="billType"/>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="billId" class="control-label col-md-4 col-xs-3 text-left">单据编号</label>
					<div class="col-md-8 col-xs-9">
						<input type="text" class="form-control" id="billId" name="billId" value="${sc.billId}"/>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="createDateFrom" class="control-label col-md-3 col-sm-3 col-xs-3 text-left">业务日期</label>
					<div class="col-md-8 col-xs-9">
						<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
							<input type="text" class="form-control" name="createDateFrom" value="${sc.createDateFrom}" >
							<span class="input-group-addon"> ~ </span>
							<input type="text" class="form-control" name="createDateTo" value="${sc.createDateTo}" >
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="compName" class="control-label col-md-5  col-xs-3 text-left">订(供)货方</label>
					<div class="col-md-7 col-xs-9">
						<input type="text" class="form-control" id="compName" name="compName" value="${sc.compName}"/>
					</div>
				</div>
			</div>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="col-xs-12 text-right">
						<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
						<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
					</div>
				</div>
			</div>
		</div>
	</form>		

	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("BillSearch", "billSearchGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
	</div>	
</div>
