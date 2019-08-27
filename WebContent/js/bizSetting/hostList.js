$(document).ready(function() {
	CommonGrid.init('grid', {
	    url: 'HostCust.do?cmd=hostListGridAjax',
	    postData: $('#addSupplySearchForm').serialize(),
	    colNames:['行业类别', '登录名', '供应商', '联系人', '联系电话', '所在地', 'ID'],
	    colModel:[
	      {name:'bizAreaType',		sortable:true,	width:100, 	align:'center', index:'bizAreaType'			},
	      {name:'hostUserNo',		sortable:true,	width:150,	align:'center', index:'hostUserNo'			},
	      {name:'hostUserName',	 	sortable:true,	width:200, 	align:'center',	index:'hostUserName'		},
	      {name:'hostContactName', 	sortable:true,	width:150, 	align:'center',	index:'hostContactName'		},
	      {name:'hostTelNo', 		sortable:true,	width:100, 	align:'center',	index:'hostTelNo'			},
	      {name:'hostLocationName',	sortable:true,	width:250, 	align:'center',	index:'hostLocationName'	},
	      {name:'hostUserId',		hidden:true																}
	    ],
	    sortname: 'createDate',
	    sortorder: 'desc',
	    loadComplete : function() {},
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager'),
	    multiselect: true
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#addSupplySearchForm').serialize()
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