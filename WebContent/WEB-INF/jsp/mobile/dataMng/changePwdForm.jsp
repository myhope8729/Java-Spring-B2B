<%@page import="com.kpc.eos.core.controller.BaseController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<script>
	var js_msgs = {
		'js.user.mismatch.confirmPassword' : '<qc:message code="js.user.mismatch.confirmPassword" />',		
		'js.user.confirmPassword.required' : '<qc:message code="user.confirmPassword.required" />'	
	};
</script>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">修改密码</h3>
</div>

<div role="main" class="ui-content mgt10">
	
	<form class="form-horizontal admin-form ajax" role="form" action="<%= BaseController.getCmdUrl("User", "changePasswordAjax") %>&empId=${user.empId}" id="changePasswordForm" method="post">
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>旧密码</label>
		 	<div class="col-xs-8">
	 			<input type="password" placeholder="旧密码" data-clear-btn="true" name="oldPwd" value="" class="form-control" validate="required: true" >
		 	</div>
		 </div>
		 
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>新密码</label>
		 	<div class="col-xs-8">
	 			<input type="password" placeholder="新密码" data-clear-btn="true" name="pwd" id="pwd" value="" class="form-control" validate="required: true">
	 		</div>
	 	</div>
		 
		<div class="form-group">
		 	<label class="control-label col-xs-4 text-right"><span class="required">*</span>重输新密码</label>
		 	<div class="col-xs-8">
		 		<input type="password" placeholder="重输新密码" data-clear-btn="true" name="confirmPwd" value="" class="form-control" validate="required2: true,equalTo2:[document.getElementById('pwd'),'新密码']">
	 		</div>
	 	</div>
		
	 	<div class="form-actions">
		    <button type="submit" class="btn btn-primary"  data-theme="b" ><qc:message code="system.common.btn.submit"/></button>
	  	</div> 
	</form>
</div>	 