$(document).ready(function(){
	function colFormatter(cellvalue, options, rowObject){
		if (rowObject.sysType == 'VT0002'){
			var str = "<select class='form-control' name='sysValue' onchange=''>\n";
			str += "<option value=''" + (rowObject.sysValue==undefined?' selected>':" >") + selMsg + "</option>\n";
			
			$.each(obj.SysDetailData, function(i, detail) {
				if (detail.sysCode == rowObject.sysCode)
				{
					str += "<option value='" + detail.sysValue + "'" + (cellvalue==detail.sysValue?" selected":'') + ">" + detail.sysValueName + "</option>\n";
				}
			});
			str += '</select>';
			return str;
		}else{
			if (rowObject.sysCode == '0420' || rowObject.sysCode == '0502' || rowObject.sysCode == '0402'){
				return '<input class="form-control" name="sysValue" value="' + (cellvalue || '') + '" onchange="chageVal(this);"></input>';
			}else{
				return '<input class="form-control" name="sysValue" value="' + (cellvalue || '') + '"></input>';
			}
		}
	}
	
	CommonGrid.init('grid', {
	    url: 'BizSetting.do?cmd=bizSettingGridAjax',
	    postData: $('#bizSettingSearchForm').serialize(),
	    colNames:['编号', '业务类型', '配置项', '配置内容'],
	    colModel:[
	      {name:'sysCode',		sortable:true,	width:50, 	align:'center', index:'sysCode'					},
	      {name:'sysKindName',	sortable:true,	width:100,	align:'center', index:'sysKindName'				},
	      {name:'sysName', 		sortable:true,	width:300, 	align:'left',	index:'sysName'					},
	      {name:'sysValueName', sortable:false,	width:150, 	align:'left',	formatter:colFormatter			}
	    ],
	    pager: false,
	    sortname: 'sysKindCd, sysCode',
	    sortorder: 'asc',
		rowNum: -1
	});
});

function reloadGrid() {
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#bizSettingSearchForm').serialize()
	}).trigger('reloadGrid');
}

function saveBizSetting(){
	var arr = new Array();
	var valid = true;
	
	jQuery.each(jQuery("[name=sysValue]"), function(i, obj){
		var sysCode = jQuery('#grid').getCell(i+1, "sysCode");
		var sysType = jQuery('#grid').getCell(i+1, "sysType");
		var sysValue = $(obj).val();
		
		if (sysCode == '0420' || sysCode == '0502' || sysCode == '0402'){
			var checkedVal = sysValue;
			if (checkedVal == '') checkedVal = 1;
			if (!isNumeric(checkedVal)) {
				KptApi.showError(invalidNumber);
				$(obj).focus();
				valid = false;
				return false;
			}
		}
		arr[i] = {'sysCode': sysCode, 'sysValue': sysValue};
	});
	
	if (valid == true){
		jQuery('#userData').val(JSON.stringify(arr));
		jQuery("#bizSettingForm").submit();
	}
}

function chageVal(obj){
	var val = $(obj).val();
	if (val == '') val = 1;
	
	if (!isNumeric(val)) {
		KptApi.showError(invalidNumber);
		$(obj).focus();
	}
}