<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin-body">	
	<div class="bg-white">	
		<div class="pd10 color-white" style="background-color:#${hostUser.mainColor};">
			<span class="font-18">回复网友评论</span>
		</div>
		<div class="form-group">
			<div class="pd20">
				<p>回复 ：${comment.commentName}</p>
				<p>${comment.commentText}</p>
			</div>
		</div>
		<div class="form-group pdb20">
			<form class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("UserPage", "saveComment") %>"
				id="commentFrm" name="commentFrm" method="post" enctype="multipart/form-data">
				<div class="comment_form container">
					<div class="form-group">
						<div class="col-md-1">
							<label class="pull-right">网友评论</label>
						</div>
						<div class="col-md-9">
							<label class="text-left">
								文明上网理性发言，请遵守
								<a href="<%= BaseController.getCmdUrl("UserPage", "docComment") %>&hostUserId=${hostUser.userId}">网络评论服务协议</a>
							</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-11 col-md-offset-1">
							<textarea class="form-control" name="cText" rows="5"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-1">
							<label class="pull-right control-label">昵称</label>
						</div>
						<div class="col-md-2">
							<input type="text" name="cName" value="${loginUser.empName}" class="form-control" />
						</div>
						<div class="col-md-4 col-md-offset-5 text-right">
							<button class="btn btn-primary">提交评论</button>
						</div>
					</div>
				</div>
				
				<input type="hidden" name="hostUserId" value="${comment.hostId}">
				<input type="hidden" name="commentType" value="${comment.commentType}" />
				<input type="hidden" name="itemId" value="${comment.itemId}" />
				<input type="hidden" name="upperId" value="${comment.commentId}" />
				<input type="hidden" name="returnType" value="list" />
			</form>
		</div>
	</div>	
</div>
