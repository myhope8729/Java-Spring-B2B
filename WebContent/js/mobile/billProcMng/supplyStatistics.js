$(document).ready(function(){
	
	CommonGrid.init('grid', {
		url: 'BillProc.do?cmd=supplyStatisticGridAjax',
		postData: $('#orderStatistic').serialize(),
	    colNames:gridData.colNames,
	    colModel:gridData.colModel,
		rowNum :  -1,
		rownumbers:true
	});
	
	$('#btnPrint').click(function(){
		window.location.href = "BillProc.do?cmd=supplyStatisticPrintForm&createDateFrom=" + $("#createDateFrom1").val() + "&createDateTo=" +  $("#createDateTo1").val();
	});
});