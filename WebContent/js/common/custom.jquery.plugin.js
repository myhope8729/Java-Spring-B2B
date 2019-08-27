
function isMobile(){
	return ($("#left-panel").length > 0);
}

/**
 
* validation default options
 */
var validateDefaultOption = {
	//showErrors: true,
	focusInvalid: true,
	submitHandler: function(form) {
		$.mvcAjax({
			form : form,
			success : function(data, ts) {
				var validator = $.data(form, 'validator');
				
				if (data.result.resultCode == 0) {
					if (validator.settings.onSuccess)
						validator.settings.onSuccess(data);
				} else {
					alert(data.result.resultMsg);
					if (validator.settings.onFail)
						validator.settings.onFail(data);
				}
			}
		});
	}
};
var validateDefaultOption2 = {
	errorElement: 'span', 
    errorClass: 'help-block', 
    focusInvalid: true, 
    errorPlacement : function(label, ele) {
    	// if mobile?
    	if (isMobile()) {
    		ele.closest(".form-group").append(label);
    	} else {
    		label.insertAfter(ele);
    	}
    },
    //ignore: "",  
	submitHandler: function(form) {
		if ($(form).hasClass("ajax")) {
			$.mvcAjax({
				form : form,
				success : function(data, ts) {
					var validator = $.data(form, 'validator');
					
					if (data.result.resultCode == 0) {
						if (validator.settings.onSuccess)
							validator.settings.onSuccess(data);
					} else {
						if (validator.settings.onFail)
							validator.settings.onFail(data);
					}
				}
			});
		} else {
			form.submit();
		}
	},
	highlight : function(element) { // hightlight error inputs
		var errorList = this.errorList;
		if (errorList.length > 0) {  
			for (var name in errorList) {
				var tmp = errorList[name].element;
				var newStr = errorList[name].message;
				
				var labelObj = null;
				if (isMobile()) {
					labelObj = $(tmp).closest('.form-group').find("label:first");
				} else {
					labelObj = $(tmp).closest('.form-group').find("label.control-label");
				}
				
				if (labelObj.length > 0) {
					var cloneTmp = labelObj.clone();
					cloneTmp.find("span").remove();
					this.errorList[name].message = errorList[name].message.replace("undefined", cloneTmp.text());
				}
			}
		}
		$(element).closest('.form-group').addClass('has-error'); // set error
	},
	
	unhighlight : function(element) { // revert the change done by hightlight
		$(element).closest('.form-group').removeClass('has-error'); // set error
		$(element).closest('.form-group').find("span.qc-error").remove();
	},
	success: function (label) {
		label.closest('.form-group').removeClass('has-error'); // set success class to the control group
		label.closest('.form-group').find("span.qc-error").remove();
    }
};

jQuery.validator.addMethod("regular", function(value, element, params) { 
	if (value == "") return true;
	return params.test(value);
});

/**
 * Calendar default option
 */
var calendarDefaultOption = {
	dateFormat: 'yy.mm.dd',
	showButtonPanel: true,
	showAnim: "slideDown",
	showOn: "button",
	buttonImage: contextUri("images/icon_cal.gif"),
	onSelect: function( selectedDate, inst ) {
			instance = $( this ).data( "datepicker" );
			var option = this.id == instance.settings.minObjId ? "minDate" : "maxDate",
			date = $.datepicker.parseDate(
									instance.settings.dateFormat || $.datepicker._defaults.dateFormat,
									selectedDate, 
									instance.settings );
			var obj = (option == 'minDate') ? instance.settings.maxObjId : instance.settings.minObjId;
			$('#' + obj ).datepicker( "option", option, date );
	}
};

/**
 * tab default option.
 */
var tabsDefaultOption = {
	cache			: true,
	collapsible		: true,
	deselectable	: true,
	event			: "click",
	spinner			: "Processing...",
	ajaxOptions		: {
		type		: 'POST'
	}
};