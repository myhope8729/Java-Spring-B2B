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
		colNames:['单据编号', '调价说明', '制单日期', '制单人', '单据状态', '处理类别<br />处理人',   '操作'],
		colModel:[ 
		          {name:'billId',  		index:'bill_id',	 	width: 160,		sortable:true,	align:'left'}, 
			      {name:'custUserName',	index:'cust_user_name',	width: 250,		sortable:true,	align:'left'},
			      {name:'createDate', 	index:'create_date', 	width: 120,		sortable:true,	align:'center'  },
			      {name:'inputorName',	index:'inputor_name',	width: 200,		sortable:true, align:'left'},
			      {name:'stateName',  	index:'state',			width: 100,		sortable:true,	align:'center'},
			      {name:'billProc',  	index:'v', 				width: 200,		sortable:false,	align:'left',
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  if (rowObj.state != "WS0002"){
			    			  return $.jgrid.template("{0}<br />{1}", rowObj.billProc || "", rowObj.procMan || "");
			    		  }else{
			    			  return "";
			    		  }
		    			  
			    	  }
			      },
			      {name: "control",		align: "center",	sortable:false, width:100,
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  var html = '';
			    		  if (rowObject.state != "WS0004"){
			    			  html = '<a href="Price.do?cmd=viewPrice&billId='+rowObject.billId+'" target="_blank">详细</a><br/>';
			    		  }
			    		  if (rowObject.state == "WS0003" || rowObject.state == "WS0004"){
		    				  html += ' <a href="Price.do?cmd=priceForm&billId='+rowObject.billId+'">修改</a>';
				    		  html += ' | <a href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
			    		  }
			    		  return html;
			    	  } 
			      },			      
			    ],
		
		pager: '#gridpager',
		sortname: 'create_date',
		sortorder: 'DESC',
		radioOption:true,
	    onSelectRow: function(id){
			if(id){
				gridObj.jqGrid('saveRow',lastSel);
				gridObj.jqGrid('editRow',id, true);
				lastSel = id;
			}
		}
	});
	
	$(".btnReset").click(function(e){
		searchForm.submit();
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