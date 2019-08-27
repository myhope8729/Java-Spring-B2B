<%--
platform 	: COPYRIGHT(c) KPC EOS Backoffice 2017 By RKRK
----------------------------------
 --%>
<%@ page import="com.kpc.eos.core.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script>
	var selMsg = "<qc:message code='sc.please.select' />";
	var reallySaveMsg = "<qc:message code='system.alert.modify' />";
	var noSaveData = "<qc:message code='sc.no.select.rows' />";
</script>
<div class="admin_body">
	<form name="menuListFrm" id="menuListFrm" onsubmit="reloadGrid();return false;">
		<input id="usr0" name="usr0" value="" type="hidden"/>
	</form>
	<h3 class="page_title">菜单资料</h3>
	
	<div class="action_bar text-right">
		<button type="button" class="btn btn-primary" onclick="saveRows()"><qc:message code='system.common.btn.save' /></button>
		<button type="button" class="btn btn-default" onclick="reset();"><qc:message code='system.common.btn.cancel' /></button>
	</div>
	
	<div class="clear">
		<table id="grid"></table>
	</div>
	
</div>