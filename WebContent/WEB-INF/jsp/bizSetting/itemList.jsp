<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var noItemMsg = "<qc:message code='useritem.item.empty' />";
</script>
	
<div class="admin_body">
	<h3 class="page_title">商品目录</h3>
	<form id="itemSearchForm" name="itemSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar col-md-8 col-md-offset-1">
			<select name="category" id="category" class="form-control col-xs-6">
				<option value="">===分类===</option>
				<c:forEach var="category" items="${categoryList}">
					<option value="${category}" <c:if test="${sc.category == category}">selected</c:if>>
						<c:if test='${category == "-1"}'>未分类</c:if>
						<c:if test='${category != "-1"}'>${category}</c:if>
					</option>
				</c:forEach>
			</select>
			<label for="chelp" class="control-label mgl20">关键字</label>
			<input type="text" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
			<button type="button" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
		</div>
		<div class="action_bar col-md-2 text-right">
			<button type="button" id="btnSave" class="btn btn-primary"><qc:message code="system.common.btn.save" /></button>
			<a type="button" class="btn btn-default mgl10" href="UserItem.do?cmd=userItemList"><qc:message code="system.common.btn.back" /></a>
		</div>
	</form>
	
	<form id="saveItemForm" name="saveItemForm" class="form-inline" method="post" onsubmit="return checkSelection();" action="UserItem.do?cmd=saveItems">
		<input type="hidden" name="selectedIds" id="selectedIds" value=""/>
	</form>
	
	<div class="clear col-md-10 col-md-offset-1">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>