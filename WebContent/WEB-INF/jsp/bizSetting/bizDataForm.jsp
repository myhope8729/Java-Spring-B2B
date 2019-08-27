<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var selRowMsg = "<qc:message code='system.common.empty.delete' />";
	var delSuccessMsg = "<qc:message code='system.common.success.delete' />";
	var emptyLabel = "<qc:message code='sc.please.select.s' />";
	var numberRequired = "<qc:message code='system.invalid.number' />";
	var required = "<qc:message code='system.common.valid.error.required' />";
</script>

<h3 class="page_title">业务资料</h3>
<div class="admin_body">
	<form class="form-horizontal admin-form" id="bizDataForm" name="bizDataForm" role="form" action="<%= BaseController.getCmdUrl("BizData", "saveBizData") %>" method="POST"> 
		<div class="row">
			<div class="col-md-3">
				<table id="grid">
				</table>
			</div>
			
			<div class="col-md-9">
				<table id="gridContent">
				</table>
				<input type="hidden" id="codeId" name="codeId" />
				
				<div class="form-group">
					<div class="col-md-12">
						<button type="button" class="btn btn-primary navbar-btn" onclick="saveBizData();"><qc:message code="system.common.btn.save" /></button>
						<button type="button" class="btn btn-default navbar-btn mgl10" onclick="deleteBizData();"><qc:message code="system.common.btn.delete" /></button>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
