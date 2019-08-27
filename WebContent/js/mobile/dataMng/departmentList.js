	CommonGrid.init('grid', {
		url: 'Dept.do?cmd=departmentGridAjax',
		postData: $('#departmentListFrm').serialize(),
		datatype: "json",
		colNames:['部门编号', '部门名称', '状态', '操作'],
		colModel:[ 
		      {name:'deptNo',  			index:'deptNo',		sortable:false,	align:'left', width:170	}, 
		      {name:'deptName',  		index:'deptName',	sortable:false,	align:'left',	width:200	}, 
		      {name:'stateName', 		index:'stateName',	sortable:false,	align:'center', width:100	},
		      {name: "control",	  		align: "center",	sortable:false,	width:80,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  return '<a class="mg0-auto" data-role="button" data-iconpos="notext" data-mini="true" data-theme="a" data-icon="edit" href="Dept.do?cmd=departmentForm&deptId='+rowObject.deptId+'">修改</a>';
		    	  } 
		      }
	    ],
		sortname: gridPager.sortname || 'deptName',
		rowNum :  10000,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#departmentListFrm').serialize()
	}).trigger('reloadGrid');
}