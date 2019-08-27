<%@page import="com.kpc.eos.model.dataMng.UserModel, com.kpc.eos.core.Constants"%>
<%@page import="com.kpc.eos.core.util.SessionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<jsp:useBean id="loginUser" scope="request" class="com.kpc.eos.model.dataMng.UserModel" />

<!-- If a user loged into the system, redraw the left side bar for the user's menu   -->
<c:if test="${not empty loginUser.userId}">
	
	<%
		String userName = loginUser.getUserName();
		if(userName.length() > 15) {
			userName = userName.substring(0,15) + "...";
		}
	
	%>
	
	<div data-role="panel" id="left-panel" data-display="overlay" data-theme="a">
	
	    <ul data-role="listview" data-split-icon="power" data-theme="a" data-split-theme="a">
	    	<li data-icon="user"><a href="javascript:void(0);"><%=userName%></a></li>
	    	<li>
	    	<a href="UserPage.do?cmd=mainPage&hostUserId=${loginUser.userId}" style="padding:5px 5px 5px 90px;">
	    			<c:if test="${not empty loginUser.logoImgPath}">
					<img src="<c:url value="/uploaded/userlogo/${loginUser.logoImgPath}"/>" style="left:2px;"/>
					</c:if>
					<c:if test="${empty loginUser.logoImgPath}">
					<img src="<c:url value="/images/sunpoto_logo_mo.png" />" style="left:2px;width:80px;" />
					</c:if>
		        	<h2>${loginUser.empName}</h2>
		        	<p>${loginUser.empNo}</p>
		        	<a href="Login.do?cmd=logout">LogOut</a>
	        	</a>
	        </li>    		
	    	<li data-role="list-divider" class="alignC">菜单</li>
	    	<%--<li data-icon="home"><a href='<c:if test="${empty loginUser}">UserPage.do?cmd=mainPage&hostUserId=<%=request.getParameter("hostUserId") %></c:if><c:if test="${not empty loginUser }">UserPage.do?cmd=mainPage&hostUserId=${loginUser.userId }</c:if>'>首页</a></li> --%>
	    </ul>
	
		<div data-role="collapsible-set" data-inset="false" data-iconpos="right" data-theme="a" data-content-theme="a">
	        <qc:menuList var="menuList" />
			<c:forEach var="menu" items="${menuList}">
			
				<c:if test="${empty menu.subMenus && not empty menu.connUrl}">
				<div data-role="collapsible" data-theme="b" data-iconpos="left" data-inset="false" data-collapsed="false" data-collapsed-icon="carat-r" data-expanded-icon="carat-d">
					<h3>${menu.menuName}</h3>
		          	<ul data-role="listview" data-theme="a" data-inset="false">
		          	
		          	 <c:if test="${menu.menuId != currentMenu}">
		          		<li><a href="${menu.connUrl}">${menu.menuName}</a></li>
		          	</c:if>
		          	<c:if test="${menu.menuId == currentMenu}">
			    		 	<li><a class="ui-btn ui-btn-icon-right ui-icon-navigation ui-focus sidebar-action" href="${menu.connUrl}" >
			    		 		<img src="<c:url value="/css/mobile/jqGridMobile/default/images/icons-png/navigation-black.png"/>" alt="active" class="ui-li-icon">${menu.menuName}
			    		 	</a></li>
			    	</c:if>
		          	</ul>        	
		        </div>
		        </c:if>
		        
		        <c:if test="${not empty menu.subMenus}">
		        	<c:set var="selectedYn" value="N"></c:set>
		        	<c:forEach var="subMenu1" items="${menu.subMenus}" varStatus="status1">
		        		 <c:if test="${subMenu1.menuId == currentMenu}">
		        		 	<c:set var="selectedYn" value="Y"></c:set>
		        		 </c:if>
		        	</c:forEach>
	       
	       		 	<c:if test="${selectedYn == 'Y'}">
	       		 		<div data-role="collapsible" data-theme="b" data-iconpos="left" data-inset="false" data-collapsed="false" data-collapsed-icon="carat-r" data-expanded-icon="carat-d">
	       		 	</c:if>	   
	       		 	
	       		 	<c:if test="${selectedYn != 'Y'}">
	       		 		<div data-role="collapsible" data-theme="b" data-iconpos="left" data-inset="false" data-collapsed="true" data-collapsed-icon="carat-r" data-expanded-icon="carat-d">
	       		 	</c:if>	         
	       		 	 		 	     	
					         <h3>${menu.menuName}</h3>
					         <ul data-role="listview">
					          
					    	 <c:forEach var="subMenu" items="${menu.subMenus}" varStatus="status">
					    		 <c:if test="${subMenu.menuId == currentMenu}">
					    		 	<li><a class="ui-btn ui-btn-icon-right ui-icon-navigation ui-focus sidebar-action" id="subA_${subMenu.menuId}" href="${subMenu.connUrl}" >
					    		 		<img src="<c:url value="/css/mobile/jqGridMobile/default/images/icons-png/navigation-black.png"/>" alt="active" class="ui-li-icon">${subMenu.menuName}
					    		 	</a></li>
					    		 </c:if>
					             <c:if test="${subMenu.menuId != currentMenu}">
					             	<li data-theme="a"><a id="subA_${subMenu.menuId}" href="${subMenu.connUrl}"><i class="${subMenu.menuIcon}"></i>&nbsp;&nbsp;${subMenu.menuName}</a></li>
					             </c:if>
					         </c:forEach>
					            
					          </ul>
			        	</div><!-- /collapsible -->
				</c:if>
			</c:forEach>
		</div>
	</div><!-- /panel -->

<script>

/* 20180217 by rmh to show always the seleted menu */
var scrHeight = $(window).height();

$( "#left-panel" ).panel({
	beforeopen: function( event, ui ) {
		var active_obj = $("a.sidebar-action");
		if(active_obj.offset()) {
			var activeHeight = active_obj.offset().top + 100;
			if(activeHeight-scrHeight>0) $.mobile.silentScroll(activeHeight-scrHeight);
			
		}

	}
});
</script>

</c:if> <!-- when a user logged in to-->


