<%@page import="com.kpc.eos.core.util.MessageUtil"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var cartGridData = ${cartGridData}; 
	var bpList = ${bpList}; 
	itemPriceCol = '${order.pricecol}';
	itemPriceColDesc = '${order.pricedesc}';
	var afterSavingUrl = "<%=BaseController.getCmdUrl("Order", "orderList")%>";
</script>
<style>
	table.dataTable02 th {
		text-align: right;
	}
	table.dataTable02 td {
		text-align: left;
	}
</style>
<div id="order-form-wrap" class="admin_body">
	<h3 class="page_title">${pageTitle}
		<div class="action_bar text-right">
			<a class="btn btn-default" href="javascript:window.close();"><qc:message code="system.common.btn.close"/></a>
		</div>
	</h3>
	<div class="info-box">
		<div class="box-body no-padding">
			<table class="table table-bordered dataTable01">
				<tbody>
					<tr>
						<th width="11%">订货单编号</th>
						<td width="37%">${order.billId}</td>
						<th width="10%">单据状态</th>
						<td width="39%" colspan="3">${order.stateName}</td>
					</tr>
					<tr>
						<th width="11%">供货方</th>
						<td width="37%">${order.hostUserName}</td>
						<th width="10%">供货方联系人</th>
						<td width="12%">${order.hostContactName}</td>
						<th width="9%">供货方电话</th>
						<td width="18%">${order.hostTelNo} ${order.hostMobileNo}</td>
					</tr>
					<tr>
						<th width="11%">订货方</th>
						<td width="37%">${order.custUserName}</td>
						<th width="10%">订货人</th>
						<td width="12%">${order.custContactName}</td>
						<th width="9%">订货人电话</th>
						<td width="18%">${order.custTelNo} ${order.custMobileNo}</td>
					</tr>
					<tr>
						<th width="11%">收货地址</th>
						<td width="37%">${order.addrLocationName}${order.addrAddress}</td>
						<th width="10%">收货人</th>
						<td width="12%">${order.addrContactName}</td>
						<th width="9%">收货人电话</th>
						<td width="18%">${order.addrTelNo} ${order.addrMobile}</td>
					</tr>

					<tr>
						<th width="11%">付款方式/类别</th>
						<td width="37%">
							${order.paytypeName}
							<c:if test="${not empty order.paymentType }">
								/  ${order.paymentType }
							</c:if>
						</td>
						<th width="10%">
							送货日期
						</th>
						<td width="12%">${order.arriveDate}</td>
						<th width="9%">备注</th>
						<td width="18%">${order.note}</td>
					</tr>
					<tr>
						<th width="11%">商品金额(元)</th>
						<td width="37%">
							<c:if test="${order.itemAmt != order.itemamount2 }"><span class='org-price'>(${order.itemAmt })</span><br />${order.itemamount2}</c:if>
							<c:if test="${order.itemAmt == order.itemamount2}">${order.itemAmt}</c:if>
							<!-- , <span id="cartTotal"></span> -->
						</td>
						<th width="10%">运费</th>
						<td width="12%">
							<c:if test="${order.freightAmt != order.freightamount2 }"><span class='org-price'>(${order.freightAmt })</span><br />${order.freightamount2}</c:if>
							<c:if test="${order.freightAmt == order.freightamount2 }">${order.freightAmt}</c:if>
						</td>
						<th>
							合计金额(元)
						</th>
						<td width="18%" >
							<c:if test="${order.totalAmt != order.total2 }"><span class='org-price'>(${order.totalAmt })</span><br />${order.total2}</c:if>
							<c:if test="${order.totalAmt == order.total2 }">${order.totalAmt}</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="info-box">
		<div class="box-body no-padding">
			<table id="cartGrid"></table>
		</div>
	</div>
	
	<div class="info-box">
		<div class="box-body no-padding">
			<table id="bpGrid"></table>
		</div>
	</div>
	<c:if test="${order.payState eq 'MT0001'}">
	
	</c:if>
	
</div>
