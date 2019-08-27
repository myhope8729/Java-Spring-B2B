<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty payType.payTypeId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增付款方式</c:when><c:otherwise>修改付款方式</c:otherwise></c:choose></h3>

<div class="admin_body">
	<form class="form-horizontal admin-form" id="payTypeForm" name="payTypeForm" role="form" action="<%= BaseController.getCmdUrl("PayType", "savePayType") %>" method="POST">
		<input type="hidden" id="payTypeId" name="payTypeId" value="${payType.payTypeId}" />
		
		<div class="form-group <qc:errors items="${formErrors}" path="payTypeName" type="errorCls" />">
			<label class="col-lg-4 control-label"><span class="required">*</span>付款方式</label>
			<div class="col-lg-4">
				<input type="text" name="payTypeName" id="payTypeName" value="${payType.payTypeName}" class="form-control" validate="required: true" autofocus />
				<qc:errors items="${formErrors}" path="payTypeName" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>是否预付</label>
			<div class="col-lg-2">
				<input type="radio" name="prePayYn" id="prePayN" value="N" <c:if test="${payType.prePayYn == 'N' or empty payType}"> checked </c:if> />
				<label for="prePayYnN">不是</label>
			</div>
			<div class="col-lg-2">
				<input type="radio" name="prePayYn" id="prePayY" value="Y" <c:if test="${payType.prePayYn == 'Y'}"> checked </c:if> />
				<label for="prePayYnN">是</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>需要授权</label>
			<div class="col-lg-2">
				<input type="radio" name="privYn" id="privN" value="N" <c:if test="${payType.privYn == 'N' or empty payType}"> checked </c:if> />
				<label for="prePayYnN">不需要</label>
			</div>
			<div class="col-lg-2">
				<input type="radio" name="privYn" id="privY" value="Y" <c:if test="${payType.privYn == 'Y'}"> checked </c:if> />
				<label for="prePayYnN">需要</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>微信付款</label>
			<div class="col-lg-2">
				<input type="radio" name="weixinYn" id="weixinN" value="N" <c:if test="${payType.weixinYn == 'N' or empty payType}"> checked </c:if> />
				<label for="prePayYnN">不是</label>
			</div>
			<div class="col-lg-2">
				<input type="radio" name="weixinYn" id="privY" value="Y" <c:if test="${payType.weixinYn == 'Y'}"> checked </c:if> />
				<label for="prePayYnN">是</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">说明</label>
			<div class="col-lg-4">
				<textarea rows="3" class="form-control" id="note" name="note">${payType.note}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>状态</label></label>
			<div class="col-lg-4">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${payType.state}' cssClass="form-control" name="state"/>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-offset-4 col-lg-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save"/></button>
				<a href="<%= BaseController.getCmdUrl("PayType", "payTypeList") %>" class="btn btn-default pull-right"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
</div>
