$(document).ready(function() {
	$.validator.addMethod("equalTo2", function(value, element, param) {
		var target = $(param[0]);
		return $(element).val() == target.val();
			
	}, js_msgs['js.user.mismatch.confirmPassword']);
	
	$.validator.addMethod("required2", function(value, element, param) {
		return $(element).val().length > 0;
		
	}, js_msgs['js.user.confirmPassword.required']);
	
	
	var valObj = $("#changePasswordForm").validate();
	valObj.settings.onSuccess = function(data) {		
		KptApi.showMsg(data.result.resultMsg);
		$("#changePasswordForm input[type=password]").val("");
	};
	valObj.settings.onError = function(data) {
		KptApi.showError(data.result.resultMsg);
	};
});

