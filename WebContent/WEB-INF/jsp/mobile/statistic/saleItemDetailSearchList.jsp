<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">商品销售明细</h3>
    <a href="<%= BaseController.getCmdUrl("SaleItemSearch", "saleItemSearchList") %>" data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>	

<div role="main" class="ui-content">	
	
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<div class="action_bar mgt10">
				<div class="text-left col-md-6">
					<fieldset class="ui-grid-a">
						<div class="ui-block-a">
							<label  class="control-label"><b>商品编码:</b>&nbsp;${sc.searchString3}</label>
						</div>
						<div class="ui-block-b">
							<label  class="control-label"><b>名称:</b>&nbsp;${sc.searchString4}  </label>
						</div>		
					</fieldset>				
				</div>
			</div>
			
			<qc:printBean value="${sc}" />
		</form>
	
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleItemSearch", "saleItemDetailSearchGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
</div>		
