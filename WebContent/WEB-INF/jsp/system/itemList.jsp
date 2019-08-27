<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin_body">
	<h3 class="page_title">商品目录</h3>
	<form id="itemSearchForm" name="itemSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
		<div class="action_bar col-md-6">
			<select name="category" id="category" class="form-control">
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
		<div class="action_bar col-md-6 text-right">
			<a class="btn btn-primary" href="<%= BaseController.getCmdUrl("Item", "itemForm") %>"> <qc:message code="system.common.btn.new" /> </a>
		</div>
	</form>
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>