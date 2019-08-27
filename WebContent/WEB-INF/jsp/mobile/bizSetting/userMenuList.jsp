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

<div data-role="header" data-theme="b" >
	<h3 class="page_title">用户菜单</h3>
</div>
	
<div role="main" class="ui-content">

	<table id="grid"></table>
	
</div>
