
$(document).ready(function() {
	calculateSum();
	
	// bottom user menu active.
	$("#mCart").addClass("ui-btn-active");
});

function checkBill()
{
	if ( document.checkFrm.totalSum.value == "0" ) {
		KptApi.showError("商品合计金额为零，不能提交订单");
		return;
	}

	//$("#checkFrm").serializeObject();
	document.checkFrm.submit();
}


//obj: qty html object.
function s_qtyChanged(obj, itemId, hostUserId)
{
	return qtyChanged(obj, itemId, hostUserId);
}

function s_qtyControl(obj, itemId, dir)
{
	return qtyControl(obj, itemId, dir);
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
		document.getElementById("priceSum" + document.getElementsByName("userId")[i].value + document.getElementsByName("itemId")[i].value).innerHTML = sumDisp;
		document.getElementsByName("productPrice")[i].value = sumDisp;		
		
		if(listCheck[i].checked)
		{
			totalSum = Math.round((parseFloat(totalSum) + sum)*100)/100; // Math.round(x*100)/100 : For toFixed(2)
		}
	}
	document.getElementById("totalSum").innerHTML = totalSum.toString();
	document.checkFrm.totalSum.value = totalSum.toString();
}
