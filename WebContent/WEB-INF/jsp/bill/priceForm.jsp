<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var gridData = ${gridData}; 
	var billItems = ${billItemList};
	var isDraft = ${isDraft};
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
	<div class="action_bar text-right">
		<button class="btn btn-primary" id="btnSaveDraft" type="button"><qc:message code="system.common.btn.draft"/></button>
		<button class="btn btn-primary" id="btnSave" type="button"><qc:message code="system.common.btn.submit"/></button>
		<c:if test="${isDraft == true}">
		<a class="btn btn-default" href="Price.do?cmd=priceList"><qc:message code="system.common.btn.back"/></a>
		</c:if>
		<c:if test="${isDraft != true}">
		<a class="btn btn-default" href="Price.do?cmd=billList"><qc:message code="system.common.btn.back"/></a>
		</c:if>
	</div>
	
	<form class="form-horizontal admin-form" role="form" 
		action="<%= BaseController.getCmdUrl("Price", "savePrice") %>" id="userItemsForm" class="searchForm" method="post">
		
		<input type="hidden" name="originBillId" id="originBillId" value="${originBill.billId}" />
		<input type="hidden" name="arriveDate" id="arriveDate" value="${originBill.arriveDate }" />
		<c:if test="${not empty newBill}">
		<input type="hidden" name="billId" id="billId" value="${newBill.billId}" />
		</c:if>
		<table class="table table-bordered dataTable01">
		<c:if test="${empty newBill}">
			<colgroup>
				<col width="10%" />
				<col width="15%" />
				<col width="10%" />
				<col width="15%" />
				<col width="10%" />
				<col width="15%" />
				<col width="10%" />
				<col width="15%" />
	 		</colgroup>
	 	</c:if>
	 	<c:if test="${not empty newBill}">
	 		<colgroup>
				<col width="14%" />
				<col width="20%" />
				<col width="13%" />
				<col width="20%" />
				<col width="13%" />
				<col width="20%" />
	 		</colgroup>
	 	</c:if>
			<tbody>
				<tr>
					<th>入库单编号</th>
					<td>${originBill.billId}</td>
					<th>入库日期</th>
					<td>${originBill.arriveDate}</td>
					<th>备注</th>
					<td>${originBill.note}</td>
				<c:if test="${empty newBill}">
					<th>调价说明</th>
					<td><input type="text" name="price_note" class="form-control" value="${price_note }"/></td>
				</c:if>
				</tr>
				<c:if test="${not empty newBill}">
				<tr>
					<th>调价单编号</th>
					<td>${newBill.billId}</td>
					<th>单据状态</th>
					<td>${newBill.stateName}</td>
					<th>调价说明</th>
					<td><input type="text" name="price_note" class="form-control" value="${newBill.custUserName}"/></td>
				</tr>
				</c:if>
			</tbody>
		</table>
		
		<div class="info-box border">
			<div class="box-title">
				<div class="row">
					<label class="title col-lg-8">调价商品</label>
				</div>
			</div>
			<div class="box-body no-padding">
				<div class="row">
					<div class="col-lg-12">
						<table id="grid"></table>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" name="draftFlg" id="draftFlg" value="N"/>
	</form>
</div>
