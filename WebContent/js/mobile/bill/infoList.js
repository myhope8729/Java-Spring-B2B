var searchForm = null;
var gridObj = null;
	
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
	postData: searchForm.serializeJSON(),
	datatype: "json",
	colNames:['标题', '类别', '发布者', '发布日期', '状态', '操作'],
	colModel:[ 
	      {name:'info',  index:'info',	 width: 200,	sortable:true,	align:'left', formatter:function(cellvalue, options, rowObject) 
	    	  {
	    	  	var rtn = cellvalue + '<br/><br/>';
    		  	return rtn += '<a data-role="button" data-mini="true" data-inline="true" href="Info.do?cmd=viewInfoPage&billId='+rowObject.billId+'&hostUserId='+rowObject.hostUserId+'" target="_blank">查看页面效果</a>';
    		  }
	      }, 
	      {name:'itype',  		index:'itype',			hidden:true,	sortable:false,	align:'left'},
	      {name:'inputorName', 	index:'inputorName',	hidden:true,	sortable:false,	align:'center'},
	      {name:'createDate', 	index:'create_date',	hidden:true,	sortable:false,	align:'center'},
	      {name:'stateName',  	index:'state',			hidden:false, width: 80,	sortable:false,	align:'center'},
	      {
	    	  name: "state",	// any db column name here.
	    	  align: "center",
	    	  width: 200,
	    	  sortable:false,
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  var html = '<div data-role="controlgroup" data-mini="true"><a data-theme="b" data-role="button" data-mini="true" href="Info.do?cmd=infoSet&billId='+rowObject.billId+'"><i class="mgr10 fa fa-picture-o"></i>编辑详情 </a>';
	    		  html += '<a data-role="button" data-mini="true" href="Info.do?cmd=infoAttachment&billId='+rowObject.billId+'" ><i class="mgr10 fa fa-cloud-upload text-success"></i>上传附件</a></div>';
	    		  
	    		  //if (rowObject.editableByState == "true") {
	    			  html += '<div data-role="controlgroup" data-mini="true"><a data-theme="b" data-role="button" data-icon="edit" data-iconpos="right" data-mini="true" href="Info.do?cmd=infoForm&billId='+rowObject.billId+'">修改</a>';
	    			  html += '<a data-theme="a" data-role="button" data-icon="delete" data-mini="true" data-iconpos="right" href="javascript: deleteBill(\''+rowObject.billId+'\')">删除</a></div>';
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
	sortorder: 'desc',
	radioOption:true,
    onSelectRow: function(id){
		if(id){
			gridObj.jqGrid('saveRow',lastSel);
			gridObj.jqGrid('editRow',id, true);
			lastSel = id;
		}
	},
	rownumbers:true
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



