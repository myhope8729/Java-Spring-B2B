<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty workFlowId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<div data-role="header" data-theme="b" >
   	<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增审批组</c:when><c:otherwise>修改审批组</c:otherwise></c:choose></h3>
    <a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowGroupList&workFlowId=" + request.getParameter("workFlowId")) %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="b" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">

	<form class="admin-form" id="workFlowGroupForm" name="workFlowGroupForm" role="form" action="<%= BaseController.getCmdUrl("WorkFlow", "saveWorkFlowGroup") %>" method="POST">
		<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlowId}"/>
		<input type="hidden" id="seqNo" name="seqNo" value="${seqNo}" />
		
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>分组名称</label>
			<input class="form-control" placeholder="分组名称" data-clear-btn="true" id="workFlowGroupName" name="workFlowGroupName" validate="required: true" value="${workFlowGroup.workFlowGroupName}" />
		</div>
		<div class="form-group col-md-6">
			<label class="control-label">处理条件</label>
			<input class="form-control" placeholder="处理条件" data-clear-btn="true" id="cond" name="cond" value="${workFlowGroup.cond}" />
		</div>
		<div class="form-group col-md-6">
			<label class="control-label">备注</label>
			<textarea rows="3" class="form-control" id="note" name="note">${workFlowGroup.note}</textarea>
		</div>
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>状态</label>
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${workFlowGroup.state}' name="state"/>
		</div>
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="submit"  data-mini="true"  data-theme="b" data-icon="check" ><qc:message code="system.common.btn.save"/></button>
				</div>
				<div class="ui-block-b">
					<a class="mgr-no" href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowGroupList&workFlowId=" + request.getParameter("workFlowId")) %>" data-mini="true" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>
			</div>
		</div>			
	</form>
</div>