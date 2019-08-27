<%@page import="com.kpc.eos.model.common.SysMsg" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%
String userid = "";
String cookieLang = "en";

Cookie [] cookies = request.getCookies();
 
  if (cookies != null) {
   for( int i = 0; i < cookies.length; i++ ) {
	    if (cookies[i].getName().equals("userid")) { 
	        if (!cookies[i].getValue().equals("")) {
	        userid = cookies[i].getValue();
	        }
	     } else if (cookies[i].getName().equals("lang")) { 
	        if (!cookies[i].getValue().equals("")) {
	        	cookieLang = cookies[i].getValue();
	        }
	     }
   }
}

%>
<script  type="text/javascript">
var savedUserId = "<%= userid%>";
var savedLang = "<%= cookieLang%>";
</script>

<c:if test="${not empty errorMessage}">
<script type="text/javascript">
	KptApi.showError("${errorMessage}");
	$("#adminId").focus();
</script>
</c:if>

<style>
/* .form-horizontal .form-group{height:35px;margin:0;padding:0;display:table;width:100%;}
.form-horizontal .form-group .control-label{display:table-cell;width:91px;padding-right:10px;margin:7px 0;padding-left:0;font-weight:normal;}
.form-horizontal .form-group > div{display:table-cell;width:100%;text-align:left;}
.form-horizontal .form-group > div input, .form-horizontal .form-group > div select{width:90%;height:28px;margin-top:3px;}
.form-horizontal .form-actions .btn{margin-top:19px;width:90%;height:32px;} */
.form-horizontal .form-group .form-control:not(.btn){border-radius:0;border: 1px solid #e0e0e0;}
.form-horizontal .form-group{padding-left:8px;}
</style>
<!-- Contents start-->
<div align="center">

<table border="0" width="94%" bgcolor="#FFFFFF"  height="46">
	<tr>
		<td align="left" height="50" width="10">
			<img border="0" src="<c:url value="/images/sunpoto_logo.png"/>" height="42px" align="left" width="107px">
		</td>
		<td align="left" >
			<table border="0" width="100%" cellspacing="0" cellpadding="0" >
				<tr>
					<td style="font-family: 微软雅黑; font-size: 14pt;" ><font size="3">商务百事通，生意好帮手</font></td>
				</tr>
				<tr>
					<td  style="font-family: 微软雅黑; font-size: 10pt; color: #FF9900" >中小企业信息化一站式服务</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="92%">
	<tbody>
		<tr>
    		<td align="center" height="35" bgcolor="#FF9900" style="border:1px solid #e0e0e0;font-family: 微软雅黑; font-size:12pt; color: #FFFFFF">
				用 户 注 册
			</td>
  		</tr>	
	</tbody>
</table>
<!-- Login Container -->
 <div class="register" style="width:92%;">
     <!-- Login Block -->
     <div class="mgt20">
         <!-- Login Form -->
         <form action="Login.do?cmd=saveUser" method="post" id="form-login" class="form-horizontal admin-form">
             <div class="form-group <qc:errors items="${formErrors}" path="userNo" type="errorCls" />" >
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>登录名</label>
                <div class="col-xs-8">
					<input type="text" name="userNo" id="userNo" class="form-control input-sm" style="ime-mode:inactive" validate="required: true" message="登录名" value="${user.userNo}" maxlength="16" tabindex="1" size="20" placeholder="请使用数字和字母" autofocus />
					<qc:errors items="${formErrors}" path="userNo" />
                </div>
             </div>
             <div class="form-group">
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>用户名称</label>
                <div class="col-xs-8">
					<input type="text" name="userName" id="userName" class="form-control input-sm" style="ime-mode:inactive" validate="required: true" message="用户名称" value="${user.userName }" maxlength="16" tabindex="2" size="20" placeholder="请输入企业名称"/>
                 </div>
             </div>
             <div class="form-group">
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>业务类别</label>
                 <div class="col-xs-8">
					<qc:codeList var="userTypes" codeGroup="UK0000" exceptCode="UK0004"/>
					<select id="userKind" name="userKind" tabindex="5" class="form-control input-sm" validate="required: true" message="业务类别">
						<option value=""><qc:message code="sc.please.select.m" /></option>
						<c:forEach var="type" items="${userTypes}">
						<option value="${type.codeId}" <c:if test="${type.codeId == user.userKind }"> selected</c:if>>${type.codeName}</option>
						</c:forEach>
					</select>
                 </div>
             </div>
             <div class="form-group">
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>登录密码</label>
                 <div class="col-xs-8">
					<input type="password" name="pwd" id="pwd" class="form-control input-sm" style="ime-mode:inactive" validate="required: true,minlength:6" message="密码" value="" maxlength="16" tabindex="3" size="20" placeholder="不能少于六位"/>
                 </div>
             </div>
             <div class="form-group">
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>重输密码</label>
                 <div class="col-xs-8">
					<input type="password" name="confirmPwd" id="confirmPwd" class="form-control input-sm" style="ime-mode:inactive" validate="required: true, equalTo: pwd" message="重输密码" value="" maxlength="16" tabindex="4" size="20" placeholder="请再次输入登录密码"/>
                 </div>
             </div>
             <div class="form-group mgb-none">
				<label class="control-label col-xs-4 text-right">所在地</label>
                 <div class="col-xs-8">
					<div id="provId">
						<div class="form-group">
							<select name="provId" class="locationList form-control input-sm" tabindex="9">
								<c:forEach var="province" items="${provList}">
								<option value="${province.addressId}" data-level="${province.addressLevel}"  
									<c:if test="${(province.addressId == userLocations.level2Id) || (province.addressId == userLocations.level1Id) || (province.addressId == userLocations.level0Id)}">selected</c:if>>
									${province.addressName}
								</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="<c:out value="${isProvince?'':'hidden'}"/>" id="cityId">
						<div class="form-group">
							<select name="cityId" class="locationList form-control input-sm" tabindex="10">
								<c:forEach var="city" items="${cityList}">
								<option value="${city.addressId}" data-level="${city.addressLevel}" <c:if test="${city.addressId == userLocations.level1Id}">selected</c:if>>${city.addressName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="<c:if test="${empty areaList}">hidden</c:if>" id="areaId">
						<div class="form-group">
							<select name="locationId" id="locationId" class="form-control input-sm" >
								<c:if test="${empty areaList }">
									<option value="${userLocations.level0Id}" selected></option>
								</c:if>
								<c:if test="${not empty areaList }">
								<c:forEach var="area" items="${areaList}">
									<option value="${area.addressId}" <c:if test="${area.addressId == userLocations.level0Id}">selected</c:if>>${area.addressName}</option>
								</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
                 </div>
             </div>
             <div class="form-group">
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>联系人</label>
                 <div class="col-xs-8">
					<input type="text" name="contactName" id="contactName" class="form-control input-sm" style="ime-mode:inactive" validate="required: true" message="联系人" value="${user.contactName }" maxlength="16" tabindex="6" size="20"/>
                 </div>
             </div>
             <div class="form-group">
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>联系电话</label>
                 <div class="col-xs-8">
					<input type="text" name="telNo" id="telNo" class="form-control input-sm" style="ime-mode:inactive" validate="required: true, digits:true" message="联系电话" value="${user.telNo }" maxlength="16" tabindex="7" size="20"/>
                 </div>
             </div>
             <div class="form-group">
               	<label class="control-label col-xs-4 text-right">手机号码</label>
                 <div class="col-xs-8">
					<input type="text" name="mobileNo" id="mobileNo" class="form-control input-sm" style="ime-mode:inactive" validate="" message="手机号码" value="${user.mobileNo}" maxlength="16" tabindex="8" size="20"/>
                 </div>
             </div>
             <div class="form-group">
				<label class="control-label col-xs-4 text-right"><span class="required">*</span>地址</label>
                 <div class="col-xs-8">
					<input type="text" name="userAddr" class="form-control input-sm" style="ime-mode:inactive" validate="required: true" message="地址" value="${user.userAddr }" maxlength="16" tabindex="12" size="20"/>
                 </div>
             </div>
             <div class="form-group form-actions">
				<button type="submit" class="btn btn-primary form-control"><qc:message code="system.common.btn.submit"/></button>                     
             </div>
             <div class="form-group form-actions" style="margin-bottom:19px;">
                 <a href="Login.do?cmd=loginForm" class="btn btn-primary form-control" id="link-register-login"><qc:message code="system.common.btn.login"/></a>
             </div>
         </form>
         <!-- END Login Form -->
         
     </div>
     <!-- END Login Block -->
 </div>
 <!-- END Login Container -->
</div>
<table border="0" width="100%" style="border-top: 1px solid #e0e0e0;margin-top:30px;background:#FAFAFA;">
	<tbody>
		<tr>
			<td height="110" align="center" style="line-height: 150%; font-family:微软雅黑; font-size:10pt; color:#FF9900; ">
				<font color="#666666">深圳市唯尔信息技术有限责任公司<br>
					客服电话：0755-26656046&nbsp;&nbsp;<br>
					&nbsp;客服 QQ：159473727<br>
			</font>
			<font face="微软雅黑" color="#FF9900" style="font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 18px; orphans: auto; text-align: -webkit-right; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-stroke-width: 0px" size="2">
				<a target="_blank" style="text-decoration: none; " href="http://www.miitbeian.gov.cn/">
				<font color="#666666">粤ICP备14007086</font></a></font>
			</td>
		</tr>
	</tbody>
</table>
<!-- //Content end-->
<div id="alert_message" class="hidden"><%= SysMsg.flashMsg(request) %></div>