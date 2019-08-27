<%--
platform 	: COPYRIGHT(c) KPC EOS Backoffice 2017
----------------------------------
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script>
	var obj = '';
	var selMsg = "<qc:message code='sc.please.select' />";
	var reallySaveMsg = "<qc:message code='system.alert.modify' />";
	var noSaveData = "<qc:message code='sc.no.select.rows' />";
	var noSelectUpper = "<qc:message code='system.code.upper.noselect' />";
</script>

<h3 class="page_title">作用 URL</h3>
<div class="admin_body">
	<form class="form-horizontal admin-form" id="menuDataForm" name="menuDataForm" role="form" action="saveBizData();return false;"> 
		<div class="col-md-4">
			<table id="grid">
			</table>
		</div>
		
		<div class="col-md-8">
			<table id="gridContent">
			</table>
			
			<div class="form-group">
				<div class="col-xs-4">
					<button type="button" class="btn btn-primary" onclick="addCode();"><qc:message code="system.common.btn.new" /></button>
					<button type="button" class="btn btn-default" onclick="resetOk();"><qc:message code="system.common.btn.cancel" /></button>					
				</div>
				<div class="alignR col-xs-8">
					<button type="button" class="btn btn-primary" onclick="saveData();"><qc:message code="system.common.btn.save" /></button>
				</div>
			</div>
		</div>
	</form>
</div>
