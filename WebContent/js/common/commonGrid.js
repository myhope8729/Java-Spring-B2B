var gridPager = {page: 1, sortname: '', sortorder: 'asc', rowNum:10 };
var CommonGrid = {
	defaultOption : {
	    //data : [{"data":"data1"},{"data":"data2"},{"data":"data3"}],
	    datatype: 'json',
	    mtype: 'POST',
	    styleUI : 'Bootstrap',
	    colModel :[ 
	      {name: 'data', index: 'data', width: 200, editable:false, sortable: false} 
	    ],
	    jsonReader : {
	        root		: "rows",
			page		: "page.page", 
			total		: "page.total", 
			records		: "page.records",
	        repeatitems	: false
	    },
	    prmNames : {
	    	page	: "page.page",
	    	sort	: "page.sidx",
	    	order	: "page.sord",
	    	rows	: "page.rows"
	    },
	    loadComplete : function (data) {
	    	KptApi.unblockUI(".admin_body");
	    	
	    	if (data.result && data.result.resultCode != 0) 
	    	{
	    		KptApi.showError(data.result.resultMsg);
	    		return;
	    	}
	    	
	    	if (this.p.loadCompleteCB != undefined )
	    		this.p.loadCompleteCB(data);
	    	
	    },
	    autowidth: true,
		height: '100%',	
	    //height: 231,
	    viewrecords: true,
	    rowList: [10,20,30,50,100],
	    gridview: true,
	    radioselect: false,
	    loadui: false,
	    viewrecords: true,
	    emptyrecords: "没有记录！",
	    customfooter: false,
	    
	    onInitGrid : function(){
	    },
	    beforeRequest: function() {
	    	KptApi.blockUI2(".admin_body");
	    },
	    gridComplete: function()
	    {
	    	if (this.p.gridCompleteCB != undefined )
	    		this.p.gridCompleteCB();
	    },
	    serializeGridData: function(data) {
			var o = {};
			o['nd'] =  new Date().getTime();
			o['page.sord'] = this.p.sortorder || '';
			o['page.sidx'] = this.p.sortname || '';
			o['page.page'] = this.p.page || '1';
			o['page.rows'] = this.p.rowNum || '';
			
			return data + "&" + jQuery.param(o);
	    }//,
	    // This is user defined function.
	    /*setPagingVars: function(grid, pager) {
	    	if (pager.sortname != null) 
	    	{
	    		grid.jqGrid('setGridParam',{
		    		page		: pager.page, 
		    		sortname 	: pager.sortname,
		    		sortorder 	: pager.sortorder,
		    		rowNum 		: pager.rowNum,
		    	});
	    	}
	    }*/
	},
	
	treeDefaultOption: {
		
	},

	radioOption: {
		multiselect : true,
		multiboxonly : true,
		onSelectRow : function(rowid, status) {
				if (!status) {
					var selectedId = this.p.selarrrow;
					if (selectedId.length > 0) {
						$(this).setSelection(selectedId, false);
					}
				}
				$(this).setSelection(rowid, status);
			},
			onSelectAll : function(aRowids, status) {
				$(this).resetSelection();
			}
	},
	
	customFooterRow: {
		loadComplete : function (data) {
			KptApi.unblockUI(".admin_body");
			var template = '';
			$("#customFooterRow").remove();
			if(data.rows && data.rows.length > 0) {
				template = '<div style="width: 1198px;" class="ui-jqgrid-sdiv" id="customFooterRow">\
					<div class="ui-jqgrid-hbox">\
						<table role="presentation" style="width:1198px" class="ui-jqgrid-ftable ui-common-table table table-bordered">\
						<tbody>\
							<tr role="row" class="footrow footrow-ltr ">\
								<td role="gridcell" style="text-align:center;" aria-describedby="grid_Custom_TotName" width="150">' 
									+ data.footerData.totalName +
									'</td>\ <td role="gridcell" style="text-align:right;" aria-describedby="grid_Custom_TotData">' 
						           	+ data.footerData.totalData +
								'</td>\
							</tr>\
						</tbody>\
						</table>\
					</div>\
					</div>		';
			} else {
				template = '<div style="width: 1198px;" class="ui-jqgrid-sdiv" id="customFooterRow">\
					<div class="ui-jqgrid-hbox">\
						<table role="presentation" style="width:1198px" class="ui-jqgrid-ftable ui-common-table table table-bordered">\
						<tbody>\
							<tr role="row" class="footrow footrow-ltr ">\
								<td role="gridcell" style="text-align:center;" aria-describedby="grid_Custom_TotData">' 
						           	+ '<font color="#FF0000" face="微软雅黑" size="2">&nbsp; 未找到资料</font>' +
								'</td>\
							</tr>\
						</tbody>\
						</table>\
					</div>\
					</div>		';				
			}
			$("#gview_grid").append(template);
		}
	},	

	init : function (id, o) {
		var gobj;
		
		o		= $.extend({}, this.defaultOption, o);
		
		if (o.datatype == 'json' && (o.url === undefined || !o.url || o.url == '')) {
			alert('没有设置  grid data url');
			return null;
		}
		if (o.radioselect) {
			o = $.extend(o, this.radioOption);
		}
		
		if (o.customfooter) {
			o = $.extend(o, this.customFooterRow);
		}		
		
		gobj 	= $("#" + id).jqGrid(o);
		if (o.shrinkToFit && !o.shrinkToFit) {
			gobj.setGridWidth(986, false); 
		}
		
		return gobj;
	}
	
};


var CommonTreeGrid = {
		defaultOption : {
		    //data : [{"data":"data1"},{"data":"data2"},{"data":"data3"}],
		    datatype: 'json',
		    mtype: 'POST',
		    styleUI : 'Bootstrap',
		    colModel :[ 
		      {name: 'data', index: 'data', width: 200, editable:false, sortable: false} 
		    ],
		    jsonReader : {
		        root		: "rows",
		        repeatitems	: false
		    },
		    treeGrid: true,
		    treeGridModel: 'adjacency',
		    tree_root_level: 1,
		    treedatatype: "json",
		    treeReader : {
		    	level_field: "level",
		    	leaf_field: "isLeaf",
		    	expanded_field: "expanded",
				parent_id_field	: "parentId"
			},
			treeIcons : {
				"plus": "fa fa-plus",
				"minus": "fa fa-minus",
				"leaf" : "fa fa-file"
			},			
		    loadComplete : function (data) {
			    /*if (data.result && data.result.resultCode != 0) 
			    	alert(data.result.resultMsg);*/
		    },
		    height: 'auto',
		    pager: false,
		    shrinkToFit:false, 
		    viewrecords: true,
		    serializeGridData: function(data) {
		    	var o = {};
				o['nd'] =  new Date().getTime();
				return data + "&" + jQuery.param(o);
			}		    
		},
		
		init : function (id, o) {
			var gobj;
			
			o		= $.extend({}, this.defaultOption, o);
			
			if (o.datatype == 'json' && (o.url === undefined || !o.url || o.url == '')) {
				alert('没有设置  grid data url');
				return null;
			}
			if (o.radioselect) {
				o = $.extend(o, this.radioOption);
			}
			
			gobj 	= $("#" + id).jqGrid(o);
			if (o.shrinkToFit && !o.shrinkToFit) {
				gobj.setGridWidth(986, false); 
			}
			
			if (gobj == null) {
				return null;
			}
			
			gobj.jqGrid('setGridParam',{ 
	            loadComplete: function(data) {
	            	if (data.rows == null){
	            //		gobj.jqGrid('setGridHeight',"245" );            			
	            	}else if (data.rows.length <10){
	            //		gobj.jqGrid('setGridHeight',"245" );            			
	            	}
	            	else {
	            		//gobj.jqGrid('setGridHeight',"100%" );
	            	}
	            }
	        });
			
			return gobj;
		}
		
	};

function getEditableRowIds(gridObj) 
{   
	var gridIds = [], ii=0;
	/*    rowarr = $('.ui-row-ltr');
    if (rowarr != null) {
    	rowarr.each(function(index) {
			gridId = $(this).attr('id');
			if($(this).attr('editable')) gridIds[i++] = gridId;
		});
    	if(gridIds.length==0) return null;
	} else {
		return null;
	}		
    return gridIds;*/
    
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

/*o['page.sord'] = this.p.sortorder || '';
o['page.sidx'] = this.p.sortname || '';
o['page.page'] = this.p.page || '1';
o['page.rows'] = this.p.rowNum || '';
*/
function reloadGridByAjax(gridObj) 
{
	var searchForm = $(gridObj.attr("searchForm"));
	gridObj.jqGrid('setGridParam',{
		postData: searchForm.serialize()
	}).trigger('reloadGrid');
}

$(document).ready(function() {
	$.extend(true, CommonGrid.defaultOption, gridPager);
});
