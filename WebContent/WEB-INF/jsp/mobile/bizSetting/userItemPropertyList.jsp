<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript" src="<c:url value="/js/lib/jquery/jqGrid/plugins/jquery.tablednd.js"/>"></script>
<script type="text/javascript">
	var messages = {
		duplicateMsg : "<qc:message code='useritem.property.duplicateprice' />",
		selRow : "<qc:message code='system.alert.select' />",
		selMsg : "<qc:message code='sc.please.select' />",
		reallySaveMsg : "<qc:message code='system.alert.modify' />",
		noSaveData : "<qc:message code='sc.no.select.rows' />"
	};
</script>

<!-- header section -->	
<div data-role="header" data-position="overlay" data-fullscreen="true" data-theme="b" >
	<a href="javascript:editRow();"  data-role="button" data-icon="edit" data-iconpos="left" data-theme="b" data-inline="true" class="ui-btn-left"><qc:message code="system.common.btn.change"/></a>  
    <h3 class="page_title">商品资料设置</h3>
	<a data-role="button" data-icon="back" data-mini="true" class="ui-btn-right" href="<%= BaseController.getCmdUrl("UserItem", "userItemList") %>"  id="btnBack"><qc:message code="system.common.btn.back"/></a> 
</div>

<div role="main" class="ui-content">
	<table id="grid"></table>
	<div id="gridpager"></div>
</div>