<%@page import="java.util.Map"%>
<%@page import="com.kpc.eos.model.dataMng.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<c:if test="${not empty hostUser}">
	<jsp:useBean id="hostUser" class="com.kpc.eos.model.dataMng.UserModel" scope="request"/>
</c:if>

<div data-role="header" data-fullscreen="true" data-theme="b" style="height:70px" class="ui-header-margin">
	<span class="ui-btn-right pdt5">
		<c:if test="${not empty loginUser.logoImgPath}">
			<img src="<c:url value="/uploaded/userlogo/${loginUser.logoImgPath}"/>" onerror="javascript:this.src='/images/sunpoto_logo.png'" style="right:2px;height:50px;max-width:120px;" />
		</c:if>
		<c:if test="${empty loginUser.logoImgPath}">
			<img src="<c:url value="/images/sunpoto_logo.png" />" style="height:50px;max-width:120px;" />
		</c:if>
	</span>
    <span class="color-white mgl20 mgt15" style="display:block;text-overflow:ellipsis;width:60%;white-space:nowrap;overflow:hidden;">
	  		<b>${loginUser.userName}</b>  
	  		<br><font style="font-weight:normal;font-size:13px;">欢迎您！&nbsp;${loginUser.empName}&nbsp;${loginUser.empNo}</font>
    </span>
</div>
	
<div class="admin_body" style="background-color:#f3f3f3">
	<!-- div class="media myMain-header">
		<a class="pull-left" href="UserPage.do?cmd=mainPage&hostUserId=${loginUser.userId}">
			<c:if test="${not empty loginUser.logoImgPath}">
				<img class="media-object" src="<c:url value="/uploaded/userlogo/${loginUser.logoImgPath}"/>" style="width:80px;">
			</c:if>
			<c:if test="${empty loginUser.logoImgPath}">
				<img class="media-object" src="<c:url value="/images/sunpoto_logo_mo.png" />" style="width:80px;">
			</c:if>
		</a>
		<div class="media-body">
			<h4 class="media-heading">${loginUser.empName}</h4>
			<p>${loginUser.empNo}</p>
		</div>
	</div-->
	<qc:menuList var="menuList" />
	<c:if test="${ 'UK0001' eq loginUser.userKind}">
		<c:forEach var="menu" items="${menuList}">
			<c:if test="${('MENU00000000001' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"/>
				<c:set var="cnt" value="1"/>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${'MENU00000000002' eq subMenu.menuId}">
						<c:if test="${isFirst eq 1}">
	<div class="menu-block">
						</c:if>
		<div class="menu-icon col-xs-3 <c:if test="${cnt > 4 }">mgt20</c:if>">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>我的订单</p>
			</a>
		</div>
						<c:set var="isFirst" value="0"></c:set>
						<c:set var="cnt" value="${cnt + 1 }"/>
					</c:if>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
			<c:if test="${('MENU00000000012' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:set var="cnt" value="1"/>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3 <c:if test="${cnt > 4 }">mgt20</c:if>">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>
					<c:if test="${'MENU00000000013' eq subMenu.menuId}">我的资料</c:if>
					<c:if test="${'MENU00000000013' != subMenu.menuId}">${subMenu.menuName}</c:if>
				</p>
			</a>
		</div>
					<c:set var="isFirst" value="0"></c:set>
					<c:set var="cnt" value="${cnt + 1 }"/>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
			<c:if test="${('MENU00000000018' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${('MENU00000000023' eq subMenu.menuId) or ('MENU00000000024' eq subMenu.menuId)}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>${subMenu.menuName}</p>
			</a>
		</div>
					<c:set var="isFirst" value="0"></c:set>
					</c:if>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${ 'UK0002' eq loginUser.userKind}">
		<c:forEach var="menu" items="${menuList}">
			<%-- <c:if test="${('MENU00000000001' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:set var="cnt" value="1"/>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3 <c:if test="${cnt > 4 }">mgt20</c:if>">
			<a href="${subMenu.connUrl}">
				<i class="${subMenu.menuIcon} bg-first center-block"></i>
				<p>${subMenu.menuName}</p>
			</a>
		</div>
				<c:set var="isFirst" value="0"></c:set>
				<c:set var="cnt" value="${cnt + 1 }"/>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div>
				</c:if>
			</c:if> --%>
			<c:if test="${('MENU00000000012' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:set var="cnt" value="1"/>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3 <c:if test="${cnt > 4 }">mgt20</c:if>">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>
					<c:if test="${'MENU00000000013' eq subMenu.menuId}">我的资料</c:if>
					<c:if test="${'MENU00000000013' != subMenu.menuId}">${subMenu.menuName}</c:if>
				</p>
			</a>
		</div>
					<c:set var="isFirst" value="0"></c:set>
					<c:set var="cnt" value="${cnt + 1 }"/>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
			<c:if test="${('MENU00000000018' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${('MENU00000000022' eq subMenu.menuId) or ('MENU00000000019' eq subMenu.menuId)}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>${subMenu.menuName}</p>
			</a>
		</div>
					<c:set var="isFirst" value="0"></c:set>
					</c:if>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${ 'UK0003' eq loginUser.userKind}">
		<c:forEach var="menu" items="${menuList}">
			<c:if test="${('MENU00000000001' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:set var="cnt" value="1"/>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${'MENU00000000002' eq subMenu.menuId}">
						<c:if test="${isFirst eq 1}">
	<div class="menu-block">
						</c:if>
		<div class="menu-icon col-xs-3 <c:if test="${cnt > 4 }">mgt20</c:if>">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>我的订单</p>
			</a>
		</div>
						<c:set var="isFirst" value="0"></c:set>
						<c:set var="cnt" value="${cnt + 1 }"/>
					</c:if>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
			<c:if test="${('MENU00000000009' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>${subMenu.menuName}</p>
			</a>
		</div>
				<c:set var="isFirst" value="0"></c:set>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
			<c:if test="${('MENU00000000012' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:set var="cnt" value="1"/>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3 <c:if test="${cnt > 4 }">mgt20</c:if>">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>
					<c:if test="${'MENU00000000013' eq subMenu.menuId}">我的资料</c:if>
					<c:if test="${'MENU00000000013' != subMenu.menuId}">${subMenu.menuName}</c:if>
				</p>
			</a>
		</div>
					<c:set var="isFirst" value="0"></c:set>
					<c:set var="cnt" value="${cnt + 1 }"/>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
			<c:if test="${('MENU00000000018' eq menu.menuId) and (not empty menu.subMenus)}">
				<c:set var="isFirst" value="1"></c:set>
				<c:forEach var="subMenu" items="${menu.subMenus}">
					<c:if test="${('MENU00000000022' eq subMenu.menuId) or ('MENU00000000024' eq subMenu.menuId) or ('MENU00000000019' eq subMenu.menuId)}">
					<c:if test="${isFirst eq 1}">
	<div class="menu-block">
					</c:if>
		<div class="menu-icon col-xs-3">
			<a href="${subMenu.connUrl}">
				<img src="<c:if test="${not empty subMenu.menuIcon }"><c:url value="/images/menu/${subMenu.menuIcon }" /></c:if><c:if test="${empty subMenu.menuIcon }"><c:url value="/images/menu/icon_default.png" /></c:if>" class="center-block"/>
				<p>${subMenu.menuName}</p>
			</a>
		</div>
					<c:set var="isFirst" value="0"></c:set>
					</c:if>
				</c:forEach>
				<c:if test="${isFirst eq 0 }">
	</div> <!-- End tag for menu-block -->
				</c:if>
			</c:if>
		</c:forEach>
	</c:if>
	<div class="menu-block">
		<div class="menu-icon col-xs-3">
			<a href="Login.do?cmd=logout">
				<img src="<c:url value="/images/menu/icon_logout.png" />" class="center-block"/>
				<p>退出</p>
			</a>
		</div>
	</div>	
</div>
