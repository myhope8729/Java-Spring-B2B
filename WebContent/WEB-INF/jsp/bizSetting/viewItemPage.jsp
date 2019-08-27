<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="java.util.List"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemPropertyModel"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.lang.Integer" %>

<div class="admin_body">
	<h3 class="page_title">预看商品详情</h3>	
	<div class="action_bar row">
		<div class="col-md-6 text-left">
		</div>
		<div class="col-md-6 text-right">
			<a type="button" class="btn btn-primary mgl10" href="UserItem.do?cmd=setItemPage&itemId=${item.itemId }"><qc:message code="system.common.btn.back"/></a>
		</div>
	</div>
				
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
	<div class="item_detail">
		<h3>${itemName}</h3>
		<dl class="dl-horizontal">
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
	<% if (!priceField.equals("")){ %>
		<blockquote>
			<p class="item_price">￥<%=userItem.get(priceField) %><% if (!unitField.equals("")) {%>&nbsp;/&nbsp;<%=userItem.get(unitField) %><%} %></p>
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
	
	<c:if test="${not empty commentList}">
	<div class="comment_list">
		
	</div>
	</c:if>
	<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("UserItem", "saveItemComment") %>" id="commentForm" method="post" enctype="multipart/form-data">
		<div class="comment_form container">
			<div class="form-group">
				<div class="col-md-1">
					<label class="pull-right">网友评论</label>
				</div>
				<div class="col-md-11">
					<label class="text-left">文明上网理性发言，请遵守<a href="#">网络评论服务协议</a></label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-11 col-md-offset-1">
					<textarea class="form-control" name="commentContent" rows="5"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-1">
					<label class="pull-right control-label">昵称</label>
				</div>
				<div class="col-md-2">
					<input type="text" name="empNickname" value="${loginUser.empName}" class="form-control"/>
				</div>
				<div class="col-md-5 text-left">
					<div class="fileinput fileinput-new" data-provides="fileinput">
						<span class="btn btn-primary btn-file">
							<span class="fileinput-new">添加图片 </span>
							<span class="fileinput-exists">变更图片</span>
							<input type="file" name="...">
						</span>
						<span class="fileinput-filename">
						</span>
						&nbsp; <a href="javascript:;" class="close fileinput-exists" data-dismiss="fileinput">&times;</a>
					</div>
				</div>
				<div class="col-md-4 text-right">
					<button class="btn btn-primary">提交评论</button>
				</div>
			</div>
		</div>
		<input type="hidden" name="itemId" value="${itemId}" />
		<input type="hidden" name="upperCommentId" value="${upperCommentId}" />
	</form>
</div>