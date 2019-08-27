<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<%@page import="com.kpc.eos.model.billProcMng.SupplyStatisticModel"%>
<%@page import="com.kpc.eos.model.billProcMng.DistributeStatisticModel" %>
<%@page import="com.kpc.eos.model.bizSetting.HostCustModel"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag" %>

<jsp:useBean id="hcList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="distList" class="java.util.ArrayList" scope="request"/>

<%
	String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
%>


<div class="admin_body">
	<form id="billProcPrintForm" name="billProcPrintForm" onsubmit="reloadGrid();return false;" class="form-horizontal form-inline">
		<%
			int custLen = hcList.size();
			for (int i = 0; i < distList.size(); i++){
				DistributeStatisticModel distObj = (DistributeStatisticModel)distList.get(i);
				if ( (i % 50) == 0){
		%>
					<h3 class="page_title">拣货单(${userName} ${distributeName})</h3>
		
					<table class="table table-bordered dataTable01" cellspacing="0" cellpadding="0" style="table-layout:fixed;width:90%; margin: 20px auto; border:3px solid black;">
						<tbody>
		<%
				}
				if ( (i % 25) == 0){
		%>
					<tr>
						<th style="width:80px;padding:7px 2px 4px;text-align:center;">编码</th>
						<th style="width:250px;padding:7px 2px 4px;text-align:center;">品名</th>
						<th style="width:80px;padding:7px 2px 4px;text-align:center;">单位</th>
		<%
						for (int j = 0; j < custLen; j++){
							HostCustModel hc = (HostCustModel)hcList.get(j);
		%>
							<th style="padding:7px 0 4px;text-align:center;"><%= hc.getCustShortName() + "<br/>" + hc.getCarNo() %></th>
		<%
						}
		%>
						<th style="padding:7px 2px 4px;text-align:center;">合计</th>
					</tr>
		<%
				}
		%>
				<tr style="height:35x;">
					<td style="padding:3px 2px;"><%= distObj.getItemNo()==null?"":distObj.getItemNo() %></td>
					<td style="padding:3px 2px;"><%= distObj.getItemName()==null?"":distObj.getItemName() %></td>
					<td style="padding:3px 2px;text-align:center;"><%= distObj.getUnit()==null?"":distObj.getUnit() %></td>
		<%
				for (int j = 0; j < custLen; j++){
					String qty = (String)HtmlSelectTag.getFieldValue(distObj, "qty" + (j + 1));
		%>
					<td style="padding:3px 2px;text-align:right;"><%= "0".equals(qty)?"":qty %></td>
		<%
				}
		%>
					<td style="padding:3px 2px;text-align:right;"><%= distObj.getTotalQty() %></td>
				</tr>
		<%			
				if ( ((i+1) % 50) == 0 || (i == (distList.size() - 1))){
		%>
						</tbody>
					</table>
		<%
				}
			}
		%>
	</form>
</div>
