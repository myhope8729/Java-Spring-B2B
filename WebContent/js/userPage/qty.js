
// obj: qty html object.
function qtyChanged(obj, itemId, oldQty)
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
		
		$.ajax({
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
	        	
	        	//update the qtyTotals
	        	if (data.shopCartNum == "0") {
	        		$("#shopCart").html("");
	        	} else {
	        		$("#shopCart").html("(" +  data.shopCartNum + ")");
	        	}
	        }
	    });
	}
}

function qtyControl(obj, itemId, dir)
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
	
	qtyChanged(qtyObj[0], itemId);
}
