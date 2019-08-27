$(document).ready(function(){
	function funcImg(cellvalue, options, rowObject){
			return "<img src='" + cellvalue + "' width='48px' height='32px' />";
	}
	CommonGrid.init('grid', {
	    url: 'UserItem.do?cmd=itemGridAjax',
	    postData: $('#itemSearchForm').serialize(),
	    colNames:['itemId', '图片', '商品编码', 	'商品名称', '大类', '小类', '单位', '规格', '产地', '状态'],
	    colModel:[
	      {name:'itemId', 		key:true,		hidden:true										},
	      {name:'itemSmallImg',	sortable:false,	width:100, 	align:'center',	formatter:funcImg	},
	      {name:'itemNo',		sortable:true,	width:150,	align:'left', 	index:'itemNo'		},
	      {name:'itemName',		sortable:true,	width:350,	align:'left', 	index:'itemName'	},
	      {name:'cat1', 		sortable:true,	width:200, 	align:'left', 	index:'cat1'		},
	      {name:'cat2', 		sortable:true,	width:200, 	align:'left',	index:'cat2'		},
	      {name:'unit', 		sortable:true,	width:100, 	align:'left',	index:'unit'		},
	      {name:'standard', 	sortable:true,	width:150, 	align:'left',	index:'standard'	},
	      {name:'manufacturer', sortable:true,	width:250, 	align:'left',	index:'manufacturer'},
	      {name:'stateName',	sortable:true,	width:80, 	align:'center',	index:'state'		}
	    ],
	    sortname: 'update_date',
	    sortorder: 'desc',
	    pager: jQuery('#gridpager'),
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
			KptApi.unblockUI(".admin_body");
	    }
	});
	
	$('#btnSave').click(function(){
		$("#saveItemForm").submit();
	});
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
		postData: $('#itemSearchForm').serialize()
	}).trigger('reloadGrid');
}