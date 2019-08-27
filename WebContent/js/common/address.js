$(document).ready(function(){
	$(".locationList").change(function(selId){
		var level = $("option:selected", this).data("level");
		var isProvCombo = ($(this).attr("name") == "provId")?true:false;
		var thisId = $(this).val();
		$.mvcAjax({
			url 	: "Address.do?cmd=getChidrenAjax",
			data 	: {upperId : thisId, level : level},
			dataType: 'json',
			type: 'POST',
			success : function(data,ts) {
				if (data.isProvince == "true"){
					$("#cityId select").find("option").remove();
					$("#areaId").removeClass("hidden");
					$("#cityId").removeClass("hidden");
					$.each(data.cityList, function(i, city){
						$("#cityId select").append("<option value='" + city.addressId + "' data-level='" + city.addressLevel + "'>" + city.addressName + "</option>");
					});
					$("#locationId").find("option").remove();
					$("#locationId").removeClass("hidden");
					$.each(data.areaList, function(i, area){
						$("#locationId").append("<option value='" + area.addressId + "'>" + area.addressName + "</option>");
					});
				}else{
					$("#areaId").addClass("hidden");
					if (isProvCombo){
						$("#cityId").addClass("hidden");
					}	
					$("#locationId").find("option").remove();
					if (data.areaList.length > 0){
						$("#areaId").removeClass("hidden");
						$.each(data.areaList, function(i, area){
							$("#locationId").append("<option value='" + area.addressId + "'>" + area.addressName + "</option>");
						});
					}else{
						$("#locationId").append("<option value='" + thisId + "' selected></option>");
					}
				}
			}
		
		});
	});
});