$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'CustType.do?cmd=custTypeGridAjax',
	    postData: $('#custTypeListForm').serialize(),
	    colNames:['类别', '说明', '状态', '操作'],
	    colModel:[
	      {name:'custTypeName',		sortable:true,	width:350, 	align:'left', index:'custTypeName'	}, 
	      {name:'note', 			sortable:true,	width:550,	align:'left', index:'note'			},
	      {name:'stateName', 		sortable:true,	width:100, 	align:'center',	index:'state'			},
	      {name: "control",			sortable:false,	width:100,	align:'center',	
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a href="CustType.do?cmd=custTypeForm&custTypeId='+rowObject.custTypeId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'custTypeName',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
		pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#custTypeListForm').serialize()
	}).trigger('reloadGrid');
}