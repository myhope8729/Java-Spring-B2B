CommonGrid.init('grid', {
    url: 'Menu.do?cmd=menuTreeAjax',
    postData: $('#menuListFrm').serializeJSON(),
    mtype: 'POST',
    datatype: "json",
    colModel:[ 
      {label:'Menu No',	 		name:'menuId', 	hidden:true,	sortable: false,	width:140, 	align:'right', key:true}, 
      {label:'',	 			name:'',		sortable: false,	width:30, 	align:'right',
    	  formatter:function(selvalue, option, rowobj) {
    		  return '<li class="mgl10"></li>';
    	  }    	  
      }, 
      {label:'名称',				name:'menuName', sortable: false,	width:140,	align:'left', searchoptions:{sopt:['cn']}, editable: true, edittype:"text"}, 
      {label:'手机端',			name:'mobileYn',  editable: true, edittype:"select",  sortable: false,	width:70, 	align:'center', search:false,
    	  formatter:function(selvalue, option, rowobj) {
    		  if(selvalue == 'Y') return '是';
    		  else return '否';
    	  },
    	  editoptions: {value: "Y:是;N:否"}   
      },	
      {label:'状态',				name:'state', 		editable: true, edittype:"select",  sortable: false,	width:70, 	align:'center', searchoptions:{sopt:['cn']}, 
    	  formatter:function(selvalue, option, rowobj) {
    		  if(selvalue == 'ST0001') return '正常';
    		  else return '禁用';
    	  },
    	  editoptions: {value: "ST0001:正常;ST0002:禁用"	}   
      },
      {label:'链接',				name:'connUrl',    sortable: false,	width:170,	align:'left', searchoptions:{sopt:['cn']}},      
      {label:'Upper Name',		name: 'upperMenuName', 	hidden:true}
    ],
    grouping: true,
    groupingView: {
        groupField: ["upperMenuName"],
        groupColumnShow: [false],
        groupText: [
			"<img src='/css/mobile/jqGridMobile/default/images/icons-png/bullets-black.png'/>  上位菜单: <b>{0}</b>"
		],
        groupCollapse: false
    },
    loadonce: true,
    /*pager: '#gridpager_toppager',*/
    /*toppager:true,*/
    autowidth: true,
    /*height:470,*/
	/*pagerpos:'left',*/
	/*pgbuttons:false,
	pginput:false,*/
	/*recordpos:'center',*/
    rowNum: 1000,
    editurl:'Menu.do?cmd=saveMenuAjaxForMobile'
}); 
jQuery('#grid').jqGrid('navGrid','#gridpager',
		{edit:true, add:false, del:false, cloneToTop:true,
			afterRefresh:function() {
				reloadGrid();
			}
		},
        {	viewPagerButtons: false,
            recreateForm: true,
			beforeSubmit : function( postdata, form , oper) {
				if (confirm(reallySaveMsg)){
					return [true, ''];
				}else{
					return [false, ''];
				}
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
					//KptApi.showMsg(response.result.resultMsg);
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
		postData: $('#menuListFrm').serializeJSON(),
		datatype: "json",
		grouping: true,
		loadonce: true,
		url: 'Menu.do?cmd=menuTreeAjax'
	}).trigger('reloadGrid');
}

