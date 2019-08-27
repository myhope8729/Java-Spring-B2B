$(document).ready(function() {
	CommonGrid.init('grid', {
	    datatype: 'local',
	    data: jsonObj.custData,
	    colNames:['单位名称', '登录名', '手机号码'],
	    colModel:[
	      {name:'custUserName',			sortable:false,	width:200, 	align:'center' 	},
	      {name:'custUserNo',			sortable:false,	width:150, 	align:'center' 	}, 
	      {name:'custMobileNo',			sortable:false,	width:150, 	align:'center' 	}
	    ]
	});
});