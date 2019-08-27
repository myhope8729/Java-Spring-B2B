<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<div id="exceptionDialog" title="Error Message">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		<pre>${exception.message}</pre>
	</p>
</div>

<script>
	$("#exceptionDialog").dialog({ modal:true, width:500, height:250 });
</script>
