$(document).ready(function(){
	$("select[name='urlType']").change(function(){
		if ($(this).val() == 'UR0003'){
			$(".product-group").show();
			$(".url-link").hide();
		}else{
			$(".product-group").hide();
			$(".url-link").show();
		}
		
	}).trigger('change');
});