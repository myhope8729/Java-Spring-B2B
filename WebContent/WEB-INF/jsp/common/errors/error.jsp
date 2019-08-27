<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<div class="bread_wrapper">
	<div class="breads">
		当前位置 : 非法要求
	</div>
	<div class="welcome alignR">
		<ul>
			<li>欢迎您！&nbsp;${loginUser.userName}&nbsp;${loginUser.empName}&nbsp;${loginUser.empNo}</li>
		</ul>
	</div>
</div>
<div class="alert alert-danger"><i class="glyphicon glyphicon-warning-sign"></i>&nbsp;&nbsp;错误的要求，请你确认一下</div>
<div id="exceptionDialog" title="Error Message">
	<p>
		<pre>${exception.message}</pre>
	</p>
</div>

<script>
	//$("#exceptionDialog").dialog({ modal:true, width:500, height:250 });
</script>
