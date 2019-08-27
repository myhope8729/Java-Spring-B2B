var searchForm = null;
var gridObj = null;
	
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
		colNames:['所在部门',  '姓名',  '操作'],
		colModel:[ 
		      {name:'deptName',  	index:'dept_no',	sortable:false,		align:'left',	width: 300,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  if (rowObject.dept != undefined && rowObject.dept.deptName != undefined) {
		    		  if (rowObject.dept.deptNo != undefined && rowObject.dept.deptNo !=''){
		    			  return '(' + rowObject.dept.deptNo + ')' + rowObject.dept.deptName;
		    		  }else{
		    			  return rowObject.dept.deptName;
			    		  }
		    		  } else {
		    			  return "";
		    		  }
		    	  }
		      },
		      {name:'empName',  		index:'emp_name',	sortable:false,	align:'left', width: 200},
		      {name:"control",			align: "center", width: 100, sortable:false,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  return '<a class="mg0-auto" data-role="button" data-iconpos="notext" data-mini="true" data-theme="a" data-icon="edit" href="User.do?cmd=employerPrivForm&empId='+rowObject.empId+'">权限</a>';
		    	  } 
		      },			      
	    ],
		pager: '#gridpager',
		rowNum:10000,
		sortname: gridPager.sortname || 'emp_name',
		
	    pager: jQuery('#gridpager')
	});
