<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	
	<h3 class="page_title">日销售明细</h3>
	
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<div class="action_bar row">
				<div class="text-left col-md-3">
					<label  class="control-label mgl20 mgt10">销售日期: ${sc.dateFrom} </label>
					<input type="hidden" id="dateFrom", name="dateFrom", value="${sc.dateFrom}" />
				</div>
				<div class="text-left col-md-5">
					<label for="searchString1" class="control-label mgl20">单据编号</label>
					<input type="text" class="form-control" id="searchString1" name="searchString1" value="${sc.searchString1}"/>
				</div>			
				<div class="text-right col-md-4">
					<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
					<a href="<%= BaseController.getCmdUrl("SaleDaySearch", "saleDaySearchList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>					
			</div>
		</form>
			
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleDaySearch", "saleDayDetailSearchGridAjax") %>" searchForm="#searchForm"></table>
			<div id="gridpager"></div>
		</div>
		
</div>
