$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'CustSearch.do?cmd=custSearchGridAjax',
	    postData: $('#custSearchForm').serialize(),
	    colNames:['单位名称', '地址', '联系人', '联系电话', '业务简介', '类别', '注册日期', '操作'],
	    colModel:[
	      {name:'custUserName',			sortable:true,	width:200, 	align:'left', 	index:'custUserName'	},
	      {name:'address',				sortable:true,	width:400,	align:'left', 	index:'address'			},
	      {name:'contactName', 			sortable:true,	width:100, 	align:'left',	index:'contactName'		},
	      {name:'telNo', 				sortable:true,	 			align:'center',	index:'telNo'			},
	      {name:'note',		 			sortable:false,	 			align:'left'							},
	      {name:'custTypeName', 		sortable:true,	 			align:'left',	index:'custTypeName'	},
	      {name:'createDate',		 	sortable:true,	 			align:'center',	index:'createDate'		},	     
	      {name: "control",		hidden:true,	sortable:false,	widht:100,	align:'center', classes:'gridLink',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a target="_blank" href="CustSearch.do?cmd=custSettingForm&hostId=' + rowObject.hostUserId + '&custId=' + rowObject.custUserId + '">设置</a>';
	    	  }
	      },
	    ],
	    sortname: 'user_id',
	    sortorder: 'desc',
	    rowNum: 20, 
	    pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#custSearchForm').serialize()
	}).trigger('reloadGrid');
}