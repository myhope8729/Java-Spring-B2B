jQuery(document).ready(function(){
	var selVal = $("#custUserId").val();
	
	var formObj = $('#paymentForm');
	$("#paymentType").change(function(selId){
		var selVal = $("#paymentType").val();
		if (selVal != '') {
			var paytypeId = $("#paymentType option[value='"+selVal+"']").attr("payTypeId");
			$("#paytypeId").val( paytypeId );
		}
	});
	
	$("#custUserId").change(function(selId){
		
		var selVal = $("#custUserId").val();
		if (selVal != '') {
			var custtypeId = $("#custUserId option[value='"+selVal+"']").attr("custTypeId");
			$("#custtypeId").val( custtypeId );
		}
		
		$.mvcAjax({
			url 	: "Payment.do?cmd=loadListDataAjax",
			data 	: formObj.serialize(),
			dataType: 'json',
			success : function(data, ts)
			{
				if (data.result.resultCode == result.FAIL)
				{
					KptApi.showMsg(data.result.resultMsg);
				}
				else
				{
					$("#paymentType option").not("[value='']").remove();
					if (data.paytypeList.length > 0){
						$.each(data.paytypeList, function(ind, item){
							$("#paymentType").append("<option value='"+item.seqNo+"' payTypeId='"+item.payTypeId+"'>"+item.payTypeName+"</option>");
						});
					}
				}
			}
		});
	});
	

});


