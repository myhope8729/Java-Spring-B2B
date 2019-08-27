<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kpc.eos.model.bizSetting.UserItemModel"%>
<%@page import="com.kpc.eos.core.web.tag.HtmlSelectTag"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<%@page import="com.kpc.eos.core.BizSetting"%>


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
						
						if (StringUtils.isNotEmpty(itemPrice) && StringUtils.isNotEmpty(itemUnit))
						{
							priceDisp += "/" + itemUnit; 
						}
						
					%>
						<div class="store-item-wrap col-lg-4 col-md-4 col-sm-6"  data-toggle="animation-appear" data-animation-class="animation-fadeInQuick" data-element-offset="-100" itemId="<%=itemId %>" id="<%=itemId %>">
							<div class="store-item">
								<% if ( StringUtils.isNotEmpty(itemNote) || StringUtils.isNotEmpty(priceDisp)) { %>
								<div class="store-item-rating text-warning">
									<% if ( StringUtils.isNotEmpty(itemNote)) { %>
										<%= itemNote %>
										<br />
									<% } %>
									<% if ( StringUtils.isNotEmpty(priceDisp)) { %>
						            <div id="cartWrap">
						            	<a class="btn btn-xs btn-danger" onclick="qtyControl(this, '<%=itemId %>', -1);"><i class="fa fa-minus"></i></a>		            			
				            			<input class="qty alignC form-control" type="text" name="qty" value="<%= StringUtils.isNotEmpty(row.getCartQty())? row.getCartQty() : "" %>" onchange="qtyChanged(this, '<%=itemId %>');" id="qty_<%=itemId %>">
										<a class="btn btn-xs btn-success" onclick="qtyControl(this, '<%=itemId %>', 1);"><i class="fa fa-plus"></i></a>
						            </div>
						            <% } %>
								</div>
					            <% } %>
								<div class="store-item-image">
									<% if (BizSetting.ITEMSHOW_BIG.equals(row.getPicShowMode())) { %>
						            <a href="<%= row.getItemBigImg() %>" class="fancybox fancybox-pic" title="<%= itemName %>" data-fancybox-group="order-gallery">
						            <% } else { %>
						            <a href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=<%=itemId %>&hostUserId=<%=row.getUserId() %>" target="_blank">
						            <% } %>
						                <img src="<%= imgPath %>" class="img-responsive" />
						            </a>
						        </div>
						        <div class="store-item-info clearfix">
						            <span class="store-item-price themed-color-dark pull-right"><%= priceDisp %></span>
						            <a class="store-item-title" href="<%= BaseController.getCmdUrl("UserItem", "viewItemPage") %>&itemId=<%=itemId %>&hostUserId=<%=row.getUserId() %>" target="_blank" title="<%= itemName %>"><%= itemNameShort %></a>
				                </div>
							</div>
						</div>
					<% }  %>
				</div>
				<div id="pagination">
					<c:if test="${not empty nextUrl }">
					<a class="next" href="${nextUrl}">Next</a>
					</c:if>
				</div>
		