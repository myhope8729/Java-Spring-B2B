jQuery(document).ready(function(){
	function funcImg(cellvalue, options, rowObject){
		return "<img src='" + cellvalue + "' width='48px' height='32px' />";
	}
	
	function funcLink(cellvalue, options, rowObject){
		return '<a href="#" onclick="deleteItem(\'' + rowObject.itemId + '\')">移除 </a>';
	}
	
	var lastSel = 0;
	jsonObj.colModel[0].formatter = funcImg;
	jsonObj.colModel[jsonObj.colModel.length - 1].formatter = funcLink;
	CommonGrid.init('grid', {
		url: 'UserItem.do?cmd=groupItemGridAjax',
		postData: $('#groupItemFrm').serialize(),
		datatype: "json",
		colNames: jsonObj.colNames,
		colModel: jsonObj.colModel,
		sortname: 'ItemId',
		pager: '#gridpager'
	});
	
	jQuery("#groupId").change(function(){
		jQuery("#group").val(jQuery(this).val());
		reloadGrid();
	});
	jQuery("#search").click(function(){
		reloadGrid();
	});
	jQuery("#group").val(jQuery("#groupId").val());
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#groupItemFrm').serialize()
	}).trigger('reloadGrid');
}

function deleteItem(itemId) {
	var groupId = $("#groupId").val();
	
	KptApi.confirm("你确定移除这个商品从分组吗?", function(){
		$.mvcAjax({
			url 	: "UserItem.do?cmd=deleteGroupItemAjax",
			data 	: {itemId : itemId, groupId : groupId},
			success : function(data) {
				if (data.result.resultCode == 0) {
					KptApi.showMsg(data.result.resultMsg);
					reloadGrid();
				}else{
					KptApi.showError(data.result.resultMsg);
				}
			}
		});
	});
}