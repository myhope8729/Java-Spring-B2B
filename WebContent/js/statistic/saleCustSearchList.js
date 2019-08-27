var searchForm = null;
var gridObj = null;
var fromObj = null;
var toObj = null;
jQuery(document).ready(function(){
	
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
		colNames:['客户名称', '销售次数', '销售金额', '操作'],
		colModel:[ 
			      {name:'custUserName',  	index:'custUserName',	sortable:true,	align:'left', width:220}, 
			      {name:'topNum', 						sortable:false,	align:'center' },
			      {name:'total2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name: "control",	   					sortable:false,	width:100,	align:'center', classes:'gridLink',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return '<a href="javascript:goDetailList(\'' + rowObject.custUserId + '\',\'' + rowObject.custUserName + '\');">详情</a>';
			    		  //SaleCustSearch.do?cmd=saleCustSearchByDayList&searchString1=' + rowObject.custUserId + '&searchString2=' + rowObject.custUserName + '&dateFrom=' + fromObj.val() + '&dateTo=' + toObj.val() + '">详情</a>';
			    	  }
			      }
			    ],
		sortname: 'total2',	    
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

function goDetailList(custId, custName) {
	fromObj = $("#createDateFrom");
	toObj = $("#createDateTo");
	
	Transitions.load({
		url : "SaleCustSearch.do?cmd=saleCustSearchByDayList",
		form : "searchForm",
		data : "searchString1=" + custId + "&searchString2=" + custName + '&dateFrom=' + fromObj.val() + '&dateTo=' + toObj.val()
	});
}
