<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var obj = ${SysDetailData};
	var selMsg = "<qc:message code='sc.please.select' />";
	var invalidNumber="<qc:message code='system.invalid.number' />";
</script>

<h3 class="page_title">业务配置</h3>
<div class="admin_body">
	<form id="bizSettingSearchForm" name="bizSettingSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar row">
			<div class="col-md-12">
				<qc:codeList var="bizTypeList" codeGroup="DK0000" />
				<qc:htmlSelect items="${bizTypeList}" itemValue="codeId" itemLabel="codeName"
					isEmpty="true" emptyLabel='sc.please.biztype.select' cssClass="form-control" id="sysKindCd" name="sysKindCd" selValue='${sc.sysKindCd}' />
				<label for="chelp" class="control-label mgl20">查找配置项</label>
				<input type="text" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
				<button type="button" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
			</div>
		</div>
	</form>
	<form class="form-horizontal admin-form" id="bizSettingForm" name="bizSettingForm" role="form" action="<%= BaseController.getCmdUrl("BizSetting", "saveBizSetting") %>" method="POST"> 
		<div class="clear">
			<table id="grid"></table>
		</div>
		<input type="hidden" id="userData" name="userData" value="" />
	</form>
	<div class="form-group" style="padding-top:20px;">
		<div class="col-lg-12 text-center">
			<button type="submit" class="btn btn-primary" onclick="saveBizSetting();"><qc:message code="system.common.btn.save" /></button>
		</div>
	</div>
</div>
