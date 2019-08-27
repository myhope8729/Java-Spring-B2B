<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin-body">
	<div class="bg-white">
		<div class="pd10 color-white" style="background-color:#${hostUser.mainColor};">
			<span class="font-18">网友评论</span>
		</div>
	<c:forEach var="comment" items="${listComment}">		
		<div class="pd20 block-table border-bottom-grey">			
			<div class="row">				
				<div class="pull-left">
					<img width="35" height="35" border="0" src="/images/user.jpg">
					<span class="font-18">${comment.commentName}</span>
					<span class="color-dark">${comment.createDate}</span>
					<a href="<%= BaseController.getCmdUrl("UserPage", "delComment") %>&commentId=${comment.commentId}">删除</a>
				</div>
				<div class="pull-right pd5">
					<button type="button" onclick="returnComment('${comment.commentId}')" class="pd5 bg-white" style="border:1px solid #${hostUser.mainColor};">
						<span>回 复</span>
					</button>
				</div>				
			</div>
			
			<c:choose>
				<c:when test="${empty comment.upperName}">
					<div class="row">
						<div class="pd10">
							<c:forEach var="commentPic" items="${comment.commentPicList}">
								<img width="100" height="100" src="/uploaded/commentpic/${commentPic.commentImgPath}">
							</c:forEach>
						</div>
					</div>
					<div class="row">
						<div class="pd10">
							${comment.commentText}
						</div>
					</div>		
				</c:when>
				<c:otherwise>
					<div class="row">
						<div class="pd10">
							<span class="color-dark">回复：${comment.upperName} </span>
							${comment.commentText}
						</div>
					</div>
					<div class="row">
						<div class="pd10">
							<div class="bg-dark pd10">
								<span>${comment.upperName} ：${comment.upperText}</span>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>		
	</c:forEach>
	</div>
</div>
