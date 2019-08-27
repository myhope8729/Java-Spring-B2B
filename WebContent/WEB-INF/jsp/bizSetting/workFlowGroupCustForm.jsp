<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<h3 class="page_title">设置审批组 处理人</h3>

<div id="content-body">
	<form class="form-horizontal admin-form" id="workFlowGroupCustForm" name="workFlowGroupCustForm" role="form" action="<%= BaseController.getCmdUrl("WorkFlow", "saveWorkFlowGroupCust") %>" method="POST">
		<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlowGroup.workFlowId}"/>
		<input type="hidden" id="seqNo" name="seqNo" value="${workFlowGroup.seqNo}" />
		
		<div class="action_bar row">
			<div class="col-lg-12 text-center">
				<label class="control-label">审批流程：&nbsp; 流程类别 = ${workFlowGroup.workFlowTypeName} ,&nbsp;&nbsp;&nbsp;流程名称 = ${workFlowGroup.workFlowName} ,&nbsp;&nbsp;&nbsp; 审批分组: &nbsp;分组名称 = ${workFlowGroup.workFlowGroupName}</label>
			</div>
		</div>
		
		<div class="form-group">
			<c:forEach var="customer" items="${customerList}">
				<div class="col-lg-2 col-lg-offset-1" style="white-space:nowrap; overflow:hidden">
					<input type="checkbox" value="${customer.custId}" id="${customer.custId}" name="custShortNameList" <c:if test="${customer.checked eq 'Y'}">checked</c:if> />
					<label class="control-label" for="${customer.custId}">${customer.custShortName}</label>
				</div>
			</c:forEach>
		</div>
		<div class="form-group">
			<div class="col-lg-offset-4 col-lg-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save"/></button>
				<a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowGroupList&workFlowId=" + request.getParameter("workFlowId")) %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
</div>