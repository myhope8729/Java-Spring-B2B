$(document).ready(function(){
	
	CommonGrid.init('grid', {
		url: 'BillProc.do?cmd=distributeStatisticGridAjax',
		postData: $('#distributeStatistic').serialize(),
	    colNames:gridData.colNames,
	    colModel:gridData.colModel,
		rowNum :  -1,
		grouping: true,
		groupingView: {
            groupField: ["groupName"],
            groupColumnShow: [false],
            groupText: ["<b>{0}</b>"],
            groupOrder: ["asc"],
            groupSummary: [true],
            groupCollapse: false
        }
	});
});

function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#distributeStatistic').serialize()
	}).trigger('reloadGrid');
}