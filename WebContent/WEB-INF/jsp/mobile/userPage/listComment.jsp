<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">网友评论</h3>
    <a data-role="button" data-icon="back" data-mini="true" class="ui-btn-right" href="<%= BaseController.getCmdUrl("UserPage", "mainPage") %>&hostUserId=${hostUser.userId}"  id="btnBack"><qc:message code="system.common.btn.back"/></a>
</div>

<div role="main" class="ui-content">
	<c:if test="${not empty listComment }">
	<ul class="media-list">
	<c:forEach var="comment" items="${listComment}">
		<li class="media">
			<a href="javascript:void(0)" class="pull-left">
				<img src="/images/user.jpg" alt="avatar" class="img-circle" width="35">
			</a>
			<div class="media-body">
				<span class="text-muted pull-right"><small><em>${comment.createDate}</em></small></span>
				<strong>${comment.commentName}</strong>
				<div class="media-content">
				<c:choose>
					<c:when test="${empty comment.upperName}">
						<c:forEach var="commentPic" items="${comment.commentPicList}">
							<img width="100" height="100" src="/uploaded/commentpic/${commentPic.commentImgPath}" class="thumbnail">
						</c:forEach>
						<p>${comment.commentText}</p>
					</c:when>
					<c:otherwise>
						<span class="color-dark">回复：${comment.upperName} </span>
						<p>${comment.commentText}</p>
						<div class="bg-dark pd10">
							<span>${comment.upperName} ：${comment.upperText}</span>
						</div>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
			<div class="media-buttons text-right">
				<a data-role="button" data-icon="delete" data-mini="true" data-inline="true" href="<%= BaseController.getCmdUrl("UserPage", "delComment") %>&commentId=${comment.commentId}"><qc:message code="system.common.btn.delete"/></a>
				<a data-role="button" data-icon="back" data-mini="true" data-inline="true" href="javascript: returnComment('${comment.commentId}');"><qc:message code="system.common.btn.reply"/></a>
			</div>
		</li>
	</c:forEach>
	</ul>
	</c:if>
</div>
