<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty custType.custTypeId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<div data-role="header" data-theme="b" >
    <h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增客户类别</c:when><c:otherwise>修改客户类别</c:otherwise></c:choose></h3>
    <a href="<%= BaseController.getCmdUrl("CustType", "custTypeList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">

	<form class="admin-form" id="custTypeForm" name="custTypeForm" role="form" action="<%= BaseController.getCmdUrl("CustType", "saveCustType") %>" method="POST">
		<input type="hidden" id="custTypeId" name="custTypeId" value="${custType.custTypeId}" />
		
		<div class="form-group col-md-12 <qc:errors items="${formErrors}" path="custTypeName" type="errorCls" />">
			<label class="control-label"><span class="required">*</span>客户类别</label>
	 		<input type="text" name="custTypeName" id="custTypeName" value="${custType.custTypeName}"  placeholder="客户类别" data-clear-btn="true" class="form-control" validate="required: true" autofocus />
	 		<qc:errors items="${formErrors}" path="custTypeName" />
		</div>
		<div class="form-group col-md-6">
			<label class="control-label">说明</label>
	 		<textarea rows="3" id="note" name="note" placeholder="说明"  class="form-control" rows="3">${custType.note}</textarea>
		 </div>
		 <div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>状态</label>
			<qc:codeList var="stateCodes" codeGroup="ST0000" />
			<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${custType.state}'  name="state"/>
		 </div>
		 
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="submit"  data-mini="true"  data-theme="b" data-icon="check" ><qc:message code="system.common.btn.save"/></button>
				</div>		
				<div class="ui-block-b">
					<a class="mgr-no" href="<%= BaseController.getCmdUrl("CustType", "custTypeList") %>" data-mini="true" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.return" /></a>
				</div>
			</div>
		</div>		 
	</form>
</div>