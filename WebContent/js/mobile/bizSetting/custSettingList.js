	CommonGrid.init('grid', {
	    url: 'HostCust.do?cmd=custSettingGridAjax',
	    postData: $('#custSettingSearchForm').serializeJSON(),
	    colNames:['客户名称', '状态', '操作'],
	    colModel:[
	      {name:'custUserFullName',		sortable:true,	width:300, 	align:'left', index:'custUserName, custUserNo'	},
	      {name:'stateName',		 	sortable:true,	width:80, 	align:'center',	index:'state'						},
	      {name: "control",				sortable:false,	width:80,	align:'center', classes:'gridLink',
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return '<a class="mg0-auto" data-role="button" data-icon="edit" data-mini="true" data-iconpos="notext" href="HostCust.do?cmd=custSettingForm&hostId=' + rowObject.hostUserId + '&custId=' + rowObject.custUserId + '">设置</a>';
	    	  }
	      },
	    ],
	    sortname: 'custUserId',
	    sortorder: 'asc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
		pager: jQuery('#gridpager')
	});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#custSettingSearchForm').serializeJSON()
	}).trigger('reloadGrid');
}