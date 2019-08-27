jQuery(document).ready(function(){ 

	CommonGrid.init('grid', {
	    url: 'ActionUrl.do?cmd=actionMenuGridAjax',
	    editurl:'clientArray',
	    mtype: 'POST',
	    datatype: "json",
	    colModel:[ 
	      {label:'菜单编号',	 			name:'menuId', 			hidden:true, 	sortable: false, 	align:'left', key:true}, 
	      {label:'菜单名称',				name:'menuName', 	    sortable: false,	align:'left',
	    	  formatter:function(selvalue, option, rowobj) {
	    		  return '<li class="mgl10">' + selvalue + '</li>';
	    	  }    
	      },
          {label:'上位菜单', 			name:'upperMenuName', 	hidden:true,		width: 150 }
	    ],
	    autowidth: true,
	    pager: false,
	    height:570,
        rowNum: -1,
	    shrinkToFit:true,
	    onSelectRow: function(rowid, selected) {
			if(rowid != null) {
				jQuery("#gridContent").jqGrid('setGridParam',{url: 'ActionUrl.do?cmd=actionUrlGridAjax', datatype: "json", postData: {"menuId":rowid}}); 
				jQuery("#gridContent").trigger("reloadGrid");
			}					
		},
	    loadComplete : function() {
	    	jQuery("#grid").setSelection('MENU00000000002');
	    },
        grouping: true,
        groupingView: {
            groupField: ["upperMenuName"],
            groupColumnShow: [false],
            groupText: [
                "<img src='/css/mobile/jqGridMobile/default/images/icons-png/bullets-black.png'/> <b>{0}</b>"
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
       				{ label: '菜单名称', 	name: 'menuName', 			sortable: false,  width: 200, hidden:true },
       				{ label: '菜单链接', 	name: 'menuActionUrl', 		width: 300,
       					editrules:{edithidden:false, required:true}
       				},
       				{ label: '菜单说明', name: 'urlDesc', 			sortable: false, width: 300, hidden:true},
       				{ label: '状态',			name:'stateName', 		sortable: false, width:150, align:'center'}          
               ],
	    pager: false,
		rowNum: -1
	});
	
});

