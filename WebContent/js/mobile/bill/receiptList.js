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
	colNames:['bill_id', '金额(元)', '单据状态', '制单人', '制单日期', '操作'],
	colModel:[ 
		      {name:'billId',  		index:'bill_id',	 	hidden:true,	sortable:false,	align:'left'}, 
		      {name:'total_amt',  	index:'total_amt',		sortable:false,	align:'right',
		    	  formatter:function(cellvalue, options, rowObj) {
		    		  if (rowObj.totalAmt != rowObj.total2) {
		    			  return $.jgrid.template("<span style='color: #909090'>({0})</span><br />{1} ", rowObj.total2, rowObj.totalAmt);
		    		  } else {
		    			  return rowObj.totalAmt;
		    		  }
		    	  }
		      },
		      {name:'stateName',  	index:'state',			sortable:false,	align:'center'},
		      {name:'inputorName', 	index:'inputor_name',	hidden:true,	align:'left'},
		      {name:'date',  		index:'d', 				sortable:false,	align:'left',
		    	  formatter:function(cellvalue, options, rowObj) {
	    			  return $.jgrid.template("{0}", rowObj.createDate.substr(0, 10));
		    	  }
		      },
		      {name: "control",		sortable:false,			align: "center", width:120,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  var html = '<div class="mg0-auto" data-role="controlgroup" data-type="horizontal" data-mini="true"><a data-role="button" data-icon="carat-r" data-iconpos="notext" target="_blank" href="Receipt.do?cmd=viewReceiptForMobile&billId='+rowObject.billId+'">详细</a>';
		    		  if (rowObject.state == "WS0003" || rowObject.state == "WS0004"){
			    		  html += '<a data-role="button" data-icon="delete" data-iconpos="notext" href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
		    		  }
		    		  html += '</div>';
		    		  return html;
		    	  } 
		      },			      
		    ],
	
	pager: '#gridpager',
	sortname: gridPager.sortname || 'create_date',
	sortorder:'DESC',
	radioOption:true
});
//gridObj.jqGrid('navGrid','#gridpager',{edit:false,add:false,del:false,search:false, position:'right'});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#receiptListFrm').serializeJSON()
	}).trigger('reloadGrid');
}

$(".btnReset").click(function(e){
	searchForm.submit();
});

function deleteBill(billId)
{
	KptApi.confirm(messages.deleteMsg, function(){
		$.mvcAjax({
			url 	: "Receipt.do?cmd=deleteReceiptAjax",
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