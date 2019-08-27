var gridId = "grid";
var gridObj = $("#" + gridId);
	
var ajaxUrl = gridObj.attr("ajaxUrl");
var	searchForm = $( gridObj.attr("searchForm") );
	
searchForm.submit(function(){
	reloadGridByAjax(gridObj);
	return false;
});
	
CommonGrid.init(gridId, {
	url: ajaxUrl,
	postData: searchForm.serializeJSON(),
	datatype: "json",
	colNames:['金额(元)', '单据状态', '制单人', '制单日期', '操作'],
	colModel:[ 
		      {name:'total_amt',  	index:'total_amt',		width: 200,	sortable:false,	align:'right',
		    	  formatter:function(cellvalue, options, rowObj) {
		    		  if (rowObj.totalAmt != rowObj.total2) {
		    			  return $.jgrid.template("<span class='org-price'>({1})</span><br />{0} ", rowObj.total2, rowObj.totalAmt);
		    		  } else {
		    			  return rowObj.totalAmt;
		    		  }
		    	  }
		      },
		      {name:'stateName',  		index:'state', 		width: 130,	sortable:false,	align:'center'},
		      {name:'inputorName',  						width: 130,	sortable:false,	align:'center'},
	    	  {name:'createDate',  	index:'create_date',	width: 160, sortable:false,	align:'center'			  },
		      {
		    	  name: "state",	// any db column name here.
		    	  align: "center",
		    	  width: 80,
		    	  sortable:false,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  var html = '<a class="mg0-auto" data-role="button" data-icon="carat-r" data-iconpos="notext" target="_blank" href="Payment.do?cmd=paymentView&billId='+rowObject.billId+'">详细</a>';
		    		  return html;
		    	  } 
		      },			      
		    ],
	
	pager: '#gridpager',
	sortname: gridPager.sortname || 'create_date',
	sortorder: 'desc'
});