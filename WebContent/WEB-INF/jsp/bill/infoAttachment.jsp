<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var messages = {
		deleteMsg : "<qc:message code='hostcust.supply.deleteConfirm' />",
		noFileMsg : "<qc:message code='info.attachment.required' />"
	};
</script>
<style>
	#infoListFrm .form-group {
		margin-bottom: 15px;
		margin-right: 15px;
	}
	#infoListFrm .form-group .control-label{margin-right: 15px;}
	
</style>
<div class="admin_body">
	
	<h3 class="page_title">${pageTitle}</h3>
	
	<form id="infoAttachmentFrm" class="searchForm form-inline">
		<input id="billId" name="billId" value="${sc.billId}" type="hidden" />
		<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
	</form>
	
	<div class="clear">
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Info", "infoAttachmentGridAjax") %>" searchForm="#infoAttachmentFrm"></table>
		<div id="gridpager"></div>
	</div>
	
	<form class="form-inline admin-form alignC" role="form"  action="<%= BaseController.getCmdUrl("Info", "saveInfoAttachment") %>" id="infoAttachmentUploadForm" method="post" enctype="multipart/form-data">	
		<input id="billId" name="billId" value="${sc.billId}" type="hidden" />
		<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
		<div class="form-group">
			<input type="file" name="attachment" id="attachment" />
		</div>
		<button type="button" class="btn btn-primary" id="btnSubmit"> <qc:message code="system.btn.upload" /></button>
		<a href="javascript:window.close();" class="btn btn-default mgl20" id="btnBack"><qc:message code="system.common.btn.close"/></a>
	</form>
</div>
