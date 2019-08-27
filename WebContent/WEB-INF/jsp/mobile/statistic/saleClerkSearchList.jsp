<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">业务员销售统计</h3>
</div>	

<div role="main" class="ui-content">	
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
	
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<div class="row">
				<div class="text-left col-md-6">
					<input type="text" placeholder="关键字" data-clear-btn="true"  class="form-control" id="searchString1" name="searchString1" value="${sc.searchString1}"/>
				</div>			
				<div class="text-left col-md-6">
					<button type="submit" data-theme="b" data-icon="search" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
				</div>
			</div>			
				
		</form>		
	</div>

	<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleClerkSearch", "saleClerkSearchGridAjax") %>" searchForm="#searchForm"></table>
	<div id="gridpager"></div>
	
</div>