<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var messages = {
		deleteMsg : "<qc:message code='hostcust.supply.deleteConfirm' />"
	};
</script>
<div class="admin_body">
	
	<h3 class="page_title">${pageTitle}</h3>
	<div class="row">
		<form id="infoListFrm" class="searchForm">
			<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
			<div class="col-lg-4 col-md-6 col-xs-12">
				<div class="row">
					<div class="form-group col-sm-6 col-xs-12">
						<label class="control-label">类别</label>
					 	<qc:htmlSelect items="${fileTypeList}" itemValue="c1" itemLabel="c1" selValue='${sc.itype}'
							isEmpty="true" emptyLabel='sc.please.select.s' cssClass="form-control" id="itype" name="itype" customAttr='' />
					</div>
					<div class="form-group col-sm-6 col-xs-12">
						<label class="control-label">标题</label>
					 	<input type="text"  class="form-control" name="info" id="info" value="${sc.info}" />
					</div>
				</div>	
			</div>
			<div class="col-lg-4 col-md-6 col-xs-12">
				<div class="row">
					<div class="form-group col-xs-12">
						<label class="control-label">制单日期</label>
						<div class="input-group input-large date-picker input-daterange" data-date="2012-11-10" data-date-format="yyyy-mm-dd">
							<input type="text" class="form-control" name="createDateFrom" value="${sc.createDateFrom}" >
							<span class="input-group-addon"> ~ </span>
							<input type="text" class="form-control" name="createDateTo" value="${sc.createDateTo}" >
						</div>
					</div>
				</div>	
			</div>
			<div class="col-md-4 col-lg-offset-0 col-md-offset-8 col-sm-offset-6 col-sm-6 col-xs-12">
				<div class="row">
					<div class="form-group col-xs-12 col-lg-mgt20">
					 	<button type="submit" class="btn btn-primary" id="btnSearch"><qc:message code="system.common.btn.search"/></button>
						<button type="button" id="reset" class="btnReset btn btn-default mgl10"><qc:message code="system.common.btn.reset"/></button>
						<a class="btn btn-primary mgl20 pull-right" href="<%= BaseController.getCmdUrl("Info", "infoForm") %>"><qc:message code="system.common.btn.new"/></a>
					</div>
				</div>	
			</div>
		</form>
	</div>
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Info", "infoGridAjax") %>" searchForm="#infoListFrm"></table>
		<div id="gridpager"></div>
	</div>
</div>
