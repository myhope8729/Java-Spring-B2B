<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	
	<h3 class="page_title">商品销售统计</h3>
	
	<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-horizontal">
		<div class="row">
			<div class="col-md-6 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="billDate" class="control-label col-md-3 col-sm-3 col-xs-3 text-left">制单日期</label>
					<div class="col-md-9 col-xs-9">
						<div class="col-md-7 no-padding">
							<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
								<input type="text" class="form-control" name="dateFrom" id="dateFrom" value="${sc.dateFrom}" >
								<span class="input-group-addon"> ~ </span>
								<input type="text" class="form-control" name="dateTo" id="dateTo" value="${sc.dateTo}" >
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 col-xs-12">
				<div class="form-group">
					<label for="searchString1" class="control-label col-md-3 col-sm-3 col-xs-3 text-left">关键字</label>
					<div class="col-md-9 col-xs-9">
						<input type="text" class="form-control" id="searchString1" name="searchString1" value="${sc.searchString1}"/>
					</div>
				</div>
			</div>		
			<div class="col-md-3 col-sm-3 col-xs-12 text-right">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
				<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
			</div>
		</div>
			
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleItemSearch", "saleItemSearchGridAjax") %>" searchForm="#searchForm"></table>
			<div id="gridpager"></div>
		</div>
		
	</form>		
</div>
