
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<!-- Contents start-->
<div class="ip_content">
	<!--  Login strar -->
	<div class="common_wrapper clfix" style="vertical-align:middle;">
		<div class="logo_header">
			<h3 class="page_title">用户注册</h3>
		</div>
		<!-- login input start -->
		<div class="form_wrapper">
			<form name="registerFrm" id="registerFrm" class="admin-form" method="post" action="Login.do?cmd=saveUser">
				<div class="row-fluid">
					<div class="form_field col-md-5 col-sm-6 col-xs-12 col-md-offset-1">
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>登录名</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="text" name="userNo" id="userNo" class="input" style="ime-mode:inactive" validate="required: true" message="登录名" value="" maxlength="16" tabindex="1" size="20" placeholder="请使用数字和字母" autofocus />
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>登录密码</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="password" name="pwd" id="pwd" class="input" style="ime-mode:inactive" validate="required: true,minlength:6" message="密码" value="" maxlength="16" tabindex="3" size="20" placeholder="不能少于六位"/>
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>所在地</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6 addr_select">
								<div class="col-md-4 col-xs-12" id="provId">
									<div class="row">
										<select name="provId" class="locationList" tabindex="5">
											<c:forEach var="province" items="${provList}">
											<option value="${province.addressId}" data-level="${province.addressLevel}">${province.addressName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-4 col-xs-12 <c:out value="${isProvince?'':'hidden'}"/>" id="cityId">
									<div class="row">
										<select name="cityId" class="locationList" tabindex="6" >
											<c:forEach var="city" items="${cityList}">
											<option value="${city.addressId}" data-level="${city.addressLevel}">${city.addressName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-4 col-xs-12" id="areaId">
									<div class="row">
										<select name="locationId" id="locationId" tabindex="7" >
											<c:forEach var="area" items="${areaList}">
											<option value="${area.addressId}">${area.addressName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>联系人</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="text" name="contactName" id="contactName" class="input" style="ime-mode:inactive" validate="required: true" message="联系人" value="" maxlength="16" tabindex="9" size="20"/>
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label">手机号码</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="text" name="telNo" id="telNo" class="input" style="ime-mode:inactive" validate="" message="手机号码" value="" maxlength="16" tabindex="11" size="20"/>
							</div>
						</div>
						<input type="hidden" name="userKind" value="UK0001"/>
					</div>
					<div class="form_field col-md-5 col-sm-6 col-xs-12 ">
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>用户名称</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="text" name="userName" id="userName" class="input" style="ime-mode:inactive" validate="required: true" message="用户名称" value="" maxlength="16" tabindex="2" size="20" placeholder="请输入企业名称"/>
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>重输密码</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="password" name="confirmPwd" id="confirmPwd" class="input" style="ime-mode:inactive" validate="required: true, equalTo: pwd" message="密码" value="" maxlength="16" tabindex="4" size="20" placeholder="请再次输入登录密码"/>
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>地址</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="text" name="userAddr" class="input" style="ime-mode:inactive" validate="required: true" message="地址" value="" maxlength="16" tabindex="8" size="20"/>
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label"><font color="#ff0000">*</font>联系电话</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="text" name="mobileNo" id="mobileNo" class="input" style="ime-mode:inactive" validate="required: true, digits:true" message="联系电话" value="" maxlength="16" tabindex="10" size="20"/>
							</div>
						</div>
						<div class="input_row">
							<div class="col-md-3 col-sm-4 col-xs-6"><label class="form_label">邀请码	</label></div>
							<div class="col-md-9 col-sm-8 col-xs-6">
								<input type="text" name="userCode" id="userCode" class="input" style="ime-mode:inactive" value="" maxlength="16" tabindex="12" size="20"/>
							</div>
						</div>
						<input type="hidden" name="hostUserId" value="${hostUser.userId }" />
					</div>
				</div>
				<div class="row-fluid mgt20">
					<div class="form_field col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12">
						<div class="col-md-10 col-md-offset-1">
							<div class="input_row">
								<input type="submit" class="btn btn-primary" value="提交" />
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!--  // Login end -->
</div>
<!-- //Content end-->