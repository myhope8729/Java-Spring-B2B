<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty store.storeId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<div data-role="header" data-theme="b" >
   	<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增仓库资料</c:when><c:otherwise>修改仓库资料</c:otherwise></c:choose></h3>
    <a href="<%= BaseController.getCmdUrl("Store", "storeList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="b" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">
	
	<form class="admin-form" id="storeForm" name="storeForm" role="form" action="<%= BaseController.getCmdUrl("Store", "saveStore") %>" method="POST">
		<input type="hidden" id="storeId" name="storeId" value="${store.storeId}" />
		<input type="hidden" id="userId" name="userId" value="${store.userId}" />
		
		<!-- Retrieve store information -->
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>所在部门</label>
			<select id="deptId" name="deptId" validate="required: true">
				<option value=""><qc:message code="sc.please.select" /></option>
				<c:forEach var="deptList" items="${deptList}">
					<option value="${deptList.deptId}" <c:if test="${store.deptId == deptList.deptId}">selected</c:if> >${deptList.deptFullName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group col-md-6 <qc:errors items="${formErrors}" path="storeNo" type="errorCls" />">
			<label class="control-label"><span class="required">*</span>仓库编号</label>
			<input class="form-control" id="storeNo" name="storeNo" validate="required: true" value="${store.storeNo}"/>
			<qc:errors items="${formErrors}" path="storeNo" />
		</div>
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>仓库名称</label>
			<input class="form-control" placeholder="仓库名称" data-clear-btn="true" id="storeName" name="storeName"  validate="required: true" value="${store.storeName}"/>
		</div>
		
		<div class="form-group col-md-6">
			<label class="control-label">说明</label>
			<textarea class="form-control" rows="3" id="note" name="note">${store.note}</textarea>
		</div>
		
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>状态</label>
			<qc:codeList var="stateCodes" codeGroup="ST0000" />
			<select id="state" name="state" validate="required: true">
				<c:forEach var="code" items="${stateCodes}">
					<option value="${code.codeId}" <c:if test="${code.codeId == store.state}">selected</c:if>>${code.codeName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="submit"  data-mini="true"  data-theme="b" data-icon="check" ><qc:message code="system.common.btn.save"/></button>
				</div>
				<div class="ui-block-b">
					<a class="mgr-no" href="<%= BaseController.getCmdUrl("Store", "storeList") %>" data-mini="true" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>
			</div>
		</div>			
	</form>
</div>