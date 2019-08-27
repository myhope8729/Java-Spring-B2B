var cartGridObj = null;
var lastSel = 0;

jQuery(document).ready(function(){
	
	cartGridData.colNames.push('item_id');
	cartGridData.colModel.push({name:'itemId', key:true, hidden: true});
	
	initCartGrid();
	
	$(document).on('change', 'input[name=tuiQty]', changeQty);
	
	$("#btnSave").click(function(){
		saveReturn();
	});
});

function initCartGrid()
{
	cartGridData.colNames.push("价格");
	cartGridData.colNames.push("数量");
	cartGridData.colNames.push("金额(元)");
	cartGridData.colNames.push("退货数量");
	cartGridData.colNames.push("退货金额(元)");
	cartGridData.colNames.push("备注");
	
	cartGridData.colModel.push({name: "price2", index: "price2", align : "right"});
	cartGridData.colModel.push({name: "qty", index: "qty", align : "right"});
	cartGridData.colModel.push({name: "tot", index: "tot", align : "right", formatter:function(cellvalue, options, rowObject){
		return cellvalue + '<input type="hidden" name="itemId" value="' + rowObject.itemId + '"/>\
		<input type="hidden" name="price" value="' + rowObject.price2 + '"/>';
	}});
	
	cartGridData.colModel.push({
		name: "tuiQty", index: "tuiQty", align : "right", 
		editable:true, editrules:{required:true, number:true, minValue:0}
	});
	cartGridData.colModel.push( {name: "tuiPrice", align : "right"});
	cartGridData.colModel.push( {name: "tuiNote", index: "tuiNote", editable:true} );
	
	cartGridObj = $("#cartGrid");
	cartGridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype:"local",
		data: billItems,
		colNames: cartGridData.colNames,
		colModel: cartGridData.colModel,
		//sortname: 'bill_id',	    
	    onSelectRow: function(id){
	    	if (id) {
				var row = gridObj.getRowData(id);
				lastSel = id;
				cartGridObj.jqGrid('editRow', id, true);
			}
		},
		loadCompleteCB : function() {
	    	var ids = jQuery('#cartGrid').getDataIDs();
	    	for (var i = ids.length - 1; i >= 0; i--){
	    		jQuery('#cartGrid').jqGrid('editRow', ids[i], {keys:false});
	    	}
	    },
		rownumbers:true,
		rowNum:10000
	}));
}

/**
 * @param rowId
 * @param row
 * @returns
 */
function calcTotalAmt(rowId, row)
{
	if (row == undefined)
	{
		row = cartGridObj.getRowData(rowId, true);
	}
	
	if (row.itemId != undefined) {
		var price = row.price2;
		if (!isNumeric(price)) {
			price = 0;
		}
		
		var qty = row.tuiQty;
		
		if (!isNumeric(qty)) {
			qty = 0;
		}
		
		row.tot = Math.round(qty * price * 100)/100;
		if (row.tot == NaN) {
			row.tot = "0";
		}
		cartGridObj.setCell(row.itemId, "tuiPrice", row.tot);
	}
	setTotalPrice();
	return row;
}

function setTotalPrice()
{
	var rowIds = cartGridObj.getDataIDs();
	var total_price = 0;
	for (i=0; i< rowIds.length; i++){
		var subtotal = parseFloat(cartGridObj.getCell(rowIds[i], "tuiPrice"));
		if (subtotal == NaN || !isNumeric(subtotal)){
			subtotal = 0.0;
		}
		total_price = Math.round((parseFloat(total_price) + subtotal) * 100) / 100;
	}
	$("#total-amt").html(total_price);
	$("#total_price").val(total_price);
}

function validateForm()
{
	if ($("#total_price").val() == ""){
		KptApi.showError("未录入退货商品数量，不能提交单据");
		return false;
	}else{
		var total_price = parseFloat($("#total_price").val());
		if (total_price == NaN || total_price == 0){
			KptApi.showError("未录入退货商品数量，不能提交单据");
			return false;
		}
		return true;
	}
}

function changeQty(e)
{
	var obj = $(e.target);
	var rowId = obj.attr("rowId");
	var val = obj.val();
	
	// validation need in val.
	if (! isNumeric(val)) {
		KptApi.showError("Please input the correct number.");
		obj.focus();
		return;
	}
	
	var row = cartGridObj.getRowData(rowId, true);
	
	if (parseFloat(val) < 0){
		KptApi.showError("商品数量不能为负数！");
		obj.val("");
		obj.focus();
		return;
	}
	
	if (parseFloat(val) > parseFloat(row.qty)){
		KptApi.showError("退货数量不能大于原单据数量。");
		obj.val("");
		obj.focus();
		return;
	}
	row.tuiQty = val;
	calcTotalAmt(rowId);
}

function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}
/**
 * Save the order.
 */
function saveReturn()
{
	if (validateForm()){

		var formObj = $("#userItemsForm");
		
		formObj.submit();
		
	}
}