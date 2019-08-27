<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var settingHostMsg = "<qc:message code='hostcust.supply.setting' />"
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">供货方目录</h3>
    <a href="<%= BaseController.getCmdUrl("HostCust", "hostSettingList") %>" data-role="button" data-icon="back" data-iconpos="notext" data-theme="b" data-inline="true" class="ui-btn-right">Back</a>
</div>
	
<div role="main" class="ui-content">
	<!-- search section -->
		<form class="form-horizontal" id="addSupplySearchForm" name="addSupplySearchForm"  onsubmit="reloadGrid();return false;">
			<div class="action_bar">
				<div class="form-group">
					<label for="chelp" class="control-label col-xs-4 text-right">查找供货方</label>
					<div class="col-xs-8">	
						<qc:codeList var="bizAreaCode" codeGroup="UT0000" />
						<qc:htmlSelect items="${bizAreaCode}" itemValue="codeId" itemLabel="codeName"
							isEmpty="true" emptyLabel="sc.please.select.m" name="bizAreaTypeCd" selValue='${sc.bizAreaTypeCd}'/>
					</div>
				</div>
				<div class="form-group">
					<label for="chelp" class="control-label col-xs-4 text-right">关键字</label>
					<div class="col-xs-8">	
						<input type="text" placeholder="关键字" data-clear-btn="true" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
					</div>
				</div>
			</div>
			
			<div class="form-group form-actions">
				<div class="col-xs-5 col-xs-offset-2">
					<button type="button" data-mini="true" data-theme="b" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
				</div>
				<div class="col-xs-5">
					<button type="button" data-mini="true" data-theme="b" class="btn btn-primary" onclick="settingHostList();"><qc:message code="hostcust.host.sethost" /></button>
				</div>
			</div>			
		</form>
	
	<form class="form-horizontal admin-form" id="supplyListFrom" name="supplyListFrom" role="form"  action="<%= BaseController.getCmdUrl("HostCust", "settingHost") %>" method="POST">
		<input type="hidden" id="selectedRows" name="selectedRows" />
		<input type="hidden" id="custUserId" name="custUserId" value="${custUserId}" />
	</form>
	
	<div class="form-group alignC">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>