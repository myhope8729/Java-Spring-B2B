CommonGrid.init('grid', {
    url: 'HostCust.do?cmd=hostSettingGridAjax',
    postData: $('#supplySettingSearchForm').serializeJSON(),
    colNames:['登录名', '供货方名称', '操作'],
    colModel:[
      {name:'hostUserNo',		sortable:true,	width:150, 	align:'left', index:'hostUserNo'				},
      {name:'hostUserName',		sortable:true,	width:300,	align:'left', index:'hostUserName'			},
      {name: "control",			sortable:false,	width:80,	align:'center', 
    	  formatter:function(cellvalue, options, rowObject) {
    		  return "<a class=\"mg0-auto\" data-role=\"button\" data-icon=\"delete\" data-mini=\"true\" data-iconpos=\"notext\" href=\"javascript:deleteHost('" +rowObject.hostUserId + "', '" + rowObject.custUserId + "')\">移除</a>";
    	  }
      },
    ],
    sortname: 'hostUserName',
    sortorder: 'desc',
    rowNum :  gridPager.rowNum,
	page :  gridPager.page,
    pager: jQuery('#gridpager')
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#supplySettingSearchForm').serializeJSON()
	}).trigger('reloadGrid');
}

function openCustTypeForm(){
	Transitions.load({
		url		: "HostCust.do?cmd=supplySettingForm",
		form	: "supplySettingSearchForm"
	});
}

function deleteHost(hostId, custId){
	KptApi.confirm(deleteConfirmMsg, function(){
		window.location.href = "HostCust.do?cmd=deleteHost&hostId=" + hostId + "&custId=" + custId;
	});
}