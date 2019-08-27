<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="java.util.List"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemPropertyModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>


<div data-role="header" data-theme="b" >
   <h3 class="page_title">修改商品资料</h3>
   <a data-role="button" data-icon="back" data-mini="true" data-iconpos="notext" class="ui-btn-right" href="<%= BaseController.getCmdUrl("UserItem", "userItemList") %>"  id="btnBack"><qc:message code="system.common.btn.back"/></a>
</div>

<div role="main" class="ui-content mgt10 control-label">
	<form class="form-horizontal admin-form" action="<%= BaseController.getCmdUrl("UserItem", "saveUserItem") %>" id="userItemForm" method="post" enctype="multipart/form-data">
		<% 
		List<UserItemPropertyModel> fieldList = (List<UserItemPropertyModel>)request.getAttribute("fieldList");
		UserItemModel userItem = (UserItemModel)request.getAttribute("userItem"); 
		for (int i=0; i<fieldList.size(); i++){%>
		<div class="form-group <% if ("PT0001".equals(fieldList.get(i).getPropertyTypeCd())) { %>  <qc:errors items="${formErrors}" path="<%=fieldList.get(i).getPropertyName() %>" type="errorCls" /> <% } %> ">
	 		<label for="<%=fieldList.get(i).getPropertyName() %>" class="col-xs-4 text-right control-label">
				<% if ("PT0002".equals(fieldList.get(i).getPropertyTypeCd())) {%>
				<span class="required">*</span>
				<% } %>
				<%=fieldList.get(i).getPropertyDesc() %>
			</label>
			<div class="col-xs-8">
	 		<% if (!"PT0002".equals(fieldList.get(i).getPropertyTypeCd()) && !"PT0005".equals(fieldList.get(i).getPropertyTypeCd()) && !"".equals(fieldList.get(i).getPropertyTypeCd())) {%>
	 			<label class="mgt10 control-label"><B><% if ("".equals(userItem.get(fieldList.get(i).getPropertyName()))) { %>无<% }else{ %><%=userItem.get(fieldList.get(i).getPropertyName()) %><% } %></B></label>
	 			<input type="hidden" name="<%=fieldList.get(i).getPropertyName() %>" value="<%=userItem.get(fieldList.get(i).getPropertyName()) %>"/>
	 		<% }else{ %>
	 		<input <% if ("PT0005".equals(fieldList.get(i).getPropertyTypeCd())) {%> type="number" <%} else{%> type="text" <%} %>
	 			name="<%=fieldList.get(i).getPropertyName() %>" 
	 			value="<%=userItem.get(fieldList.get(i).getPropertyName()) %>" 
	 			class="form-control" data-clear-btn="true"
	 			placeholder="<%=fieldList.get(i).getPropertyDesc() %>"
	 			message="<%=fieldList.get(i).getPropertyDesc() %>"
	 			<% if ("PT0002".equals(fieldList.get(i).getPropertyTypeCd())) {%> validate="required: true, number: true" <%} %>
	 		/>
	 		<% } %>
	 		</div>
		</div>
		<% } %>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right">商品图片</label>
			<div class="col-xs-8 fileinput crop 
				<c:if test="${not empty userItem.itemImgPath}">fileinput-exists</c:if>
				<c:if test="${empty userItem.itemImgPath}">fileinput-new</c:if>" data-provides="fileinput">
				<div class="fileinput-preview thumbnail">
					<c:if test="${not empty userItem.itemImgPath}">
					<img src='/uploaded/useritem/${userItem.itemImgPath}'/>
					</c:if>
				</div>
				<img src='/uploaded/useritem/medium/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
				<div>
					<span class="default btn-file">
						<span class="fileinput-new btn btn-primary">选择图片</span>
						<span class="fileinput-exists btn btn-info">变更</span>
						<input type="file" name="imgFile" id="imgFile">
						</span>
						<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
				</div>
			</div>
			<label class="control-label text-right"><qc:message code="upload.filePixelSize.minimum" param="${minWidth},${minHeight}" /></label>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right"><span class="required">*</span>出货仓库</label>
			<div class="col-xs-8">
		 		<select name="storeId" id="storeId" validate="required: true" message="出货仓库">
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:forEach var="store" items="${storeList}">
					<option value="${store.storeId}" <c:if test="${userItem.storeId == store.storeId}"> selected</c:if>>${store.storeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right">按库存下单</label>
			<div class="col-xs-8">
		 		<select name="stockMark" id="stockMark">
		 			<option value=""><qc:message code="sc.please.select" /></option>
					<option value="Y" <c:if test="${userItem.stockMark == 'Y'}"> selected</c:if>>是</option>
					<option value="N" <c:if test="${userItem.stockMark == 'N'}"> selected</c:if>>否</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right">状态</label>
			<div class="col-xs-8">
		 		<select name="state" id="state">
					<option value="ST0001" <c:if test="${userItem.state == 'ST0001'}"> selected</c:if>>正常</option>
					<option value="ST0002" <c:if test="${userItem.state == 'ST0002'}"> selected</c:if>>禁用</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-4 text-right">商品简介</label>
			<div class="col-xs-8">
				<textarea name="note" id="note" class="form-control" rows="3">${userItem.note}</textarea>
			</div>
		</div>
		<input type="hidden" name="itemId" id="itemId" value="${userItem.itemId }" />
		<input type="hidden" name="userId" id="userId" value="${userItem.userId }" />
		<input type="hidden" name="itemImgPath" id="itemImgPath" value="${userItem.itemImgPath}" />
		
		<div class="form-actions">
		    <button type="submit" data-theme="b" class="btn btn-primary"><qc:message code="system.common.btn.submit"/></button>
		</div>
		<div class="form-actions">
		    <a data-role="button" href="<%= BaseController.getCmdUrl("UserItem", "userItemList") %>" class="btn btn-default" id="btnBack"><qc:message code="system.common.btn.back"/></a>
		</div>
	</form>
</div>