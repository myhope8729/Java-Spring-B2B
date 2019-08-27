<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty payType.payTypeId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<div data-role="header" data-theme="b" >
    <h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增付款方式</c:when><c:otherwise>修改付款方式</c:otherwise></c:choose></h3>
    <a href="<%= BaseController.getCmdUrl("PayType", "payTypeList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">

	<form class="admin-form" id="payTypeForm" name="payTypeForm" role="form" action="<%= BaseController.getCmdUrl("PayType", "savePayType") %>" method="POST">
		<input type="hidden" id="payTypeId" name="payTypeId" value="${payType.payTypeId}" />
		
		<div class="form-group col-md-6 <qc:errors items="${formErrors}" path="payTypeName" type="errorCls" />">
			<label for="payTypeName" class="control-label"><span class="required">*</span>付款方式</label>
			<input type="text" placeholder="付款方式" data-clear-btn="true"  name="payTypeName" id="payTypeName" value="${payType.payTypeName}" class="form-control" validate="required: true" autofocus />
			<qc:errors items="${formErrors}" path="payTypeName" />
		</div>
		<div class="form-group col-md-6 alignC">
			<label class="control-label"><span class="required">*</span>是否预付</label>
		    <fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
		        <input type="radio" name="prePayYn" id="prePayN" value="N" <c:if test="${payType.prePayYn == 'N' or empty payType}"> checked </c:if> />
		        <label for="prePayN">不是</label>
		        <input type="radio" name="prePayYn" id="prePayY" value="Y" <c:if test="${payType.prePayYn == 'Y'}"> checked </c:if> />
		        <label for="prePayY">是</label>
		    </fieldset>		
		</div>
		<div class="form-group col-xs-6">
			<label class="control-label"><span class="required">*</span>需要授权</label>
			<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
				<input type="radio" name="privYn" id="privN" value="N" <c:if test="${payType.privYn == 'N' or empty payType}"> checked </c:if> />
				<label for="privN">不需要</label>
				<input type="radio" name="privYn" id="privY" value="Y" <c:if test="${payType.privYn == 'Y'}"> checked </c:if> />
				<label for="privY">需要</label>
			</fieldset>
		</div>
		<div class="form-group col-xs-6 alignC">
			<label control-label"><span class="required">*</span>微信付款</label>
			<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
				<input type="radio" name="weixinYn" id="weixinN" value="N" <c:if test="${payType.weixinYn == 'N' or empty payType}"> checked </c:if> />
				<label for="weixinN">不是</label>
				<input type="radio" name="weixinYn" id="weixinY" value="Y" <c:if test="${payType.weixinYn == 'Y'}"> checked </c:if> />
				<label for="weixinY">是</label>
			</fieldset>
		</div>
		<div class="form-group col-md-6">
			<label class="control-label">说明</label>
			<textarea rows="3" placeholder="说明"  class="form-control" id="note" name="note">${payType.note}</textarea>
		</div>
		<div class="form-group col-md-6">
			<label control-label"><span class="required">*</span>状态</label></label>
			<qc:codeList var="stateCodes" codeGroup="ST0000" />
			<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${payType.state}' name="state"/>
		</div>
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="submit"  data-mini="true"  data-theme="b" data-icon="check" ><qc:message code="system.common.btn.save"/></button>
				</div>		
				<div class="ui-block-b">
					<a class="mgr-no"  href="<%= BaseController.getCmdUrl("PayType", "payTypeList") %>" data-mini="true" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.return" /></a>
				</div>
			</div>
		</div>
	</form>
</div>
