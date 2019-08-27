<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">商品管理</h3>
</div>
	
<div role="main" class="ui-content">	
	<form id="itemSearchForm" name="itemSearchForm" class="form-horizontal" onsubmit="reloadGrid();return false;">
		<div class="action_bar">
			<div class="form-group">
				<label for="category" class="control-label col-xs-4 text-right">商品分类</label>
				<div class="col-xs-8">
					<select name="category" id="category">
						<option value="">===分类===</option>
						<c:forEach var="category" items="${categoryList}">
							<option value="${category}" <c:if test="${sc.category == category}">selected</c:if>>
								<c:if test='${category == "-1"}'>未分类</c:if>
								<c:if test='${category != "-1"}'>${category}</c:if>
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="chelp" class="control-label col-xs-4 text-right">关键字</label>
				<div class="col-xs-8">
					<input type="text" placeholder="关键字" data-clear-btn="true" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
				</div>
			</div>
			<div class="form-group form-actions">
				<div class="col-xs-5 col-xs-offset-2">
					<button type="button" data-mini="true" data-theme="b" id="search" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
				</div>
				<div class="col-xs-5">
					<a data-theme="b" data-mini="true" data-role="button"  data-inline="true" class="btn btn-primary btn-fullwidth" data-iconpos="left"  href="<%= BaseController.getCmdUrl("Item", "itemForm") %>"><qc:message code="system.common.btn.new"/></a>
				</div>
			</div>
		</div>
	</form>
	<div class="col-xs-12 form-group">
		<div class="row">
			<table id="grid"></table>
			<div id="gridpager"></div>
		</div>
	</div>
</div>