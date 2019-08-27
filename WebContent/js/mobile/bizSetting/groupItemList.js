function funcCell(cellvalue, options, rowObject){
	
	var imageNm = 'noImage_300x200.gif';
	if (cellvalue != "") imageNm = cellvalue;
	
	var itemName, cat1, cat2;
	for (i=0; i< jsonObj.properties.length; i++){
		switch (jsonObj.properties[i]){
			case "PT0006":
				itemName = eval("rowObject." + jsonObj.colModel[i + 1].name);
			case "PT0003":
				cat1 = eval("rowObject." + jsonObj.colModel[i + 1].name);
			case "PT0004":
				cat2 = eval("rowObject." + jsonObj.colModel[i + 1].name);
		}
	}
	if(cat1==undefined) cat1 = '未分类';
	if(cat2==undefined) cat2 = '未分类';

	htmlStr = '<div class="custom_block3">\
						<div class="ui-block-a"> ' + 
							'<img src="'
							+ imageNm + 
							'" alt="image" width="150px" height="100px"> </div>' +		
						'<div class="ui-block-b">\
							<div> <h4>   <strong>' 
								+ itemName +
                		         '</strong> \
								  </h4>\
							</div>\
							<div class="mgl10">大类:&nbsp;<b>' 
								+ cat1 + 
								 '</b>\
							</div>\
							<div class="mgl20"><small>小类:&nbsp;<b>' 
								+ cat2 + 
								 '</b></small>\
							</div>\
						</div>' +
						'<div class="ui-block-c">\
							<div class="custom_block2"><div class="ui-block-a"> <div class="mgt10 alignC">状态: <b>'
									+ rowObject.stateName +
									'</b></div></div>' +		
								'<div class="ui-block-b"><a href="#" onclick="deleteItem(\''
									+ rowObject.itemId +
									'\')" data-role="button" dat a-iconpos="left" data-mini="true" data-theme="a" data-icon="delete">移除</a>' +	
							'</div></div>' +
						'</div>' + 									
					'</div>'	;
    return htmlStr;
}

CommonGrid.init('grid', {
	url: 'UserItem.do?cmd=groupItemGridAjax',
	postData: $('#groupItemFrm').serializeJSON(),
    mtype: "POST",
    colNames:['商品目录'],
    colModel:[
      {name:'itemSmallImg',		sortable:false,	width:150, 	align:'left',	formatter:funcCell	}
    ],
    sortname: 'update_date',
    sortorder: 'desc',
    rowNum :  gridPager.rowNum,
	page :  gridPager.page,
    pager: jQuery('#gridpager')
});

jQuery("#search").click(function(){
	reloadGrid();
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#groupItemFrm').serializeJSON()
	}).trigger('reloadGrid');
}

jQuery("#groupId").change(function(){
	jQuery("#group").val(jQuery(this).val());
	reloadGrid();
});
jQuery("#group").val(jQuery("#groupId").val());
function deleteItem(itemId) {
	
	var groupId = $("#groupId").val();
	KptApi.confirm("你确定移除这个商品从分组吗?", function(){
		$.mvcAjax({
			url 	: "UserItem.do?cmd=deleteGroupItemAjax",
			data 	: {itemId: itemId, groupId : groupId},
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