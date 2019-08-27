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
		wrongJsQty : "<qc:message code='system.invalid.js.qty' />",
		select_address : "<qc:message code='order.select_address' />",
		select_one_at_least : "<qc:message code='order.select_one_at_least' />"
	};
	var itemGridData = ${gridData}; 
	var cartGridData = ${cartGridData}; 
	var addrList = ${addrList};
	var curAddrId = "${order.addrId}";
	var hcModel = ${hc};
	var isJsMark = ${isJsMark};
	var itemJsCol = '<c:if test="${not empty itemPackageProperty}">${itemPackageProperty.propertyName }</c:if>';
	var afterSavingUrl = "<%= BaseController.getCmdUrl("Order", "orderList") %>";
</script>
<div id="order-form-wrap" class="admin_body">

	<h3 class="page_title">${pageTitle}</h3>
	<div class="action_bar text-right">
		<button class="btn btn-primary" id="btnPrev"><qc:message code="system.common.btn.previous"/></button>
		<button class="btn btn-primary" id="btnSave"><qc:message code="system.common.btn.draft"/></button>
		<button class="btn btn-primary" id="btnNext"><qc:message code="system.common.btn.next"/> </button>
		<button class="btn btn-primary" id="btnSubmit"><qc:message code="system.common.btn.submit"/> </button>
		
		<a class="btn btn-default pull-right mgl20" href="<%= BaseController.getCmdUrl("Order", "vendorsList") %>"><qc:message code="system.common.btn.back"/></a>
	</div>
	
	<form class="form-horizontal admin-form" role="form" 
		action="<%= BaseController.getCmdUrl("Order", "saveOrderAjax") %>&hostUserId=${hostUser.userId}"
		id="userItemsForm" class="searchForm" method="post">
		
		<input type="hidden" name="catFieldName" id="catFieldName" <c:if test="${not empty itemType1Property}"> value="${itemType1Property.propertyName }" </c:if> />
		<input type="hidden" name="catFieldName2" id="catFieldName2" <c:if test="${not empty itemType2Property}">value="${itemType2Property.propertyName }"</c:if> />
		<input type="hidden" name="category" id="category" value="${sc.category }" />
		<input type="hidden" name="category2" id="category2" value="${sc.category2 }" />
		
		<input type="hidden" name="billId" id="billId" value="${order.billId}" />
		<input type="hidden" name="hbmark" id="hbmark" value="${order.hbmark}" />
		
		<input type="hidden" name="paymentTypeName" id="paymentTypeName" value="${order.paymentType}" />
		
		<input type="hidden" name="action" id="action" value="submit" />
		<input type="hidden" name="copymark" id="copymark" value="<%= request.getParameter("copymark") %>" />
		
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
		
		<div class="info-box border hd"  id="cust-info-wrap">
			<div class="box-title">
				<label class="title">订货方</label> <label class="text">${hcModel.custUserName}</label>
			</div>
			<div class="box-body">
				<div class="row-fluid">
					<label class="title">预付款余额(元)：</label> 
					<label class="text" id="prepay-wrap"></label>
					<a href="<%= BaseController.getCmdUrl("Payment", "paymentBillList") %>&hostUserId=${hcModel.hostUserId}&custUserId=${hcModel.custUserId}" target="_blank">详细</a>
				</div>
			</div>
		</div>
		
		<div class="row">
			<c:if test="${isItemTypeDisp}">
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label class="control-label col-md-3 col-xs-4 text-left">商品类别</label>
					<div class="col-md-8 col-xs-8">
						<qc:htmlSelect items="${itemTypeList}" itemValue="seqNo" itemLabel="c1" selValue='${order.itype }'
							isEmpty="true" emptyLabel="=请选择商品类别=" cssClass="form-control" id="itype" name="itype"/>
					</div>
				</div>
			</div>
			</c:if>
			<div class="col-sm-4 col-xs-12">
				<div class="form-group">
					<label class="control-label col-md-3 col-xs-4 text-left">付款方式</label>
					<div class="col-md-8 col-xs-8">
						<qc:htmlSelect items="${payTypeList}" itemValue="payTypeId" itemLabel="payTypeName" selValue='${order.paytypeId }'
						isEmpty="true" emptyLabel="=请选择付款方式=" cssClass="form-control" id="paytypeId" name="paytypeId" modelAttr="prePayYn,weixinYn" />
					</div>
				</div>
			</div>
			<div class="col-sm-4 col-xs-12 hd" id="paymentTypeWrap">
				<div class="form-group">
					<label class="control-label col-md-3 col-xs-4 text-left">付款类别</label>
					<div class="col-md-8 col-xs-8">
						<qc:htmlSelect items="${subPayTypeList}" itemValue="name" itemLabel="name" selValue='${order.paymentType}'
						isEmpty="true" emptyLabel="=请选择预付款类别=" cssClass="form-control" id="paymentType" name="paymentType" />
					</div>
				</div>
			</div>
		</div>

		<div class="info-box">
			<div class="box-title">
				<div class="row">
					<label class="title col-xs-8">订货商品</label>
					<label class="title col-xs-4">合计金额(元): <span id="cartTotal"></span><%-- , ${order.totalAmt } --%></label>
				</div>
			</div>
			<div class="box-body no-padding">
				<div class="row">
					<div class="col-lg-12">
						<table id="cartGrid"></table>
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
			
			<div class="info-box border" id="cat1-wrapper">
				<div class="box-title"><label class="title">商品分类 - 大类</label></div>
				<div class="box-body no-padding">
					<div class="row">
						<div class="col-xs-12">
							<div class="cat-menu" id="cat1">
								
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="info-box border hd" id="cat2-wrapper">
				<div class="box-title"><label class="title">商品分类 - 小类</label></div>
				<div class="box-body no-padding">
					<div class="row">
						<div class="col-xs-12">
							<div class="cat-menu" id="cat2">
								
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="info-box">
				<div class="box-body no-padding">
					<table id=itemGrid ajaxUrl="<%= BaseController.getCmdUrl("Order", "userItemsGridAjax") %>&hostUserId=${hostUser.userId}" searchForm="#userItemsForm"></table>
					<div id="itemGridPager"></div>
				</div>
			</div>
		</div>
		
		
		<div id="addrList">
			<div class="info-box border">
				<div class="box-title"><label class="title">收货地址</label></div>
				<div class="box-body no-padding">
					<div class="row">
						<div class="col-lg-12">
							<table id=addrGrid  searchForm="#userItemsForm"></table>
							<div id="addrGridPager"></div>
						</div>
					</div>
					
				</div>
			</div>
			<div class="info-box">
				<div class="form-group">
					<div class="col-md-4 col-sm-5 col-xs-6">
						<div class="row">
							<label class="col-sm-4 col-xs-6 control-label text-left"><c:if test="${isArrivalDateRequired}"><span class="required">*</span></c:if>送货日期</label>
							<div class="col-sm-8 col-xs-6">
								<input type="text" class="form-control date-picker" name="arriveDate" id="arriveDate" data-date-format="yyyy-mm-dd" value="${order.arriveDate }" />
							</div>
						</div>
					</div>
					<div class="col-md-8 col-sm-7 col-xs-6">
						<div class="row">
							<label class="col-md-2 col-xs-4 control-label text-left">备注 </label>
							<div class="col-xs-8">
								<input type="text"  class="form-control" name="note" id="note" value="${order.note }" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>