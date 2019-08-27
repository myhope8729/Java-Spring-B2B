var searchForm = null;
var gridObj = null;
var cartGridObj = null;
var addrGridObj = null;
var itemPriceCol = null;
var itemPriceColDesc = null;

jQuery(document).ready(function(){
	
	cartGridData.colNames.push('item_id');
	cartGridData.colModel.push({name:'itemId', key:true, hidden: true});
	
	initCartGrid();
	
	initBpGrid();
	
});

function initCartGrid()
{
	if (cartGridData.data.length < 1) return;
	cartGridData.colNames.push("价格");
	cartGridData.colNames.push("数量");
	cartGridData.colNames.push("金额(元)");
	cartGridData.colNames.push("备注");
	cartGridData.colNames.unshift("图片");
	
	cartGridData.colModel.push( {
		name: "price2", index: "price2", align : "right", width:100,
		formatter: function(cellValue, option, rowObject){
			if (rowObject.price == rowObject.price2){
				return rowObject.price2;
			}else{
				return $.jgrid.template("<span class='org-price'>({0})</span><br />{1} ", rowObject.price, rowObject.price2);
			}
		}
	});
	cartGridData.colModel.push( {
		name: "qty", index: "qty", align : "right", width:100,
		editable:true, 
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
	cartGridData.colModel.push( {name: "total_amt", index: "total_amt", align : "right", width:100,
		formatter: function(cellValue, option, rowObject){
			if (rowObject.total_amt == rowObject.total_amt2){
				return rowObject.total_amt;
			}else{
				return $.jgrid.template("<span class='org-price'>({0})</span><br />{1} ", rowObject.total_amt, rowObject.total_amt2);
			}
		}
	});
	cartGridData.colModel.push( {name: "item_note", index: "item_note", editable:true, width:150} );
	cartGridData.colModel.unshift( {name: "itemSmallImg", index: "item_img_path", width:80, align: "center", formatter: funcImg} );
	
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
	    rownumbers:true,
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
	       {name:'userName', 		label:'处理方',		width:'250', 	align:'left',		sortable:false	},
	       {name:'empName', 		label:'处理人',		width:'250',	align:'left',		sortable:false  },
	       {name:'billProcFullName',label:'处理类别',	width:'250',	align:'left',		sortable:false	},
	       {name:'stateName', 		label:'处理状态',	width:'100',	align:'center',		sortable:false	},
	       {name:'procNote',		label:'处理意见',	width:'200',	align:'left',		sortable:false	},
	       {name:'updateDate', 		label:'处理时间',	width:'160',	align:'center',		sortable:false	},
	    ],
	    rowNum : 10000,
		rownumbers:false
	}));
}

function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
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
	
	// set the js display : js_display
	/*if ( itemJsCol != '' ) {
		eval("var cntInPackage = row." + itemJsCol + " || 0;");
		var ret = getJsDisplay(row.qty, cntInPackage, true);
		cartGridObj.setCell(row.itemId, "js_display", ret.existPackage? ret.jsDisplay : " ");
		cartGridObj.setCell(row.itemId, "js_qty", ret.qtyNew);
	}*/
}
