var gridPager = {page: 1, sortname: '', sortorder: 'asc', rowNum:12 };

var CommonGrid = {
	defaultOption : {
	    datatype: 'json',
	    mtype: 'POST',
	    jsonReader : {
	        root		: "rows",
			page		: "page.page", 
			total		: "page.total", 
			records		: "page.records",
	        repeatitems	: false,
	        subgrid		: {repeatitems:false}
	    },
	    prmNames : {
	    	page	: "page.page",
	    	sort	: "page.sidx",
	    	order	: "page.sord",
	    	rows	: "page.rows"
	    },
		hoverrows:false,
		viewrecords:true,
		pagerpos:'left',
		recordpos:'center',
		gridview:true,
		loadonce: false,
		rowNum:10,
//		height:570,
		height: '100%',	
		autowidth:true,
		rowList:[10,12,15,30,40],
	    emptyrecords: "没有记录！",
	    customfooter: false,
	    loadui: 'disable',
	    serializeGridData: function(data) {
			var o = {};
			o['nd'] =  new Date().getTime();
			o['page.sord'] = this.p.sortorder || '';
			o['page.sidx'] = this.p.sortname || '';
			o['page.page'] = this.p.page || '1';
			o['page.rows'] = this.p.rowNum || '';
			
			data = $.extend(true, o, data);
			return data;// + "&" + jQuery.param(o);
	    },
	    beforeRequest: function() {
	    	KptApi.blockUI2(".ui-content:first");
	    },
	    gridComplete: function()
	    {
	    	KptApi.unblockUI(".ui-content:first");
	    	if (this.p.gridCompleteCB != undefined )
	    		this.p.gridCompleteCB();
	    }
	},
	
	customFooterRow: {
		loadComplete : function (data) {
			var template = '';
			$("#customFooterRow").remove();
			if(data.rows && data.rows.length > 0) {
				template = '<div class="ui-jqgrid-sdiv ui-content" data-theme="a" id="customFooterRow">\
				    <div class="ui-jqgrid-hbox">\
				        <table class="ui-jqgrid-ftable ui-common-table ui-responsive table-stroke" role="presentation">\
				            <tbody>\
				                <tr class="footrow footrow-ltr" role="row">\
				                    <td aria-describedby="grid_Custom_TotName" role="gridcell" width="150">' 
									+ data.footerData.totalName +
									'</td>\ <td role="gridcell"  aria-describedby="grid_Custom_TotData" style="text-align:right;">' 
						           	+ data.footerData.totalData +
								'</td>\
			                </tr>\
				            </tbody>\
				        </table>\
				    </div>\
				</div>';
			}  
			$("#gview_grid").append(template);
		}
	},	
	

	init : function (id, o) {
		var gobj;
		o		= $.extend({}, this.defaultOption, o);
		if (o.datatype == 'json' && (o.url === undefined || !o.url || o.url == '')) {
			alert('没有设置 url');
			return null;
		}
		if (o.customfooter) {
			o = $.extend(o, this.customFooterRow);
		}	
		
		gobj 	= $("#" + id).jqGrid(o);
		if (!o.autowidth && o.width != undefined && o.shrinkToFit) {
			var winWidth=$(window).width();
			var gridWidth = o.width;
			/*alert(winWidth + ':' + gridWidth);*/
			
			if(winWidth>gridWidth) gobj.setGridWidth(winWidth-10, o.shrinkToFit); 
		}
		
		return gobj;
	}
	
};

var CommonTreeGrid = {
	defaultOption : {
	    datatype: 'json',
	    mtype: 'POST',
		hoverrows:false,
		viewrecords:true,
	    jsonReader : {
	        root		: "rows",
			page		: "page.page", 
			total		: "page.total", 
			records		: "page.records",
	        repeatitems	: false,
	        subgrid		: {repeatitems:false}
	    },
	    prmNames : {
	    	page	: "page.page",
	    	sort	: "page.sidx",
	    	order	: "page.sord",
	    	rows	: "page.rows"
	    },		
		loadonce: false,
		autowidth:true,
		height: '100%',	
	    emptyrecords: "没有记录！",	    
	    treeGrid: true,
	    treeGridModel: 'adjacency',
	    tree_root_level: 1,
	    treedatatype: "json",
	    treeReader : {
	    	left_field: "isLeaf",
	    	expanded_field: "expanded",
			parent_id_field	: "parentId"
		},
	    loadComplete : function (data) {
		    /*if (data.result && data.result.resultCode != 0) 
		    	alert(data.result.resultMsg);*/
	    },
	    pager: false,
	    serializeGridData: function(data) {
			var o = {};
			o['nd'] =  new Date().getTime();
			o['page.sord'] =  '';
			o['page.sidx'] =  '';
			o['page.page'] =  '1';
			o['page.rows'] =  '';
			
			data = $.extend(true, o, data);
			return data;// + "&" + jQuery.param(o);			
		}		    
	},
	
	init : function (id, o) {
		var gobj;
		
		o		= $.extend({}, this.defaultOption, o);
		
		if (o.datatype == 'json' && (o.url === undefined || !o.url || o.url == '')) {
			alert('没有设置  grid data url');
			return null;
		}
		
		gobj 	= $("#" + id).jqGrid(o);
		return gobj;
	}
	
};


function getEditableRowIds(gridObj) 
{   
	var gridIds = [], ii=0;
    
	var ids = gridObj.getDataIDs();
    if (ids != null && ids.length > 0) {
    	$.each(ids, function(i, id) {
    		row = gridObj.getGridRowById(id);
    		if($(row).attr('editable')=="1") gridIds[ii++] = id;
    	});    	
    	if(gridIds.length==0) return null;
	} else {
		return null;
	}		
    return gridIds;    
}

function reloadGridByAjax(gridObj) 
{
	var searchForm = $(gridObj.attr("searchForm"));
	gridObj.jqGrid('setGridParam',{
		postData: searchForm.serializeJSON()
	}).trigger('reloadGrid');
}


