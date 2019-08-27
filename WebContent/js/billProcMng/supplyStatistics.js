$(document).ready(function(){
	
	CommonGrid.init('grid', {
		url: 'BillProc.do?cmd=supplyStatisticGridAjax',
		postData: $('#orderStatistic').serialize(),
	    colNames:gridData.colNames,
	    colModel:gridData.colModel,
		rowNum :  -1,
		rownumbers:true
	});
});