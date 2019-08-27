<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript">
	var messages = {
		deleteMsg : "<qc:message code='hostcust.supply.deleteConfirm' />"
	};
</script>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">${pageTitle}</h3>
    <a data-theme="b" data-role="button" data-icon="plus" data-inline="true" class="ui-btn-right" data-iconpos="left" href="<%= BaseController.getCmdUrl("Info", "infoForm") %>"> 
    	<qc:message code="system.common.btn.new" /> 
    </a>    
</div>	

<div role="main" class="ui-content">
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
	
		<form id="infoListFrm" class="searchForm">
			<input id="userId" name="userId" value="${sc.userId}" type="hidden" />
			<div class="row">
				<div class="col-md-6">
					<label class="control-label">类别</label>
				 	<qc:htmlSelect items="${fileTypeList}" itemValue="c1" itemLabel="c1" selValue='${sc.itype}'
						isEmpty="true" emptyLabel='sc.please.select.s' id="itype" name="itype" customAttr='' />
				</div>
				<div class="col-md-6">
					<label class="control-label">标题</label>
				 	<input type="text"  class="form-control" name="info" id="info" value="${sc.info}" />
				</div>
				<div class="col-md-6">
					<label class="control-label">制单日期</label>
					<div class="input-group input-large date-picker input-daterange" data-date="2012-11-10" data-date-format="yyyy-mm-dd">
						<input type="text" name="createDateFrom" value="${sc.createDateFrom}" >
						<span class="input-group-addon"> ~ </span>
						<input type="text" name="createDateTo" value="${sc.createDateTo}" >
					</div>
				</div>
			</div>
			<div class="text-right custom_block_73">
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<button type="submit" class="btn btn-primary"  data-theme="b" data-icon="search" ><qc:message code="system.common.btn.search"/></button>
					</div>
					<div class="ui-block-b">
							<button type="button" class="btnReset btn btn-primary" id="reset"><qc:message code="system.common.btn.reset" /></button>
					</div>
				</div>						
			</div>			
		</form>
	</div>
	
	<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("Info", "infoGridAjax") %>" searchForm="#infoListFrm"></table>
	<div id="gridpager"></div>
	
</div>
