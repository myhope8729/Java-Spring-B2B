<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kpc.eos.model.billProcMng.BillProcModel"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.model.billProcMng.PrepayBillModel" %>
<%@page import="org.apache.commons.lang.StringUtils"%>

<jsp:useBean id="bpList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="ppList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="order" class="com.kpc.eos.model.bill.BillHeadModel" scope="request"/>
<jsp:useBean id="itemList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="itemNameCol" class="java.lang.String" scope="request"/>
<jsp:useBean id="itemUnitCol" class="java.lang.String" scope="request"/>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">订单明细</h3>
    <a data-role="button" data-icon="back" data-mini="true" data-iconpos="notext" class="ui-btn-right" href="<%= BaseController.getCmdUrl("Order", "orderList") %>"  id="btnBack"><qc:message code="system.common.btn.back"/></a>
</div>

<div id="page-main" role="main" class="ui-content p-cart p-shopcart" >
	<table class="table dataTable02">
		<tbody>
<%
	for (int i = 0; i < itemList.size(); i++){
		UserItemModel userItem = (UserItemModel)itemList.get(i);
		String imgPath = userItem.getItemMediumImg();
		
		String itemName = (String)HtmlSelectTag.getFieldValue(userItem, itemNameCol);
		String itemPrice = (String)HtmlSelectTag.getFieldValue(userItem, order.getPricecol());
		String itemUnit = (String)HtmlSelectTag.getFieldValue(userItem, itemUnitCol);
		String itemPriceInfo = itemPrice + (StringUtils.isEmpty(itemUnit)?"":"/"+itemUnit);
%>
		<tr>
			<td class="store-item-image" style="padding:5px 2px;">
				<a><img border="0" src="<%= imgPath %>"></a>
			</td>
			<td class="store-item-info">
				<a class="store-item-title" style="margin:0;"><%= itemName %></a>
				<div class="store-item-note">
					<%= userItem.getNote() %>
				</div>
				<div class="store-item-price" style="font-size:11px;">
					<div class="form-group">
						<div class="col-xs-4" style="padding:0;">
							<%= itemPriceInfo %>
						</div>
						<div class="col-xs-8 text-right" style="color: black;">
							<%= Math.round(Double.valueOf(userItem.getPrice2())) %>x<%= Math.round(Double.valueOf(userItem.getQty2()))%>=<%=Math.round(Double.valueOf(userItem.getTotal_amt2())) %>
						</div>
					</div>
               	</div>
			</td>
		</tr>
<%
	}
%>
		</tbody>
	</table>
	
	<table class="table dataTable03" style="margin-top:10px;margin-bottom:0;">
		<tbody>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;min-width:70px;">
					订单编号:
				</td>
				<td style="padding: 0; font-size:13px;width:100%;">
					<c:if test="${not empty order.bnoUser}">${order.bnoUser}</c:if>
					<c:if test="${empty order.bnoUser}">${order.billId}</c:if>
				</td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;">创建时间:</td>
				<td style="padding: 0; font-size:13px;">${order.createDate}</td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;">单据状态:</td>
				<td style="padding: 0; font-size:13px;">
					${order.stateName}&nbsp;<c:if test="${not empty billProc.billHead.paymentType}"> / ${billProc.billHead.paymentType}</c:if>
				</td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; font-size:13px;">付款方式:</td>
				<td style="padding: 0; font-size:13px;">
					${order.paytypeName}<c:if test="${not empty order.paymentType}">/${order.paymentType}</c:if>
				</td>
			</tr>
			<tr>
				<td style="padding: 2px 4px; vertical-align: top; font-size:13px;">付款状态:</td>
				<td style="padding: 0; font-size:13px;">
					<c:if test="${empty ppList}">无</c:if>
					<c:if test="${not empty ppList}">
						<table class="table dataTable03" style="margin-bottom:0;">
							<c:forEach var="prePay" items="${ppList}">
								<tr>
									<td style="padding:0; font-size:13px;">
										${prePay.payType2}
									</td>
									<td style="padding:0; font-size:13px;">
										${prePay.totalAmt}
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div role="main" class="ui-content">
	<div class="info-box">
		<div class="box-title">
			<label class="title">收货地址</label>
		</div>
		<div class="box-body">
			<table class="table dataTable03" style="margin:0;">
				<tbody>
					<tr>
						<td>${order.addrLocationName}</td>
					</tr>
					<tr>
						<td>${order.addrAddress}</td>
					</tr>
					<tr>
						<td>
							${order.addrContactName}&nbsp;&nbsp;${order.addrTelNo}
							<c:if test="${not empty order.addrMobile}">
								,&nbsp;${order.addrMobile}
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<%
		
		if ( (bpList != null) && (bpList.size() > 0) ){
	%>
		<div class="info-box" style="margin-bottom:20px;">
			<div class="box-title">
				<label class="title">订单处理记录</label>
			</div>
			<div class="box-body">
	<%
			for (int i = 0; i < bpList.size(); i++){
				BillProcModel history = (BillProcModel)bpList.get(i);
	%>
				<table class="table dataTable03">
					<tbody>
	<%
						if (StringUtils.isNotEmpty(history.getUpdateDate())){
	%>
						<tr>
							<td><%= history.getUpdateDate() %></td>
						</tr>
	<%
						}
	%>
						<tr>
	<%
							if (history.getState().equals("PS0001")){
	%>
							<td style="color:#e86462;">
								<%= history.getEmpName() %> &nbsp;&nbsp;
								<%= history.getBillProcFullName() %> &nbsp;&nbsp;
								<%= history.getStateName() %>
							</td>
	<%
							}else{
	%>
							<td>
								<%= history.getEmpName() %> &nbsp;&nbsp;
								<%= history.getBillProcFullName() %> &nbsp;&nbsp;
								<%= history.getStateName() %>
							</td>
	<%
							}
	%>
						</tr>
					</tbody>
				</table>
	<%
				if (i < bpList.size() - 1){
	%>
				<div style="border-bottom:2px solid #f3f3f3;margin-top:5px;">
					
				</div>
	<%
				}
			}
	%>
			</div>
		</div>
	
	<%
		}
	%>
</div>
