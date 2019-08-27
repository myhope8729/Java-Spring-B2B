<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<%@page import="com.kpc.eos.model.bizSetting.UserItemPropertyModel"%>
<%@page import="com.kpc.eos.model.bill.BillLineModel"%>
<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<jsp:useBean id="itemProp" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="billLine" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="billInfo" class="com.kpc.eos.model.bill.BillHeadModel" scope="request" />
<jsp:useBean id="colCnt" class="java.lang.String" scope="request"/>

<h3 class="page_title">${billInfo.hostUserName} &nbsp; 出库单</h3>
<div class="admin_body">
	<form id="billProcPrintForm" name="billProcPrintForm" onsubmit="reloadGrid();return false;" class="form-horizontal form-inline"> 
		<table class="table table-nobordered dataTable01"  style="width:95%; margin: 20px auto;">
			<colgroup>
				<col width="50%" />
				<col width="50%" />
	 		</colgroup>
			<tbody>
				<tr>
					<td>购买单位: ${billInfo.custUserName}</td>
					<td style="text-align:right">联系人: ${billInfo.custContactName}</td>
				</tr>
				<tr>
					<td>配送地址: ${billInfo.addrLocationName} ${billInfo.addrAddress}</td>
					<td style="text-align:right">电话: ${billInfo.addrTelNo}
						<c:if test="${not empty billInfo.addrMobile}">&nbsp;${billInfo.addrMobile}</c:if>
					</td>
				</tr>
			</tbody>
		</table>
		
		<table class="table table-bordered dataTable01" cellspacing="0" cellpadding="0" style="width:95%; margin: 20px auto; border:3px solid black;">
			<tbody>
				<tr>
					<c:choose>
						<c:when test="${colCnt gt 0}">
							<c:set var="cnt" value="${colCnt}"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="cnt" value="1"></c:set>
						</c:otherwise>
					</c:choose>
					<c:forEach begin="1" end="${cnt}" var="idx">
						<td>序号</td>
						<c:forEach var="itemPropertyObj" items="${itemProp}">
							<td>${itemPropertyObj.propertyDesc}</td>
						</c:forEach>
						<c:choose>
							<c:when test="${qtyMark eq 'Y'}">
								<td>订单数</td>
								<td>配送数</td>
							</c:when>
							<c:otherwise>
								<td>数量</td>
							</c:otherwise>
						</c:choose>
						<td>单价</td>
						<td>金额</td>
						<c:if test="${noteMark eq 'Y'}">
							<td>备注</td>
						</c:if>
					</c:forEach>
				</tr>
				<%
					String today = new SimpleDateFormat("M/dd/yyyy").format(new Date());
					Integer rowCnt = (billLine.size() % Integer.parseInt(colCnt)) == 0 ? billLine.size() / Integer.parseInt(colCnt) : billLine.size() / Integer.parseInt(colCnt) + 1;
					double totalAmt = (double)0;
					double totalQty1 = (double)0;
					double totalQty2 = (double)0;
					
					double totalWeight = (double)0;
					double totalVolumn = (double)0;
					
					for (int i = 0; i < rowCnt; i++){
				%>
						<tr>
				<%
						for (int j = 0; j < Integer.parseInt(colCnt); j++){
							int idx = j + (i * Integer.parseInt(colCnt));
							BillLineModel billLineObj = null;
							if (idx >= billLine.size()){
								idx = -1;
							}else{
								billLineObj = (BillLineModel)billLine.get(idx);
								totalAmt += Double.valueOf(billLineObj.getTot2());
								totalQty1 += Double.valueOf(billLineObj.getQty());
								totalQty2 += Double.valueOf(billLineObj.getQty2());
								
								if (StringUtils.isNotEmpty(billLineObj.getJsQty())){
									totalWeight += Double.valueOf(billLineObj.getWeight()) * Double.valueOf(billLineObj.getJsQty());
									totalVolumn += Double.valueOf(billLineObj.getVolumn()) * Double.valueOf(billLineObj.getJsQty());
								}else{
									totalWeight += Double.valueOf(billLineObj.getWeight()) * Double.valueOf(billLineObj.getQty2());
									totalVolumn += Double.valueOf(billLineObj.getVolumn()) * Double.valueOf(billLineObj.getQty2());
								}
							}
				%>
							<td><%= idx==-1?"":idx + 1 %></td>
				<%
							for (int k = 0; k < itemProp.size(); k++){
								UserItemPropertyModel upm = (UserItemPropertyModel) itemProp.get(k);
				%>
								<td><%= idx==-1?"":HtmlSelectTag.getFieldValue(billLineObj, upm.getPropertyName()) %></td>
				<%
							}
				%>
							<c:choose>
								<c:when test="${qtyMark eq 'Y'}">
									<td><%= idx==-1?"":billLineObj.getQty() %></td>
									<td><%= idx==-1?"":billLineObj.getQty2() %></td>
								</c:when>
								<c:otherwise>
									<td><%= idx==-1?"":billLineObj.getQty2() %></td>
								</c:otherwise>
							</c:choose>
							<td><%= idx==-1?"":billLineObj.getPrice2() %></td>
							<td><%= idx==-1?"":billLineObj.getTot2() %></td>
							<c:choose>
								<c:when test="${noteMark eq 'Y'}">
									<td>
										<%= idx==-1?"": StringUtils.isEmpty(billLineObj.getJsDisplay())?"":billLineObj.getJsDisplay() %>
										<%= idx==-1?"": billLineObj.getNote() %>
									</td>
								</c:when>
							</c:choose>
				<%
						}
				%>
						</tr>
				<%
					}
				%>
				<%
					if (Integer.parseInt(colCnt) == 1){
				%>
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
							<c:choose>
								<c:when test="${qtyMark eq 'Y'}">
									<td><%= totalQty1 %></td>
									<td><%= totalQty2 %></td>
								</c:when>
								<c:otherwise>
									<td><%= totalQty2 %></td>
								</c:otherwise>
							</c:choose>
							<td></td>
							<td><%= totalAmt %></td>
							<c:choose>
								<c:when test="${noteMark eq 'Y'}">
									<td></td>
								</c:when>
							</c:choose>
						</tr>
				<%
						}
				%>
			</tbody>
		</table>
		<table class="table table-nobordered dataTable01" style="width:95%; margin: 20px auto;">
			<colgroup>
				<col width="70%" />
				<col width="30%" />
	 		</colgroup>
			<tbody>
				<tr>
					<td>
						总计金额(元): <%= billInfo.getTotal2() %>&nbsp;&nbsp;&nbsp;&nbsp;
						人民币大写:
						<%= billInfo.getRmbTotalAmt() %>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>单据编号: 
						<c:choose>
							<c:when test="${empty billInfo.bnoUser}">${billInfo.billId}</c:when>
							<c:otherwise>${billInfo.bnoUser}</c:otherwise>
						</c:choose>
					</td>
					<td style="text-align:right">
						<c:if test="${not empty billInfo.time1}">
							送货时间：${billInfo.time1}至${billInfo.time2}
						</c:if>
						<c:choose>
							<c:when test="${bzMark eq 'Y'}">
								总重量: <%= totalWeight %> ${weightUnit},
								总体积: <%= totalVolumn %> ${volumnUnit}
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>联系人: 
						<c:choose>
							<c:when test="${empty billInfo.clerkno2}">
								${billInfo.hostContactName}
							</c:when>
							<c:otherwise>
								${billInfo.clerkName}
							</c:otherwise>
						</c:choose>
						&nbsp;电话：
						<c:choose>
							<c:when test="${empty billInfo.clerkno2}">
								${billInfo.hostTelNo}
							</c:when>
							<c:otherwise>
								${billInfo.clerkTelNo}
							</c:otherwise>
						</c:choose>
					</td>
					<td style="text-align:right">
						<c:if test="${not empty bizDataCar.c2}">
							配送：${bizDataCar.c2}
						</c:if>
						<c:if test="${not empty bizDataCar.c3}">
							电话：${bizDataCar.c3}
						</c:if>
						<c:if test="${not empty bizDataCar.c1}">
							车号：${bizDataCar.c1}
						</c:if>
					</td>
				</tr>
				<tr>
					<td>收货签名(盖章)：</td>
					<td style="text-align:right">打印日期：<%= today %></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
