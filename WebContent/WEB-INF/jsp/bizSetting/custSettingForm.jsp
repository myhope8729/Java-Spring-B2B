<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
</script>

<h3 class="page_title">订货方设置</h3>

<div id="content-body">
	<form class="form-horizontal admin-form" id="custSettingForm" name="custSettingForm" role="form" action="<%= BaseController.getCmdUrl("HostCust", "saveCustSetting") %>" method="POST">
		<input type="hidden" id="hostUserId" name="hostUserId" value="${custSetting.hostUserId}" />
		<input type="hidden" id="custUserId" name="custUserId" value="${custSetting.custUserId}" />
		
		<!-- Retrieve the customer information -->
		<div class="clear" style="margin-bottom:30px;">
			<table id="grid"></table>
		</div>
		
		<!-- Retrieve customer setting information -->
		<c:if test="${saleOnlineMark == 'Y'}">
			<div class="form-group">
				<label class="col-lg-4 control-label"><span class="required">*</span>付款方式</label>
				<div class="col-lg-4">
			 		<c:set var="cnt" value="0"/>
			 		<c:forEach var="payType" items="${payTypeList}">
			 			<c:set var="cnt" value="${cnt + 1}"/>
			 			<div class="col-lg-4">
							<input type="checkbox" class="mgl20" id="payTypeId" name="payTypeId" value="${payType.payTypeId}" <c:if test="${payType.privYn == 'N'}">disabled</c:if> <c:if test="${payType.payTypeChecked == 'Y'}">checked</c:if>/>
							<label class="control-label">${payType.payTypeName}</label>
						</div>
					</c:forEach>
					<c:if test="${cnt == 0}">
						<span class="required">请先设置付款方式！</span>
					</c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-4 control-label"><span class="required">*</span>价格类别</label>
				<div class="col-lg-4">
					<select id="priceColSeqNo" name="priceColSeqNo" class="form-control" validate="required: true">
						<option value=""><qc:message code="sc.please.select" /></option>
						<c:forEach var="priceList" items="${priceList}">
							<option value="${priceList.seqNo}" <c:if test="${custSetting.priceColSeqNo == priceList.seqNo}">selected</c:if> >${priceList.propertyDesc}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-4 control-label"><span class="required">*</span>拣货组</label>
				<div class="col-lg-4">
					<select id="distributeSeqNo" name="distributeSeqNo" class="form-control" validate="required: true">
						<option value=""><qc:message code="sc.please.select" /></option>
						<c:forEach var="pickList" items="${pickList}">
							<option value="${pickList.seqNo}" <c:if test="${custSetting.distributeSeqNo == pickList.seqNo}">selected</c:if> >${pickList.c1}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-4 control-label"><span class="required">*</span>拣货组内序号</label>
				<div class="col-lg-4">
					<input class="form-control" id="distributeNo" name="distributeNo" validate="required: true, digits:true" value="${custSetting.distributeNo}" />
				</div>
			</div>
		</c:if>
		
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>类别</label>
			<div class="col-lg-4">
				<select id="custTypeId" name="custTypeId" class="form-control" validate="required: true">
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:forEach var="custTypeList" items="${custTypeList}">
						<option value="${custTypeList.custTypeId}" <c:if test="${custTypeList.custTypeId == custSetting.custTypeId}">selected</c:if> >${custTypeList.custTypeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">邀请码</label>
			<div class="col-lg-4">
				<input class="form-control" id="clerkNo" name="clerkNo" value="${custSetting.clerkNo}" disabled/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>业务员</label>
			<div class="col-lg-4">
				<select id="empId" name="empId" class="form-control" validate="required: true">
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:forEach var="empList" items="${empList}">
						<option value="${empList.empId}" <c:if test="${empList.empId == custSetting.empId}">selected</c:if> >${empList.empName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>配送车辆</label>
			<div class="col-lg-4">
				<select id="carSeqNo" name="carSeqNo" class="form-control" validate="required: true">
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:forEach var="carList" items="${carList}">
						<option value="${carList.seqNo}" <c:if test="${carList.seqNo == custSetting.carSeqNo}">selected</c:if> >${carList.c1}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>客户简称</label>
			<div class="col-lg-4">
				<input class="form-control" id="custShortName" name="custShortName" validate="required: true" value="${custSetting.custShortName}" />
			</div>
		</div>
		
		<c:choose>
			<c:when test="${not empty copyMark}">
				<div class="form-group">
					<label class="col-lg-4 control-label">复制审批流程</label>
					<div class="col-lg-4">
						<select id="copyCustId" name="copyCustId" class="form-control">
							<option value=""><qc:message code="sc.please.select" /></option>
							<c:forEach var="custListForCopyMark" items="${custListForCopyMark}">
								<option value="${custListForCopyMark.custUserId}">${custListForCopyMark.custUserNo} 
									<c:if test="${not empty custListForCopyMark.custShortName}"> - ${custListForCopyMark.custShortName} </c:if>
									[<c:if test="${not empty custListForCopyMark.distributeName}">${custListForCopyMark.distributeName}</c:if>
									<c:if test="${not empty custListForCopyMark.distributeNo}"> - ${custListForCopyMark.distributeNo}</c:if>] 
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</c:when>
		</c:choose>
		
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>状态</label>
			<div class="col-lg-4">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<select id="state" name="state" class="form-control" validate="required: true">
					<c:forEach var="code" items="${stateCodes}">
						<option value="${code.codeId}" <c:if test="${code.codeId == custSetting.state}">selected</c:if>>${code.codeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-lg-offset-4 col-lg-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save" /></button>
				<a href="<%= BaseController.getCmdUrl("HostCust", "custSettingList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
</div>