<%@page import="java.util.Map"%>
<%@page import="com.kpc.eos.model.dataMng.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<jsp:include page="topInner.jsp" />

<script type="text/javascript">
	<c:if test="${not empty sc && not empty sc.page}">
	gridPager = {
		page : "${sc.page.page}",
		sortname : "${sc.page.sidx}",
		sortorder : "${sc.page.sord}",
		rowNum : "${sc.page.rows}"
	};
	</c:if>
</script>
<div class="header_wrapper">
	<div class="container_header">
		<div class="container">
			<div id="header">
				<div class="gnb">
					<div class="gnb_head">
						<div class="gnb_logo">
							<a href="UserPage.do?cmd=mainPage&hostUserId=${loginUser.userId}">
								<c:if test="${not empty loginUser.logoImgPath}">
								<img src="<c:url value="/uploaded/userlogo/${loginUser.logoImgPath}"/>" onerror="javascript:this.src='/images/sunpoto_logo.png'" height="60px" />
								</c:if>
								<c:if test="${empty loginUser.logoImgPath}">
								<img src="<c:url value="/images/sunpoto_logo.png" />" height="60px" />
								</c:if>
							</a>
						</div>
						<nav id="menu-wrap" class="right_action"> 
							<ul id="menu" class="main-menu">
								<li><a href="Main.do?cmd=main">首页</a></li>
								<qc:menuList var="menuList" />
								<c:forEach var="menu" items="${menuList}">
								<li>
									<a href="<c:if test="${empty menu.subMenus}">${menu.connUrl}</c:if>" <c:if test="${not empty menu.subMenus}">onclick="return false;"</c:if>>${menu.menuName}</a>
									<c:if test="${not empty menu.subMenus}">
										<ul>
										<c:forEach var="subMenu" items="${menu.subMenus}" varStatus="status">
											<li><a id="subA_${subMenu.seqNo}" href="${subMenu.connUrl}">${subMenu.menuName}</a>
											<c:if test="${not empty subMenu.subMenus}">
												<ul>
												<c:forEach var="sub2Menu" items="${subMenu.subMenus}" varStatus="status">
												<li><a href="${sub2Menu.connUrl}&mId=${sub2Menu.menuId}">${sub2Menu.menuName}</a></li>
												</c:forEach>
												</ul>
											</c:if>
											</li>
										</c:forEach>
										</ul>
									</c:if>
								</li>
								</c:forEach>
								<li><a href="#" onclick="logout(); return false;">退出</a></li>
							</ul>
						</nav>
						<!-- div class="right_action">
						</div-->
					</div>
				</div>
			</div>
		</div>
	</div>
		

</div>
