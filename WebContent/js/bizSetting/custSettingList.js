$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'HostCust.do?cmd=custSettingGridAjax',
	    postData: $('#custSettingSearchForm').serialize(),
	    colNames:['客户名称', '类别', '拣货组', '拣货组内序号', '状态', '操作'],
	    colModel:[
	      {name:'custUserFullName',		sortable:true,	width:350, 	align:'left', index:'custUserName, custUserNo'	},
	      {name:'custTypeName',			sortable:true,	width:250,	align:'left', index:'custTypeName'				},
	      {name:'distributeName', 		sortable:true,	width:200, 	align:'center',	index:'distributeName'				},
	      {name:'distributeNo', 		sortable:true,	width:100, 	align:'right',	index:'distributeNo'				},
	      {name:'stateName',		 	sortable:true,	width:100, 	align:'center',	index:'state'						},
	      {name: "control",				sortable:false,	width:100,	align:'center', 
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a href="HostCust.do?cmd=custSettingForm&hostId=' + rowObject.hostUserId + '&custId=' + rowObject.custUserId + '">设置</a>';
	    	  }
	      },
	    ],
	    sortname: 'custUserId',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
		pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#custSettingSearchForm').serialize()
	}).trigger('reloadGrid');
}