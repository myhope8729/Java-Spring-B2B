<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
	var messages = {
		noItemMessage : "<qc:message code='useritem.group.noselectitem' />"
	};
</script>
<div class="admin_body">
	<h3 class="page_title">添加分组商品</h3>
		<div class="action_bar row">
			<form name="searchItemFrm" class="form-inline" id="searchItemFrm" onsubmit="reloadGrid();return false;">
				<div class="text-left col-md-2">
					<label for="groupName" class="control-label">商品分组:&nbsp;</label>${groupName}
				</div>
				<div class="text-center col-md-7">
					<input type="hidden" name="groupId" value="${groupId}" />
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
					<input type="text" class="form-control mgl10" name="chelp" id="chelp" value="${sc.chelp}"/>
					<button type="button" id="search" class="btn btn-primary mgl10"><qc:message code="system.common.btn.search"/></button>
				</div>
			</form>
			<form name="saveGroupItemFrm" class="form-inline" id="saveGroupItemFrm" method="post" onsubmit="return checkSelection();" action="UserItem.do?cmd=saveGroupItems">
				<div class="text-right col-md-3">
					<input type="hidden" name="selectedIds" id="selectedIds" value=""/>
					<button type="submit" class="btn btn-primary mgl10"><qc:message code="system.common.btn.save"/></button>
					<a type="button" class="btn btn-default mgl10" href="UserItem.do?cmd=groupItemList"><qc:message code="system.common.btn.back"/></a>
				</div>
			</form>
		</div>
	
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
	<!-- list end -->
</div>