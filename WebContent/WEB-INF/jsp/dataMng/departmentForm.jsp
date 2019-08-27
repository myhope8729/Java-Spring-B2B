<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<form name="departmentListForm" id="departmentListForm" onsubmit="reloadGrid();return false;"></form>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>
	
	<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("Dept", "saveDepartment") %>&deptId=${dept.deptId}" id="departmentForm" method="post">
		 <div class="form-group">
		 	<label class="col-lg-4 control-label">上级部门</label>
		 	<div class="col-lg-4">
		 		<select name="upperDeptId" id="upperDeptId" class="form-control" >
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:forEach var="item" items="${deptList}">
					<option value="${item.deptId}" <c:if test="${dept.upperDeptId == item.deptId}"> selected</c:if>>(${item.deptNo}) ${item.deptName}</option>
					</c:forEach>
				</select>
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="deptNo" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>部门编号</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="deptNo" value="${dept.deptNo}" class="form-control" validate="required: true" />
		 		<qc:errors items="${formErrors}" path="deptNo" />
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>部门名称</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="deptName" value="${dept.deptName}" class="form-control" validate="required: true" />
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<div class="col-lg-offset-4 col-lg-4">
		 		<div class="checkbox">
		 			<label>
		 				<input type="checkbox" name="accountYn" value="Y" <c:if test="${dept.accountYn == 'Y'}"> checked="checked"</c:if> /> 核算单位
		 			</label>
		 		</div>
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>状态 </label>
		 	<div class="col-lg-4">
		 		<qc:codeList var="stateList" codeGroup="ST0000" />
				<select class="form-control" id="state" name="state" validate="required: true">
					<c:forEach var="state" items="${stateList}">
					<option value="${state.codeId}"<c:if test="${dept.state == state.codeId}"> selected</c:if>>${state.codeName}</option>
					</c:forEach>
				</select>
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label">备注 </label>
		 	<div class="col-lg-4">
		 		<input class="form-control" name="note" value="${dept.note}" />
		 	</div>
		 </div>
		 
		 <div class="form-group">
		    <div class="col-lg-offset-4 col-lg-4">
		      <button type="submit" class="btn btn-primary">提      交</button>
		      <a href="<%= BaseController.getCmdUrl("Dept", "departmentList") %>" class="btn btn-default pull-right" id="btnBack">返      回</a>
		    </div>
		 </div>
	</form>
</div>