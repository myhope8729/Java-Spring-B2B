CommonGrid.init('grid', {
	url:'CustSearch.do?cmd=custSearchGridAjax',
	postData: $('#custSearchForm').serializeJSON(),
    datatype: 'local',
	sortname:"user_id",
	colNames:['单位名称', '地址', '联系人', '联系电话'],
    colModel:[
      {name:'custUserName',			sortable:true,	width:200, 	align:'left', 	index:'custUserName'	},
      {name:'address',				sortable:true,	width:300,	align:'left', 	index:'address'			},
      {name:'contactName', 			sortable:true,	width:100, 	align:'left',	index:'contactName'		},
      {name:'telNo', 				sortable:true,	 			align:'left',	index:'telNo'			} 
    ],
    height:'100%',
    pager: '#pager'
});


function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData:  $('#custSearchForm').serializeJSON(),
		page:1,
		datatype: 'json'
	}).trigger('reloadGrid');
}

$(document).ready(function() {
	reloadGrid();
});
