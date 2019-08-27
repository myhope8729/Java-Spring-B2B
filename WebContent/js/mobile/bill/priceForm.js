var lastSel = 0;

jQuery(document).ready(function(){
	
	initCartGrid();
	$(document).on('change', 'input[name^=new_price]', changePrice);
	
	$("#btnSaveDraft").click(function(){
		saveDraft();
	});
	
	$("#btnSave").click(function(){
		savePrice();
	});
});

function initCartGrid()
{
	gridColNames = new Array();
	gridColNames.push(gridData.ncpColDesc.join("<br/>"));
	gridColNames.push("前次入库价<br/>本次入库价<br/>成本价");
	gridColNames.push("入库数量<br/>入库金额(元)");
	for (i=0;i<gridData.priceColDesc.length; i++){
		gridColNames.push(gridData.priceColDesc[i] + "(旧)<br/>" + gridData.priceColDesc[i] + "(新)");
	}
	gridColNames.push("备注");
	
	gridColModel = new Array();
	gridColModel.push({name: "properties", align : "center", 
		formatter:function(cellvalue, options, rowObject){
			var html = "";
			for (i = 0; i<gridData.ncpColNames.length; i++){
				if (i==0){
					html = eval("rowObject." + gridData.ncpColNames[i]);
				}else{
					html = html + "<br/>" + eval("rowObject." + gridData.ncpColNames[i]);
				}
			}
			return html;
		}
	});
	gridColModel.push({name: "priceIn", align: "center", 
		formatter:function(cellvalue, options, rowObject){
			var price2 = "";
			var qty2 = "";
			var tot2 = "";
			if (isDraft){
				price2 = rowObject.price;
				qty2 = rowObject.qty;
				tot2 = rowObject.total;
			}else{
				price2 = rowObject.price2;
				qty2 = rowObject.qty2;
				tot2 = rowObject.tot2;
			}
			return rowObject.priceIn + "<br/>" + price2 + "<br/>" + rowObject.cost + 
				"<input type='hidden' class='cost' name='cost' value='" + rowObject.cost + "' />" +
				"<input type='hidden' name='price' value='" + price2 + "' />" +
				"<input type='hidden' name='qty' value='" + qty2 + "' />" + 
				"<input type='hidden' name='tot' value='" + tot2 + "' />" +
				"<input type='hidden' name='itemId' value='" + rowObject.itemId + "' />" +
				"<input type='hidden' name='priceIn' value='" + rowObject.priceIn + "' />";
		}
	});
	
	gridColModel.push({name: "qty", align: "center", 
		formatter:function(cellvalue, options, rowObject){
			if (isDraft){
				return rowObject.qty + "<br/>" + rowObject.total;
			}
			return rowObject.qty2 + "<br/>" + rowObject.tot2;
		}
	});
	for (i=0;i<gridData.priceColNames.length; i++){
		var priceColName = gridData.priceColNames[i];
		gridColModel.push({name: "price" + i, align: "center", formatoptions:{colNo:i},
			formatter:function(cellvalue, options, rowObject){
				var html = "";
				var oldPrice = "";
				var newPrice = "";
				if (isDraft){
					oldPrice = eval("rowObject.d" + (options.colModel.formatoptions.colNo + 1) + "1");
					newPrice = eval("rowObject.d" + (options.colModel.formatoptions.colNo + 1) + "2");
				}else{
					oldPrice = eval("rowObject." + priceColName);
					newPrice = eval("rowObject." + priceColName);
				}
				
				var percentage = 0;
				html = "<font color='#15428B'><input type='text' name='old_price" + options.colModel.formatoptions.colNo + 
					"' readonly class='chPrice oldPrice' value='" + oldPrice + "'/></font>";
				if (rowObject.cost != "" && rowObject.cost != "0" && oldPrice != "" && oldPrice != "0"){
					percentage = Math.round((parseFloat(oldPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(oldPrice)) / 10;
					html = html + "<font color='#15428B'>(" + percentage + "%)</font>";
				}
				html = html + "<br/><input type='text' class='chPrice' name='new_price" + options.colModel.formatoptions.colNo + 
					"' value='" + newPrice + "' /><span class='new_percentage'>";
				if (rowObject.cost != "" && rowObject.cost != "0" && newPrice != "" && newPrice != "0"){
					percentage = Math.round((parseFloat(newPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(newPrice)) / 10;
					html = html + "(" + percentage + "%)";
				}
				html = html + "</span>";
				return html;
				
				/*var html = "";
				var thisPrice = eval("rowObject." + priceColName);
				var percentage = 0;
				html = "<font color='#15428B'><input type='text' name='old_price" + options.colModel.formatoptions.colNo + 
					"' readonly class='chPrice oldPrice' value='" + thisPrice + "'/></font>";
				if (rowObject.cost != "" && rowObject.cost != "0" && thisPrice != "" && thisPrice != "0"){
					percentage = Math.round((parseFloat(thisPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(thisPrice)) / 10;
					html = html + "<font color='#15428B'>(" + percentage + "%)</font>";
				}
				html = html + "<br/><input type='text' class='chPrice' name='new_price" + options.colModel.formatoptions.colNo + 
					"' value='" + thisPrice + "' /><span class='new_percentage'>";
				if (percentage != 0){
					html = html + "(" + percentage + "%)";
				}
				html = html + "</span>";
				return html;*/
			}
		});
	}
	gridColModel.push({name:"note", align:"left", editable:true});
	
	gridObj = $("#grid");
	gridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype:"local",
		data: billItems,
		colNames: gridColNames,
		colModel: gridColModel,
		//sortname: 'bill_id',
		onSelectRow: function(id){
	    	if (id) {
				var row = gridObj.getRowData(id);
				lastSel = id;
				gridObj.jqGrid('editRow', id, true);
			}
		},
		loadCompleteCB : function() {
	    	var ids = jQuery('#grid').getDataIDs();
	    	for (var i = ids.length - 1; i >= 0; i--){
	    		jQuery('#grid').jqGrid('editRow', ids[i], {keys:false});
	    	}
	    },
		rownumbers:true,
		rowNum:10000
	}));
}

function changePrice(e)
{
	var obj = $(e.target);
	var val = obj.val();
	
	// validation need in val.
	if (! isNumeric(val)) {
		KptApi.showError("Please input the correct number.");
		obj.focus();
		return;
	}
	
	var cost = obj.closest("tr").find(".cost").val();
	var newPercentage = Math.round((parseFloat(val) - parseFloat(cost)) * 1000 / parseFloat(val)) / 10 ;
	
	obj.closest("td").find(".new_percentage").html("(" + newPercentage + "%)");
}

function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}
/**
 * Save the order.
 */

function validateForm(){
	var ids = jQuery('#grid').getDataIDs();
	var result = true;
	if (ids.length == 0){
		KptApi.showError("没有调价商品记录，不能提交单据");
		return false;
	}
	jQuery("input[name^=new_price]").each(function(){
		if (jQuery(this).val() == "" || jQuery(this).val() == "0"){
			KptApi.showError("请录入价格！");
			jQuery(this).focus();
			result = false;
			return false;
		}
		if (jQuery(this).val() < 0){
			KptApi.showError("价格不能为负数！");
			jQuery(this).focus();
			result = false;
			return false;
		}
	});
	return result;
}
function savePrice()
{
	if (validateForm()){
		$("#userItemsForm").submit();
	}
}

function saveDraft()
{
	if (validateForm()){
		$("#draftFlg").val("Y");
		$("#userItemsForm").submit();
	}
}