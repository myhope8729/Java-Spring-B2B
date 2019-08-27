var searchForm = null;
var gridObj = null;
jQuery(document).ready(function(){
	
	var lastSel = 0;
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	searchForm.submit(function(){
		reloadGridByAjax(gridObj);
		return false;
	});
	
	CommonGrid.init('grid', {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
		colNames:['billId', '单据编号', '供货方', '金额(元)', '付款方式', '仓库', '单据状态', '制单日期<br/>入库日期'],
		colModel:[ 
		          {name:'billId',  			index:'bill_id',		key:true,		hidden:true,	align:'center'	},
		          {name:'billIdDisplay',  	index:'bill_id',	 	width: 220,		sortable:true,	align:'left', 
			    	  formatter:function(cellvalue, options, rowObj){
			    		  if (rowObj.bnoUser){
			    			  return rowObj.bnoUser;
			    		  }else{
			    			  return rowObj.billId;
			    		  }
			    	  }
			      },
			      {name:'hostUserName', index:'host_user_name', sortable:false,	align:'left'},
			      {name:'total_amt',  	index:'total_amt',		sortable:false,	align:'right',
			    	  formatter:function(cellvalue, options, rowObj){
			    		  if (rowObj.totalAmt != rowObj.total2) {
			    			  return $.jgrid.template("<span style='color: #808080'>({0})</span><br />{1} ", rowObj.totalAmt, rowObj.total2);
			    		  } else {
			    			  return rowObj.total2;
			    		  }
			    	  }
			      },
			      {name:'paytypeName',	index:'paytype_name', 	sortable:false, align:'center'},
			      {name:'storeName',	index:'store_name', 	sortable:false, align:'center'},
			      {name:'stateName',  	index:'state',			sortable:false,	align:'center'},
			      {name:'date',  		index:'d', width:130,	sortable:false,	align:'left',
			    	  formatter:function(cellvalue, options, rowObj) {
		    			  return $.jgrid.template("{0}<br />{1}", rowObj.createDate, rowObj.arriveDate);
			    	  }
			      }			      
			    ],
		pager: '#gridpager',
		sortname: gridPager.sortname || 'billId',
		radioOption:true,
		rownumbers:true
	});
	gridObj.jqGrid('navGrid','#gridpager',{edit:false,add:false,del:false,search:false, position:'right'});
	
	gridObj.on("jqGridSelectRow", function(event, id, orgEvent) {
		window.location.href = selectActionUrl + "&rbillId=" + id;
	});
	
	$(".btnReset").click(function(e){
		searchForm.submit();
	});
});

