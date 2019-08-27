<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">每日销售统计</h3>
</div>	

<div role="main" class="ui-content">	
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
	
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<div class="action_bar row">
				<div class="text-left col-md-12">
						<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
							<input type="text" placeholder="开始日期" data-clear-btn="true" name="dateFrom" value="${sc.dateFrom}" >
							<span class="input-group-addon"> ~ </span>
							<input type="text" placeholder="截至日期" data-clear-btn="true" name="dateTo" value="${sc.dateTo}" >
						</div>
				</div>
			</div>
			
			<div class="text-left custom_block_73">
				<fieldset class="ui-grid-a">
					<div class="ui-block-a">
						<button type="submit" class="btn btn-primary"  data-theme="b" data-icon="search"><qc:message code="system.common.btn.search"/></button>
					</div>
					<div class="ui-block-b">
						<button type="button" class="btnReset btn btn-primary" id="reset"><qc:message code="system.common.btn.reset" /></button>
					</div>
				</fieldset>
			</div>
		</form>			
		
	</div>		
			
	<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleDaySearch", "saleDaySearchGridAjax") %>" searchForm="#searchForm"></table>
	<div id="gridpager"></div>
		
</div>	
