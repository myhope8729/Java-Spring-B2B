<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">商品出入库明细</h3>
    <a href="<%= BaseController.getCmdUrl("StockSearch", "stockSearchList") %>" data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>	

<div role="main" class="ui-content">	
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
		
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<input type="hidden" id="itemId" name="itemId"  value="${sc.itemId}" >
			<input type="hidden" id="storeId" name="storeId"  value="${sc.storeId}" >
			<div class="action_bar row">
				<div class="text-left col-xs-12">
					<qc:codeList var="billType" codeGroup="DT0000" />
					<qc:htmlSelect items="${billType}" itemValue="codeId" itemLabel="codeName" selValue='${sc.billType}'
						isEmpty="true" emptyLabel='billproc.billtype.select' customAttr="data-theme='a'" id="billType" name="billType"/>
				</div>
				<div class="text-left col-xs-12">			
					<input type="text" placeholder="单据编号"  data-clear-btn="true" class="form-control" id="billId" name="billId" value="${sc.billId}"/>
				</div>							
			</div>
						
			<div class="action_bar row">
				<div class="text-right col-md-6">
					<fieldset class="ui-grid-b">
						<div class="ui-block-a">
							<button type="submit" class="btn btn-primary"  data-theme="b" data-icon="search" ><qc:message code="system.common.btn.search"/></button>
						</div>
						<div class="ui-block-b">
							<div class="mgl5">
								<button type="button" class="btnReset btn btn-primary" id="reset"><qc:message code="system.common.btn.reset" /></button>
							</div>
						</div>
						<div class="ui-block-c">
							<div class="mgl5">
								<a href="<%= BaseController.getCmdUrl("StockSearch", "stockSearchList") %>" class="btn btn-primary" data-role="button" data-icon="back"  id="btnBack"><qc:message code="system.common.btn.back" /></a>
							</div>
						</div>					
					</fieldset>						
				</div>		
			</div>					
	
		</form>					
	</div>			
	
	<div class="table-responsive">	
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("StockSearch", "stockDetailSearchGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
	</div>
		
</div>		

