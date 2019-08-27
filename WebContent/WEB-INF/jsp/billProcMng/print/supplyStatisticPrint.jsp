<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<%@page import="com.kpc.eos.model.billProcMng.SupplyStatisticModel"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<jsp:useBean id="userItemPropList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="supplyStatList" class="java.util.ArrayList" scope="request"/>

<%
	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
%>

<div class="admin_body">
	<form id="billProcPrintForm" name="billProcPrintForm" onsubmit="reloadGrid();return false;" class="form-horizontal form-inline"> 
		
	<%
		int size = supplyStatList.size();
		int tblCnt = (size <= 45) ? 1 : ((size / 45) + 1);
		int groupIdx = 0;
		for (int idx = 0; idx < size; idx++){
			SupplyStatisticModel supplyStatiscObj = (SupplyStatisticModel)supplyStatList.get(idx);
			if ((idx % 45) == 0){
				groupIdx++;
	%>
		<h3 class="page_title">${userName} &nbsp; 采购单</h3>
		<table class="table table-bordered dataTable01" cellspacing="0" cellpadding="0" style="width:95%; margin: auto; border:3px solid black;">
			<tbody>
				<tr>
					<th width="80">序号</th>
					<th>商品编码</th>
					<th>商品名称</th>
					<th>单位</th>
					<th>数量</th>
					<c:if test="${salePriceYn eq 'Y'}">
						<th>销售价</th>
					</c:if>
					<th>参考	</th>
				</tr>
	<%
			}
	%>
				<tr>
					<td><%= idx + 1 %></td>
					<td><%= supplyStatiscObj.getItemNo()==null?"":supplyStatiscObj.getItemNo() %></td>
					<td><%= supplyStatiscObj.getItemName()==null?"":supplyStatiscObj.getItemName() %></td>
					<td><%= supplyStatiscObj.getUnit()==null?"":supplyStatiscObj.getUnit() %></td>
					<td><%= supplyStatiscObj.getQty() %></td>
					<c:if test="${salePriceYn eq 'Y'}">
						<td><%= supplyStatiscObj.getSalePrice() %></td>
					</c:if>
					<td>
						<%= (supplyStatiscObj.getArriveDate()==null?"":supplyStatiscObj.getArriveDate() + ", ") +
							(supplyStatiscObj.getLastVendorName()==null?"":supplyStatiscObj.getLastVendorName() + ", ") + 
							(supplyStatiscObj.getLastPriceIn()==null?"":supplyStatiscObj.getLastPriceIn()) %>
					</td>
				</tr>
		<%			
				if ( ((idx+1) % 45) == 0 || (idx == (size - 1))){
		%>
			</tbody>
		</table>
		<table class="table table-nobordered dataTable01" style="width:95%; margin: auto;">
			<colgroup>
				<col width="80%"
				<col width="20%" />
	 		</colgroup>
			<tbody>
				<tr>
					<td style="text-align:right">第<%= groupIdx %>页 &nbsp;&nbsp;共<%= tblCnt %>页</td>
					<td style="text-align:right">打印日期：<%= today %></td>
				</tr>
			</tbody>
		</table>
		<%
				}
			}
		%>
	</form>
</div>
