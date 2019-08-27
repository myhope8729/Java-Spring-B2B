$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'Store.do?cmd=storeGridAjax',
	    postData: $('#custTypeListForm').serialize(),
	    colNames:['所属部门', '仓库编号', '仓库名称', '状态', '操作'],
	    colModel:[
	      {name:'deptFullName',		sortable:true,	width:300, 	align:'left', 	index:'deptId'   	}, 
	      {name:'storeNo', 			sortable:true,	width:150,	align:'left', 	index:'storeNo'		},
	      {name:'storeName', 		sortable:true,	width:500,	align:'left', 	index:'storeName'	},
	      {name:'stateName', 		sortable:true,	width:100, 	align:'center',	index:'state'		},
	      {name: "control",			sortable:false,	width:100,	align:'center',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a href="Store.do?cmd=storeForm&storeId='+rowObject.storeId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'deptName',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#storeSearchForm').serialize()
	}).trigger('reloadGrid');
}