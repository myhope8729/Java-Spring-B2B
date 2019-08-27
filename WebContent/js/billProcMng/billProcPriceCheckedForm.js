$(document).ready(function(){
	var billProc = itemGridData.billProc;
	
	var gridColNames = new Array();
	var gridColModels = new Array();
	
	gridColNames.push(itemGridData.colNames.join("<br/>"));
	gridColModels.push({name: "properties", align : "center", sortable:false, width:200,
		formatter:function(cellvalue, options, rowObject){
			var rtn = "";
			jQuery.each(itemGridData.colModel, function(i, colModel){
				rtn += (eval("rowObject." + colModel.name) || '') + "<br/>";
			});
			return rtn;
		}
	});
	
	gridColNames.push("前次入库价<br/>本次入库价<br/>成本价");
	gridColModels.push({name:"price", align:"center", sortable:false, width:120,
		formatter:function(cellvalue, options, rowObject){
			return rowObject.priceIn + "<br/>" + rowObject.price + "<br/>" + rowObject.cost;
		}
	});
	
	gridColNames.push("入库数量<br/>入库金额(元)");
	gridColModels.push({name:"qty", align:"center", sortable:false, width:120,
		formatter:function(cellvalue, options, rowObject){
			return rowObject.qty + "<br/>" + rowObject.total;
		}
	});
	
	jQuery.each(itemGridData.priceCols, function(i, col){
		gridColNames.push(col.propertyDesc + "(旧)<br/>" + col.propertyDesc + "(新)");
		gridColModels.push({name : "d" + (i+1) + "2", align:"center", sortable:false, width:120,
			formatter:function(cellvalue, options, rowObject){
				var rtn = "";
				var percentage = 0;
				var oldPrice = round(eval("rowObject.d" + (i+1) + "1"), 2);
				var newPrice = round(eval("rowObject.d" + (i+1) + "2"), 2);
				
				rtn += "<font color='#15428B'>";
				rtn += "<input type='text' name='oldPrice_" + (i+1) + "' readonly class='chPrice oldPrice' value='" + oldPrice + "'/>";
				rtn += "</font>";
				if (rowObject.cost != "" && rowObject.cost != "0" && oldPrice != "" && oldPrice != "0"){
					percentage = Math.round((parseFloat(oldPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(oldPrice)) / 10;
					rtn += "<font color='#15428B' style='font-size:12px;'>(" + percentage + "%)</font>";
				}
				rtn += "<br/><input type='text' class='chPrice oldPrice' name='newPrice_" + (i+1) + "' readonly value='" + newPrice + "' rowid='" + rowObject.itemId + "' />";
				rtn += "<span class='new_percentage' style='font-size:12px;'>";
				if (rowObject.cost != "" && rowObject.cost != "0" && newPrice != "" && newPrice != "0"){
					percentage = Math.round((parseFloat(newPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(newPrice)) / 10;
					rtn += "(" + percentage + "%)";
				}
				rtn += "</span>";
				return rtn;
			}
		});
	});
	
	gridColNames.push("备注");
	gridColModels.push({name:"note", sortable:false, width:150});
	gridColNames.push("itemId");
	gridColModels.push({name:"itemId", sortable:false, hidden:true, key:true});
	
	CommonGrid.init('gridItem', {
	    datatype:"local",
	    data: billProc.priceDetailList,
	    colNames:gridColNames,
	    colModel:gridColModels,
	    rownumbers:true,
	    loadCompleteCB : function() {
	    	var ids = jQuery('#gridItem').getDataIDs();
	    	for (var i = ids.length - 1; i >= 0; i--){
	    		jQuery('#gridItem').jqGrid('editRow', ids[i], {keys:false});
	    	}
	    },
		onSelectRow: function(id){
	    	if (id) {
				var row = jQuery('#gridItem').getRowData(id);
				jQuery('#gridItem').jqGrid('editRow', id, {keys:false});
			}
		},
		rowNum : 10000
	});
	
	CommonGrid.init('gridProc', {
	    url: 'BillProc.do?cmd=billProcHistGridAjax',
	    postData: $('#billProcPriceForm').serialize(),
	    colModel:[
	       {name:'userName', 			label:'处理方',		width:'250', 	align:'left',		sortable:false	},
	       {name:'empName', 			label:'处理人',		width:'250',	align:'left',		sortable:false  },
	       {name:'billProcFullName',	label:'处理类别',	width:'200',	align:'left',		sortable:false	},
	       {name:'stateName', 			label:'处理状态',	width:'100',	align:'center',		sortable:false	},
	       {name:'procNote',			label:'处理意见',	width:'150',	align:'left',		sortable:false	},
	       {name:'updateDate', 			label:'处理时间',	width:'150',	align:'center',		sortable:false	},
	    ],
	    rowNum : 10000
	});
});
