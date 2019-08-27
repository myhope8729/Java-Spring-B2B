var cartGrid = null;
var bpGrid = null;
jQuery(document).ready(function(){
	
	initCartGrid();
	
	initBpGrid();
});

function initCartGrid()
{
	//if (bpList.length < 1) return;
	cartGrid = $("#cartGrid");
	cartGrid.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype: "local",
		data: detailList || [],
		colModel:[
	       {name:'paytypeName', 	label:'预付款名称',		width:'250', 	align:'left',		sortable:false, formatter: function(cellValue, option, rowObject){
	    	   return (cellValue == '')? rowObject.paytype1: cellValue;
	       }},
	       {name:'paytype2', 		label:'款项名称',		width:'250',	align:'left',		sortable:false  },
	       {name:'tot',				label:'收款金额(元)',	width:'200',	align:'right',		sortable:false, formatter: function(cellValue, option, rowObject){
	    	   return billTotalAmt;
	       }},
	       {name:'tot', 			label:'款项金额(元)',	width:'200',	align:'right',		sortable:false	},
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
	       {name:'userName', 			label:'处理方',		width:'250', 	align:'left',		sortable:false	},
	       {name:'empName', 			label:'处理人',		width:'250',	align:'left',		sortable:false  },
	       {name:'billProcFullName',	label:'处理类别',	width:'200',	align:'left',		sortable:false	},
	       {name:'stateName', 			label:'处理状态',	width:'100',	align:'center',		sortable:false	},
	       {name:'procNote',			label:'处理意见',	width:'150',	align:'left',		sortable:false	},
	       {name:'updateDate', 			label:'处理时间',	width:'150',	align:'center',		sortable:false	},
	    ],
	    rowNum : 10000,
		rownumbers:false
	}));
}
