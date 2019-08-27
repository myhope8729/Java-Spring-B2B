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
	colNames:['附件名称', '上传日期', '操作'],
	colModel:[ 
	      {name:'attachName',  index:'attachName',	 width: 220,	sortable:true,	align:'left', formatter:function(cellvalue, options, rowObject) 
	    	  {
    		  	return '<a href="/uploaded/info_detail/'+cellvalue+'" target="_blank">'+cellvalue+' </a>';
	    	  	//return cellvalue;  
    		  }
	      }, 
	      {name:'createDate', index:'createDate',	sortable:false,	align:'center', formatter:'date',
	    	  formatoptions:{
	    		  srcformat: "Y-m-d H:i:s",
	    	      newformat: "Y-m-d" 
	    	    }
	      },
	      {
	    	  name: "createDate",	// any db column name here.
	    	  align: "center",
	    	  width: 70,
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  var  html = '<a class="mg0-auto" data-role="button" data-mini="true" data-icon="delete" data-iconpos="notext" href="javascript: deleteItem(\''+rowObject.id+'\')">删除</a>';
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
//gridObj.jqGrid('navGrid','#gridpager',{edit:false,add:false,del:false,search:false, position:'right'});


$("#btnSubmit").click(function(e){
	if ($("#attachment").val().length < 1 ) {
		KptApi.showInfo(messages.noFileMsg);
		return;
	}
	$("#infoAttachmentUploadForm").submit();
});
	

/**
 * Delete the bill by id.
 * @param id
 */
function deleteItem(id)
{
	KptApi.confirm(messages.deleteMsg, function(){
		$.mvcAjax({
			url 	: "Info.do?cmd=deleteInfoAttachmentAjax",
			data 	: {id: id},
			dataType: 'json',
			success : function(data, ts)
			{
				if (data.result.resultCode == result.FAIL)
				{
					KptApi.showMsg(data.result.resultMsg);
				}
				else
				{
					window.location.href = "Info.do?cmd=infoAttachment&billId=" + $("#billId").val();
				}
			}
		});
	});
}



