var currSelection;
jQuery(document).ready(function(){ 
    var lastSelection;
	CommonTreeGrid.init('grid', {
	    url: 'MenuSetting.do?cmd=menuSettingGridAjax',
	    mtype: 'POST',
	    datatype: "json",
	    jsonReader : {
			root		: "rows",
			repeatitems	: false
		},
	    treeGrid: true,
	    treeGridModel: 'adjacency',
	    tree_root_level: 1,
	    treedatatype: "json",
	    treeReader : {
	    	level_field: "level",
	    	leaf_field: "isLeaf",
	    	expanded_field: "expanded",
			parent_id_field	: "upperMenuId",
			icon_field : "uiicon"				
		},
		treeIcons : {
			"plus": "fa fa-plus",
			"minus": "fa fa-minus",
			"leaf" : "fa fa-file"
		},
	    ExpandColumn : 'menuName',
	    colNames:['MENU ID', '功能名称', 'SORT NO', '设定功能名称', '状态', 'SEQ NO'],
	    colModel:[ 
	      {name:'menuId', 			index:'menuId',		width:1, hidden:true, key:true}, 
	      {name:'menuName', 		sortable: false,	width:300,	align:'left'}, 
	      {name:'sortNo', 			index:'sortNo',		width:1, hidden:true},
	      {name:'menuUserName',  	editable: true, edittype:"text",	width:300, 	align:'left'},
	      {name:'state', 			editable: true, edittype:"checkbox", editoptions:{value:"YES:NO"}, formatter:'checkbox',formatoptions : {disabled : true},	width:120, 	align:'center'},
	      //{name:'stateName', 		editable: true, edittype:"checkbox", editoptions:{value:"正常:禁用"}, 	width:120, 	align:'center'},
	      {name:'seqNo', 			index:'seqNo',		width:1, hidden:true}
	    ],
	    loadonce:true,
	    onSelectRow: editRow,
	    height: 'auto',
	    pager: false,
	    shrinkToFit:false,
	    serializeRowData: function(data) {
	        var data1 = jQuery('#grid').jqGrid('getRowData', lastSelection);
	        jQuery.extend(data1, data);
	    	return	 data1;
	    }
	}); 
	

    function editRow(id) {
    	currSelection = id;
        if (id && id !== lastSelection) {
            var grid = $("#grid");
            grid.jqGrid('saveRow',lastSelection, {url:'MenuSetting.do?cmd=saveMenuSettingAjax'});  // url:'clientArray': when occur saveRow event, for does not submit to Server
            grid.jqGrid('editRow',id, {keys:true, focusField: 3});
            lastSelection = id;
        }
    }
    
});


function reloadGrid() {
	$("#grid").jqGrid('saveRow',currSelection, {url:'MenuSetting.do?cmd=saveMenuSettingAjax'});  // url:'clientArray': when occur saveRow event, for does not submit to Server
	document.location.href="MenuSetting.do?cmd=menuSettingList";
}    

function resetOk() {
    var grid = $("#grid");
    var editIDs = getEditableRowIds(grid);
	$.each(editIDs, function(i, id) {
		grid.jqGrid('restoreRow', id);
	});
}
