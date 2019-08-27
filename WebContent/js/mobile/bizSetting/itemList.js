function funcImg(cellvalue, options, rowObject){
	return "<img src='" + cellvalue + "' width='48px' height='32px' />";
}
CommonGrid.init('grid', {
    url: 'UserItem.do?cmd=itemGridAjax',
    postData: $('#searchForm').serializeJSON(),
    colNames:['itemId', '图片', '商品编码', '商品名称',  '单位'],
    colModel:[
      {name:'itemId', 		key:true,		hidden:true										},
      {name:'itemSmallImg',		sortable:false,	width:120, 	align:'center',	formatter:funcImg	},
      {name:'itemNo',		sortable:true,	width:120,	align:'center', index:'itemNo'	},
      {name:'itemName',		sortable:true,	width:200,	align:'center', index:'itemName'	},
/*      {name:'cat1', 		sortable:true,	width:100, 	align:'center', index:'cat1',
    	  formatter:function(selvalue, option, rowobj) {
    		  var cat1, cat2;
    		  if(selvalue==undefined || selvalue=='') cat1 = "--";
    		  else cat1 = selvalue;
    		  if(rowobj.cat2==undefined || rowobj.cat2=='') cat2 = "--";
    		  else cat2 = rowobj.cat2;
    		  
    		  return cat1 + '<br>' + cat2;
    	  }
      },*/
      /*{name:'cat2', 		sortable:true,	width:100, 	align:'center',	index:'cat2'		},*/
      {name:'unit',	sortable:true,	width:80, 	align:'center',	index:'unit' }
    ],
    sortname: 'update_date',
    sortorder: 'desc',
    pager: jQuery('#gridpager'),
    rowNum :  gridPager.rowNum,
	page :  gridPager.page,    
    multiselect: true,
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
		KptApi.unblockUI(".ui-content");
    }
});

function checkSelection() {
	if (jQuery("#selectedIds").val() == ""){
		KptApi.showError(noItemMsg);
		return false;
	}else{
		return true;
	}
}
function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#searchForm').serializeJSON()
	}).trigger('reloadGrid');
}