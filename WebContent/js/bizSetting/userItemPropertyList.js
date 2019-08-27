jQuery(document).ready(function(){
	
	var lastSel = 0;
	var existItems = false;
	jQuery("#grid").tableDnD({
		onDrop : function(table, row){
			/*jQuery("tr.jqgrow", table).each(function(i){
				jQuery("td", this).eq(0).html(i+1);
			});*/
		}
	});
	
	CommonGrid.init('grid', {
		url: 'UserItem.do?cmd=userItemPropertyGridAjax',
		datatype: "json",
		colNames: ['标题名称', '标题类型', '销售价格', '显示', '打印', '状态', 'propertyName', 'propertyTypeCd', 'propertyValue', 'displayYn', 'printYn', 'state'],
		colModel: [
		      {name:'propertyDesc', 		editable:true,  edittype:'text', sortable:false,	align:'center'},
		      {name:'propertyTypeName',		editable:true,  edittype:'select', sortable:false,	align:'center'},
		      {name:'propertyValueName', 	editable:true,  sortable:false,	align:'center', edittype:'select', 
		    	  editoptions:{
		    		  value:":请选择;N:价格;Y:默认价格",
		    		  dataEvents: [{
			    			type : 'change', 
			    			data : { colName : 'propertyValue' },
			    			fn   :	changePropertyType
			    	  }]
		    	  }
		      },
		      {name:'displayYnName',		editable:true,  edittype:'select', sortable:false,	align:'center', 
		    	  editoptions:{
		    		  value:{N:'否',Y:'是'},
		    		  dataEvents: [{
			    			type : 'change', 
			    			data : { colName : 'displayYn' },
			    			fn   :	changePropertyType
			    	  }]
		    	  } 
		      },
		      {name:'printYnName',			editable:true,  edittype:'select', sortable:false,	align:'center',
		    	  editoptions:{
		    		  value:{N:'否',Y:'是'},
		    		  dataEvents: [{
			    			type : 'change', 
			    			data : { colName : 'printYn' },
			    			fn   :	changePropertyType
			    	  }]
		    	  }
		      },
		      {name:'stateName',			editable:true,  edittype:'select', sortable:false,	align:'center',
		    	  editoptions:{
		    		  value:{ST0001:'正常',ST0002:'禁用'},
		    		  dataEvents: [{
			    			type : 'change', 
			    			data : { colName : 'state' },
			    			fn   :	changePropertyType
			    	  }]
		    	  }
		      },
		      {name:'propertyName',		hidden:true},
		      {name:'propertyTypeCd',	hidden:true},
		      {name:'propertyValue',	hidden:true},
		      {name:'displayYn',		hidden:true},
		      {name:'printYn',			hidden:true},
		      {name:'state',			hidden:true}
		           ],
		sortname: 'ItemId',
		pager: '',
		rowNum:20,
		loadComplete : function (data) {
			existItems = data.itemExist;
		    jQuery("#grid").setColProp('propertyTypeName',{
		    	editoptions:{
		    		value:data.typeList, 
		    		dataEvents: [{
		    			type : 'change', 
		    			data : { colName : 'propertyTypeCd' },
		    			fn   :	changePropertyType}
		    		]
		    	}
		    });
	    },
	    gridComplete : function(){
	    	jQuery("#grid").tableDnDUpdate();
	    	KptApi.unblockUI(".admin_body");
	    },
		onSelectRow: function(id, status){
			if (id != lastSel){
				jQuery('#grid').jqGrid('saveRow', lastSel);
				if (jQuery('#grid').getCell(lastSel, "propertyTypeName") == '请选择'){
					jQuery('#grid').setCell(lastSel, "propertyTypeName", " ", "","");
				}
				if (jQuery('#grid').getCell(lastSel, "propertyTypeName") != '销售价格' 
					||	jQuery('#grid').getCell(lastSel, "propertyValueName") == '请选择'){
					jQuery('#grid').setCell(lastSel, "propertyValueName", " ", "","");
				}
				var rowData = jQuery('#grid').jqGrid('getRowData', lastSel);
				if (rowData.propertyDesc == ""){
					KptApi.showError("标题名称不能为空。");
					jQuery('#grid').jqGrid('editRow', lastSel, true);
					return false;
				}
			}
			if(id && status){
				var rowData = jQuery('#grid').jqGrid('getRowData', id);
				if (existItems && rowData.propertyTypeCd != 'PT0002' && rowData.propertyTypeCd != 'PT0005' && rowData.propertyTypeCd != ""){
					jQuery("#grid").setColProp('propertyTypeName',{editable : false});
				}else{
					jQuery("#grid").setColProp('propertyTypeName',{editable : true});
				}
				jQuery('#selectedId').val(id);
				jQuery('#grid').jqGrid('editRow',id, true);
				lastSel = id;
			}else if (!status){
				jQuery('#grid').jqGrid('saveRow', lastSel);
				if (jQuery('#grid').getCell(lastSel, "propertyTypeName") == '请选择'){
					jQuery('#grid').setCell(lastSel, "propertyTypeName", " ", "","");
				}
				if (jQuery('#grid').getCell(lastSel, "propertyTypeName") != '销售价格' 
					|| jQuery('#grid').getCell(lastSel, "propertyValueName") == '请选择'){
					jQuery('#grid').setCell(lastSel, "propertyValueName", " ", "","");
				}
				var rowData = jQuery('#grid').jqGrid('getRowData', lastSel);
				if (rowData.propertyDesc == ""){
					KptApi.showError("标题名称不能为空。");
					jQuery('#grid').jqGrid('editRow', lastSel, true);
					return false;
				}
			}
		}
	});
	
	//jQuery("#grid").jqGrid('navGrid','#gridpager',{edit:false,add:false,del:false,position:'right'});
	
	jQuery("#popupHistory").fancybox();

});

function changePropertyType(obj){
	var rowId = jQuery("#selectedId").val();
	var currentRowData = jQuery('#grid').jqGrid('getRowData', rowId);
	var destColName = obj.data.colName;
	var thisVal = jQuery(obj.target).val();
	var existDefaultValue = false, existCurrentVal = false;
	if (destColName == "propertyValue"){
		jQuery('tr.jqgrow', '#grid').each(function(i){
			var thisId = this.id;
			if (rowId != thisId){
				var rowData = jQuery('#grid').jqGrid('getRowData', thisId);
				if (rowData.propertyValue == "Y" && rowData.state == "ST0001" 
					&& thisVal == "Y" && currentRowData.state == "ST0001"){
					KptApi.showError(messages.duplicateMsg);
					existDefaultValue = true;
					jQuery(obj.target).find("option").removeAttr("selected");
					jQuery(obj.target).find("option").eq(0).attr("selected", "selected");
				}
			}
		});
	}else if (destColName == 'state'){
		jQuery('tr.jqgrow', '#grid').each(function(i){
			var thisId = this.id;
			if (rowId != thisId){
				var rowData = jQuery('#grid').jqGrid('getRowData', thisId);
				if (rowData.propertyValue == "Y" && rowData.state == "ST0001" 
					&& thisVal == "ST0001" && currentRowData.propertyValue == "Y"){
					KptApi.showError(messages.duplicateMsg);
					existDefaultValue = true;
					jQuery(obj.target).find("option").removeAttr("selected");
					jQuery(obj.target).find("option").eq(1).attr("selected", "selected");
					jQuery(obj.target).val("ST0002");
				}
			}
		});
	}
	if (destColName == 'propertyTypeCd'){
		jQuery('tr.jqgrow', '#grid').each(function(i){
			var thisId = this.id;
			if (rowId != thisId){
				var rowData = jQuery('#grid').jqGrid('getRowData', thisId);
				if (thisVal != "" && thisVal != "PT0002" && rowData.propertyTypeCd == thisVal){
					KptApi.showError(messages.duplicateField);
					existDefaultValue = true;
					jQuery(obj.target).find("option").removeAttr("selected");
					jQuery(obj.target).find("option").eq(0).attr("selected", "selected");
				}
			}
		});
	}
	if (!existDefaultValue){
		if (thisVal == "")
		{
			jQuery('#grid').setCell(rowId, destColName, null, "", "");
		}else{
			jQuery('#grid').setCell(rowId, destColName, thisVal, "", "");
		}
	}
}
function reloadGrid() {
	jQuery('#grid').trigger('reloadGrid');
}

function saveItemPropertyList(){
	var gridData = new Array();
	
	var itemNoFieldExist = false;
	var itemNameFieldExist = false;
	var propertyNameBlank = false;
	
	jQuery("tr.jqgrow", "#grid").each(function(i){
		var rowId = this.id;
		jQuery('#grid').jqGrid('saveRow', rowId);
		if (jQuery('#grid').getCell(rowId, "propertyTypeName") == '请选择'){
			jQuery('#grid').setCell(rowId, "propertyTypeName", " ", "","");
		}
		if (jQuery('#grid').getCell(rowId, "propertyTypeName") != '销售价格' 
			||	jQuery('#grid').getCell(rowId, "propertyValueName") == '请选择'){
			jQuery('#grid').setCell(rowId, "propertyValueName", " ", "","");
		}
		var rowData = jQuery('#grid').jqGrid('getRowData', rowId);
		if (rowData.propertyDesc == ""){
			jQuery('#grid').jqGrid('editRow', rowId, true);
			propertyNameBlank = true;
		}
		if (rowData.propertyTypeCd == "PT0001"){
			itemNoFieldExist = true;
		}else if (rowData.propertyTypeCd == "PT0006" && rowData.state=="ST0001"){
			itemNameFieldExist = true;
		}
		gridData[i] = rowData;
	});
	if (propertyNameBlank){
		KptApi.showError("标题名称不能为空。");
		return false;
	}
	if (!itemNoFieldExist || !itemNameFieldExist){
		KptApi.showError(messages.noRequiredField);
		return false;
	}
	
	var userData = JSON.stringify(gridData);
	jQuery("#userData").val(userData);
	jQuery("#userItemPropertyFrm").submit();
	//return false;
}