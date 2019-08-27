var searchForm = null;
var gridObj = null;
var fromObj = null;
var toObj = null;
	
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	fromObj = $("#dateFrom");
	toObj = $("#dateTo");
	
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
		colNames:['制单日期', '次数', '销售金额', '操作'],
		colModel:[ 
			      {name:'createDate',  	 				sortable:false,	align:'center'}, 
			      {name:'topNum', 						sortable:false, width:70,	align:'center' },
			      {name:'total2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name: "control",	   					sortable:false,	width:150,	align:'center',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return '<a data-role="button" data-icon="carat-r" data-mini="true" data-iconpos="right" href="SaleDaySearch.do?cmd=saleDayDetailSearchList&dateFrom=' + rowObject.createDate + '">详情</a>';
			    	  }
			      }
			    ],
		pager: '#gridpager',
		rowNum: 12,
		customfooter:true
	});


function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 		
		postData: $('#searchForm').serializeJSON()
	}).trigger('reloadGrid');
}
