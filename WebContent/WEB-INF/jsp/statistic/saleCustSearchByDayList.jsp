<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var BILL_TYPE_DING 		= '<%=Constants.CONST_BILL_TYPE_DING%>';
</script>

<div class="admin_body">
	
	<h3 class="page_title">客户销售明细</h3>
	
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<div class="action_bar row">
				<div class="text-left col-md-3">
					<label  class="control-label mgl20 mgt10">客户: ${sc.searchString2} </label>
				</div>
				<div class="text-left col-md-5">
					<label for="searchString3" class="control-label mgl20">单据编号</label>
					<input type="text" class="form-control" id="searchString3" name="searchString3" value="${sc.searchString3}"/>
				</div>			
				<div class="text-right col-md-4">
					<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
					<a href="<%= BaseController.getCmdUrl("SaleCustSearch", "saleCustSearchList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>					
			</div>
		
			<qc:printBean value="${sc}" />
		</form>
			
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleCustSearch", "saleCustSearchByDayGridAjax") %>" searchForm="#searchForm"></table>
			<div id="gridpager"></div>
		</div>
		
</div>
