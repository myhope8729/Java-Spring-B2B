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
	    jsonReader : {
	        root		: "rows",
			page		: "page.page", 
			total		: "page.total", 
			records		: "page.records",
	        repeatitems	: true,
	        userdata	: "footerData"
	    },			
		colNames:['商品编码', '商品名称', '销售次数', '销售数量', '销售金额', '操作'],
		colModel:[ 
			      {name:'itemId',  index:'itemId',	sortable:false,	align:'left'					}, 
			      {name:'itemName', 					sortable:false,	align:'left', width:220	},
			      {name:'totalCount', 				    sortable:false,	align:'center' 				},
			      {name:'tot2', 						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name:'qty2',  						sortable:false,	align:'right', formatter:'number', formatoptions: {decimalPlaces:'2'}},
			      {name: "control",	   					sortable:false,	width:100,	align:'center', classes:'gridLink',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return '<a href="javascript:goDetailList(\'' + rowObject.itemId + '\',\'' + rowObject.itemName + '\');">详情</a>';
			    	  }
			      }
			    ],
		sortname: 'tot2',	    
		sortorder: 'DESC',			    
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

function goDetailList(itemId, itemName) {
	var fromObj = $("#dateFrom");
	var toObj = $("#dateTo");
	
	Transitions.load({
		url : "SaleItemSearch.do?cmd=saleItemDetailSearchList",
		form : searchForm,
		data : "searchString3=" + itemId + "&searchString4=" + itemName
	});
}