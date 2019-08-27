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
		colNames:['billId', '单据编号', '仓库', '对方单位', '金额(元)', '单据状态', '处理类别<br />处理人', '制单人', '制单日期', '操作'],
		colModel:[ 
		          {name:'billId',  				key:true,				hidden:true									},
			      {name:'billIdDisplay',  		index:'bill_id',	 	width: 160,		sortable:true,	align:'center', 
			    	  formatter:function(cellvalue, options, rowObj){
			    		  if (rowObj.bnoUser){
			    			  return rowObj.bnoUser;
			    		  }else{
			    			  return rowObj.billId;
			    		  }
			    	  }
			      }, 
			      {name:'storeName',  	index:'storeName',		width: 100, 	sortable:true,	align:'left'},
			      {name:'hostUserName', index:'host_user_name', width: 180,		sortable:true,	align:'left', 
			    	  formatter:function(cellvalue, options, rowObj){
			    		  if (rowObj.rbillType == "DT0004" || rowObj.rbillType == "DT0008" ){
			    			  return rowObj.custUserName;
			    		  }else{
			    			  return rowObj.hostUserName;
			    		  }
			    	  }
			      },
			      {name:'total_amt',  	index:'total_amt',		width: 120,		sortable:true,	align:'right',
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  if (rowObj.totalAmt != rowObj.total2) {
			    			  return $.jgrid.template("<span style='color: #909090'>({0})</span><br />{1} ", rowObj.totalAmt, rowObj.total2);
			    		  } else {
			    			  return rowObj.total2;
			    		  }
			    	  }
			      },
			      {name:'stateName',  	index:'state',			width: 100,		sortable:true,	align:'center'	},
			      {name:'billProc',  	index:'v', width:130,	width: 150,		sortable:false,	align:'left',
			    	  formatter:function(cellvalue, options, rowObj) {
			    		  if (rowObj.state != "WS0002"){
			    			  return $.jgrid.template("{0}<br />{1}", rowObj.billProc, rowObj.procMan);
			    		  }else{
			    			  return "";
			    		  }
		    			  
			    	  }
			      },
			      {name:'inputorName',	index:'inputor_name',	width: 100,		sortable:true, 	align:'left'	},
			      {name:'createDate', 	index:'create_date', 	width: 120,		sortable:true,	align:'center'  },
			      {name: "control",								width: 100,		sortable:false,	align: "center",
			    	  formatter:function(cellvalue, options, rowObject) {
			    		  var html = '<a href="Return.do?cmd=viewReturn&billId='+rowObject.billId+'" target="_blank">详细</a>';
			    		  html += ' | <a href="Return.do?cmd=printReturn&billId='+rowObject.billId+'" target="_blank">打印</a>';
			    		  /*if (rowObject.state == "WS0003" || rowObject.state == "WS0004"){
			    			  html += ' | <a href="Return.do?cmd=returnForm&billId='+rowObject.billId+'">修改</a>';
				    		  html += ' | <a href="Return.do?cmd=deleteReturn&billId='+rowObject.billId+'">删除</a>';
			    		  }*/
			    		  return html;
			    	  } 
			      },
			    ],
		
		pager: '#gridpager',
		sortname: gridPager.sortname || 'createDate',
		sortorder:'desc'
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
			url 	: "Return.do?cmd=deleteReturnAjax",
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