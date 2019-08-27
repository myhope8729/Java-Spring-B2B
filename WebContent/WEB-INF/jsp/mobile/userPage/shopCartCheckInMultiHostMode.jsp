<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript">
	var afterSavingUrl = "<%= BaseController.getCmdUrl("Order", "orderList") %>";
	var multiHostMode = "${multiHostMode}";
</script>
<style>
	.ui-select .ui-btn select{color: #333 !important; }
	.ui-select .ui-btn{font-weight:normal;}
</style>

<div id="page-header" class="" data-role="header" data-theme="b" >
    <h3 class="page_title"><i class="fa fa-shopping-cart"></i> 商品结算</h3>    
</div>

<div id="page-main" role="main" class="ui-content p-cart p-shopcart">
	<form name="billFrm" id="billFrm" class="form form-horizontal" action="<%= BaseController.getCmdUrl("Order", "saveOrderAjax") %>" method="post">
		<input type="hidden" name="hbmark" id="hbmark" value="" />
		<input type="hidden" id="itemCount" name="itemCount" value="${itemCount}">
		<input type="hidden" id="hostUserId" name="hostUserId" value="${hostUser.userId}">
		<input type="hidden" id="addrId" name="addrId" value="${deliveryAddrId}">
		
		<table class="table dataTable02">
	        <thead></thead>
	        <tbody>
	        	<c:set var="vendor" value="" />
	        	<c:set var="vendor_id" value="" />
				<c:forEach var="cartProduct" items="${listCart}">	
				<c:if test="${vendor != cartProduct.userItem.vendor }">
					<c:if test='${vendor != ""}'>
						<tr class="">
							<td style="padding-left: 15px;">
								<label class="">订货说明 </label>
							</td>
							<td colspan="2" style="padding-right: 15px;">
								<input type="text" name="note" id="note_${vendor_id}" userId="${vendor_id}" value="" class="" />
							</td>
						</tr>
					</c:if>
					<tr class="vendor-row">
						<td colspan="3">
							${cartProduct.userItem.vendor}
						</td>
					</tr>
		        	<c:set var="vendor" value="${cartProduct.userItem.vendor}" />
				</c:if>
	        	<tr>
	                <td class="store-item-image"  style="padding-left: 15px;">
	                	<c:if test="${cartProduct.userItem.picShowMode == 'SYSD00000000011'}">
			            <a href="${cartProduct.userItem.itemBigImg}" class="fancybox fancybox-pic" title="${cartProduct.userItem.itemName}" data-fancybox-group="cart-gallery">
			            </c:if>
			            
			            <c:if test="${cartProduct.userItem.picShowMode != 'SYSD00000000011'}">
	                	<a href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=${cartProduct.itemId}&hostUserId=${cartProduct.userItem.userId}" target="_blank">
			            </c:if>
			            	<img border="0" src="${cartProduct.userItem.itemMediumImg}">
						</a>
	                </td>
	                <td class="alignL store-item-info">
                		<a class="store-item-title" href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=${cartProduct.userItem.itemId}&hostUserId=${cartProduct.userItem.userId}" target="_blank" title="${cartProduct.userItem.itemName}">${cartProduct.userItem.itemName}</a>
			            <div class="store-item-note">
							${cartProduct.userItem.note}
						</div>
						<div class="store-item-price">
	                		<c:if test="${not empty cartProduct.userItem.itemPrice }">
	                			${cartProduct.userItem.itemPrice}元
	                		</c:if>
	                		<c:if test="${not empty cartProduct.userItem.itemUnit }">
	                		 / ${cartProduct.userItem.itemUnit}
	                		</c:if>
							
							<input type="hidden" name="productPrice" value="">
	                	</div>
	                </td>
	                <td class="alignR vam amt-total" style="padding-right: 15px;">
	                	<div class="store-item-check">
	                		X ${cartProduct.qty}	
	                	</div>
	                	<div class="totalSumWrap"><b>
	                		<span name="priceSum" id="priceSum${cartProduct.itemId}">
	                			<c:if test="${ cartProduct.userItem.total_amt > 0 }" >
	                			${cartProduct.userItem.total_amt}元
	                			</c:if>
	                		</span>
	                	</b></div>
	                </td>
	            </tr>
	            
		            <input type="hidden" name="userId" value="${cartProduct.userItem.userId}">            			
		            <input type="hidden" name="itemId" value="${cartProduct.itemId}">            			
		            <input type="hidden" name="price" value="${cartProduct.userItem.itemPrice}">
		            <input type="hidden" name="qty" value="${cartProduct.qty}" />
	         		<input type="hidden" name="priceField" value="${cartProduct.userItem.priceCol}" />
					<input type="hidden" name="priceValue" value="${cartProduct.userItem.itemPrice}" />
					
					<c:set var="vendor_id" value="${cartProduct.userItem.userId}" />
					
	            </c:forEach>
	            <c:if test='${vendor != ""}'>
					<tr class="">
						<td style="padding-left: 15px;">
							<label class="">订货说明 </label>
						</td>
						<td colspan="2" style="padding-right: 15px;">
							<input type="text" name="note" id="note_${vendor_id}" userId="${vendor_id}" value="" class="" />
						</td>
					</tr>
				</c:if>
	        </tbody>
		</table>
		<div class="row">
			<div class="col-sm-12 alignC ui-btn-3">
				<span class="totalSumWrap">${itemCount}</span>种商品，合计<span class="totalSumWrap">${totalSum}元</span>
			</div>				
			<div class="col-sm-12">
				<div class="form-group">
					<label class="col-lg-1 control-label tb-cell"><c:if test="${isArrivalDateRequired}"><span class="required">*</span></c:if> 送货日期</label>
					<div class="col-lg-2 tb-cell">
						<input type="text" class="form-control date-picker" name="arriveDate" id="arriveDate" data-date-format="yyyy-mm-dd" value="${defaultArrivalDate }" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="col-sm-12 ui-btn-4  mgb20 ">
				收货地址
			</div>
			<div class="col-sm-12">
				<label class="control-label col-sm-12"><span>${deliveryAddr.locationName}&nbsp;${deliveryAddr.address}</span></label>
				<label class="control-label col-sm-12"><span>收货人：${deliveryAddr.contactName}</span></label>
				<label class="control-label col-sm-12"><span>联系电话：${deliveryAddr.telNo}</span></label>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="text-center mgb20 mgt20">
				<button type="button" onclick="billOk()" class="bill-btn ui-btn ui-btn-b ui-corner-all">
					<qc:message code="system.common.btn.submit"/>
				</button>
			</div>
		</div>
		
		
	</form>
</div>
