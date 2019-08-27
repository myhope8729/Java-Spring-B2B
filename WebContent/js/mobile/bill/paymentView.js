var cartGrid = null;
var bpGrid = null;
initCartGrid();
initBpGrid();

function initCartGrid()
{
	//if (bpList.length < 1) return;
	cartGrid = $("#cartGrid");
	cartGrid.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype: "local",
		data: detailList || [],
		colModel:[
	       {name:'paytypeName', 	label:'预付款名称',		width:'200', 	align:'center',		sortable:false, formatter: function(cellValue, option, rowObject){
	    	   return (cellValue == '')? rowObject.paytype1: cellValue;
	       }},
	       {name:'paytype2', 		label:'款项名称',		width:'150',	align:'center',		sortable:false  },
	       {name:'tot',				label:'收款金额(元)',	width:'200',	align:'center',		sortable:false, formatter: function(cellValue, option, rowObject){
	    	   return billTotalAmt;
	       }},
	       {name:'tot', 			label:'款项金额(元)',	width:'200',	align:'center',		sortable:false	},
	    ],
	    rowNum : 10000,
		rownumbers:false
	}));
}

function initBpGrid()
{
	//if (bpList.length < 1) return;
	bpGrid = $("#bpGrid");
	bpGrid.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype: "local",
		data: bpList || [],
		colModel:[
   	       {name:'userName', 			label:'处理方',		width:'150', 	align:'center',		sortable:false	},
   	       {name:'empName', 			label:'处理人',		width:'150',	align:'center',		sortable:false  },
   	       {name:'billProcFullName',	label:'处理类别',	width:'200',	align:'center',		sortable:false	},
   	       {name:'stateName', 			label:'处理状态',	width:'200',	align:'center',		sortable:false	},
   	       {name:'updateDate', 			label:'处理时间',	width:'200',	align:'center',		sortable:false,	
   	    	   	formatter:function(cellvalue, options, rowObj) {
   	    			  return cellvalue.substr(0, 10);
   		    	}
   	       },
   	    ],
	    rowNum : 10000,
		rownumbers:false
	}));
}
