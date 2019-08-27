<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script>
	var js_msgs = {
		'js.user.mismatch.confirmPassword' : '<qc:message code="js.user.mismatch.confirmPassword" />',		
		'js.user.confirmPassword.required' : '<qc:message code="user.confirmPassword.required" />'	
	};
</script>
<div class="admin_body">
	<h3 class="page_title">修改密码</h3>
	
	<form class="form-horizontal admin-form ajax" role="form" action="<%= BaseController.getCmdUrl("User", "changePasswordAjax") %>&empId=${user.empId}" id="changePasswordForm" method="post">
		<div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>旧密码</label>
		 	<div class="col-lg-4">
		 		<input type="password" name="oldPwd" value="" class="form-control" validate="required: true" >
		 	</div>
		 </div>
		 
		<div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>新密码</label>
		 	<div class="col-lg-4">
		 		<input type="password" name="pwd" id="pwd" value="" class="form-control" validate="required: true">
		 	</div>
		 </div>
		 
		<div class="form-group">
		 	<label class="col-lg-4 control-label"><span class="required">*</span>重输新密码</label>
		 	<div class="col-lg-4">
		 		<input type="password" name="confirmPwd" value="" class="form-control" validate="required2: true,equalTo2:[document.getElementById('pwd'),'新密码']">
		 	</div>
		 </div>
		
		 <div class="form-group">
		    <div class="col-lg-offset-4 col-lg-4">
		      <button type="submit" class="btn btn-primary">提      交</button>
		    </div>
		  </div> 
	</form>
</div>	 