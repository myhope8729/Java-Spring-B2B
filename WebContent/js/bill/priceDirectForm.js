var lastSel = 0;

jQuery(document).ready(function(){
	
	var gridObj = $("#grid");
	initCartGrid();
	$(document).on('change', 'input[name^=new_price]', changePrice);
	
	$(document).on('change', 'input[name=createDate]', searchList);
	$(document).on('change', '#category', searchList);
	
	$("#btnSaveDraft").click(function(){
		saveDraft();
	});
	
	$("#btnSave").click(function(){
		savePrice();
	});
	
	function searchList(){
		reloadGridByAjax(gridObj);
		return false;
	}
	function initCartGrid()
	{
		
		var ajaxUrl = gridObj.attr("ajaxUrl");
		searchForm = $( gridObj.attr("searchForm") );
		
		gridColNames = new Array();
		gridColNames.push("图片");
		gridColNames.push(gridData.ncpColDesc.join("<br/>"));
		gridColNames.push("前次入库价<br/>本次入库价");
		gridColNames.push("入库数量<br/>入库金额(元)");
		for (i=0;i<gridData.priceColDesc.length; i++){
			gridColNames.push(gridData.priceColDesc[i] + "(旧)<br/>" + gridData.priceColDesc[i] + "(新)");
		}
		gridColNames.push("备注");
		
		gridColModel = new Array();
		gridColModel.push( {name: "itemSmallImg", sortable:false, align: "center", width:60, formatter: funcImg} );
		gridColModel.push({name: "properties", align : "center", width:200,
			formatter:function(cellvalue, options, rowObject){
				var html = "";
				for (i = 0; i<gridData.ncpColNames.length; i++){
					if (i==0){
						html = (eval("rowObject." + gridData.ncpColNames[i]) || '');
					}else{
						html = html + "<br/>" + (eval("rowObject." + gridData.ncpColNames[i]) || '');
					}
				}
				return html;
			}
		});
		gridColModel.push({name: "priceIn", align: "center", width:120,
			formatter:function(cellvalue, options, rowObject){
				var qty2 = rowObject.qty || "0";
				var tot2 = rowObject.total || "0";
				var price = Math.round(parseFloat(tot2) *100 / parseFloat(qty2)) / 100;
				rowObject.priceIn = rowObject.priceIn || "0";
				return rowObject.priceIn + "<br/>" + price + 
					"<input type='hidden' class='cost' name='cost' value='" + price + "' />" +
					"<input type='hidden' name='price' value='" + price + "' />" +
					"<input type='hidden' name='qty' value='" + qty2 + "' />" + 
					"<input type='hidden' name='tot' value='" + tot2 + "' />" +
					"<input type='hidden' name='itemId' value='" + rowObject.itemId + "' />" +
					"<input type='hidden' name='priceIn' value='" + rowObject.priceIn + "' />";
			}
		});
		
		gridColModel.push({name: "qty", align: "center", width:120,
			formatter:function(cellvalue, options, rowObject){
				return rowObject.qty + "<br/>" + rowObject.total;
			}
		});
		for (i=0;i<gridData.priceColNames.length; i++){
			gridColModel.push({name: "price" + i, align: "left", width:120, formatoptions:{colNo:i},
				formatter:function(cellvalue, options, rowObject){
					var html = "";
					var priceColName = gridData.priceColNames[options.colModel.formatoptions.colNo];
					var oldPrice = eval("rowObject." + priceColName) || "0";
					var newPrice = eval("rowObject." + priceColName) || "0";
					var qty2 = rowObject.qty || "0";
					var tot2 = rowObject.total || "0";
					var cost = Math.round(parseFloat(tot2) *100 / parseFloat(qty2)) / 100;
					
					var percentage = 0;
					html = "<font color='#15428B'><input type='text' name='old_price" + options.colModel.formatoptions.colNo + 
						"' readonly class='chPrice oldPrice' value='" + oldPrice + "'/></font>";
					if (rowObject.cost != "" && rowObject.cost != "0" && oldPrice != "" && oldPrice != "0"){
						percentage = Math.round((parseFloat(oldPrice) - cost) * 1000 / parseFloat(oldPrice)) / 10;
						html = html + "<font color='#15428B' style='font-size:12px;'>(" + percentage + "%)</font>";
					}
					html = html + "<br/><input type='text' class='chPrice' name='new_price" + options.colModel.formatoptions.colNo + 
						"' value='" + newPrice + "' /><span class='new_percentage' style='font-size:12px;'>";
					if (rowObject.cost != "" && rowObject.cost != "0" && newPrice != "" && newPrice != "0"){
						percentage = Math.round((parseFloat(newPrice) - cost) * 1000 / parseFloat(newPrice)) / 10;
						html = html + "(" + percentage + "%)</font>";
					}
					html = html + "</span>";
					return html;
				}
			});
		}
		gridColModel.push({name:"note", align:"left", editable:true, width:130});
		
		gridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
			url: ajaxUrl,
			postData: searchForm.serialize(),
			datatype: "json",
			colNames: gridColNames,
			colModel: gridColModel,
			loadCompleteCB : function() {
		    	var ids = jQuery('#grid').getDataIDs();
		    	for (var i = ids.length - 1; i >= 0; i--){
		    		jQuery('#grid').jqGrid('editRow', ids[i], {keys:false});
		    	}
		    },
			rowNum:10000
		}));
	}
});

function changePrice(e)
{
	var obj = $(e.target);
	var val = obj.val();
	
	// validation need in val.
	if (! isNumeric(val)) {
		KptApi.showError("请输入正确的数值，只能输入数值。");
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
		if (! isNumeric(jQuery(this).val())) {
			KptApi.showError("请输入正确的数值，只能输入数值。");
			jQuery(this).focus();
			result = false;
			return false;
		}
	});
	return result;
}
function savePrice()
{
	var result = validateForm();
	if (result){
		$("#priceItemForm").submit();
	}
}

function saveDraft()
{
	var result = validateForm();
	if (result){
		$("#draftFlg").val("Y");
		$("#priceItemForm").submit();
	}
}