var searchForm = null;
var gridObj = null;
var fromObj = null;
var toObj = null;
jQuery(document).ready(function(){
	
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	fromObj = $("#dateFrom");
	toObj = $("#dateTo");
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
	    jsonReader : {
	        root		: "rows",
			page		: "page.page", 
			total		: "page.total", 
			records		: "page.records",
	        repeatitems	: true,
	        userdata	: "footerData"
	    },		
		colNames:['制单日期', '销售次数', '销售金额', '操作'],
		colModel:[ 
			      {name:'createDate',  	 				sortable:false,	align:'center'}, 
			      {name:'topNum', 						sortable:false,	align:'center' },
			      {name:'total2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name: "control",	   					sortable:false,	width:100,	align:'center', classes:'gridLink',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return '<a target="_blank" href="SaleDaySearch.do?cmd=saleDayDetailSearchList&dateFrom=' + rowObject.createDate + '">详情</a>';
			    	  }
			      }
			    ],
		pager: '#gridpager',
		rowNum: 20, 
		rownumbers:true,
		customfooter:true
	});
});


function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 		
		postData: $('#searchForm').serialize()
	}).trigger('reloadGrid');
}
