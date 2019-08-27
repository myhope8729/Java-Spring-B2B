<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>


<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">客户销售统计</h3>
</div>	

<div role="main" class="ui-content">
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>		
	
		<form id="searchForm" onsubmit="reloadGrid();return false;">
			<div class="row">
				<div class="col-md-6 mgt7">
					<input type="text" data-clear-btn="true" id="compName" name="compName" class="form-control" placeholder="客户" value="${sc.compName}" />
				</div>
				<div class="text-left col-md-6">
					<button type="submit" class="btn btn-primary"  data-theme="b" data-icon="search" ><qc:message code="system.common.btn.search"/></button>
				</div>					
			</div>	
	
		</form>		
			
	</div>			
	
	<table id="salecustgrid" ajaxUrl="<%= BaseController.getCmdUrl("SaleCustSearch", "saleCustSearchGridAjax") %>" searchForm="#searchForm"></table>
	<div id="salecustgridpager"></div>
</div>		
