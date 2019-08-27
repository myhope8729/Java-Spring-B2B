$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'PayType.do?cmd=payTypeGridAjax',
	    postData: $('#payTypeListForm').serialize(),
	    colNames:['付款方式', '是否预付方式', '需要授权', '微信付款', '说明', '状态', '操作'],
	    colModel:[
	      {name:'payTypeName', 		sortable:true,	width:300, 	align:'left', 	index:'payTypeName'	}, 
	      {name:'prePayYnName', 	sortable:true,	width:150, 	align:'center',	index:'prePayYn'	}, 
	      {name:'privYnName', 		sortable:true,	width:150,	align:'center', index:'privYn'		},
	      {name:'weixinYnName',		sortable:true,	width:150,	align:'center', index:'weixinYn'	},
	      {name:'note', 			sortable:true,	width:450,	align:'left',	index:'note'		},
	      {name:'stateName', 		sortable:true,	width:100, 	align:'center',	index:'state'		},
	      {name: "control",			sortable:false,	width:100,	align:'center',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a href="PayType.do?cmd=payTypeForm&payTypeId='+rowObject.payTypeId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'payTypeName',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
		pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#payTypeListForm').serialize()
	}).trigger('reloadGrid');
}