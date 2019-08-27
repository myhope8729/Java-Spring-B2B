jQuery(document).ready(function(){ 

	CommonGrid.init('grid', {
	    url: 'ActionUrl.do?cmd=actionMenuGridAjax',
	    editurl:'clientArray',
	    mtype: 'POST',
	    datatype: "json",
	    colModel:[ 
	      {label:'菜单编号',	 			name:'menuId', 			sortable: false,	width:200, 	align:'left', key:true}, 
	      {label:'菜单名称',				name:'menuName', 	    sortable: false,	width:170,	align:'left'},
          {label:'Upper Menu', 			name:'upperMenuName', 	hidden:true,		width: 150 }
	    ],
	    autowidth: false,
	    height:571,
	    pager: false,
        rowNum: -1,
	    shrinkToFit:false,
	    onSelectRow: function(rowid, selected) {
			if(rowid != null) {
				jQuery("#gridContent").jqGrid('setGridParam',{url: 'ActionUrl.do?cmd=actionUrlGridAjax', datatype: "json", postData: {"menuId":rowid}}); 
				jQuery("#gridContent").trigger("reloadGrid");
			}					
		},
	    loadCompleteCB : function() {
	    	jQuery("#grid").setSelection('MENU00000000002');
	    },
        grouping: true,
        groupingView: {
            groupField: ["upperMenuName"],
            groupColumnShow: [false],
            groupText: [
				"上位菜单: <b>{0}</b> &nbsp;-&nbsp; (<b>{1}</b>)"
			],
            groupCollapse: false
        }
	}); 
	
	
	CommonGrid.init('gridContent', {
	    editurl:'clientArray',
	    mtype: 'POST',
	    datatype: "local",
        colModel: [
                   	{ label: 'id', 			name: 'id', 				key: true, hidden:true },
       				{ label: '菜单名称', 	name: 'menuName', 			sortable: false,  width: 200 },
       				{ label: '菜单链接', 	name: 'menuActionUrl', 		editable: true,   edittype:"text", width: 300,
       					editrules:{edithidden:false, required:true}
       				},
       				{ label: '菜单说明', name: 'urlDesc', 			sortable: false,  editable: true, edittype:"text", width: 300 },
       				{ label: '状态',			name:'stateName', 			sortable: false,  editable: true, edittype:"select",  sortable: false,	width:150, 	align:'center',
         	    	  editoptions: {
         	    		  			value: "ST0001:正常;ST0002:禁用",
         		    		    	dataEvents: [{
         		    		    		type : 'change', 
         		    		    		data : { colName : 'state' },
         		    		    		fn   :	changePropertyType
         		    		    	}]}   
       				},            
       				{ label: 'Menu ID', 		name: 'menuId', 		hidden:true },
       				{ label: 'state',			name: 'state', 			hidden:true},
       				{ label: 'CRUD',			name: 'crud', 			hidden:true}
               ],
	    pager: false,
		rowNum: -1,
		onSelectRow: gridContentEditRow,
        serializeGridData: function(data) {
        	return data;
        }
	});
	
    function gridContentEditRow(id) {
        var grid = $("#gridContent");
        grid.jqGrid('editRow',id, {keys:true, focusField: 3});
    }
});


function changePropertyType(obj){
	var rowId = jQuery(obj.target).attr('rowid');
	var destColName = obj.data.colName;
	var thisVal = jQuery(obj.target).val();
	jQuery('#gridContent').setCell(rowId, destColName, thisVal, "","");
}

var saveIdLen=0, saveRowsData=[];

function saveData() {
	saveIdLen=0, saveRowsData=[];
    var grid = $("#gridContent");
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
	saveRowsData[--saveIdLen] = jQuery('#gridContent').jqGrid('getRowData', id);
	if(saveIdLen==0) {
		$.mvcAjax({
			url 	: "ActionUrl.do?cmd=saveActionUrlAjax",
			type: 'POST',
	        dataType: 'json',			
			data 	: {userListData:JSON.stringify(saveRowsData)}, //JSON.stringify(saveRowsData),
			success : function(data) {
				if (data.result.resultCode == result.SUCCESS) {
					KptApi.showMsg(data.result.resultMsg);
				}else{
					KptApi.showError(data.result.resultMsg);
				}
				jQuery("#gridContent").trigger("reloadGrid");
			}
		});
	}
}


function resetOk() {
    var grid = $("#gridContent");
    var editIDs = getEditableRowIds(grid);
	$.each(editIDs, function(i, id) {
		grid.jqGrid('restoreRow', id);
	});
}

function addCode() {
	var grid = $("#grid");
	var selelctedId = grid.jqGrid('getGridParam', 'selrow');
	var menuId = jQuery('#grid').getCell(selelctedId, 'menuId');
	var menuName = jQuery('#grid').getCell(selelctedId, 'menuName');
	if(!selelctedId) {
		KptApi.showMsg(noSelectUpper);
		return;
	}
	var newId = new Date().getTime();
	$("#gridContent").jqGrid('addRow', {	rowID:newId,
							initdata:{codeId:'NEW',menuId:menuId, menuName:menuName, state:'ST0001', crud:'C'},
							position:"first"
							});
}