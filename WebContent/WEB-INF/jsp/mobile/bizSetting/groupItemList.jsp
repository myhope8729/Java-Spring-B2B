<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">分组商品目录</h3>
    <a data-role="button" data-icon="back" data-mini="true" class="ui-btn-right" href="<%= BaseController.getCmdUrl("UserItem", "userItemList") %>"  id="btnBack"><qc:message code="system.common.btn.back"/></a>
</div>

<div role="main" class="ui-content">	
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
		<form name="groupItemFrm" class="form-inline" id="groupItemFrm" onsubmit="reloadGrid();return false;">
			<div class="action-bar row">
				<div class="text-left col-xs-12">	
					<select name="groupId" id="groupId">
						<c:forEach var="group" items="${groupList}">
						<option value="${group.seqNo}" <c:if test="${group.seqNo == groupId }">selected</c:if>>${group.c1}</option>
						</c:forEach>
					</select>
				</div>
				<div class="text-left col-xs-12">	
					<label for="groupChelp" class="control-label">关键字</label>
					<input type="text" class="form-control mgl10" name="groupChelp" id="groupChelp" value="${sc.groupChelp}"/>
				</div>
			</div>	
			
			<div class="text-left custom_block_73">
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<button type="button" data-mini="true"  data-theme="b" data-icon="search" id="search" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
					</div>
					<div class="ui-block-b">
						<button type="button" data-mini="true"  class="btnReset" id="reset"><qc:message code="system.common.btn.reset" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<form name="userItemSFrm" class="form-inline" id="userItemSFrm" method="post" action="UserItem.do?cmd=searchUserItemListForMobile">
			<input type="hidden" name="group" id="group" value=""/>
			<div class="action_bar custom_block_73">
				<div class="ui-grid-a">
					<div class="ui-block-a">				
						<input type="text" name="chelp" value="${sc.chelp}" />
					</div>
					<div class="ui-block-b">					
						<button type="submit" data-theme="a" class="mg-no" data-mini="true" data-icon="plus" data-iconpos="left"><qc:message code="system.common.btn.product.add" /></button>
					</div>
				</div>
			</div>
	</form>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>