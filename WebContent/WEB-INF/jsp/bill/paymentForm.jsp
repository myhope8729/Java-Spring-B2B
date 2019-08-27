<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>
	
	<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("Payment", "savePayment") %>" id="paymentForm" method="post">
		 <input type="hidden" name="billId" value="${payment.billId}" />
		 <input type="hidden" name="custtypeId" id="custtypeId" value="${payment.custtypeId}" />
		 <input type="hidden" name="paytypeId" id="paytypeId" value="${payment.paytypeId}" />

		 <div class="form-group <qc:errors items="${formErrors}" path="custUserId" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>客户名称 </label>
		 	<div class="col-lg-4">
		 		<qc:htmlSelect items="${custList}" itemValue="custUserId" itemLabel="custUserName" selValue='${payment.custUserId}'
					isEmpty="true" emptyLabel="sc.please.select.s" cssClass="form-control wd200" id="custUserId" name="custUserId" customAttr='validate="required: true"' modelAttr="custTypeId" />
				<qc:errors items="${formErrors}" path="custUserId" />
		 	</div>
		</div>

		<div class="form-group <qc:errors items="${formErrors}" path="paymentType" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>预付款名称 </label>
		 	<div class="col-lg-4">
		 		<select class="form-control" id="paymentType" name="paymentType" validate="required: true">
					<option value=""><qc:message code="sc.please.select.s" /></option>
					<c:forEach var="paytype" items="${paytypeList}">
						<option value="${paytype.seqNo}" paytypeid="${paytype.payTypeId}" <c:if test="${paytype.c1 == payment.paymentType}">selected</c:if>>${paytype.payTypeNameDetail}</option>
					</c:forEach>
				</select>
				<qc:errors items="${formErrors}" path="paymentType" />
		 	</div>
		</div>
		
		<div class="form-group <qc:errors items="${formErrors}" path="arriveDate" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>到账日期 </label>
		 	<div class="col-lg-4">
				<input type="text" class="form-control date-picker" name="arriveDate" id="arriveDate" data-date-format="yyyy-mm-dd" value="${payment.arriveDate }" validate="required: true" />
				<qc:errors items="${formErrors}" path="arriveDate" />
		 	</div>
		</div>
		
		<div class="form-group <qc:errors items="${formErrors}" path="totalAmt" type="errorCls" />">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>金额(元)</label>
		 	<div class="col-lg-4">
				<input type="text" class="form-control" name="totalAmt" id="totalAmt" value="${payment.totalAmt}" validate="required: true, number:true" />
				<qc:errors items="${formErrors}" path="totalAmt" />
		 	</div>
		</div>
		
		<div class="form-group">
		    <div class="col-lg-offset-4 col-lg-4">
		      <button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.submit" /></button>
		      <a href="<%= BaseController.getCmdUrl("Payment", "paymentList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back"/></a>
		 	</div>
		</div> 
	</form>
</div>
