<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript">
	var afterSavingUrl = "<%= BaseController.getCmdUrl("Order", "orderList") %>";
	var custtypeId = "${custtypeId}";
</script>
<!--  
	// BillHeadModel order
-->
<div class="admin_body bg-white">
	<div class="pd10 color-white" style="background-color:#${hostUser.mainColor};">
		<span class="font-18">商品结算</span>
	</div>
	<form name="billFrm" id="billFrm" class="form form-horizontal pd15" action="<%= BaseController.getCmdUrl("Order", "saveOrderAjax") %>" method="post">
		<input type="hidden" name="hbmark" id="hbmark" value="" />
		
		<div class="row">
		<table class="table table-bordered dataTable02">
	        <thead>
	            <tr>
	                <th class="text-center"></th>
	                <th colspan="2">商品</th>
	                <th class="text-center w100">数量</th>
	                <th class="text-right w100">价格/单位</th>
	                <th class="text-right w100">金额</th>
	                <th class="text-right w100" >备注</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<c:set var="item_record" value="0" />
				<c:forEach var="cartProduct" items="${listCart}">	
	        	<tr>
	                <td class="alignC vam">
	                	${item_record + 1}
	                </td>
	                <td class="item-img vam w100">
	                	<a href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=${cartProduct.itemId}&hostUserId=${cartProduct.userItem.userId}">
							<img class="" border="0" src="${cartProduct.userItem.itemMediumImg}" onerror="javascript:this.src='/images/rm.jpg'">
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
	                <td class="alignC vam w150">
	                	${cartProduct.qty}
	                </td>
	                <td class="alignR vam w150">
	                	<div class="color-red">
	                		<c:if test="${not empty cartProduct.userItem.itemPrice }">
	                			${cartProduct.userItem.itemPrice}元
	                		</c:if>
	                		<c:if test="${not empty cartProduct.userItem.itemUnit }">
	                		 / ${cartProduct.userItem.itemUnit}
	                		</c:if>
	                	</div>
	                </td>
	                <td class="alignR vam w150">
	                	<div class="color-red"><b>
	                		<span name="priceSum" id="priceSum${cartProduct.itemId}">
	                			<c:if test="${ cartProduct.userItem.total_amt > 0 }" >
	                			${cartProduct.userItem.total_amt}元
	                			</c:if>
	                		</span>
	                	</b></div>
	                </td>
	                <td class="vam">
	         			<input type="text" name="item_note" class="form-control w100" value="" />
	                </td>
	            </tr>
		            <input type="hidden" name="itemId" value="${cartProduct.itemId}">            			
		            <input type="hidden" name="price" value="${cartProduct.userItem.itemPrice}">
		            <input type="hidden" name="qty" value="${cartProduct.qty}" />
	         		<input type="hidden" name="priceField" value="${priceField}" />
					<input type="hidden" name="priceValue" value="${cartProduct.userItem.itemPrice}" />
					
					<c:set var="item_record" value="${item_record + 1}"/>
	            </c:forEach>
	        </tbody>
		</table>
		</div>
		
		<div class="row">
			<div class="bg-dark">
				<div class="pd10">
					<span class="font-18">${itemCount}种商品，合计${totalSum}元</span>
				</div>
			</div>
			
			<div class="form-group mgt20">
				<label class="col-lg-1 control-label"><span class="required">*</span>付款方式</label>
				<div class="col-lg-3">
					<qc:htmlSelect items="${payTypeList}" itemValue="payTypeId" itemLabel="payTypeName" selValue=''
						isEmpty="true" emptyLabel="==请选择付款方式==" cssClass="form-control wd200" id="paytypeId" name="paytypeId" modelAttr="prePayYn,weixinYn" />
				</div>
				<div class="col-lg-3"  id="paymentTypeWrap">
					<qc:htmlSelect items="${subPayTypeList}" itemValue="paytype2" itemLabel="paytype2" selValue=''
						isEmpty="true" emptyLabel="==请选择预付款类别==" cssClass="form-control wd200" id="paymentType" name="paymentType" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-lg-1 control-label"><c:if test="${isArrivalDateRequired}"><span class="required">*</span></c:if> 送货日期</label>
				<div class="col-lg-2">
					<input type="text" class="form-control date-picker" name="arriveDate" id="arriveDate" data-date-format="yyyy-mm-dd" value="${defaultArrivalDate }" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-1 control-label">订货说明 </label>
				<div class="col-lg-3">
					<input type="text" name="note" id="note" value="" class="form-control" />
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="bg-dark">
				<div class="pd10">
					<span class="font-18">收货地址</span>
				</div>
			</div>
			<div class="pd10">
				<div class="pd5 font-14"><span>${deliveryAddr.locationName}&nbsp;${deliveryAddr.address}</span></div>
				<div class="pd5 font-14"><span>收货人：${deliveryAddr.contactName}</span></div>
				<div class="pd5 font-14"><span>联系电话：${deliveryAddr.telNo}</span></div>
			</div>
		</div>
		
		<div class="row">
			<div class="text-center pdb20">
				<button type="button" onclick="billOk()" class="bill-btn btn btn-primary">
					<qc:message code="system.common.btn.submit"/>
				</button>
			</div>
		</div>
		
		<input type="hidden" id="itemCount" name="itemCount" value="${item_record}">
		<input type="hidden" id="hostUserId" name="hostUserId" value="${hostUser.userId}">
		<input type="hidden" id="addrId" name="addrId" value="${deliveryAddrId}">
		
	</form>
</div>
