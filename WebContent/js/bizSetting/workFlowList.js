$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'WorkFlow.do?cmd=workFlowGridAjax',
	    postData: $('#workFlowSearchForm').serialize(),
	    colNames:['业务类型', '流程序号', '流程名称', '分组审批', '改价格', '改数量', '改运费', '最小金额(元)', '最大金额(元)', '处理人(组)', '状态', '操作'],
	    colModel:[
	      {name:'workFlowTypeName',		sortable:true,	width:150, 	align:'center', index:'wf_type'				}, 
	      {name:'seqNo', 				sortable:true,	width:100,	align:'right',	index:'seq_no'				},
	      {name:'workFlowName', 		sortable:true,	width:220, 	align:'left',	index:'bill_proc_name'		},
	      {name:'groupYnName', 			sortable:true,	width:100, 	align:'center',	index:'group_yn'			},
	      {name:'priceYnName', 			sortable:true,	width:120, 	align:'center',	index:'price_yn'			},
	      {name:'qtyYnName', 			sortable:true,	width:120, 	align:'center',	index:'qty_yn'				},
	      {name:'shipPriceYnName', 		sortable:true,	width:120, 	align:'center',	index:'ship_price_yn'		},
	      {name:'minCost', 				sortable:true,	width:160, 	align:'right',	index:'min_cost'			},
	      {name:'maxCost', 				sortable:true,	width:160, 	align:'right',	index:'max_cost'			},
	      {name:'processor', 			sortable:false,	width:150, 	align:'left',
	    	  formatter:function(cellValue, opiotns, rowObject){
	    		  var obj = rowObject.empList;
	    		  var rtn = '';
	    		  jQuery.each(obj, function(i, item){
	    			  rtn += item + '<br/>';
	    		  });
	    		  return rtn;
	    	  }
	      },
	      {name:'stateName',			sortable:true,	width:100, 	align:'center',	index:'state'				},
	      {name: "control",				sortable:false,	widht:80,	align:'center', 
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  var str = '<a href="WorkFlow.do?cmd=workFlowForm&workFlowId='+rowObject.workFlowId+'&workFlowType=' + rowObject.workFlowType + '">修改</a>';
	    		  if (rowObject.groupYn == 'Y')
    			  {
	    			  str += '<a style="margin-left:5px;" href="WorkFlow.do?cmd=workFlowGroupList&workFlowId='+rowObject.workFlowId+'">设置分组</a>';
    			  }
	    		  return str;
	    	  }
	      },
	    ],
	    sortname: 'wf_type, seq_no',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#workFlowSearchForm').serialize()
	}).trigger('reloadGrid');
}