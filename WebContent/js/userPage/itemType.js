var searchForm = null;
var itemNameCol = null;
var itemUnitCol = null;
var itemUnitCol = null;
var itemPackage = null;
var cookieId = null;

var iasOptions = {
	container: '#items-wrap',
    scrollContainer: $(window),
    item: '.store-item-wrap',
    pagination: '#pagination',
    next: '.next',
    noneleft: false,
    loader: '<img src="/js/lib/plugins/ias/images/loader.gif"/>',
    loaderDelay: 600,
    triggerPageTreshold: 5,
    trigger: '<span class="btn btn-primary">加载更多信息</span>',
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
	
	$("ul.store-menu a.cat-item").click(function(e){
		e.preventDefault();
		if ( $(this).hasClass("submenu") ) {
			if ($(e.target).hasClass("fa")) return;
		}
		
		var prevObj = $(this).closest("ul").prev("a");
		var cat = $(this).attr("catName");
		
		// parent exists.
		if (prevObj.length > 0) {
			var pCat = prevObj.attr("catName");
			$("#category").val(pCat);
			$("#category2").val(cat);
		} else {
			$("#category").val(cat);
			$("#category2").val("");
		}
		
		reloadPage();
	});

	$(".btnReset").click(function(e){
		e.preventDefault();
		
		var formObj = $(this).parents("form:first");
		formObj.find("input[type=text]").val("");
		$("#category").val("");
		$("#category2").val("");
		
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
	
});

function handleResizeForCat()
{
	if ($("ul.store-menu").length > 0) {
		var height = KptApi.getViewPort().height;
		height -= 125;
		$("ul.store-menu").css({height: height + "px"});
	}
}

function arrangeCatList()
{
	var cat = $("#category").val();
	var cat2 = $("#category2").val();
	
	$("a.cat-item").removeClass("active");
	$("a.cat-item.sumbmenu").parent().removeClass("open");
	
	$("a.cat-item").each(function (ind, obj) {
		var catName = $(obj).attr("catName");
		
		// if parent cat?
		if (catName == cat) {
			$(obj).parent().addClass("open");
			var hasChild = false;
			
			$(obj).parent().find("ul li a.cat-item").each(function(ind, c){
				var catName = $(c).attr("catName");
				if (catName == cat2) {
					var temp = $(c).parent().parents("li");
					
					if (temp.length > 0) {
						temp.addClass("open");
					}
					$(c).addClass("active");
					hasChild = true;
				}
			});
			
			if (hasChild == false) {
				$(obj).addClass("active");
			}
		}
		
		if (catName == cat2) {
			if ( ! $(obj).hasClass("submenu")) {
				$(obj).addClass("active");
			}
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
			arrangeCatList();
			
			$("#items-wrap").html("");
			
			var obj = $(data);
			
			$("#items-wrap").html(obj.find("#items-wrap").html());
			$("#pagination").html(obj.find("#pagination").html());
			
			$('.ias_trigger').hide();
			
			$.ias(iasOptions);
			
		}
	});
}
