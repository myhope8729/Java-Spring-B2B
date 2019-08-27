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
<div class="admin_body">
	
	<h3 class="page_title">${pageTitle}</h3>
	
	<div class="row">
		<form id="orderListFrm" class="searchForm">
			<div class="col-lg-4 col-md-6 col-xs-12">
				<div class="row">
					<div class="form-group col-sm-4 col-xs-12">
					 	<label class="control-label">单据编号</label>
					 	<input type="text" name="billId" value="${sc.billId}" class="form-control" />
					</div>
					<div class="form-group col-sm-8 col-xs-12">
						<label class="control-label">制单日期</label>
						<div class="input-group input-large date-picker input-daterange" data-date="2012-11-10" data-date-format="yyyy-mm-dd">
							<input type="text" class="form-control" name="createDateFrom" value="${sc.createDateFrom}" >
							<span class="input-group-addon"> ~ </span>
							<input type="text" class="form-control" name="createDateTo" value="${sc.createDateTo}" >
						</div>
					</div>
				</div>	
			</div>
			<div class="col-lg-4 col-md-6 col-xs-12">
				<div class="row">
					<div class="form-group col-sm-6 col-xs-12">
						<label class="control-label">单据状态</label>
					 	<qc:codeList var="stateList" codeGroup="WS0000" />
						<qc:htmlSelect items="${stateList}" itemValue="codeId" itemLabel="codeName" selValue='${sc.state}'
							isEmpty="true" emptyLabel='sc.please.select.s' cssClass="form-control" id="state" name="state" customAttr='' />
					</div>
					<div class="form-group col-sm-6 col-xs-12">
						<label class="control-label">供货方</label>
					 	<input type="text" name="vendorName" value="${sc.vendorName}" class="form-control" />
					</div>
				</div>	
			</div>
			<div class="col-md-4 col-lg-offset-0 col-md-offset-8 col-sm-offset-6 col-sm-6 col-xs-12">
				<div class="row">
					<div class="form-group col-xs-12 col-lg-mgt20">
					 	<button type="submit" id="search" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
						<button type="button" id="reset" class="btnReset btn btn-default mgl10"><qc:message code="system.common.btn.reset"/></button>
						<a class="btn btn-primary pull-right" href="<%= BaseController.getCmdUrl("Order", "vendorsList") %>"><qc:message code="system.common.btn.new"/></a>
					</div>
				</div>	
			</div>
		</form>
	</div>
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Order", "orderGridAjax") %>" searchForm="#orderListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>
