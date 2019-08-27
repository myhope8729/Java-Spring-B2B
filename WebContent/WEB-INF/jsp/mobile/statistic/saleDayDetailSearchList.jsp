<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">日销售明细</h3>
    <a href="<%= BaseController.getCmdUrl("SaleDaySearch", "saleDaySearchList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>	

<div role="main" class="ui-content">	
	
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<input type="hidden" id="dateFrom", name="dateFrom", value="${sc.dateFrom}" />
			
			<div class="form-group mgt10">
						<div class="text-center col-md-12">
							<label>销售日期: ${sc.dateFrom} </label>
						</div>
			</div>			
								
		</form>
			
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleDaySearch", "saleDayDetailSearchGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
		
</div>		
