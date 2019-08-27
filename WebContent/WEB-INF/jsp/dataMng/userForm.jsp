<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script>
	var messages = {confirm: "<qc:message code='uf.user_kind_change_confirm' />"}
</script>
<div class="admin_body">
	<h3 class="page_title">会员资料</h3>
	
		<form class="form-horizontal admin-form ajax" role="form" action="<%= BaseController.getCmdUrl("User", "saveUserAjax") %>&userId=${user.userId}" id="userForm" method="post">
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>登录名</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="userNo" value="${user.userNo}" class="form-control" <c:if test="${user.userYn != 'Y'}">disabled=disabled</c:if> validate="required: true" >
		 		<c:if test="${user.userYn != 'Y'}">
		 		<input type="hidden" name="userNo" value="${user.userNo}" class="form-control" validate="required: true" >
		 		</c:if>
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>用户名称</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="userName" value="${user.userName}" class="form-control" validate="required: true" >
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>业务类别</label>
		 	<div class="col-lg-4">
		 		<qc:codeList var="ukList" codeGroup="UK0000" exceptCode="UK0004"/>
		 		<qc:htmlSelect items="${ukList}" itemValue="codeId" itemLabel="codeName" selValue='${user.userKind }'
					isEmpty="true" emptyLabel="== 请选择 ==" cssClass="form-control" id="userKind" name="userKind" customAttr='validate="required: true"'/>
				<input type="hidden" id="userKindBk" value="${user.userKind}" />
			</div>
		</div>	
		 
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>所在地</label>
		 	<div class="col-lg-4">
		 		<div class="row" id="locationSelector">
		 			<input type="hidden" name="locationId" id="locationId" value="${user.locationId}">
		 			<div class="col-lg-4" id="provIdWrap">
						<select name="provId" id="provId" class="form-control" validate="required: true">
							<c:forEach var="province" items="${provList}">
							<option value="${province.addressId}" data-level="${province.addressLevel}" <c:if test="${userLocations.level2Id == province.addressId}"> selected</c:if>> ${province.addressName}</option>
							</c:forEach>
						</select>
					</div>
		 			<div class='col-lg-4<c:if test="${userLocations.level1Id==''}"> hidden</c:if>' id="cityIdWrap">
						<select name="cityId" id="cityId" class='form-control' validate="required: true">
							<c:forEach var="city" items="${cityList}">
							<option value="${city.addressId}" data-level="${city.addressLevel}" <c:if test="${userLocations.level1Id == city.addressId}"> selected</c:if>>${city.addressName}</option>
							</c:forEach>
						</select>
					</div>
					<div class='col-lg-4 col-xs-12<c:if test="${userLocations.level0Id==''}"> hidden</c:if>' id="areaIdWrap">
						<select name="areaId" id="areaId" class="form-control">
							<c:forEach var="area" items="${areaList}">
							<option value="${area.addressId}" <c:if test="${userLocations.level0Id == area.addressId}"> selected</c:if>>${area.addressName}</option>
							</c:forEach>
						</select>
					</div>
					
				</div>
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>地址</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="address" value="${user.address}" class="form-control" validate="required: true" >
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>联系人</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="contactName" value="${user.contactName}" class="form-control" validate="required: true" >
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>联系电话</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="telNo" value="${user.telNo}" class="form-control" validate="required: true" >
		 	</div>
		 </div>
		 
		 <div class="form-group">
		 	<label class="col-lg-4 control-label">手机号码</label>
		 	<div class="col-lg-4">
		 		<input type="text" name="mobileNo" value="${user.mobileNo}" class="form-control" />
		 	</div>
		 </div>
		 
		 <div class="form-group">
		    <div class="col-lg-offset-4 col-lg-4">
		      <button type="button" id="btnSave" class="btn btn-primary">提      交</button>
		    </div>
		  </div>
	</form>
</div>