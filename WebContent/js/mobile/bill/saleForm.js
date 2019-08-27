var searchForm = null;
var gridObj = null;
var cartGridObj = null;
var addrGridObj = null;
var lastSel = 0;
var itemPriceCol = null;
var step = 1;
var cartTotal = 0.0;
var maxCol = 6;

jQuery(document).ready(function(){
	//updateCategorySelection();
	
	initItemGrid();
	initCartGrid();
	
	initAddrGrid();
	
	$("#btnSearch").click(function(){
		gridObj.jqGrid('setGridParam',{
			postData: searchForm.serialize()
		}).trigger('reloadGrid');
	});
	
	setPage();
	
	$("#btnPrev").click(function(e){
		e.preventDefault();
		step = 1;
		setPage();
	});
	
	$("#btnNext").click(function(e){
		e.preventDefault();
		
		var reccount = cartGridObj.jqGrid("getGridParam", "reccount");
		if (reccount < 1) {
			KptApi.showInfo("Please add an item at least.");
			KptApi.scrollTo($("#itemGrid"), -10);
			return;
		}
		step = 2;
		setPage();
	});
	
	$(document).on('change', 'input[name=qty]', changeColValue);
	$(document).on('change', 'input[name=item_note]', function(e){
		var obj = $(e.target);
		var rowId = obj.attr("rowid");
		var val = obj.val();
		row = cartGridObj.getRowData(rowId, true);
		row.item_note = val;
	});
	
	// user Item category
	$(document).on('click', 'div.cat-menu-item', function(e) {
		var catName =  $(this).attr("cid");
		
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		if ($(this).parents("div#cat2").length > 0)
		{
			$("#category2").val(catName);
		}
		if ($(this).parents("div#cat1").length > 0)
		{
			$("#category").val(catName);
			$("#category2").val("");
		}
		$("#btnSearch").click();
	});
	
	// itype list change event. Not used.
	/*if ($("#itype").length >0 )
	{
		$("#itype").change(function(e){
			e.preventDefault();
			
			var itypeVal = $("#itype").val();
			if (itypeVal.length < 1 ) return;
			updateCategorySelection();
			$("#btnSearch").click();
		});
	}*/
	
	// paytype list change event.
	$("#paytypeId").change(function(e){
		e.preventDefault();
		setHbMark();
	});
	
	
	$("#paymentType").change(function(e){
		e.preventDefault();
		if ($("#paymentType").val().length < 1 ) return;
		
		$("#btnSearch").click();
	});
	
	
	$("#btnSave").click(function(){
		$("#action").val("save");
		saveSale();
	});
	$("#btnSubmit").click(function(){
		$("#action").val("submit");
		saveSale();
	});
	
	initSelection();
});


function initSelection()
{
	setHbMark();
	
	arrangeCats(1);
}

function updateCategorySelection()
{
	/*var itypeVal = $("#itype").val();
	var catVal = "";
	if (itypeVal.length > 0) {
		catVal = $("#itype").find("option[value="+itypeVal+"]").text();
	}
	$("#category").val( catVal );
	$(".cat-menu-item").removeClass("active");
	$(".cat-menu-item[cid="+catVal+"]").addClass("active");*/
}

function setHbMark()
{
	var selected = $("#paytypeId").find("option[value='"+$("#paytypeId").val()+"']");
	if (selected.length > 0) 
	{
		if (selected.attr("prePayYn") == "Y") {
			$("#paymentTypeWrap").show();
			$("#hbmark").val("Y");
		} else {
			$("#paymentTypeWrap").hide();
			$("#hbmark").val("N");
		}
	} else {
		$("#paymentTypeWrap").hide();
		$("#hbmark").val("N");
	}
}

function initItemGrid()
{
    gridObj = $("#itemGrid");
	var ajaxUrl = gridObj.attr("ajaxUrl");
	searchForm = $( gridObj.attr("searchForm") );
	
	// setting col model in jqgrid
	itemPriceCol = hcModel.priceColName;
	
	for (i=0; i<itemGridData.colModel.length; i++)
	{
		if (itemGridData.colModel[i].name == itemPriceCol){
			itemGridData.colModel[i].formatter = funcPriceFormatter;
			break;
		}
	}
	
	itemGridData.colNames.push('item_id');
	itemGridData.colModel.push({name:'itemId', key:true, hidden: true});
	
	itemGridData.colNames.push('js_yn');
	itemGridData.colModel.push({name:'jsYn', hidden: true});
	
	itemGridData.colNames.push('custPrice');
	itemGridData.colModel.push({name:'custPrice', hidden: true});
	
	itemGridData.colNames.push('jsYn');
	itemGridData.colModel.push({name:'jsYn', hidden: true});
	
	itemGridData.colNames.push("图片");
	itemGridData.colModel.push( {name: "itemImgPath", index: "item_img_path", align: "center", formatter: funcImg} );
	
	
	gridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		url: gridObj.attr("ajaxUrl"),
		datatype: "json",
		keyName : "itemId",
		postData: searchForm.serialize(),
		//colNames: $.extend(true, itemGridData.colNames, ['item_id'] ),
		//colModel: $.extend(true, itemGridData.colModel, [{name:'itemId', key:true, hidden: true}]),
		colNames: itemGridData.colNames,
		colModel: itemGridData.colModel,
		pager: '#itemGridPager',
		sortname: gridPager.sortname || itemGridData.colModel[0].name,
		radioOption:true,
	    onSelectRow: function(id){
	    	// validation check
	    	if ( $("#hbmark").val() == "Y" && $("#paymentType").val().length < 1) 
	    	{
				KptApi.showWarning("请先选择预付款类别");
				KptApi.scrollTo($("#hbmark"), -10);
				$("#hbmark").focus();
				
				return;
			}
	    	
			if (id) {
				// check the existence
				var row = gridObj.getRowData(id, true);
				
				var rowTemp = cartGridObj.getRowData(id);
				
				if (rowTemp.itemId != undefined) {
					KptApi.showWarning("该商品已经录入过了");
				} else {
					// validation check
					eval("var price = row." + itemPriceCol);
					
					price = row.custPrice || price;
					if (price == undefined || !isNumeric(price)) {
						price = row.custPrice;
					}
					if (price == undefined || !isNumeric(price)) {
						KptApi.showWarning("该商品没有设置价格，不能订货，请联系供货方");
						return;
					}
					eval("row." + itemPriceCol + "=" + price);
					
					row.qty = 1;
					row.item_note = '';
					row.state = '';
					cartGridObj.addRowData(id, row);
					calcTotalAmt(id, row);
					cartGridObj.setSelection(id);
				}
				setPage();
			}
		},
		loadCompleteCB: function(data) 
		{
			setCats(data);
			setCats2(data);
			setSubPayType(data);
			setPrepayInfo(data);
		},
		rownumbers:true
	}));
	gridObj.jqGrid('navGrid','#itemGridPager',{edit:false,add:false,del:false,search:false, position:'right'});
}  

function initCartGrid()
{

	cartGridData.colNames.push('item_id');
	cartGridData.colNames.push('custPrice');
	cartGridData.colNames.push("数量");
	if (itemJsCol != '') {
		cartGridData.colNames.push("件数量");
		cartGridData.colNames.push("jsQty");
	}
	cartGridData.colNames.push("金额(元)");
	cartGridData.colNames.push("备注");
	cartGridData.colNames.push("操作");
	
	cartGridData.colModel.push({name:'itemId', key:true, hidden: true});
	cartGridData.colModel.push({name:'custPrice', hidden: true});
	cartGridData.colModel.push( {
		name: "qty", index: "qty", align : "right", 
		editable:true
	});
	if (itemJsCol != '') {
		
		cartGridData.colModel.push( {name: "js_display", index: "js_display", align : "center", formatter: function(cellvalue, options, rowObj){
			return cellvalue;
		}} );
		cartGridData.colModel.push( {name: "js_qty", hidden: true} );
	}
	cartGridData.colModel.push( {name: "total_amt", index: "total_amt", align : "right"} );
	cartGridData.colModel.push( {name: "item_note", index: "item_note", editable:true} );
	cartGridData.colModel.push( {name: "state", index: "state", align: "center", formatter: controlFunc} );
	
	cartGridObj = $("#cartGrid");
	cartGridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype: "local",
		data: cartGridData.data,
		postData: {},
		colNames: cartGridData.colNames,
		colModel: cartGridData.colModel,
		
		//sortname: 'bill_id',	    
		//pager: false,
		rowNum: 10000,
		radioOption:true,
	    onSelectRow: function(id){
	    	if (id) {
				cartGridObj.jqGrid('editRow', id, {keys:false});
			}
		},
		loadCompleteCB : function(data) {
			if (data.rows != undefined && data.rows.length > 0) 
			{
				for (i=data.rows.length-1; i >= 0; i--) {
					cartGridObj.jqGrid('editRow', data.rows[i].itemId, {keys:false});
				}	
			}
			calcTotalAmtAll(cartGridObj);
		},
		rownumbers:true
	}));
}

function initAddrGrid()
{
	addrGridObj = $("#addrGrid");
	addrGridObj.jqGrid($.extend({}, CommonGrid.defaultOption, {
		datatype: "local",
		data: addrList || [],
		postData: {},
		colNames: ["选中", "所在地", "收货地址", "收货人", "收货人联系电话", "说明"],
		colModel: [
            {name:'addr_id',  index:'addr_id',	 width: 50,	sortable:false,	align:'center', formatter:function(cellvalue, options, rowObj) {
            	var html = "<input type='radio' name='addrId' id='addrId_" + rowObj.addrId + "' value='"+ rowObj.addrId +"' " + (rowObj.defaultYn=="Y"? "checked='checked'" : "") + " />";
            	return html;
    	    }}, 
            {name:'locationName',  index:'locationName',		sortable:false,	align:'left'},
            {name:'address',  index:'address', width: 220,	sortable:false,	align:'left'},
            {name:'contactName',  index:'contactName',	sortable:false,	align:'left'},
            {name:'telNo',  index:'tel_no'},
            {name:'note',  index:'note'}
		],
		pager: '#addrGridpager',
		radioOption:true,
		onSelectRow: function(id) {
			if (id) {
				/*var row = gridObj.getRowData(id);
				lastSel = id;
				addrGridObj.jqGrid('editRow', id, true);*/
			}
		},
		rownumbers:false
	}));
}

function controlFunc(cellvalue, options, rowObject) 
{
	return '<a href="javascript: deleteCartItem(\''+options.rowId+'\')">删除</a>';
} 

function deleteCartItem(rowId)
{
	cartGridObj.delRowData(rowId);
	setPage();
}

/**
 * @param rowId
 * @param row
 * @returns
 */
function calcTotalAmt(rowId, row)
{
	if (row == undefined)
	{
		row = cartGridObj.getRowData(rowId, true);
	}
	
	if (row.itemId != undefined) {
		eval("var price = row." + itemPriceCol);
		
		if (!isNumeric(price)) {
			price = 0;
		}
		
		row.total_amt = row.qty * price;
		if (row.total_amt == 0 || row.total_amt == NaN) {
			row.total_amt = "";
		} else {
			row.total_amt = round(row.total_amt, 3);
		}
		
		cartGridObj.setCell(row.itemId, "total_amt", row.total_amt);
		
		// set the js display : js_display
		if ( itemJsCol != '' ) {
			eval("var cntInPackage = row." + itemJsCol + " || 0;");
			var jsDisplay = ' ';
			var jsQty = row.qty;
			if (cntInPackage != 0) {
				var ret = getJsDisplay(row.qty, cntInPackage, true);
				jsDisplay = ret.existPackage? ret.jsDisplay : " ";
				jsQty = ret.qtyNew;
				if ( isJsMark && ! ret.matched) {
					KptApi.showInfo(messages.wrongJsQty);
				}
			}
			cartGridObj.setCell(row.itemId, "js_display", jsDisplay);
			cartGridObj.setCell(row.itemId, "js_qty", jsQty);
		}
	}
	
	calcTotalAmtAll(cartGridObj);
	
	return row;
}

function calcTotalAmtAll(cartGrid)
{
	var items = cartGrid.jqGrid("getGridParam", "data");
	cartTotal = 0.0;
	for (var i=0; i<items.length; i++)
	{
		var t = items[i].total_amt;
		t = parseFloat(t);
		if (t == undefined)
			t = 0.0;
		cartTotal += t;
	}
	$("#cartTotal").html( round(cartTotal,3) );
}

function changeColValue(e)
{
	var obj = $(e.target);
	var rowId = obj.attr("rowid");
	var val = obj.val();
	
	// validation need in val.
	if (! isNumeric(val)) {
		KptApi.showError("Please input the correct number.");
		obj.focus();
		return;
	}
	
	row = cartGridObj.getRowData(rowId, true);
	row.qty = val;
	calcTotalAmt(rowId);
	
}

function setPage()
{
	var reccount = cartGridObj.jqGrid("getGridParam", "reccount");
	
	if (step == 1) {
		$("#btnPrev").attr("disabled", true);
		$("#btnNext").attr("disabled", false);
		
		$("#addrList").hide();
		$("#itemList").show();
	} else {
		$("#btnPrev").attr("disabled", false);
		$("#btnNext").attr("disabled", true);
		$("#addrList").show();
		$("#itemList").hide();
	}
	
	if (reccount < 1) {
		$("#btnPrev").attr("disabled", true);
		$("#btnNext").attr("disabled", true);
		$("#btnSave").attr("disabled", true);
		$("#btnSubmit").attr("disabled", true);
	} else {
		$("#btnSave").attr("disabled", false);
		$("#btnSubmit").attr("disabled", false);
	}
}
function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}
/**
 * Save the sale.
 */
function saveSale()
{
	var reccount = cartGridObj.jqGrid("getGridParam", "reccount");
	var action = $("#action").val();
	
	if (reccount < 1) {
		page = 1;
		setPage();
		KptApi.showInfo("Please add an item at least.");
		KptApi.scrollTo($("#itemGrid"), -10);
		return;
	}
	
	if ($("#itype").length > 0) {
		if ($("#itype").val().length < 1) {
			KptApi.showInfo("请选择商品类别");
			$("#itype").focus();
			return;
		}
	}
	
	if ($("#paytypeId").length > 0) {
		if ($("#paytypeId").val().length < 1) {
			KptApi.showInfo("请选择付款方式");
			$("#paytypeId").focus();
			return;
		}
	}
	
	var addrObj = $("input[name=addrId]");
	if (addrObj.length > 0) {
		var sel = $("input[name=addrId]:checked");
		if (sel.length > 0 && sel.val() != '') {
		} else {
			KptApi.showInfo("请选择收货地址");
			page = 2;
			setPage();
			return;
		}
	}
	
	if ($("#arriveDate").parents("div.form-group:first").children("span.required").length > 0) {
		if ($("#arriveDate").val().length < 10)
		{
			KptApi.showInfo("请输入送货日期");
			page = 2;
			setPage();
			$("#arriveDate").focus();
			return;
		}
	}
	
	var formObj = $("#userItemsForm");
	
	//var postData = cartGridObj.
	var items = cartGridObj.jqGrid("getGridParam", "data");
	
	if ( isJsMark && itemJsCol != "") {
		for(i=0; i< items.length; i++) {
			var obj = items[i];
			// zero ?
			if (obj.qty <= 0)
			{
				KptApi.showError(messages.wrongQty);
				var jObj =  $("#" + obj.itemId + "_qty");
				jObj.focus();
				KptApi.scrollTo(jObj, -10);
				return;
			}
			
			// js display?
			eval("var cntInPackage = obj." + itemJsCol + " || 0;");
			if (cntInPackage != 0) {
				var ret = getJsDisplay(obj.qty, cntInPackage, true);
				if ( ! ret.matched) {
					KptApi.showError("只能输入整件数量!");
					var jObj =  $("#" + obj.itemId + "_qty");
					jObj.focus();
					KptApi.scrollTo(jObj, -10);
					return;
				}
			}
		}
	}
	
	var postData = {itemsStr: JSON.stringify(items)};
	formArray = formObj.serializeArray();
	if (formArray.length > 0) {
		for(i=0; i<formArray.length; i++) {
			eval("postData." + formArray[i].name + "= formArray[i].value ");
		}
	}
	
	postData.action = action;
	
	$.mvcAjax({
		url 	: formObj.attr("action"),
		data 	: postData,
		dataType: 'json',
		success : function(data, ts)
		{
			if (data.result.resultCode == result.FAIL)
			{
				KptApi.showMsg(data.result.resultMsg);
			}
			else
			{
				window.location.href = afterSavingUrl;
			}
		}
	});
}

function setCats(data)
{
	$("#cat1-wrapper").hide();
	$("#cat1").html("");
	
	if (data.itemType1List.length > 0)
	{
		for (i=0;i < data.itemType1List.length; i++) 
		{
			var catData = data.itemType1List[i];
			var catName = catData.catName || "";
			var row = '<div class="cat-menu-item';
			if ((catName == "") && data.sc.category == "-1"){
				row = row + ' active';
			}else if (catName != "" && data.sc.category == catName){
				row = row + ' active';
			}
			var cid = (catName == "" )? "-1" : catName;
			row = row + '" cid="'+ cid + '">';
			if (catName == ""){
				row = row + '未分类';
			}else{
				row = row + catName;
			}
			row = row + '(' + catData.cnt + ')</div>';
			$("#cat1").append(row);
		}
		
		$("#cat1-wrapper").show();
		arrangeCats(1);
	}
}

function setCats2(data)
{
	$("#cat2-wrapper").hide();
	$("#cat2").html("");
	if (data.itemType2List.length > 0)
	{
		for (i=0;i < data.itemType2List.length; i++) 
		{
			var cat2Data = data.itemType2List[i];
			var cat2Name = cat2Data.catName || "";
			var row = '<div class="cat-menu-item';
			if ((cat2Name == "") && data.sc.category2 == "-1"){
				row = row + ' active';
			}else if (cat2Name != "" && data.sc.category2 == cat2Name){
				row = row + ' active';
			}
			var cid = ( cat2Name == "")? "-1" : cat2Name;
			row = row + '" cid="'+ cid + '">';
			if (cat2Name == ""){
				row = row + '未分类';
			}else{
				row = row + cat2Name;
			}
			row = row + '(' + cat2Data.cnt + ')</div>';
			$("#cat2").append(row);
		}
		
		if (data.itemType2List.length == 1 && $("#cat2 > div[cid=-1]").length > 0) {
			$("#cat2").html("");
		} else {
			$("#cat2-wrapper").show();
			arrangeCats(2);
		}
	}
}
function arrangeCats(level)
{
	var mObj = $("#cat" + level);
	var len = mObj.children(".cat-menu-item").length;
	
	if (len > 0)
	{
		if (len == 5) {
			mObj.find(":nth-child(1)").addClass("col-lg-2");
			mObj.find(":nth-child(2)").addClass("col-lg-2");
			mObj.find(":nth-child(3)").addClass("col-lg-2");
			mObj.find(":nth-child(4)").addClass("col-lg-3");
			mObj.find(":nth-child(5)").addClass("col-lg-3");
			mObj.children().addClass("last-row");
		} else {
			var ret = getColNumsPerRow(len);
			var colWid = ret[0];
			if (colWid == 1) {
				mObj.addClass("l-height");
			}
			colCnt = 12 / colWid;
			mObj.children().addClass("col-lg-" + colWid);
			mObj.children(":nth-child("+colCnt+"n+1)").addClass("first-col");
			
			var lastColCnt = len % colCnt;
			if ( lastColCnt == 0 ) 
				lastColCnt = colCnt;
			lastColCnt = len-lastColCnt-1;
			
			if (lastColCnt < 0) {
				mObj.children().addClass("last-row");
			} else {
				mObj.children(':gt('+lastColCnt+')').addClass("last-row");
			}
			if (len % colCnt != 0)
			{
				mObj.children(":last").addClass("last");
			}
		} 
		
	}
}
function getColNumsPerRow(n)
{
	var rows = Math.ceil(n / maxCol);
	if (rows > 2) 
	{
		return [1, 12]; 
	}
	if (rows == 1) 
	{
		if (n == 5) {
			return [2, 3];
		} else {
			return [Math.ceil(12/n), 0];
		}
	}
	return [2, 6];
}


function setSubPayType(data)
{
	$("#paymentType").html("");
	if (data.subPayTypeList.length > 0)
	{
		var html = "<option value='' selected='selected'>==请选择预付款类别==</option>";
		for (i=0;i < data.subPayTypeList.length; i++) 
		{
			var d = data.subPayTypeList[i];
			html += "<option value='" + d.paytype2 + "'>" + d.paytype2 + "</option>";
		}
		$("#paymentType").html(html);
		$("#paymentType").val(data.sc.paymentType);
	} else {
		var html = "<option value='' selected='selected'>==请选择预付款类别==</option>";
		$("#paymentType").html(html);
	}
}

function setPrepayInfo(data)
{
	$("#cust-info-wrap").hide();
	$("#prepay-wrap").html("");
	if (data.ppList.length > 0)
	{
		var html = "";
		for (i=0;i < data.ppList.length; i++) 
		{
			var d = data.ppList[i];
			html += "<span class='sub-title'>"+d.payType2 + "</span> : " + d.totalAmt;
			html += "&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		$("#prepay-wrap").html(html);
		$("#cust-info-wrap").show();
	}
}

function funcPriceFormatter(cellvalue, options, rowObject)
{
	if (isNumeric(rowObject.custPrice))
	{
		//return rowObject.custPrice;
		return cellvalue + ", " + rowObject.custPrice;
	}
	if (!isNumeric(cellvalue)) return "";
	return cellvalue;
}

function paymentBillList(url)
{
	var brand = $("#brand").val();
	if (brand.length > 0)
	{
		url += "&brand=" + brand;
	}
	window.location.href = url;
}