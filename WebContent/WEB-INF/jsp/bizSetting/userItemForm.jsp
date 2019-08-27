<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="java.util.List"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemPropertyModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
var minWidth = "${minWidth}";
var minHeight = "${minHeight}";
</script>
<form name="departmentListForm" id="departmentListForm" onsubmit="reloadGrid();return false;"></form>

<div class="admin_body">
	<h3 class="page_title">修改商品资料</h3>
	
	<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("UserItem", "saveUserItem") %>" id="userItemForm" method="post" enctype="multipart/form-data">
	<% 
		List<UserItemPropertyModel> fieldList = (List<UserItemPropertyModel>)request.getAttribute("fieldList");
		UserItemModel userItem = (UserItemModel)request.getAttribute("userItem"); 
		for (int i=0; i<fieldList.size(); i++){%>
		<div class="form-group <% if ("PT0001".equals(fieldList.get(i).getPropertyTypeCd())) { %>  <qc:errors items="${formErrors}" path="<%=fieldList.get(i).getPropertyName() %>" type="errorCls" /> <% } %> ">
			<label class="col-md-4 control-label">
				<% if ("PT0002".equals(fieldList.get(i).getPropertyTypeCd())) {%>
				<span class="required">*</span>
				<% } %>
				<%=fieldList.get(i).getPropertyDesc() %>
			</label>
		 	<div class="col-md-4">
		 		<% if (!"PT0002".equals(fieldList.get(i).getPropertyTypeCd()) && !"PT0005".equals(fieldList.get(i).getPropertyTypeCd()) && !"".equals(fieldList.get(i).getPropertyTypeCd())) {%>
		 			<label class="mgt10"><% if ("".equals(userItem.get(fieldList.get(i).getPropertyName()))) { %>无<% }else{ %><%=userItem.get(fieldList.get(i).getPropertyName()) %><% } %></label>
		 			<input type="hidden" name="<%=fieldList.get(i).getPropertyName() %>" value="<%=userItem.get(fieldList.get(i).getPropertyName()) %>"/>
		 		<% }else{ %>
		 		<input <% if ("PT0005".equals(fieldList.get(i).getPropertyTypeCd())) {%> type="number" <%} else{%> type="input" <%} %>
		 			name="<%=fieldList.get(i).getPropertyName() %>" 
		 			value="<%=userItem.get(fieldList.get(i).getPropertyName()) %>" 
		 			class="form-control"
		 			<% if ("PT0002".equals(fieldList.get(i).getPropertyTypeCd())) {%> validate="required: true, number:true" <%} %>
		 		/>
		 		<% } %>
		 	</div>
		</div>
	<% } %>
	<!-- 
	<c:if test="${isThirdVendor}">
		<div class="form-group">
			<label class="col-md-4 control-label">供应商</label>
			<div class="col-md-4">
		 		<select name="hostUserId" id="hostUserId" class="form-control" >
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:if test="${isVendorGroup}">
					<option value="vendorGroup">分组供货</option>
					</c:if>
					<option value="${loginUser.userId}" <c:if test="${userItem.vendorId == loginUser.userId}"> selected</c:if>>${loginUser.userName}</option>
					<c:forEach var="host" items="${hostList}">
					<option value="${host.hostUserId}" <c:if test="${userItem.vendorId == host.hostUserId}"> selected</c:if>>${host.hostUserName}</option>
					</c:forEach>
				</select>
		 	</div>
		</div>
	</c:if>
	-->
		<div class="form-group">
			<label class="col-md-4 control-label">商品图片</label>
			<div class="col-md-4">
				<div class="fileinput crop 
					<c:if test="${not empty userItem.itemImgPath}">fileinput-exists</c:if>
					<c:if test="${empty userItem.itemImgPath}">fileinput-new</c:if>" data-provides="fileinput">
					<div class="fileinput-preview thumbnail">
						<c:if test="${not empty userItem.itemImgPath}">
						<img src='/uploaded/useritem/${userItem.itemImgPath}' id="crop_target" style="width:300px;height:auto;max-height:initial;"/>
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
				<label class="control-label"><qc:message code="upload.filePixelSize.minimum" param="${minWidth},${minHeight}" /></label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label"><span class="required">*</span>出货仓库</label>
			<div class="col-md-4">
		 		<select name="storeId" id="storeId" class="form-control" validate="required: true" message="出货仓库">
					<option value=""><qc:message code="sc.please.select" /></option>
					<c:forEach var="store" items="${storeList}">
					<option value="${store.storeId}" <c:if test="${userItem.storeId == store.storeId}"> selected</c:if>>${store.storeName}</option>
					</c:forEach>
				</select>
		 	</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">按库存下单</label>
			<div class="col-md-4">
		 		<select name="stockMark" id="stockMark" class="form-control" >
		 			<option value=""><qc:message code="sc.please.select" /></option>
					<option value="Y" <c:if test="${userItem.stockMark == 'Y'}"> selected</c:if>>是</option>
					<option value="N" <c:if test="${userItem.stockMark == 'N'}"> selected</c:if>>否</option>
				</select>
		 	</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">状态</label>
			<div class="col-md-4">
		 		<select name="state" id="state" class="form-control" >
					<option value="ST0001" <c:if test="${userItem.state == 'ST0001'}"> selected</c:if>>正常</option>
					<option value="ST0002" <c:if test="${userItem.state == 'ST0002'}"> selected</c:if>>禁用</option>
				</select>
		 	</div>
		</div>
		<div class="form-group">
			<label class="col-md-4 control-label">商品简介</label>
			<div class="col-md-4">
				<textarea name="note" id="note" class="form-control" rows="3">${userItem.note}</textarea>
		 	</div>
		</div>
		
		<input type="hidden" name="itemId" id="itemId" value="${userItem.itemId }" />
		<input type="hidden" name="userId" id="userId" value="${userItem.userId }" />
		<input type="hidden" name="itemImgPath" id="itemImgPath" value="${userItem.itemImgPath}" />
		
		<input type="hidden" name="cropX" id="cropX" value="" />
		<input type="hidden" name="cropY" id="cropY" value="" />
		<input type="hidden" name="cropW" id="cropW" value="" />
		<input type="hidden" name="cropH" id="cropH" value="" />
		<div class="form-group">
		    <div class="col-md-offset-4 col-md-4">
		      <button type="submit" class="btn btn-primary"><qc:message code="system.common.btn.submit"/></button>
		      <a href="<%= BaseController.getCmdUrl("UserItem", "userItemList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back"/></a>
		 	</div>
		</div> 
	</form>
</div>