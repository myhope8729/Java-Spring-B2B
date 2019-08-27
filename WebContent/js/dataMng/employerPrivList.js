var searchForm = null;
var gridObj = null;
jQuery(document).ready(function(){
	
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
		colNames:['所在部门', '登录名', '姓名', '职责',/* '电话', '手机',*/ '状态', '操作'],
		colModel:[ 
		      {name:'deptName',  	index:'dept_no',	sortable:true,		align:'left',	width: 300,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  if (rowObject.dept != undefined && rowObject.dept.deptName != undefined) {
			    		  if (rowObject.dept.deptNo != undefined && rowObject.dept.deptNo !=''){
			    			  return '(' + rowObject.dept.deptNo + ') ' + rowObject.dept.deptName;
			    		  }else{
			    			  return rowObject.dept.deptName;
			    		  }
		    		  } else {
		    			  return "";
		    		  }
		    	  }
		      },
		      {name:'empNo',  			index:'emp_no',		sortable:true,	align:'left',	width: 150	},
		      {name:'empName',  		index:'emp_name',	sortable:true,	align:'left',	width: 200	},
		      {name:'empRole',  		index:'emp_role',	sortable:true,	align:'left',	width: 300	},
		      {name:'stateName',  		index:'state',		sortable:true,	align:'center', width: 100	},
		      {name: "state",			sortable:false,		align: "center",	width: 100,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  var html = '<a href="User.do?cmd=employerPrivForm&empId='+rowObject.empId+'">权限</a>';
		    		  return html;
		    	  } 
		      },			      
	    ],
		pager: '#gridpager',
		sortname: gridPager.sortname || 'emp_name',
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});
});