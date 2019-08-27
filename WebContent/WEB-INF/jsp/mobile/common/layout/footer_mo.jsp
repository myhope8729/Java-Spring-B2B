<%@page import="com.kpc.eos.core.util.SessionUtil"%>
<%@page import="com.kpc.eos.model.common.SysMsg" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>

<div class="footer_wrapper">
	<div class="container">
		<div class="container_footer" style="color:#777;">
			
				    <p><label>${loginUser.userName}&nbsp;版权所有 </label>
					<p><label>公司地址:
						<c:if test="${not empty loginUser.address}">${loginUser.address}&nbsp;</c:if>
						</label>
					</p>
					<p><label>欢迎垂询:
						<c:if test="${not empty loginUser.telNo}">${loginUser.telNo}&nbsp;&nbsp;</c:if>
						<c:if test="${not empty loginUser.userName}">${loginUser.userName}&nbsp;</c:if>
						</label>
					</p>
					<p><label>技术支持:</label>&nbsp;<a href="" class="orangeLink">商百通云平台-中小企业信息化一站式服务</a></p>
		</div>
		<div id="dummy"></div>
	</div>
</div>
