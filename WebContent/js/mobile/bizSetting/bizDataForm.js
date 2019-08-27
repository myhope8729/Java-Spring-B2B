var selrow = 1;
var custList = null;
var ppList = null;
var contGrid = null;

$(document).ready(function(){
	contGrid = jQuery('#gridContent');
	
	CommonGrid.init('grid', {
	    url: 'BizData.do?cmd=bizDataFormGridAjax',
	    colNames:['业务分类', 'codeId'],
	    colModel:[
	      {name:'bizDataName',	sortable:false, 	align:'left'		},
	      {name:'codeId',		hidden:true								}
	    ],
	    pager: false,
	    gridCompleteCB : function() {
	    	jQuery("#grid").setSelection(1);
	    },
	    onSelectRow: function(id){
	    	var codeId = jQuery('#grid').getCell(id, "codeId");
	    	selrow = id;
	    	jQuery('#codeId').val(codeId);
	    	
	    	$.mvcAjax({
	    		url 	 : "BizData.do?cmd=bizDataContentAjax",
	    		data 	 : "codeId=" + codeId,
	    		dataType : 'json',
	    		success  : function(data, ts) {
	    			
	    			// set the prepay setting
	    			resetColProps(data);
	    			
	    			for (i = 0; i < 5; i++){
	    				var colName = 'c' + (i + 1);
		    			jQuery('#gridContent').hideCol(colName);
	    			}
	    			
	    			jQuery.each(data.jsonObj.colNames, function(i, obj){
	    				if (obj == '') return;
	    				
	    				var colName = 'c' + (i + 1);
	    				
	    				jQuery('#gridContent').setLabel(colName, data.jsonObj.colNames[i]);
		    			jQuery('#gridContent').showCol(colName);
	    			});
	    			
	    			jQuery('#gridContent').clearGridData();
	    			contGrid.addRowData("X", data.rows, "last");
	    			
	    			// set the grid width again.
	    			var t = jQuery('#gridContent').closest("div.ui-jqgrid").parents("div:first");
	    			var gWidth = t.width();
    				if(data.jsonObj.colNames.length * 120 <= gWidth) {
    	    			jQuery('#gridContent').setGridWidth(gWidth, true);
    				}
	    			
    				//$("select").checkboxradio("refresh");
	    		}
	    	});
	    },
		rowNum: -1
	});
	CommonGrid.init('gridContent', {
		datatype:"local",
		colModel:[
		      {label:'c1', 		name:'c1',		hidden:false,		align:'left',		sortable:false,		width:120,
		    	  formatter:function(cellvalue, options, rowObject)
		    	  {
		    		  return "<input name='c1' type='text' value='" + (cellvalue==undefined?'':cellvalue) + "' >";
		    	  }
		      },
		      {label:'c2', 		name:'c2',		hidden:false,		align:'left',		sortable:false,		width:120,
		    	  formatter:function(cellvalue, options, rowObject)
		    	  {
		    		  return "<input name='c2' type='text' value='" + (cellvalue==undefined?'':cellvalue) + "' >";
		    	  }
		      },
		      {label:'c3', 		name:'c3',		hidden:false,		align:'left',		sortable:false,		width:120,
		    	  formatter:function(cellvalue, options, rowObject)
		    	  {
		    		  return "<input name='c3' type='text' value='" + (cellvalue==undefined?'':cellvalue) + "' >";
		    	  }
		      },
		      {label:'c4', 		name:'c4',		hidden:false,		align:'left',		sortable:false,		width:120,
		    	  formatter:function(cellvalue, options, rowObject)
		    	  {
		    		  return "<input name='c4' type='text' value='" + (cellvalue==undefined?'':cellvalue) + "' >";
		    	  }
		      },
		      {label:'c5', 		name:'c5',		hidden:false,		align:'left',		sortable:false,		width:120,
		    	  formatter:function(cellvalue, options, rowObject)
		    	  {
		    		  return "<input name='c5' type='text' value='" + (cellvalue==undefined?'':cellvalue) + "' >";
		    	  }
		      },
		      {label:'seqNo', 	name:'seqNo',   hidden:true, 			align:'left'}
		],
		autowidth:false,
	    pager: false,
		rowNum: -1,
		multiselect:true
	});
	
	// bind the select boxes
	$('body').on('change', 'select.custList', function(e){
		var custTypeId = $(this).val();
		var custObj = $(this);
		
		$.mvcAjax({
    		url 	 : "BizData.do?cmd=loadPrePayTypeAjax",
    		data 	 : "custTypeId=" + custTypeId,
    		dataType : "post",
    		dataType : 'json',
    		success  : function(data, ts) {
    			ppList = data.ppList;
    				
    			var tag = custObj.parents("tr:first").find("td select.ppList");
    			
    			if (tag.length > 0)
    			{
    				tag.html("<option value=''>" + emptyLabel + "</option>");
					$(ppList).each(function(ind, p){
						tag.append("<option value='"+p.payTypeId+"'"+ (p.payTypeId==''? " selected='selected'" : "") +">"+p.payTypeName+"</option>");
					});
					
					tag.selectmenu( "refresh" );
    			}
    		}
		});
	});
	
	$(document).on('change', 'input[name=c4], input[name=c5]', changeColValue);
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#bizDataForm').serialize()
	}).trigger('reloadGrid');
}

function reloadContentGrid(){
	jQuery('#gridContent').trigger("reloadGrid");
}

function changeColValue(e)
{
	var obj = $(e.target);
	var val = obj.val();
	
	if (val.length < 1) return;
	
	// validation need in val.
	if (! isNumeric(val)) {
		KptApi.showError( numberRequired );
		obj.focus();
		return;
	}
}

function saveBizData()
{
	// start the validation.
	var codeId = $("#codeId").val();
	var hasError = false;
	if (codeId == 'BD0013') {
			
		$("select.custList").each( function(ind, c2Obj) {
			var obj = $(c2Obj);
			if (obj.val().length > 0) {
				//var p = obj.parents("tr:first");
				var c3 = $("select[name=c3]:eq("+ind+")").val();
				if ( c3.length < 1 ) {
					KptApi.showError( $.jgrid.template(required, "预付款名称") );
					$("select[name=c3]:eq("+ind+")").focus();
					hasError = true; return;
				} 
				
				var c4 = $("input[name=c4]:eq("+ind+")").val();
				if (!isNumeric(c4)) {
					KptApi.showError( numberRequired );
					$("input[name=c4]:eq("+ind+")").focus();
					hasError = true; return;
				} 
				
				var c5 = $("input[name=c5]:eq("+ind+")").val();
				if (!isNumeric(c5)) {
					KptApi.showError( numberRequired );
					$("input[name=c5]:eq("+ind+")").focus();
					hasError = true; return;
				} 
			}
		});
	}
	
	if (hasError) return;
	
	$.mvcAjax({
		url 	 : "BizData.do?cmd=saveBizDataAjax",
		data 	 : $('#bizDataForm').serialize(),
		success  : function(data, ts) {
			KptApi.showMsg(data.result.resultMsg);
			jQuery("#grid").setSelection(selrow);
		}
	});
}



function deleteBizData(){
	var selectedIds = jQuery('#gridContent').jqGrid('getGridParam','selarrrow');
	if (selectedIds.length == 0) {
		KptApi.showInfo(selRowMsg);
		return;
	}
	
	selectedIds = getRowId(selectedIds, '#gridContent', "seqNo");
	
	$.mvcAjax({
		url 	 : "BizData.do?cmd=deleteBizDataAjax",
		data 	 : "codeId=" + $('#codeId').val() + "&seqNo=" + selectedIds,
		success  : function(data, ts) {
			KptApi.showMsg(delSuccessMsg);
			jQuery("#grid").setSelection(selrow);
		}
	});
}

function resetColProps(data)
{
	var codeId = $("#codeId").val();
	if (codeId == 'BD0013') 
	{
		ppList = null;
		custList = data.custList;
		
		contGrid.setColProp("c2", { formatter: function(cellvalue, options, rowObject) {
			var seqNo = rowObject.seqNo || '';
			var html = "<select class='custList' name='c2' seqNo='" + seqNo + "' >";
			html += "<option  value=''>" + emptyLabel + "</option>";
			$(custList).each(function(ind, c){
				html += "<option value='"+c.custTypeId+"'"+ (c.custTypeId==cellvalue? " selected='selected'" : "") +">"+c.custTypeName+"</option>";
			});
			html += "</select>";
			return html;
		}});
		
		contGrid.setColProp("c3", { formatter: function(cellvalue, options, rowObject) {
			var seqNo = rowObject.seqNo || '';
			var html = "<select class='ppList' name='c3' seqNo='" + seqNo + "'>";
			html += "<option  value=''>" + emptyLabel + "</option>";
			
			ppList = [];
			$(custList).each(function(ind, c){
				if (c.custTypeId == rowObject.c2) {
					ppList = c.ppList;
				}
			});
			
			$(ppList).each(function(ind, p){
				html += "<option value='"+p.payTypeId+"'"+ (p.payTypeId==cellvalue? " selected='selected'" : "") +">"+p.payTypeName+"</option>";
			});
			html += "</select>";
			return html;
		}});
	} else {
		ppList = null;
		custList = null;
		
		contGrid.setColProp("c2", {formatter: function(cellvalue, options, rowObject){
			return "<input name='c2' type='text' value='" + (cellvalue==undefined?'':cellvalue) + "' >";
		}});
		
		contGrid.setColProp("c3", {formatter: function(cellvalue, options, rowObject){
			return "<input name='c3' type='text' value='" + (cellvalue==undefined?'':cellvalue) + "' >";
		}});
	}
}