<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	
	<h3 class="page_title">业务员销售统计</h3>
	
	<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
		<div class="action_bar row">
			<div class="text-right col-md-8"> 
				<label for="searchString1" class="control-label mgl20">关键字</label>
				<input type="text" class="form-control" id="searchString1" name="searchString1" value="${sc.searchString1}"/>
			</div>			
			<div class="text-right col-md-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
				<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
			</div>
		</div>
			
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleClerkSearch", "saleClerkSearchGridAjax") %>" searchForm="#searchForm"></table>
			<div id="gridpager"></div>
		</div>
		
	</form>		
</div>
