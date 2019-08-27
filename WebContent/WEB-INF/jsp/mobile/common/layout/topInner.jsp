<%@page import="java.util.Map"%>
<%@page import="com.kpc.eos.model.dataMng.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>


<c:if test="${not empty loginUser}">
<jsp:useBean id="loginUser" class="com.kpc.eos.model.dataMng.UserModel" scope="request"/>
<%  
	Map colors = loginUser.getColors();
	request.setAttribute("colors", colors);
%>
<c:if test='${not (empty colors or colors.bk_full eq "FFFFFF")}'>
<style>
	body{background-color: #${colors.bk_full}}
</style>
</c:if>
<c:if test='${not (empty colors or colors.bk eq "f9f9f9")}'>
<style>
/* ------- Layout ------- */
.container_header {
	border-top-color: #${colors.bk};
}
.ui-header.ui-bar-b {
	color: #FFF;
	background: #${colors.bk};
	border-color: #${colors.bk};
}

h3.page_title {
	color: #FFF;
}

.input-daterange .input-group-addon {
	border-color: #${colors.bk};
}


/* ------- text color setting ------- */
.footer_wrapper p a.orangeLink {
	color: #${colors.bk};
}
/* ------- btn color Setting ------- */
html head + body .ui-btn.ui-btn-b, html .ui-header.ui-bar-b .ui-btn
{
	background-color:#${colors.bk} !important; /* #337ab7; */
	border-color: #${colors.border}!important; /*#2e6da4;  /* -50D13 */
}

.btn-primary:focus,.btn-primary.focus {
	background-color: #${colors.bk_f};  /* -b1a27 */
	border-color: #${colors.border_f};		/* -214f77 */
}

.btn-primary:hover {
	background-color: #${colors.bk_f}; /* -b1a27 */
	border-color: #${colors.border_h};		/* -132d43 */
}

.btn-primary:active:hover,
.btn-primary.active:hover,
.open > .dropdown-toggle.btn-primary:hover,
.btn-primary:active:focus,
.btn-primary.active:focus,
.open > .dropdown-toggle.btn-primary:focus,
.btn-primary:active.focus,
.btn-primary.active.focus,
.open > .dropdown-toggle.btn-primary.focus,
.btn-primary:active,.btn-primary.active,.open>.dropdown-toggle.btn-primary
	{
	background-color: #${colors.bk_f}; /* -b1a27 */
	border-color: #${colors.border_h};		/* 1c4264 */
}
/* ------- form control setting ------- */
.form-control {
	border-color: #${colors.border};
}
.form-control:focus {
	border-color: #${colors.border_f};
	/* -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6);
    box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, .6); */
}

/* ---------- user page ------------- */
.cat-title, .store-menu a.active, 
.cat-wrap-upper ul li.active,
.cat-wrap ul li.active,
.title-bg{
	background-color:#${colors.bk}; 
}
.store-item-title{color:#${colors.bk} }

.p-shopcart .title-bg .ui-btn.ui-btn-b{
	border-color: #fff !important;
}
.cat-wrap-upper{
	border-bottom: 1px solid #${colors.bk};
}
.p-left{border-right: 1px solid #${colors.bk};} 
.p-right{background: #fff;}
.store-item-info{background: #fff;}
.store-item {border-bottom: 1px solid #${colors.bk};}
#cartWrap .btn-success{
	background-color: #ee0000;
	border-color: #dd0000;
	
}
#cartWrap .btn-danger{
	background-color: #c2c2c2;
	border-color: #c2c2c2;
}
</style>
</c:if>
</c:if>