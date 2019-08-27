$(document).ready(function(){
	function funcImg(cellvalue, options, rowObject){
		return "<img src='" + cellvalue + "' width='48px' height='32px' />";
	}
	
	CommonGrid.init('grid', {
	    url: 'Item.do?cmd=itemGridAjax',
	    postData: $('#itemSearchForm').serialize(),
	    colNames:['图片', '商品编码', 	'商品名称', '大类', '小类', '单位', '规格', '产地', '状态', '操作'],
	    colModel:[
	      {name:'itemSmallImg',		sortable:false,	width:100, 	align:'center',	formatter:funcImg		},
	      {name:'itemNo',			sortable:true,	width:150,	align:'left', 	index:'itemNo'			},
	      {name:'itemName',			sortable:true,	width:350,	align:'left', 	index:'itemName'		},
	      {name:'cat1', 			sortable:true,	width:200, 	align:'left', 	index:'cat1'			},
	      {name:'cat2', 			sortable:true,	width:200, 	align:'left',	index:'cat2'			},
	      {name:'unit', 			sortable:true,	width:100, 	align:'center',	index:'unit'			},
	      {name:'standard', 		sortable:true,	width:150, 	align:'center',	index:'standard'		},
	      {name:'manufacturer', 	sortable:true,	width:250, 	align:'left',	index:'manufacturer'	},
	      {name:'stateName',		sortable:true,	width:100, 	align:'center',	index:'state'			},
	      {name: "control",			sortable:false,	width:100,	align:'center',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a href="Item.do?cmd=itemForm&itemId=' + rowObject.itemId + '">修改</a>';
	    	  }
	      }
	    ],
	    sortname: 'update_date',
	    sortorder: 'desc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#itemSearchForm').serialize()
	}).trigger('reloadGrid');
}