<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
	var messages = {
			addItemConfirm : "<qc:message code='useritem.item.addconfirm' />"
		};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">用户商品目录</h3>
</div>
	
<div role="main" class="ui-content">	
	<form id="searchForm" name="searchForm" class="form-horizontal" method="post" action="UserItem.do?cmd=downloadUserItemList">
		<div class="action-bar">
			<c:if test="${not empty categoryList }">
			<div class="form-group">
				<label for="userNo" class="control-label col-xs-4 text-right">商品分类</label>
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
			</c:if>
			<div class="form-group">	
				<label for="scValidYn" class="control-label col-xs-4 text-right">状态</label>	
				<div class="col-xs-8">							
			 		<qc:codeList var="states" codeGroup="ST0000" />
					<select id="state" name="state">
						<option value="">==状态==</option>
						<c:forEach var="state" items="${states}">
						<option value="${state.codeId}">${state.codeName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="chelp" class="control-label col-xs-4 text-right">关键字</label>
				<div class="col-xs-8">
					<input type="text" class="form-control" data-clear-btn="true" name="chelp" id="chelp"/>
				</div>
			</div>		
			<div class="form-group form-actions">
				<div class="col-xs-5 col-xs-offset-2">
					<button type="button" data-mini="true" data-theme="b" id="search" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
				</div>
				<div class="col-xs-5">
					<a data-theme="b" data-mini="true" data-role="button"  data-inline="true" class="btn btn-primary btn-fullwidth" data-iconpos="left"  href="javascript:addItem();"><qc:message code="system.common.btn.new"/></a>
				</div>
			</div>
		</div>
		
		<!-- div class="col-xs-12 text-right" >
			<div class="row">
				<div data-role="controlgroup" data-type="horizontal" data-mini="true">
					<a data-theme="a" data-role="button" data-icon="plus" data-inline="true" data-iconpos="left" href="UserItem.do?cmd=userItemPropertyList"><qc:message code="system.common.btn.setting"/></a>
					<a data-theme="a" data-role="button" data-icon="plus" data-inline="true" data-iconpos="left" href="javascript:addItem();"><qc:message code="system.common.btn.new"/></a>
					<a data-theme="a" data-role="button" data-icon="plus" data-inline="true" data-iconpos="left" href="UserItem.do?cmd=groupItemList"><qc:message code="system.common.btn.product.group"/></a>
				</div>
			</div>
		</div-->
	</form>
	<div class="col-xs-12 form-group">
		<div class="row">
			<table id="grid"></table>
			<div id="gridpager"></div>
		</div>
	</div>
</div>