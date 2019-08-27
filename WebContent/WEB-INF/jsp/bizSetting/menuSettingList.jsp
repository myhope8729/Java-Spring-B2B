<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="text-center">
	<h3 class="page_title">菜单设置</h3>

	<div class="display-ib">
	<div class="action_bar text-right">	
			<button type="button" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.setting.confirm"/></button>
			<button type="button" class="btn btn-default" onclick="resetOk();"><qc:message code="system.common.btn.reset" /></button>
	</div>	
		<table id="grid"></table>
	</div>
	<!-- list end -->
</div>
