$(document).ready(function(){
	$(document).on('change', 'input[name=inQty]', changeValue);
	$(document).on('change', 'input[name=inPrice]', changeValue);
	$(document).on('change', 'input[name=salePrice]', changeValue);
});

function changeValue(e){
	var obj = $(e.target);
	
	var objName = jQuery(obj).attr('name');
	var val = obj.val();
	
	if (!isNumeric(val)){
		KptApi.showError(invalidNumber);
		obj.focus();
		return;
	}
	
	var itemId = jQuery(obj).attr("rowId");
	var inQty = obj.closest('table').find('input[name=inQty]').val();
	var inPrice = obj.closest('table').find('input[name=inPrice]').val();
	var salePrice = obj.closest('table').find('input[name=salePrice]').val();
	
	var inTot = inQty * inPrice;
	var saleTot = inQty * salePrice;
	
	var totRate;
	
	if (saleTot == 0){
		totRate = '';
	}
	else{
		totRate = ((saleTot - inTot) / saleTot) * 100;
		totRate = digit(totRate, 1) + "%";
	}
	
	obj.closest('table').find('tr').eq(1).find('td').eq(3).html(inTot);
	obj.closest('table').find('tr').eq(3).find('td').eq(1).html(saleTot);
	obj.closest('table').find('tr').eq(3).find('td').eq(2).html(saleTot - inTot);
	obj.closest('table').find('tr').eq(3).find('td').eq(3).html(totRate);
	
	$.mvcAjax({
		url 	: "BillProc.do?cmd=saveBillProcInfoAjax",
		data 	: {	
					itemId: 	itemId, 
					inQty:		inQty,
					inPrice: 	inPrice,
					salePrice:	salePrice,
					fromDate:	$('#createDateFrom1').val(),
					toDate:		$('#createDateTo1').val()
		},
		dataType: 'json',
		success : function(data, ts)
		{
			if (data.result.resultCode == result.FAIL)
			{
				KptApi.showMsg(data.result.resultMsg);
			}
		}
	});
}

function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}