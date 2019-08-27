<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<style>
	tr.ding {background-color: #F6FCF3;}
	tr.ding td{color: rgb(0, 0, 128);}
</style>
<div id="order-form-wrap" class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>
	<form id="pbListFrm" class="searchForm">
		<input type="hidden" name="hostUserId" id="hostUserId" value="${sc.hostUserId }" />
		<input type="hidden" name="custUserId" id="custUserId" value="${sc.custUserId }" />
	</form>
	<div class="info-box">
		<div class="box-body no-padding">
			<table id=pbGrid ajaxUrl="<%= BaseController.getCmdUrl("Payment", "paymentBillListAjax") %>" searchForm="#pbListFrm"></table>
			<div id="pbGridPager"></div>
		</div>
	</div>
</div>
