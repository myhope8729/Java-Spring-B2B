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
		colNames:['单据编号', '制单日期', '单据类型', '业务单位', '价格', '数量', '金额(元)'],
		colModel:[ 
			      {name:'billId',  		index:'billId',	sortable:true,	align:'left', width:220}, 
			      {name:'createDate', 					sortable:false,	align:'center' },
			      {name:'billTypeName', index:'billType',		sortable:true,	align:'center'},
			      {name:'hostUserName',   				sortable:false,	align:'left', width:250,
			    	  formatter:function(selvalue, option, rowobj) {
			    		  return '供货方：' + rowobj.hostUserName + '<br>订货方：' + rowobj.custUserName;
			    	  }					    	  
			      },
			      {name:'price2',   					sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name:'qty2',   						sortable:false,	align:'right', width: 100, formatter:'number', formatoptions: {decimalPlaces:'1'}},
			      {name:'tot2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}}	      
			    ],
		sortname: 'billId',	    
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
