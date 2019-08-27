<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page import="com.kpc.eos.core.util.SessionUtil"%>

<h3 class="page_title">待处理单据</h3>
<div class="admin_body">
	<form id="billProcUncheckedSearchForm" name="billProcUncheckedSearchForm" onsubmit="reloadGrid();return false;" class="form-horizontal">
		<input type="hidden" id="userId" name="userId" value="${sc.userId}" />
		<div class="row">
			<div class="col-md-2 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="billId" class="control-label col-xs-3 visible-sm visible-xs text-left">单据类型</label>
					<div class="col-md-12 col-xs-9">
						<qc:codeList var="billType" codeGroup="DT0000" />
						<qc:htmlSelect items="${billType}" itemValue="codeId" itemLabel="codeName" selValue='${sc.billType}'
							isEmpty="true" emptyLabel='billproc.billtype.select' cssClass="form-control" id="billType" name="billType"/>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="billId" class="control-label col-md-4 col-xs-3 text-left">单据编号</label>
					<div class="col-md-8 col-xs-9">
						<input type="text" class="form-control" id="billId" name="billId" value="${sc.billId}"/>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="createDateFrom" class="control-label col-md-3 col-sm-3 col-xs-3 text-left">制单日期</label>
					<div class="col-md-8 col-xs-9">
						<div class="input-group input-large date-picker input-daterange" data-date-format="yyyy-mm-dd">
							<input type="text" class="form-control" id="createDateFrom" name="createDateFrom" value="${sc.createDateFrom}" >
							<span class="input-group-addon"> ~ </span>
							<input type="text" class="form-control" id="createDateTo" name="createDateTo" value="${sc.createDateTo}" >
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="compName" class="control-label col-md-5  col-xs-3 text-left">订(供)货方</label>
					<div class="col-md-7 col-xs-9">
						<input type="text" class="form-control" id="compName" name="compName" value="${sc.compName}"/>
					</div>
				</div>
			</div>
			<div class="col-xs-12">
				<div class="form-group">
					<div class="col-xs-12 text-right">
						<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
						<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
						<button type="button" class="btn btn-primary mgl10" id="btnSupplyStatistics"><qc:message code="system.common.btn.supply.statistic" /></button>
						<!-- 
						<button type="button" class="btn btn-primary mgl10" id="btnDistribute"><qc:message code="system.common.btn.order.distribute" /></button>
						-->
						<button type="button" class="btn btn-primary mgl10" id="btnBuyStatistic"><qc:message code="system.common.btn.buy.statistic" /></button>
						<button type="button" class="btn btn-primary mgl10" id="btnDistributeStatistic"><qc:message code="system.common.btn.distribute.statistic" /></button>
						<button type="button" class="btn btn-primary mgl10" id="btnDistributeConfirm"><qc:message code="system.common.btn.distribute.confirm" /></button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="clear">
			<table id="grid"></table>
			<div id="gridpager"></div>
		</div>
	</form>
	
	
</div>
