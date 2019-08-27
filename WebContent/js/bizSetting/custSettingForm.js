$(document).ready(function() {
	CommonGrid.init('grid', {
	    datatype: 'local',
	    data: jsonObj.custData,
	    colNames:['登录名', '单位名称', '所在地', '地址', '联系人', '联系电话', '手机号码'],
	    colModel:[
	      {name:'custUserNo',			sortable:false,	width:200, 	align:'center' 	}, 
	      {name:'custUserName',			sortable:false,	width:200, 	align:'center' 	},
	      {name:'custLocationName',		sortable:false,	width:200, 	align:'center' 	},
	      {name:'custAddress',			sortable:false,	width:200, 	align:'center' 	},
	      {name:'custContactName',		sortable:false,	width:200, 	align:'center' 	},
	      {name:'custTelNo',			sortable:false,	width:200, 	align:'center' 	},
	      {name:'custMobileNo',			sortable:false,	width:200, 	align:'center' 	}
	    ],
	    loadComplete : function() {}
	});
});