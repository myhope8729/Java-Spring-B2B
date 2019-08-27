CommonGrid.init('grid', {
    url: 'HostCust.do?cmd=hostListGridAjax',
    postData: $('#addSupplySearchForm').serializeJSON(),
    colNames:['登录名', '供应商', 'ID'],
    colModel:[
      {name:'hostUserNo',		sortable:true,	width:150,	align:'left', index:'hostUserNo'			},
      {name:'hostUserName',	 	sortable:true,	width:200, 	align:'left',	index:'hostUserName'		},
      {name:'hostUserId',		hidden:true																}
    ],
    sortname: 'createDate',
    sortorder: 'desc',
    rowNum :  gridPager.rowNum,
	page :  gridPager.page,
    pager: jQuery('#gridpager'),
    multiselect: true
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#addSupplySearchForm').serializeJSON()
	}).trigger('reloadGrid');
}

function settingHostList(){
	var selectedIds = jQuery('#grid').jqGrid('getGridParam','selarrrow');
	if (selectedIds.length == 0) {
		KptApi.showInfo(settingHostMsg);
		return false;
	}
	var selectedRows = jQuery.map(selectedIds, function(id, index){
		if (id === undefined) 
			return;
		return jQuery('#grid').jqGrid('getRowData', id);
	});
	
	var selectedData = JSON.stringify(selectedRows);
	$("#selectedRows").val(selectedData);
	
	$('#supplyListFrom').submit();
}