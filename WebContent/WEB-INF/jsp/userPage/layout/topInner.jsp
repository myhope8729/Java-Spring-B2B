<%@page import="java.util.Map"%>
<%@page import="com.kpc.eos.model.dataMng.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<c:if test="${not empty hostUser}">
<jsp:useBean id="hostUser" class="com.kpc.eos.model.dataMng.UserModel" scope="request"/>
<%  
	Map colors = hostUser.getColors();
	request.setAttribute("colors", colors);
%>
<c:if test='${not (empty colors or colors.bk_full eq "FFFFFF")}'>
<style>
	body{background-color: #${colors.bk_full}}
/*	.ui-jqgrid .ui-jqgrid-htable thead th {
		background: #${colors.bk_th};
	}
	.table > thead > tr > td.active, .table > tbody > tr > td.active, .table > tfoot > tr > td.active, .table > thead > tr > th.active, .table > tbody > tr > th.active, .table > tfoot > tr > th.active, .table > thead > tr.active > td, .table > tbody > tr.active > td, .table > tfoot > tr.active > td, .table > thead > tr.active > th, .table > tbody > tr.active > th, .table > tfoot > tr.active > th {
		background-color: #${colors.bk_th_h};
	} */
</style>
</c:if>
<c:if test='${not (empty colors or colors.bk eq "FFA51E")}'>
<style>
/* ------- Layout ------- */
.container_header {
	border-top-color: #${colors.bk};
}
div.bread_wrapper {
	color: #FFF;
	background: #${colors.bk};
}
/* ------- Menu ------- */
#menu li:hover > a {
	color: #${colors.bk};
}
#menu ul a:hover {
	background-color: #${colors.bk};
	background-image: -moz-linear-gradient(#${colors.bk},  #${colors.bk});	
	background-image: -webkit-gradient(linear, left top, left bottom, from(#${colors.bk}), to(#${colors.bk}));
	background-image: -webkit-linear-gradient(#${colors.bk}, #${colors.bk});
	background-image: -o-linear-gradient(#${colors.bk}, #${colors.bk});
	background-image: -ms-linear-gradient(#${colors.bk}, #${colors.bk});
	background-image: linear-gradient(#${colors.bk}, #${colors.bk});
	color:#FFF;
}
#menu ul li:first-child a:hover:after {
	border-bottom-color: #${colors.bk}; 
}
#menu ul ul li:first-child a:hover:after {
	border-right-color: #${colors.bk}; 
}
.input-daterange .input-group-addon {
	border-color: #${colors.bk};
}


/* ------- text color setting ------- */
h3.page_title, .footer_wrapper p a.orangeLink {
	color: #${colors.bk};
}
/* ------- btn color Setting ------- */
.btn-primary,
.btn-primary.disabled,
.btn-primary[disabled],
fieldset[disabled] .btn-primary,
.btn-primary.disabled:hover,
.btn-primary[disabled]:hover,
fieldset[disabled] 
.btn-primary:hover,.btn-primary.disabled:focus,.btn-primary[disabled]:focus,fieldset[disabled] .btn-primary:focus,.btn-primary.disabled.focus,.btn-primary[disabled].focus,fieldset[disabled] .btn-primary.focus,.btn-primary.disabled:active,.btn-primary[disabled]:active,fieldset[disabled] .btn-primary:active,.btn-primary.disabled.active,.btn-primary[disabled].active,fieldset[disabled] .btn-primary.active
{
	background-color:#${colors.bk}; /* #337ab7; */
	border-color: #${colors.border}; /*#2e6da4;  /* -50D13 */
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
.cat-title, .store-menu a.active{
	background-color:#${colors.bk}; 
}
.store-item-title{color:#${colors.bk} }
 
</style>
</c:if>
</c:if>