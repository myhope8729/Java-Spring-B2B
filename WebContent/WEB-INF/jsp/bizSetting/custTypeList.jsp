<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin_body">
	<h3 class="page_title">客户类别目录
		<div class="action_bar text-right">
			<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("CustType", "custTypeForm") %>"><qc:message code="system.common.btn.new" /></a>
		</div>		
	</h3>
	<form name="custTypeListForm" id="custTypeListForm" onsubmit="reloadGrid();return false;"></form>
	
	<div class="clear">
		<table id="grid"></table> 
		<div id="gridpager"></div>
	</div>
</div>