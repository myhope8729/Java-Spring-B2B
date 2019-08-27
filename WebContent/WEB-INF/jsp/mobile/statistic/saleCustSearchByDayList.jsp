<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var BILL_TYPE_DING 		= '<%=Constants.CONST_BILL_TYPE_DING%>';
</script>

<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">客户销售明细</h3>
    <a href="<%= BaseController.getCmdUrl("SaleCustSearch", "saleCustSearchList") %>" data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>	

<div role="main" class="ui-content">	
	<!-- search section -->
	<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
		<h3><qc:message code="system.common.btn.search" /></h3>	
	
		<form id="searchForm" onsubmit="reloadGrid();return false;"  class="form-inline">
			<div class="action_bar row">		
					<div class="text-left col-xs-12">
						<input type="text" placeholder="单据编号" data-clear-btn="true" class="form-control" id="searchString3" name="searchString3" value="${sc.searchString3}"/>
					</div>
     		</div>

			<div class="action_bar row">
				<div class="text-right col-md-6">
					<fieldset class="ui-grid-a">
						<div class="ui-block-a">
							<button type="submit" class="btn btn-primary"  data-theme="b" data-icon="search" ><qc:message code="system.common.btn.search"/></button>
						</div>
						<div class="ui-block-b">
							<div style="margin-left:5px;">
								<a href="<%= BaseController.getCmdUrl("SaleCustSearch", "saleCustSearchList") %>"  class="btn btn-primary" data-role="button" data-icon="back"  id="btnBack"><qc:message code="system.common.btn.back" /></a>
							</div>
						</div>					
					</fieldset>						
				</div>	
			</div>		
			<div class="text-center col-md-6">
				<label><b>客户:</b> ${sc.searchString2} </label>
			</div>
			<qc:printBean value="${sc}" />
		</form>
	</div>		
		
	<div class="table-responsive">			
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("SaleCustSearch", "saleCustSearchByDayGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
	</div>
</div>