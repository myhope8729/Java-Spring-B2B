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
		colNames:['单据编号', '客户名称', '金额(元)', '付款方式<br />付款类别', '制单日期<br />制单人', /*'备注',*/ '单据状态',  '处理类别<br />处理人', '操作'],
		colModel:[ 
			      {name:'billId',  		index:'bill_id',	 	width: 160,	sortable: true,	align:'center'	}, 
			      {name:'custUserName', index:'cust_user_name', width: 180,	sortable: true,	align:'left'	},
			      {name:'total_amt',  	index:'total_amt',		width: 150,	sortable: true,	align:'right',
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  if (rowObj.totalAmt != rowObj.total2) {
			    			  return $.jgrid.template("<span class='org-price'>({1})</span><br />{0} ", rowObj.total2, rowObj.totalAmt);
			    		  } else {
			    			  return rowObj.totalAmt;
			    		  }
			    	  }
			      },
			      {name:'paytypeName',  index:'paytype_name',	sortable:false,	align:'left', 
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return rowObject.paytypeName + "<br />" + (rowObject.paymentType || '');
			    	  }
			      },
			      {name:'createDate',  index:'create_date',	sortable:false,	align:'center',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return rowObject.createDate + "<br />" + (rowObject.inputorName || '');
			    	  }
			      },
			      {name:'stateName',  	index:'state',	width: 100, sortable:true,	align:'center'},
			      {name:'processDetail',index:'', 		width: 200,	sortable:false,	align:'left'},
			      {
			    	  name: "state",	// any db column name here.
			    	  align: "center",
			    	  width: 100,
			    	  sortable:false,	
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  var html = '<a href="Sale.do?cmd=saleView&billId='+rowObject.billId+'" target="_blank">详细</a> | ';
			    		  
			    		  if (rowObject.editableByState == "true") {
			    			  html += '<a href="Sale.do?cmd=saleForm&billId='+rowObject.billId+'">修改</a><br/>';
			    			  html += '<a href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a> | ';
			    		  }
			    		  html += '<a href="Sale.do?cmd=saleForm&billId='+rowObject.billId+'&copymark=1">复制 </a>';
			    		  return html;
			    	  } 
			      },
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
