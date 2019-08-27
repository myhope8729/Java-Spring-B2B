jQuery(document).ready(function(){ 

	CommonTreeGrid.init('grid', {
	    url: 'Menu.do?cmd=menuTreeAjax',
	    postData: $('#menuListFrm').serializeJSON(),
	    editurl:'clientArray',
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
			parent_id_field	: "upperMenuId"
		},
	    ExpandColumn : 'menuId',
	    colModel:[ 
	      {label:'菜单编号',	 			name:'menuId', 			sortable: false,	width:200, 	align:'left', key:true}, 
	      {label:'菜单名称',				name:'menuName', 		
	    	  editable: true, edittype:"text", sortable: false,	width:150,	align:'left',
	    	  editrules:{edithidden:false, required:true}
	      }, 
	      {label:'菜单说明',				name:'menuDesc', 		editable: true, edittype:"text", sortable: false,	width:200,	align:'left'}, 
	      {label:'菜单链接',				name:'connUrl', 		editable: true, edittype:"text", sortable: false,	width:250,	align:'left'}, 
	      {label:'序号',					name:'sortNo', 			
	    	  editable: false, edittype:"text", sortable: false,	width:50, 	align:'center',
	    	  editrules:{edithidden:false, required:true, number:true}
	      },
	      {label:'菜单图像',				name:'menuIcon',  editable: true, edittype:"text", sortable: false, 	align:'left'},	     
	      {label:'手机端显示',			name:'mobileYnName',  editable: true, edittype:"select",  sortable: false,	width:100, 	align:'center',
	    	  editoptions: {
	    		  			value: "Y:是;N:否",
		    		    	dataEvents: [{
		    		    		type : 'change', 
		    		    		data : { colName : 'mobileYn' },
		    		    		fn   :	changePropertyType
		    		    	}]}   
	      },	
	      {label:'状态',					name:'stateName', 		editable: true, edittype:"select",  sortable: false,	width:100, 	align:'center',
	    	  editoptions: {
	    		  			value: "ST0001:正常;ST0002:禁用",
		    		    	dataEvents: [{
		    		    		type : 'change', 
		    		    		data : { colName : 'state' },
		    		    		fn   :	changePropertyType
		    		    	}]}   
	      },
/*	      {label:'Upper Menu',			name:'upperMenuName', 	editable: false, edittype:"select", sortable: false,	width:150,	align:'left',
                  editoptions: {
                	    
						dataUrl : "Menu.do?cmd=getUpperMenuListWithJson",
						cacheUrlData: true,
						buildSelect : function( data) {
							
							data = jQuery.parseJSON( data );
							var result = data.data;
							var i=0, len=result.length, s="<select class='form-control'>";
							s += "<option value='' selected></option>\n";
							while(i<len) {
								
								s += '<option value='+ result[i].upperMenuId + '>'+result[i].upperMenuName+'</option>';
								i++;
							}
							s += '</select>';
							return s;
						},
						dataEvents: [{
	    	    				type : 'change', 
	    	    				data : { colName : 'upperMenuCode' },
	    	    				fn   :	changePropertyType
	           		  	}]
                   }
            },*/
            {label:'StateCode',			name:'state', 			hidden:true},
	      	{label:'MobileYn',			name:'mobileYn', 		hidden:true}
	    ],
	    autowidth: true,
	    onSelectRow: editRow,
	    height:571,
	    pager: false,
	    shrinkToFit:false
	}); 
	
	var lastSelection; 
	
    function editRow(id) {
            var grid = $("#grid");
            grid.jqGrid('editRow',id, {keys:true, focusField: 3});
    }
    
});

//검색, grid reload
function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#menuListFrm').serializeJSON()
	}).trigger('reloadGrid');
}

function changePropertyType(obj){
	var rowId = jQuery(obj.target).attr('rowid');
	var destColName = obj.data.colName;
	var thisVal = jQuery(obj.target).val();
	jQuery('#grid').setCell(rowId, destColName, thisVal, "","");
}

var saveIdLen=0, saveRowsData=[];

function saveRows() {
	saveIdLen=0, saveRowsData=[];
    var grid = $("#grid");
    var editIDs = getEditableRowIds(grid);
    if (!editIDs)
    	KptApi.showMsg(noSaveData);
    else {
    		KptApi.confirm(reallySaveMsg, function(){
        		saveIdLen = editIDs.length;
    	    	$.each(editIDs, function(i, id) {
    	    		grid.jqGrid('saveRow', id, {url:'clientArray', aftersavefunc:afterSaveRows});
    	    	});
    		});

    	}  
}

function afterSaveRows(id) {
	saveRowsData[--saveIdLen] = jQuery('#grid').jqGrid('getRowData', id);
	if(saveIdLen==0) {
		$.mvcAjax({
			url 	: "Menu.do?cmd=saveMenuAjax",
			type: 'POST',
	        dataType: 'json',			
			data 	: {userListData:JSON.stringify(saveRowsData)}, //JSON.stringify(saveRowsData),
			success : function(data) {
				if (data.result.resultCode == result.SUCCESS) {
					KptApi.showMsg(data.result.resultMsg);
				}else{
					KptApi.showError(data.result.resultMsg);
				}
				reloadGrid();
			}
		});
	}
}


function reset() {
    var grid = $("#grid");
    var editIDs = getEditableRowIds(grid);
	$.each(editIDs, function(i, id) {
		grid.jqGrid('restoreRow', id);
	});
}
