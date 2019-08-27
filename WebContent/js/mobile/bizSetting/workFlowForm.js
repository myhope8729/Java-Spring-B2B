$(document).ready(function(){
	if (jQuery('#groupYn').val() == 'N'){
		jQuery('#divEmpList').css('display', 'block');
	}else{
		jQuery('#divEmpList').css('display', 'none');
	}
});
function submitForm(){
	if (jQuery('#groupYn').val() == 'N'){
		var empListCnt = jQuery("[name=empList]:checked").length;
		if (empListCnt == 0){
			KptApi.showInfo(msgEmp);
			return false;
		}
	}
	jQuery("#workFlowForm").submit();
}


function showEmployerDiv(obj){
	if (jQuery(obj).val() == 'N'){
		jQuery('#divEmpList').css('display', 'block');
	}else{
		jQuery('#divEmpList').css('display', 'none');
	}
}