<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">订货方目录</h3>
</div>
	
<div role="main" class="ui-content">
	<!-- search section -->
		<form id="custSettingSearchForm" name="custSettingSearchForm" class="form-horizontal" onsubmit="reloadGrid();return false;">
			<div class="action_bar">
				<div class="form-group">
					<label class="control-label col-xs-4 text-right">类别</label>
					<div class="col-xs-8">
						<qc:htmlSelect items="${custTypeList}" itemValue="custTypeId" itemLabel="custTypeName"
							isEmpty="true" emptyLabel="sc.please.select.s" name="custTypeId" selValue='${sc.custTypeId}'/>
					</div>
				</div>
				<div class="form-group">		
					<label class="control-label col-xs-4 text-right">业务员</label>
					<div class="col-xs-8">								
						<qc:htmlSelect items="${empList}" itemValue="empId" itemLabel="empName"
							isEmpty="true" emptyLabel="sc.please.select.s" name="empId" selValue='${sc.empId}'/>
					</div>
				</div>
				<div class="form-group">
					<label for="chelp" class="control-label col-xs-4 text-right">关键字</label>
					<div class="col-xs-8">
						<input type="text" placeholder="关键字" data-clear-btn="true" class="form-control" id="chelp" name="chelp" value="${sc.chelp}"/>
					</div>
				</div>
			</div>
			
			<div class="form-group form-actions">
				<div class="col-xs-5 col-xs-offset-2">
					<button type="button" data-mini="true" data-theme="b" id="search" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
				</div>
				<div class="col-xs-5">
					<a data-theme="a" data-mini="true" data-role="button"  class="btn btn-primary btnReset" data-iconpos="left"  href="javascript:addItem();"><qc:message code="system.common.btn.reset"/></a>
				</div>
			</div>
		</form>

	<table id="grid"></table>
	<div id="gridpager"></div>
	
</div>
