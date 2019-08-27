<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	
	<h3 class="page_title"> 商品出入库明细</h3>
	
	<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-horizontal">
		<input type="hidden" id="itemId" name="itemId"  value="${sc.itemId}" >
		<input type="hidden" id="storeId" name="storeId"  value="${sc.storeId}" >
		
		<div class="row">
				<div class="col-md-2 col-sm-6 col-xs-12">
					<div class="form-group">
						<div class="col-md-12 col-xs-9">
							<qc:codeList var="billType" codeGroup="DT0000" />
							<qc:htmlSelect items="${billType}" itemValue="codeId" itemLabel="codeName" selValue='${sc.billType}'
								isEmpty="true" emptyLabel='billproc.billtype.select' cssClass="form-control" id="billType" name="billType"/>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="form-group">
						<label for="billNo" class="control-label col-md-4 col-xs-3 text-left">单据编号</label>
						<div class="col-md-8 col-xs-9">
							<input type="text" class="form-control" id="billId" name="billId" value="${sc.billId}"/>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-sm-6 col-xs-12">
					<div class="form-group">
						<label for="billDate" class="control-label col-md-3 col-sm-3 col-xs-3 text-left">制单日期</label>
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
						<div class="col-xs-12 text-right">
							<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
							<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
							<a href="<%= BaseController.getCmdUrl("StockSearch", "stockSearchList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
						</div>
					</div>
				</div>
		</div>
			
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("StockSearch", "stockDetailSearchGridAjax") %>" searchForm="#searchForm"></table>
			<div id="gridpager"></div>
		</div>
		
	</form>		
</div>
