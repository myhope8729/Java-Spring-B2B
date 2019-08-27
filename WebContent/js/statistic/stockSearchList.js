var searchForm = null;
var gridObj = null;
jQuery(document).ready(function(){
	
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	function funcLink(cellvalue, options, rowObject){
		return '<a href="StockSearch.do?cmd=stockDetailSearchList&itemId='+rowObject.itemId+'&storeId=' + rowObject.storeId +'">详细</a>';
	}
	
	itemGridData.colModel[itemGridData.colModel.length - 1].formatter = funcLink;
	
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
	    colNames:itemGridData.colNames,
	    colModel:itemGridData.colModel,
		sortname: 'ItemId',
		sortorder:'DESC',
		pager: '#gridpager',
		rowNum: 20,
		customfooter:true
	});
});


function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#searchForm').serialize()
	}).trigger('reloadGrid');
}
