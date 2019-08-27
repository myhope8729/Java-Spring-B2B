<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.BizSetting"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script>
	var userId = '${userId}';
	var printType = '${printType}';
	var BILL_TYPE_CUSTPAY  	= '<%=Constants.CONST_BILL_TYPE_CUSTPAY%>';
	var BILL_TYPE_RUKU 		= '<%=Constants.CONST_BILL_TYPE_RUKU%>';
	var BILL_TYPE_NEWS 		= '<%=Constants.CONST_BILL_TYPE_NEWS%>';
	var BILL_TYPE_DING 		= '<%=Constants.CONST_BILL_TYPE_DING%>';
	var BILL_TYPE_PRICE 	= '<%=Constants.CONST_BILL_TYPE_PRICE%>';
	var BILL_TYPE_PAYMENT 	= '<%=Constants.CONST_BILL_TYPE_PAYMENT%>';
	var BILL_TYPE_TUI 		= '<%=Constants.CONST_BILL_TYPE_TUI%>';
	var BILL_TYPE_SALE 		= '<%=Constants.CONST_BILL_TYPE_SALE%>';
	var BILL_TYPE_POS		= '<%=Constants.CONST_BILL_TYPE_POS%>';
	var BILL_TYPE_NOTICE	= '<%=Constants.CONST_BILL_TYPE_NOTICE%>';
	var BILL_TYPE_LOSS 		= '<%=Constants.CONST_BILL_TYPE_LOSS%>';
	
	var BILL_STATE_COMPLETED	= '<%=Constants.BILL_STATE_COMPLETED%>';
	
	var PRINT_PAPER_LARGE 	= '<%=BizSetting.PRINT_PAPER_LARGE%>';
	var PRINT_PAPER_SMALL 	= '<%=BizSetting.PRINT_PAPER_SMALL%>';
	
</script>
	
<!-- header section -->	
<div data-role="header" data-theme="b" >
    <h3 class="page_title">业务单据查询</h3>
</div>	

<div role="main" class="ui-content">
<!-- search section -->
<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
	<h3><qc:message code="system.common.btn.search" /></h3>	
		
	<form id="searchForm" onsubmit="reloadGrid();return false;" class="form-inline">
		<div class="action_bar row">
			<div class="text-left col-md-6">
				<qc:codeList var="billType" codeGroup="DT0000" exceptCode="DT0001,DT0003,DT0009,DT0010,DT0011"/>
				<qc:htmlSelect items="${billType}" itemValue="codeId" itemLabel="codeName" selValue='${sc.billType}'
					isEmpty="true"  emptyLabel="billproc.billtype.select" customAttr="data-theme='a'" id="billType" name="billType"/>
			</div>
			
			<div class="mgt10 col-md-6">
				<fieldset class="ui-grid-a">
					<div class="ui-block-a">
						<input type="text" class="form-control"  placeholder="单据编号" data-clear-btn="true" id="billId" name="billId" value="${sc.billId}"/>
					</div>
					<div class="ui-block-b">
						<div style="margin-left:5px;">
							<input type="text" class="form-control" placeholder="订(供)货方" data-clear-btn="true" id="compName" name="compName" value="${sc.compName}"/>
						</div>
					</div>
				</fieldset>					
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
			
	<div class="table-responsive">	
		<table id="grid" ajaxUrl="<%= BaseController.getCmdUrl("BillSearch", "billSearchGridAjax") %>" searchForm="#searchForm"></table>
		<div id="gridpager"></div>
	</div>

</div>