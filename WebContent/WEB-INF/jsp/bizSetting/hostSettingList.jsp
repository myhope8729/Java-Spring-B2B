<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<script>
	var deleteConfirmMsg = "<qc:message code='hostcust.supply.deleteConfirm' />"
</script>

<h3 class="page_title">供货方目录</h3>
<div class="admin_body">
	<form id="supplySettingSearchForm" name="supplySettingSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar row">
			<div class="col-md-6">
				<label for="chelp" class="control-label">查找供货方</label>
				<input type="text" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
				<button type="button" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
			</div>
			<div class="col-md-6 text-right">
				<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("HostCust", "hostList") %>"><qc:message code="system.common.btn.new" /> </a>
			</div>
		</div>
	</form>
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
	<!-- list end -->
</div>
