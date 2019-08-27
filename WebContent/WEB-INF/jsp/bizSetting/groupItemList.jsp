<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
</script>
<div class="admin_body">
	<h3 class="page_title">分组商品目录</h3>
		<div class="action_bar row">
			<form name="groupItemFrm" class="form-inline" id="groupItemFrm" onsubmit="reloadGrid();return false;">
				<div class="text-left col-lg-4 col-sm-2">
					<select name="groupId" id="groupId" class="form-control">
						<c:forEach var="group" items="${groupList}">
							<option value="${group.seqNo}" <c:if test="${group.seqNo == sc.groupId }">selected</c:if>>${group.c1}</option>
						</c:forEach>
					</select>
				</div>
				<div class="text-center col-lg-4 col-md-5 col-sm-4">
					<label for="groupChelp" class="control-label visible-md-inline">关键字</label>
					<input type="text" class="form-control mgl10" name="groupChelp" id="groupChelp" value="${sc.groupChelp}"/>
					<button type="button" id="search" class="btn btn-primary mgl10"><qc:message code="system.common.btn.search" /></button>
				</div>
			</form>
			<form name="userItemSFrm" class="form-inline" id="userItemSFrm" method="post" action="UserItem.do?cmd=searchUserItemList">
				<div class="text-right col-lg-4 col-md-5 col-sm-6">
					<input type="hidden" name="group" id="group" value=""/>
					<input type="text" class="form-control" name="chelp" value="${sc.chelp}" />
					<button type="submit" class="btn btn-primary mgl10"><qc:message code="system.common.btn.product.add" /></button>
					<a type="button" class="btn btn-default mgl10" href="UserItem.do?cmd=userItemList"><qc:message code="system.common.btn.back" /></a>
				</div>
			</form>
		</div>
	
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
	<!-- list end -->
</div>