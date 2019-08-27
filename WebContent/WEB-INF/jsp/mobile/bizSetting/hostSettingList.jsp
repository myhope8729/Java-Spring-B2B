<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<script>
	var deleteConfirmMsg = "<qc:message code='hostcust.supply.deleteConfirm' />"
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">供货方目录</h3>
</div>
	
<div role="main" class="ui-content">
	<!-- search section -->
	<div class="action-bar">
		<form id="supplySettingSearchForm" name="supplySettingSearchForm" class="form-horizontal" onsubmit="reloadGrid();return false;">
			<div class="form-group">
				<label for="chelp" class="control-label col-xs-4 text-right">关键字</label>
				<div class="col-xs-8">	
					<input type="text" placeholder="关键字" data-clear-btn="true" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
				</div>
			</div>
			<div class="form-group form-actions">
				<div class="col-xs-5 col-xs-offset-2">
					<button type="button" data-mini="true" data-theme="b" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
				</div>
				<div class="col-xs-5">
					<a data-theme="b" data-role="button" data-mini="true" data-inline="true" class="btn btn-primary btn-fullwidth ui-mini" data-iconpos="left"  href="<%= BaseController.getCmdUrl("HostCust", "hostList") %>"><qc:message code="system.common.btn.new"/></a>
				</div>
			</div>
		</form>
	</div>
	
	<table id="grid"></table>
	<div id="gridpager"></div>
	<!-- list end -->
	
</div>
