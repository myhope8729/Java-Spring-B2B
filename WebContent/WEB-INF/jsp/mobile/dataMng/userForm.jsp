<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script>
	var messages = {confirm: "<qc:message code='uf.user_kind_change_confirm' />"}
</script>
<div data-role="header" data-theme="b" >
   <h3 class="page_title">会员资料</h3>
</div>

<div role="main" class="ui-content mgt10">	
	
	<form class="form-horizontal admin-form ajax" action="<%= BaseController.getCmdUrl("User", "saveUserAjax") %>&userId=${user.userId}" id="userForm" method="post">
		<div class="form-group">
		 	<label for="userNo" class="control-label col-xs-4 text-right"><span class="required">*</span>登录名</label>
		 	<div class="col-xs-8">
		 		<input type="text" name="userNo" placeholder="登录名" data-clear-btn="true" value="${user.userNo}" class="form-control" <c:if test="${user.userYn != 'Y'}">disabled=disabled</c:if> validate="required: true" >
		 		<c:if test="${user.userYn != 'Y'}">
		 		<input type="hidden" name="userNo" value="${user.userNo}" class="form-control" validate="required: true" >
		 		</c:if>
		 	</div>
		</div>
		 
		<div class="form-group">
		 	<label  for="userName" class="control-label col-xs-4 text-right"><span class="required">*</span>用户名称</label>
		 	<div class="col-xs-8">
		 		<input type="text" name="userName"  placeholder="用户名称" data-clear-btn="true" value="${user.userName}" class="form-control" validate="required: true" >
		 	</div>
		</div>
		 
		<div class="form-group">
		 	<label for="userKind" class="control-label col-xs-4 text-right"><span class="required">*</span>业务类别</label>
		 	<div class="col-xs-8">
		 		<qc:codeList var="ukList" codeGroup="UK0000" />
		 		<qc:htmlSelect items="${ukList}" itemValue="codeId" itemLabel="codeName" selValue='${user.userKind }'
					isEmpty="true" emptyLabel="== 请选择 ==" cssClass="" id="userKind" name="userKind" customAttr='validate="required: true"'/>
				<input type="hidden" id="userKindBk" value="${user.userKind}" />
			</div>
		</div>	
		 
		<div class="form-group mgb-none">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>所在地</label>
		 	<div id="locationSelector" class="col-xs-8">
		 		<input type="hidden" name="locationId" id="locationId" value="${user.locationId}">
		 		<div class="col-xs-12">
		 			<div class="form-group pd-none" id="provIdWrap">
						<select name="provId" id="provId" validate="required: true">
							<c:forEach var="province" items="${provList}">
							<option value="${province.addressId}" data-level="${province.addressLevel}" <c:if test="${userLocations.level2Id == province.addressId}"> selected</c:if>> ${province.addressName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-xs-12">
		 			<div class='form-group pd-none<c:if test="${userLocations.level1Id==''}"> hidden</c:if>' id="cityIdWrap">
						<select name="cityId" id="cityId" validate="required: true">
							<c:forEach var="city" items="${cityList}">
							<option value="${city.addressId}" data-level="${city.addressLevel}" <c:if test="${userLocations.level1Id == city.addressId}"> selected</c:if>>${city.addressName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-xs-12">
					<div class='form-group pd-none<c:if test="${userLocations.level0Id==''}"> hidden</c:if>' id="areaIdWrap">
						<select name="areaId" id="areaId">
							<c:forEach var="area" items="${areaList}">
							<option value="${area.addressId}" <c:if test="${userLocations.level0Id == area.addressId}"> selected</c:if>>${area.addressName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
		 
		<div class="form-group">
		 	<label for="address" class="control-label col-xs-4 text-right"><span class="required">*</span>地址</label>
		 	<div class="col-xs-8">
	 			<input type="text" placeholder="地址" data-clear-btn="true" name="address" value="${user.address}" class="form-control" validate="required: true" >
	 		</div>
		</div>
		 
		<div class="form-group">
		 	<label for="contactName" class="control-label col-xs-4 text-right"><span class="required">*</span>联系人</label>
		 	<div class="col-xs-8">
	 			<input type="text" placeholder="联系人" data-clear-btn="true" name="contactName" value="${user.contactName}" class="form-control" validate="required: true" >
	 		</div>
		</div>
		 
		<div class="form-group">
		 	<label for="telNo" class="control-label col-xs-4 text-right"><span class="required">*</span>联系电话</label>
	 		<div class="col-xs-8">
	 			<input type="text" placeholder="联系电话" data-clear-btn="true" name="telNo" value="${user.telNo}" class="form-control" validate="required: true" >
	 		</div>
		</div>
		 
		<div class="form-group">
		 	<label for="mobileNo" class="control-label col-xs-4 text-right">手机号码</label>
		 	<div class="col-xs-8">
	 			<input type="text" placeholder="手机号码" data-clear-btn="true" name="mobileNo" value="${user.mobileNo}" class="form-control" />
	 		</div>
		</div>
		 
		<div class="form-actions">
		    	<button id="btnSave" type="button"  data-theme="b" class="btn btn-primary"><qc:message code="system.common.btn.submit"/></button>
		</div>
	</form>
</div>