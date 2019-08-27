<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<form name="departmentListForm" id="departmentListForm" onsubmit="reloadGrid();return false;"></form>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
</div>

<div role="main" class="ui-content mgt10">
	
	<form class="form-horizontal admin-form" action="<%= BaseController.getCmdUrl("Dept", "saveDepartment") %>&deptId=${dept.deptId}" id="departmentForm" method="post">
		 <div class="form-group">
	 		<label class="control-label col-xs-4 text-right"><span class="required">*</span>上级部门</label>
	 		<div class="col-xs-8">
		 		<select name="upperDeptId" id="upperDeptId" validate="required: true">
					<option value=""><qc:message code="sc.please.select.s" /></option>
					<c:forEach var="item" items="${deptList}">
					<option value="${item.deptId}" <c:if test="${dept.upperDeptId == item.deptId}"> selected</c:if>>(${item.deptNo}) ${item.deptName}</option>
					</c:forEach>
				</select>
			</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="deptNo" type="errorCls" />">
		 	<label for="deptNo" class="control-label col-xs-4 text-right"><span class="required">*</span>部门编号</label>
		 	<div class="col-xs-8">
		 		<input type="text" name="deptNo" value="${dept.deptNo}" placeholder="部门编号" data-clear-btn="true" class="form-control" validate="required: true" />
			 	<qc:errors items="${formErrors}" path="deptNo" />
			 </div>
		 </div>
		 
		 <div class="form-group">
		 	<label for="deptName" class="control-label col-xs-4 text-right"><span class="required">*</span>部门名称</label>
	 		<div class="col-xs-8">
	 			<input type="text" name="deptName" value="${dept.deptName}"  placeholder="部门名称" data-clear-btn="true"  class="form-control" validate="required: true" />
			</div>
		 </div>
		 
		 <div class="form-group">
			 <div class="col-xs-4"></div>
			 <div class="col-xs-8">
			 	 <label class="control-label">
	 				<input type="checkbox" name="accountYn" value="Y" <c:if test="${dept.accountYn == 'Y'}"> checked="checked"</c:if> > 核算单位
	 			</label>
			 </div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>状态 </label>
		 	<div class="col-xs-8">
		 		<qc:codeList var="stateList" codeGroup="ST0000" />
				<select id="state" name="state" validate="required: true">
					<c:forEach var="state" items="${stateList}">
					<option value="${state.codeId}"<c:if test="${dept.state == state.codeId}"> selected</c:if>>${state.codeName}</option>
					</c:forEach>
				</select>
			</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="control-label col-xs-4 text-right">备注 </label>
		 	<div class="col-xs-8">
	 			<input  placeholder="备注" data-clear-btn="true"  class="form-control" name="note" value="${dept.note}" />
		 	</div>
		 </div>
		 
		<div class="form-actions">
			<button type="submit" data-theme="b" class="btn btn-primary"><qc:message code="system.common.btn.submit"/></button>
			<a class="mgr-no btn btn-default" href="<%= BaseController.getCmdUrl("Dept", "departmentList") %>" data-role="button" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		</div>		 
	</form>
</div>