$(document).ready(function() {
	
	$("#loginFrm").validate( $.extend({}, validateDefaultOption, {
		showErrors 	: false,
		success	   	: false,
		wrapper		: false,
		focusInvalid: true,
		submitHandler: function(form) {
			form.submit();
		}
	}));
});

function onChangeLang( lang ){
	$("#lang").val(lang);
	
	if(lang == "en"){
		$("#langEn").attr("class", "language_selected");
		$("#langKo").attr("class", "language_deselected");
	}else if(lang == "ko"){
		$("#langKo").attr("class", "language_selected");
		$("#langEn").attr("class", "language_deselected");
	}
}