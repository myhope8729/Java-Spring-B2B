<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript">
var minWidth = "${minWidth}";
var minHeight = "${minHeight}";
</script>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty item.itemId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增商品</c:when><c:otherwise>修改商品</c:otherwise></c:choose></h3>

<div class="admin_body">
	<form class="form-horizontal admin-form" id="itemForm" name="itemForm" role="form" action="<%= BaseController.getCmdUrl("Item", "saveItem") %>" method="POST" enctype="multipart/form-data">
		<input type="hidden" id="itemId" name="itemId" value="${item.itemId}" />
		
		<!-- Retrieve item information -->
		<div class="form-group <qc:errors items="${formErrors}" path="itemNo" type="errorCls" />">
			<label class="col-lg-4 control-label"><span class="required">*</span>商品编码</label>
			<div class="col-lg-4">
				<input class="form-control" id="itemNo" name="itemNo" validate="required: true" autofocus value="${item.itemNo}" />
				<qc:errors items="${formErrors}" path="itemNo" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>商品名称</label>
			<div class="col-lg-4">
				<input class="form-control" id="itemName" name="itemName" validate="required: true" value="${item.itemName}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">大类</label>
			<div class="col-lg-4">
				<input class="form-control" id="cat1" name="cat1" value="${item.cat1}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">小类</label>
			<div class="col-lg-4">
				<input class="form-control" id="cat2" name="cat2" value="${item.cat2}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">商品条形码</label>
			<div class="col-lg-4">
				<input class="form-control" id="barCode" name="barCode" value="${item.barCode}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">规格</label>
			<div class="col-lg-4">
				<input class="form-control" id="standard" name="standard" value="${item.standard}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">单位</label>
			<div class="col-lg-4">
				<input class="form-control" id="unit" name="unit" value="${item.unit}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">产地</label>
			<div class="col-lg-4">
				<input class="form-control" id="manufacturer" name="manufacturer" value="${item.manufacturer}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label"><span class="required">*</span>状态</label>
			<div class="col-lg-4">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${item.state}' cssClass="form-control" name="state"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-lg-4 control-label">商品图片</label>
			<div class="col-lg-4">
				<div class="fileinput crop
					<c:if test="${not empty item.itemImg}">fileinput-exists</c:if>
					<c:if test="${empty item.itemImg}">fileinput-new</c:if>" data-provides="fileinput">
					<div class="fileinput-preview thumbnail">
						<c:if test="${not empty item.itemImg}">
						<img src='/uploaded/useritem/${item.itemImg}' id="crop_target"/>
						</c:if>
					</div>
					<img src='/uploaded/useritem/medium/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
					<div>
						<span class="default btn-file">
							<span class="fileinput-new btn btn-primary">选择图片</span>
							<span class="fileinput-exists btn btn-info">变更</span>
							<input type="file" name="imgFile" id="imgFile">
							</span>
							<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" name="itemImg" id="itemImg" value="${item.itemImg}" />
		<input type="hidden" name="cropX" id="cropX" value="" />
		<input type="hidden" name="cropY" id="cropY" value="" />
		<input type="hidden" name="cropW" id="cropW" value="" />
		<input type="hidden" name="cropH" id="cropH" value="" />
		<div class="form-group">
			<div class="col-lg-offset-4 col-lg-4">
				<button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.save" /></button>
				<a href="<%= BaseController.getCmdUrl("Item", "itemList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.return" /></a>
			</div>
		</div>
	</form>
	
</div>