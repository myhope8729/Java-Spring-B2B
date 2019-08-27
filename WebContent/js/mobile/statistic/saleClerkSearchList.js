var searchForm = null;
var gridObj = null;
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serializeJSON(),
		datatype: "json",
	    jsonReader : {
	        root		: "rows",
			page		: "page.page", 
			total		: "page.total", 
			records		: "page.records",
	        repeatitems	: true,
	        userdata	: "footerData"
	    },			
		colNames:['业务员', '业务员编码', '销售次数', '销售金额'],
		colModel:[ 
			      {name:'empName',  index:'empName',	sortable:true,	align:'left'					}, 
			      {name:'empNo', 						sortable:false,	align:'left', width:220	},
			      {name:'cnt', 				    		sortable:false,	align:'center' 				},
			      {name:'total2', 	index:'total2',		sortable:true,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}}
			    ],
		sortname: 'total2',	    
		sortorder: 'DESC',			    
		pager: '#gridpager',
		rowNum: 12,
		rownumbers:true,
		customfooter:true
	});


function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 		
		postData: $('#searchForm').serializeJSON()
	}).trigger('reloadGrid');
}

