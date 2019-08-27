<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript" src="<c:url value="/js/mobile/userPage/shopCart.js"/>"></script>

<div id="page-header" class="" data-role="header" data-theme="b" >
    <h3 class="page_title"><i class="fa fa-shopping-cart"></i> 购物车</h3>
</div>

<div id="page-main" role="main" class="ui-content p-cart p-shopcart">
	<form name="checkFrm" id="checkFrm" action="<%= BaseController.getCmdUrl("UserPage", "shopCartCheck") %>&hostUserId=${hostUser.userId}" method="post">			
		<table class="table dataTable02">
	        <thead>
	        </thead>
	        <tbody>
	        	<c:set var="item_record" value="0" />
	        	<c:set var="vendor" value="" />
				<c:forEach var="cartProduct" items="${listShopCart}">	
				<c:if test="${vendor != cartProduct.userItem.vendor }">
					<tr class="vendor-row">
						<td colspan="3">
							${cartProduct.userItem.vendor}
						</td>
					</tr>
		        	<c:set var="vendor" value="${cartProduct.userItem.vendor}" />
				</c:if>
	        	<tr>
	                <td class="alignR vam amt-total totalSumWrap" style="width: 45px;">
	                	<div class="store-item-check">
	                		<input onchange="line_changed()" type="checkbox" name="lineCheck" <c:if test="${not empty cartProduct.userItem.itemPrice }">checked="checked"</c:if> value="${cartProduct.userItem.userId}${cartProduct.itemId}">
	                	</div>
	                </td>
	                <td class="alignL store-item-info">
                		<a class="store-item-title" href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=${cartProduct.userItem.itemId}&hostUserId=${cartProduct.userItem.userId}" target="_blank" title="${cartProduct.userItem.itemName}">${cartProduct.userItem.itemName}</a>
			            <div class="store-item-note">
							${cartProduct.userItem.note}
						</div>
						<div class="store-item-price">
	                		<c:if test="${not empty cartProduct.userItem.itemPrice }">
	                			${cartProduct.userItem.itemPrice}元
		                		
		                		<c:if test="${not empty cartProduct.userItem.itemUnit }">
		                		 / ${cartProduct.userItem.itemUnit}
		                		</c:if>
	                		</c:if>
							<input type="hidden" name="productPrice" value="">
		                	<div class="priceSum" name="priceSum" id="priceSum${cartProduct.userItem.userId}${cartProduct.itemId}"></div>
	                	</div>
	                </td>
	                <td class="store-item-image">
	                	<c:if test="${cartProduct.userItem.picShowMode == 'SYSD00000000011'}">
			            <a href="${cartProduct.userItem.itemBigImg}" class="fancybox fancybox-pic" title="${cartProduct.userItem.itemName}" data-fancybox-group="cart-gallery">
			            </c:if>
			            
			            <c:if test="${cartProduct.userItem.picShowMode != 'SYSD00000000011'}">
	                	<a href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=${cartProduct.itemId}&hostUserId=${cartProduct.userItem.userId}" target="_blank">
			            </c:if>
							<img border="0" src="${cartProduct.userItem.itemMediumImg}">
						</a>
						<div id="cartWrap" class="<c:if test="${empty cartProduct.userItem.itemPrice }">hidden</c:if>">
							<a class="btn btn-xs btn-danger" onclick="s_qtyControl(this, '${cartProduct.itemId}', -1);" userId="${cartProduct.userItem.userId}"><i class="fa fa-minus"></i></a>		            			
	            			<input class="qty alignC form-control" data-role="none" type="text" name="qty" value="${cartProduct.qty}" onchange="s_qtyChanged(this, '${cartProduct.itemId}', '${cartProduct.userItem.userId}');" id="qty_${cartProduct.itemId}">
							<a class="btn btn-xs btn-success" onclick="s_qtyControl(this, '${cartProduct.itemId}', 1);" userId="${cartProduct.userItem.userId}"><i class="fa fa-plus"></i></a>
						</div>
	                </td>
	            </tr>
		            <c:set var="item_record" value="${item_record + 1}"/>
		            <input type="hidden" name="itemId" value="${cartProduct.itemId}">            			
		            <input type="hidden" name="userId" value="${cartProduct.userItem.userId}">            			
	         		<input type="hidden" name="price" value="${cartProduct.userItem.itemPrice}">
	            </c:forEach>
	        </tbody>
		</table>
	
		<div class="row-fluid">
			<div class="text-center pd20">
				<span class="ui-btn-3">商品合计金额：<span class="totalSumWrap"><span id="totalSum"></span>元</span></span>
				<input type="hidden" name="totalSum" value="">
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="text-center col- pdb20">
				<button type="button" onclick="checkBill()" class="checkbill-btn ui-btn ui-btn-b  ui-corner-all">
					结 算
				</button>
			</div>
		</div>	
	
	<input type="hidden" id="hostUserId" name="hostUserId" value="${hostUser.userId}"/>
	<input type="hidden" id="cookieShopCart" name="cookieShopCart" value="${cookieShopCart}"/>
	<input type="hidden" id="itemCount" name="itemCount" value="${item_record}">
	
	</form>
</div>
