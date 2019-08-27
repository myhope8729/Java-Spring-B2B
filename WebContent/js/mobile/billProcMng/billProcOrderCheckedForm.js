var billProc = itemGridData.billProc;

var colNames = [];
var colModels = [];

colNames.push('itemId');
colModels.push({name:'itemId', key:true, hidden:true, editable:false});

colNames.push('商品');
colModels.push({name:'itemName', sortable:false, width:'250', 
	formatter:function(cellvalue, options, rowObject){
		var rtn = '';
		if ((itemNoCol || '') != '') rtn = (eval('rowObject.' + itemNoCol) || '') + '<br/>';
		if ((itemNameCol || '') != '') rtn = rtn + (eval('rowObject.' + itemNameCol) || '') + '<br/>';
		if ((itemUnitCol || '') != '') rtn = rtn + (eval('rowObject.' + itemUnitCol) || '') + '<br/>';
		return rtn;
	}
});

colNames.push('价格');
colModels.push({name:'price', sortable:false,
	formatter:function(cellValue, options, rowObject){
		if (rowObject.price != rowObject.price2){
			return '<font style="color:#808080">' + rowObject.price + '</font>' + '<br/>' + rowObject.price2;
		}else{
			return rowObject.price2;
		}
	}
});

colNames.push('数量');
colModels.push({name:'qty', sortable:false, 
	formatter:function(cellValue, options, rowObject){
		if (rowObject.qty != rowObject.qty2){
			return '<font style="color:#808080">' + rowObject.qty + '</font>' + '<br/>' + rowObject.qty2;
		}else{
			return rowObject.qty2;
		}
	}
});

colNames.push('金额');
colModels.push({name:'total', sortable:false,
	formatter:function(cellValue, options, rowObject){
		if (rowObject.tot != rowObject.tot2){
			return '<font style="color:#808080">' + rowObject.tot + '</font>' + '<br/>' + rowObject.tot2;
		}else{
			return rowObject.tot2;
		}
	}
});

CommonGrid.init('gridItem', {
    datatype:"local",
    data: billProc.billLineList,
    colNames:colNames,
    colModel:colModels,
	rowNum : 10000
});	
