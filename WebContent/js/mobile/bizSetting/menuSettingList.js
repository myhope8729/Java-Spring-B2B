CommonGrid.init('grid', {
    url: 'MenuSetting.do?cmd=menuSettingGridAjax',
    mtype: 'POST',
    datatype: "json",
    colModel:[ 
      {name:'menuId', 			index:'menuId',		width:1, hidden:true, key:true}, 
      {label:'',	 			name:'',		sortable: false,	width:60, 	align:'right',
    	  formatter:function(selvalue, option, rowobj) {
    		  return '<li class="mgl10"></li>';
    	  }    	  
      },       
      {label:'功能名称',			name:'menuName', 		sortable: false,	width:300,	align:'left'}, 
      {label:'设定功能',		name:'menuUserName',  	sortable: false,    editable: true, edittype:"text",	width:300, 	align:'left'},
      {label:'状态',				name:'state', 	        sortable: false,    editable: true, edittype:"checkbox", editoptions:{value:"YES:NO"}
      	, formatter:'checkbox',formatoptions : {disabled : true},	width:120, 	align:'center', searchoptions:{sopt:['cn']}
      		
      },
      {name:'seqNo', 			index:'seqNo',		width:1, hidden:true},
      {name:'sortNo', 			index:'sortNo',		width:1, hidden:true},
      {label:'Upper Name', 			name: 'upperMenuName', 	hidden:true}
    ],
    grouping: true,
    groupingView: {
        groupField: ["upperMenuName"],
        groupColumnShow: [false],
        groupText: [
			"<img src='/css/mobile/jqGridMobile/default/images/icons-png/bullets-black.png'/>  <b>{0}</b> - {1} 条"
		],
        groupCollapse: false
    },
    loadonce: false,
    pager: '#gridpager',
    toppager:false,
    autowidth: true,
    height:450,
	pgbuttons:false,
	pginput:false,
    rowNum: 1000,
    editurl:'MenuSetting.do?cmd=saveMenuSettingAjax'
}); 
jQuery('#grid').jqGrid('navGrid','#gridpager',
		{edit:true, add:false, del:false, cloneToTop:false,refresh:true,search:false,
			afterRefresh:function() {
				reloadGrid();
			}
		},
        {	viewPagerButtons: false,
            recreateForm: true,
	        width:300,
			beforeSubmit : function( postdata, form , oper) {
				if (confirm(reallySaveMsg)){
					return [true, ''];
				}else{
					return [false, ''];
				}
			},
		    serializeEditData: function(data) {
		    	var selId = $("#grid").jqGrid('getGridParam','selrow');
		        var data1 = jQuery('#grid').jqGrid('getRowData', selId);
		        jQuery.extend(data1, data);
		    	return	 data1;
		    },				
	        afterSubmit : function( data, postdata, oper) {
				var response = jQuery.parseJSON( data.responseText );
				if (response.result.resultCode == result.SUCCESS) {
					reloadGrid();
					return [true, response.result.resultMsg,''];
				}else{
					return [false, 'Error:' + response.result.resultMsg];
				}
			},	
			afterComplete : function( data, postdata, oper) {
				var response = jQuery.parseJSON( data.responseText );
				if (response.result.resultCode == result.SUCCESS) {
					KptApi.showMsg(response.result.resultMsg);
				}
			},
            closeAfterEdit: true,
            errorTextFormat: function (data) {
                return 'Error: ' + data.responseText;
            }
        },
        {reloadAfterSubmit:false},{},{},{multipleSearch:false}
);
	
//검색, grid reload
function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		datatype: "json",
		grouping: true,
		loadonce: false,
		url: 'MenuSetting.do?cmd=menuSettingGridAjax'
	}).trigger('reloadGrid');
}

function editRow() {
	var selId = $("#grid").jqGrid('getGridParam','selrow');
	if(!selId) {
		KptApi.showWarning(selRow);
		return;
	}
	jQuery("#grid").jqGrid('editGridRow', selId, 
		    {	viewPagerButtons: false,
		        recreateForm: true,
		        width:300,
				beforeSubmit : function( postdata, form , oper) {
					if (confirm(reallySaveMsg)){
						return [true, ''];
					}else{
						return [false, ''];
					}
				},
			    serializeEditData: function(data) {
			    	var selId = $("#grid").jqGrid('getGridParam','selrow');
			        var data1 = jQuery('#grid').jqGrid('getRowData', selId);
			        jQuery.extend(data1, data);
			    	return	 data1;
			    },			
		        afterSubmit : function( data, postdata, oper) {
					var response = jQuery.parseJSON( data.responseText );
					if (response.result.resultCode == result.SUCCESS) {
						reloadGrid();
						return [true, response.result.resultMsg,''];
					}else{
						return [false, 'Error:' + response.result.resultMsg];
					}
				},	
				afterComplete : function( data, postdata, oper) {
					var response = jQuery.parseJSON( data.responseText );
					if (response.result.resultCode == result.SUCCESS) {
						KptApi.showMsg(response.result.resultMsg);
					}
				},
		        closeAfterEdit: true,
		        errorTextFormat: function (data) {
		            return 'Error: ' + data.responseText;
		        }
		    }
	);
}