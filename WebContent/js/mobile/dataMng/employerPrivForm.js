$(document).ready(function(){
	$(".check-all").change(function(e){
		var checked = $(this).is(':checked');
        var obj = $(this).parents('table:first').find("tbody input[type=checkbox]");
        
        obj.each(function(){
        	var val = checked ^ $(this).is(':checked');
        	if (val) {
        		$(this).trigger("click");
        	}
        });
    });
	
	$("tbody tr.um input[type=checkbox]").change(function(e){
		var trObj = $(this).parents("tr:first");
		var checked = $(this).is(':checked');
		$("tbody tr.um"+trObj.attr("refId")+" input[type=checkbox]").each(function(){
			var val = checked ^ $(this).is(':checked');
			if (val) {
        		$(this).trigger("click");
        	}
		});
	});
	
	$("tbody tr.um-sub input[type=checkbox]").change(function(e){
		var trObj = $(this).closest("tr");
		var checked = $(this).is(':checked');
		
		var cls = trObj.attr("class");
		var checkedLen = $("tbody tr[class='" + cls + "'] input[type=checkbox]:checked").length;
		
		var pId = cls.replace(" um-sub", "");
		pId = pId.replace("um", "");
		
		var pObj = $("#menu_" + pId);
		if (checkedLen > 0) {
			pObj[0].checked = true;
		} else {
			pObj[0].checked = false;
		}
	});
});