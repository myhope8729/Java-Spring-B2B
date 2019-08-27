<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty deliveryAddr.addrId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<div data-role="header" data-theme="b" >
    <h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增收货地址</c:when><c:otherwise>修改收货地址</c:otherwise></c:choose></h3>
    <a href="<%= BaseController.getCmdUrl("DeliveryAddr", "deliveryAddrList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">

	<form class="form-horizontal admin-form" id="deliveryAddrForm" name="deliveryAddrForm" role="form" action="<%= BaseController.getCmdUrl("DeliveryAddr", "saveDeliveryAddr") %>" method="POST">
		<input type="hidden" id="addrId" name="addrId" value="${deliveryAddr.addrId}" />
		
		<!-- Retrieve delivery address information -->
		<div class="form-group">
			<label class="control-label col-xs-4 text-right"><span class="required">*</span>所在地</label>
			<div class="col-xs-8 addr_select">
				<div class="col-xs-12">
					<div class="form-group pd-none" id="provId">
						<select name="provId" id="provId" class="locationList">
							<c:forEach var="province" items="${provList}">
								<option value="${province.addressId}" data-level="${province.addressLevel}" <c:if test="${province.addressId == deliveryAddr.provinceId}">selected='selected'</c:if> >${province.addressName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-xs-12">
					<div class="form-group pd-none <c:out value="${isProvince?'':'hidden'}"/>" id="cityId">
						<select name="cityId" id="cityId" class="locationList">
							<c:forEach var="city" items="${cityList}" varStatus="status">
								<option value="${city.addressId}" data-level="${city.addressLevel}" <c:if test="${city.addressId == deliveryAddr.cityId}">selected='selected'</c:if> >${city.addressName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-xs-12">
					<div class="form-group pd-none <c:if test="${empty areaList}">hidden</c:if>" id="areaId">
						<select name="locationId" id="locationId" class="" >
							<c:if test="${empty areaList }">
								<option value="${deliveryAddr.provinceId}" selected></option>
							</c:if>
							<c:if test="${not empty areaList }">
							<c:forEach var="area" items="${areaList}">
								<option value="${area.addressId}" data-level="${area.addressLevel}" <c:if test="${area.addressId == deliveryAddr.districtId}">selected='selected'</c:if>>${area.addressName}</option>
							</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right"><span class="required">*</span>地址</label>
			<div class="col-xs-8">
				<input class="form-control" placeholder="地址" data-clear-btn="true"  id="address" name="address" validate="required: true" value="${deliveryAddr.address}" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right"><span class="required">*</span>联系人</label>
			<div class="col-xs-8">
				<input class="form-control" placeholder="联系人" data-clear-btn="true"  id="contactName" name="contactName" validate="required: true" value="${deliveryAddr.contactName}" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right"><span class="required">*</span>联系电话</label>
			<div class="col-xs-8">
				<input class="form-control" id="telNo" name="telNo" validate="required: true" value="${deliveryAddr.telNo}" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right">备注</label>
			<div class="col-xs-8">
				<input class="form-control" id="note" name="note" value="${deliveryAddr.note}" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-xs-4">
			</div>
			<div class="col-xs-8">
				<label class="control-label">默认
					<input type="checkbox" id="defaultYn" name="defaultYn" value="Y" <c:if test="${deliveryAddr.defaultYn == 'Y'}">checked</c:if>/>
				</label>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right"><span class="required">*</span>状态</label>
			<div class="col-xs-8">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<select id="state" name="state" >
					<c:forEach var="code" items="${stateCodes}">
						<option value="${code.codeId}" <c:if test="${code.codeId == deliveryAddr.state}">selected</c:if>>${code.codeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="form-actions">
			<button type="submit" data-theme="b" data-icon="check" class="btn btn-primary"><qc:message code="system.common.btn.submit"/></button>
			<a class="mgr-no btn btn-default" href="<%= BaseController.getCmdUrl("DeliveryAddr", "deliveryAddrList") %>" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		</div>	
	</form>
</div>