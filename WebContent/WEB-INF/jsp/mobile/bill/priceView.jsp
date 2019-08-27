<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var gridData = ${gridData}; 
	var billItems = ${itemList};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a data-theme="b" data-role="button" data-icon="delete" data-inline="true" class="ui-btn-right" data-iconpos="left" href="javascript:window.close();"> 
    	<qc:message code="system.common.btn.close" /> 
    </a>
</div>

<div role="main" class="ui-content">
	<form class="form-horizontal admin-form" role="form" 
		action="<%= BaseController.getCmdUrl("Price", "savePrice") %>" id="userItemsForm" class="searchForm" method="post">
		<input type="hidden" name="billId" value="${bill.billId }"/>
		<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="false" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
			<h3>单据详情</h3>	
			<div>
				<table class="table table-bordered dataTable01">
					<colgroup>
						<col width="35%" />
						<col width="65%" />
			 		</colgroup>
					<tbody>
						<tr>
							<th>单据编号</th>
							<td>${bill.billId}</td>
						</tr>
						<tr>
							<th>单据状态</th>
							<td>${bill.stateName}</td>
						</tr>
						<tr>
							<th>调价说明</th>
							<td>${bill.custUserName}</td>
						</tr>
					</tbody>
				</table>				
			</div>	
		</div>
		
		<div class="table-responsive">
			<table id="grid"></table>
		</div>
		
		<table id="procGrid"></table>
		
	</form>
</div>
