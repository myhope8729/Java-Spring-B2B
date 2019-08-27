<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var userId = '${userId}';
	var itemGridData = ${gridModel};
</script>
	
<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">商品库存统计</h3>
</div>	

<div role="main" class="ui-content">	
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
			
		<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
			<input type="hidden" name="propertyField" id="propertyField" value="${sc.propertyField}" />
			<div class="action_bar row">
				<div class="text-left col-md-6">
					<label for="storeId" class="control-label">仓库</label>
					<qc:htmlSelect items="${storeComboList}" itemValue="storeId" itemLabel="storeName" selValue='${sc.storeId}'
						isEmpty="true" emptyLabel='sc.please.select.all.s' customAttr="data-theme='a'" id="storeId" name="storeId" />
				</div>
				<div class="text-left col-md-6">
					<label for="propertyFieldValue" class="control-label">商品类别</label>
					<qc:htmlSelect items="${iTypeComboList}" itemValue="itype" itemLabel="itypeName" selValue='${sc.propertyFieldValue}'
						isEmpty="true" emptyLabel='sc.please.select.all.s' customAttr="data-theme='a'" id="propertyFieldValue" name="propertyFieldValue" />
				</div>	
				<div class="text-left col-md-12">
					<input type="text" placeholder="关键字" class="form-control" data-clear-btn="true"  id="searchString" name="searchString" value="${sc.searchString}"/>
				</div>			
			</div>
			<div class="text-left custom_block_73">
				<div class="ui-grid-a">
						<div class="ui-block-a">
							<button type="submit" class="btn btn-primary"  data-theme="b" data-icon="search" ><qc:message code="system.common.btn.search"/></button>
						</div>
						<div class="ui-block-b">
								<button type="button" class="btnReset btn btn-primary" id="reset"><qc:message code="system.common.btn.reset" /></button>
						</div>
				</div>
			</div>	
		</form>		
	</div>	
	
	<div class="table-responsive">	
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("StockSearch", "stockSearchGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
	</div>

</div>