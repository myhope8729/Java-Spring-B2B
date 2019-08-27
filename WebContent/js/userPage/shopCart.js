
$(document).ready(function() {
	calculateSum();
});

function checkBill()
{
	if ( document.checkFrm.totalSum.value == "0" ) {
		KptApi.showError("商品合计金额为零，不能提交订单");
		return;
	}

	// remove the unchecked items now
	
	
	
	//$("#checkFrm").serializeObject();
	document.checkFrm.submit();
}


//obj: qty html object.
function s_qtyChanged(obj, itemId, oldQty)
{
	var qty = $(obj).val();
	
	if ( !isNumeric(qty) || qty < 0 ) {
		KptApi.showError(messages.number_only);
		$(obj).focus();
		return;
	}  
	
	qty = parseFloat(qty);
	
	if (qty >= 0) 
	{
		var cookieId = getCookie(ckEosCart);
		
		$.mvcAjax({
	        url: 'UserPage.do?cmd=saveShopCartAjax',
	        type: 'POST',
	        data: {'cookieId' : cookieId, 'hostUserId' : hostUserId, 'itemId' : itemId, 'qty' : qty },
	        dataType: 'json',
	        success: function (data) {
	        	if (data.result.resultCode == result.FAIL)
				{
					KptApi.showError(messages.add_to_cart_failed);
				}
				else
				{
					KptApi.showMsg(data.result.resultMsg);
				}
	        	
	        	//update the qtyTotal
	        	if (data.shopCartNum == "0") {
	        		$("#shopCart").html("");
	        	} else {
	        		$("#shopCart").html("(" +  data.shopCartNum + ")");
	        	}
	        	
	        	calculateSum();
	        }
	    });
	}
}

function s_qtyControl(obj, itemId, dir)
{
	var qtyObj = $("#qty_" + itemId);
	var oldQty = qtyObj.val();
	var qty = 0;
	if ( !isNumeric(oldQty) ) {
		oldQty = 0;
	}
	oldQty = parseFloat(oldQty);
	qty = oldQty + dir;
	
	if (qty < 0) {qty = 0;}
	qtyObj.val(qty);
	
	s_qtyChanged(qtyObj[0], itemId);
}

function line_changed()
{
	calculateSum();
}

function calculateSum()
{
	var count = parseInt(document.getElementById("itemCount").value);
	var sum = 0;
	var totalSum = 0;
	var listCheck = document.getElementsByName("lineCheck");
	
	for ( i = 0; i < count; i++ )
	{
		sum = Math.round(parseInt(document.getElementsByName("qty")[i].value) * parseFloat(document.getElementsByName("price")[i].value) * 100)/100; // Math.round(x*100)/100 : For toFixed(2)
		if (isNaN(sum)) {
			sum = 0.00;
		}
		var sumDisp = (sum > 0)? sum.toString() + "元" : ""; 
		document.getElementById("priceSum" + document.getElementsByName("itemId")[i].value).innerHTML = sumDisp;
		document.getElementsByName("productPrice")[i].value = sumDisp;		
		
		if(listCheck[i].checked)
		{
			totalSum = Math.round((parseFloat(totalSum) + sum)*100)/100; // Math.round(x*100)/100 : For toFixed(2)
		}
	}
	document.getElementById("totalSum").innerHTML = totalSum.toString();
	document.checkFrm.totalSum.value = totalSum.toString();
}
