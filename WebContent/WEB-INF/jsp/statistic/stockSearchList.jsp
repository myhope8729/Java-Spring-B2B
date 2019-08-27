<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var userId = '${userId}';
	var itemGridData = ${gridModel};
</script>
<div class="admin_body">
	
	<h3 class="page_title">商品库存统计</h3>
	
	<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-horizontal">
		<input type="hidden" name="propertyField" id="propertyField" value="${sc.propertyField}" />
		<div class="action_bar row">
			<div class="text-left col-md-3">
				<div class="form-group">
					<label for="storeId" class="control-label col-md-4 text-left">仓库</label>
					<div class="col-md-8">
						<qc:htmlSelect items="${storeComboList}" itemValue="storeId" itemLabel="storeName" selValue='${sc.storeId}'
							isEmpty="true" emptyLabel='sc.please.select.all' cssClass="form-control" id="storeId" name="storeId" />
					</div>
				</div>
			</div>
			<div class="text-left col-md-3">
				<div class="form-group">
					<label for="propertyFieldValue" class="control-label col-md-5 text-right">商品类别</label>
					<div class="col-md-7">
						<qc:htmlSelect items="${iTypeComboList}" itemValue="itype" itemLabel="itypeName" selValue='${sc.propertyFieldValue}'
							isEmpty="true" emptyLabel='sc.please.select.all' cssClass="form-control" id="propertyFieldValue" name="propertyFieldValue" />
					</div>
				</div>
			</div>
			<div class="text-left col-md-3">
				<div class="form-group">
					<label for="searchString" class="control-label col-md-4 text-right">关键字</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="searchString" name="searchString" value="${sc.searchString}"/>
					</div>
				</div>
			</div>
			<div class="text-right col-md-3">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
				<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
			</div>	
		</div>
	</form>		
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("StockSearch", "stockSearchGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
	</div>
</div>
