<%@page import="com.kpc.eos.core.Constants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.BizSetting"%>


<script type="text/javascript" src="<c:url value="/js/lib/plugins/ias/js/jquery-ias.min.js"/>"></script>


<script>
	itemNameCol = "${itemNameCol}";
	itemPriceCol = "${itemPriceCol}";
	itemUnitCol = "${itemUnitCol}";
	itemPackage = "${itemPackage}";
	cookieId 	= "${ckEosCart}";
</script>
<div data-role="header" data-theme="b" >
   <h3 class="page_title">${itemGroup.c1}</h3>
   <a data-theme="b" data-role="button" data-icon="back" data-inline="true" class="ui-btn-right" data-iconpos="left" href="UserPage.do?cmd=mainPage&hostUserId=${hostUserId}"> 
    	<qc:message code="system.common.btn.back" /> 
    </a>
</div>
<div class="admin-body">
	<h3 class="page_title"></h3>	
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
			
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div id="items-wrap" class="row store-items">
					<%
					
					ArrayList<UserItemModel> rows = (ArrayList<UserItemModel>)request.getAttribute("rows");
					
					for (UserItemModel row : rows)
					{
						String itemName = row.getItemName();
						String itemNameShort = "";
						if (StringUtils.isEmpty(itemName)) 
						{
							itemName = row.getItemCode();
						}
						if (itemName == null) {itemName = "";}
						
						
						if (itemName.length() > 10) {
							itemNameShort = itemName.substring(0, 10) + "...";
						} else {
							itemNameShort = itemName;
						}
						
						String itemUnit = row.getItemUnit();
						String itemPrice = row.getItemPrice();
						String itemNote = row.getNote();
						
						//String itemNote = itemName;
						String itemId = row.getItemId();
						
						String imgPath = row.getItemMediumImg();
						
						String priceDisp = "";
						if (StringUtils.isNotEmpty(itemPrice))
						{
							priceDisp += itemPrice + "元"; 
						}
						
						if (StringUtils.isNotEmpty(itemUnit))
						{
							priceDisp += "/" + itemUnit; 
						}
						
						String hcConnected = row.getHcConnected();
						if ( StringUtils.isNotEmpty(request.getParameter("hostUserId")) ) 
						{
							hcConnected = "1";	
						}
						
					%>
						<div class="store-item-wrap col-xs-12" itemId="<%=itemId %>" id="<%=row.getUserId() %>_<%=itemId %>">
							<div class="store-item mgt10">
					        	<div class="col-md-12 store-item-detail">
							        <div class="store-item-info">
							            <a class="store-item-title" href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=<%=itemId %>&hostUserId=<%=row.getUserId() %>" target="_blank" title="<%= itemName %>"><%= itemNameShort %></a>
							            <div class="store-item-note">
							            <% if ( StringUtils.isNotEmpty(itemNote)) { %>
											<%= itemNote %>
										<% } %>
							            </div>
							            <span class="store-item-price themed-color-dark"><%= priceDisp %></span>
					                </div>
					                <div class="img-wrap">
										<div class="store-item-image">
											<% if (BizSetting.ITEMSHOW_BIG.equals(row.getPicShowMode())) { %>
								            <a href="<%= row.getItemBigImg() %>" class="fancybox fancybox-pic" title="<%= itemName %>" data-fancybox-group="order-gallery">
								            <% } else { %>
								            <a href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=<%=itemId %>&hostUserId=<%=row.getUserId() %>" target="_blank">
								            <% } %>
								                <img src="<%= imgPath %>" class="img-responsive" />
								            </a>
								        </div>
										<% if ( StringUtils.isNotEmpty(priceDisp) && "1".equals(hcConnected)) { %>
							            <div id="cartWrap" class="">
							            	<a class="btn btn-xs btn-danger" onclick="qtyControl(this, '<%=itemId %>', -1);" userId="<%=row.getUserId() %>"><i class="fa fa-minus"></i></a>		            			
					            			<input class="qty alignC form-control" data-role="none" type="text" name="qty" value="<%= StringUtils.isNotEmpty(row.getCartQty())? row.getCartQty() : "" %>" hcConnected="<%= hcConnected %>" onchange="qtyChanged(this, '<%=itemId %>', '<%=row.getUserId() %>');" id="qty_<%=itemId %>">
											<a class="btn btn-xs btn-success" onclick="qtyControl(this, '<%=itemId %>', 1);" userId="<%=row.getUserId() %>"><i class="fa fa-plus"></i></a>
							            </div>
							            <% } else { %>
							            <div id="cartWrapInfo">不能订货，请联系供货方</div>
							            <% } %>
									</div>
								</div>
							</div>
						</div>
					<% }  %>
				</div>
			</div>
		</div>
	</form>
	
</c:otherwise>
</c:choose>

</div>
