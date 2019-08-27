<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty store.storeId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<div id="content-body">
	<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增仓库资料</c:when><c:otherwise>修改仓库资料</c:otherwise></c:choose></h3>
	
	<form class="form-horizontal admin-form" id="storeForm" name="storeForm" role="form" action="<%= BaseController.getCmdUrl("Store", "saveStore") %>" method="POST">
		<input type="hidden" id="storeId" name="storeId" value="${store.storeId}" />
		<input type="hidden" id="userId" name="userId" value="${store.userId}" />
		
		<!-- Retrieve store information -->
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>所在部门</label>
			<div class="col-md-4">
				<select id="deptId" name="deptId" class="form-control" validate="required: true">
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:forEach var="deptList" items="${deptList}">
						<option value="${deptList.deptId}" <c:if test="${store.deptId == deptList.deptId}">selected</c:if> >${deptList.deptFullName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group <qc:errors items="${formErrors}" path="storeNo" type="errorCls" />">
			<label class="col-md-4 control-label"><span class="required">*</span>仓库编号</label>
			<div class="col-md-4">
				<input class="form-control" id="storeNo" name="storeNo" value="${store.storeNo}" validate="required: true"/>
				<qc:errors items="${formErrors}" path="storeNo" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>仓库名称</label>
			<div class="col-md-4">
				<input class="form-control" id="storeName" name="storeName" value="${store.storeName}" validate="required: true"/>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-md-4 control-label">说明</label>
			<div class="col-md-4">
				<textarea class="form-control" rows="3" id="note" name="note">${store.note}</textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>状态</label>
			<div class="col-md-4">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<select id="state" name="state" class="form-control" validate="required: true">
					<c:forEach var="code" items="${stateCodes}">
						<option value="${code.codeId}" <c:if test="${code.codeId == store.state}">selected</c:if>>${code.codeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save"/></button>
				<a href="<%= BaseController.getCmdUrl("Store", "storeList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back"/></a>
			</div>
		</div>
	</form>
</div>