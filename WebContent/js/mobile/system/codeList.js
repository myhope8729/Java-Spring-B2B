	CommonGrid.init('grid', {
        url: 'Code.do?cmd=codeGridAjax',
        postData: $('#searchForm').serializeJSON(),
        mtype: "POST",
        datatype: "json",
        colModel: [
            { label: '编码编号', 		name: 'codeId', 		key: true, width: 120,
          	  formatter:function(selvalue, option, rowobj) {
        		  return '<li class="mgl10">' + selvalue + '</li>';
        	  }	
            },
            { label: '编码名称', 		name: 'codeName', 	    width: 150,
            	editrules:{edithidden:false, required:true}
			},
            { label: '状态',			name:'state', 			sortable: false,   sortable: false,	width:100, 	align:'center'},            
            { label: '上位编码', 		name: 'upperCodeName', 	hidden:true},
        ],
        autowidth: true,
        rowNum: 10000,
        /*height:480,*/
    	pgbuttons:false,
    	pginput:false,
	    loadonce: false,
	    pager: '#gridpager',
	    sortname: 'codeId',
        grouping: true,
        groupingView: {
            groupField: ["upperCodeName"],
            groupColumnShow: [false],
            groupText: [
				"<img src='/css/mobile/jqGridMobile/default/images/icons-png/bullets-black.png'/>  上位编码: <b>{0}</b>"
			],
            groupOrder: ["asc"],
            groupCollapse: false
        }
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#searchForm').serializeJSON()
	}).trigger('reloadGrid');
}
