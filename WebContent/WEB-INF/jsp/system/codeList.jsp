<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var obj = '';
	var selMsg = "<qc:message code='sc.please.select' />";
	var reallySaveMsg = "<qc:message code='system.alert.modify' />";
	var noSaveData = "<qc:message code='sc.no.select.rows' />";
	var noSelectUpper = "<qc:message code='system.code.upper.noselect' />";
</script>

<h3 class="page_title">编码资料</h3>
<div class="admin_body">
	<form id="searchForm" name="searchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar row">
			<div class="text-left col-md-8">
				<qc:htmlSelect items="${upperCodeList}" itemValue="codeId" itemLabel="codeName"
					isEmpty="true" emptyLabel='sc.please.select.codetype' cssClass="form-control" id="scUpperCode" name="scUpperCode" />
				<label for="scCodeName" class="control-label mgl20">编码名称</label>
				<input type="text" class="form-control" id="scCodeName" name="scCodeName"/>
				<label for="scValidYn" class="control-label mgl20">状态</label>							
		 		<select name="scValidYn" id="scValidYn" class="form-control" >
		 		    <option value="" <c:if test="${sc.scValidYn == ''}"> selected</c:if>><qc:message code="sc.please.select.s" /></option>
					<option value="Y" <c:if test="${sc.scValidYn == 'Y'}"> selected</c:if>>正常</option>
					<option value="N" <c:if test="${sc.scValidYn == 'N'}"> selected</c:if>>禁用</option>
				</select>
				<button type="button" id="search" class="btn btn-primary mgl10" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
			</div>
			<div class="text-right col-md-4">
				<button type="button" class="btn btn-primary" onclick="saveCodeList();"><qc:message code="system.common.btn.save" /></button>
				<button type="button" class="btn btn-primary" onclick="addCode();"><qc:message code="system.common.btn.new" /></button>
				<button type="button" class="btnReset btn btn-default" onclick="resetOk();"><qc:message code="system.common.btn.cancel" /></button>
			</div>
		</div>
	</form>
		<div class="clear">
			<table id="grid"></table>
		</div>
</div>
