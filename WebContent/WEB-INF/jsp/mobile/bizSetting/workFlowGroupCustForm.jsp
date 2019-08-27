<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div data-role="header" data-theme="b" >
   	<h3 class="page_title">设置审批组 处理人</h3>
    <a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowGroupList&workFlowId=" + request.getParameter("workFlowId")) %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="b" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">
	<form class="admin-form" id="workFlowGroupCustForm" name="workFlowGroupCustForm" role="form" action="<%= BaseController.getCmdUrl("WorkFlow", "saveWorkFlowGroupCust") %>" method="POST">
		<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlowGroup.workFlowId}"/>
		<input type="hidden" id="seqNo" name="seqNo" value="${workFlowGroup.seqNo}" />
		
		<div class="action_bar">
			<div class="col-md-12 text-center">
				<label class="control-label">审批流程：&nbsp; 类别 = ${workFlowGroup.workFlowTypeName} ,&nbsp;&nbsp;&nbsp;名称 = ${workFlowGroup.workFlowName} ,&nbsp;&nbsp;&nbsp; 审批分组: &nbsp;名称 = ${workFlowGroup.workFlowGroupName}</label>
			</div>
		</div>
		
		<div class="form-group">
			<c:forEach var="customer" items="${customerList}">
				<div class="col-xs-6" style="white-space:nowrap; overflow:hidden">
					<input type="checkbox" value="${customer.custId}" id="${customer.custId}" name="custShortNameList" <c:if test="${customer.checked eq 'Y'}">checked</c:if> />
					<label class="control-label" for="${customer.custId}">${customer.custShortName}</label>
				</div>
			</c:forEach>
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