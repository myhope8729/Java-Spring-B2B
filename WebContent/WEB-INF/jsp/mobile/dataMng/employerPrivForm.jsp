<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a href="<%= BaseController.getCmdUrl("User", "employerPrivList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content">
	
	<form class="admin-form" role="form" action="<%= BaseController.getCmdUrl("User", "saveEmployerPriv") %>" id="privForm" method="post">
		<input type="hidden" name="empId" value="<%= request.getParameter("empId") %>" />
		
		<div class="ui-jqgrid ui-corner-all">
			<table class="table nobottom table-bordered">
				<thead>
					<tr class="ui-jqgrid-labels">
						<th style="width: 55px;"><input type="checkbox" class="check-all" name="checkAll" value="1" /></th>
						<th>功能名称</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${empMenuRightList}" var="item">
				<tr class='um${item.upperMenuId} <c:if test="${item.level == 2}">um-sub</c:if>'<c:if test="${item.level == 1}"> refId="${item.menuId}"</c:if>>
					<td style="text-align:center;">	
							<input type="checkbox" name="menuId[]"  id="menu_${item.menuId}" value="${item.menuId}" <c:if test="${not empty item.assigned}"> checked="checked"</c:if> />
						</td>
						<td><span<c:if test="${item.level == 2}"> style="padding-left: 60px"</c:if>>${item.menuUserName}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		  
		<div class="form-actions">
			<button type="submit" class="btn btn-primary" data-theme="b" ><qc:message code="system.common.btn.submit"/></button>
			<a class="mgr-no btn btn-default" href="<%= BaseController.getCmdUrl("User", "employerPrivList") %>" data-role="button" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		</div>		
				  
	</form>
</div>