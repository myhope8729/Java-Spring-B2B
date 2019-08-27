<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="admin_body">
	<c:if test="${not empty bannerList }">
	<div id="banner-slide">
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<c:set var="index" value="0"></c:set>
				<c:forEach var="banner" items="${bannerList}">
					<c:if test="${banner.showMark == 'Y'}">
						<li data-target="#carousel-example-generic"	data-slide-to="${index}"
							<c:if test="${index == 0 }"> class="active" </c:if>>
						</li>
						<c:set var="index" value="${index + 1}"></c:set>
					</c:if>
				</c:forEach>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner">
				<c:set var="index" value="0"></c:set>
				<c:forEach var="banner" items="${bannerList}">
					<c:if test="${'UR0001' == banner.urlType }"><c:set var="banner_url" value="" /></c:if>
					<c:if test="${'UR0002' == banner.urlType }"><c:set var="banner_url" value="${banner.url}" /></c:if>
					<c:if test="${'UR0003' == banner.urlType and not empty banner.url}"><c:set var="banner_url" value="UserPage.do?cmd=viewItemGroup&hostUserId=${hostUser.userId }&groupId=${banner.url}" /></c:if>
					<c:if test="${'UR0003' == banner.urlType and empty banner.url}"><c:set var="banner_url" value="" /></c:if>
					<c:if test="${banner.showMark == 'Y'}">
						<div class="item <c:if test="${index == 0 }"> active </c:if>">
							<a href="${banner_url}">
								<img src="<c:url value="/uploaded/pagebanner/${banner.bannerImgPath}"/>" onerror="javascript:this.src='/images/sc.png'">			
							</a>
						</div>
						<c:set var="index" value="1"></c:set>
					</c:if>
				</c:forEach>
			</div>

			<!-- Controls -->
			<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
			<a class="right carousel-control" href="#carousel-example-generic" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
		</div>
	</div>
	</c:if>
	
	<div class="panel-body font-14">
	<form name="qtyFrm" id="qtyFrm" class="bg-white" method="post">
		<c:set var="col_num" value="0"/>
		<c:set var="row_num" value="0"/>
		<c:set var="isShow" value="0"/>
		<c:set var="item_record" value="0"/>
		<c:forEach var="detail" items="${detailList}">
				<c:set var="isShow" value="1"/>
				<c:if test="${(row_num != detail.rowNum && detail.colNum != 0 && row_num != 0) || (col_num != detail.colNum && col_num != 0)}"></div></c:if>
				<c:if test="${row_num != detail.rowNum && row_num != 0}"></div></div></c:if>
				
				<c:if test="${row_num != detail.rowNum}">
					<c:set var="total_width" value="0" />
					<div class="row"><div class="table block-table bg-white">
				</c:if>
					<c:if test="${(row_num != detail.rowNum && detail.colNum != 0) || col_num != detail.colNum}">
						<c:set var="widthPc" value="10"/>
				
						<c:if test="${not empty detail.widthPc}">
							<c:set var="widthPc" value="${detail.widthPc }"/>
						</c:if>
						<c:set var="total_width" value="${total_width + widthPc }" />
						<div class="block-col pd5 <c:if test="${total_width > 100 }">clear</c:if>" style="width:${widthPc}%">
						<c:if test="${total_width > 100 }">
							<c:set var="total_width" value="${widthPc}"/>
						</c:if>
					</c:if>
						
						<c:if test="${'UR0001' == detail.urlType }"><c:set var="url" value="" /></c:if>
						<c:if test="${'UR0002' == detail.urlType }"><c:set var="url" value="${detail.url}" /></c:if>
						<c:if test="${'UR0003' == detail.urlType and not empty detail.url}"><c:set var="url" value="UserPage.do?cmd=viewItemGroup&hostUserId=${hostUser.userId }&groupId=${detail.url}" /></c:if>
						<c:if test="${'UR0003' == detail.urlType and empty detail.url}"><c:set var="url" value="" /></c:if>
						<c:if test="${'PI0002' eq detail.detailType}">
							<!-- check machine : pc / desktop -->
							<c:if test="${'Y' != detail.mobOnly }">
								<div class="pd10" style="border:1px solid #${detail.bdColor}; background-color:#${detail.bgColor};">
									<c:if test="${'Y' == detail.liMark }"><li style="list-style:outside"></c:if>
										<c:if test="${'' != url }"><a href="${url}"></c:if>
											<span style="color:#${detail.fontColor};">
												<c:if test="${'3' eq detail.fontSize}"><span class="font-14">${detail.fontNote}</span></c:if>
												<c:if test="${'2' eq detail.fontSize}"><span class="font-12">${detail.fontNote}</span></c:if>
												<c:if test="${'4' eq detail.fontSize}"><span class="font-16">${detail.fontNote}</span></c:if>
											</span>
										<c:if test="${'' != url }"></a></c:if>
	                  				<c:if test="${'Y' == detail.liMark }"></li></c:if>
	               				</div>
	               			</c:if>
						</c:if>

						<c:if test="${'PI0003' eq detail.detailType}">
							<div class="text-center" style="border:1px solid #${detail.picBdColor}">
								<c:if test="${'' != url }"><a href="${url}"></c:if>
									<img width="100%" border="0" src="/uploaded/pagedetail/${detail.detailImgPath}" onerror="javascript:this.src='/images/sc.png'">
									<c:if test="${'' != detail.picNote}">
										<p class="font-12">${detail.picNote}</p>
									</c:if>
								<c:if test="${'' != url }"></a></c:if>
              				</div>
						</c:if>

						<c:if test="${'PI0004' eq detail.detailType}">
							<c:if test="${not empty detail.userItem }">
								<div class="text-center mgb10" style="border:1px solid #${detail.bdColor}">
								
								<c:choose>
									<c:when test="${'Y' eq itemDetailShow}">
										<a href="<c:if test="${not empty url}">${url}</c:if><c:if test="${empty url}">UserItem.do?cmd=viewItemPage&hostUserId=${hostUser.userId}&itemId=${detail.userItem.itemId}</c:if>" target="_blank">
											<img width="100%" border="0" src="/uploaded/useritem/${detail.userItem.itemImgPath}" onerror="javascript:this.src='/images/rm.jpg'">
										</a>										
									</c:when>
									<c:otherwise>										
										<c:if test="${not empty detail.userItem.itemImgPath}">
										<a href="/uploaded/useritem/${detail.userItem.itemImgPath}" class="fancybox">
												<img width="100%" border="0" src="/uploaded/useritem/${detail.userItem.itemImgPath}" />
											</a>
										</c:if>
										<c:if test="${empty detail.userItem.itemImgPath}">
											<img width="100%" border="0" src="/images/rm.jpg">
										</c:if>
									</c:otherwise>
								</c:choose>
									<div class="mgb10">
						            	${detail.userItem.itemName}
						            </div>
									<div class="row pd5">
										<div class="pull-left col-xs-5 color-red text-left">
					         				<span color="#FF0000">￥<c:if test='${not empty detail.userItem.itemPrice}'>${detail.userItem.itemPrice}</c:if><c:if test='${empty detail.userItem.itemPrice}'>0</c:if><c:if test='${not empty detail.userItem.itemUnit}'>/${detail.userItem.itemUnit}</c:if></span>
					         			</div>
					         			<div class="pull-right col-xs-6 text-right">					            			
					            			<a class="btn btn-xs btn-danger" onclick="qtyControl(this, '${detail.userItem.itemId}', -1);"><i class="fa fa-minus"></i></a>					            			
					            			<c:choose>
												<c:when test="${detail.userItem.cartQty != null}">
													<input class="qty alignC form-control" type="text" name="qty" value="${detail.userItem.cartQty}" id="qty_${detail.userItem.itemId}" onchange="qtyChanged(this, '${detail.userItem.itemId}');">
												</c:when>
												<c:otherwise>
													<input class="qty alignC form-control" type="text" name="qty" value="0" id="qty_${detail.userItem.itemId}" onchange="qtyChanged(this, '${detail.userItem.itemId}');">
												</c:otherwise>
											</c:choose>					            									             		
						             		<a class="btn btn-xs btn-success" onclick="qtyControl(this, '${detail.userItem.itemId}', 1);"><i class="fa fa-plus"></i></a>
					             		</div>
			            			</div>
			            			
			            			<input type="hidden" name="itemId" value="${detail.userItem.itemId}">
									<c:set var="item_record" value="${item_record + 1}"/>
	              				</div>
              				</c:if>
						</c:if>

						<c:if test="${'PI0005' eq detail.detailType}">
							<div class="pd10" style="border:1px solid #${detail.bdColor}; background-color:#${detail.titleFontBgColor}">
								<span style="color:#${detail.titleFontColor};">
									<c:if test="${'3' eq detail.titleFontSize}"><span>${detail.infoType}</span></c:if>
									<c:if test="${'2' eq detail.titleFontSize}"><span class="font-12">${detail.infoType}</span></c:if>
									<c:if test="${'4' eq detail.titleFontSize}"><span class="font-16">${detail.infoType}</span></c:if>
                 				</span>
               				</div>
               				<c:forEach var="infoLink" items="${detail.infoLinkList}">
               					<div class="table pd10 border-bottom-grey">
	               					<c:if test="${empty infoLink.picType}">	               						
               							<a href="<c:if test="${not empty url}">${url}</c:if><c:if test="${empty url}">Info.do?cmd=viewInfoPage&hostUserId=${hostUser.userId}&billId=${infoLink.billId}</c:if>" target="_blank">
											<div class="color-blank">${infoLink.info}</div>
											<div class="font-12 color-grey">
												<span>${infoLink.infoType}</span>
												<span>${infoLink.createDate}</span>
											</div>
										</a>										
	               					</c:if>
	               					<c:if test="${'NI0001' eq infoLink.picType}">
	               						<div class="block-col">	               							
	               							<c:if test="${not empty infoLink.picPath[0]}">
												<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[0]}" onerror="javascript:this.src='/images/rm.jpg'">
											</c:if>
	               						</div>
	               						<div class="block-col pdl10">               							
	               							<a href="<c:if test="${not empty url}">${url}</c:if><c:if test="${empty url}">Info.do?cmd=viewInfoPage&hostUserId=${hostUser.userId}&billId=${infoLink.billId}</c:if>" target="_blank">
												<div class="color-blank">${infoLink.info}</div>
												<div class="font-12 color-grey">
													<span>${infoLink.infoType}</span>
													<span>${infoLink.createDate}</span>
												</div>
											</a>
										</div>
	               					</c:if>
	               					<c:if test="${'NI0002' eq infoLink.picType}">	               						
	               						<div class="block-col">               							
	               							<a href="<c:if test="${not empty url}">${url}</c:if><c:if test="${empty url}">Info.do?cmd=viewInfoPage&hostUserId=${hostUser.userId}&billId=${infoLink.billId}</c:if>" target="_blank">
												<div class="color-blank">${infoLink.info}</div>
												<div class="font-12 color-grey">
													<span>${infoLink.infoType}</span>
													<span>${infoLink.createDate}</span>
												</div>
											</a>
										</div>
										<div class="block-col pdl10">
	               							<c:if test="${not empty infoLink.picPath[0]}">
												<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[0]}" onerror="javascript:this.src='/images/rm.jpg'">
											</c:if>
	               						</div>
	               					</c:if>
	               					<c:if test="${'NI0003' eq infoLink.picType}">	               						
	               						<div>               							
	               							<a href="<c:if test="${not empty url}">${url}</c:if><c:if test="${empty url}">Info.do?cmd=viewInfoPage&hostUserId=${hostUser.userId}&billId=${infoLink.billId}</c:if>" target="_blank">
												<div class="color-blank">${infoLink.info}</div>
												<div class="font-12 color-grey">
													<span>${infoLink.infoType}</span>
													<span>${infoLink.createDate}</span>
												</div>
											</a>
										</div>
										<div class="pdt10">
	               							<c:if test="${not empty infoLink.picPath[0]}">
												<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[0]}" onerror="javascript:this.src='/images/rm.jpg'">
											</c:if>
	               						</div>
	               					</c:if>
	               					<c:if test="${'NI0004' eq infoLink.picType}">	               						
	               						<div>
	               							<a href="<c:if test="${not empty url}">${url}</c:if><c:if test="${empty url}">Info.do?cmd=viewInfoPage&hostUserId=${hostUser.userId}&billId=${infoLink.billId}</c:if>" target="_blank">
												<div class="color-blank">${infoLink.info}</div>
												<div class="font-12 color-grey">
													<span>${infoLink.infoType}</span>
													<span>${infoLink.createDate}</span>
												</div>
											</a>
										</div>
										<div class="table pdt10">
											<div class="block-col">
												<c:if test="${not empty infoLink.picPath[0]}">
													<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[0]}" onerror="javascript:this.src='/images/rm.jpg'">
												</c:if>
											</div>
											<div class="block-col">
												<c:if test="${not empty infoLink.picPath[1]}">
													<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[1]}" onerror="javascript:this.src='/images/rm.jpg'">
												</c:if>
											</div>
										</div>
	               					</c:if>
	               					<c:if test="${'NI0005' eq infoLink.picType}">
	               						<div>
	               							<a href="<c:if test="${not empty url}">${url}</c:if><c:if test="${empty url}">Info.do?cmd=viewInfoPage&hostUserId=${hostUser.userId}&billId=${infoLink.billId}</c:if>" target="_blank">
												<div class="color-blank">${infoLink.info}</div>
												<div class="font-12 color-grey">
													<span>${infoLink.infoType}</span>
													<span>${infoLink.createDate}</span>
												</div>
											</a>
										</div>
										<div class="table pdt10">
											<div class="block-col">
												<c:if test="${not empty infoLink.picPath[0]}">
													<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[0]}" onerror="javascript:this.src='/images/rm.jpg'">
												</c:if>
											</div>
											<div class="block-col">
												<c:if test="${not empty infoLink.picPath[1]}">
													<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[1]}" onerror="javascript:this.src='/images/rm.jpg'">
												</c:if>
											</div>
											<div class="block-col">
												<c:if test="${not empty infoLink.picPath[2]}">
													<img height="100" border="0" src="/uploaded/info_pic/${infoLink.picPath[2]}" onerror="javascript:this.src='/images/rm.jpg'">
												</c:if>
											</div>
										</div>		
	               					</c:if>
               					</div>
               				</c:forEach>
						</c:if>
			<c:set var="row_num" value="${detail.rowNum}"/>
			<c:set var="col_num" value="${detail.colNum}"/>
		</c:forEach>
		<c:if test="${isShow == 1}"></div></div></div></c:if>
	</form>
	</div>
	
	<div id="comment" class="pdb20">
	<%@ include file="/WEB-INF/jsp/userPage/comment.jsp"%>
	</div>
	
	<input type="hidden" id="hostUserId" name="hostUserId" value="${hostUser.userId}"/>
	<input type="hidden" id="cookieShopCart" name="cookieShopCart" value="${cookieShopCart}"/>
	
</div>
