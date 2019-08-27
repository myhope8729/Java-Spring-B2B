<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var gridData = ${gridData}; 
</script>

<style>
	.mgb15{
		margin-bottom: 15p;
	}
	.info-box{
		padding: 0px;
		font-size:13px;
		margin-bottom: 15px;
	}
	.info-box.border{
		border: 1px solid #aaa;
	}
	.info-box label{
		font-weight: normal;
		margin-bottom: 0px;
	}
	.info-box .control-label {
		text-align: left;
	}
	.info-box label.title{
		font-weight: bold;
	}
	
	.info-box label.text{
		margin-right: 20px; 
		margin-left: 10px;
	}
	.info-box div.box-title {
		background-color: #aaa;
		padding: 10px;
		font-size: 13px;
		margin-bottom:0;
		border-bottom: 1px solid #999;
	}
	.info-box div.box-body{
		padding: 10px;
	}
	
	.info-box .text-info{
		color: #FE730E;
		line-height: 160%;
	}
	.no-padding{padding:0 !important;} 
	
	.info-box .ui-jqgrid{margin-bottom:0;}	
	.cat-menu .cat-menu-item{
		float: left;
		height: 40px;
		text-align: center;
		padding-top: 10px;
		border-left: 1px solid #aaa;
		border-bottom: 1px solid #aaa;
		cursor: pointer;
	}
	.cat-menu .cat-menu-item:FIRST-CHILD { border-left: 0px; }
	.cat-menu .cat-menu-item:hover, .cat-menu .cat-menu-item:ACTIVE { 
		background: rgb(254, 115, 14);
		color: #fff;
	}
	.cat-menu .cat-menu-item.active {
		background: rgb(73, 140, 208);
		color: #fff;
	}
	.cat-menu .cat-menu-item.last{border-right:1px solid #aaa;}
	.ui-jqgrid td .chPrice{width:60%;text-align:center;margin-right:10px;font-size:12px;}
	.ui-jqgrid td .oldPrice{background-color:transparent;border-color:transparent;font-size:12px;}
</style>

<div id="" class="admin_body">

	<h3 class="page_title">${pageTitle}</h3>
	
	<form class="form-horizontal admin-form" role="form" 
		action="<%= BaseController.getCmdUrl("Price", "savePrice") %>" id="priceItemForm" class="searchForm" method="post">
		
		<div class="row">
			<c:if test="${not empty categoryList }">
			<div class="text-left col-md-3 col-sm-6">
				<div class="form-group">
					<label class="control-label col-md-4 col-sm-4 text-left">商品分类</label>
					<div class="col-md-8 col-sm-8">
						<select name="category" id="category" class="form-control col-md-6">
							<option value="">===分类===</option>
							<c:forEach var="category" items="${categoryList}">
								<option value="${category}" <c:if test="${sc.category == category}">selected</c:if>>
									<c:if test='${category == "-1"}'>未分类</c:if>
									<c:if test='${category != "-1"}'>${category}</c:if>
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			</c:if>
			<div class="col-md-3 col-sm-6">
				<div class="form-group">
					<label class="control-label col-md-4 col-sm-4 text-left">业务日期</label>
					<div class="input-large  col-md-8 col-sm-8">
						<input type="text" class="form-control date-picker" name="createDate" message="入库日期" data-date-format="yyyy-mm-dd" value="${sc.createDate}" >
					</div>
				</div>
			</div>
			<div class="text-center col-md-3 col-sm-6">
				<div class="form-group">
					<label for="chelp" class="control-label col-md-5 col-sm-4 text-left"><font color="ff0000">*</font>调价说明</label>
					<div class="input-large  col-md-7 col-sm-8">
						<input type="text" class="form-control" validate="required:true" name="price_note" id="price_note" value="${price_note }"/>
					</div>
				</div>
			</div>
			<div class="text-right col-md-3 col-sm-6">
				<div class="form-group">
					<div class="col-xs-12">
						<button class="btn btn-primary" type="button" id="btnSaveDraft"><qc:message code="system.common.btn.draft"/></button>
						<button class="btn btn-primary" type="button" id="btnSave"><qc:message code="system.common.btn.submit"/></button>
						<a class="btn btn-default" href="Price.do?cmd=priceList"><qc:message code="system.common.btn.back"/></a>
					</div>
				</div>
			</div>
		</div>
		
		<input type="hidden" name="catFieldName" id="arriveDate" value="${catFieldName}" />
		
		<div class="clear">
			<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Price", "itemListGridAjax") %>" searchForm="#priceItemForm"></table>
		</div>
		<input type="hidden" name="draftFlg" id="draftFlg" value="N"/>
	</form>
</div>
