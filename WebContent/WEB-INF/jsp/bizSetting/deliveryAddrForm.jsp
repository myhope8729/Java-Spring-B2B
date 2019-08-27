<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty deliveryAddr.addrId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增收货地址</c:when><c:otherwise>修改收货地址</c:otherwise></c:choose></h3>
<div id="content-body">
	<form class="form-horizontal admin-form" id="deliveryAddrForm" name="deliveryAddrForm" role="form" action="<%= BaseController.getCmdUrl("DeliveryAddr", "saveDeliveryAddr") %>" method="POST">
		<input type="hidden" id="addrId" name="addrId" value="${deliveryAddr.addrId}" />
		
		<!-- Retrieve delivery address information -->
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>所在地</label>
			<div class="col-md-4 col-sm-8 col-xs-6 addr_select" style="padding:0px;">
				<div class="col-md-4 col-xs-12" id="provId">
					<select name="provId" id="provId" class="locationList form-control">
						<c:forEach var="province" items="${provList}">
							<option value="${province.addressId}" data-level="${province.addressLevel}" <c:if test="${province.addressId == deliveryAddr.provinceId}">selected</c:if> >${province.addressName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-4 col-xs-12 <c:out value="${isProvince?'':'hidden'}"/>" id="cityId">
					<select name="cityId" class="locationList form-control">
						<c:forEach var="city" items="${cityList}">
							<option value="${city.addressId}" data-level="${city.addressLevel}" <c:if test="${city.addressId == deliveryAddr.cityId}">selected</c:if>>${city.addressName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-4 col-xs-12" id="areaId">
					<select name="locationId" id="locationId" class="form-control <c:if test="${empty areaList}">hidden</c:if>" >
						<c:if test="${empty areaList}">
							<option value="${deliveryAddr.provinceId}" selected></option>
						</c:if>
						<c:if test="${not empty areaList}">
						<c:forEach var="area" items="${areaList}">
							<option value="${area.addressId}" data-level="${area.addressLevel}" <c:if test="${area.addressId == deliveryAddr.districtId}">selected</c:if>>${area.addressName}</option>
						</c:forEach>
						</c:if>
					</select>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>地址</label>
			<div class="col-md-4">
				<input class="form-control" id="address" name="address" validate="required: true" value="${deliveryAddr.address}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>联系人</label>
			<div class="col-md-4">
				<input class="form-control" id="contactName" name="contactName" validate="required: true" value="${deliveryAddr.contactName}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>联系电话</label>
			<div class="col-md-4">
				<input class="form-control" id="telNo" name="telNo" validate="required: true" value="${deliveryAddr.telNo}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">备注</label>
			<div class="col-md-4">
				<input class="form-control" id="note" name="note" value="${deliveryAddr.note}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">默认</label>
			<div class="col-md-4">
				<input type="checkbox" id="defaultYn" name="defaultYn" value="Y" <c:if test="${deliveryAddr.defaultYn == 'Y'}">checked</c:if>/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>状态</label>
			<div class="col-md-4">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<select id="state" name="state" class="form-control" >
					<c:forEach var="code" items="${stateCodes}">
						<option value="${code.codeId}" <c:if test="${code.codeId == deliveryAddr.state}">selected</c:if>>${code.codeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save" /></button>
				<a href="<%= BaseController.getCmdUrl("DeliveryAddr", "deliveryAddrList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
</div>