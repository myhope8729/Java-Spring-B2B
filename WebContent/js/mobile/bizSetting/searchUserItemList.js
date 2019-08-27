function funcImg(cellvalue, options, rowObject){
	return "<img src='" + cellvalue + "' width='30px' height='20px' />";
}

jsonObj.colModel[0].formatter = funcImg;
CommonGrid.init('grid', {
	url: 'UserItem.do?cmd=searchUserItemGridAjax',
	postData: $('#searchItemFrm').serializeJSON(),
	datatype: "json",
	colNames: jsonObj.colNames,
	colModel: jsonObj.colModel,
	sortname: 'itemId',
	pager: '#gridpager',
	multiselect : true,
	onSelectRow: function(id, status){
		if (status){
			var selectedIds = $("#selectedIds").val();
			if (selectedIds == ""){
				$("#selectedIds").val(id);
			}else{
				$("#selectedIds").val(selectedIds + "," + id);
			}
		}else{
			var selectedIds = "," + $("#selectedIds").val();
			var idArr = selectedIds.split("," + id);
			$("#selectedIds").val(idArr.join("").substr(1));
		}
	},
	onSelectAll: function(ids,status){
		if (status){
			for (var i = 0; i < ids.length; i++){
				var selectedIds = "," + $("#selectedIds").val();
				if (selectedIds.indexOf("," + ids[i]) < 0){
					$("#selectedIds").val($("#selectedIds").val() + "," + ids[i]);
				}
			}
		}else{
			for (var i=0; i< ids.length; i++){
				var selectedIds = "," + $("#selectedIds").val();
				var idArr = selectedIds.split("," + ids[i]);
				$("#selectedIds").val(idArr.join("").substr(1));
			}
		}
	},
	gridCompleteCB: function()
    {
		var selectedIds = "," + $("#selectedIds").val();
		var idArr = selectedIds.split(",");
		jQuery("tr.jqgrow", "#grid").each(function(i){
			var rowId = this.id;
			if (selectedIds.indexOf("," + rowId) > 0){
				jQuery("#grid").setSelection(rowId);
			}
		});
    }
});

jQuery("#category").change(function(){
	reloadGrid();
});
jQuery("#search").click(function(){
	reloadGrid();
});

function checkSelection() {
	if (jQuery("#selectedIds").val() == ""){
		KptApi.showError("There is no data to add to group.");
		return false;
	}else{
		return true;
	}
}

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#searchItemFrm').serializeJSON()
	}).trigger('reloadGrid');
}