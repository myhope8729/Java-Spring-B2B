var gridObj = $("#grid");
var searchForm = $( gridObj.attr("searchForm") );
var ajaxUrl = gridObj.attr("ajaxUrl");
	
searchForm.submit(function(){
	reloadGridByAjax(gridObj);
	return false;
});
	
CommonGrid.init('grid', {
	url: ajaxUrl,
	postData: searchForm.serializeJSON(),
	datatype: "json",
	colNames:['制单日期', '金额(元)', '单据状态', '制单人',  '操作'],
	colModel:[ 
          {name:'createDate',  	index:'create_date',	sortable:false,		align:'left', width:160,
        	  formatter:function(cellValue, options, rowObj){
        		  if (rowObj.custUserId == $("#userId").val()){
    				  return rowObj.createDate + '<br/>' + rowObj.hostUserName;
    			  }else{
    				  return rowObj.createDate + '<br/>' + rowObj.custUserName;
    			  }
        	  }
          },
	      {name:'total_amt',  	index:'total_amt',		sortable:false,		align:'right', 
	    	  formatter:function(cellvalue, options, rowObj) {
	    		  if (rowObj.totalAmt != rowObj.total2) {
	    			  return $.jgrid.template("<span class='org-price'>({1})</span><br />{0} ", rowObj.total2, rowObj.totalAmt);
	    		  } else {
	    			  return rowObj.totalAmt;
	    		  }
	    	  }
	      },
	      {name:'stateName',  	index:'state',			sortable:false,	align:'center', width:100},
	      {name:'inputorName',  index:'inputor_name',	sortable:false,	align:'center', hidden:true},
	      {
	    	  name: "control",	// any db column name here.
	    	  align: "center",
	    	  width: 80,
	    	  sortable:false,
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  var html = '<a class="mg0-auto" data-role="button" data-icon="carat-r" data-iconpos="notext" href="Order.do?cmd=orderViewForMobile&billId='+rowObject.billId+'">详细</a>';
	    		  if (rowObject.editableByState == "true") {
	    			  html += '<a class="mg0-auto" data-role="button" data-icon="delete" data-iconpos="notext" href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
	    		  }
	    		  return html;
	    	  } 
	      }
	],
	
	pager: '#gridpager',
	sortname: gridPager.sortname || 'create_date',
	sortorder: 'desc',
	radioOption:true
});

/**
 * Delete the bill by billId.
 * @param billId
 */
function deleteBill(billId)
{
	KptApi.confirm(messages.deleteMsg, function(){
		$.mvcAjax({
			url 	: "Order.do?cmd=deleteOrderAjax",
			data 	: {billId: billId},
			dataType: 'json',
			success : function(data, ts)
			{
				if (data.result.resultCode == result.FAIL)
				{
					KptApi.showError(data.result.resultMsg);
				}
				else
				{
					reloadGridByAjax(gridObj);
					KptApi.showMsg(data.result.resultMsg);
				}
			}
		});
	});
}