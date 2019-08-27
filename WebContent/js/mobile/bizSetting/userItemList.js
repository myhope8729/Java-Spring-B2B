	var itemExist = false;
	function funcCell(cellvalue, options, rowObject){
		
		var imageNm = 'noImage_300x200.gif';
		if (cellvalue != "") imageNm = cellvalue;
		/*console.log(jsonObj);*/
		var itemName, cat1, cat2, cat1Name, cat2Name, price, priceName, unit, unitName, itemNo;
		for (i=0; i< jsonObj.properties.length; i++){
			switch (jsonObj.properties[i]){
				case "PT0006":
					itemName = eval("rowObject." + jsonObj.colModel[i + 1].name);
					break;
				case "PT0003":
					cat1 = eval("rowObject." + jsonObj.colModel[i + 1].name);
					cat1Name = jsonObj.colNames[i + 1];
					break;
				case "PT0004":
					cat2 = eval("rowObject." + jsonObj.colModel[i + 1].name);
					cat2Name = jsonObj.colNames[i + 1];
					break;
				case "PT0002":
					if(jsonObj.colNames[i + 1]==undefined || jsonObj.colNames[i + 1] == '' || eval("rowObject." + jsonObj.colModel[i + 1].name) == '')
						break;
					price = eval("rowObject." + jsonObj.colModel[i + 1].name);
					priceName = jsonObj.colNames[i + 1];
					break;
				case "PT0007":
					unit = eval("rowObject." + jsonObj.colModel[i + 1].name);
					unitName = jsonObj.colNames[i + 1];
					break;		
				case "PT0001":
					itemNo = eval("rowObject." + jsonObj.colModel[i + 1].name);
					break;							
			}
		}
		
		if(cat1==undefined) cat1 = '未分类';
		if(cat2==undefined) cat2 = '未分类';
		if(price==undefined) price = '';
		if(priceName==undefined || priceName=='') priceName = '价格';
		if(unit==undefined || unit=='') unit = ''; else price=price+"/"+unit;

		htmlStr = '<div class="custom_block3">\
							<div class="ui-block-a"> ' + 
								'<img src="'
								+ imageNm + 
								'" alt="image" width="150px"> </div>' +		
							'<div class="ui-block-b">\
								<div> <h5 style="color:#333;">'+ itemNo +  ':&nbsp;<strong>' 
									+ itemName + '</strong></h5></div>';
			htmlStr += '<div class="mgl10">' +  priceName + ':&nbsp;<b style="color:#777;">' + price + '</b></div>';
/*		if (cat2Name){
			htmlStr += '<div class="mgl20"><small>' + cat2Name + ':&nbsp;<b>' + cat2 + '</b></small></div>';
		}*/
		
		htmlStr +='<div class="mgt5"><a class="btn btn-primary" href="UserItem.do?cmd=userItemForm&itemId='
										+ rowObject.itemId +
										'" data-role="button" data-iconpos="left" data-mini="true" data-theme="a" data-icon="edit">修改</a>' +
									'</div>'+
						'</div>';
        return htmlStr;
	}
	
	CommonGrid.init('grid', {
		url: 'UserItem.do?cmd=userItemGridAjax',
		postData: $('#searchForm').serializeJSON(),
	    mtype: "POST",
	    colNames:['商品目录'],
	    colModel:[
	      {name:'itemSmallImg',		sortable:false,	width:150, 	align:'left',	formatter:funcCell	}
	    ],
	    sortname: 'update_date',
	    sortorder: 'desc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
		loadComplete:function(data){
			if (data.rows.length > 0)
				itemExist = true;
		},
	    pager: jQuery('#gridpager')
	});
	
	
	jQuery("#category, #state").change(function(){
		reloadGrid();
	});
	jQuery("#search").click(function(){
		reloadGrid();
	});

	function reloadGrid() {
		jQuery('#grid').jqGrid('setGridParam',{
			page:1, 
			postData: $('#searchForm').serializeJSON()
		}).trigger('reloadGrid');
	}
	
	function addItem(){
		if (!itemExist){
			KptApi.confirm(messages.addItemConfirm,function(){
				location.href = "UserItem.do?cmd=itemList";
			});
		}else{
			location.href = "UserItem.do?cmd=itemList";
		}
	}