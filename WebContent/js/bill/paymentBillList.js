var gridObj = null;

$(document).ready(function(){
	initPbGrid();
});
function initPbGrid()
{
	gridObj = $("#pbGrid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	searchForm.submit(function(){
		reloadGridByAjax(gridObj);
		return false;
	});
	
	CommonGrid.init( 'pbGrid', {
		url		: ajaxUrl,
		datatype: "json",
		postData: searchForm.serialize(),
		colNames:['单据类别', '货款类别', '单据编号', '单据金额(元)', '费用明细', '制单日期'],
		colModel:[ 
	          {name:'billTypeName', index:'h.bill_type',	width: 100,	sortable:true,	align:'center'}, 
		      {name:'paymentType',  index:'h.payment_type', width: 200,	sortable:true,	align:'left'},
		      {name:'billId',  		index:'h.bill_id', 		width: 160,	sortable:true,	align:'center', key: true},
		      {name:'total2',  		index:'h.total2',		width: 120,	sortable:true,	align:'right'},
		      {name:'paytype2',  	index:'paytype2',		width: 250,	sortable:false,	align:'left',
		    	  formatter:function(cellvalue, options, rowObj) {
		    		  return cellvalue;
		    	  }
		      },
		      {name:'createDate',  index:'h.create_date',	width: 120,	sortable:true,	align:'center'}
		    ],
		pager: '#pbGridPager',
		sortname: gridPager.sortname || 'h.create_date',
		sortorder: 'asc',
	    loadCompleteCB: function(data){
	    	for (i=0; i<data.rows.length; i++)
    		{
	    		var d = data.rows[i];
	    		if (d.billType == 'DT0004') {
	    			$("table tr#"+d.billId).addClass("ding");
	    		}
    		}
	    }
	});
}