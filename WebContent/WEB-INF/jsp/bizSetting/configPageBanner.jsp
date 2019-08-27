<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin_body">
	<h3 class="page_title">设置幻灯片</h3>
	
	<form id="bannerFrm" class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("ConfigPage", "savePageBanner") %>" method="post" enctype="multipart/form-data">				
		<div class="form-group row">
			<label class="col-md-4 control-label">幻灯图片</label>
			<div class="col-md-4">
				<div class="fileinput 
						<c:if test="${not empty banner.bannerImgPath}">fileinput-exists</c:if>
						<c:if test="${empty banner.bannerImgPath}">fileinput-new</c:if>" data-provides="fileinput">
					<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 100%; height: 200px;">						
						<c:choose>
							<c:when test="${banner.showMark == 'Y'}">
								<img width="90%" height="180" src="<c:url value="/uploaded/pagebanner/${banner.bannerImgPath}"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:when>
							<c:otherwise>
								<img width="90%" height="180" src="<c:url value="/images/del.jpg"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:otherwise>
						</c:choose>
					</div>
					<div>
						<span class="default btn-file">
					    	<span class="fileinput-new btn btn-primary">选择图片</span>
					    	<span class="fileinput-exists btn btn-info">变更</span>
					    	<input type="file" name="bannerImgFile" id="bannerImgFile">
					    </span>
					    <a href="#" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-md-4 control-label"><span class="required">*</span>链接到</label>
			<div class="col-md-4">
			    <qc:codeList var="links" codeGroup="UR0000" />
				<select id="urlType" name="urlType" class="form-control">
					<c:forEach var="link" items="${links}">
						<option value="${link.codeId}" <c:if test="${link.codeId eq banner.urlType}">selected</c:if>>${link.codeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group row url-link">
			<label class="col-md-4 control-label">链接内容</label>
			<div class="col-md-4">
				<input type="text" name="url" value="${banner.url}" class="form-control" validate="required: false">
			</div>
		</div>
		<div class="form-group row product-group">
			<label class="col-md-4 control-label">链接内容</label>
			<div class="col-md-4">
				<select name="productGroup" class="form-control">
					<option value="">==请选择商品组==</option>
					<c:if test="${not empty productGroupList }">
					<c:forEach var="group" items="${productGroupList}">
						<option value="${group.seqNo }" <c:if test="${banner.url eq group.seqNo }">selected</c:if>>${group.c1}</option>
					</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-md-4 control-label"><span class="required">*</span>显示图片</label>
			<div class="col-md-4">
				<input type="checkbox" id="showMark" name="showMark" value="Y" <c:if test="${'Y' eq banner.showMark}">checked</c:if> />				
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-6 text-right">
				<input type="submit" class="btn btn-primary" value="<qc:message code="system.common.btn.save" />">
			</div>
			<div class="col-md-6">
				<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageMain") %>" class="btn btn-primary">
					<qc:message code="system.common.btn.back" />
				</a>
			</div>
		</div>
		
		<input type="hidden" name="bannerId" value="${banner.bannerId}" />
		<input type="hidden" name="bannerImgPath" value="${banner.bannerImgPath}" /> 
	</form>
</div>
