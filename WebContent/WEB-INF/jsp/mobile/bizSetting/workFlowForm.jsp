<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script>
	var msgEmp = "<qc:message code='workflow.employee.select' />";
	var settingSortNo = "<qc:message code='workflow.setting.sort' />";
</script>

<c:set var="ableToInsert" value="false"/>
<c:set var="ableToUpdate" value="false"/>
<c:choose>
	<c:when test="${empty workFlowId}"><c:set var="ableToInsert" value="true"/></c:when>
	<c:otherwise><c:set var="ableToUpdate" value="true"/></c:otherwise>
</c:choose>

<div data-role="header" data-theme="b" >
   	<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增业务流程</c:when><c:otherwise>修改业务流程</c:otherwise></c:choose></h3>
    <a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowList") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="b" data-inline="true" class="ui-btn-right">Back</a>
</div>

<div role="main" class="ui-content mgt10">
	<form class="admin-form" id="workFlowForm" name="workFlowForm" role="form" action="<%= BaseController.getCmdUrl("WorkFlow", "saveWorkFlow") %>" method="POST">
		<input type="hidden" id="pagingYn" name="pagingYn" value="N" />
		<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlow.workFlowId}" />
		<input type="hidden" id="seqData" name="seqData">
		
		<div class="form-group col-md-12">
			<label class="control-label"><span class="required">*</span>业务类型</label>
			<qc:codeList var="workFlowTypeList" codeGroup="BT0000" />
			<qc:htmlSelect items="${workFlowTypeList}" itemValue="codeId" itemLabel="codeName" selValue='${workFlow.workFlowType}'
				isEmpty="true" emptyLabel='sc.please.select' name="workFlowType" customAttr='validate="required: true"' />
		</div>
		
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>序号</label>
			<input type="text" placeholder="序号" data-clear-btn="true" name="seqNo" value="${workFlow.seqNo}" class="form-control" validate="required: true, digits: true" />
		</div>
						
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>单据处理名称</label>
			<input type="text" placeholder="单据处理名称" data-clear-btn="true" name="workFlowName" value="${workFlow.workFlowName}" class="form-control" validate="required: true" />
		</div>			

		<div class="form-group col-md-12">
			<div class="col-xs-6">
				<label class="control-label"><span class="required">*</span>能否修改价格</label>
				<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
						<input type="radio" defaultValue="N" name="priceYn" id="priceN" value="N" <c:if test="${empty workFlow.priceYn or workFlow.priceYn=='N'}"> checked </c:if> />
						<label class="control-label" for="priceN">不能</label>
						<input type="radio" name="priceYn" id="priceY" value="Y" <c:if test="${workFlow.priceYn=='Y'}"> checked </c:if> />
						<label class="control-label" for="priceY">能</label>
				</fieldset>
			</div>
			<div class="col-xs-6 alignR">
				<label class="control-label"><span class="required">*</span>能否修改数量</label>
				<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
						<input type="radio" defaultValue="N" name="qtyYn" id="qtyN" value="N" <c:if test="${empty workFlow.qtyYn or workFlow.qtyYn=='N'}"> checked </c:if> />
						<label class="control-label" for="qtyN">不能</label>
						<input type="radio" name="qtyYn" id="qtyY" value="Y" <c:if test="${workFlow.qtyYn=='Y'}"> checked </c:if> />
						<label class="control-label" for="qtyY">能</label>
				</fieldset>
			</div>
		</div>		
				
		<div class="form-group col-md-12 alignC">
			<label class="control-label"><span class="required">*</span>能否修改运费</label>
			<fieldset data-role="controlgroup" data-type="horizontal" data-mini="true">
				<input type="radio" defaultValue="N" name="shipPriceYn" id="shipPriceN" value="N" <c:if test="${empty workFlow.shipPriceYn or workFlow.shipPriceYn=='N'}"> checked </c:if> />
				<label class="control-label" for="shipPriceN">不能</label>
				<input type="radio" name="shipPriceYn" id="shipPriceY" value="Y" <c:if test="${workFlow.shipPriceYn=='Y'}"> checked </c:if> />
				<label class="control-label" for="shipPriceY">能</label>
			</fieldset>
		</div>				


		<div class="form-group row col-md-12">
			<div class="col-xs-6">
				<label class="control-label"><span class="required">*</span>最小金额(元)</label>
				<input type="text" placeholder="最小金额(元)" data-clear-btn="true" name="minCost" defaultValue="0" value="<c:choose><c:when test="${empty workFlow.minCost}">0</c:when><c:otherwise>${workFlow.minCost}</c:otherwise></c:choose>" class="form-control" validate="required: true, digits: true" />
			</div>
			<div class="col-xs-6">
				<label class="control-label"><span class="required">*</span>最大金额(元)</label>
				<input type="text"  placeholder="最大金额(元)" data-clear-btn="true" name="maxCost" defaultValue="9999999999" value="<c:choose><c:when test="${empty workFlow.maxCost}">9999999999</c:when><c:otherwise>${workFlow.maxCost}</c:otherwise></c:choose>" class="form-control" validate="required: true, digits: true" />
			</div>
		</div>
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>分发订单</label>
			<select name="distributeYn" validate="required: true">
				<option value="" <c:if test="${workFlow.distributeYn==''}">selected</c:if>><qc:message code="sc.please.select" /></option>
				<option value="Y" <c:if test="${workFlow.distributeYn=='Y'}">selected</c:if>>是</option>
				<option value="N" <c:if test="${workFlow.distributeYn=='N'}">selected</c:if>>否</option>
			</select>
		</div>
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>仅处理自己供货商品</label>
			<select name="itemYn" validate="required: true">
				<option value="" <c:if test="${workFlow.itemYn==''}">selected</c:if>><qc:message code="sc.please.select" /></option>
				<option value="Y" <c:if test="${workFlow.itemYn=='Y'}">selected</c:if>>是</option>
				<option value="N" <c:if test="${workFlow.itemYn=='N'}">selected</c:if>>否</option>
			</select>
		</div>
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>分组处理</label>
			<select id="groupYn" name="groupYn" validate="required: true" onchange="showEmployerDiv(this);">
				<option value="" <c:if test="${workFlow.groupYn==''}">selected</c:if>><qc:message code="sc.please.select" /></option>
				<option value="Y" <c:if test="${workFlow.groupYn=='Y'}">selected</c:if>>是</option>
				<option value="N" <c:if test="${workFlow.groupYn=='N'}">selected</c:if>>否</option>
			</select>
		</div>
		<div class="form-group col-md-6">
			<label class="control-label"><span class="required">*</span>状态</label>
			<qc:codeList var="stateCodes" codeGroup="ST0000" />
			<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${workFlow.state}' name="state"/>
		</div>			
		<div class="form-group col-md-12">
			<label class="control-label">说明</label>
			<textarea name="note" class="form-control" rows="3">${workFlow.note}</textarea>
		</div>
		<div class="form-group" id="divEmpList">
			<label class="control-label col-md-12"><span class="required">*</span>处理人</label>
			<c:forEach var="employee" items="${employee}">
				<div class="col-xs-6" style="white-space:nowrap; overflow:hidden">
					<input type="checkbox" value="${employee.empId}" id="${employee.empId}" name="empList" <c:if test="${employee.checked eq 'Y'}">checked</c:if> />
					<label class="control-label" for="${employee.empId}">${employee.empName}</label>
				</div>
			</c:forEach>
		</div>
		
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="button"  data-mini="true"  data-theme="b" data-icon="check" onclick="submitForm();"><qc:message code="system.common.btn.save"/></button>
				</div>
				<div class="ui-block-b">
					<a class="mgr-no" href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowList") %>" data-mini="true" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>
			</div>
		</div>				
	</form>
</div>