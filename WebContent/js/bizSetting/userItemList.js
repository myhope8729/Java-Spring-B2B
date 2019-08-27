var itemExist = false;
jQuery(document).ready(function(){
	function funcImg(cellvalue, options, rowObject){
		return "<img src='" + cellvalue + "' height='32px' />";
	}
	
	function funcLink(cellvalue, options, rowObject){
		return '<a href="UserItem.do?cmd=userItemForm&itemId='+rowObject.itemId+'">修改</a><br/><a href="UserItem.do?cmd=setItemPage&itemId='+rowObject.itemId+'">设置详情</a>';
	}
	
	var lastSel = 0;
	jsonObj.colModel[0].formatter = funcImg;
	jsonObj.colModel[jsonObj.colModel.length - 1].formatter = funcLink;
	CommonGrid.init('grid', {
		url: 'UserItem.do?cmd=userItemGridAjax',
		postData: $('#userItemFrm').serialize(),
		datatype: "json",
		colNames: jsonObj.colNames,
		colModel: jsonObj.colModel,
		sortname: 'update_date',
		sortorder: 'desc',
		pager: '#gridpager',
		onSelectRow: function(id, status){
		},
		loadCompleteCB:function(data){
			if (data.rows.length > 0)
				itemExist = true;
		}
	});
	
	jQuery("#category, #state").change(function(){
		reloadGrid();
	});
	jQuery("#category, #state, #chelp").keypress(function(e){
		if ( e.which == 13 ) {
			e.preventDefault();
			reloadGrid();
		}
	});
	jQuery("#search").click(function(){
		reloadGrid();
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#userItemFrm').serialize()
	}).trigger('reloadGrid');
}

function addItem(){
	if (!itemExist){
		KptApi.confirm(messages.addItemConfirm,function(){
			location.href = "UserItem.do?cmd=itemList";
		});
	}else{
		location.href = "UserItem.do?cmd=itemList";
	}
}