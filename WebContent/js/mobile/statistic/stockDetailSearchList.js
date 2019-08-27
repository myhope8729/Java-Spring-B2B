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
	colNames:['单据编号', '制单日期', '单据类型', '业务单位', '价格', '数量', '金额(元)'],
	colModel:[ 
		      {name:'billId',  		index:'billId',	sortable:true,	align:'left', width:170}, 
		      {name:'createDate', 					sortable:false,	align:'center', width:100 },
		      {name:'billTypeName', index:'billType',		sortable:true,	align:'center', width:100},
		      {name:'hostUserName',   				sortable:false,	align:'left', width:200,
		    	  formatter:function(selvalue, option, rowobj) {
		    		  return '供货方：' + rowobj.hostUserName + '<br>订货方：' + rowobj.custUserName;
		    	  }					    	  
		      },
		      {name:'price2',   					sortable:false,	align:'right', width: 80, formatter:'number', formatoptions: {decimalPlaces:'2'}},
		      {name:'qty2',   						sortable:false,	align:'right', width: 80, formatter:'number', formatoptions: {decimalPlaces:'1'}},
		      {name:'tot2',  						sortable:false,	align:'right', width:100, formatter:'number', formatoptions: {decimalPlaces:'2'}}	      
		    ],
	autowidth:false,
    width:865,
    shrinkToFit: true,
	sortname: 'billId',	    
	sortorder: 'DESC',
	pager: '#gridpager',
	rowNum: 12,
	rownumbers:true
});


function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 		
		postData: $('#searchForm').serializeJSON()
	}).trigger('reloadGrid');
}
