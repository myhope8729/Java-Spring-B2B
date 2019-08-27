
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%
String userid = "";
String cookieLang = "en";
String userpwd = "";
String rememberme = "";
Cookie [] cookies = request.getCookies();
  if (cookies != null) {
   for( int i = 0; i < cookies.length; i++ ) {
	    if (cookies[i].getName().equals("userid")) { 
	        if (!cookies[i].getValue().equals("")) {
	        userid = cookies[i].getValue();
	        }
	     } else if (cookies[i].getName().equals("userpwd")){
	    	 if (!cookies[i].getValue().equals("")){
	    		 userpwd = cookies[i].getValue();
	    	 }
	     } else if (cookies[i].getName().equals("rememberme")){
	    	 if (!cookies[i].getValue().equals("")){
	    		 rememberme = cookies[i].getValue();
	    	 }
	     }
   }
}
%>
<script  type="text/javascript">
var savedUserId = "<%= userid%>";
</script>

<c:if test="${not empty errorMessage}">
<script type="text/javascript">
	$("#adminId").focus();
</script>
</c:if>

<!-- Contents start-->
<div class="ip_content">
	<!--  Login strar -->
	<div class="common_wrapper clfix" style="vertical-align:middle;">
		<div class="logo_header">
			<h3 class="page_title">用户登录</h3>
			
		</div>
		<!-- login input start -->
		<div class="form_wrapper login_wrapper">
			<form name="loginFrm" class="admin-form" id="loginFrm" method="post" action="Login.do?cmd=login">
				<input type="hidden" name="returnUrl" value="${returnUrl}"/>
				<div class="form_field col-md-4 col-sm-6 col-xs-12 col-md-offset-4 col-sm-offset-3">
					<div class="form-group">
						<i class="fa fa-user"></i>
						<input type="text" name="empId" id="empId" class="input hasIcon" style="ime-mode:inactive" validate="required: true" message="登录名" value="<% if (rememberme.equals("1")) {%><%=userid %><%}else{ %>${empId}<%} %>" maxlength="16" tabindex="1" size="20" autofocus placeholder="请输入登录名"/>
					</div>
					<div class="form-group">
						<i class="fa fa-lock"></i>
						<input type="password" name="pwd" id="pwd" class="input hasIcon" validate="required: true" message="密码" value="<% if (rememberme.equals("1")) {%><%=userpwd %><%}%>" maxlength="20" tabindex="2" size="20" placeholder="请输入登录密码"/>
					</div>
					<div class="form-group">
						<input type="checkbox" name="rememberme" id="rememberme" <% if (rememberme.equals("1")) {%>checked<%} %>/><label for="rememberme" class="f-left">记住登录名和密码</label>
					</div>
					<div class="form-group">
						<input type="submit" class="btn btn-primary" value="登录" />
					</div>
				</div>					
			</form>
		</div>
		<!-- Board end -->
	</div>
	<!--  // Login end -->
</div>
<!-- //Content end-->