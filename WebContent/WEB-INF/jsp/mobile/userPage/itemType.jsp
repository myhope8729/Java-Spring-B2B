<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript" src="<c:url value="/js/lib/plugins/ias/js/jquery-ias.min.js"/>"></script>

<script>
	cookieId 	= "${ckEosCart}";
	var messages = {
		product_list_all : '<qc:message code="up.product_list_all" />',			
		product_list : '<qc:message code="up.product_list" />'		
	};
	var catList = ${itemType1List};
</script>

<div id="page-main" role="main" class="ui-content p-shopcart">
	<input type="hidden" id="<%= Constants.COOKIE_KEY_EOS_CART %>" value="${ckEosCartVal}" />
	
	<form name="productCategoryFrm" id="productCategoryFrm" class="form-inline" action="<%= BaseController.getCmdUrl("UserPage", "itemTypeSearchAjax") %>&hostUserId=${hostUserId}" method="post" onsubmit="return false;">	
		<input type="hidden" name="catFieldName" id="catFieldName" value="${itemType1}" />
		<input type="hidden" name="catFieldName2" id="catFieldName2" value="${itemType2}" />
		<input type="hidden" name="category" id="category" value="${sc.category }" />
		<input type="hidden" name="category2" id="category2" value="${sc.category2 }" />
		<input type="hidden" name="page.sidx" id="sidx" value="${sc.page.sidx}" />
		<input type="hidden" name="page.sord" id="sord" value="${sc.page.sord}" />
		
		<div class="title-bg">
			<div class="title-left">
				<input type="text"  class="form-control" name="chelp" id="chelp" value="${sc.chelp}" />
			</div>
			
			<div class="title-right text-left">
				<button type="button" data-mini="true"  data-theme="b" id="btnSearch"><qc:message code="system.common.btn.search" /></button>
				<button type="button" data-mini="true"  data-theme="b" class="btnReset" id="reset"><qc:message code="system.common.btn.reset" /></button>
			</div>	
		</div>
		
		<div class="cat-wrap-upper" id="cat-wrap-upper">
			<ul>
				<c:forEach var="cat1" items="${cat1List}" >
					<li class="col-xs-4" catName='<c:if test="${not empty cat1.parentCatName}">${cat1.parentCatName}</c:if><c:if test="${empty cat1.parentCatName }">-1</c:if>'><a href='javascript:void(0)'><c:if test="${not empty cat1.parentCatName }">${cat1.parentCatName}</c:if><c:if test="${empty cat1.parentCatName }">未分类</c:if></a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-sm-12">
				<div class="p-left">
					<div class="cat-wrap" id="cat-wrap">
						<ul>
						
						</ul>
					</div>
				</div>
				
				<div class="p-right">
					<jsp:include page="itemTypeInner.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</form>
</div>
