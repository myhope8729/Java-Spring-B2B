$(document).ready(function() {
	$("#custTypeForm").validate( $.extend({}, validateDefaultOption, {
		showErrors : false,
		wrapper		: false,
		focusInvalid: true,
		submitHandler: function(form) {
			validateDefaultOption.submitHandler(form);
		},
		onSuccess: function(data) {
			if (data.result.resultCode == result.SUCCESS){
				KptApi.showMsg(data.result.RESULT_SUCCESS_MSG);
			}else{
				KptApi.showError(data.result.RESULT_FAIL_MSG);
			}
			previous();
		}
	}));
});