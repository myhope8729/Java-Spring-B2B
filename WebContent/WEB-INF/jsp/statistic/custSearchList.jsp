<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<h3 class="page_title">客户列表</h3>

<div class="admin_body">
	<form id="custSearchForm" name="custSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar row">
			<div class="text-left col-md-4">
				<qc:htmlSelect items="${custTypeList}" itemValue="custTypeId" itemLabel="custTypeName" selValue='${sc.custTypeId}'
					cssClass="form-control" isEmpty="true" emptyLabel="hostcust.cust.selectcustomer" name="custTypeId"/>  
			</div>
			<div class="text-left col-md-4">					
				<label for="chelp" class="control-label mgl20">关键字</label>
				<input type="text" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
			</div>
			<div class="text-right col-md-4">
				<button type="button" class="btn btn-primary mgl10" onclick="reloadGrid();"><qc:message code="system.common.btn.search"/></button>
				<button type="button" class="btnReset btn btn-default mgl10" id="reset"><qc:message code="system.common.btn.reset" /></button>
			</div>
		</div>
	</form>
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>
