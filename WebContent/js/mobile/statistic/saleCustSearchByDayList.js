var searchForm = null;
var gridObj = null;
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serializeJSON(),
		colNames:['客户名称', '制单日期', '单据类别', '单据编号', '销售金额', '操作'],
		colModel:[ 
			      {name:'custUserName',  	index:'custUserName',	sortable:false,	align:'left', width:120}, 
			      {name:'createDate', 					sortable:false,	align:'center', width:100 },
			      {name:'billTypeName', 				sortable:false,	align:'center', width:100 },
			      {name:'billId', 						sortable:false,	align:'center', width:140 },
			      {name:'total2',  						sortable:false,	align:'right', width:130, formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name: "control",	   					sortable:false,	width:100,	align:'center', classes:'gridLink',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  if(rowObject.billType == BILL_TYPE_DING) {
			    			  return '<a data-role="button" data-icon="carat-r" data-mini="true" data-iconpos="right" target="_blank" href="Order.do?cmd=orderView&billId='+rowObject.billId+'">详细</a>';
			    		  }else {
			    			  return '<a data-role="button" data-icon="carat-r" data-mini="true" data-iconpos="right" target="_blank" href="Sale.do?cmd=saleView&billId=' + rowObject.billId+'">详细</a>';
			    		  }
			    		 
			    	  }
			      }
			    ],
		datatype: "json",
		autowidth:false,
	    width:720,
	    shrinkToFit: true,	
		sortname: 'create_date',	    
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
