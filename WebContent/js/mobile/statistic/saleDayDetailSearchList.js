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
		colNames:['客户名称',  '销售金额', '操作'],
		colModel:[ 
			      {name:'custUserName',  	index:'custUserName',	sortable:false,	align:'left', width:220}, 
			      {name:'total2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name: "control",	   					sortable:false,	width:100,	align:'center', classes:'gridLink',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return '<a class="mg0-auto" data-role="button" data-icon="carat-r" data-mini="true" data-iconpos="notext" target="_blank" href="Order.do?cmd=orderView&billId=' + rowObject.billId + '">详情</a>';
			    	  }
			      }
			    ],
		sortname: 'custUserName',	    
		sortorder: 'ASC',			    
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
