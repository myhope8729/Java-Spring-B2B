<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<script>
	var deleteConfirmMsg = "<qc:message code='hostcust.supply.deleteConfirm' />";
	var hc_no_connection = "<qc:message code='order.host.cust.map.not.saved' />";
	var selectActionUrl = "${selectActionUrl}";
</script>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>
	<form id="vendorsListFrm" class="searchForm form-inline">
		<div class="form-group col-md-4 col-sm-6 col-xs-12 mgb20">
			<div class="row">
			 	<label for="chelp" class="control-label">查找供货方</label>
			 	<input type="text" class="form-control" id="chelp" name="chelp" value="${sc.chelp}" />
			</div>
		</div>
		<div class="form-group col-md-8 col-sm-6 col-xs-12 action_bar mgb20">
			<div class="row">
				<button type="submit" id="search" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
				<button type="button" id="reset" class="btnReset btn btn-default mgl10"><qc:message code="system.common.btn.reset"/></button>
				<a class="btn btn-default pull-right" href="${returnUrl}"><qc:message code="system.common.btn.back"/></a>
			</div>
		</div>
	</form>
	<div class="clear">
		<table id="grid" ajaxUrl="${gridAjaxUrl}" searchForm="#vendorsListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>
