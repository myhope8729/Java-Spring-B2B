	function funcImg(cellvalue, options, rowObject){
		
		var imageNm = 'noImage_300x200.gif';
		if (cellvalue != "") imageNm = cellvalue;
		var cat1 = rowObject.cat1;
		var cat2 = rowObject.cat2;
		if(cat1==undefined) cat1 = '未分类';
		if(cat2==undefined) cat2 = '未分类';

		htmlStr = '<div class="custom_block3">\
							<div class="ui-block-a"> ' + 
								'<img src="/uploaded/useritem/'
								+ imageNm + 
								'" alt="image" width="150px" height="100px"> </div>' +		
							'<div class="ui-block-b">\
								<div> <h4>   <strong>' 
									+ rowObject.itemName +
                    		         '</strong> \
									  </h4>\
								</div>\
								<div class="mgl10">大类:&nbsp;<b>' 
									+ cat1 + 
									 '</b>\
								</div>\
								<div class="mgl20"><small>小类:&nbsp;<b>' 
									+ cat2 + 
									 '</b></small>\
								</div>\
							</div>' +
							'<div class="ui-block-c">\
								<div class="custom_block2"><div class="ui-block-a"> <div class="mgt10 alignC">状态: <b>'
										+ rowObject.stateName +
										'</b></div></div>' +		
									'<div class="ui-block-b"><a href="Item.do?cmd=itemForm&itemId='
										+ rowObject.itemId +
										'" data-role="button" data-iconpos="left" data-mini="true" data-theme="a" data-icon="edit">修改</a>' +	
								'</div></div>' +
							'</div>' + 
						'</div>'	;
        return htmlStr;
/*		if (cellvalue != "")
			return "<img src='/uploaded/useritem/" + cellvalue + "' width='35px' height='35px' />";
		else
			return "<img src='/uploaded/useritem/noImage_140x140.gif' width='35px' height='35px' />";*/
	}
	
	CommonGrid.init('grid', {
	    url: 'Item.do?cmd=itemGridAjax',
	    postData: $('#itemSearchForm').serializeJSON(),
	    colNames:['商品目录'],
	    colModel:[
	      {name:'itemImg',		sortable:false,	width:150, 	align:'left',	formatter:funcImg	}
	    ],
	    sortname: 'update_date',
	    sortorder: 'desc',
	    rowNum :  gridPager.rowNum,
		page :  gridPager.page,
	    pager: jQuery('#gridpager')
	});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		page:1, 
		postData: $('#itemSearchForm').serializeJSON()
	}).trigger('reloadGrid');
}