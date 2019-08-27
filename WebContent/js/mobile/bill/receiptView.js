cartGridData.colNames.push('item_id');
cartGridData.colModel.push({name:'itemId', key:true, hidden: true});

initCartGrid();
if (!isDraft){
	initProcGrid();
}

function initCartGrid()
{
	
	cartGridData.colNames.push("入库价");
	cartGridData.colNames.push("数量");
	cartGridData.colNames.push("金额(元)");
	cartGridData.colNames.push("备注");
	
	cartGridData.colModel.push( {
		name: "price", index: "price2", width:100, align : "right", sortable:false,
		formatter: function(cellValue, option, rowObject){
			if (rowObject.price == rowObject.price2){
				return rowObject.price;
			}else{
				return "<span class='old_price'>(" + rowObject.price + ")</span>" + rowObject.price2;
			}
		}
	});
	
	cartGridData.colModel.push( {
		name: "qty", index: "qty", width:80, align : "right",  sortable:false,
		formatter: function(cellValue, option, rowObject){
			if (rowObject.qty == rowObject.qty2){
				return rowObject.qty;
			}else{
				return "<span class='old_price'>(" + rowObject.qty + ")</span>" + rowObject.qty2;
			}
		}
	});
	cartGridData.colModel.push( {name: "tot2", index: "tot2", sortable:false, align : "right"} );
	cartGridData.colModel.push( {name: "note", index: "note", width:80, sortable:false  } );
		
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	gridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		url: gridObj.attr("ajaxUrl"),
		datatype: "json",
		keyName : "itemId",
		postData: searchForm.serializeJSON(),
		//colNames: $.extend(true, itemGridData.colNames, ['item_id'] ),
		//colModel: $.extend(true, itemGridData.colModel, [{name:'itemId', key:true, hidden: true}]),
		colNames: cartGridData.colNames,
		colModel: cartGridData.colModel,
		radioOption:true
	}));
}

function initProcGrid()
{
	CommonGrid.init('procGrid', {
	    url: 'Receipt.do?cmd=billProcGridAjax',
	    postData: $('#billForm').serializeJSON(),
	    colModel:[
	       {name:'userName', 			label:'处理方',		width:'150', 	align:'center',		sortable:false	},
	       {name:'empName', 			label:'处理人',		width:'150',	align:'center',		sortable:false  },
	       {name:'billProcFullName',	label:'处理类别',	width:'200',	align:'center',		sortable:false	},
	       {name:'stateName', 			label:'处理状态',	width:'200',	align:'center',		sortable:false	},
	       {name:'updateDate', 			label:'处理时间',	width:'200',	align:'center',		sortable:false,	
	    	   	formatter:function(cellvalue, options, rowObj) {
	    			  return cellvalue.substr(0, 10);
		    	}
	       },
	    ]
	});
}