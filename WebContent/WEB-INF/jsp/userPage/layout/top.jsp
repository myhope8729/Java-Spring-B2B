<%@page import="com.kpc.eos.core.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<script type="text/javascript" src="<c:url value="/js/userPage/qty.js"/>"></script>
<script>
	var ckEosCart = "<%= Constants.COOKIE_KEY_EOS_CART %>";
	var ckEosCartVal = "${ckEosCartVal}";
	var hostUserId = "<%= request.getParameter("hostUserId") == null? request.getParameter("hostId") : request.getParameter("hostUserId") %>" ;
	var messages = {
		add_to_cart_failed : "<qc:message code='up.cart.add.failed' />",
		number_only : "<qc:message code='system.invalid.number' />",
	};
	$(document).ready(function(){
		setCookie(ckEosCart, ckEosCartVal, 3);
	});
</script>
<jsp:include page="topInner.jsp" />

<div class="header_wrapper">
	<div class="container_header">
		<div class="min_width">
			<div id="header">
				<div class="gnb">
					<div class="gnb_head">
						<div class="gnb_logo">							
							<a href="UserPage.do?cmd=mainPage&hostUserId=${hostUser.userId}">
								<c:if test="${not empty loginUser.logoImgPath}">
								<img src="<c:url value="/uploaded/userlogo/${loginUser.logoImgPath}"/>" onerror="javascript:this.src='/images/sunpoto_logo.png'" height="60px" />
								</c:if>
								<c:if test="${empty loginUser.logoImgPath}">
								<img src="<c:url value="/images/sunpoto_logo.png" />" height="60px" />
								</c:if>
							</a>
						</div>
						<div class="gnb_description">
							<p>${hostUser.topic1}</p>
							<p class="sm_desc">${hostUser.topic2}</p>
						</div>
						
						<nav class="right_action">
							<ul id="menu">
								<qc:menuList var="userMenuList" menuType="USERMENU"/>
								<c:forEach var="userMenu" items="${userMenuList}">
								<li><a  href="${userMenu.menuUrl}">${userMenu.menuName}</a></li>
								</c:forEach>
								<li><a href="UserPage.do?cmd=register&hostUserId=${hostUser.userId}">会员注册</a></li>
								<li><a href="UserPage.do?cmd=itemType&hostUserId=${hostUser.userId}">商品分类</a></li>								
								<c:choose>
									<c:when test="${empty loginUser}">
										<li><a href="Login.do?cmd=loginForm">请登录</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="Main.do?cmd=main">管理中心 </a></li>
										<li><a href="#" onclick="logout(); return false;">退出 </a></li>
									</c:otherwise>
								</c:choose>								
								<li>
									<a href="UserPage.do?cmd=shopCart&hostUserId=${hostUser.userId}">
										购物车 <span id="shopCart" val="${shopCartNum}"><c:if test="${not empty shopCartNum && shopCartNum != '0'}"> (${shopCartNum})</c:if></span>
									</a>
								</li>
							</ul>
						</nav>

					</div>
				</div>
			</div>
		</div>
	</div>

</div>
