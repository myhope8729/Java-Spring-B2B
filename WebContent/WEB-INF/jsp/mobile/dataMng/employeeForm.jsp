<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a href="<%= BaseController.getCmdUrl("User", "employeeList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">
	
	<form class="form-horizontal admin-form"  action="<%= BaseController.getCmdUrl("User", "saveEmployee") %>" id="userForm" method="post">
		 <input type="hidden" name="empId" value="${emp.empId}" />
		 <input type="hidden" name="userId" value="${emp.userId}" />
		 
		 <div class="form-group  <qc:errors items="${formErrors}" path="empNo" type="errorCls" />">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>登录名</label>
		 	<div class="col-xs-8">
		 		<input type="text" name="empNo" value="${emp.empNo}" placeholder="登录名" data-clear-btn="true" validate="required: true" >
		 		<qc:errors items="${formErrors}" path="empNo" />
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="empName" type="errorCls" />">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>员工姓名</label>
		 	<div class="col-xs-8">		 	
		 		<input type="text" name="empName" value="${emp.empName}" placeholder="员工姓名" data-clear-btn="true" validate="required: true" >
		 		<qc:errors items="${formErrors}" path="empName" />
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="control-label col-xs-4 text-right">职责</label>
		 	<div class="col-xs-8">		 	
	 			<input type="text" name="empRole" placeholder="职责" data-clear-btn="true" value="${emp.empRole}">
	 		</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="pwd" type="errorCls" />">
		 	<label class="control-label col-xs-4 text-right"><c:if test="${empty emp.empId}"><span class="required">*</span></c:if>登录密码</label>
		 	<div class="col-xs-8">	 		
		 		<input type="password" name="pwd" id="pwd" placeholder="登录密码" data-clear-btn="true" value="" validate='<c:if test="${empty emp.empId}">required: true</c:if>' >
		 		<qc:errors items="${formErrors}" path="pwd" /> 
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="confirmPwd" type="errorCls" />">
		 	<label class="control-label col-xs-4 text-right"><c:if test="${empty emp.empId}"><span class="required">*</span></c:if>重复密码</label>
		 	<div class="col-xs-8">	 		
		 		<input type="password" name="confirmPwd" placeholder="重复密码" data-clear-btn="true" value="" class="form-control" validate="<c:if test="${empty emp.empId}">required: true, </c:if>equalTo:[pwd, '登录密码']" >
		 		<qc:errors items="${formErrors}" path="confirmPwd" />
		 	</div>
		 </div>
		 
		 <div class="form-group <qc:errors items="${formErrors}" path="deptId" type="errorCls" />">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>所在部门</label>
		 	<div class="col-xs-8">		 	
		 		<qc:htmlSelect items="${deptList}" itemValue="deptId" itemLabel="deptFullName" selValue='${emp.deptId}'
					isEmpty="true" emptyLabel='sc.please.select' name="deptId" customAttr='validate="required: true"' />
				<qc:errors items="${formErrors}" path="deptId" />
			</div>				
		</div>	
		 
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right">联系电话</label>
		 	<div class="col-xs-8">		 	
	 			<input type="text" name="telNo" placeholder="联系电话" data-clear-btn="true" value="${emp.telNo}" class="form-control" >
			</div>
		</div> 
		
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right">手机号码</label>
		 	<div class="col-xs-8">		 	
	 			<input type="text" name="mobileNo" placeholder="手机号码" data-clear-btn="true" value="${emp.mobileNo}" class="form-control" >
			</div>
		</div> 
		
		<c:if test="${not empty emp.empId}">
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right">QQ号码</label>
		 	<div class="col-xs-8">	
	 			<input type="text" name="qqNo" placeholder="QQ号码" data-clear-btn="true" value="${emp.qqNo}" class="form-control" >
			</div>
		</div> 
		
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right">Email</label>
		 	<div class="col-xs-8">	
	 			<input type="text" name="emailAddr" placeholder="Email" data-clear-btn="true" value="${emp.emailAddr}" class="form-control" >
			</div>
		</div> 
		</c:if>
		
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>状态 </label>
		 	<div class="col-xs-8">	
		 		<qc:codeList var="stateList" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateList}" itemValue="codeId" itemLabel="codeName" selValue='${emp.state}'
				   id="state" name="state" customAttr='validate="required: true"' />
			</div>
		</div>
		
		<div class="form-actions">
			<button class="btn btn-primary" type="submit"  data-theme="b"><qc:message code="system.common.btn.submit"/></button>
			<a class="btn btn-default" href="<%= BaseController.getCmdUrl("User", "employeeList") %>"  data-role="button" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		</div>			
	</form>
</div>