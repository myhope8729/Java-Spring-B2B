<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
	var messages = {
			addItemConfirm : "<qc:message code='useritem.item.addconfirm' />"
		};
</script>
<div class="admin_body">
	<h3 class="page_title">用户商品目录</h3>
	<form name="userItemFrm" class="form-horizontal" id="userItemFrm" action="UserItem.do?cmd=downloadUserItemList" method="post">
		<div class="row">
			<div class="text-left col-sm-6 col-md-4 col-xs-12">
				<div class="form-group">
					<c:if test="${not empty categoryList}">
						<div class="col-xs-6">
							<select name="category" id="category" class="form-control col-xs-6">
								<option value="">===分类===</option>
								<c:forEach var="category" items="${categoryList}">
									<option value="${category}" <c:if test="${sc.category == category}">selected</c:if>>
										<c:if test='${category == "-1"}'>未分类</c:if>
										<c:if test='${category != "-1"}'>${category}</c:if>
									</option>
								</c:forEach>
							</select>
						</div>
					</c:if>
					<div class="col-xs-6">
						<qc:codeList var="states" codeGroup="ST0000" />
						<select id="state" name="state" class="form-control col-xs-6">
							<option value="" <c:if test="${sc.state == ''}">selected</c:if>>==状态==</option>
							<c:forEach var="state" items="${states}">
								<option value="${state.codeId}" <c:if test="${sc.state == state.codeId}">selected</c:if>>${state.codeName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="text-right col-sm-6 col-md-4 col-xs-6">
				<div class="form-group">
					<label for="chelp" class="col-xs-3 control-label text-right">关键字</label>
					<div class="col-sm-6 col-xs-5">
						<input type="text" class="form-control" name="chelp" id="chelp" value="${sc.chelp}"/>
					</div>
					<div class="col-sm-3 col-xs-4">
						<button type="button" id="search" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
					</div>
				</div>
			</div>
			<div class="text-right col-sm-12 col-md-4 col-xs-6">
				<div class="form-group">
					<div class="col-xs-12">
						<a class="btn btn-primary" href="UserItem.do?cmd=userItemPropertyList"><qc:message code="system.common.btn.setting"/></a>
						<a class="btn btn-primary mgl10" href="javascript:addItem();"><qc:message code="system.common.btn.new"/></a>
						<button type="submit" class="btn btn-primary mgl10"><qc:message code="system.common.btn.export"/></button>
						<a class="btn btn-primary mgl10" href="UserItem.do?cmd=groupItemList"><qc:message code="system.common.btn.product.group"/></a>
					</div>
				</div>
			</div>
		</div>
	</form>
	
	<div class="clear">
		<table id="grid"></table>
		<div id="gridpager"></div>
	</div>
	<!-- list end -->
</div>