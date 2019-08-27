var searchForm = null;
var itemNameCol = null;
var itemUnitCol = null;
var itemUnitCol = null;
var itemPackage = null;
var cookieId = null;

var iasOptions = {
	container: '#items-wrap',
    scrollContainer: $(".p-right"),
    item: '.store-item-wrap',
    pagination: '.pagination',
    next: '.next',
    noneleft: false,
    loader: '<img src="/js/lib/plugins/ias/images/loader.gif"/>',
    loaderDelay: 600,
    triggerPageTreshold: 1,
    trigger: '<button type="button" class="ui-btn ui-mini ui-btn-inline ui-btn-b ui-corner-all ui-btn-icon-right ui-icon-arrow-d">加载更多信息</button>',
    tresholdMargin: 0,
    history : false,
    
    // Hacked by LIKI. Form Id to post extra form's data.
    formId : "#productCategoryFrm",
    customLoaderProc: function(loader){
        $("#items-wrap").after(loader);
        loader.fadeIn();   
    	return true;
    },
    onLoadItems: function (data) {
    	return true;
    }
};

$(document).ready(function(){
	searchForm = $("#productCategoryFrm");
	
	$.ias(iasOptions);
	
	$("#cat-wrap-upper ul li").click(function(e){
		var pCat = $(this).attr("catName");
		$("#category").val(pCat);
		
		$(this).parent().find("li").removeClass("active");
		$(this).addClass("active");
		
		// getting sub menu here.
		replaceCatChildren(true);
		
		reloadPage();
	});
	
	$(document).on("click", "#cat-wrap ul li", function(e){
		var pCat = $(this).attr("catName");
		$("#category2").val(pCat);
		$(this).parent().find("li").removeClass("active");
		$(this).addClass("active");
		
		reloadPage();
	});
	
	$(".btnReset").click(function(e){
		e.preventDefault();
		
		var formObj = $(this).parents("form:first");
		formObj.find("input[type=text]").val("");
		
		reloadPage();
	});
	
	$("#btnSearch").click(function(e){
		e.preventDefault();
		reloadPage();
	});
	
	$(document).on("keyup", ".qty", function(e){
		var qty  = $(this).val();
		
		if (!isNumeric(qty) ) {
			KptApi.showError(messages.number_only);
			e.preventDefault();
			return;
		}
	});
	
	arrangeCatList();
	
	handleResizeForCat();
	
	KptApi.addResizeHandler(handleResizeForCat);
	
	// setting the shopping cart.
	var cookieId = $("#" + ckEosCart).val();
	setCookie(ckEosCart, cookieId, 3);
	
	// bottom user menu active.
	$("#mItems").addClass("ui-btn-active");
	
	$(".footer_wrapper").remove();
});

function handleResizeForCat()
{
	var height = KptApi.getViewPort().height;
	var offset = $("#cat-wrap-upper").outerHeight(true);
	//console.log(offset);
	offset += $(".title-bg").outerHeight(true);
	//console.log(offset);
	offset += $("#footer").outerHeight(true);
	//console.log(offset);
	height -= offset + 0;
	//console.log(height + " : " + offset);
	$(".p-right").css({height: height + "px"});
	$(".p-left").css({height: height + "px"});
}

function replaceCatChildren(isReselect)
{
	var cat = $("#category").val();
	var newCat = null;
	//$(".p-left").css("overflow", "hidden");
	$("#cat-wrap ul").html("");
	for (var i=0; i < catList.length; i++) {
		var catObj = catList[i];
		if (catObj.parentCatName == cat || (catObj.parentCatName == "" && cat == "-1")) {
			var liObj = $("<li />");
			liObj.attr("catName", catObj.catName == ""? "-1" : catObj.catName );
			liObj.html("<a href='javascript:void(0)'>" + (catObj.catName == ""? "未分类" : catObj.catName) /*+ " ("+catObj.cnt+")"*/ + "</a>");
			
			if (isReselect) {
				if (newCat == null) {
					newCat = catObj.catName;
					liObj.addClass("active");
				}
			}
			
			$("#cat-wrap ul").append(liObj);
		}
	}
	//$(".p-left").css("overflow", "hidden");
	
	if (isReselect) {
		$("#category2").val(newCat==null? "-1" : newCat);
	}
}

function arrangeCatList()
{
	var cat = $("#category").val();
	var cat2 = $("#category2").val();
	
	$("#cat-wrap-upper ul li").removeClass("active");
	$("#cat-wrap-upper ul li").each(function(ind, obj){
		if ($(obj).attr("catName") == cat) {
			$(obj).addClass("active");
		}
	});
	
	replaceCatChildren();
	
	$("#cat-wrap ul li").removeClass("active");
	$("#cat-wrap ul li").each(function(ind, obj){
		if ($(obj).attr("catName") == cat2) {
			$(obj).addClass("active");
		}
	});
}

function reloadPage()
{
	$.mvcAjax({
		url 	: searchForm.attr("action"),
		data 	: searchForm.serialize(),
		dataType: 'html',
		type: 'post',
		success : function(data, ts)
		{
			$("#items-wrap").html("");
			
			var obj = $(data);
			
			$("#items-wrap").html(obj.find("#items-wrap").html());
			$("#pagination").html(obj.find("#pagination").html());
			
			$.ias(iasOptions);
			
			// footer offset reset?
			handleResize();
		}
	});
}
