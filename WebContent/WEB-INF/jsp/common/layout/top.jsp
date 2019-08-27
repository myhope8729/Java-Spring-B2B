<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script type="text/javascript" src="<c:url value="/js/common/menu.js"/>"></script>

<div class="header_wrapper">
	<div class="container_header">
		<div class="container">
			<div id="header">
				<div class="gnb">
					<div class="gnb_head">
						<div class="gnb_logo">
							<a href="Main.do?cmd=main">
								<img src="<c:url value="/images/sunpoto_logo.png"/>" height="60px" title="" alt="" />
							</a>
						</div>
						<div class="gnb_description">
							<p>商务百事通，生意好帮手</p>
							<p class="sm_desc">中小企业信息化一站式服务</p>
						</div>
						<div class="gnb_search">
							<form name="searchFrm" id="searchFrm" method="post" action="Search.do?cmd=search">
								<div class="gnb_search_wrapper">
									<input type="text" class="input" size="30" maxlength="30" name="searchStr" value=""/>
									<input type="submit" class="button_search" value="搜索" />
								</div>
							</form>
						</div>
						<nav id="menu-wrap" class="right_action"> 
							<ul id="menu">
								<li><a href="Login.do?cmd=register">注册</a></li>
								<li><a href="#">申请使用</a></li>
								<li><a href="Login.do?cmd=loginForm">请登录</a></li>
								<li><a href="#">诚征代理</a></li>
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
