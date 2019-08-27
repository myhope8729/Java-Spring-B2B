<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page import="com.kpc.eos.core.util.SessionUtil"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">待处理单据</h3>
</div>

<div role="main" class="ui-content">

	<!-- search section -->
	<form id="billProcUncheckedSearchForm" name="billProcUncheckedSearchForm" onsubmit="reloadGrid();return false;" class="form-horizontal searchForm">
		<input type="hidden" id="userId" name="userId" value="${sc.userId}" />
		<div class="action-bar">
			<div class="form-group">
				<label for="createDateFrom" class="control-label col-xs-4 text-right">制单日期(起)</label>
				<div class="col-xs-8">
					<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
						<input type="text" id="createDateFrom" name="createDateFrom" value="${sc.createDateFrom}" >
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="createDateFrom" class="control-label col-xs-4 text-right">制单日期(止)</label>
				<div class="col-xs-8">
					<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
						<input type="text" id="createDateTo" name="createDateTo" value="${sc.createDateTo}" >
					</div>
				</div>
			</div>
			<div class="form-group form-actions">
				<div class="col-xs-5 col-xs-offset-2">
					<button type="button" data-mini="true" data-theme="b" id="search" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
				</div>
				<div class="col-xs-5">
					<a id="btnBuyStatistic" data-theme="b" data-mini="true" data-role="button"  data-inline="true" class="btn btn-primary btn-fullwidth" data-iconpos="left"><qc:message code="system.common.btn.buy.statistic" /></a>
				</div>
			</div>
		</div>
	</form>
	
	<table id="grid"></table>
	<div id="gridpager"></div>
	
</div>
