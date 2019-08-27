$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'WorkFlow.do?cmd=workFlowGroupGridAjax',
	    postData: $('#workFlowGroupSearchForm').serialize(),
	    colNames:['分组名称', '订货方', '处理人', '处理条件', '备注', '状态', '操作', 'seqNo'],
	    colModel:[
	      {name:'workFlowGroupName',	sortable:true,	width:130, 	align:'left', index:'workFlowGroupName'	}, 
	      {name:'custShortName', 		sortable:false,	width:500,	align:'left', 
	    	  formatter:function(cellValue, opiotns, rowObject){
	    		  var obj = rowObject.custShortNameList;
	    		  var rtn = '';
	    		  jQuery.each(obj, function(i, item){
	    			  rtn += item + '&nbsp;&nbsp;';
	    		  });
	    		  rtn += '<br/>' + '<a href="WorkFlow.do?cmd=workFlowGroupCustForm&workFlowId='+rowObject.workFlowId+'&seqNo=' + rowObject.seqNo + '">设置</a>';
	    		  return rtn;
	    	  }
	      },
	      {name:'groupEmpList',		sortable:false,	width:250,	align:'left',
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
	      {name:'cond',				sortable:true,	width:200,	align:'left',	index:'cond'				},
	      {name:'note',				sortable:true,	width:200,	align:'left', 	index:'note'				},
	      {name:'stateName',		sortable:true,	width:100,	align:'center', index:'state'				},
	      {name: "control",			sortable:false,	width:100,	align:'center', 
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a href="WorkFlow.do?cmd=workFlowGroupForm&workFlowId='+rowObject.workFlowId+'&seqNo=' + rowObject.seqNo + '">修改</a>';
	    	  }
	      },
	      {name:'seqNo',			hidden:true																},
	    ],
	    sortname: 'seq_no',
	    sortorder: 'desc',
	    pager: jQuery('#gridpager')
	});
});
