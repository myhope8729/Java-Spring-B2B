<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<h3 class="page_title">订货方目录</h3>

<div class="admin_body">
	<form id="custSettingSearchForm" name="custSettingSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar row">
			<div class="col-lg-12 text-left">
				<qc:htmlSelect items="${custTypeList}" itemValue="custTypeId" itemLabel="custTypeName"
					cssClass="form-control" isEmpty="true" emptyLabel="hostcust.cust.selectcustomer" name="custTypeId" selValue='${sc.custTypeId}'/>
				<qc:htmlSelect items="${empList}" itemValue="empId" itemLabel="empName"
					cssClass="form-control mgl10" isEmpty="true" emptyLabel="hostcust.cust.selectemp" name="empId" selValue='${sc.empId}'/>
				<label for="chelp" class="control-label mgl20">关键字</label>
				<input type="text" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
				<button type="button" class="btn btn-primary mgl10" onclick="reloadGrid();"><qc:message code="system.common.btn.search"/></button>
				<button type="button" class="btn btn-default mgl10"><qc:message code="system.common.btn.qccode" /></button>
			</div>
		</div>
	</form>
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>
