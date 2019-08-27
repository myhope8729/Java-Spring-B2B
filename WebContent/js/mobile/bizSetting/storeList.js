	CommonGrid.init('grid', {
	    url: 'Store.do?cmd=storeGridAjax',
	    postData: $('#custTypeListForm').serializeJSON(),
	    colNames:['所属部门', '仓库编号', '仓库名称', '状态', '操作'],
	    colModel:[
	      {name:'deptName',			sortable:true,	width:200, 	align:'center', index:'deptName'	}, 
	      {name:'storeNo', 			sortable:true,	width:130,	align:'center', index:'storeNo'		},
	      {name:'storeName', 		sortable:true,	width:130,	align:'center', index:'storeName'	},
	      {name:'stateName', 		sortable:true,	width:100, 	align:'center',	index:'state'		},
	      {name: "control",			sortable:false,	width:80,	align:'center', classes:'gridLink',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a class="mg0-auto" data-role="button" data-icon="edit" data-mini="true" data-iconpos="notext" href="Store.do?cmd=storeForm&storeId='+rowObject.storeId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'deptName',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#storeSearchForm').serializeJSON()
	}).trigger('reloadGrid');
}