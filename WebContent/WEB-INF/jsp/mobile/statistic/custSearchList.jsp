<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">客户列表</h3>
</div>	

<div role="main" class="ui-content">	
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
		
		<form id="custSearchForm" name="custSearchForm" onsubmit="reloadGrid();return false;">
			<div class="row">
				<div class="text-left col-md-6">
					<qc:htmlSelect items="${custTypeList}" itemValue="custTypeId" itemLabel="custTypeName" selValue='${sc.custTypeId}'
						customAttr="data-theme='a'" isEmpty="true" emptyLabel="hostcust.cust.selectcustomer" name="custTypeId"/>  
				</div>
				<div class="text-right col-md-6">
					<button type="button" class="btn btn-primary" data-theme="b" data-icon="search" onclick="reloadGrid();"><qc:message code="system.common.btn.search"/></button>
				</div>
			</div>
		</form>
	</div>

	<table id="grid"></table>
	<div id="pager"></div>

</div>	
