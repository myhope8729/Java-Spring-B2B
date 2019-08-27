<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>

<form name="deliveryAddrListForm" id="deliveryAddrListForm" onsubmit="reloadGrid();return false;"></form>
<h3 class="page_title">收货地址目录
	<div class="action_bar text-right">
		<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("DeliveryAddr", "deliveryAddrForm") %>"><qc:message code="system.common.btn.new"/> </a>
	</div>
</h3>
<div class="admin_body">
	
	<div class="clear">
		<table id="grid"></table> 
		<div id="gridpager"></div> 
	</div>
</div>

