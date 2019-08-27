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
	itemPriceCol = '${sale.pricecol}';
	itemPriceColDesc = '${sale.pricedesc}';
	var afterSavingUrl = "<%=BaseController.getCmdUrl("Sale", "saleList")%>";
</script>
<style>
	table.dataTable02 th {
		text-align: right;
	}
	table.dataTable02 td {
		text-align: left;
	}
</style>
<div id="sale-form-wrap" class="admin_body">
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
						<th width="11%">单据编号</th>
						<td width="37%">${sale.billId}</td>
						<th width="10%">单据状态</th>
						<td width="39%" colspan="3">${sale.stateName}</td>
					</tr>
					<tr>
						<th width="11%">供货方</th>
						<td width="37%">${sale.hostUserName}</td>
						<th width="10%">供货方联系人</th>
						<td width="12%">${sale.hostContactName}</td>
						<th width="9%">供货方电话</th>
						<td width="18%">${sale.hostTelNo} ${sale.hostMobileNo}</td>
					</tr>
					<tr>
						<th width="11%">订货方</th>
						<td width="37%">${sale.custUserName}</td>
						<th width="10%">订货人</th>
						<td width="12%">${sale.custContactName}</td>
						<th width="9%">订货人电话</th>
						<td width="18%">${sale.custTelNo} ${sale.custMobileNo}</td>
					</tr>
					<tr>
						<th width="11%">收货地址</th>
						<td width="37%">${sale.addrLocationName}${sale.addrAddress}</td>
						<th width="10%">收货人</th>
						<td width="12%">${sale.addrContactName}</td>
						<th width="9%">收货人电话</th>
						<td width="18%">${sale.addrTelNo} ${sale.addrMobile}</td>
					</tr>

					<tr>
						<th width="11%">付款方式/类别</th>
						<td width="37%">
							${sale.paytypeName}
							<c:if test="${not empty sale.paymentType }">
								/  ${sale.paymentType }
							</c:if>
						</td>
						<th width="10%">
							送货日期
						</th>
						<td width="12%">${sale.arriveDate}</td>
						<th width="9%">备注</th>
						<td width="18%">${sale.note}</td>
					</tr>
					<tr>
						<th width="11%">商品金额(元)</th>
						<td width="37%">
							<c:if test="${sale.itemAmt != sale.itemamount2 }"><span class='org-price'>(${sale.itemAmt })</span><br />${sale.itemamount2}</c:if>
							<c:if test="${sale.itemAmt == sale.itemamount2}">${sale.itemAmt}</c:if>
							<!-- , <span id="cartTotal"></span> -->
						</td>
						<th width="10%">运费</th>
						<td width="12%">
							<c:if test="${sale.freightAmt != sale.freightamount2 }"><span class='org-price'>(${sale.freightAmt })</span><br />${sale.freightamount2}</c:if>
							<c:if test="${sale.freightAmt == sale.freightamount2 }">${sale.freightAmt}</c:if>
						</td>
						<th>
							合计金额(元)
						</th>
						<td width="18%" >
							<c:if test="${sale.totalAmt != sale.total2 }"><span class='org-price'>(${sale.totalAmt })</span><br />${sale.total2}</c:if>
							<c:if test="${sale.totalAmt == sale.total2 }">${sale.totalAmt}</c:if>
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
		<c:if test="${sale.payState eq 'MT0001'}">
		
		</c:if>
</div>
