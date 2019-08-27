<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">添加分组商品</h3>
    <a data-role="button" data-icon="back" data-mini="true" class="ui-btn-right" href="UserItem.do?cmd=groupItemList"  id="btnBack"><qc:message code="system.common.btn.back"/></a>
</div>

<div role="main" class="ui-content">
	<form name="searchItemFrm" class="form-inline" id="searchItemFrm" onsubmit="reloadGrid();return false;">
		<div class="alignC col-xs-12">
			<label for="groupName" class="control-label">商品分组:${groupName}</label>
		</div>
		<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="false" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
			<h3><qc:message code="system.common.btn.search" /></h3>	
			<div class="action-bar row">
				<div class="text-left col-xs-12">	
					<input type="hidden" name="groupId" value="${groupId}" />
					<c:if test="${not empty categoryList }">
					<select name="category" id="category">
						<option value="">===分类===</option>
						<c:forEach var="category" items="${categoryList}">
							<option value="${category}" <c:if test="${sc.category == category}">selected</c:if>>
								<c:if test='${category == "-1"}'>未分类</c:if>
								<c:if test='${category != "-1"}'>${category}</c:if>
							</option>
						</c:forEach>
					</select>
					</c:if>
				</div>
				<div class="text-left col-xs-12">
					<label for="chelp" class="control-label">关键字</label>
					<input type="text" data-place="关键字" placeholder="关键字" data-clear-btn="true" class="form-control mgl10" name="chelp" id="chelp" value="${sc.chelp}"/>
				</div>				
			</div>	
			
			<div class="text-left custom_block_73 mgt5">
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<button type="button" data-mini="true"  data-theme="b" data-icon="search" id="search" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
					</div>
					<div class="ui-block-b">
						<button type="button" data-mini="true"  class="btnReset" id="reset"><qc:message code="system.common.btn.reset" /></button>
					</div>
				</div>
			</div>					
		</div>
	</form>
	<form name="saveGroupItemFrm" id="saveGroupItemFrm" method="post" onsubmit="return checkSelection();" action="UserItem.do?cmd=saveGroupItems">
		<div class="col-xs-12 mgb10" >
			<button class="btn btn-orange mg0-auto w150" type="submit" data-theme="a" data-mini="true"  data-icon="plus" data-iconpos="left"><qc:message code="system.common.btn.save" /></button>
		</div>
		<input type="hidden" id="selectedIds" name="selectedIds" value=""/>
	</form>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
</div>