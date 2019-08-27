<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<h3 class="page_title">仓库目录
	<div class="action_bar text-right">
		<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("Store", "storeForm") %>"><qc:message code="system.common.btn.new" /></a>
	</div>
</h3>
<div class="admin_body">
	<form id="storeSearchForm" name="storeSearchForm" class="form-inline" onsubmit="reloadGrid();return false;"></form>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>
