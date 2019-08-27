<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var obj = ${SysDetailData};
	var selMsg = "<qc:message code='sc.please.select.s' />";
	var invalidNumber="<qc:message code='system.invalid.number' />";
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">业务配置</h3>
</div>
	
<div role="main" class="ui-content">
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="false" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
		
		<form id="bizSettingSearchForm" name="bizSettingSearchForm" class="form-inline" onsubmit="reloadGrid();return false;">
			<div class="row">
				<div class="col-md-12">
					<qc:codeList var="bizTypeList" codeGroup="DK0000" />
					<qc:htmlSelect items="${bizTypeList}" itemValue="codeId" itemLabel="codeName"
						isEmpty="true" emptyLabel='sc.please.biztype.select' id="sysKindCd" name="sysKindCd" selValue='${sc.sysKindCd}' />
				</div>
				<div class="col-md-12">
					<input type="text" placeholder="查找配置项" data-clear-btn="true" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
				</div>
			</div>
			
			<div class="text-right custom_block_73">
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<button type="button" class="btn btn-primary" onclick="reloadGrid();"  data-theme="b" data-icon="search" ><qc:message code="system.common.btn.search"/></button>
					</div>
					<div class="ui-block-b">
						<button type="button" class="btnReset btn btn-primary" id="reset"><qc:message code="system.common.btn.reset" /></button>
					</div>
				</div>						
			</div>				
		</form>
	</div>
		
	<div class="col-md-12 mgb10">
			<button type="submit" data-theme="a" data-mini="true"  data-icon="check" class="btn btn-orange mg0-auto w150" onclick="saveBizSetting();"><qc:message code="system.common.btn.save" /></button>
	</div>		
		
	<form class="admin-form" id="bizSettingForm" name="bizSettingForm" role="form" action="<%= BaseController.getCmdUrl("BizSetting", "saveBizSetting") %>" method="POST"> 
		<table id="grid"></table>
		<input type="hidden" id="userData" name="userData" value="" />
	</form>
	
</div>
