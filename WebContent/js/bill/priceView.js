jQuery(document).ready(function(){
	
	initCartGrid();
	initProcGrid();
});

function initCartGrid()
{
	gridColNames = new Array();
	gridColNames.push("图片");
	gridColNames.push(gridData.ncpColDesc.join("<br/>"));
	gridColNames.push("前次入库价<br/>本次入库价<br/>成本价");
	gridColNames.push("入库数量<br/>入库金额(元)");
	for (i=0;i<gridData.priceColDesc.length; i++){
		gridColNames.push(gridData.priceColDesc[i] + "(旧)<br/>" + gridData.priceColDesc[i] + "(新)");
	}
	gridColNames.push("备注");
	
	gridColModel = new Array();
	gridColModel.push( {name: "itemSmallImg", sortable:false, align: "center", width:60, formatter: funcImg} );
	gridColModel.push({name: "properties", align : "center", width:200,
		formatter:function(cellvalue, options, rowObject){
			var html = "";
			for (i = 0; i<gridData.ncpColNames.length; i++){
				if (i==0){
					html = eval("rowObject." + gridData.ncpColNames[i]);
				}else{
					html = html + "<br/>" + eval("rowObject." + gridData.ncpColNames[i]);
				}
			}
			return html;
		}
	});
	gridColModel.push({name: "priceIn", align: "center", width:120,
		formatter:function(cellvalue, options, rowObject){
			return rowObject.priceIn + "<br/>" + rowObject.price + "<br/>" + rowObject.cost;
		}
	});
	
	gridColModel.push({name: "qty", align: "center", width:120, 
		formatter:function(cellvalue, options, rowObject){
			return rowObject.qty + "<br/>" + rowObject.total;
		}
	});
	for (i=0;i<gridData.priceColNames.length; i++){
		var priceColName = gridData.priceColNames[i];
		gridColModel.push({name: "price" + i, align: "left", width:120, formatoptions:{colNo:i},
			formatter:function(cellvalue, options, rowObject){
				var html = "";
				var oldPrice = eval("rowObject.d" + (options.colModel.formatoptions.colNo + 1) + "1");
				var newPrice = eval("rowObject.d" + (options.colModel.formatoptions.colNo + 1) + "2");
				var percentage = 0;
				
				html = "<font color='#15428B' style='font-size:12px;'>" + "<input type='text' name='old_price" + options.colModel.formatoptions.colNo + 
					"' readonly class='chPrice oldPrice' value='" + oldPrice + "'/>" + "</font>";
				if (rowObject.cost != "" && rowObject.cost != "0" && oldPrice != "" && oldPrice != "0"){
					percentage = Math.round((parseFloat(oldPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(oldPrice)) / 10;
					html = html + "<font color='#15428B' style='font-size:12px;'> (" + percentage + "%)</font>";
				}
				html = html + "<br/><font color='#808080' style='font-size:12px;'>" + "<input type='text' readonly class='chPrice oldPrice' class='chPrice' name='new_price" + options.colModel.formatoptions.colNo + 
					"' value='" + newPrice + "' />" + "</font>";
				if (rowObject.cost != "" && rowObject.cost != "0" && newPrice != "" && newPrice != "0"){
					percentage = Math.round((parseFloat(newPrice) - parseFloat(rowObject.cost)) * 1000 / parseFloat(newPrice)) / 10;
					html = html + "<font color='#808080' style='font-size:12px;'> (" + percentage + "%)</font>";
				}
				return html;
			}
		});
	}
	gridColModel.push({name:"note", align:"left", width:130});
	
	gridObj = $("#grid");
	gridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype:"local",
		data: billItems,
		colNames: gridColNames,
		colModel: gridColModel,
		//sortname: 'bill_id',
		rownumbers:true,
		rowNum:10000
	}));
}

function initProcGrid()
{
	CommonGrid.init('procGrid', {
	    url: 'Price.do?cmd=billProcGridAjax',
	    postData: $('#userItemsForm').serialize(),
	    colModel:[
	       {name:'userName', 			label:'处理方',		width:'250', 	align:'left',		sortable:false	},
	       {name:'empName', 			label:'处理人',		width:'250',	align:'left',		sortable:false  },
	       {name:'billProcFullName',	label:'处理类别',	width:'200',	align:'left',		sortable:false	},
	       {name:'stateName', 			label:'处理状态',	width:'100',	align:'center',		sortable:false	},
	       {name:'procNote',			label:'处理意见',	width:'150',	align:'left',		sortable:false	},
	       {name:'updateDate', 			label:'处理时间',	width:'150',	align:'center',		sortable:false	},
	    ]
	});
}