<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var messages = {
		deleteMsg : "<qc:message code='hostcust.supply.deleteConfirm' />",
		noFileMsg : "<qc:message code='info.attachment.required' />"
	};
</script>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
	<a data-role="button" data-icon="back" data-iconpos="left" data-theme="b" data-inline="true" class="ui-btn-right" href="<%= BaseController.getCmdUrl("Info", "infoList") %>"><qc:message code="system.common.btn.back"/></a>   
</div>	

<div role="main" class="ui-content mgt10">
	
	<form id="infoAttachmentFrm" class="searchForm">
		<input id="billId" name="billId" value="${sc.billId}" type="hidden" />
		<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
	</form>
	
	<form class="admin-form alignC"  action="<%= BaseController.getCmdUrl("Info", "saveInfoAttachment") %>" id="infoAttachmentUploadForm" method="post" enctype="multipart/form-data">	
		<input id="billId" name="billId" value="${sc.billId}" type="hidden" />
		<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
		<div class="action_bar row">
			<div class="col-sm-12">
				<input type="file" name="attachment" id="attachment" />
			</div>
			<div class="col-sm-12">
				<button type="button" data-icon="arrow-u" data-iconpos="left" class="btn btn-orange" data-inline="true" data-mini="true" id="btnSubmit">&nbsp;&nbsp;&nbsp;<qc:message code="system.btn.upload" />&nbsp;&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
		
	<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Info", "infoAttachmentGridAjax") %>" searchForm="#infoAttachmentFrm"></table>
	<div id="gridpager"></div>
	

</div>
