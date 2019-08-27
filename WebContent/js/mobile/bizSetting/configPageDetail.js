function funcImg(cellvalue, options, rowObject){
	return "<img src='" + cellvalue + "' height='32px' />";
}
var itemNameField, itemNoField;
var selectedName, selectedId, selectedNo;
for (i=0; i< jsonObj.properties.length; i++){
	if (jsonObj.properties[i] == "PT0006"){
		itemNameField = jsonObj.colModel[i + 1].name;
	}else if (jsonObj.properties[i] == "PT0001"){
		itemNoField = jsonObj.colModel[i+1].name;
	}
}
jsonObj.colModel[0].formatter = funcImg;

jsonObj.colNames.push("item_id");
jsonObj.colModel.push({name: "itemId", key:true, hidden:true});

jsonObj.colNames.push("firstpage_item");
jsonObj.colModel.push({name: "firstpageItemId", hidden:true});

CommonGrid.init('product-grid', {
	url: 'ConfigPage.do?cmd=userItemGridAjax',
	postData: $('#userItemFrm').serializeJSON(),
	datatype: "json",
	colNames: jsonObj.colNames,
	colModel: jsonObj.colModel,
	sortname: 'update_date',
	sortorder: 'desc',
	pager: '#product-gridpager',
	shrinkToFit:false,
	onSelectRow: function(id, status){
		var row = $("#product-grid").getRowData(id, true);
		if (row.stateName == "禁用"){
			$("#selectItem").attr('disabled', 'disabled');
		}else{
			if (row.firstpageItemId != "0"){
				KptApi.showWarning(messages.itemAlreadyOnHome);
				$("#selectItem").attr('disabled', 'disabled');
			}else{
				$("#selectItem").removeAttr('disabled');
			}
		}
		
		selectedName = eval("row." + itemNameField);
		selectedNo = eval("row." + itemNoField);
		selectedId = id;
	},
	rowNum : 6,
	rowList: [6],
	autowidth:false,
	shrinkToFit: true
});

$("select[name='detailType']").change(function(){
	$(".d_type").hide();
	$("." + $(this).val()).show();
}).trigger('change');

$("select[name='urlType']").change(function(){
	if ($(this).val() == 'UR0003'){
		$(".product-group").show();
		$(".url-link").hide();
	}else{
		$(".product-group").hide();
		$(".url-link").show();
	}
}).trigger('change');

$(document).on('click', '#search', function(){
	reloadGrid();
});

jQuery("#selectItem").click(function(){
	jQuery("input[name='productId']").val(selectedId);
	jQuery("input[name='product-name']").val(selectedName + "(编码：" + selectedNo + ")");
	jQuery("#productModal").modal('hide');
});

function reloadGrid() {
	jQuery('#product-grid').jqGrid('setGridParam',{
		postData: $('#userItemFrm').serializeJSON()
	}).trigger('reloadGrid');
}