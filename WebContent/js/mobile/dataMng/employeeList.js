var searchForm = null;
var gridObj = null;
	
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
		colNames:['所在部门', '姓名', '操作'],
		colModel:[ 
		      {name:'dept.deptName',  	index:'dept_no',	sortable:true,		align:'left',	width: 250,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  if (rowObject.dept != undefined && rowObject.dept.deptName != undefined) {
		    		  if (rowObject.dept.deptNo != undefined && rowObject.dept.deptNo !=''){
		    			  return '(' + rowObject.dept.deptNo + ') <br />' + rowObject.dept.deptName;
		    		  }else{
		    			  return rowObject.dept.deptName;
			    		  }
		    		  } else {
		    			  return "";
		    		  }
		    	  }
		      },
			      {name:'empName',		 	index:'emp_name',	sortable:true,		align:'center',	width: 150},
			      {name: "control",   		align: "center",	sortable: false,	width: 100,
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  var html = '<a class="mg0-auto" href="User.do?cmd=employeeForm&empId='+rowObject.empId+'" data-role="button" data-icon="edit" data-mini="true" data-iconpos="notext">修改</a>';
			    		  //html += '<a target="_blank" href="User.do?cmd=employeeQRCodeForm&empId='+rowObject.empId+'" data-role="button" data-mini="true">二维码</a></div>';
			    		  return html;
			    	  } 
			      },			      
			    ],
		sortname: gridPager.sortname || 'emp_name',
		//rowNum :  gridPager.rowNum,
		//page :  gridPager.page,
		rowNum:10000,
	    pager: jQuery('#gridpager')
	});


function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: searchForm.serialize()
	}).trigger('reloadGrid');
}