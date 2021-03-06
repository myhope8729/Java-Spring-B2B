$(document).ready(function(){
	cartGridData.colNames.push('item_id');
	cartGridData.colModel.push({name:'itemId', key:true, hidden: true});
	
	initCartGrid();
	initProcGrid();
});

function initCartGrid()
{
	cartGridData.colNames.unshift("图片");
	cartGridData.colModel.unshift( {name: "itemSmallImg", index: "itemImgPath", align: "center", sortable:false, width:80, formatter: funcImg} );
	
	cartGridData.colNames.push("退货价");
	cartGridData.colNames.push("退货数量");
	cartGridData.colNames.push("金额(元)");
	cartGridData.colNames.push("备注");
	
	cartGridData.colModel.push( {
		name: "price", index: "price2", align : "right", width:100,
		formatter: function(cellValue, option, rowObject){
			if (rowObject.price == rowObject.price2){
				return rowObject.price;
			}else{
				if (rowObject.price < rowObject.price2){
					return "<span class='old_price'>(" + rowObject.price + ")</span><font color='FF0000'>" + rowObject.price2 + "</font>";
				}
				return "<span class='old_price'>(" + rowObject.price + ")</span>" + rowObject.price2;
			}
		}
	});
	
	cartGridData.colModel.push( {
		name: "qty", index: "qty", align : "right", width:100,
		formatter: function(cellValue, option, rowObject){
			if (rowObject.qty == rowObject.qty2){
				return rowObject.qty;
			}else{
				if (rowObject.qty2 == 0){
					return "<span class='old_price'>(" + rowObject.qty + ")</span><font color='FF0000'>" + rowObject.qty2 + "</font>";
				}else{
					return "<span class='old_price'>(" + rowObject.qty + ")</span>" + rowObject.qty2;
				}
			}
		}
	});
	cartGridData.colModel.push( {name: "tot2", index: "tot2", align : "right", width:100} );
	cartGridData.colModel.push( {name: "note", index: "note", width:150} );
		
	gridObj = $("#grid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	gridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		url: gridObj.attr("ajaxUrl"),
		datatype: "json",
		keyName : "itemId",
		postData: searchForm.serialize(),
		//colNames: $.extend(true, itemGridData.colNames, ['item_id'] ),
		//colModel: $.extend(true, itemGridData.colModel, [{name:'itemId', key:true, hidden: true}]),
		colNames: cartGridData.colNames,
		colModel: cartGridData.colModel,
		radioOption:true,
	    onSelectRow: function(id){
		},
		rownumbers:true
	}));
}

function initProcGrid()
{
	CommonGrid.init('procGrid', {
	    url: 'Return.do?cmd=billProcGridAjax',
	    postData: $('#billForm').serialize(),
	    colModel:[
	       {name:'userName', 			label:'处理方',		width:'250', 	align:'left',		sortable:false	},
	       {name:'empName', 			label:'处理人',		width:'250',	align:'left',		sortable:false  },
	       {name:'billProcFullName',	label:'处理类别',	width:'200',	align:'left',		sortable:false	},
	       {name:'stateName', 			label:'处理状态',	width:'100',	align:'center',		sortable:false	},
	       {name:'note',				label:'处理意见',	width:'150',	align:'left',		sortable:false	},
	       {name:'updateDate', 			label:'处理时间',	width:'150',	align:'center',		sortable:false	},
	    ]
	});
}