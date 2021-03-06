<%@page import="com.kpc.eos.core.util.SessionUtil"%>
<%@page import="com.kpc.eos.model.common.SysMsg" %>
<%@page import="com.kpc.eos.model.dataMng.UserModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="footer_wrapper">
	<div class="container">
		<div class="container_footer">
			<p><c:if test="${not empty loginUser}">${loginUser.userName}</c:if><c:if test="${empty loginUser and not empty hostUser }">${hostUser.userName }</c:if>&nbsp;版权所有 Copyright 2016-2017</p>
			<p><label>公司地址:</label>
				<c:if test="${not empty loginUser.address}">${loginUser.address}&nbsp;</c:if>
				<c:if test="${not empty loginUser.userAddr}">${loginUser.userAddr}&nbsp;</c:if>
				<c:if test="${(empty loginUser) and (not empty hostUser)}">${hostUser.topic2}&nbsp;</c:if>				
			</p>
			<p><label>欢迎垂询:</label>
				<c:if test="${not empty loginUser.telNo}">${loginUser.telNo}&nbsp;&nbsp;</c:if>
				<c:if test="${not empty loginUser.userName}">${loginUser.userName}&nbsp;</c:if>
				<c:if test="${empty loginUser and not empty hostUser}">${hostUser.telNo}&nbsp;</c:if>
				<c:if test="${empty loginUser and not empty hostUser}">${hostUser.userName}&nbsp;</c:if>
			</p>
			<p><label>技术支持:</label>&nbsp;<a href="" class="orangeLink">商百通云平台-中小企业信息化一站式服务</a></p>
		</div>
	</div>
</div>
<div id="alert_message" class="hidden"><%= SysMsg.flashMsg(request) %></div>