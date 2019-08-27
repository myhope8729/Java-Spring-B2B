var billProc = itemGridData.billProc;

if (billProc.priceYn == 'Y'){
	itemGridData.colNames.push('确认价格');
	itemGridData.colModel.push({name:'price2', width:100, sortable:false,
		formatter:function(cellvalue, options, rowObject){
			rtn = "<input type='text' class='form-control' value='" + round(cellvalue, 2) + "' name='price2' rowid='" + rowObject.itemId + "' />";
			return rtn;
		}
	});
}
itemGridData.colNames.push('数量');
itemGridData.colModel.push({name:'qtyUnion', align:'right', sortable:false, width:80});
if (billProc.qtyYn == 'Y'){
	itemGridData.colNames.push('确认数量');
	itemGridData.colModel.push({name:'qty2', width:80, sortable:false,
		formatter:function(cellvalue, options, rowObject){
			rtn = "<input type='text' class='form-control' value='" + round(cellvalue, 2) + "' name='qty2' rowid='" + rowObject.itemId + "' />";
			rtn += "<font>" + (rowObject.jsDisplay || '') + "</font>";
			return rtn;
		}
	});
}
itemGridData.colNames.push('金额(元)');
itemGridData.colModel.push({name:'tot2', align:'right', width:100, sortable:false});
itemGridData.colNames.push('备注');
itemGridData.colModel.push({name:'note', editable:true, width:100, sortable:false});
itemGridData.colNames.unshift('图片');
itemGridData.colModel.unshift({name:'itemSmallImg', align:'center', sortable:false, width:80, 
	formatter:function(cellvalue, options, rowObject)
	{
		return "<img src='" + cellvalue + "' width='48px'/>";
	}
});
itemGridData.colNames.push('itemId');
itemGridData.colModel.push({name:'itemId', key:true, hidden:true, editable:true});
itemGridData.colNames.push('jsQty');
itemGridData.colModel.push({name:'jsQty', hidden:true});
itemGridData.colNames.push('jsDisplay');
itemGridData.colModel.push({name:'jsDisplay', hidden:true});

CommonGrid.init('gridProc', {
    url: 'BillProc.do?cmd=billProcHistGridAjax',
    postData: $('#billProcSaleForm').serializeJSON(),
    colModel:[
      {name:'userName', 			label:'处理方',		width:'150', 	align:'center',		sortable:false	},
      {name:'empName', 				label:'处理人',		width:'150',	align:'center',		sortable:false  },
      {name:'billProcFullName',		label:'处理类别',	width:'200',	align:'center',		sortable:false	},
      {name:'stateName', 			label:'处理状态',	width:'200',	align:'center',		sortable:false	},
      {name:'updateDate', 			label:'处理时间',	width:'200',	align:'center',		sortable:false,	
   	   	formatter:function(cellvalue, options, rowObj) {
   			  return cellvalue.substr(0, 10);
    	}
      },
   ],
    rowNum : 10000
});

CommonGrid.init('gridItem', {
    datatype:"local",
    data: billProc.billLineList,
    colNames:itemGridData.colNames,
    colModel:itemGridData.colModel,
    loadCompleteCB : function() {
    	var ids = jQuery('#gridItem').getDataIDs();
    	for (var i = ids.length - 1; i >= 0; i--){
    		jQuery('#gridItem').jqGrid('editRow', ids[i], {keys:false});
    	}
    },
	onSelectRow: function(id){
    	if (id) {
			var row = jQuery('#gridItem').getRowData(id);
			jQuery('#gridItem').jqGrid('editRow', id, {keys:false});
		}
	},
	rowNum : 10000,
    autowidth:false
});

$(document).on('change', 'input[name=price2]', changeColValue);
$(document).on('change', 'input[name=qty2]', changeColValue);
$(document).on('change', 'input[name=note]', function(e){
	var obj = $(e.target);
	var rowId = obj.attr("rowid");
	var val = obj.val();
	row = jQuery('#gridItem').getRowData(rowId, true);
	row.note = val;
});


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
		
		if (((jsCol || '') != '') && eval('row.' + jsCol) != ''){
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
			$(obj).parent('td').find('font').text(jsVal);
			
			jQuery('#gridItem').setCell(rowId, "jsQty", jsQty);
			jQuery('#gridItem').setCell(rowId, "jsDisplay", jsVal);
			row.jsQty = jsQty + '';
			row.jsDisplay = jsVal;
		}
	}
	
	row.tot2 = round((row.price2 * row.qty2), 2);
	jQuery('#gridItem').setCell(rowId, "tot2", row.tot2);
}

var blnPriceChange = false;

function saveBillProc(saveFlag)
{
	if (!validationCheck()) return false;
	
	var items = jQuery('#gridItem').jqGrid("getGridParam", "data");
	var postData = {itemsStr: JSON.stringify(items)};
	
	var formArray = jQuery('#billProcSaleForm').serializeArray();
	for(i=0; i<formArray.length; i++) {
		eval("postData." + formArray[i].name + "= formArray[i].value ");
	}
	postData.saveFlag = saveFlag;
	
	if ((blnPriceChange == true) && (saveFlag == 'N')){
		KptApi.confirm(confirmPrice, function(){
			postData.changePrice = 'Y';
			submitAjax(postData);
		},
		function(){
			postData.changePrice = 'N';
			submitAjax(postData);
		});
	}
	else
	{
		postData.changePrice = 'N';
		submitAjax(postData);
	}
}

function submitAjax(postData)
{
	$.mvcAjax({
		url 	: jQuery('#billProcSaleForm').attr("action"),
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
		
		if (parseFloat(row.price) != parseFloat(row.price2)){
			blnPriceChange = true;
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
		data 	: jQuery('#billProcSaleForm').serialize(),
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