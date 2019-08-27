jQuery(document).ready(function(){ 
	
	CommonGrid.init('grid', {
	    url:'UserMenu.do?cmd=userMenuGridAjax',
	    datatype: "json",
        colModel: [
                   	{ label: 'id', 			name: 'id', 				key:true,	hidden:true },
                   	{ label: 'userId', 		name: 'userId', 			hidden:true },
       				{ label: '菜单名', 		name: 'menuName', 			sortable: false,  editable: true,	edittype:"text",	width: 200,
       					editrules:{edithidden:false, required:true}
                   	},
       				{ label: '菜单链接', 	name: 'menuUrl', 			editable: true,   edittype:"text", width: 300,
       					editrules:{edithidden:false, required:true}
       				},
       				{ label: '状态',			name:'stateName', 			sortable: false,  editable: true, edittype:"select",  sortable: false,	width:150, 	align:'center',
         	    	  editoptions: {
         	    		  			value: "ST0001:正常;ST0002:禁用",
         		    		    	dataEvents: [{
         		    		    		type : 'change', 
         		    		    		data : { colName : 'state' },
         		    		    		fn   :	changePropertyType
         		    		    	}]}   
       				},            
       				{ label: 'state',			name: 'state', 			hidden:true},
       				{ label: 'CRUD',			name: 'crud', 			hidden:true}
               ],
		rowNum: -1,
		pager: false,
		rownumbers:true,
		onSelectRow: gridContentEditRow,
        serializeGridData: function(data) {
        	return data;
        }
	});
	
    function gridContentEditRow(id) {
        var grid = $("#grid");
        grid.jqGrid('editRow',id, {keys:true, focusField: 3});
    }
});


function changePropertyType(obj){
	var rowId = jQuery(obj.target).attr('rowid');
	var destColName = obj.data.colName;
	var thisVal = jQuery(obj.target).val();
	jQuery('#grid').setCell(rowId, destColName, thisVal, "","");
}

var saveIdLen=0, saveRowsData=[];

function saveData() {
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
			url 	: "UserMenu.do?cmd=saveUserMenuAjax",
			type: 'POST',
	        dataType: 'json',			
			data 	: {userListData:JSON.stringify(saveRowsData)}, //JSON.stringify(saveRowsData),
			success : function(data) {
				if (data.result.resultCode == result.SUCCESS) {
					KptApi.showMsg(data.result.resultMsg);
				}else{
					KptApi.showError(data.result.resultMsg);
				}
				jQuery("#grid").trigger("reloadGrid");
			}
		});
	}
}


function resetOk() {
    var grid = $("#grid");
    var editIDs = getEditableRowIds(grid);
	$.each(editIDs, function(i, id) {
		grid.jqGrid('restoreRow', id);
	});
}

function addCode() {
	var grid = $("#grid");
	var newId = new Date().getTime();
	$("#grid").jqGrid('addRow', {	
							initdata:{menuName:'', menuUrl:'', state:'ST0001', crud:'C'},
							position:"last"
							});
}