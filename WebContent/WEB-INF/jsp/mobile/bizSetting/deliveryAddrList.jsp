<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>

<form name="deliveryAddrListForm" id="deliveryAddrListForm" onsubmit="reloadGrid();return false;"></form>

<!-- header section -->	
<div data-role="header"  data-theme="b" >
    <h3 class="page_title">收货地址目录</h3>
</div>	

<div role="main" class="ui-content">

	<table id="grid"></table> 
	<!-- <div id="gridpager"></div>  -->
	
	<div class="form-group form-actions">
 		<a data-theme="b" data-role="button" data-icon="plus" data-inline="true" class="btn btn-primary btn-fullwidth" data-iconpos="left" href="<%= BaseController.getCmdUrl("DeliveryAddr", "deliveryAddrForm") %>">
 			<qc:message code="system.common.btn.new" />
 		</a>
	</div>		
</div>

