$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'DeliveryAddr.do?cmd=deliveryAddrGridAjax',
	    postData: $('#deliveryAddrListForm').serialize(),
	    colNames:['所在地', '地址', '联系人', '联系电话', '默认地址', '说明', '状态', '操作'],
	    colModel:[
	      {name:'locationName', 	sortable:true,	width:200, 	align:'left', 	index:'locationName'	}, 
	      {name:'address', 			sortable:true,	width:250, 	align:'left',	index:'address'			}, 
	      {name:'contactName', 		sortable:true,	width:150,	align:'left', 	index:'contactName'		},
	      {name:'telNo', 			sortable:true,	width:150,	align:'center', index:'telNo'			},
	      {name:'defaultYnName', 	sortable:true,	width:150, 	align:'center',	index:'defaultYn'		},
	      {name:'note', 			sortable:true,	width:250,	align:'left',	index:'note'			},
	      {name:'stateName', 		sortable:true,	width:100, 	align:'center',	index:'state'			},
	      {name: "control",			sortable:false,	width:100,	align:'center', 
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a href="DeliveryAddr.do?cmd=deliveryAddrForm&addrId='+rowObject.addrId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'locationName',
	    sortorder: 'asc',
	    pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#deliveryAddrListForm').serialize()
	}).trigger('reloadGrid');
}