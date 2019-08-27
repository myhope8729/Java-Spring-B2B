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

<div data-role="header"  data-theme="b" >
    <h3 class="page_title">业务资料</h3>
</div>
	
<div role="main" class="ui-content">
	<form class="admin-form" id="bizDataForm" name="bizDataForm" role="form" action="<%= BaseController.getCmdUrl("BizData", "saveBizData") %>" method="POST"> 
	
		<div class="solo_block_37">
			<div class="solo-block-a">
				<table id="grid"></table>	
			</div>				
			
			<div class="solo-block-b">
				<div class="table-responsive mgminus11">	
					<table id="gridContent"></table>
				</div>
				<input type="hidden" id="codeId" name="codeId" />
				
					<div class="custom_block_64">
						<div class="ui-block-6">
							<button type="button"  data-mini="true"  data-theme="b" data-icon="check" onclick="saveBizData();"><qc:message code="system.common.btn.save"/></button>
						</div>
						<div class="ui-block-4">
							<button type="button" data-mini="true"  data-theme="a" data-icon="delete" onclick="deleteBizData();"><qc:message code="system.common.btn.delete" /></button>
						</div>	
					</div>
			</div>
		</div>
				
	</form>
</div>
