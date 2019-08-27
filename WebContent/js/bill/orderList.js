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
		colNames:['单据编号', '供货方', '制单日期', '金额(元)', '单据状态', '付款方式<br />付款类别', '处理类别<br />处理人', '操作'],
		colModel:[ 
	      {name:'billId',  		index:'bill_id',	 	width: 160,		sortable:true,		align:'center'}, 
	      {name:'hostUserName', index:'host_user_name', width: 180,		sortable:true,		align:'left'},
	      {name:'createDate',  	index:'create_date',	width: 110,		sortable:true,		align:'center'},
	      {name:'total_amt',  	index:'total_amt',		width: 150,		sortable:true,		align:'right',
	    	  formatter:function(cellvalue, options, rowObj) {
	    		  if (rowObj.totalAmt != rowObj.total2) {
	    			  return $.jgrid.template("<span class='org-price'>({1})</span><br />{0} ", rowObj.total2, rowObj.totalAmt);
	    		  } else {
	    			  return rowObj.totalAmt;
	    		  }
	    	  }
	      },
	      {name:'stateName', 	index:'state',			width: 100,		sortable:true,		align:'center'},
	      {name:'paytypeName',  index:'paytype_name',	width: 120,		sortable:false,		align:'left', 
	    	  formatter: function(cellvalue, options, rowObject) {
		    	  if (cellvalue === undefined) return "";
		    	  var html = cellvalue;
		    	  if (rowObject.paymentType != undefined && rowObject.paymentType != "") {
		    		  html += " / " + rowObject.paymentType;
		    	  }
		    	  return html;
	    	  }
	      },
	      {name:'processDetail',  index:'v', 			width:200,		sortable:false,		align:'left'},
	      {
	    	  name: "state",	// any db column name here.
	    	  align: "center",
	    	  width: 100,
	    	  sortable:false,
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  var html = '<a href="Order.do?cmd=orderView&billId='+rowObject.billId+'" target="_blank">详细</a>';
	    		  
	    		  if (rowObject.editableByState == "true") {
	    			  html += ' | <a href="Order.do?cmd=orderForm&billId='+rowObject.billId+'">修改</a><br/>';
	    			  html += '<a href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
	    		  }
	    		  html += ' | <a href="Order.do?cmd=orderForm&billId='+rowObject.billId+'&copymark=1">复制 </a>';
	    		  return html;
	    	  }
	      }
	    ],
		pager: '#gridpager',
		sortname: gridPager.sortname || 'create_date',
		sortorder: 'desc'
	});
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
