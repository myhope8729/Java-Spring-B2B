<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a data-role="button" data-icon="back" data-iconpos="left" data-theme="b" data-inline="true" class="ui-btn-right" href="<%= BaseController.getCmdUrl("Info", "infoList") %>"><qc:message code="system.common.btn.back"/></a>
</div>

<div role="main" class="ui-content mgt10">
	
	<form class="admin-form" action="<%=BaseController.getCmdUrl("Info", "saveInfo")%>" id="infoForm" method="post">
		<input type="hidden" name="billId" value="${info.billId}" /> 
		<input type="hidden" name="action" id="action" value="" /> 

		<div class="form-group col-sm-6 <qc:errors items="${formErrors}" path="itype" type="errorCls" />">
			<label class="control-label"><span class="required">*</span>类别</label>
			<qc:htmlSelect items="${fileTypeList}" itemValue="c1" itemLabel="c1"
				selValue='${info.itype }' isEmpty="true"
				emptyLabel="sc.please.select.s"
				id="itype" name="itype" customAttr='validate="required: true"' />
			<qc:errors items="${formErrors}" path="itype" />
		</div>

		<div class="form-group col-sm-6 <qc:errors items="${formErrors}" path="info" type="errorCls" />">
			<label class="control-label"><span class="required">*</span>标题</label>
			<input placeholder="标题" data-clear-btn="true" type="text" class="form-control" name=info id="info" value="${info.info}" validate="required: true"/>
			<qc:errors items="${formErrors}" path="info" />
		</div>

		<div class="form-group col-sm-6 <qc:errors items="${formErrors}" path="note" type="errorCls" />">
			<label class="control-label"><span class="required">*</span>内容</label>
			<textarea class="form-control" name="note" id="note" validate="required: true" rows=6 style="max-width: 100%">${info.note}</textarea>
			<qc:errors items="${formErrors}" path="note" />
		</div>

		<div class="form-group col-sm-6 <qc:errors items="${formErrors}" path="hbmark" type="errorCls" />">
			<label class="control-label"><span class="required">*</span>是否报名</label>
			<qc:htmlSelect items="${hbList}" itemValue="key" itemLabel="value"
				selValue='${info.hbmark}' isEmpty="true"
				emptyLabel="sc.please.select.s"
				id="hbmark" name="hbmark" customAttr='validate="required: true"' />
			<qc:errors items="${formErrors}" path="hbmark" />
		</div>
		
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="button"  data-mini="true"  data-theme="b" data-icon="check" id="btnSave"><qc:message code="system.common.btn.save"/></button>
				</div>
				<div class="ui-block-b">
					<button type="button" data-mini="true"  class="btn btn-orange" data-icon="action" id="btnSubmit"> <qc:message code="system.common.btn.submit" /></button>
				</div>
			</div>
		</div>			
	</form>
</div>
