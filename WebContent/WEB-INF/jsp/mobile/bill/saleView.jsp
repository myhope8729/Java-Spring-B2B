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

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a data-theme="b" data-role="button" data-icon="delete" data-inline="true" class="ui-btn-right" data-iconpos="left" href="javascript:window.close();"> 
    	<qc:message code="system.common.btn.close" /> 
    </a>
</div>

<div role="main" class="ui-content" id="sale-form-wrap">
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="false" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3>单据详情</h3>	
		<table class="table table-bordered dataTable01">
			<colgroup>
				<col width="35%" />
				<col width="65%" />
	 		</colgroup>
			<tbody>
				<tr>
					<th>单据编号</th>
					<td>${sale.billId}</td>
				</tr>
				<tr>
					<th>单据状态</th>
					<td>${sale.stateName}</td>
				</tr>
				<tr>
					<th>供货方</th>
					<td>${sale.hostUserName}</td>
				</tr>
				<tr>
					<th>供货方联系人</th>
					<td>${sale.hostContactName}</td>
				</tr>
				<tr>
					<th>供货方电话</th>
					<td>${sale.hostTelNo} ${sale.hostMobileNo}</td>
				</tr>
				<tr>
					<th>订货方</th>
					<td>${sale.custUserName}</td>
				</tr>
				<tr>
					<th>订货人</th>
					<td>${sale.custContactName}</td>
				</tr>
				<tr>
					<th>订货人电话</th>
					<td>${sale.custTelNo} ${sale.custMobileNo}</td>
				</tr>
				<tr>
					<th>收货地址</th>
					<td>${sale.addrLocationName}${sale.addrAddress}</td>
				</tr>
				<tr>
					<th>收货人</th>
					<td>${sale.addrContactName}</td>
				</tr>
				<tr>
					<th>收货人电话</th>
					<td>${sale.addrTelNo} ${sale.addrMobile}</td>
				</tr>
				<tr>
					<th>付款方式/类别</th>
					<td>
						${sale.paytypeName}
						<c:if test="${not empty sale.paymentType }">
							/  ${sale.paymentType }
						</c:if>
					</td>
				</tr>
				<tr>
					<th>送货日期</th>
					<td>${sale.arriveDate}</td>
				</tr>
				<tr>
					<th>备注</th>
					<td>${sale.note}</td>
				</tr>
				<tr>
					<th>商品金额(元)</th>
					<td>
						<c:if test="${sale.itemAmt != sale.itemamount2 }"><span class='org-price'>(${sale.itemAmt })</span><br />${sale.itemamount2}</c:if>
						<c:if test="${sale.itemAmt == sale.itemamount2}">${sale.itemAmt}</c:if>
					</td>
				</tr>
				<tr>
					<th>运费</th>
					<td>
						<c:if test="${sale.freightAmt != sale.freightamount2 }"><span class='org-price'>(${sale.freightAmt })</span><br />${sale.freightamount2}</c:if>
						<c:if test="${sale.freightAmt == sale.freightamount2 }">${sale.freightAmt}</c:if>
					</td>
				</tr>
				<tr>
					<th>合计金额(元)</th>
					<td>
						<c:if test="${sale.totalAmt != sale.total2 }"><span class='org-price'>(${sale.totalAmt })</span><br />${sale.total2}</c:if>
						<c:if test="${sale.totalAmt == sale.total2 }">${sale.totalAmt}</c:if>
					</td>
				</tr>
			</tbody>
		</table>				
	</div>
	<div class="clear">
		<table id="cartGrid"></table>
	</div>
	<div class="clear">
		<table id="bpGrid"></table>
	</div>
	<c:if test="${sale.payState eq 'MT0001'}">
	
	</c:if>
</div>
