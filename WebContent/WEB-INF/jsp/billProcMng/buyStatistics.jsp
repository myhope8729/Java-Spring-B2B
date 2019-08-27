<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page import="com.kpc.eos.core.util.SessionUtil"%>

<%@page import="com.kpc.eos.model.billProcMng.BuyStatisticModel"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<jsp:useBean id="buyList" class="java.util.ArrayList" scope="request"/>

<h3 class="page_title">采购单</h3>

<div class="admin_body">
	<form id="buyStatistic" name="buyStatistic" onsubmit="reloadGrid();return false;" class="form-inline">
		<input type="hidden" id="createDateFrom1" name="createDateFrom1" value="${sc.createDateFrom1}" />
		<input type="hidden" id="createDateTo1" name="createDateTo1" value="${sc.createDateTo1}" />
		
		<div class="action_bar row">
			<div class="col-xs-4">
				<label class="control-label mgt10"><strong>到货日期 : ${sc.createDateFrom1} 至 ${sc.createDateTo1}</strong></label>
			</div>
			<div class="col-xs-8 text-right">
				<a target="_blank" class="btn btn-primary" href='<%= BaseController.getCmdUrl("BillProc", "buyStatisticPrintForm") %>&createDateFrom1=${sc.createDateFrom1}&createDateTo1=${sc.createDateTo1}'><qc:message code="system.common.btn.print"/></a>
				<a href="<%= BaseController.getCmdUrl("BillProc", "billProcUncheckedList") %>" class="btn btn-default mgl10" id="btnBack"><qc:message code="system.common.btn.back" /></a>
			</div>
		</div>
	</form>
	
	<%
		for (int i = 0; i < buyList.size(); i++){
			BuyStatisticModel buyModel = (BuyStatisticModel)buyList.get(i);
	%>
			<table class="table dataTable01" cellspacing="0" cellpadding="0" style="margin-bottom:0px;margin-top:30px;">
				<tbody>
					<tr>
						<th style='font-size:16px;font-weight:normal;'>
							[<%= i+1 %>] &nbsp;&nbsp;
							<%= buyModel.getItemNo() %> &nbsp;&nbsp;
							<%= buyModel.getItemName() %> &nbsp;&nbsp;
							数量( <%= buyModel.getTotalQty() %> )
						</th>
					</tr>
				</tbody>
			</table>
	<%
			List<BuyStatisticModel> buyInfo = buyModel.getBuyStatisticInfo();
			if (buyInfo == null || buyInfo.size() == 0) continue;
			String curDistName = buyInfo.get(0).getDistributeName();
	%>
			<table class="table dataTable01 table-bordered" cellspacing="0" cellpadding="0" style="margin-bottom:0px;table-layout:fixed;">
				<tbody>
					<tr>
	<%
			for (int j = 0; j < buyInfo.size(); j++)
			{
				BuyStatisticModel distInfo = (BuyStatisticModel)buyInfo.get(j);
				
				if (!StringUtils.equals(curDistName, distInfo.getDistributeName())){
					curDistName = distInfo.getDistributeName();
	%>
					</tr>
				</tbody>
			</table>
			<table class="table dataTable01 table-bordered" cellspacing="0" cellpadding="0" style="margin-bottom:0px;table-layout:fixed;">
				<tbody>
					<tr>
						<td>
							<%= distInfo.getCustShortName() %> <br/>
							<%= distInfo.getQty() %>
						</td>
	<%
				}
				else
				{
	%>
					<td>
						<%= distInfo.getCustShortName() %> <br/>
						<%= distInfo.getQty() %>
					</td>
	<%
				}
			}
	%>
					</tr>
				</tbody>
			</table>
	<%
		}
	%>
</div>