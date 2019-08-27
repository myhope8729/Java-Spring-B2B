<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var obj = '';
	var selMsg = "<qc:message code='sc.please.select' />";
	var reallySaveMsg = "<qc:message code='system.alert.modify' />";
	var noSaveData = "<qc:message code='sc.no.select.rows' />";
	var noSelectUpper = "<qc:message code='system.code.upper.noselect' />";
</script>

<!-- header section -->	
<div data-role="header"  data-theme="b" >
    <h3 class="page_title">编码资料</h3>
</div>

<div role="main" class="ui-content">
	<!-- search section -->
		<form id="searchForm" name="searchForm" class="form-horizontal" onsubmit="reloadGrid();return false;">
			<div class="action-bar">
				<div class="form-group">
				  	<label for="scUpperCode" class="control-label col-xs-4 text-right">编码类型</label> 
				  	<div class="col-xs-8">
						<qc:htmlSelect items="${upperCodeList}" itemValue="codeId" itemLabel="codeName"
							isEmpty="true" emptyLabel='sc.please.select.s' customAttr="data-theme='a'" id="scUpperCode" name="scUpperCode" />
					</div>
				</div>
				<div class="form-group">
					<label for="scValidYn" class="control-label col-xs-4 text-right">状态</label>	
					<div class="col-xs-8">						
				 		<select name="scValidYn" id="scValidYn" data-theme="a" >
				 		    <option value="" <c:if test="${sc.scValidYn == ''}"> selected</c:if>><qc:message code="sc.please.select.s" /></option>
							<option value="Y" <c:if test="${sc.scValidYn == 'Y'}"> selected</c:if>>正常</option>
							<option value="N" <c:if test="${sc.scValidYn == 'N'}"> selected</c:if>>禁用</option>
						</select>
					</div>
				</div>				
				<div class="form-group">
					<label for="scCodeName" class="control-label col-xs-4 text-right">编码名称</label>
					<div class="col-xs-8">
						<input type="text" class="form-control" placeholder="编码名称" data-clear-btn="true" id="scCodeName" name="scCodeName"/>
					</div>
				</div>
			</div>	
			
			<div class="form-group form-actions">
				<div class="col-xs-5 col-xs-offset-2">
					<button type="submit" data-mini="true" data-theme="b" id="search" class="btn btn-primary" onclick="reloadGrid();"><qc:message code="system.common.btn.search" /></button>
				</div>
				<div class="col-xs-5">
					<a data-theme="a" data-mini="true" data-role="button"  class="btn btn-primary btnReset" data-iconpos="left"  href="javascript:addItem();"><qc:message code="system.common.btn.reset"/></a>
				</div>
			</div>				
		</form>
	
	<!-- grid section --no pager -->
	<table id="grid"></table>
	<div id="gridpager"></div>		

</div>
