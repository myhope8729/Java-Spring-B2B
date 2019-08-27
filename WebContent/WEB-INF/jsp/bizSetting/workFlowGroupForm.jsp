<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty workFlowId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增审批组</c:when><c:otherwise>修改审批组</c:otherwise></c:choose></h3>

<div id="content-body">
	<form class="form-horizontal admin-form" id="workFlowGroupForm" name="workFlowGroupForm" role="form" action="<%= BaseController.getCmdUrl("WorkFlow", "saveWorkFlowGroup") %>" method="POST">
		<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlowId}"/>
		<input type="hidden" id="seqNo" name="seqNo" value="${seqNo}" />
		
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>分组名称</label>
			<div class="col-lg-4">
				<input class="form-control" id="workFlowGroupName" name="workFlowGroupName" validate="required: true" value="${workFlowGroup.workFlowGroupName}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">处理条件</label>
			<div class="col-lg-4">
				<qc:htmlSelect items="${itemType}" itemValue="seqNo" itemLabel="c1" selValue='${workFlowGroup.cond}' 
					cssClass="form-control" id="cond" name="cond"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">备注</label>
			<div class="col-lg-4">
				<textarea rows="3" class="form-control" id="note" name="note">${workFlowGroup.note}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>状态</label>
			<div class="col-lg-4">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${workFlowGroup.state}' cssClass="form-control" name="state"/>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-offset-4 col-lg-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save"/></button>
				<a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowGroupList&workFlowId=" + request.getParameter("workFlowId")) %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
</div>