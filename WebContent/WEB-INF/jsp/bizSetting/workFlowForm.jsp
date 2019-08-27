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

<h3 class="page_title"><c:choose><c:when test="${ableToInsert}">新增业务流程</c:when><c:otherwise>修改业务流程</c:otherwise></c:choose></h3>

<div class="admin_body">
	<div class="col-md-6">
		<table id="grid">
		</table>
	</div>
	<div class="col-md-6">
		<form class="form-horizontal admin-form" id="workFlowForm" name="workFlowForm" role="form" action="<%= BaseController.getCmdUrl("WorkFlow", "saveWorkFlow") %>" method="POST">
			<input type="hidden" id="pagingYn" name="pagingYn" value="N" />
			<input type="hidden" id="workFlowId" name="workFlowId" value="${workFlow.workFlowId}" />
			<input type="hidden" id="seqData" name="seqData">
			
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>业务类型</label>
				<div class="col-md-7">
					<qc:codeList var="workFlowTypeList" codeGroup="BT0000" />
					<qc:htmlSelect items="${workFlowTypeList}" itemValue="codeId" itemLabel="codeName" selValue='${workFlow.workFlowType}'
						isEmpty="true" emptyLabel='sc.please.select' cssClass="form-control" name="workFlowType" customAttr='validate="required: true" onchange="reloadWorkFlowGrid(this);"' />
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>单据处理名称</label>
				<div class="col-md-7">
					<input type="text" name="workFlowName" value="${workFlow.workFlowName}" class="form-control" validate="required: true" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>能否修改价格</label>
				<div class="col-md-4">
					<input type="radio" defaultValue="N" name="priceYn" id="priceN" value="N" <c:if test="${empty workFlow.priceYn or workFlow.priceYn=='N'}"> checked </c:if> />
					<label class="control-label" for="priceN">不能</label>
				</div>
				<div class="col-md-3">
					<input type="radio" name="priceYn" id="priceY" value="Y" <c:if test="${workFlow.priceYn=='Y'}"> checked </c:if> />
					<label class="control-label" for="priceY">能</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>能否修改数量</label>
				<div class="col-md-4">
					<input type="radio" defaultValue="N" name="qtyYn" id="qtyN" value="N" <c:if test="${empty workFlow.qtyYn or workFlow.qtyYn=='N'}"> checked </c:if> />
					<label class="control-label" for="qtyN">不能</label>
				</div>
				<div class="col-md-3">
					<input type="radio" name="qtyYn" id="qtyY" value="Y" <c:if test="${workFlow.qtyYn=='Y'}"> checked </c:if> />
					<label class="control-label" for="qtyY">能</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>能否修改运费</label>
				<div class="col-md-4">
					<input type="radio" defaultValue="N" name="shipPriceYn" id="shipPriceN" value="N" <c:if test="${empty workFlow.shipPriceYn or workFlow.shipPriceYn=='N'}"> checked </c:if> />
					<label class="control-label" for="shipPriceN">不能</label>
				</div>
				<div class="col-md-3">
					<input type="radio" name="shipPriceYn" id="shipPriceY" value="Y" <c:if test="${workFlow.shipPriceYn=='Y'}"> checked </c:if> />
					<label class="control-label" for="shipPriceY">能</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>最小金额(元)</label>
				<div class="col-md-7">
					<input type="text" name="minCost" defaultValue="0" value="<c:choose><c:when test="${empty workFlow.minCost}">0</c:when><c:otherwise>${workFlow.minCost}</c:otherwise></c:choose>" class="form-control" validate="required: true, digits: true" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>最大金额(元)</label>
				<div class="col-md-7">
					<input type="text" name="maxCost" defaultValue="9999999999" value="<c:choose><c:when test="${empty workFlow.maxCost}">9999999999</c:when><c:otherwise>${workFlow.maxCost}</c:otherwise></c:choose>" class="form-control" validate="required: true, digits: true" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>分发订单</label>
				<div class="col-md-7">
					<select name="distributeYn" class="form-control" validate="required: true">
						<option value="" <c:if test="${workFlow.distributeYn==''}">selected</c:if>><qc:message code="sc.please.select" /></option>
						<option value="Y" <c:if test="${workFlow.distributeYn=='Y'}">selected</c:if>>是</option>
						<option value="N" <c:if test="${workFlow.distributeYn=='N'}">selected</c:if>>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>仅处理自己供货商品</label>
				<div class="col-md-7">
					<select name="itemYn" class="form-control" validate="required: true">
						<option value="" <c:if test="${workFlow.itemYn==''}">selected</c:if>><qc:message code="sc.please.select" /></option>
						<option value="Y" <c:if test="${workFlow.itemYn=='Y'}">selected</c:if>>是</option>
						<option value="N" <c:if test="${workFlow.itemYn=='N'}">selected</c:if>>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>分组处理</label>
				<div class="col-md-7">
					<select name="groupYn" id="groupYn" class="form-control" validate="required: true" onchange="showEmployerDiv(this);">
						<option value="" <c:if test="${workFlow.groupYn==''}">selected</c:if>><qc:message code="sc.please.select" /></option>
						<option value="Y" <c:if test="${workFlow.groupYn=='Y'}">selected</c:if>>是</option>
						<option value="N" <c:if test="${workFlow.groupYn=='N'}">selected</c:if>>否</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label">说明</label>
				<div class="col-md-7">
					<textarea name="note" class="form-control" rows="3">${workFlow.note}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-5 control-label"><span class="required">*</span>状态</label>
				<div class="col-md-7">
					<qc:codeList var="stateCodes" codeGroup="ST0000" />
					<qc:htmlSelect items="${stateCodes}" itemValue="codeId" itemLabel="codeName" selValue='${workFlow.state}' cssClass="form-control" name="state"/>
				</div>
			</div>
			<div class="form-group" id="divEmpList">
				<label class="col-md-5 control-label"><span class="required">*</span>处理人</label>
				<div class="col-md-7">
					<c:forEach var="employee" items="${employee}">
						<div class="col-md-6" style="white-space:nowrap; overflow:hidden">
							<input type="checkbox" value="${employee.empId}" id="${employee.empId}" name="empList" <c:if test="${employee.checked eq 'Y'}">checked</c:if> />
							<label class="control-label" for="${employee.empId}">${employee.empName}</label>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-offset-5 col-md-7">
					<button type="button" class="btn btn-primary" onclick="submitForm();"><qc:message code="system.common.btn.save"/></button>
					<a href="<%= BaseController.getCmdUrl("WorkFlow", "workFlowList") %>" class="btn btn-default pull-right" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>
			</div>
		</form>
	</div>
</div>