	CommonGrid.init('grid', {
	    url: 'CustType.do?cmd=custTypeGridAjax',
	    postData: $('#custTypeListForm').serializeJSON(),
	    colNames:['类别',  '状态', '操作'],
	    colModel:[
	      {name:'custTypeName',		sortable:true,	width:200, 	align:'center', index:'custTypeName'	}, 
	      {name:'stateName', 		sortable:true,	width:150, 	align:'center',	index:'state'			},
	      {name: "control",			sortable:false,	widht:150,	align:'center',	
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a data-role="button" data-icon="edit" data-mini="true" data-iconpos="left" href="CustType.do?cmd=custTypeForm&custTypeId='+rowObject.custTypeId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'custTypeName',
	    sortorder: 'asc',
		page :  gridPager.page,
		pager: jQuery('#gridpager')
	});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#custTypeListForm').serializeJSON()
	}).trigger('reloadGrid');
}