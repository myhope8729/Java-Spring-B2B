<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>

	<form class="form-horizontal admin-form" role="form" action="<%=BaseController.getCmdUrl("Info", "saveInfo")%>" id="infoForm" method="post">
		<input type="hidden" name="billId" value="${info.billId}" /> 
		<input type="hidden" name="action" id="action" value="" /> 

		<div class="form-group <qc:errors items="${formErrors}" path="itype" type="errorCls" />">
			<label class="col-lg-4 control-label"><span class="required">*</span>类别</label>
			<div class="col-lg-4">
				<qc:htmlSelect items="${fileTypeList}" itemValue="c1" itemLabel="c1"
					selValue='${info.itype }' isEmpty="true"
					emptyLabel="sc.please.select.s" cssClass="form-control"
					id="itype" name="itype" customAttr='validate="required: true"' />
				<qc:errors items="${formErrors}" path="itype" />
			</div>
		</div>

		<div class="form-group <qc:errors items="${formErrors}" path="info" type="errorCls" />">
			<label class="col-lg-4 control-label"><span class="required">*</span>标题</label>
			<div class="col-lg-4">
				<input type="text" class="form-control" name=info id="info" value="${info.info}" validate="required: true"/>
				<qc:errors items="${formErrors}" path="info" />
			</div>
		</div>

		<div class="form-group <qc:errors items="${formErrors}" path="note" type="errorCls" />">
			<label class="col-lg-4 control-label"><span class="required">*</span>内容</label>
			<div class="col-lg-4">
				<textarea class="form-control" name="note" id="note" validate="required: true" rows=6 style="max-width: 100%">${info.note}</textarea>
				<qc:errors items="${formErrors}" path="note" />
			</div>
		</div>

		<div class="form-group <qc:errors items="${formErrors}" path="hbmark" type="errorCls" />">
			<label class="col-lg-4 control-label"><span class="required">*</span>是否报名</label>
			<div class="col-lg-4">
				<qc:htmlSelect items="${hbList}" itemValue="key" itemLabel="value"
					selValue='${info.hbmark}' isEmpty="true"
					emptyLabel="sc.please.select.s" cssClass="form-control wd200"
					id="hbmark" name="hbmark" customAttr='validate="required: true"' />
				<qc:errors items="${formErrors}" path="hbmark" />
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-lg-offset-4 col-lg-4">
				<button type="button" class="btn btn-primary" id="btnSave"> <qc:message code="system.common.btn.save" /></button>
				<button type="button" class="btn btn-primary mgl20" id="btnSubmit"> <qc:message code="system.common.btn.submit" /></button>
				<a href="<%=BaseController.getCmdUrl("Info", "infoList")%>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back"/></a>
			</div>
		</div>
	</form>
</div>
