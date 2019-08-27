<%@page import="com.kpc.eos.model.common.SysMsg" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div id="footer" data-role="footer" data-position="fixed"  data-theme="b" visibleOnPageShow="true" data-tap-toggle="false">
	<!-- div class="toggleMenu"><i class="fa fa-chevron-up"></i></div-->
	<div data-role="navbar" data-iconpos="bottom">
<c:if test="${not empty loginUser }">
	<c:if test="${ 'UK0001' eq loginUser.userKind}">
		<ul>
			<li>
				<a href='UserPage.do?cmd=itemType&hostUserId=<c:if test="${loginUser.userId != hostUser.userId}">${hostUser.userId}</c:if>' id="mItems" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text"><qc:message code="up.product_list"/></span>
						<span class="nav-icon"><i class="fa fa-list"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href="UserPage.do?cmd=shopCart&hostUserId=<c:if test="${loginUser.userId != hostUser.userId}">${hostUser.userId}</c:if>" id="mCart" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text"><qc:message code="system.common.btn.cart"/><span id="shopCart" class="badge pull-right" val="${shopCartNum}"><c:if test='${not empty shopCartNum && shopCartNum != "0"}'>${shopCartNum}</c:if></span></span>
						<span class="nav-icon"><i class="fa fa-shopping-cart"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href='UserPage.do?cmd=myMain' class="ui-btn ui-btn-icon-bottom" data-inline="true" id="mHome"  >
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							<qc:message code="system.common.btn.mine"/>
						</span>
						<span class="nav-icon"><i class="fa fa-home"></i></span>
					</span>
				</a>
			</li>
		</ul>
	</c:if>
	<c:if test="${ 'UK0002' eq loginUser.userKind}">
		<ul>
			<li>
				<a href='UserPage.do?cmd=mainPage&hostUserId=${loginUser.userId }' class="ui-btn ui-btn-icon-bottom" id="mMain" >
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							<qc:message code="system.common.btn.home"/>
						</span>
						<span class="nav-icon"><i class="fa fa-home"></i></span>
					</span>
				</a>
			</li>
			<li class="dropdown">
				<a href="#" class="ui-btn ui-btn-icon-bottom dropdown-toggle">
					<span class="ui-btn-inner">
						<span class="ui-btn-text">单据处理</span>
						<span class="nav-icon"><i class="fa fa-file-o"></i></span>
					</span>
				</a>
				<ul class="submenu menu-closed">
					<li>
						<a href="BillProc.do?cmd=billProcUncheckedList" class="ui-btn ui-btn-icon-left">
							<span class="ui-btn-inner">
								<span class="ui-btn-text">待处理单据</span>
								<span class="nav-icon"><i class="fa fa-square-o"></i></span>
							</span>
						</a>
					</li>
					<li>
						<a href="BillProc.do?cmd=billProcCheckedList" class="ui-btn ui-btn-icon-left">
							<span class="ui-btn-inner">
								<span class="ui-btn-text">已处理单据</span>
								<span class="nav-icon"><i class="fa fa-check-square-o"></i></span>
							</span>
						</a>
					</li>
				</ul>
			</li>
			<li>
				<a href='UserPage.do?cmd=myMain' class="ui-btn ui-btn-icon-bottom" data-inline="true" id="mHome"  >
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							<qc:message code="system.common.btn.mine"/>
						</span>
						<span class="nav-icon"><i class="fa fa-home"></i></span>
					</span>
				</a>
			</li>
		</ul>
	</c:if>
	<c:if test="${ 'UK0003' eq loginUser.userKind}">
		<ul>
			<li>
				<a href='UserPage.do?cmd=mainPage&hostUserId=${loginUser.userId }' class="ui-btn ui-btn-icon-bottom" id="mMain" >
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							<qc:message code="system.common.btn.home"/>
						</span>
						<span class="nav-icon"><i class="fa fa-home"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href='UserPage.do?cmd=itemType&hostUserId=<c:if test="${loginUser.userId != hostUser.userId}">${hostUser.userId}</c:if>' id="mItems" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text"><qc:message code="up.product_list"/></span>
						<span class="nav-icon"><i class="fa fa-list"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href="UserPage.do?cmd=shopCart&hostUserId=<c:if test="${loginUser.userId != hostUser.userId}">${hostUser.userId}</c:if>" id="mCart" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text"><qc:message code="system.common.btn.cart"/><span id="shopCart" class="badge pull-right" val="${shopCartNum}"><c:if test='${not empty shopCartNum && shopCartNum != "0"}'>${shopCartNum}</c:if></span></span>
						<span class="nav-icon"><i class="fa fa-shopping-cart"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href='UserPage.do?cmd=myMain' class="ui-btn ui-btn-icon-bottom" data-inline="true" id="mHome"  >
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							<qc:message code="system.common.btn.mine"/>
						</span>
						<span class="nav-icon"><i class="fa fa-home"></i></span>
					</span>
				</a>
			</li>
		</ul>
	</c:if>
	<c:if test="${ 'UK0004' eq loginUser.userKind}">
		<ul>
			<li>
				<a href='Item.do?cmd=itemList' class="ui-btn ui-btn-icon-bottom" id="mMain" >
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							商品管理
						</span>
						<span class="nav-icon"><i class="fa fa-truck"></i></span>
					</span>
				</a>
			</li>
			<li class="dropdown">
				<a href="#" class="ui-btn ui-btn-icon-bottom dropdown-toggle">
					<span class="ui-btn-inner">
						<span class="ui-btn-text">系统资料</span>
						<span class="nav-icon"><i class="fa fa-gears"></i></span>
					</span>
				</a>
				<ul class="submenu menu-closed">
					<li>
						<a href="Code.do?cmd=codeList" class="ui-btn ui-btn-icon-left">
							<span class="ui-btn-inner">
								<span class="ui-btn-text">编码资料</span>
								<span class="nav-icon"><i class="fa fa-gear"></i></span>
							</span>
						</a>
					</li>
					<li>
						<a href="Menu.do?cmd=menuList" class="ui-btn ui-btn-icon-left">
							<span class="ui-btn-inner">
								<span class="ui-btn-text">菜单资料</span>
								<span class="nav-icon"><i class="fa fa-gear"></i></span>
							</span>
						</a>
					</li>
					<li>
						<a href="ActionUrl.do?cmd=actionUrlList" class="ui-btn ui-btn-icon-left">
							<span class="ui-btn-inner">
								<span class="ui-btn-text">作用URL</span>
								<span class="nav-icon"><i class="fa fa-gear"></i></span>
							</span>
						</a>
					</li>
					<li>
						<a href="User.do?cmd=changePwdForm" id="mCart" class="ui-btn ui-btn-icon-left">
							<span class="ui-btn-inner">
								<span class="ui-btn-text">修改密码</span>
								<span class="nav-icon"><i class="fa fa-key"></i></span>
							</span>
						</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="Login.do?cmd=logout" id="mCart" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text">退出</span>
						<span class="nav-icon"><i class="fa fa-power-off"></i></span>
					</span>
				</a>
			</li>
		</ul>
	</c:if>
</c:if>
	<c:if test="${empty loginUser }">
		<ul>
			<li>
				<a href='UserPage.do?cmd=mainPage&hostUserId=<%=request.getParameter("hostUserId") %>' data-inline="true" id="mHome" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							<qc:message code="system.common.btn.home"/>
						</span>
						<span class="nav-icon"><i class="fa fa-navicon"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href="UserPage.do?cmd=itemType&hostUserId=${hostUser.userId}" id="mItems" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text"><qc:message code="up.product_list"/></span>
						<span class="nav-icon"><i class="fa fa-list"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href="UserPage.do?cmd=shopCart&hostUserId=${hostUser.userId}" id="mCart" class="ui-btn ui-btn-icon-bottom">
					<span class="ui-btn-inner">
						<span class="ui-btn-text"><qc:message code="system.common.btn.cart"/>&nbsp;<span id="shopCart" val="${shopCartNum}"><c:if test='${not empty shopCartNum && shopCartNum != "0"}'> (${shopCartNum})</c:if></span></span>
						<span class="nav-icon"><i class="fa fa-shopping-cart"></i></span>
					</span>
				</a>
			</li>
			<li>
				<a href='Login.do?cmd=loginForm' class="ui-btn ui-btn-icon-bottom" id="mMain" >
					<span class="ui-btn-inner">
						<span class="ui-btn-text">
							<qc:message code="system.common.btn.mine"/>
						</span>
						<span class="nav-icon"><i class="fa fa-home"></i></span>
					</span>
				</a>
			</li>
		</ul>
	</c:if>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(".dropdown .dropdown-toggle").click(function(e){
			$(this).closest('.dropdown').find('.submenu').toggleClass('menu-closed').toggleClass('menu-opened');
		});
	});
</script>
<div id="alert_message" class="hidden"><%= SysMsg.flashMsg(request) %></div>