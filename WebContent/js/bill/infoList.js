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
		colNames:['标题', '类别', '发布者', '发布日期', '状态', '操作'],
		colModel:[ 
		      {name:'info',  index:'info',	 width: 200,	sortable:true,	align:'left', 
		    	  formatter:function(cellvalue, options, rowObject) 
		    	  {
		    		  return '<a href="Info.do?cmd=viewInfoPage&billId='+rowObject.billId+'&hostUserId='+rowObject.hostUserId+'" target="_blank">'+cellvalue+' </a>';
	    		  }
		      },
		      {name:'itype', 	 	index:'itype',			width:200,		sortable:true,	align:'left'},
		      {name:'inputorName', 	index:'inputorName',	width:150,		sortable:true,	align:'left'},
		      {name:'createDate', 	index:'create_date',	width:120,		sortable:true,	align:'center'},
		      {name:'stateName',  	index:'state',			width:100,		sortable:true,	align:'center'},
		      {
		    	  name: "state",	// any db column name here.
		    	  align: "center",
		    	  width: 250,
		    	  sortable:false,
		    	  formatter:function(cellvalue, options, rowObject) {
		    		  var html = '<a href="Info.do?cmd=infoSet&billId='+rowObject.billId+'">编辑详情 </a>';
		    		  html += ' | <a href="Info.do?cmd=infoAttachment&billId='+rowObject.billId+'" target="_blank"> 上传附件</a>';
		    		  
		    		  //if (rowObject.editableByState == "true") {
		    			  html += ' | <a href="Info.do?cmd=infoForm&billId='+rowObject.billId+'">修改</a>';
		    			  html += ' | <a href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a>';
		    		  //}
		    		  if (rowObject.state == 'WS0002') {
		    			  /*html += ' | <a href="Info.do?cmd=infoSignDetail&billId='+rowObject.billId+'">报名表</a>';*/
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
			url 	: "Info.do?cmd=deleteInfoAjax",
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
					KptApi.showMsg(data.result.resultMsg);
					reloadGridByAjax(gridObj);
				}
			}
		});
	});
}



