$(document).ready(function(){
	jQuery("#grid").tableDnD({
		onDrop : function(table, row){
			
		}
	});
	
	CommonGrid.init('grid', {
	    url: 'WorkFlow.do?cmd=workFlowGridAjax&formObj="Y"',
	    postData: $('#workFlowForm').serialize(),
	    colNames:['业务类型', '流程名称', '分组审批', '改价格', '改数量', '改运费', '处理人(组)', 'id', '状态'],
	    colModel:[
	      {name:'workFlowTypeName', 	sortable:true,	width:150, 	align:'center',	index:'wf_type'				},
	      {name:'workFlowName', 		sortable:true,	width:150, 	align:'center',	index:'bill_proc_name'		},
	      {name:'groupYnName', 			sortable:true,	width:100, 	align:'center',	index:'group_yn'			},
	      {name:'priceYnName', 			sortable:true,	width:120, 	align:'center',	index:'price_yn'			},
	      {name:'qtyYnName', 			sortable:true,	width:120, 	align:'center',	index:'qty_yn'				},
	      {name:'shipPriceYnName', 		sortable:true,	width:120, 	align:'center',	index:'ship_price_yn'		},
	      {name:'processor', 			sortable:false,	width:150, 	align:'center',
	    	  formatter:function(cellValue, opiotns, rowObject){
	    		  var obj = rowObject.empList;
	    		  var rtn = '';
	    		  jQuery.each(obj, function(i, item){
	    			  rtn += item + '<br/>';
	    		  });
	    		  return rtn;
	    	  }
	      },
	      {name:'workFlowId', 		key:true, hidden:true														},
	      {name:'stateName', 		sortable:false,		width:80, 	align:'center'								}
	    ],
	    sortname: 'wf_type, seq_no',
	    sortorder: 'asc',
	    rowNum: 10000,
	    gridCompleteCB : function(){
	    	var workFlowId = $("#workFlowId").val();
	    	var workFlowType = $("[name=workFlowType]").val();
	    	
	    	jQuery("#grid").setSelection(workFlowId);
	    	
	    	if (workFlowId == undefined || workFlowId == '' || workFlowType == undefined || workFlowType == '') return;
	    	
	    	jQuery("#grid").tableDnDUpdate();
	    },
	    cellEdit:true
	});
	
	if (jQuery('#groupYn').val() == 'N'){
		jQuery('#divEmpList').css('display', 'block');
	}else{
		jQuery('#divEmpList').css('display', 'none');
	}
});

function reloadWorkFlowGrid(obj) {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#workFlowForm').serialize()
	}).trigger('reloadGrid');
}

function submitForm(){
	if (jQuery('#groupYn').val() == 'N'){
		var empListCnt = jQuery("[name=empList]:checked").length;
		if (empListCnt == 0){
			KptApi.showInfo(msgEmp);
			return false;
		}
	}
	
	var arr = new Array();
	var checkGrid = 'N';
	
	jQuery("tr.jqgrow", "#grid").each(function(i){
		var rowId = this.id;
		checkGrid = 'Y';
		var workFlowId = jQuery('#grid').getCell(rowId, "workFlowId");
		arr[i] = {'workFlowId' : workFlowId, 'seqNo' : i + 1};
	});
	
	if (checkGrid == 'N'){
		arr[0] = {'workFlowId' : $("#workFlowId").val(), 'seqNo' : 1};
	}
	
	jQuery('#seqData').val(JSON.stringify(arr));
	jQuery("#workFlowForm").submit();
}

function showEmployerDiv(obj){
	if (jQuery(obj).val() == 'N'){
		jQuery('#divEmpList').css('display', 'block');
	}else{
		jQuery('#divEmpList').css('display', 'none');
	}
}
