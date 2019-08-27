<%@page import="com.kpc.eos.core.BizSetting"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>


		<div id="reload-wrap">
			<div id="items-wrap" class="store-items">
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
					
					
					if (false && itemName.length() > 10) {
						itemNameShort = itemName.substring(0, 10) + "...";
					} else {
						itemNameShort = itemName;
					}
					
					String itemUnit = row.getItemUnit();
					String itemPrice = row.getItemPrice();
					String itemNote = row.getNote();
					
					String vendorStr = (StringUtils.isEmpty(request.getParameter("hostUserId")))? row.getVendor() : "";
					
					//String itemNote = itemName;
					String itemId = row.getItemId();
					
					String imgPath = row.getItemMediumImg();
					
					String priceDisp = "";
					if (StringUtils.isNotEmpty(itemPrice))
					{
						priceDisp += itemPrice + "元"; 
						
						if (StringUtils.isNotEmpty(itemUnit))
						{
							priceDisp += "/" + itemUnit; 
						}
						
					}
					
					String hcConnected = row.getHcConnected();
					if ( StringUtils.isNotEmpty(request.getParameter("hostUserId")) ) 
					{
						hcConnected = "1";	
					}
					
				%>
					<div class="store-item-wrap" itemId="<%=itemId %>" id="<%=row.getUserId() %>_<%=itemId %>">
						<div class="store-item">
					        <div class="row">
					        	<div class="col-md-12">
						        	<div class="store-item-vendor">
						            <% if ( StringUtils.isNotEmpty(vendorStr)) { %>
										<%= vendorStr %>
									<% } %>
						            </div>
					        	</div>
					        </div>
					        
					        <div class="row">
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
					</div>
				<% } %>
				<%= (rows.size() < 1) ? "<div class='no-product'>未找到商品资料</div>" : "" %>
			</div>
			<div id="pagination" class="pagination">
				<c:if test="${not empty nextUrl }">
				<a class="next" href="${nextUrl}">Next</a>
				</c:if>
			</div>
		</div>