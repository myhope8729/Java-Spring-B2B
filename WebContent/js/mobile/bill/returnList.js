var gridObj = $("#grid");
var ajaxUrl = gridObj.attr("ajaxUrl");
var searchForm = $( gridObj.attr("searchForm") );

searchForm.submit(function(){
	reloadGridByAjax(gridObj);
	return false;
});

CommonGrid.init('grid', {
	url: ajaxUrl,
	postData: searchForm.serializeJSON(),
	datatype: "json",
	colNames:['billId', '金额(元)', '单据状态', '制单人', '制单日期', '操作'],
	colModel:[ 
	          {name:'billId',  		key:true,				hidden:true									},
		      {name:'total_amt',  	index:'total_amt',		sortable:false,	align:'right',
		    	  formatter:function(cellvalue, options, rowObj) {
		    		  if (rowObj.totalAmt != rowObj.total2) {
		    			  return $.jgrid.template("<span style='color: #909090'>({0})</span><br />{1} ", rowObj.totalAmt, rowObj.total2);
		    		  } else {
		    			  return rowObj.total2;
		    		  }
		    	  }
		      },
		      {name:'stateName',  	index:'state',			sortable:false,	align:'center'},
		      {name:'inputorName',	index:'inputor_name',	sortable:false, align:'center'},
		      {name:'createDate', 	index:'d', width:130,	sortable:false,	align:'left'  },
		      {name: "control",		sortable:false,			align: "center", width:80,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  var html = '<a class="mg0-auto" data-role="button" data-icon="carat-r" data-iconpos="notext" target="_blank" href="Return.do?cmd=viewReturnForMobile&billId='+rowObject.billId+'">详细</a>';
		    		  return html;
		    	  } 
		      },			      
		    ],
	
	pager: '#gridpager',
	sortname: gridPager.sortname || 'createDate',
	sortorder:'desc',
	radioOption:true
});

$(".btnReset").click(function(e){
	searchForm.submit();
});

