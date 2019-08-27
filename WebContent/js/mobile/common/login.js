$(document).ready(function() {
	$("#form-login").validate( $.extend({}, validateDefaultOption, {
		showErrors 	: false,
		success	   	: false,
		wrapper		: false,
		focusInvalid: true,
		submitHandler: function(form) {
			form.submit();
		}
	}));
});

