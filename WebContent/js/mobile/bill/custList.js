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
	    colNames:['客户名称', '所在地', '联系人', '联系电话', '状态', '', ''],
	    colModel:[
	        {name:'custUserName',  index:'custUserName',		sortable:true,	align:'left', width: 250,
				  formatter:function(cellvalue, options, rowObj) {
					 return rowObj.custUserName + " (" + rowObj.custUserNo + ")";
				  }
			},
			{name:'custLocationName', 	sortable:true,	width:200, 	align:'center',	index:'custLocationName'		},
			{name:'custContactName', 	sortable:true,	width:200, 	align:'center',	index:'custContactName'			},
			{name:'custTelNo', 			sortable:true,	width:150, 	align:'center',	index:'custTelNo'				},
			{name:'stateName',			sortable:true,	width:100, 	align:'center',	index:'state'					},
			{name:'custUserId',			hidden: true,	index:'custUserId'					},
			{name:'connection',			hidden: true,	index:'connection'					},
	    ],
	    sortname : gridPager.sortname || 'custUserName',
	    sortorder: gridPager.sortorder,
	    rownumbers:true,
	    loadCompleteCB: function(data){
	    	if (data.rows.length == 1) {
	    		if (data.rows[0].connection == "true"){
	    			window.location.href = selectActionUrl + "&custUserId=" + data.rows[0].hostUserId;
	    		}
	    	}
	    },
	    pager: jQuery('#gridpager')
	});
	
	gridObj.on("jqGridSelectRow", function(event, id, orgEvent) {
		if(id && id!==lastSel){
			jQuery(this).jqGrid('restoreRow',lastSel);
			lastSel=id;
		}
		
		var data = gridObj.getRowData(id);
		// validation here.
		if (data.connection !== "true")
		{
			KptApi.showError(hc_no_connection);
			return;
		}
		var custUserId = data.custUserId || '';
		window.location.href = selectActionUrl + "&custUserId=" + data.custUserId;
	});
});
