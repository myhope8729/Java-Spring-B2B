jQuery(document).ready(function(){
	// paytype list change event.

	$("#paytypeId").change(function(e){
		e.preventDefault();
		setHbMark();
	});
	
	
	$("#btnSubmit").click(function(){
		$("#action").val("submit");
		billOk();
	});
	
	setHbMark();
	
	
});

function setHbMark()
{
	var selected = $("#paytypeId").find("option[value='"+$("#paytypeId").val()+"']");
	if (selected.length > 0) 
	{
		if (selected.attr("prePayYn") == "Y") {
			$("#paymentTypeWrap").show();
			$("#hbmark").val("Y");
			
			$.mvcAjax({
				url 	: "Order.do?cmd=loadSubpaymentList",
				data 	: {userId: hostUserId, custtypeId: custtypeId, paytypeId: $("#paytypeId").val()},
				dataType: 'json',
				success : function(data, ts)
				{
					if (data.result.resultCode == result.FAIL)
					{
						KptApi.showError(data.result.resultMsg);
					}
					else
					{
						setSubPayType(data.subPayTypeList);
					}
				}
			});
		} else {
			$("#paymentTypeWrap").hide();
			$("#hbmark").val("N");
			
			setSubPayType([]);
		}
	} else {
		$("#paymentTypeWrap").hide();
		$("#hbmark").val("N");
		
		setSubPayType([]);
	}
}

function billOk()
{
	if ($("#paytypeId").length > 0) {
		if ($("#paytypeId").val().length < 1) {
			KptApi.showInfo("请选择付款方式");
			$("#paytypeId").focus();
			return;
		}
	}
	
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
	
	var paymentType = "";
	
	if ($("#paymentType").length > 0) {
		if ($("#hbmark").val() == "Y" && $("#paymentType").val().length < 1) {
			KptApi.showWarning("请先选择预付款类别");
			KptApi.scrollTo($("#paymentType"), -10);
			$("#paymentType").focus();
			return;
		}
	}
	
	var jsonArray = new Array();
	var jsonObject = new Object();
	
	var count = parseInt(document.getElementById("itemCount").value);
	for ( i = 0; i < count; i++ )
	{
		var itemId = document.getElementsByName("itemId")[i].value;
		var qty = document.getElementsByName("qty")[i].value;
		//var item_note = document.getElementsByName("item_note")[i].value;
		var item_note = "";
		var priceField = document.getElementsByName("priceField")[i].value;
		var priceValue = document.getElementsByName("priceValue")[i].value;
		
		var jsonObj = {};
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
	
	$.mvcAjax({
		url 	: formObj.attr("action"),
		data 	: {itemsStr : jsonData, 
			hostUserId : hostUserId, 
			addrId : addrId, 
			paytypeId : $("#paytypeId").val(), 
			paymentType : ($("#paymentType").length > 0)? $("#paymentType").val() : "", 
			arriveDate : $("#arriveDate").val(), 
			note : $("#note").val(), 
			action : "submit", 
			cookieId: cookieId },
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



function setSubPayType(subPayTypeList)
{
	$("#paymentType").html("");
	if (subPayTypeList.length > 0)
	{
		var html = "<option value='' selected='selected'>==请选择预付款类别==</option>";
		for (i=0;i < subPayTypeList.length; i++) 
		{
			var d = subPayTypeList[i];
			html += "<option value='" + d.name + "'>" + d.name + "</option>";
		}
		
		$("#paymentType").html(html);
		$("#paymentType").val($("#paymentTypeName").val());
	} else {
		var html = "<option value='' selected='selected'>==请选择预付款类别==</option>";
		$("#paymentType").html(html);
		
		$("#paymentTypeName").val("");
	}
}
