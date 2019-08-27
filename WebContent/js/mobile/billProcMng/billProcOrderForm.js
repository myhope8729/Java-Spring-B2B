var billProc = itemGridData.billProc;

itemGridData.colNames=[];
itemGridData.colModel=[];

itemGridData.colNames.push('itemId');
itemGridData.colModel.push({name:'itemId', key:true, hidden:true, editable:true});
itemGridData.colNames.push('jsQty');
itemGridData.colModel.push({name:'jsQty', hidden:true});
itemGridData.colNames.push('jsDisplay');
itemGridData.colModel.push({name:'jsDisplay', hidden:true});

itemGridData.colNames.push('商品');
itemGridData.colModel.push({name:'itemName', sortable:false,
	formatter:function(cellvalue, options, rowObject){
		var rtn = '';
		if ((itemNoCol || '') != '') rtn = eval('rowObject.' + itemNoCol) + '<br/>';
		if ((itemNameCol || '') != '') rtn = rtn + eval('rowObject.' + itemNameCol);
		return rtn;
	}
});

itemGridData.colNames.push('销售价');
if (billProc.priceYn == 'Y'){
	itemGridData.colModel.push({name:'price2', sortable:false,
		formatter:function(cellvalue, options, rowObject){
			var rtn = rowObject.price;
			rtn += "<br/><input type='text' value='" + rowObject.price2 + "' name='price2' rowid='" + rowObject.itemId + "' />";
			return rtn;
		}
	});
}else{
	itemGridData.colModel.push({name:'price', sortable:false});
}

itemGridData.colNames.push('数量');
if (billProc.qtyYn == 'Y'){
	itemGridData.colModel.push({name:'qty2', sortable:false,
		formatter:function(cellvalue, options, rowObject){
			var rtn = rowObject.qty;
			rtn += "<br/><input type='text' value='" + rowObject.qty2 + "' name='qty2' rowid='" + rowObject.itemId + "' />";
			return rtn;
		}
	});
}else{
	itemGridData.colModel.push({name:'qty', sortable:false});
}

itemGridData.colNames.push('金额');
if ((billProc.qtyYn == 'Y') || (billProc.priceYn == 'Y')){
	itemGridData.colModel.push({name:'tot2', sortable:false,
		formatter:function(cellvalue, options, rowObject){
			if (cellvalue == rowObject.tot2){
				var rtn = rowObject.tot;
				rtn += '<br/>' + (rowObject.tot2);
				return rtn;
			}else{
				return cellvalue;
			}
		}
	});
}else{
	itemGridData.colModel.push({name:'tot2', sortable:false,
		formatter:function(cellvalue, options, rowObject){
			var rtn = rowObject.tot2;
			return rtn;
		}
	});
}

CommonGrid.init('gridItem', {
    datatype:"local",
    data: billProc.billLineList,
    colNames: itemGridData.colNames,
    colModel: itemGridData.colModel,
    loadCompleteCB : function() {
    	var ids = jQuery('#gridItem').getDataIDs();
    	for (var i = ids.length - 1; i >= 0; i--){
    		jQuery('#gridItem').jqGrid('editRow', ids[i], {keys:false});
    	}
    },
	onSelectRow: function(id){
    	
	},
	rowNum : 10000
});

$(document).on('change', 'input[name=price2]', changeColValue);
$(document).on('change', 'input[name=qty2]', changeColValue);

function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}

function changeColValue(e)
{
	var obj = $(e.target);
	var rowId = obj.attr("rowid");
	var val = obj.val();
	
	var row = jQuery('#gridItem').getRowData(rowId, true);
	
	var objName = jQuery(obj).attr('name');
	
	if (objName == 'price2')
	{
		row.price2 = val;
		if (!isNumeric(val)){
			KptApi.showError(invalidNumber);
			obj.focus();
			return;
		}
	}
	if (objName == 'qty2')
	{
		row.qty2 = val;
		if (!isNumeric(val)){
			KptApi.showError(invalidNumber);
			obj.focus();
			return;
		}
		
		var jsQty1 = '';
		var jsQty2 = '';
		var jsQty = '';
		
		if (((jsCol || '') != '') && (eval('row.' + jsCol) || '') != ''){
			jsQty1 = Math.floor(val / eval('row.' + jsCol));
			jsQty2 = val % (eval('row.' + jsCol));
			jsQty = jsQty1 + (jsQty2 /  (eval('row.' + jsCol)));
			
			if ((jsYn == 'Y') && (jsQty2 > 0)){
				KptApi.showError(invalidJSQtyMsg);
				obj.focus();
				obj.val(row.qty2);
				return;
			}
			
			var jsVal = jsQty1 + '件';
			
			if (jsQty2 > 0)
			{
				jsVal += "+" + jsQty2;
			}
			row.jsQty = jsQty + '';
			row.jsDisplay = jsVal;
		}
	}
	
	totalAmt = round((parseFloat(row.price2) * parseFloat(row.qty2)), 3);	
	tempStr = row.tot + "<br/>" + totalAmt;
	jQuery('#gridItem').setCell(rowId, "tot2", tempStr);
}

function saveBillProc(saveFlag)
{
	if (!validationCheck()) return false;
	
	var items = jQuery('#gridItem').jqGrid("getGridParam", "data");
	var postData = {itemsStr: JSON.stringify(items)};
	
	var formArray = jQuery('#billProcOrderForm').serializeArray();
	for(i=0; i<formArray.length; i++) {
		eval("postData." + formArray[i].name + "= formArray[i].value ");
	}
	postData.saveFlag = saveFlag;
	
	$.mvcAjax({
		url 	: jQuery('#billProcOrderForm').attr("action"),
		data 	: postData,
		dataType: 'json',
		success : function(data, ts)
		{
			if (data.result.resultCode == result.FAIL)
			{
				KptApi.showInfo(data.result.resultMsg);
			}
			else
			{
				window.location.href = afterSavingUrl;
			}
		}
	});
}

function validationCheck(){
	var ids = jQuery('#gridItem').getDataIDs();
	
	var rtn = true;
	
	jQuery.each(ids, function(i, id){
		var row = jQuery('#gridItem').getRowData(id, true);
		
		if (!isNumeric(row.price2)) {
			KptApi.showError(invalidNumber);
			jQuery('#gridItem').setSelection(id);
			rtn = false;
		}
		
		if (row.price2 <= 0)
		{
			KptApi.showError(wrongPrice);
			jQuery('#gridItem').setSelection(id);
			rtn = false;
		}
		
		if (!isNumeric(row.qty2)) {
			KptApi.showError(invalidNumber);
			jQuery('#gridItem').setSelection(id);
			rtn = false;
		}
		
		if (row.qty2 <= 0)
		{
			KptApi.showError(wrongQty);
			jQuery('#gridItem').setSelection(id);
			rtn = false;
		}
		
		if ((jsCol || '') != ''){
			if (eval('row.' + jsCol) != ''){
				jsQty1 = Math.floor(row.qty2 / eval('row.' + jsCol));
				jsQty2 = row.qty2 % (eval('row.' + jsCol));
				jsQty = jsQty1 + (jsQty2 /  (eval('row.' + jsCol)));
				
				if ((jsYn == 'Y') && (jsQty2 > 0)){
					KptApi.showError(invalidJSQtyMsg);
					jQuery('#gridItem').setSelection(id);
					rtn = false;
				}
			}
		}
	});
	
	return rtn;
}

function rejectBillProc(){
	KptApi.confirm(confirmReject, function(){
		if ($("#procNote").val() == ''){
			KptApi.showError(emptyNote);
			return;
		}
		submitAjax();
	});
}

function submitAjax()
{
	$.mvcAjax({
		url 	: "BillProc.do?cmd=rejectDocumentAjax",
		data 	: jQuery('#billProcOrderForm').serialize(),
		dataType: 'json',
		success : function(data, ts)
		{
			if (data.result.resultCode == result.FAIL)
			{
				KptApi.showInfo(data.result.resultMsg);
			}
			else
			{
				window.location.href = afterSavingUrl;
			}
		}
	});
}