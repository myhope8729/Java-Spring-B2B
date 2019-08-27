var billProc = itemGridData.billProc;

var gridColNames = new Array();
var gridColModels = new Array();

gridColNames.push(itemGridData.colNames.join("<br/>"));
gridColModels.push({name: "properties", align : "center", sortable:false,
	formatter:function(cellvalue, options, rowObject){
		var rtn = "";
		jQuery.each(itemGridData.colModel, function(i, colModel){
			rtn += (eval("rowObject." + colModel.name) || '') + "<br/>";
		});
		rtn += "<input type='hidden' name='cost' value='" + rowObject.cost + "' />";
		rtn += "<input type='hidden' name='itemId' value='" + rowObject.itemId + "' />";
		return rtn;
	}
});

gridColNames.push("前次入库价<br/>本次入库价<br/>成本价");
gridColModels.push({name:"price", align:"center", sortable:false,
	formatter:function(cellvalue, options, rowObject){
		return rowObject.priceIn + "<br/>" + rowObject.price + "<br/>" + rowObject.cost;
	}
});

gridColNames.push("入库数量<br/>入库金额(元)");
gridColModels.push({name:"qty", align:"center", sortable:false,
	formatter:function(cellvalue, options, rowObject){
		return rowObject.qty + "<br/>" + rowObject.total;
	}
});

jQuery.each(itemGridData.priceCols, function(i, col){
	gridColNames.push(col.propertyDesc + "(旧)<br/>" + col.propertyDesc + "(新)");
	gridColModels.push({name : "d" + (i+1) + "2", align:"center", sortable:false,
		formatter:function(cellvalue, options, rowObject){
			var rtn = "";
			var percentage = 0;
			var oldPrice = round(eval("rowObject.d" + (i+1) + "1"), 2);
			var newPrice = round(eval("rowObject.d" + (i+1) + "2"), 2);
			
			rtn += "<font color='#15428B'>";
			rtn += "<input type='text' name='oldPrice_" + (i+1) + "' readonly class='chPrice oldPrice' value='" + oldPrice + "'/>";
			rtn += "</font>";
			if (rowObject.cost != "" && rowObject.cost != "0" && oldPrice != "" && oldPrice != "0"){
				percentage = Math.round((parseFloat(oldPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(oldPrice)) / 10;
				rtn += "<font color='#15428B'>(" + percentage + "%)</font>";
			}
			if (billProc.priceYn == 'Y'){
				rtn += "<br/><input type='text' class='chPrice' name='newPrice_" + (i+1) + "' value='" + newPrice + "' rowid='" + rowObject.itemId + "'/>";
			}else{
				rtn += "<br/><input type='text' class='chPrice oldPrice' name='newPrice_" + (i+1) + "' readonly value='" + newPrice + "' rowid='" + rowObject.itemId + "' />";
			}
			rtn += "<span class='new_percentage'>";
			if (rowObject.cost != "" && rowObject.cost != "0" && newPrice != "" && newPrice != "0"){
				percentage = Math.round((parseFloat(newPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(newPrice)) / 10;
				rtn += "(" + percentage + "%)";
			}
			rtn += "</span>";
			return rtn;
		}
	});
});

gridColNames.push("备注");
gridColModels.push({name:"note", sortable:false,
	formatter:function(cellvalue, options, rowObject){
		rtn = "<input type='text' class='form-control' value='" + rowObject.note + "' name='note' rowid='" + rowObject.itemId + "' />";
		return rtn;
	}
});
gridColNames.push("itemId");
gridColModels.push({name:"itemId", sortable:false, hidden:true, key:true});

CommonGrid.init('gridItem', {
    datatype:"local",
    data: billProc.priceDetailList,
    colNames:gridColNames,
    colModel:gridColModels,
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

CommonGrid.init('gridProc', {
    url: 'BillProc.do?cmd=billProcHistGridAjax',
    postData: $('#billProcPriceForm').serializeJSON(),
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
    rowNum : 10000
});

$(document).on('change', 'input[name^=newPrice]', changeColValue);

function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}

function changeColValue(e)
{
	var obj = $(e.target);
	var rowId = obj.attr("rowid");
	var val = obj.val();
	var objName = jQuery(obj).attr('name');
	
	var row = jQuery('#gridItem').getRowData(rowId, true);
	
	if (!isNumeric(val)){
		KptApi.showError(invalidNumber);
		obj.focus();
		return;
	}
	if (val < 0){
		KptApi.showError(posValPrice);
		obj.focus();
		return;
	}
	
	var cost = obj.closest("tr").find("input[name='cost']").val();
	var newPercentage = Math.round((parseFloat(val) - parseFloat(cost)) * 1000 / parseFloat(val)) / 10 ;
	
	obj.closest("td").find(".new_percentage").html("(" + newPercentage + "%)");
}

function saveBillProc(saveFlag)
{
	if (!validationCheck()) return false;
	$('#saveFlag').val(saveFlag);
	
	$("#priceCols").val(JSON.stringify(itemGridData.priceCols));
	
	$.mvcAjax({
		url 	: jQuery('#billProcPriceForm').attr("action"),
		data 	: jQuery('#billProcPriceForm').serialize(),
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
	
	if (ids.length == 0){
		KptApi.showError("没有调价商品记录，不能提交单据");
		return false;
	}
	
	jQuery("input[name^=newPrice]").each(function(){
		if (!isNumeric(jQuery(this).val())){
			KptApi.showError(invalidNumber);
			jQuery(this).focus();
			rtn = false;
			return false;
		}
		if (jQuery(this).val() == "" || jQuery(this).val() == "0"){
			KptApi.showError(inputPrice);
			jQuery(this).focus();
			rtn = false;
			return false;
		}
		if (jQuery(this).val() < 0){
			KptApi.showError(posValPrice);
			jQuery(this).focus();
			rtn = false;
			return false;
		}
	});
	
	return rtn;
}