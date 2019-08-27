<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	
	<h3 class="page_title">每日销售统计</h3>
	
	<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
		<div class="action_bar row">
			<div class="text-right col-md-3"></div>
			<div class="text-right col-md-5">
				<div class="col-md-4 alignR">
					<label for="billDate" class="mgt10" >制单日期</label>
				</div>
				<div class="col-md-7 no-padding">
					<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
						<input type="text" class="form-control" name="dateFrom" value="${sc.dateFrom}" >
						<span class="input-group-addon"> ~ </span>
						<input type="text" class="form-control" name="dateTo" value="${sc.dateTo}" >
					</div>
				</div>
			</div>
			<div class="text-right col-md-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
				<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
			</div>			
		</div>
			
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleDaySearch", "saleDaySearchGridAjax") %>" searchForm="#searchForm"></table>
			<div id="gridpager"></div>
		</div>
		
	</form>		
</div>
