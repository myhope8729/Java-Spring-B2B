var gridObj = $("#grid");
var ajaxUrl = gridObj.attr("ajaxUrl");
var	searchForm = $( gridObj.attr("searchForm") );
	
searchForm.submit(function(){
	reloadGridByAjax(gridObj);
	return false;
});
	
CommonGrid.init('grid', {
	url: ajaxUrl,
	postData: searchForm.serialize(),
	datatype: "json",
	colNames:['金额(元)', '单据状态', '制单人', '制单日期', '操作'],
	colModel:[ 
		      {name:'total_amt',  	index:'total_amt',		sortable: true,	align:'right', width:130,
		    	  formatter:function(cellvalue, options, rowObj) {
		    		  if (rowObj.totalAmt != rowObj.total2) {
		    			  return $.jgrid.template("<span class='org-price'>({1})</span>/{0} ", rowObj.total2, rowObj.totalAmt);
		    		  } else {
		    			  return rowObj.totalAmt;
		    		  }
		    	  }
		      },
		      {name:'stateName',  	index:'state',			width: 150, sortable:false,	align:'center'},
		      {name:'inputorName',  index:'inputor_name',	width: 120, hidden:true,	align:'center'},
		      {name:'createDate',  	index:'create_date',	width: 150,	sortable:false,	align:'center'},
		      {
		    	  name: "control",	// any db column name here.
		    	  align: "center",
		    	  width: 120,
		    	  sortable:false,	
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  var html = '<div class="mg0-auto" data-role="controlgroup" data-type="horizontal" data-mini="true"><a data-role="button" data-icon="carat-r" data-iconpos="notext" target="_blank" href="Sale.do?cmd=saleViewForMobile&billId='+rowObject.billId+'">详细</a>';
		    		  if (rowObject.editableByState == "true") {
		    			  html += '<a data-role="button" data-icon="delete" data-iconpos="notext" href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
		    		  }
		    		  html += '</div>';
		    		  return html;
		    	  } 
		      },			      
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
			url 	: "Sale.do?cmd=deleteSaleAjax",
			data 	: {billId: billId},
			dataType: 'json',
			success : function(data, ts)
			{
				if (data.result.resultCode == result.FAIL)
				{
					KptApi.showMsg(data.result.resultMsg);
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
