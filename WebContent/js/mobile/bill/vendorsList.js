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
	    colNames:['供货方名称', '所在地', '联系人', '联系电话', '状态', '', ''],
	    colModel:[
	        {name:'hostUserName',  index:'hostUserName',		sortable:true,	align:'left', width: 250,
				  formatter:function(cellvalue, options, rowObj) {
					 return rowObj.hostUserName + " (" + rowObj.hostUserNo + ")";
				  }
			},
			{name:'hostLocationName', 	sortable:true,	width:200, 	align:'center',	index:'hostLocationName'		},
			{name:'hostContactName', 	sortable:true,	width:200, 	align:'center',	index:'hostContactName'			},
			{name:'hostTelNo', 			sortable:true,	width:150, 	align:'center',	index:'hostTelNo'				},
			{name:'stateName',			sortable:true,	width:100, 	align:'center',	index:'state'					},
			{name:'hostUserId',			hidden: true,	index:'hostUserId'					},
			{name:'connection',			hidden: true,	index:'connection'					},
	    ],
	    sortname: 'hostUserName',
	    rownumbers:true,
	    sortorder: 'desc',
	    loadCompleteCB: function(data){
	    	if (data.rows.length == 1) {
	    		if (data.rows[0].connection == "true"){
	    			window.location.href = selectActionUrl + "&hostUserId=" + data.rows[0].hostUserId;
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
		var hostUserId = data.hostUserId;
		window.location.href = selectActionUrl + "&hostUserId=" + hostUserId;
	});
});


function openCustTypeForm(){
	Transitions.load({
		url		: "HostCust.do?cmd=supplySettingForm",
		form	: "supplySettingSearchForm"
	});
}