<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	
	<h3 class="page_title">商品销售明细</h3>
	
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<div class="action_bar row">
				<div class="text-left col-md-8">
					<label  class="control-label mgl20 mgt10">商品编码:${sc.searchString3}&nbsp;&nbsp;名称:${sc.searchString4}  </label>
				</div>
				<div class="text-right col-md-4">
					<a href="<%= BaseController.getCmdUrl("SaleItemSearch", "saleItemSearchList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>					
			</div>
			
			<qc:printBean value="${sc}" />
		</form>
			
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleItemSearch", "saleItemDetailSearchGridAjax") %>" searchForm="#searchForm"></table>
			<div id="gridpager"></div>
		</div>
		
</div>
