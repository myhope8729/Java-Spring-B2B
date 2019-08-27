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
	itemNameCol = "${itemNameCol}";
	itemPriceCol = "${itemPriceCol}";
	itemUnitCol = "${itemUnitCol}";
	itemPackage = "${itemPackage}";
	cookieId 	= "${ckEosCart}";
</script>
<div class="admin-body">	
<c:choose>
<c:when test="${EmptyCategory eq 'Y'}">
	<div class="bg-white">
		没有数据
	</div>			
</c:when>
<c:otherwise>

	<input type="hidden" id="<%= Constants.COOKIE_KEY_EOS_CART %>" value="${ckEosCartVal}" />
	
	<form name="productCategoryFrm" id="productCategoryFrm" class="form-inline" action="<%= BaseController.getCmdUrl("UserPage", "itemTypeSearchAjax") %>&hostUserId=${hostUserId}" method="post" onsubmit="return false;">	
		<input type="hidden" name="catFieldName" id="catFieldName" value="${itemType1}" />
		<input type="hidden" name="catFieldName2" id="catFieldName2" value="${itemType2}" />
		<input type="hidden" name="category" id="category" value="${sc.category }" />
		<input type="hidden" name="category2" id="category2" value="${sc.category2 }" />
		<input type="hidden" name="page.sidx" id="sidx" value="${sc.page.sidx}" />
		<input type="hidden" name="page.sord" id="sord" value="${sc.page.sord}" />
		
		<div class="row">
			<c:if test="${not empty uiCatListHtml }">
			<div class="col-lg-3 col-md-3 col-sm-4 col-xs-5">
				<div class="cat-wrap">
					<div class="cat-title">商品分类</div>
					${uiCatListHtml}
				</div>
			</div>
			</c:if>
			
			<c:if test="${not empty uiCatListHtml }">
			<div class="col-lg-9 col-md-9 col-sm-8  col-xs-6">
			</c:if>
			<c:if test="${empty uiCatListHtml }">
			<div class="col-lg-12 col-md-12 col-sm-12">
			</c:if>
				<div class="form-group">
					<label class="control-label">查找商品</label>
					<input type="text"  class="form-control" name="chelp" id="chelp" value="${sc.chelp}" />
					<button type="button" class="btn btn-primary" id="btnSearch"><qc:message code="system.common.btn.search"/></button>
					<button type="button" id="reset" class="btnReset btn btn-default"><qc:message code="system.common.btn.reset"/></button>
				</div>
				
				<jsp:include page="itemTypeInner.jsp"></jsp:include>
			</div>
		</div>
	</form>
	
</c:otherwise>
</c:choose>

</div>
