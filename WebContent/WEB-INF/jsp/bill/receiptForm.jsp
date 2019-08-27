<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var messages = {
		number_only : "<qc:message code='system.invalid.number' />",
		wrongQty : "<qc:message code='system.common.validate.wrong.qty' />",
		select_one_at_least : "<qc:message code='order.select_one_at_least' />"
	};
	var itemGridData = ${gridData}; 
	var cartGridData = ${cartGridData}; 
	var billItems = ${billItemList};
</script>
<style>
	.cat-menu .cat-menu-item{
		float: left;
		height: 40px;
		text-align: center;
		padding-top: 10px;
		border-right: 1px solid #aaa;
		border-bottom: 1px solid #aaa;
		cursor: pointer;
	}
	.cat-menu .col-lg-3:nth-child(4n+0) { border-right: 0px; }
	.cat-menu .col-lg-2:nth-child(6n+0) { border-right: 0px; }
	.cat-menu .cat-menu-item:hover, .cat-menu .cat-menu-item:ACTIVE { 
		background: rgb(254, 115, 14);
		color: #fff;
	}
	.cat-menu .cat-menu-item.active {
		background: rgb(73, 140, 208);
		color: #fff;
	}
	.cat-menu .cat-menu-item.last-row{border-bottom:0px;}
	
</style>
<div id="" class="admin_body">

	<h3 class="page_title">${pageTitle}</h3>
	<div class="action_bar text-right">
		<button class="btn btn-primary" id="btnDraft"><qc:message code="system.common.btn.draft"/></button>
		<button class="btn btn-primary" id="btnSave"><qc:message code="system.common.btn.submit"/></button>
		<a class="btn btn-default" href="Receipt.do?cmd=vendorsList"><qc:message code="system.common.btn.back"/></a>
	</div>
	
	<form class="form-horizontal admin-form" role="form" 
		action="<%= BaseController.getCmdUrl("Receipt", "saveReceipt") %>" id="userItemsForm" class="searchForm" method="post">
		
		<input type="hidden" name="hostUserId" id="hostUserId" value="${hostUser.userId }" />
		<input type="hidden" name="catFieldName" id="catFieldName" <c:if test="${not empty itemType1Property}"> value="${itemType1Property.propertyName }" </c:if> />
		<input type="hidden" name="catFieldName2" id="catFieldName2" <c:if test="${not empty itemType2Property}">value="${itemType2Property.propertyName }"</c:if> />
		<input type="hidden" name="billId" id="billId" value="${receipt.billId}" />
		<input type="hidden" name="draftFlg" id="draftFlg" value="N" />
		
		<div class="info-box border">
			<div class="box-title">
				<label class="title">供货方</label> <label class="text">${hostUser.userName}</label>
			</div>
			<div class="box-body">
				<div class="row-fluid">
					<label class="title">联系人</label> <label class="text">${hostUser.contactName}</label>
					<label class="title">联系电话</label> <label class="text">${hostUser.telNo}</label>
					<label class="title">地址</label> <label class="text">${hostUser.address}</label>
				</div>
				<c:if test="${not empty hostUser.note }">
					<div class="row-fluid">
						<label class="text-info">${hostUser.note}</label>
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
				<c:if test="${not empty payTypeList}">
					<label class="control-label col-md-3 col-xs-4 text-left">付款方式</label>
					<div class="col-md-7 col-xs-8">
						<qc:htmlSelect items="${payTypeList}" itemValue="payTypeId" itemLabel="payTypeName" selValue='${receipt.paytypeId }'
						isEmpty="true" emptyLabel="==请选择付款方式==" cssClass="form-control wd200" id="paytype" name="paytype" customAttr="validate='required: true' message='付款方式'"/>
					</div>
				</c:if>
				</div>
			</div>
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-offset-0 col-md-offset-1 col-xs-4 text-left">入库仓库</label>
					<div class="col-md-7 col-xs-8">
						<qc:htmlSelect items="${storeList}" itemValue="storeId" itemLabel="storeName" selValue='${receipt.storeId }'
						isEmpty="true" emptyLabel="==请选择入库仓库==" cssClass="form-control wd200" id="store" name="store"  customAttr="validate='required: true' message='入库仓库'"/>
					</div>
				</div>
			</div>
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label class="control-label col-md-3 col-md-offset-3 col-sm-offset-0 col-xs-4 text-left">入库日期</label>
					<div class="col-md-6 col-xs-8">
						<input type="text" class="form-control date-picker" validate="required:true" name="arriveDate" message="入库日期" data-date-format="yyyy-mm-dd" value="${receipt.arriveDate}" >
					</div>
				</div>
			</div>
		</div>
		
		<div class="info-box">
			<div class="box-title">
				<div class="row">
					<label class="title col-xs-8">入库商品</label>
					<label class="title col-xs-4">合计金额(元): <span id="total-amt">${receipt.itemAmt}</span></label>
					<input type="hidden" name="total_price" id="total_price" value="${receipt.itemAmt}"/>
				</div>
			</div>
			<div class="box-body no-padding">
				<div class="row">
					<div class="col-lg-12">
						<table id="cartGrid" ajaxUrl="<%= BaseController.getCmdUrl("Receipt", "billItemsGridAjax") %>"></table>
					</div>
				</div>
			</div>
		</div>
		<div id="itemList">
			<div class="row">
				<div class="col-md-4 col-sm-6 col-xs-12">
					<div class="form-group">
						<label class="col-xs-3 control-label text-left">查找商品</label>
						<div class="col-xs-6">
							<input type="text"  class="form-control" name="searchKey" id="searchKey" value="${sc.chelp }" />
						</div>
						<div class="col-xs-3">
							<button type="button" class="btn btn-primary" id="btnSearch"><qc:message code="system.common.btn.search"/></button>
						</div>
					</div>
				</div>
				<div class="col-md-8 col-sm-6 col-xs-12">
					<div class="form-group">
						<label class="col-xs-12 control-label text-info"><div class="text-center">可按商品名称、规格、价格查询，支持模糊查询，支持拼音首字母查询</div></label>
					</div>
				</div>
			</div>
			
			<c:if test="${not empty itemType1List }">
			<div class="info-box border" id="cat-wrapper">
				<div class="box-title"><label class="title">商品分类 - 大类</label></div>
				<div class="box-body no-padding">
					<div class="row">
						<div class="col-lg-12">
							<div class="cat-menu" id="cat1">
								<c:forEach var="cat" items="${itemType1List}">
								<div class="cat-menu-item col-lg-3 
									<c:if test='${((not empty cat.catName) && (cat.catName == sc.category)) || ((empty cat.catName) && (sc.category == "-1"))}'>active</c:if>" cid="${cat.catName}">
									<c:if test="${not empty cat.catName}">${cat.catName}</c:if>
									<c:if test="${empty cat.catName}">未分类</c:if> 
									(${cat.cnt})
								</div>
								</c:forEach>
								<input type="hidden" name="category" id="category" value="${sc.category }" />
							</div>
						</div>
					</div>
				</div>
			</div>
			</c:if>
			<div class="info-box">
				<div class="box-body no-padding">
					<div class="row">
						<div class="col-lg-12">
							<table id="itemGrid" ajaxUrl="<%= BaseController.getCmdUrl("Receipt", "userItemsGridAjax") %>" searchForm="#userItemsForm"></table>
							<div id="itemGridPager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
