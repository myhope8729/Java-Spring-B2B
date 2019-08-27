<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
	var messages = {
		duplicateMsg : "<qc:message code='useritem.property.duplicateprice' />",
		duplicateField : "<qc:message code='useritem.property.duplicated' />",
		noRequiredField : "<qc:message code='useritem.property.nonameommitted' />"
	};
</script>
<div class="admin_body">
	<h3 class="page_title">商品资料设置</h3>
	<div class="action_bar text-right">
		<button type="button" class="btn btn-primary mgl10" onclick="saveItemPropertyList();"><qc:message code="system.common.btn.save" /></button>
		<a type="button" class="btn btn-default mgl10" href="UserItem.do?cmd=userItemList"><qc:message code="system.common.btn.back" /></a>
		<input type="hidden" id="selectedId" value=""/>
	</div>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
	<!-- list end -->
	<form name="userItemPropertyFrm" id="userItemPropertyFrm" method="post" action="UserItem.do?cmd=saveUserItemProperty">
		<textarea name="userData" id="userData" class="hidden"></textarea>
	</form>
</div>