var searchForm = null;
var gridObj = null;
jQuery(document).ready(function(){
	
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
		colNames:['单据编号', '供货方', '金额(元)', '付款方式', '仓库', '单据状态', '处理类别<br />处理人','制单日期<br />入库日期', '操作'],
		colModel:[ 
			      {name:'billId',  		index:'bill_id',	 	width: 160,		sortable:true,	align:'center'}, 
			      {name:'hostUserName', index:'host_user_name', width: 180,		sortable:true,	align:'left'},
			      {name:'total_amt',  	index:'total_amt',		width: 120,		sortable:true,	align:'right',
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  if (rowObj.totalAmt != rowObj.total2) {
			    			  return $.jgrid.template("<span style='color: #909090'>({0})</span><br />{1} ", rowObj.total2, rowObj.totalAmt);
			    		  } else {
			    			  return rowObj.totalAmt;
			    		  }
			    	  }
			      },
			      {name:'paytypeName',  index:'paytype_name',	width: 120,		sortable:true,	align:'left'},
			      {name:'storeName',  	index:'storeName',		width: 120,		sortable:true,	align:'left'},
			      {name:'stateName',  	index:'state',			width: 100,		sortable:true,	align:'center'},
			      {name:'billProc',  	index:'v', 				width: 150,		sortable:false,	align:'left',
			    	  formatter:function(cellvalue, options, rowObj) {
		    			  return $.jgrid.template("{0}<br />{1}", rowObj.billProc, rowObj.procMan);
			    	  }
			      },
			      {name:'date',  		index:'d', 				width:100,		sortable:false,	align:'center',
			    	  formatter:function(cellvalue, options, rowObj) {
		    			  return $.jgrid.template("{0}<br />{1}", rowObj.createDate.substr(0, 10), rowObj.arriveDate.substr(0, 10));
			    	  }
			      },
			      {name: "control",		sortable:false,			width: 100,		align: "center",
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  var html = '<a href="Receipt.do?cmd=viewReceipt&billId='+rowObject.billId+'" target="_blank">详细</a>';
			    		  if (rowObject.state == "WS0003" || rowObject.state == "WS0004"){
			    			  html += ' | <a href="Receipt.do?cmd=receiptForm&billId='+rowObject.billId+'">修改</a><br/>';
				    		  html += '<a href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
			    		  }
			    		  return html;
			    	  } 
			      },
			    ],
		
		pager: '#gridpager',
		sortname: gridPager.sortname || 'create_date',
		sortorder:'DESC',
		radioOption:true
	});
	
	$(".btnReset").click(function(e){
		searchForm.submit();
	});
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
