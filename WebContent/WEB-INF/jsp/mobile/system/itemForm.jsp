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

<div data-role="header" data-theme="b" >
    <h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增商品</c:when><c:otherwise>修改商品</c:otherwise></c:choose></h3>
    <a href="<%= BaseController.getCmdUrl("Item", "itemList") %>" data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content">	

	<form class="admin-form form-horizontal" id="itemForm" name="itemForm" action="<%= BaseController.getCmdUrl("Item", "saveItem") %>" method="POST" enctype="multipart/form-data">
		<input type="hidden" id="itemId" name="itemId" value="${item.itemId}" />
		
		<!-- Retrieve item information -->
		<div class="form-group <qc:errors items="${formErrors}" path="itemNo" type="errorCls" />">
			<label class="control-label col-xs-4 text-right"><span class="required">*</span>商品编码</label>
			<div class="col-xs-8">
				<input class="form-control" placeholder="商品编码" data-clear-btn="true" id="itemNo" name="itemNo" validate="required: true" autofocus value="${item.itemNo}" />
				<qc:errors items="${formErrors}" path="itemNo" />
			</div>
		</div>
		
		<div class="form-group <qc:errors items="${formErrors}" path="itemName" type="errorCls" />">
			<label for="itemName" class="col-xs-4 control-label text-right"><span class="required">*</span>商品名称:</label>
			<div class="col-xs-8">
				<input class="form-control" placeholder="商品名称" data-clear-btn="true" id="itemName" name="itemName" validate="required: true" value="${item.itemName}" />
				<qc:errors items="${formErrors}" path="itemName" />
			</div>
		</div>
		<div class="form-group">	
			<label for="cat1" class="col-xs-4 control-label text-right">大类:</label>
			<div class="col-xs-8">
				<input class="form-control" placeholder="大类" data-clear-btn="true" id="cat1" name="cat1" value="${item.cat1}" />
			</div>
		</div>
		<div class="form-group">		
			<label for="cat2" class="col-xs-4 control-label text-right">小类:</label>
			<div class="col-xs-8">
				<input class="form-control" placeholder="小类" data-clear-btn="true" id="cat2" name="cat2" value="${item.cat2}" />
			</div>
		</div>
		<div class="form-group">		
			<label for="barCode" class="col-xs-4 control-label text-right">商品条形码:</label>
			<div class="col-xs-8">
				<input type="text" class="form-control"  placeholder="商品条形码" data-clear-btn="true"  id="barCode" name="barCode" value="${item.barCode}" />
			</div>
		</div>
		<div class="form-group">
			<label  class="col-xs-4 control-label text-right">规格:</label>
			<div class="col-xs-8">
				<input class="form-control" id="standard" placeholder="规格" data-clear-btn="true" name="standard" value="${item.standard}" />
			</div>
		</div>
		<div class="form-group">
			<label  class="col-xs-4 control-label text-right">单位:</label>
			<div class="col-xs-8">
				<input type="text" class="form-control" placeholder="单位" data-clear-btn="true" id="unit" name="unit" value="${item.unit}" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label text-right">产地:</label>
			<div class="col-xs-8">
				<input class="form-control" id="manufacturer" placeholder="产地" data-clear-btn="true" name="manufacturer" value="${item.manufacturer}" />
			</div>
		</div>
			
		<div class="form-group">		
			<label for="codeId" class="col-xs-4 control-label text-right"><span class="required">*</span>状态:</label>
			<div class="col-xs-8">
				<qc:codeList var="stateCodes" codeGroup="ST0000" />
				<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${item.state}' customAttr="data-theme='a'" name="state"/>
			</div>
		</div>
		<div class="form-group">
			<label for="crop_target" class="control-label text-right col-xs-4">商品图片</label>
			<div class="col-xs-8 fileinput crop
				<c:if test="${not empty item.itemImg}">fileinput-exists</c:if>
				<c:if test="${empty item.itemImg}">fileinput-new</c:if>"  data-provides="fileinput">
				<div class="fileinput-preview thumbnail">
					<c:if test="${not empty item.itemImg}">
					<img src='/uploaded/useritem/${item.itemImg}' class="fileinput_imagesize" id="crop_target"/>
					</c:if>
				</div>
				<img src='/uploaded/useritem/medium/noImage_300x200.gif' class="noImage fileinput-new"  data-trigger="fileinput"/>
				<div class="alignL">
					<span class="default btn-file">
						<span class="fileinput-new btn btn-primary" data-trigger="fileinput">选择图片</span>
						<span class="fileinput-exists btn btn-info">变更</span>
						<input type="file" name="imgFile" id="imgFile" style="display:none;">
					</span>
					<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<button type="submit" data-theme="b" class="btn btn-primary"><qc:message code="system.common.btn.save"/></button>
		</div>
		<div class="form-actions">
			<a class="btn btn-default" href="<%= BaseController.getCmdUrl("Item", "itemList") %>" data-role="button" id="btnBack"><qc:message code="system.common.btn.back" /></a>
		</div>
		<input type="hidden" name="itemImg" id="itemImg" value="${item.itemImg}" />
		<input type="hidden" name="cropX" id="cropX" value="" />
		<input type="hidden" name="cropY" id="cropY" value="" />
		<input type="hidden" name="cropW" id="cropW" value="" />
		<input type="hidden" name="cropH" id="cropH" value="" />
	</form>

</div>	