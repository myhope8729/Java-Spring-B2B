<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div data-role="header"  data-theme="b" >
    <h3 class="page_title">设置幻灯片</h3>
    <a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageMain") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>
	
<div role="main" class="ui-content">
	
	<form id="bannerFrm" class="admin-form" action="<%= BaseController.getCmdUrl("ConfigPage", "savePageBanner") %>" method="post" enctype="multipart/form-data">				
		<div class="form-group col-md-12 mgt10">
			<label class="control-label">幻灯图片</label>
			<div class="alignC">
				<div class="fileinput 
						<c:if test="${not empty banner.bannerImgPath}">fileinput-exists</c:if>
						<c:if test="${empty banner.bannerImgPath}">fileinput-new</c:if>" data-provides="fileinput">
					<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 100%; height: 110px;min-width:280px">						
						<c:choose>
							<c:when test="${banner.showMark == 'Y'}">
								<img style="width:280px;height:110px;" src="<c:url value="/uploaded/pagebanner/${banner.bannerImgPath}"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:when>
							<c:otherwise>
								<img style="width:280px;height:110px;" src="<c:url value="/images/del.jpg"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:otherwise>
						</c:choose>
					</div>
					<div>
						<span class="default btn-file pull-left">
					    	<span class="fileinput-new btn btn-primary">选择图片</span>
					    	<span class="fileinput-exists btn btn-info">变更</span>
					    	<input type="file" name="bannerImgFile" id="bannerImgFile">
					    </span>
					    <a href="#" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group col-md-12">
			<label class="control-label"><span class="required">*</span>链接到</label>
		    <qc:codeList var="links" codeGroup="UR0000" />
			<select id="urlType" name="urlType">
				<c:forEach var="link" items="${links}">
					<option value="${link.codeId}" <c:if test="${link.codeId eq banner.urlType}">selected</c:if>>${link.codeName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group col-md-12 url-link">
			<label class="control-label">链接内容</label>
			<input type="text" name="url" value="${banner.url}" class="form-control" validate="required: false">
		</div>
		<div class="form-group col-md-12 product-group">
			<label class="control-label">链接内容</label>
			<select name="productGroup">
				<option value="">==请选择商品组==</option>
				<c:if test="${not empty productGroupList }">
				<c:forEach var="group" items="${productGroupList}">
					<option value="${group.seqNo }" <c:if test="${banner.url eq group.seqNo }">selected</c:if>>${group.c1}</option>
				</c:forEach>
				</c:if>
			</select>
		</div>
		<div class="form-group col-md-12">
			<label class="control-label"><span class="required">*</span>显示图片
				<input type="checkbox" id="showMark" name="showMark" value="Y" <c:if test="${'Y' eq banner.showMark}">checked</c:if> />		
			</label>		
		</div>
		
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="submit"  data-mini="true"  data-theme="b" data-icon="check" ><qc:message code="system.common.btn.save"/></button>
				</div>		
				<div class="ui-block-b">
					<a class="mgr-no" href="<%= BaseController.getCmdUrl("ConfigPage", "configPageMain") %>" data-mini="true" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>
			</div>
		</div>			
		<input type="hidden" name="bannerId" value="${banner.bannerId}" />
		<input type="hidden" name="bannerImgPath" value="${banner.bannerImgPath}" /> 
	</form>
</div>
