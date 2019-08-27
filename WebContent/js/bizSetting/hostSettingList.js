$(document).ready(function(){
	CommonGrid.init('grid', {
	    url: 'HostCust.do?cmd=hostSettingGridAjax',
	    postData: $('#supplySettingSearchForm').serialize(),
	    colNames:['登录名', '供货方名称', '所在地', '联系人', '联系电话', '状态', '操作'],
	    colModel:[
	      {name:'hostUserNo',		sortable:true,	width:150, 	align:'left', 	index:'hostUserNo'				},
	      {name:'hostUserName',		sortable:true,	width:200,	align:'left', 	index:'hostUserName'			},
	      {name:'hostLocationName', sortable:true,	width:300, 	align:'left',	index:'hostLocationName'		},
	      {name:'hostContactName', 	sortable:true,	width:150, 	align:'left',	index:'hostContactName'			},
	      {name:'hostTelNo', 		sortable:true,	width:250, 	align:'center',	index:'hostTelNo'				},
	      {name:'stateName',		sortable:true,	width:100, 	align:'center',	index:'state'					},
	      {name: "control",			sortable:false,	width:100,	align:'center', 
	    	  formatter:function(cellvalue, options, rowObject) {
	    		  return "<a href=\"javascript:deleteHost('" +rowObject.hostUserId + "', '" + rowObject.custUserId + "')\">移除</a>";
	    	  }
	      },
	    ],
	    sortname: 'hostUserName',
	    sortorder: 'desc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#supplySettingSearchForm').serialize()
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