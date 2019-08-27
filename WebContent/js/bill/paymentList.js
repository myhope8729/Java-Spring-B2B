var searchForm = null;
var gridObj = null;
var gridId = "grid";

jQuery(document).ready(function(){
	
	gridObj = $("#" + gridId);
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	searchForm.submit(function(){
		reloadGridByAjax(gridObj);
		return false;
	});
	
	CommonGrid.init(gridId, {
		url: ajaxUrl,
		postData: searchForm.serialize(),
		datatype: "json",
		colNames:['单据编号', '订货方', '货款名称', '收款金额(元)', '单据状态', '处理类别<br />处理人', '制单日期<br />收款日期', '备注', '操作'],
		colModel:[ 
			      {name:'billId',  		index:'bill_id',	 	width: 180,	sortable:true,	align:'center',
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  var bnoUser = rowObj.bnoUser || '';
			    		  return (bnoUser != '')? bnoUser : cellvalue;
			    	  }
			      }, 
			      {name:'custUserName', index:'cust_user_name',	width: 200,	sortable:true,	align:'left'},
			      {name:'paytypeName',  index:'paytype_name',	width: 150, sortable:true,	align:'left', formatter:function(cellvalue, options, rowObject) {
			    	  return rowObject.paytypeName/* + "<br />" + (rowObject.paymentType || '')*/;
		    	  }},
			      {name:'total_amt',  	index:'total_amt',		width: 120,	sortable:true,	align:'right',
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  if (rowObj.totalAmt != rowObj.total2) {
			    			  return $.jgrid.template("<span class='org-price'>({1})</span><br />{0} ", rowObj.total2, rowObj.totalAmt);
			    		  } else {
			    			  return rowObj.totalAmt;
			    		  }
			    	  }
			      },
			      {name:'stateName',  		index:'state', 		width: 100,	sortable:true,	align:'center'},
		    	  {name:'processDetail',  	index:'billProc', 	width: 200,	sortable:false,	align:'left', 
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  return cellvalue;
			    	  }
			      },
			      {name:'createDate',  	index:'create_date',	width: 120,	sortable:true,	align:'center',
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  return rowObject.createDate + "<br />" + (rowObject.arriveDate);
			    	  }
			      },
			      {name:'note',  index:'note',	sortable:true,	align:'center', width:150},
			      {
			    	  name: "state",	// any db column name here.
			    	  align: "center",
			    	  width: 100,
			    	  sortable:false,
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  var html = '<a href="Payment.do?cmd=paymentView&billId='+rowObject.billId+'" target="_blank">详细</a>';
			    		  
			    		  if (rowObject.editableByState == "true") {
			    			  html += ' | <a href="Payment.do?cmd=paymentForm&billId='+rowObject.billId+'">修改</a><br/>';
			    			  html += '<a href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
			    		  }
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
			url 	: "Payment.do?cmd=deletePaymentAjax",
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
					window.location.href = "Payment.do?cmd=paymentList";
				}
			}
		});
	});
}
