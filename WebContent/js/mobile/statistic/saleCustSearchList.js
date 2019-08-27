var searchForm = null;
var gridObj = null;
var fromObj = null;
var toObj = null;
	
	var lastSel = 0;
	gridObj = $("#salecustgrid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('salecustgrid', {
		url: ajaxUrl,
		postData: searchForm.serializeJSON(),
		datatype: "json",
		colNames:['客户名称', '销售次数', '销售金额', '操作'],
		colModel:[ 
			      {name:'custUserName',  	index:'custUserName',	sortable:true,	align:'left', width:200}, 
			      {name:'topNum', 						sortable:false,	width:100,	align:'center' },
			      {name:'total2',  						sortable:false,	width:200, 	align:'right', formatter:'number', formatoptions: {decimalPlaces:'1'}},
			      {name: "control",	   					sortable:false,	width:100,	align:'center', classes:'gridLink',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return '<a class="mg0-auto" data-role="button" data-icon="carat-r" data-mini="true" data-iconpos="notext" href="javascript:goDetailList(\'' + rowObject.custUserId + '\',\'' + rowObject.custUserName + '\');">详情</a>';
			    	  }
			      }
			    ],
		sortname: 'total2',	    
		sortorder: 'DESC',
		pager: '#salecustgridpager',
		rowNum: 12
});


function reloadGrid(){
	jQuery('#salecustgrid').jqGrid('setGridParam',{
		page:1, 		
		postData: $('#searchForm').serializeJSON()
	}).trigger('reloadGrid');
}

function goDetailList(custId, custName) {
	fromObj = $("#createDateFrom");
	toObj = $("#createDateTo");
	
	Transitions.load({
		url : "SaleCustSearch.do?cmd=saleCustSearchByDayList",
		form : "searchForm",
		data : "searchString1=" + custId + "&searchString2=" + custName
	});
}
