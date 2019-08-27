$(document).ready(function(){
	
	$("#btnSave").click(function(){
		$("#action").val("save");
		$("#infoForm").submit();
	});
	$("#btnSubmit").click(function(){
		$("#action").val("submit");
		$("#infoForm").submit();
	});
});