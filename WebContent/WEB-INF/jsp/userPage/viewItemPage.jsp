<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="java.util.List"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemPropertyModel"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.lang.Integer" %>

<div class="admin_body">
	<h3 class="page_title">${pageTitle}</h3>	
				
	<div class="slideshow">
		<c:if test="${not empty itemSlideImgList }">
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<c:set var="index" value="0"></c:set>
				<c:forEach var="slideImg" items="${itemSlideImgList}">
				<li data-target="#carousel-example-generic" data-slide-to="${index}" <c:if test="${index == 0 }"> class="active" </c:if>></li>
				<c:set var="index" value="${index + 1}"></c:set>
				</c:forEach>
			</ol>
			
			<!-- Wrapper for slides -->
			<div class="carousel-inner">
				<c:set var="index" value="0"></c:set>
				<c:forEach var="slideImg" items="${itemSlideImgList}">
				<div class="item <c:if test="${index == 0 }"> active </c:if>">
					<c:if test="${not empty slideImg.widgetContent}">
					<img src='/uploaded/itemwidget/${slideImg.widgetContent}' />
					</c:if>
				</div>
				<c:set var="index" value="1"></c:set>
				</c:forEach>
			</div>
			
			<!-- Controls -->
			<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left"></span>
			</a>
			<a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>
		</c:if>
	</div>
	
	<div class="item_detail" style="width: 100%;">
		<h3>${itemName}</h3>
		<div class="item_block">
			<dl class="dl-horizontal col-lg-3 col-md-4 col-xs-6">
		<% 
			List<UserItemPropertyModel> fieldList = (List<UserItemPropertyModel>)request.getAttribute("fieldList");
			UserItemModel userItem = (UserItemModel)request.getAttribute("item"); 
			String priceField = "";
			String unitField = "";
			boolean isSetPriceDefault = false;
			for (int i=0; i<fieldList.size(); i++){
				if (StringUtils.equals(fieldList.get(i).getPropertyTypeCd(), "PT0002")){
					if (!isSetPriceDefault){
						priceField = fieldList.get(i).getPropertyName();
						if (fieldList.get(i).getPropertyValue().equals("Y")){
							isSetPriceDefault = true;
						}
					}
				}
				if (StringUtils.equals(fieldList.get(i).getPropertyTypeCd(), "PT0007")){
					unitField = fieldList.get(i).getPropertyName();
				}
		%>
				<dt><%=fieldList.get(i).getPropertyDesc() %>:</dt>
				<dd><%=userItem.get(fieldList.get(i).getPropertyName()) %></dd>
					
		<% } %>
			</dl>
			<div class="col-lg-9 col-md-8 col-xs-6 item_image">
			    <img src="<%=userItem.getItemBigImg() %>" class="thumbnail">
			</div>
		</div>
	<% if (!StringUtils.isEmpty(priceField) && !StringUtils.isEmpty(userItem.get(priceField))){ %>
				<blockquote style="padding-right: 0px;">
					<div class="row">
						<div class="col-lg-9 col-md-8 col-sm-9" >
							<span class="item_price">￥<%=userItem.get(priceField) %><% if (!unitField.equals("")) {%>&nbsp;
							<% if (!StringUtils.isEmpty(userItem.get(unitField))) {%>
							/&nbsp;<%=userItem.get(unitField) %><%}} %></span>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-3 alignR no-padding" id="cartWrap">
							<a class="btn btn-xs btn-danger" onclick="qtyControl(this, '<%= userItem.getItemId() %>', -1);" userId="<%=userItem.getUserId() %>"><i class="fa fa-minus"></i></a>		            			
		           			<input class="qty alignC form-control" type="text" name="qty" data-role="none" value="${qty}" onchange="qtyChanged(this, '<%= userItem.getItemId() %>', '<%=userItem.getUserId() %>');" id="qty_<%= userItem.getItemId() %>">
							<a class="btn btn-xs btn-success" onclick="qtyControl(this, '<%= userItem.getItemId() %>', 1);" userId="<%=userItem.getUserId() %>"><i class="fa fa-plus"></i></a>
						</div>
					</div>
				</blockquote>
	<% } %>
	</div>
	
	<div class="item_content">
		<c:forEach var="itemWidget" items="${itemWidgetList }">
		<div class="item_widget col-md-${itemWidget.colClass}">
			<c:if test="${itemWidget.widgetType == 'WT0002' }">
			${itemWidget.widgetContent }
			</c:if>
			<c:if test="${itemWidget.widgetType != 'WT0002' }">
			<img src='/uploaded/itemwidget/${itemWidget.widgetContent}' class="img-rounded content-image"/>
			</c:if>
		</div>
		</c:forEach>
	</div>
		
	<div id="comment" class="pdb20">
	<%@ include file="/WEB-INF/jsp/userPage/comment.jsp"%>
	</div>
	
</div>