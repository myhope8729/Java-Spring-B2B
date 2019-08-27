$(document).ready(function(){
	var billProc = itemGridData.billProc;
	
	itemGridData.colNames.push('数量');
	itemGridData.colModel.push({name:'qtyUnion', align:'right', sortable:false, width:100,
		formatter:function(cellvalue, options, rowObject){
			var rtn = '';
			if ((rowObject.qty || '') == (rowObject.qty2 || '')){
				rtn += round(rowObject.qty2, 2);
			}else{
				rtn += "<font color='#808080'>(" + round(rowObject.qty, 2) + ")  </font>" + round(rowObject.qty2, 2);
			}
			if ((rowObject.jsDisplay || '') != ''){
				rtn += "<br/><font>(" + (rowObject.jsDisplay || '') + ")</font>";
			}
			return rtn;
		}
	});
	
	itemGridData.colNames.push('金额(元)');
	itemGridData.colModel.push({name:'tot2', align:'right', sortable:false, width:100,
		formatter:function(cellvalue, options, rowObject){
			var rtn = '';
			if ((rowObject.tot || '') == (rowObject.tot2 || '')){
				rtn += round(rowObject.tot2, 2);
			}else{
				rtn += "<font color='#808080'>(" + round(rowObject.tot, 2) + ")  </font>" + round(rowObject.tot2, 2);
			}
			return rtn;
		}
	});
	
	itemGridData.colNames.push('备注');
	itemGridData.colModel.push({name:'note', width:150, sortable:false});
	itemGridData.colNames.push('图片');
	itemGridData.colModel.push({name:'itemSmallImg', align:'center', sortable:false, width:80,
		formatter:function(cellvalue, options, rowObject)
		{
			return "<img src='" + cellvalue + "' width='48px' height='32px' />";
		}
	});
	itemGridData.colNames.push('itemId');
	itemGridData.colModel.push({name:'itemId', key:true, hidden:true, editable:true});
	
	CommonGrid.init('gridProc', {
	    url: 'BillProc.do?cmd=billProcHistGridAjax',
	    postData: $('#billProcSaleCheckedForm').serialize(),
	    colModel:[
	       {name:'userName', 			label:'处理方',		width:'250', 	align:'left',		sortable:false	},
	       {name:'empName', 			label:'处理人',		width:'250',	align:'left',		sortable:false  },
	       {name:'billProcFullName',	label:'处理类别',	width:'200',	align:'left',		sortable:false	},
	       {name:'stateName', 			label:'处理状态',	width:'100',	align:'center',		sortable:false	},
	       {name:'procNote',			label:'处理意见',	width:'150',	align:'left',		sortable:false	},
	       {name:'updateDate', 			label:'处理时间',	width:'150',	align:'center',		sortable:false	},
	    ],
	    rowNum : 10000
	});
	
	CommonGrid.init('gridItem', {
	    datatype:"local",
	    data: billProc.billLineList,
	    colNames:itemGridData.colNames,
	    colModel:itemGridData.colModel,
	    rownumbers:true,
		rowNum : 10000
	});	
});