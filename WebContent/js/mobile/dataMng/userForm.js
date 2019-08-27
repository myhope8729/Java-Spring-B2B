var inputName = ""; 
var upperId = "";
$(document).ready(function(){
	// Setting the location List
	$("#locationSelector select").change(function(selId){
		inputName = $(this).attr("name");
		upperId = $(this).val();
		if (inputName == "provId" || inputName == "cityId") {
			$.mvcAjax({
				url 	: "Address.do?cmd=getChidren2Ajax",
				data 	: {upperId : upperId, inputName: inputName},
				dataType: 'json',
				type: 'POST',
				success : function(data, ts) {
					if (inputName == "provId") {
						if (data.childrenList.length > 0) {
							setItemsInLoctionList(data.childrenList, $("#cityId"));
							$("#cityIdWrap").removeClass("hidden");
							$("#cityId").selectmenu("refresh");
						} else {
							$("#cityIdWrap").addClass("hidden");
						}
						
						if (data.firstchildrenList.length>0) {
							setItemsInLoctionList(data.firstchildrenList, $("#areaId"));
							$("#areaIdWrap").removeClass("hidden");
							$("#areaId").selectmenu("refresh");
						} else {
							$("#areaIdWrap").addClass("hidden");
						}
					} else if(inputName == "cityId") { 
						if (data.childrenList.length > 0) {
							setItemsInLoctionList(data.childrenList, $("#areaId"));
							$("#areaIdWrap").removeClass("hidden");
							$("#areaId").selectmenu("refresh");
						} else {
							$("#areaIdWrap").addClass("hidden");
						}
					}
					
					$("#locationId").val(upperId);
					if (data.childrenList.length > 0) {
						$("#locationId").val(data.childrenList[0].addressId);
					}
					if (data.firstchildrenList.length > 0) {
						$("#locationId").val(data.firstchildrenList[0].addressId);
					} 
				}
			});
		} else {
			$("#locationId").val(upperId);
		}
	});
	$("#userForm").validate( $.extend(false, validateDefaultOption2, {
		submitHandler: function(form) {
			validateDefaultOption2.submitHandler(form);
		},
		onSuccess: function(data) {
			if ($("#userKindBk").val() != $('#userKind').val() ) {
				window.location.reload();
			} else {
				KptApi.showMsg(data.result.resultMsg);
			}
		},
		onFail: function(data) {
			KptApi.showError(data.result.resultMsg);
		}
	}));
	
	$('#btnSave').click(function(e){
		if ($("#userKindBk").val() != $('#userKind').val() ) {
			KptApi.confirm(messages.confirm, function(){
				$("#userForm").submit();
			});
			return;
		} else {
			$("#userForm").submit();
		}
	});
});
function setItemsInLoctionList(list, ele) {
	ele.html("");
	$.each(list, function(i, city){
		ele.append("<option value='" + city.addressId + "' data-level='" + city.addressLevel + "'>" + city.addressName + "</option>");
	});
	
	ele.selectmenu("refresh");
}