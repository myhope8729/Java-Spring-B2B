<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript" src="<c:url value="/js/common/menu.js"/>"></script>
<script type="text/javascript">
	var messages = {
		deleteMsg : "<qc:message code='hostcust.supply.deleteConfirm' />"
	};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
</div>

<div role="main" class="ui-content">
	<form id="receiptListFrm" class="searchForm">
		<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
			<h3><qc:message code="system.common.btn.search" /></h3>	
			<div class="action-bar row">
				<div class="text-left col-xs-12">	
					<label for="billId">单据编号</label>
					<input type="text" class="form-control mgl10" name="billId" value="${sc.billId}"/>
				</div>
				<div class="text-left col-xs-12">	
					<label for="arriveDateFrom">入库日期</label>
					<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
						<input type="text" name="arriveDateFrom" value="${sc.arriveDateFrom}" >
						<span class="input-group-addon"> ~ </span>
						<input type="text" name="arriveDateTo" value="${sc.arriveDateTo}" >
					</div>
				</div>
				<div class="text-left col-xs-12">
					<label for="state">单据状态</label>
		 			<qc:codeList var="stateList" codeGroup="WS0000" />
					<qc:htmlSelect items="${stateList}" itemValue="codeId" itemLabel="codeName" selValue='${sc.state}'
						isEmpty="true" emptyLabel='sc.please.select.s' id="state" name="state" customAttr='' />
				</div>				
				<div class="text-left col-xs-12">
					<label for="vendorName">供货方</label>
			 		<input type="text" name="vendorName" value="${sc.vendorName}" class="form-control mgl10" />
				</div>
			</div>	
			
			<div class="text-left custom_block_73">
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<button type="button" data-mini="true"  data-theme="b" data-icon="search" id="search" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
					</div>
					<div class="ui-block-b">
						<button type="button" data-mini="true"  class="btnReset" id="reset"><qc:message code="system.common.btn.reset" /></button>
					</div>
				</div>
			</div>					
		</div>
	</form>
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Receipt", "receiptGridAjax") %>" searchForm="#receiptListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>
