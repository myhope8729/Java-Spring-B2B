<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>
	<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("User", "saveEmployerPriv") %>" id="privForm" method="post">
		<input type="hidden" name="empId" value="<%= request.getParameter("empId") %>" />
		<div class="ui-jqgrid" style="">
			<table class="table table-bordered ui-jqgrid-htable">
				<thead>
					<tr class="ui-jqgrid-labels" >
						<th style="width: 80px; text-align: center"><input type="checkbox" class="check-all" name="checkAll" value="1" /></th>
						<th>功能名称</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${empMenuRightList}" var="item">
					<tr class='um${item.upperMenuId} <c:if test="${item.level == 2}">um-sub</c:if>'<c:if test="${item.level == 1}"> refId="${item.menuId}"</c:if>>
						<td style="text-align:center;">	
							<input type="checkbox" name="menuId[]"  id="menu_${item.menuId}" value="${item.menuId}" <c:if test="${not empty item.assigned}"> checked="checked"</c:if> />
						</td>
						<td><span<c:if test="${item.level == 2}"> style="padding-left: 40px"</c:if>>${item.menuUserName}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row-fluid">
		    <div class="col-lg-offset-5 col-lg-2">
		      <button type="submit" class="btn btn-primary">提      交</button>
		      <a href="<%= BaseController.getCmdUrl("User", "employerPrivList") %>" class="btn btn-default pull-right" id="btnBack">返      回</a>
		    </div>
		  </div>
	</form>
</div>