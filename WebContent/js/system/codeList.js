jQuery(document).ready(function(){ 
	CommonGrid.init('grid', {
        url: 'Code.do?cmd=codeGridAjax',
        postData: $('#searchForm').serialize(),
        editurl:'clientArray',
        mtype: "POST",
        datatype: "json",
        colModel: [
            { label: '编码编号', 		name: 'codeId', 		key: true, width: 200 },
			{ label: '上位编码编号', 	name: 'upperCodeId', 	sortable: false,  width: 200 },
            { label: '编码名称', 		name: 'codeName', 		editable: true,   edittype:"text", width: 250,
            	editrules:{edithidden:false, required:true}
			},
            { label: '编码说明', 		name: 'codeDesc', 		sortable: false,  editable: true, edittype:"text", width: 450 },
            { label: '状态',				name:'state', 			sortable: false,  editable: true, edittype:"select",  sortable: false,	width:100, 	align:'center',
  	    	  editoptions: {
  	    		  			value: "Y:正常;N:禁用",
  		    		    	dataEvents: [{
  		    		    		type : 'change', 
  		    		    		data : { colName : 'useYn' },
  		    		    		fn   :	changePropertyType
  		    		    	}]}   
            },            
            { label: 'Upper Name', 		name: 'upperCodeName', 	hidden:true,		width: 150 },
            { label: 'useYn',			name: 'useYn', 			hidden:true},
            { label: 'CRUD',			name: 'crud', 			hidden:true}
        ],
        autowidth: true,
		height:571,
		onSelectRow: editRow,
        rowNum: -1,
	    pager: false,
	    shrinkToFit:false,
	    sortname: 'codeId',
        grouping: true,
        groupingView: {
            groupField: ["upperCodeName"],
            groupColumnShow: [false],
            groupText: [
				"上位编码: <b>{0}</b> &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp; Records: <b>{1}</b>"
			],
            groupOrder: ["asc"],
            groupCollapse: false
        }
});
	
    function editRow(id) {
        var grid = $("#grid");
        grid.jqGrid('editRow',id, {keys:true, focusField: 3});
    }
    
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#searchForm').serialize()
	}).trigger('reloadGrid');
}

function changePropertyType(obj){
	var rowId = jQuery(obj.target).attr('rowid');
	var destColName = obj.data.colName;
	var thisVal = jQuery(obj.target).val();
	jQuery('#grid').setCell(rowId, destColName, thisVal, "","");
}

var saveIdLen=0, saveRowsData=[];

function saveCodeList() {
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
			url 	: "Code.do?cmd=saveCodeAjax",
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


function resetOk() {
    var grid = $("#grid");
    var editIDs = getEditableRowIds(grid);
	$.each(editIDs, function(i, id) {
		grid.jqGrid('restoreRow', id);
	});
}

function addCode() {
	var grid = $("#grid");
	var selelctedId = grid.jqGrid('getGridParam', 'selrow');
	var upperCode = jQuery('#grid').getCell(selelctedId, 'upperCodeId');
	if(!selelctedId) {
		KptApi.showMsg(noSelectUpper);
		return;
	}
	var newId = new Date().getTime();
	grid.jqGrid('addRow', {	rowID:newId,
							initdata:{codeId:'NEW',upperCodeId:upperCode, useYn:'Y',crud:'C'},
							position:"first"
							});
//	grid.addRowData(newId, {codeId:'NEW',upperCodeId:upperCode, useYn:'Y',crud:'C'}, "after", selelctedId);
//	grid.jqGrid('editRow',newId, {keys:false, focusField: 2});
}
