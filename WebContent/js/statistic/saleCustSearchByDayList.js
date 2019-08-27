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
		colNames:['客户名称', '制单日期', '单据类别', '单据编号', '销售金额', '操作'],
		colModel:[ 
			      {name:'custUserName',  	index:'custUserName',	sortable:false,	align:'left', width:220}, 
			      {name:'createDate', 					sortable:false,	align:'center' },
			      {name:'billTypeName', 				sortable:false,	align:'center' },
			      {name:'billId', 						sortable:false,	align:'center' },
			      {name:'total2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name: "control",	   					sortable:false,	widht:100,	align:'center', classes:'gridLink',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  if(rowObject.billType == BILL_TYPE_DING) {
			    			  return '<a target="_blank" href="Order.do?cmd=orderView&billId='+rowObject.billId+'">详细</a>';
			    		  }else {
			    			  return '<a target="_blank" href="Sale.do?cmd=saleView&billId=' + rowObject.billId+'">详细</a>';
			    		  }
			    		 
			    	  }
			      }
			    ],
		sortname: 'create_date',	    
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
