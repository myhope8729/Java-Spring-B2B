<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="comment">
	<form class="admin-form" role="form" action="<%= BaseController.getCmdUrl("UserPage", "saveComment") %>"
		id="commentFrm" name="commentFrm" method="post" enctype="multipart/form-data">
		<div class="comment_form">
			<div class="form-group">
				<div class="col-xs-6">
					<label class="pull-left">网友评论</label>
				</div>
				<div class="col-xs-6 text-right">
					<label>
						<a href="<%= BaseController.getCmdUrl("UserPage", "listComment") %>&hostUserId=${hostUser.userId}&commentType=${commentType}&itemId=${itemId}">
						${commentCount}条评论
						</a>
					</label>
				</div>
				<div class="col-xs-12">
					<label class="text-left">
						文明上网理性发言，请遵守
						<a href="<%= BaseController.getCmdUrl("UserPage", "docComment") %>&hostUserId=${hostUser.userId}" target="_blank">网络评论服务协议</a>
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-11 col-md-offset-1">
					<textarea class="form-control" name="cText" rows="5"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-12">
					<label class="pull-left control-label">昵称</label>
				</div>
				<div class="col-xs-12">
					<input type="text" data-role="none" name="cName" value="${loginUser.empName}" data-clear-btn="true"  />
				</div>
				<div class="col-xs-12 text-left mgt10">
					<div class="fileinput fileinput-new" data-provides="fileinput">
						<span class="btn btn-primary btn-file">
							<span class="fileinput-new">添加图片 </span>
							<span class="fileinput-exists">变更图片</span>
							<input type="file" name="commentPicFile">
						</span>
						<span class="fileinput-filename"></span>&nbsp;
						<a href="javascript:;" class="close fileinput-exists" data-dismiss="fileinput">&times;</a>
					</div>
				</div>
				<div class="col-xs-12 alignC mgt10">
					<button class="btn btn-primary"  data-role="button" data-icon-pos="left" data-icon="check" data-theme="b">提交评论</button>
				</div>
			</div>
		</div>
		
		<input type="hidden" name="hostUserId" value="${hostUser.userId}">
		<input type="hidden" name="commentType" value="${commentType}" />
		<input type="hidden" name="itemId" value="${itemId}" />		
		<input type="hidden" name="upperId" value="${upperId}" />
		<input type="hidden" name="returnType" value="" />
	</form>
</div>