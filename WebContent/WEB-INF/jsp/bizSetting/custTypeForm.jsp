<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty custType.custTypeId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增客户类别</c:when><c:otherwise>修改客户类别</c:otherwise></c:choose></h3>

<div id="content-body">
	<form class="form-horizontal admin-form" id="custTypeForm" name="custTypeForm" role="form" action="<%= BaseController.getCmdUrl("CustType", "saveCustType") %>" method="POST">
		<input type="hidden" id="custTypeId" name="custTypeId" value="${custType.custTypeId}" />
		<div class="form-group <qc:errors items="${formErrors}" path="custTypeName" type="errorCls" />">
			<label class="col-lg-4 control-label"><span class="required">*</span>客户类别</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="custTypeName" id="custTypeName" value="${custType.custTypeName}" class="form-control" validate="required: true" autofocus />
		 		<qc:errors items="${formErrors}" path="custTypeName" />
		 	</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">说明</label>
			<div class="col-lg-4">
		 		<textarea rows="3" id="note" name="note" class="form-control" rows="3">${custType.note}</textarea>
		 	</div>
		 </div>
		 <div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>状态</label></label>
			<div class="col-lg-4">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${custType.state}' cssClass="form-control" name="state"/>
			</div>
		 </div>
		 
		 <div class="form-group">
		 	<div class="col-lg-offset-4 col-lg-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save"/></button>
				<a href="<%= BaseController.getCmdUrl("CustType", "custTypeList") %>" class="btn btn-default pull-right"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
</div>