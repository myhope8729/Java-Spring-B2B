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
	colNames:['调价说明', '单据状态', '制单人', '制单日期',  '操作'],
	colModel:[ 
		      {name:'custUserName',	index:'cust_user_name',	sortable:false,	align:'left',width:200},
		      {name:'stateName',  	index:'state',			sortable:false,	align:'center', width:140},
		      {name:'inputorName',	index:'inputor_name',	sortable:false, align:'center',	width:120},
		      {name:'createDate', 	index:'create_date', 	sortable:false,	align:'left'  },
		      {name:'control',		align:'center',			sortable:false,	align:'center',	width:80,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  var html = '';
		    		  if (rowObject.state != "WS0004"){
		    			  html = '<div class="mg0-auto" data-role="controlgroup" data-type="horizontal" data-mini="true"><a data-role="button" data-icon="carat-r" data-iconpos="notext" target="_blank" href="Price.do?cmd=viewPriceForMobile&billId='+rowObject.billId+'">详细</a>';
		    		  }
		    		  if (rowObject.state == "WS0003" || rowObject.state == "WS0004"){
		    			  html += '<a data-role="button" data-icon="delete" data-iconpos="notext" href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
		    		  }
		    		  if (rowObject.state != "WS0004"){
		    			  html += '</div>';
		    		  }
		    		  return html;
		    	  } 
		      },			      
		    ],
	
	pager: '#gridpager',
	sortname: 'create_date',
	sortorder: 'DESC',
	radioOption:true
});
	
$(".btnReset").click(function(e){
	searchForm.submit();
});

/**
 * Delete the bill by billId.
 * @param billId
 */
function deleteBill(billId)
{
	KptApi.confirm(messages.deleteMsg, function(){
		$.mvcAjax({
			url 	: "Price.do?cmd=deletePriceAjax",
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