$(document).ready(function(){
	
	CommonGrid.init('grid', {
		datatype:"local",
	    colNames:gridData.colNames,
	    colModel:gridData.colModel,
	    data: gridData.gridList,
		rowNum :  100000
	});
});

function reloadGrid(){
	window.location.href = 'BillProc.do?cmd=distributeStatistic&createDateFrom=' + jQuery("#createDateFrom1").val() + "&createDateTo=" + 
		jQuery("#createDateTo1").val() + "&distributeSeqNo=" + jQuery("#distributeSeqNo").val();
}

function printForm(){
	if (jQuery("#distributeSeqNo").val() == null) return;
	window.open("BillProc.do?cmd=distributeStatisticPrintForm&createDateFrom1=" + jQuery("#createDateFrom1").val() + "&createDateTo1=" + 
			jQuery("#createDateTo1").val() + "&distributeSeqNo=" + jQuery("#distributeSeqNo").val());
}