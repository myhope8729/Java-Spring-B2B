<%@page import="com.kpc.eos.model.common.SysMsg" %>
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
	KptApi.showError("${errorMessage}");
	$("#adminId").focus();
</script>
</c:if>

<style>
body{height:100%; font:12px/18px "Microsoft Yahei",Tahoma,Helvetica,Arial,Verdana,"\5b8b\4f53",sans-serif; color:#51555c}
/* Toastr Style for Mobile */
#toast-container{
	top:50%;
	margin-top:-20px;
}
#toast-container>div{
    width: 280px;
}
.toast-top-center {
    top: 12px;
    margin: 0 auto;
    left: 50%;
    margin-left: -140px;
}
</style>
<form action="Login.do?cmd=login" method="post" id="form-login" class="form-horizontal admin-form">
	<div align="center">
		<table border="0" width="94%" bgcolor="#FFFFFF" height="46">
			<tr>
				<td align="left" height="60" width="10">
					<img border="0" src="<c:url value="/images/sunpoto_logo.png"/>" height="42px" align="left" width="107px">
				</td>
				<td align="left">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td style="font-family: 微软雅黑; font-size: 14pt;">
								<font size="3">商务百事通，生意好帮手</font>
							</td>
						</tr>
						<tr>
							<td style="font-family: 微软雅黑; font-size: 10pt; color: #808080">中小企业信息化一站式服务</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	
		<table width="92%">
			<tr>
				<td align=center height="35" bgcolor="#FF9900" style="font-family: 微软雅黑; font-size:12pt; color: #FFFFFF">用 户 登 录</td>
			</tr>	
		</table>
	
		<table border="0" width="92%" height="30"   bgcolor="#FFFFFF" cellspacing="0" cellpadding="0" style="font-family: 微软雅黑; font-size:12pt; color: #FFFFFF">
			<tr>
				<td height="20" align=center>
					<table border="0" width="80%" height="30" style="border-left-width: 1px; border-right-width: 1px;">
						<tr>
					    	<td align=right height="30" colspan="2"></td>
						</tr>
						<tr>
					    	<td align=right height="35" width="60">
					    		<i class="gi gi-user" style="font-size:20px;color:#000000;margin-right:10px;"></i>
					    	</td>
					    	<td align=left height="50" >
					    		<input type="text" id="empId" name="empId" " validate="required:true" message="登录名" value="<% if (rememberme.equals("1")) {%><%=userid %><%}else{ %>${empId}<%} %>" placeholder="请输入登录名" style=" width:100%;height:30px;border:1px solid #e0e0e0;background-color:#FFFFFF; padding-left:5px; padding-right:5px; padding-top:1px; padding-bottom:6px; font-size:12pt; font-family:微软雅黑; color:#808080; " size="28">
					    	</td>
						</tr>
						<tr>
							<td align=right height="35" width="60">
								<i class="gi gi-lock" style="font-size:20px;color:#000000;margin-right:10px;"></i>
							</td>
					    	<td align=left height="50" >
								<input type="password" id="pwd" name="pwd" validate="required:true" message="登录密码" value="<% if (rememberme.equals("1")) {%><%=userpwd %><%}%>" placeholder="请输入登录密码" size="28" style="width:100%;height:30px;border:1px solid #e0e0e0;background-color:#FFFFFF; padding-left:5px; padding-right:5px; padding-top:3px; padding-bottom:3px; font-family:微软雅黑;font-size:12pt; color:#808080;">
							</td>
						</tr>	 
						<tr>
							<td align=right height="35" width="60" style="padding-right:6px">
							</td>
							<td align=left height="50" >
								<table border="0" width="100%" height="30" style="border-left-width: 1px; border-right-width: 1px;">
									<tr>
										<td align=right height="30" width="20">
											<input type="checkbox" id="rememberme" name="rememberme" <% if (rememberme.equals("1")) {%>checked<%} %> style="margin: 3px 3px 3px 4px;">
										</td>
										<td align="right" height="30px" style="font-family: 微软雅黑; font-size:12pt;color:#51555c;">
											<p align="left" style="margin-bottom:0;">记住登录名和密码
										</td>
									</tr>    
								</table>
							</td>
						</tr>  
					</table>
				</td>
			</tr>
		</table>
		
		<table border="0" width="92%" height="30" cellspacing="0" cellpadding="0">
			<tr>
				<td align=right height="20"></td>
			</tr> 
			<tr>
				<td align=center height="50" width="100%">
					<button type="submit" style="height:32px;width:100%; border-radius: 4px;background-color:#FF9900; border:0px solid #FFA51E; cursor: pointer;  color:#FFFFFF;  font-family:微软雅黑; font-size:12pt" type="button">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
				</td>
			</tr>
			<tr>
				<td align=center height="50" width="100%">
					<a href="Login.do?cmd=register" style="display:block;line-height:32px;height:32px;width:100%; border-radius: 4px;background-color:#FF9900; border:0px solid #FFA51E; cursor: pointer;  color:#FFFFFF;  font-family:微软雅黑; font-size:12pt">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</a>
				</td>
			</tr>	  
			<tr>
				<td align=left height="90" ></td>
			</tr>	     
		</table>
	</div>
</form>
<table border="0" width="100%" style="background-color:#f1f1f1;" cellspacing="0" cellpadding="0">
	<tr>
		<td align="center" style="line-height: 150%; font-family:微软雅黑; font-size:10pt; color:#808080; padding-top:30px; padding-bottom:30px">深圳市唯尔信息技术有限责任公司&nbsp; 版权所有 <br>
			<font face="微软雅黑" color="#808080" style="font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 18px; orphans: auto; text-align: -webkit-right; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-stroke-width: 0px" size="2">
				<a target="_blank" style="text-decoration: none; " href="http://www.miitbeian.gov.cn/">
					<font color="#FFA51E">粤ICP备14007086号-5</font>
				</a>
			</font>
		</td>
	</tr>
</table>
<div id="alert_message" class="hidden"><%= SysMsg.flashMsg(request) %></div>