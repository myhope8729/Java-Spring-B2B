<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript" src="<c:url value="/js/userPage/shopCart.js"/>"></script>

<div class="admin_body bg-white">
	<div class="pd10 color-white" style="background-color:#${hostUser.mainColor};">
		<span class="font-18"><i class="fa fa-shopping-cart"></i> 购物车</span>
	</div>
	<form name="checkFrm" id="checkFrm" action="<%= BaseController.getCmdUrl("UserPage", "shopCartCheck") %>&hostUserId=${hostUser.userId}" method="post">			
		<table class="table table-bordered dataTable02">
	        <thead>
	            <tr>
	                <th class="text-center"></th>
	                <th colspan="2">商品</th>
	                <th class="text-center">数量</th>
	                <th class="text-right">价格/单位</th>
	                <th class="text-right">金额</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:set var="item_record" value="0" />
				<c:forEach var="cartProduct" items="${listShopCart}">	
	        	<tr>
	                <td class="alignC vam">
	                	<input onClick="line_changed()" type="checkbox" name="lineCheck" id="lineCheck${item_record}" <c:if test="${not empty cartProduct.userItem.itemPrice }">checked="checked"</c:if> value="${cartProduct.userItem.userId}${cartProduct.itemId}">
	                </td>
	                <td class="item-img" width="200">
	                	<a href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=${cartProduct.itemId}&hostUserId=${cartProduct.userItem.userId}" target="_blank">
							<img border="0" src="${cartProduct.userItem.itemMediumImg}" onerror="javascript:this.src='/images/rm.jpg'">
						</a>
	                </td>
	                <td class="alignL vam">
	                	<div class="col-lg-12">
							<p class="font-16">${cartProduct.userItem.itemName}</p>
							<p class="color-dark">${cartProduct.userItem.note}</p>
							<br>
							<input type="hidden" name="productPrice" value="">
						</div>
	                </td>
	                <td class="alignC vam w150 cartBtns">
	                	<a class="btn btn-xs btn-danger" onclick="s_qtyControl(this, '${cartProduct.itemId}', -1);"><i class="fa fa-minus"></i></a>		            			
            			<input class="qty alignC form-control" type="text" name="qty" value="${cartProduct.qty}" onchange="s_qtyChanged(this, '${cartProduct.itemId}');" id="qty_${cartProduct.itemId}">
						<a class="btn btn-xs btn-success" onclick="s_qtyControl(this, '${cartProduct.itemId}', 1);"><i class="fa fa-plus"></i></a>
	                </td>
	                <td class="alignR vam w150">
	                	<div class="color-red">
	                		<c:if test="${not empty cartProduct.userItem.itemPrice }">
	                			${cartProduct.userItem.itemPrice}元
		                		<c:if test="${not empty cartProduct.userItem.itemUnit }">
		                		 / ${cartProduct.userItem.itemUnit}
		                		</c:if>
	                		</c:if>
	                	</div>
	                </td>
	                <td class="alignR vam w150">
	                	<div class="color-red"><b>
	                		<span name="priceSum" id="priceSum${cartProduct.itemId}"></span>
	                	</b></div>
	                </td>
	            </tr>
		            <c:set var="item_record" value="${item_record + 1}"/>
		            <input type="hidden" name="itemId" value="${cartProduct.itemId}">            			
	         		<input type="hidden" name="price" value="${cartProduct.userItem.itemPrice}">
	            </c:forEach>
	        </tbody>
		</table>
	
		<div class="row">
			<div class="text-center pd20">
				<span class="font-16">商品合计金额：<span id="totalSum"></span>元</span>
				<input type="hidden" name="totalSum" value="">
			</div>
		</div>
		
		<div class="row">
			<div class="text-center pdb20">
				<button type="button" onclick="checkBill()" class="checkbill-btn color-white pd10" style="background-color:#${hostUser.mainColor};">
					<span class="font-16">结&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;算</span>
				</button>
			</div>
		</div>	
	
	<input type="hidden" id="hostUserId" name="hostUserId" value="${hostUser.userId}"/>
	<input type="hidden" id="cookieShopCart" name="cookieShopCart" value="${cookieShopCart}"/>
	<input type="hidden" id="itemCount" name="itemCount" value="${item_record}">
	
	</form>
</div>
