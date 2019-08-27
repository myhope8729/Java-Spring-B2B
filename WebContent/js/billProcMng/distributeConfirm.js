$(document).ready(function(){
	
	CommonGrid.init('gridReceipt', {
		url: 'BillProc.do?cmd=distributeConfirmReceiptGridAjax',
		postData: $('#distributeConfirm').serialize(),
	    colNames:gridData.colNames,
	    colModel:gridData.colModel,
		rowNum :  100000,
		viewrecords: false
	});
	
	gridData.confirmNames.push('订货数量');
	gridData.confirmModels.push({name:'qty', align:'right', sortable:false, width: 100});
	
	gridData.confirmNames.push('拣货数量');
	gridData.confirmModels.push({name:'qty2', sortable:false, width:120,
		formatter:function(cellvalue, options, rowObject){
			rtn = "<input type='text' class='form-control' style='width:100%;' value='" + round(rowObject.qty2, 2) + "' name='qty2' rowid='" + (rowObject.billId + rowObject.itemId) + "' />";
			return rtn;
		}
	});
	
	gridData.confirmNames.push('销售价格');
	gridData.confirmModels.push({name:'price', align:'right', sortable:false, width: 100});
	
	gridData.confirmNames.push('确认价格');
	gridData.confirmModels.push({name:'price2', align:'right', sortable:false, width: 120,
		formatter:function(cellvalue, options, rowObject){
			rtn = "<input type='text' class='form-control' style='width:100%;' value='" + round(rowObject.price2, 2) + "' name='price2' rowid='" + (rowObject.billId + rowObject.itemId) + "' />";
			return rtn;
		}
	});
	
	gridData.confirmNames.push('billId');
	gridData.confirmModels.push({name:'billId', hidden:true, align:'right', sortable:false, width: 100});
	gridData.confirmNames.push('itemId');
	gridData.confirmModels.push({name:'itemId', hidden:true, align:'right', sortable:false, width: 100});
	
	CommonGrid.init('gridItem', {
		url: 'BillProc.do?cmd=distributeConfirmGridAjax',
		postData: $('#distributeConfirm').serialize(),
		colNames:gridData.confirmNames,
	    colModel:gridData.confirmModels,
	    rowNum :  100000,
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
		}
	});
	
	$(document).on('change', 'input[name=price2]', changeColValue);
	$(document).on('change', 'input[name=qty2]', changeColValue);
	
});

function changeColValue(e){
	var obj = $(e.target);
	var rowId = obj.attr("rowid");
	var val = obj.val();
	
	var row = jQuery('#gridItem').getRowData(rowId, true);
	
	var objName = jQuery(obj).attr('name');
	
	if (objName == 'price2')
	{
		row.price2 = val;
	}
	if (objName == 'qty2')
	{
		row.qty2 = val;
	}
}

function saveBillPrice(){
	var arr = new Array();
	jQuery.each(jQuery("[name=qty2]"), function(i, obj){
		var billId = jQuery('#gridItem').getCell(i+1, "billId");
		var itemId = jQuery('#gridItem').getCell(i+1, "itemId");
		var price2 = jQuery('#gridItem').getCell(i+1, "price2");
		var qty2 = jQuery('#gridItem').getCell(i+1, "qty2");
		arr[i] = {'billId': billId, 'itemId': itemId, 'price2':jQuery("[name=price2]").eq(i).val(), qty2:$(obj).val()};
	});
	if (arr.length == 0) return false;
	
	jQuery('#userData').val(JSON.stringify(arr));
	
	$.mvcAjax({
		url 	: 'BillProc.do?cmd=changeBillProcPriceAjax',
		data 	: $('#distributeConfirm').serialize(),
		dataType: 'json',
		success : function(data, ts)
		{
			if (data.result.resultCode == result.FAIL)
			{
				KptApi.showInfo(data.result.resultMsg);
			}
			else
			{
				KptApi.showMsg(data.result.resultMsg);
			}
		}
	});
}

function reloadGrid() {
	jQuery('#gridReceipt').jqGrid('setGridParam',{
		postData: $('#distributeConfirm').serialize()
	}).trigger('reloadGrid');
	
	jQuery('#gridItem').jqGrid('setGridParam',{
		postData: $('#distributeConfirm').serialize()
	}).trigger('reloadGrid');
}