	CommonGrid.init('grid', {
	    url: 'WorkFlow.do?cmd=workFlowGroupGridAjax',
	    postData: $('#workFlowGroupSearchForm').serializeJSON(),
	    colNames:['分组', '订货方', '处理人', '处理条件', '操作', 'seqNo'],
	    colModel:[
	      {name:'workFlowGroupName',	sortable:false,	width:120, 	align:'center', index:'workFlowGroupName'	}, 
	      {name:'custShortName', 		sortable:false,	width:550,	align:'left',   classes:'gridLink',
	    	  formatter:function(cellValue, opiotns, rowObject){
	    		  var obj = rowObject.custShortNameList;
	    		  var rtn = '';
	    		  jQuery.each(obj, function(i, item){
	    			  rtn += item + '&nbsp;&nbsp;';
	    		  });
	    		  rtn += '<br/>' + '<a data-role="button" data-mini="true" data-inline="true"  href="WorkFlow.do?cmd=workFlowGroupCustForm&workFlowId='+rowObject.workFlowId+'&seqNo=' + rowObject.seqNo + '">设置</a>';
	    		  return rtn;
	    	  }
	      },
	      {name:'groupEmpList',		sortable:false,	width:200,	align:'left',	classes:'gridLink',
	    	  formatter:function(cellValue, opiotns, rowObject){
	    		  var obj = rowObject.empList;
	    		  var rtn = '';
	    		  jQuery.each(obj, function(i, item){
	    			  rtn += item + '&nbsp;&nbsp;';
	    		  });
	    		  rtn += '<br/>' + '<a href="WorkFlow.do?cmd=workFlowGroupEmpForm&workFlowId='+rowObject.workFlowId+'&seqNo=' + rowObject.seqNo + '">设置</a>';
	    		  return rtn;
	    	  }
	      },
	      {name:'cond',				sortable:false,	width:250,	align:'center' },
	      {name: "control",			sortable:false,	widht:80,	align:'center', classes:'gridLink',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a class="mg0-auto" data-role="button" data-icon="edit" data-iconpos="notext" href="WorkFlow.do?cmd=workFlowGroupForm&workFlowId='+rowObject.workFlowId+'&seqNo=' + rowObject.seqNo + '">修改</a>';
	    	  }
	      },
	      {name:'seqNo',			hidden:true																},
	    ],
	    sortname: 'seq_no',
	    sortorder: 'desc',
	    pager: jQuery('#gridpager')
	});
