<%@page import="com.kpc.eos.core.util.MessageUtil"%>
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
    <h3 class="page_title">我的订单</h3>
</div>

<div role="main" class="ui-content">
	<form id="orderListFrm" class="form-horizontal search-form">
		<input type="hidden" id="userId" name="userId" value="${sc.userId}" />
		<div class="action-bar">
			<div class="form-group">	
				<label for="billId" class="control-label col-xs-4 text-right">单据编号</label>
				<div class="col-xs-8">
					<input type="text" class="form-control mgl10" id="billId" name="billId" value="${sc.billId}"/>
				</div>
			</div>
			<div class="form-group">
				<label for="state" class="control-label col-xs-4 text-right">单据状态</label>
				<div class="col-xs-8">
		 			<qc:codeList var="stateList" codeGroup="WS0000" />
					<qc:htmlSelect items="${stateList}" itemValue="codeId" itemLabel="codeName" selValue='${sc.state}'
						isEmpty="true" emptyLabel='sc.please.select.s' id="state" name="state" customAttr='' />
				</div>
			</div>				
			<div class="form-group">
				<label for="vendorName" class="control-label col-xs-4 text-right">供货方</label>
				<div class="col-xs-8">
		 			<input type="text" id="vendorName" name="vendorName" value="${sc.vendorName}" class="form-control mgl10" />
				</div>
			</div>
		</div>
		
		<div class="form-group form-actions">
			<div class="col-xs-5 col-xs-offset-2">
				<button type="submit" data-mini="true" data-theme="b" id="search" class="btn btn-primary"><qc:message code="system.common.btn.search" /></button>
			</div>
			<div class="col-xs-5">
				<a data-theme="a" data-mini="true" data-role="button"  class="btn btn-primary btnReset" data-iconpos="left"><qc:message code="system.common.btn.reset"/></a>
			</div>
		</div>
	</form>
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Order", "orderGridAjax") %>" searchForm="#orderListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>
