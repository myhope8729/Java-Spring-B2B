$(document).ready(function(){
	CommonGrid.init('paymentGrid', {
	    datatype:"local",
	    data: detailList || [],
	    colModel:[
	   	       {name:'paytypeName', 	label:'预付款名称',		width:'250', 	align:'left',		sortable:false, formatter: function(cellValue, option, rowObject){
	   	    	   return (cellValue == '')? rowObject.paytype1: cellValue;
	   	       }},
	   	       {name:'paytype2', 		label:'款项名称',		width:'250',	align:'left',		sortable:false  },
	   	       {name:'tot2',			label:'收款金额(元)',	width:'200',	align:'right',		sortable:false, 
	   	    	   formatter: function(cellValue, option, rowObject){
	   	    		   return billTotalAmt;
	   	       }},
	   	       {name:'tot', 			label:'款项金额(元)',	width:'200',	align:'right',		sortable:false	},
	   	]
	});
});

function saveBillProc(saveFlag){
	var postData = {saveFlag: saveFlag};
	var formArray = jQuery('#billProcPaymentForm').serializeArray();
	for(i=0; i<formArray.length; i++) {
		eval("postData." + formArray[i].name + "= formArray[i].value ");
	}
	
	$.mvcAjax({
		url 	: jQuery('#billProcPaymentForm').attr("action"),
		data 	: postData,
		dataType: 'json',
		success : function(data, ts)
		{
			if (data.result.resultCode == result.FAIL)
			{
				KptApi.showInfo(data.result.resultMsg);
			}
			else
			{
				window.location.href = afterSavingUrl;
			}
		}
	});
}

function rejectBillProc(){
	KptApi.confirm(confirmReject, function(){
		if ($("#procNote").val() == ''){
			KptApi.showError(emptyNote);
			return;
		}
		submitAjax();
	});
}

function submitAjax()
{
	$.mvcAjax({
		url 	: "BillProc.do?cmd=rejectDocumentAjax",
		data 	: jQuery('#billProcPaymentForm').serialize(),
		dataType: 'json',
		success : function(data, ts)
		{
			if (data.result.resultCode == result.FAIL)
			{
				KptApi.showInfo(data.result.resultMsg);
			}
			else
			{
				window.location.href = afterSavingUrl;
			}
		}
	});
}
