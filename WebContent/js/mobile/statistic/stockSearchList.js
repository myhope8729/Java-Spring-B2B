var searchForm = null;
var gridObj = null;
	
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	function funcLink(cellvalue, options, rowObject){
		return '<a class="mg0-auto" data-role="button" data-icon="carat-r" data-mini="true" data-iconpos="notext" href="StockSearch.do?cmd=stockDetailSearchList&itemId='+rowObject.itemId+'&storeId=' + rowObject.storeId +'">详细</a>';
	}
	
	itemGridData.colModel[itemGridData.colModel.length - 1].formatter = funcLink;
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serializeJSON(),
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
	    autowidth:false,
	    height:'100%',
	    width:1020,
	    shrinkToFit: true,
		sortname: 'ItemId',
		sortorder:'DESC',
		pager: '#gridpager',
		rownumbers:true,
		rowNum: 12,
		customfooter:true
	});
	
function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#searchForm').serializeJSON()
	}).trigger('reloadGrid');
}
