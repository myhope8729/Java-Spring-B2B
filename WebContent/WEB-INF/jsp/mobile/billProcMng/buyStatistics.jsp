<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page import="com.kpc.eos.core.util.SessionUtil"%>

<%@page import="com.kpc.eos.model.billProcMng.BuyStatisticModel"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>


<script>
	var invalidNumber="<qc:message code='system.invalid.number' />";
</script>

<jsp:useBean id="buyList" class="java.util.ArrayList" scope="request"/>

<div data-role="header"  data-theme="b" >
    <h3 class="page_title">采购单</h3>
	<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="b" data-inline="true" class="ui-btn-right">Back</a>   
</div>
	
<div role="main" class="ui-content mgt10">

	<form id="buyStatistic" name="buyStatistic" onsubmit="reloadGrid();return false;" class="form-inline">
		<input type="hidden" id="createDateFrom1" name="createDateFrom" value="${sc.createDateFrom1}" />
		<input type="hidden" id="createDateTo1" name="createDateTo" value="${sc.createDateTo1}" />
	</form>
	
	<%
		for (int i = 0; i < buyList.size(); i++){
			BuyStatisticModel buyModel = (BuyStatisticModel)buyList.get(i);
			
			Double totIn = Double.valueOf(buyModel.getInQty()) * Double.valueOf(buyModel.getInPrice());
			Double totSale = Double.valueOf(buyModel.getInQty()) * Double.valueOf(buyModel.getSalePrice());
			Double totGain = totSale - totIn;
			
			double totRate = 0;
			if (Double.valueOf(buyModel.getSalePrice()) > 0.000) {
				totRate = ((Double.valueOf(buyModel.getSalePrice()) - Double.valueOf(buyModel.getInPrice())) / Double.valueOf(buyModel.getSalePrice())) * 100;
				totRate = Math.floor(totRate * 10) / 10;
			}
	%>
			<label style="font-size:14px;">
				<b>
					<%= buyModel.getItemNo() %> &nbsp;
					<%= buyModel.getItemName() %> &nbsp;
					<%= buyModel.getUnit()==null?"":buyModel.getUnit() %>
				</b>
			</label>
			<table class="table dataTable01 table-bordered" cellspacing="0" cellpadding="0" style='margin-bottom:20px;'>
				<tbody>
					<tr>
						<th style="text-align:center;width:25%;">订货数</th>
						<th style="text-align:center;width:25%;">采购数</th>
						<th style="text-align:center;width:25%;">采购价 </th>
						<th style="text-align:center;width:25%;">采购额 </th>
					</tr>
					<tr>
						<td style="vertical-align: middle; color:blue;"><%= buyModel.getTotalQty() %></td>
						<td>
							<input type="text" value="<%= buyModel.getInQty() %>" rowId="<%= buyModel.getItemId() %>" name="inQty" />
						</td>
						<td>
							<input type="text" value="<%= buyModel.getInPrice() %>" rowId="<%= buyModel.getItemId() %>" name="inPrice"/>
						</td>
						<td style="vertical-align: middle;">
							<%= totIn %>
						</td>
					</tr>
					<tr>
						<th style="text-align:center;">销售价</th>
						<th style="text-align:center;">销售额</th>
						<th style="text-align:center;">毛利</th>
						<th style="text-align:center;">毛利率</th>
					</tr>
					<tr>
						<td>
							<input type="text" value="<%= buyModel.getSalePrice() %>" rowId="<%= buyModel.getItemId() %>" name="salePrice"/>
						</td>
						<td style="vertical-align: middle;"><%= totSale %></td>
						<td style="vertical-align: middle; color:red;"><%= totGain %></td>
						<td style="vertical-align: middle; olor:red;"><%= totRate==0?"":(totRate + "%") %></td>
					</tr>
				</tbody>
			</table>
	<%	
		}
	%>
	<div class="form-actions">
		<a class="mgr-no btn btn-default" href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" data-role="button" id="btnBack"><qc:message code="system.common.btn.return" /></a>
	</div>
</div>