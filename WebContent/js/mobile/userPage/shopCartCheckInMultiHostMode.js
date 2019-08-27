jQuery(document).ready(function() {
	$("#btnSubmit").click(function(){
		$("#action").val("submit");
		billOk();
	});
	
	
});

function billOk()
{
	if ($("#addrId").val().length < 1 ) {
		KptApi.showInfo("请选择收货地址");
		return;
	}
	
	if ($("#arriveDate").closest("div.form-group").find("span.required").length > 0) {
		if ($("#arriveDate").val().length < 10)
		{
			KptApi.showInfo("请输入送货日期");
			$("#arriveDate").focus();
			return;
		}
	}
	
	var jsonArray = new Array();
	var jsonObject = new Object();
	
	var count = parseInt(document.getElementById("itemCount").value);
	for ( i = 0; i < count; i++ )
	{
		var userId = document.getElementsByName("userId")[i].value;
		var itemId = document.getElementsByName("itemId")[i].value;
		var qty = document.getElementsByName("qty")[i].value;
		//var item_note = document.getElementsByName("item_note")[i].value;
		var item_note = "";
		var priceField = document.getElementsByName("priceField")[i].value;
		var priceValue = document.getElementsByName("priceValue")[i].value;
		
		var jsonObj = {};
		jsonObj["userId"] = userId;
		jsonObj["itemId"] = itemId;
		jsonObj["qty"] = qty;
		jsonObj["item_note"] = item_note;
		jsonObj[priceField] = priceValue;
		
	    jsonArray.push(jsonObj);
	}
	
	var formObj = $("#billFrm");
	var hostUserId = $("#hostUserId").attr('value');
	var addrId = $("#addrId").attr('value');
	
	var jsonData = JSON.stringify(jsonArray);
	
	// setting the shopping cart.
	var cookieId = getCookie(ckEosCart);
	//setCookie(ckEosCart, "", 3);
	
	var postData = {
		multiHostMode : "1", 
		itemsStr : jsonData, 
		addrId : addrId, 
		arriveDate : $("#arriveDate").val(), 
		action : "submit", 
		cookieId: cookieId
	};
	
	for (i=0; i < document.getElementsByName("note").length; i++) {
		var hObj = document.getElementsByName("note")[i];
		postData["note_" + $(hObj).attr("userId")] = hObj.value;
	}
	
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
				setCookie(ckEosCart, "", 3);
				window.location.href = afterSavingUrl;
			}
		}
	});
}
