
initCartGrid();
initBpGrid();

function initCartGrid()
{
	//if (cartGridData.data.length < 1) return;
	cartGridData.colNames.push('item_id');
	cartGridData.colModel.push({name:'itemId', key:true, hidden: true});
	cartGridData.colNames.push("价格");
	cartGridData.colNames.push("数量");
	cartGridData.colNames.push("金额(元)");
	cartGridData.colNames.push("备注");
	
	cartGridData.colModel.push( {
		name: "price2", index: "price2", align : "right", 
		formatter: function(cellValue, option, rowObject){
			rowObject.js_display = $.trim(rowObject.js_display);
			if (rowObject.qty == rowObject.qty2){
				if (rowObject.js_display != "")
					return rowObject.qty + "<br />(" + rowObject.js_display + ")";
				else
					return rowObject.qty;
			} else {
				
				return $.jgrid.template("<span class='org-price'>({0})</span><br />{1} ", rowObject.qty, (rowObject.js_display != "")? rowObject.qty2 + " (" + rowObject.js_display + ")" : rowObject.qty2);
			}
		}
	});
	cartGridData.colModel.push( {
		name: "qty", index: "qty", align : "right", 
		editable:true, 
		formatter: function(cellValue, option, rowObject){
			if (rowObject.qty == rowObject.qty2){
				return rowObject.qty;
			}else{
				return $.jgrid.template("<span class='org-price'>({0})</span><br />{1} ", rowObject.qty, rowObject.qty2);
			}
		}
	});
	cartGridData.colModel.push( {name: "total_amt", index: "total_amt", align : "right", formatter: function(cellValue, option, rowObject){
		if (rowObject.total_amt == rowObject.total_amt2){
			return rowObject.total_amt;
		}else{
			return $.jgrid.template("<span class='org-price'>({0})</span><br />{1} ", rowObject.total_amt, rowObject.total_amt2);
		}
	}} );
	cartGridData.colModel.push( {name: "item_note", index: "item_note", editable:true} );
	
	cartGridObj = $("#cartGrid");
	cartGridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype: "local",
		data: cartGridData.data,
		postData: {},
		colNames: cartGridData.colNames,
		colModel: cartGridData.colModel,
		//sortname: 'bill_id',	    
		pager: false,
		radioOption:true,
		rowNum: 10000,
	    loadCompleteCB : function(data) {
			calcTotalAmtAll(cartGridObj);
		}
	}));
}

function initBpGrid()
{
	if (bpList.length < 1) return;
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

function calcTotalAmtAll(cartGrid)
{
	var items = cartGrid.jqGrid("getGridParam", "data");
	cartTotal = 0.0;
	for (var i=0; i<items.length; i++)
	{
		var t = items[i].total_amt;
		t = parseFloat(t);
		if (t == undefined)
			t = 0.0;
		cartTotal += t;
	}
	$("#cartTotal").html( round(cartTotal,3) );
}