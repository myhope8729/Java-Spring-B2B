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

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">菜单资料</h3>
</div>

<div role="main" class="ui-content">
	<form name="menuListFrm" id="menuListFrm" onsubmit="reloadGrid();return false;">
		<input id="usr0" name="usr0" value="" type="hidden"/>
	</form>
	
	<table id="grid"></table>
	<!-- <div id="gridpager"></div>		 -->	
</div>