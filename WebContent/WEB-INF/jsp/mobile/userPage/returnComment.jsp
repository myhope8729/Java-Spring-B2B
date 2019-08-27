<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">回复网友评论</h3>
    <a data-role="button" data-icon="back" data-mini="true" class="ui-btn-right" href="<%= BaseController.getCmdUrl("UserPage", "listComment") %>&hostUserId=${hostUser.userId}&commentType=${comment.commentType}&itemId=${comment.itemId}"  id="btnBack"><qc:message code="system.common.btn.back"/></a>
</div>

<div role="main" class="ui-content">
	<div class="bg-white">	
		<div class="form-group">
			<div class="pd20">
				<p>回复 ：${comment.commentName}</p>
				<p>${comment.commentText}</p>
			</div>
		</div>
		<div class="comment">
			<form class="admin-form" role="form" action="<%= BaseController.getCmdUrl("UserPage", "saveComment") %>"
				id="commentFrm" name="commentFrm" method="post" enctype="multipart/form-data">
				<div class="comment_form">
					<div class="form-group">
						<div class="col-xs-12 pd5">
							<label class="pull-left">网友评论</label>
						</div>
						<div class="col-xs-12 pd5">
							<label class="text-left">
								文明上网理性发言，请遵守
								<a href="<%= BaseController.getCmdUrl("UserPage", "docComment") %>&hostUserId=${hostUser.userId}" target="_blank">网络评论服务协议</a>
							</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12 pd5">
							<textarea class="form-control" name="cText" rows="5"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12 pd5">
							<label class="pull-left control-label">昵称</label>
						</div>
						<div class="col-xs-12 pd5">
							<input type="text" data-role="none" name="cName" value="${loginUser.empName}" data-clear-btn="true"  />
						</div>
						<div class="col-xs-12 pd5 text-center">
							<button class="btn btn-primary"  data-role="button" data-icon-pos="left" data-icon="check" data-theme="b">提交评论</button>
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
