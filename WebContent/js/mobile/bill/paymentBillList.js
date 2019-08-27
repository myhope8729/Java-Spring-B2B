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
		colNames:['品牌', '单据类别', '货款类别', '单据编号', '单据金额(元)', '费用明细', '制单日期'/*, '操作'*/],
		colModel:[ 
	          {name:'brand',  		index:'h.brand',		sortable:true,	align:'center'},
		      {name:'billTypeName', index:'h.bill_type',	width: 100,	sortable:true,	align:'center'}, 
		      {name:'paymentType',  index:'h.payment_type', width: 100,	sortable:true,	align:'left'},
		      {name:'billId',  		index:'h.billId', 		width: 150,	sortable:false,	align:'left', key: true},
		      {name:'total2',  		index:'h.total2',		sortable:true,	align:'right'},
		      {name:'paytype2',  	index:'paytype2',		sortable:false,	align:'left',
		    	  formatter:function(cellvalue, options, rowObj) {
		    		  return cellvalue;
		    	  }
		      },
		      {name:'createDate',  index:'h.create_date',	sortable:true,	align:'center'},
		      /*{name: "createDate",	// any db column name here.
		    	  align: "center",
		    	  width: 50,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  return '';
		    	  } 
		      },	*/		      
		    ],
		
		pager: '#pbGridPager',
		sortname: gridPager.sortname || 'h.create_date',
		sortorder: 'asc',
	    rownumbers:true,
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
	gridObj.jqGrid('navGrid','#pbGridPager',{edit:false,add:false,del:false,search:false, position:'right'});
}