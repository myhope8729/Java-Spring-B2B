<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<%@page import="com.kpc.eos.model.billProcMng.BuyStatisticModel"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.lang.Math"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<jsp:useBean id="itemList" class="java.util.ArrayList" scope="request"/>

<%
	String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
%>


<div class="admin_body">
	<form id="billProcPrintForm" name="billProcPrintForm" onsubmit="reloadGrid();return false;" class="form-horizontal form-inline"> 
<%
	for (int i = 0; i < itemList.size(); i++)
	{
		BuyStatisticModel buyModel = (BuyStatisticModel)itemList.get(i);
%>
		<div class='bs-group'>
			<label><b>美宜多商品采购单 &nbsp;&nbsp; <%= today %></b></label>
			<label style='margin-bottom:10px'>[<%= i + 1%>] <%= "商品编码: " + buyModel.getItemNo() + ", 商品名称: " + buyModel.getItemName() + " ( 数量: " + buyModel.getTotalQty() + " )" %></label>
<%
			List<BuyStatisticModel> buyInfo = buyModel.getBuyStatisticInfo();
			
			if (buyInfo == null || buyInfo.size() == 0) continue;
			String curDistName = buyInfo.get(0).getDistributeName();
%>
			<table class="table table-bordered dataTable01" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
				<tbody>
					<tr>
<%
						for (int j = 0; j < buyInfo.size(); j++){
							BuyStatisticModel distInfo = (BuyStatisticModel)buyInfo.get(j);
							if (!StringUtils.equals(curDistName, distInfo.getDistributeName())){
								curDistName = distInfo.getDistributeName();
%>
								</tr>
							</tbody>
						</table>
						<table class="table table-bordered dataTable01" cellspacing="0" cellpadding="0">
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
		</div>
<%
	}
%>
	</form>
</div>
