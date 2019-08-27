<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>
	
	<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("User", "saveEmployee") %>" id="userForm" method="post">
		 <input type="hidden" name="empId" value="${emp.empId}" />
		 <input type="hidden" name="userId" value="${emp.userId}" />
		 <input type="hidden" name="firstMark" value="${emp.firstMark}" />
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="empNo" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>登录名</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="empNo" value="${emp.empNo}" class="form-control" validate="required: true" <c:if test="${emp.firstMark eq 'Y'}">readonly</c:if>>
		 		<qc:errors items="${formErrors}" path="empNo" />
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="empName" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>员工姓名</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="empName" value="${emp.empName}" class="form-control" validate="required: true" >
		 		<qc:errors items="${formErrors}" path="empName" />
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label">职责</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="empRole" value="${emp.empRole}" class="form-control" >
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="pwd" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><c:if test="${empty emp.empId}"><span class="required">*</span></c:if>登录密码</label>
		 	<div class="col-lg-4">
		 		<input type="password" name="pwd" id="pwd" value="${emp.pwd}" class="form-control" validate='<c:if test="${empty emp.empId}">required: true</c:if>' >
		 		<qc:errors items="${formErrors}" path="pwd" /> 
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="confirmPwd" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><c:if test="${empty emp.empId}"><span class="required">*</span></c:if>重复密码</label>
		 	<div class="col-lg-4">
		 		<input type="password" name="confirmPwd" value="${emp.confirmPwd}" class="form-control" validate="<c:if test="${empty emp.empId}">required: true, </c:if>equalTo:[pwd, '登录密码']" >
		 		<qc:errors items="${formErrors}" path="confirmPwd" />
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="deptId" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>所在部门</label>
		 	<div class="col-lg-4">
		 		<qc:htmlSelect items="${deptList}" itemValue="deptId" itemLabel="deptFullName" selValue='${emp.deptId}'
					isEmpty="true" emptyLabel='sc.please.select' cssClass="form-control" name="deptId" customAttr='validate="required: true"' />
				<qc:errors items="${formErrors}" path="deptId" />
			</div>
		</div>	
		 
		<div class="form-group">
		 	<label class="col-lg-4 control-label">联系电话</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="telNo" value="${emp.telNo}" class="form-control" >
		 	</div>
		</div> 
		
		<div class="form-group">
		 	<label class="col-lg-4 control-label">手机号码</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="mobileNo" value="${emp.mobileNo}" class="form-control" >
		 	</div>
		</div> 
		
		<c:if test="${not empty emp.empId}">
		<div class="form-group">
		 	<label class="col-lg-4 control-label">QQ号码</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="qqNo" value="${emp.qqNo}" class="form-control" >
		 	</div>
		</div>
		
		<div class="form-group">
		 	<label class="col-lg-4 control-label">Email</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="emailAddr" value="${emp.emailAddr}" class="form-control" >
		 	</div>
		</div> 
		</c:if>
		
		<div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>状态 </label>
		 	<div class="col-lg-4">
		 		<qc:codeList var="stateList" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateList}" itemValue="codeId" itemLabel="codeName" selValue='${emp.state}'
					cssClass="form-control" id="state" name="state" customAttr='validate="required: true"' />
		 	</div>
		</div>
		
		<div class="form-group">
		    <div class="col-lg-offset-4 col-lg-4">
		      <button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save"/></button>
		      <a href="<%= BaseController.getCmdUrl("User", "employeeList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		 	</div>
		</div> 
	</form>
</div>