<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<%@page import="com.kpc.eos.model.bizSetting.UserItemPropertyModel"%>
<%@page import="com.kpc.eos.model.bill.BillLineModel"%>
<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag"%>

<jsp:useBean id="itemProp" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="billLine" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="billInfo" class="com.kpc.eos.model.bill.BillHeadModel" scope="request" />

<form class="form-horizontal admin-form" role="form" id="billForm" class="searchForm" method="post">
	<input type="hidden" name="billId" id="billId" value="${bill.billId}" />
	<div class="admin_body">
		<h3 class="page_title">${pageTitle}</h3>
		<table class="table table-nobordered dataTable01" style="width:95%; margin: 20px auto;">
			<colgroup>
				<col width="50%" />
				<col width="50%" />
	 		</colgroup>
			<tbody>
				<tr>
					<td>退货单位:
						<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostUserName}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.custUserName}</c:if>
					</td>
					<td style="text-align:right">联系人:
						<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostContactName}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.custContactName}</c:if>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align:right">电话:
						<c:if test="${bill.rbillType == 'DT0002'}">${bill.hostTelNo}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.custTelNo}</c:if>
					</td>
				</tr>
			</tbody>
		</table>
		
		<table class="table table-bordered dataTable01" cellspacing="0" cellpadding="0" style="width:95%; margin: 20px auto; border:3px solid black;">
			<tbody>
				<tr>
					<td>序号</td>
					<c:forEach var="itemPropertyObj" items="${itemProp}">
					<td>${itemPropertyObj.propertyDesc}</td>
					</c:forEach>
					<td>数量</td>
					<td>单价</td>
					<td>金额(元)</td>
					<td>备注</td>
				</tr>
				<%
					for (int i = 0; i < billLine.size(); i++){
						BillLineModel billLineObj = (BillLineModel)billLine.get(i);
				%>
				<tr>
					<td><%= i + 1 %></td>
				<%
						for (int k = 0; k < itemProp.size(); k++){
							UserItemPropertyModel upm = (UserItemPropertyModel) itemProp.get(k);
				%>
					<td><%=HtmlSelectTag.getFieldValue(billLineObj, upm.getPropertyName()) %></td>
				<%
						}
				%>
					<td><%= billLineObj.getQty2() %></td>
					<td><%= billLineObj.getPrice2() %></td>
					<td><%= billLineObj.getTot2() %></td>
					<td><%= billLineObj.getNote() %></td>
				<%
					}
				%>
				</tr>
				<tr>
					<td>合计</td>
				<%
					for (int k = 0; k < itemProp.size(); k++){
						UserItemPropertyModel upm = (UserItemPropertyModel) itemProp.get(k);
				%>
					<td></td>
				<%
					}
				%>
					<td>${totalQtyData }</td>
					<td></td>
					<td>${totalAmtData }</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		
		<table class="table table-nobordered dataTable01" style="width:95%; margin: 20px auto;">
			<colgroup>
				<col width="70%" />
				<col width="30%" />
	 		</colgroup>
			<tbody>
				<tr>
					<td colspan="2">合计金额(元)：${bill.totalAmt }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人民币大写:${bill.rmbTotalAmt }</td>
				</tr>
				<tr>
					<td>
						单据编号：<c:if test="${not empty bill.bnoUser }">${bill.bnoUser }</c:if>
						<c:if test="${empty bill.bnoUser }">${bill.billId}</c:if>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						联系人：<c:if test="${bill.rbillType == 'DT0002'}">${bill.custContactName}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.hostContactName}</c:if>
					</td>
					<td style="text-align:right">电话:
						<c:if test="${bill.rbillType == 'DT0002'}">${bill.custTelNo}</c:if>
						<c:if test="${bill.rbillType != 'DT0002'}">${bill.hostTelNo}</c:if>
					</td>
				</tr>
				<tr>
					<td>
						收货签名(盖章)：
					</td>
					<td style="text-align:right">
						打印日期：${printDate }
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>