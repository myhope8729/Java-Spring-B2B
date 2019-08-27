<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var settingHostMsg = "<qc:message code='hostcust.supply.setting' />"
</script>

<h3 class="page_title">供货方目录</h3>
<div id="admin_body">
	<form id="addSupplySearchForm" name="addSupplySearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar row">
			<div class="col-md-8">
				<label for="chelp" class="control-label">查找供货方</label>
				<qc:codeList var="bizAreaCode" codeGroup="UT0000" />
				<qc:htmlSelect items="${bizAreaCode}" itemValue="codeId" itemLabel="codeName"
					cssClass="form-control" isEmpty="true" emptyLabel="sc.please.select" name="bizAreaTypeCd" selValue='${sc.bizAreaTypeCd}'/>
				
				<input type="text" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
				<button type="button" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
			</div>
			<div class="col-md-4 text-right">
				<button type="button" class="btn btn-primary" onclick="settingHostList();"><qc:message code="hostcust.host.sethost" /></button>
	 			<a href="<%= BaseController.getCmdUrl("HostCust", "hostSettingList") %>" class="btn btn-default mdl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
	
	<form class="form-horizontal admin-form" id="supplyListFrom" name="supplyListFrom" role="form"  action="<%= BaseController.getCmdUrl("HostCust", "settingHost") %>" method="POST">
		<input type="hidden" id="selectedRows" name="selectedRows" />
		<input type="hidden" id="custUserId" name="custUserId" value="${custUserId}" />
	</form>
	
	<div class="form-group">
		<div class="clear">
			<table id="grid"></table>
			<div id="gridpager"></div>
		</div>
	</div>
</div>