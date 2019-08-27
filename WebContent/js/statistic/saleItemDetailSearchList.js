var searchForm = null;
var gridObj = null;
jQuery(document).ready(function(){
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
		colNames:['制单日期', '客户名称', '销售数量', '销售价格', '销售金额'],
		colModel:[ 
			      {name:'createDate',  index:'createDate',	sortable:false,	align:'center'					}, 
			      {name:'custUserName', 				sortable:false,	align:'left', width:220	},
			      {name:'qty2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'1'}},
			      {name:'price2', 				    	sortable:false,	align:'center', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name:'tot2', 						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}}
			    ],
		sortname: 'createDate',	    
		sortorder: 'DESC',			    
		pager: '#gridpager',
		rowNum: 20, 
		rownumbers:true		
	});
});


function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 		
		postData: $('#searchForm').serialize()
	}).trigger('reloadGrid');
}
