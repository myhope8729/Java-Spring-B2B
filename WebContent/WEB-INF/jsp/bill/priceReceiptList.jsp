<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript" src="<c:url value="/js/common/menu.js"/>"></script>

<script>
	var selectActionUrl = "${selectActionUrl}";
</script>
<div class="admin_body">
	
	<h3 class="page_title">${pageTitle}</h3>
	
	<div class="row">
		<form id="billListFrm" class="searchForm">
			<div class="col-lg-4 col-md-6 col-xs-12">
				<div class="row">
					<div class="form-group col-sm-6 col-xs-12">
						<label class="control-label">单据编号</label>
				 		<input type="text" name="billId" value="${sc.billId}" class="form-control" />
					</div>
					<div class="form-group col-sm-6 col-xs-12">
						<label class="control-label">供货方</label>
				 		<input type="text" name="vendorName" value="${sc.vendorName}" class="form-control" />
					</div>
				</div>	
			</div>
			<div class="col-lg-4 col-md-6 col-xs-12">
				<div class="row">
					<div class="form-group col-xs-12">
						<label class="control-label">入库日期</label>
						<div class="input-group input-large date-picker input-daterange" data-date="2012-11-10" data-date-format="yyyy-mm-dd">
							<input type="text" class="form-control" name="arriveDateFrom" value="${sc.arriveDateFrom}" >
							<span class="input-group-addon"> ~ </span>
							<input type="text" class="form-control" name="arriveDateTo" value="${sc.arriveDateTo}" >
						</div>
					</div>
				</div>	
			</div>
			<div class="col-md-4 col-lg-offset-0 col-md-offset-8 col-sm-offset-6 col-sm-6 col-xs-12">
				<div class="row">
					<div class="form-group col-xs-12 col-lg-mgt20">
					 	<button type="submit" id="search" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
						<button type="button" id="reset" class="btnReset btn btn-default mgl10"><qc:message code="system.common.btn.reset"/></button>
						<a class="btn btn-default pull-right" href="<%= BaseController.getCmdUrl("Price", "priceList") %>"><qc:message code="system.common.btn.back"/></a>
					</div>
				</div>	
			</div>
		</form>
	</div>
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Price", "billGridAjax") %>" searchForm="#billListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>
