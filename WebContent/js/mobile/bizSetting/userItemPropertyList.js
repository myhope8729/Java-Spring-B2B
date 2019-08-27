	var lastSel = 0;
	var existItems = false;
	jQuery("#grid").tableDnD({
	});
	
	CommonGrid.init('grid', {
		url: 'UserItem.do?cmd=userItemPropertyGridAjax',
		datatype: "json",
		colNames: ['seqno', 'propertyName', '名称', '类型', '价格', '显示', '打印', '状态'],
		colModel: [
		      {name:'seqNo', 				hidden:true, 	editable:true,	 	key:true},
		      {name:'propertyName',			hidden:true, 	editable:true				},
		      {name:'propertyDesc', 		editable:true,  edittype:'text', 	sortable:false,	align:'center'},
		      {name:'propertyTypeCd',		editable:true,  edittype:'select', 	sortable:false,	align:'center',
		    	  formatter:function(selvalue, option, rowobj) {
		    		  switch (selvalue){
		    		  case 'PT0001': return '商品编码';
		    		  case 'PT0002': return '销售价格';
		    		  case 'PT0003': return '大类';
		    		  case 'PT0004': return '小类';
		    		  case 'PT0005': return '包装数';
		    		  case 'PT0006': return '商品名称';
		    		  case 'PT0007': return '销售单位';
		    		  case 'PT0008': return '规格';
		    		  case 'PT0009': return '产地';
		    		  default : return '';
		    		  }
		    	  }
		      },
		      {name:'propertyValue', 		editable:true,  sortable:false,	align:'center', edittype:'select',
		    	  formatter:function(selvalue, option, rowobj) {
		    		  if(selvalue == 'Y') return '默认价格';
		    		  else if (selvalue == 'N' && rowobj.propertyTypeCd == 'PT0002') return '价格';
		    		  else	return '';
		    	  },
		    	  editoptions: {value: ":请选择;N:价格;Y:默认价格"}
		      },
		      {name:'displayYn',			editable:true,  edittype:'select', sortable:false,	align:'center',
		    	  formatter:function(selvalue, option, rowobj) {
		    		  if(selvalue == 'Y') return '是';
		    		  else return '否';
		    	  },
		    	  editoptions: {value: {N:'否',Y:'是'}}
		      },
		      {name:'printYn',				editable:true,  edittype:'select', sortable:false,	align:'center',
		    	  formatter:function(selvalue, option, rowobj) {
		    		  if(selvalue == 'Y') return '是';
		    		  else return '否';
		    	  },
		    	  editoptions: {value: {N:'否',Y:'是'}}
		      },
		      {name:'state',				editable:true,  edittype:'select', sortable:false,	align:'center',
		    	  formatter:function(selvalue, option, rowobj) {
		    		  if(selvalue == 'ST0001') return '正常';
		    		  else return '禁用';
		    	  },
		    	  editoptions: {value: {ST0001:'正常',ST0002:'禁用'}}
		      }],
		sortname: 'ItemId',
		rowNum:20,
		pager: '#gridpager',
	    toppager:false,
	    autowidth: true,
		pgbuttons:false,
		editurl:'UserItem.do?cmd=saveUserItemPropertyForMobile',
		pginput:false,
	    gridCompleteCB : function(){
	    	jQuery("#grid").tableDnDUpdate();
	    },
		loadComplete : function (data) {
			existItems = data.itemExist;
		    jQuery("#grid").setColProp('propertyTypeCd',{
		    	editoptions:{
		    		value:data.typeList
		    	}
		    });
	    }
	});
	
	jQuery('#grid').jqGrid('navGrid','#gridpager',
			{edit:true, add:false, del:false, cloneToTop:false,refresh:true,search:false,
				afterRefresh:function() {
					reloadGrid();
				}
			},
	        {	viewPagerButtons: false,
	            recreateForm: true,
				beforeSubmit : function( postdata, form , oper) {
					if (confirm(messages.reallySaveMsg)){
						return [true, ''];
					}else{
						return [false, ''];
					}
				},
	            afterSubmit : function( data, postdata, oper) {
	            	reloadGrid();
	            	return [true, ''];
				},	
				afterComplete : function( data, postdata, oper) {
					var response = jQuery.parseJSON( data.responseText );
					if (response.result.resultCode == result.SUCCESS) {
						//KptApi.showMsg(response.result.resultMsg);
						KptApi.showMsg(response.result.resultMsg);
					}else{
						KptApi.showError(response.result.resultMsg);
					}
				},
	            closeAfterEdit: true,
	            errorTextFormat: function (data) {
	                return 'Error: ' + data.responseText;
	            }
	        },
	        {reloadAfterSubmit:false},{},{},{multipleSearch:false}
	);
	
	//jQuery("#grid").jqGrid('navGrid','#gridpager',{edit:false,add:false,del:false,position:'right'});
	
function reloadGrid() {
	jQuery('#grid').trigger('reloadGrid');
}

function editRow() {
	var selId = $("#grid").jqGrid('getGridParam','selrow');
	if(!selId) {
		KptApi.showWarning(messages.selRow);
		return;
	}
	var rowData = jQuery('#grid').jqGrid('getRowData', selId);
	if (existItems && rowData.propertyTypeCd != '包装数' && rowData.propertyTypeCd != '销售价格' && rowData.propertyTypeCd != ""){
		jQuery("#grid").jqGrid('setColProp', 'propertyTypeCd',{editable : false});
	}else{
		jQuery("#grid").setColProp('propertyTypeCd',{editable : true});
	}
	jQuery("#grid").jqGrid('editGridRow', selId, 
		    {	viewPagerButtons: false,
		        recreateForm: true,
				beforeSubmit : function( postdata, form , oper) {
					if (confirm(messages.reallySaveMsg)){
						return [true,''];
					}else{
						return [false,''];
					}
				},
	            afterSubmit : function( data, postdata, oper) {
	            	reloadGrid();
	            	return [true, ''];
				},	
				afterComplete : function( data, postdata, oper) {
					var response = jQuery.parseJSON( data.responseText );
					if (response.result.resultCode == result.SUCCESS) {
						//KptApi.showMsg(response.result.resultMsg);
						KptApi.showMsg(response.result.resultMsg);
					}else{
						KptApi.showError(response.result.resultMsg);
					}
				},
		        closeAfterEdit: true,
		        errorTextFormat: function (data) {
		            return 'Error: ' + data.responseText;
		        }
		    }
	);
}