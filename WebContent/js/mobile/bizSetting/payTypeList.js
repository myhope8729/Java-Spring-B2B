	CommonGrid.init('grid', {
	    url: 'PayType.do?cmd=payTypeGridAjax',
	    postData: $('#payTypeListForm').serializeJSON(),
	    colNames:['付款方式', '状态', '操作'],
	    colModel:[
	      {name:'payTypeName', 		sortable:true,	width:150, 	align:'center', index:'payTypeName'	}, 
	      {name:'stateName', 		sortable:true,	width:150, 	align:'center',	index:'state'		},
	      {name: "control",			sortable:false,	widht:150,	align:'center', classes:'gridLink',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a data-role="button" data-icon="edit" data-mini="true" data-iconpos="left"  href="PayType.do?cmd=payTypeForm&payTypeId='+rowObject.payTypeId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'payTypeName',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
		pager: jQuery('#gridpager')
	});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#payTypeListForm').serializeJSON()
	}).trigger('reloadGrid');
}