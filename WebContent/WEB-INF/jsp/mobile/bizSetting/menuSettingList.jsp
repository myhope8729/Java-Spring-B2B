<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var selRow = "<qc:message code='system.alert.select' />";
	var selMsg = "<qc:message code='sc.please.select' />";
	var reallySaveMsg = "<qc:message code='system.alert.modify' />";
	var noSaveData = "<qc:message code='sc.no.select.rows' />";
</script>

<div data-role="header" data-theme="b" >
	<h3 class="page_title">菜单设置</h3> 
    <a href="javascript:editRow();"  data-role="button" data-icon="edit" data-iconpos="left" data-theme="b" data-inline="true" class="ui-btn-left"><qc:message code="system.common.btn.update"/></a>   
    <a href="<%= BaseController.getCmdUrl("MenuSetting", "menuSettingList") %>"  data-role="button" data-icon="refresh" data-iconpos="left" data-theme="b" data-inline="true" class="ui-btn-right"><qc:message code="system.common.btn.setting.confirm"/></a>
</div>
	
<div role="main" class="ui-content">

	<table id="grid"></table>
	<div id="gridpager"></div>
	
</div>
