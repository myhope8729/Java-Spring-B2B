	CommonGrid.init('grid', {
	    url: 'DeliveryAddr.do?cmd=deliveryAddrGridAjax',
	    postData: $('#deliveryAddrListForm').serializeJSON(),
	    colNames:['所在地', '地址', '操作'],
	    colModel:[
	      {name:'locationName', 	sortable:true,	width:200, 	align:'left', index:'locationName'	}, 
	      {name:'address', 			sortable:true,	width:250, 	align:'left',	index:'address'			}, 
	      {name: "control",			sortable:false,	width:70,	align:'center', classes:'gridLink',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a class="mg0-auto" data-role="button" data-icon="edit" data-mini="true" data-iconpos="notext" href="DeliveryAddr.do?cmd=deliveryAddrForm&addrId='+rowObject.addrId+'">修改</a>';
	    	  }
	      },
	    ],
	    sortname: 'locationName',
	    sortorder: 'asc',
	    /*pager: jQuery('#gridpager'),*/
	    rowNum:10000
	});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#deliveryAddrListForm').serializeJSON()
	}).trigger('reloadGrid');
}