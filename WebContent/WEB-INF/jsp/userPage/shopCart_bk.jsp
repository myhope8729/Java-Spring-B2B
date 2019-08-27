<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin_body bg-white">
	<div class="pd10 color-white" style="background-color:#${hostUser.mainColor};">
		<span class="font-18"><i class="fa fa-shopping-cart"></i> 购物车</span>
	</div>
	<form name="checkFrm" id="checkFrm" action="<%= BaseController.getCmdUrl("UserPage", "shopCartCheck") %>&hostUserId=${hostUser.userId}" method="post">			
		<div class="table block-table pd5">
			<div class="block-cell pd15">
				<c:set var="item_record" value="0" />
				<c:forEach var="cartProduct" items="${listShopCart}">				
					<div class="row border-bottom-grey">
						<div class="pull-left">
							<input onchange="line_changed()" type="checkbox" name="lineCheck" checked="" value="${cartProduct.itemId}">
						</div>
						<div class="col-xs-10">
							<p class="font-16">${cartProduct.userItem.itemName}</p>
							<p class="color-dark">${cartProduct.userItem.note}</p>
							<br>
							<div class="row-fluid">
								<div class="pull-left">
									<p class="color-red">${cartProduct.userItem.itemPrice}元/${cartProduct.userItem.itemUnit}</p>									
								</div>
								<div class="span2 pull-right text-right">
									<span name="priceSum"></span>
									<input type="hidden" name="productPrice" value="">
								</div>
							</div>
						</div>
						<div class="pull-right">
							<div>
								<a href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=cartProduct.itemId">
									<img width="90" border="0" src="/uploaded/useritem/${cartProduct.userItem.itemImgPath}" onerror="javascript:this.src='/images/rm.jpg'">
								</a>
							</div>
							<div>
		            			<a onclick="s_qtyminus('${item_record}');"><img border="0" width="25" src="/images/minus.png"></a>
			             		<input class="qty mg-none" type="text" name="qty" value="${cartProduct.qty}" onchange="s_qtychanged('${item_record}');">
			             		<a onclick="s_qtyplus('${item_record}');"><img border="0" width="25" src="/images/plus.png"></a>
		             		</div>
						</div>
						<input type="hidden" name="itemId" value="${cartProduct.itemId}">            			
            			<input type="hidden" name="price" value="${cartProduct.userItem.itemPrice}">
						<c:set var="item_record" value="${item_record + 1}"/>
					</div>
				</c:forEach>
			</div>
		</div>
		
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
