jQuery(document).ready(function(){
	CommonGrid.init('grid', {
		url: 'Dept.do?cmd=departmentGridAjax',
		postData: $('#departmentListFrm').serialize(),
		datatype: "json",
		colNames:['部门编号', '部门名称', '是否销售单位', '备注', '状态', '操作'],
		colModel:[ 
		      {name:'deptNo',  			index:'deptNo',		sortable:true,	align:'center', width:150	}, 
		      {name:'deptName',  		index:'deptName',	sortable:true,	align:'left',	width:250	}, 
		      {name:'accountYnName', 	index:'accountYn',	sortable:true,	align:'center', width:100	},
		      {name:'note',    	 		index:'note',		sortable:true,	align:'left',	width:400	},
		      {name:'stateName', 		index:'state',		sortable:true,	align:'center', width:100	},
		      {name: "control",	  		align: "center",	sortable:false,	width:100,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  return '<a href="Dept.do?cmd=departmentForm&deptId='+rowObject.deptId+'">修改</a>';
		    	  } 
		      }
	    ],
		sortname: gridPager.sortname || 'deptNo',
		rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#departmentListFrm').serialize()
	}).trigger('reloadGrid');
}