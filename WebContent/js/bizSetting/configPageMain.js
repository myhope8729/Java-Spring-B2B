$(document).ready(function(){
	$(".add-row").click(function(){
		var newRowNum = parseInt($("#detail-setting .panel-body >div:last-child").data("row")) + 1;
		$("#detail-setting .panel-body").append(
			"<div class='row' data-row='" + newRowNum + "'>" +
				"<div class='well'>" +
					"<div class='row'>" +
						"<div class='row_header'>" +
							"<span class='ftR add-col'><i class='fa fa-plus-square'></i>&nbsp;添加分区</span>" +
							"<span class='ftR del-block'><i class='fa fa-minus-circle'></i>&nbsp;删除区域</span>" +
						"</div>" +
						"<div class='row_container'></div>" +
					"</div>" +
				"</div>" +
			"</div>");
	});
	$(".mobile-view").click(function(){
		var widthClass;
		if ($("#detail-setting .panel-body").hasClass('mobile')){
			$(this).html('<i class="fa fa-mobile"></i>&nbsp;手机端设置');
			widthClass = 'col-width';
		}else{
			$(this).html('<i class="fa fa-desktop"></i>&nbsp;PC端设置');
			widthClass = 'col-mobile';
		}
		$("#detail-setting .panel-body").toggleClass('mobile');
		$(".col").each(function(){
			$(this).css('width', $(this).find('.' + widthClass).eq(0).val() + '%');
		});
		resetClear();
	});
	var currentValue = 0;
	$(document).on('click', '.col-width, .col-mobile', function(e){
		var $this = $(e.target);
		$this.removeAttr('readonly');
		$this.focus();
		$this.select();
		currentValue = $this.val();
	});
	
	function setWidth(e)
	{
		var $this = $(e.target);
		var widthData = $this.hasClass("col-width")?"data-width":"data-mobile";
		var widthClass = $this.hasClass("col-width")?"col-width":"col-mobile";
		var block = $this.closest('.row');
		var temp = $this.closest('.col').attr(widthData);
		$this.closest('.col').attr(widthData, $this.val());
		var currentWidth = 0;
		var flag = true;
		block.find('.col[' + widthData + ']').each(function(){
			currentWidth += parseFloat($(this).attr(widthData));
		});
		if (($this.val() < 10 && widthData == 'data-width') || ($this.val() < 33.3 && widthData == 'data-mobile')){
			KptApi.showError('最小宽度是10%在PC端，33.3%在手机端。');
			flag = false;
		}
		if (currentWidth > 100 && widthData == 'data-width'){
			KptApi.showError('总宽度超出100%。');
			flag = false;
		}
		if (!flag)
		{
			if (!temp){
				$this.closest('.col').removeAttr(widthData);
			}else{
				$this.closest('.col').attr(widthData, temp);
			}
			$this.val(currentValue);
			$this.attr('readonly', 'readonly');
			return;
		}
		
		var rowNum = parseInt($this.closest('.col').data('row'));
		var colNum = parseInt($this.closest('.col').data('col'));
		var data = {rowId:rowNum, colId:colNum};
		if (widthData == 'data-width'){
			data.width = $this.val();
		}else{
			data.width_mob = $this.val();
		}
		$.mvcAjax({
			url 	: "ConfigPage.do?cmd=setPageDetailWidthAjax",
			data 	: data,
			dataType: 'json',
			success : function(data, ts)
			{
				$this.closest('.col').css('width', $this.val() + "%");
				$this.attr('readonly', 'readonly');
				resetClear();
			}
		});
	}
	$(document).on('blur', '.col-width, .col-mobile', function(e){
		setWidth(e);
	});
	$(document).on('keyup', '.col-width, .col-mobile', function(e){
		if (e.which == 13){
			$(e.target).blur();
		}
	});
	$(document).on('click', '.del-col', function(e){
		var $this = $(e.target);
		var block = $this.closest('.row');
		
		var widthData = $("#detail-setting .panel-body").hasClass('mobile')?"data-mobile":"data-width";
		var widthClass = $("#detail-setting .panel-body").hasClass('mobile')?"col-mobile":"col-width";
		
		var col = $this.closest('.col');
		var rowId = col.data('row');
		var colId = col.data('col');
		KptApi.confirm(messages.deleteMsg, function(){
			$.mvcAjax({
				url 	: "ConfigPage.do?cmd=deletePageDetailAjax",
				data 	: {rowId: rowId, colId: colId},
				dataType: 'json',
				success : function(data, ts)
				{
					col.remove();
					var flag = true;
					block.find('.add-col').removeClass('disabled');
					resetClear();
				}
			});
		});
	});
	$(document).on('click', '.add-col', function(e){
		var $this = $(e.target);
		if ($this.hasClass('disabled')){
			return;
		}
		
		var widthData = $("#detail-setting .panel-body").hasClass('mobile')?"data-mobile":"data-width";
		var widthClass = $("#detail-setting .panel-body").hasClass('mobile')?"col-mobile":"col-width";
		
		var block = $this.closest('.row');
		var rowNum = parseInt(block.parent().parent().data('row'));
		var colNum = parseInt(block.find('.row_container > div:last-child').data('col'));
		var newRowNum = isNaN(rowNum)?1:rowNum;
		var newColNum = isNaN(colNum)?1:colNum + 1;
		
		$.mvcAjax({
			url 	: "ConfigPage.do?cmd=addPageDetailAjax",
			data 	: {rowId: newRowNum, colId: newColNum, cellId: 1, width:'20', width_mob: '33.3'},
			dataType: 'json',
			success : function(data, ts)
			{
				var otherWidth;
				
				if (widthData == 'data-width'){
					otherWidth = 20;
				}else{
					otherWidth = 33.3;
				}
				
				$this.closest('.well').find('.row_container').append(
					"<div class='col' data-row='" + newRowNum + "' data-col='" + newColNum + "' data-width='20' data-mobile='33.3' style='width:" + otherWidth +"%'>" +
						"<div class='well'>" +
							"<div class='col_header'>" +
								"<span class='ftR del-col'><i class='fa fa-times'></i></span>" +
								"<span class='ftR add-cell'><i class='fa fa-plus'></i></span>" +
								"<input class='col-width ftR' name='col-width' readonly value='20'>" +
								"<input class='col-mobile ftR' name='col-mobile' readonly value='33.3'>" +
							"</div>" +
							"<div class='col_container'>" +
								"<div class='cell_row' data-row='" + newRowNum + "' data-col='" + newColNum + "' data-cell='1'>" +
									"<div class='del-cell'><i class='fa fa-times'></i></div>" +
									"<div class='cell_content'><a href='ConfigPage.do?cmd=configPageDetail&detailId=" + data.detailId + "' class='cell_edit'>添加+</a></div>" +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>");
				
				if (colNum > 3){
					$this.addClass('disabled');
				}
				
				resetClear();
			}
		});
	});
	$(document).on('click', '.add-cell', function(e){
		var $this = $(e.target);
		var $col = $this.closest('.col');
		var rowNum = parseInt($col.data("row"));
		var colNum = parseInt($col.data("col"));
		var lastCellNum = parseInt($col.find('.cell_row:last-child').data("cell"));
		lastCellNum = isNaN(lastCellNum)?1:lastCellNum + 1;
		
		$.mvcAjax({
			url 	: "ConfigPage.do?cmd=addPageDetailAjax",
			data 	: {rowId: rowNum, colId: colNum, cellId: lastCellNum, width:'20', width_mob: '33.3'},
			dataType: 'json',
			success : function(data, ts)
			{
				$this.closest('.col').find('.col_container').append(
						"<div class='cell_row' data-row='" + rowNum + "' data-col='" + colNum + "' data-cell='" + lastCellNum + "'>" +
							"<div class='del-cell'><i class='fa fa-times'></i></div>" +
							"<div class='cell_content'><a href='ConfigPage.do?cmd=configPageDetail&detailId=" + data.detailId + "' class='cell_edit'>添加+</a></div>" +
						"</div>");
			}
		});
	});
	$(document).on('click', '.del-cell', function(e){
		var $this = $(e.target);
		var block = $this.closest('.cell_row');
		var rowId = block.data('row');
		var colId = block.data('col');
		var cellId = block.data('cell');
		KptApi.confirm(messages.deleteMsg, function(){
			$.mvcAjax({
				url 	: "ConfigPage.do?cmd=deletePageDetailAjax",
				data 	: {rowId: rowId, colId: colId, cellId: cellId},
				dataType: 'json',
				success : function(data, ts)
				{
					$(e.target).closest('.cell_row').remove();
				}
			});
		});
	});
	$(document).on('click', '.del-block', function(e){
		var $this = $(e.target);
		var block = $this.closest('.well').parent();
		var rowId = block.data('row');
		KptApi.confirm(messages.deleteMsg, function(){
			$.mvcAjax({
				url 	: "ConfigPage.do?cmd=deletePageDetailAjax",
				data 	: {rowId: rowId},
				dataType: 'json',
				success : function(data, ts)
				{
					$this.closest('.well').parent().remove();
				}
			});
		});
	});
	resetClear();
});


function resetClear(){
	$("#detail-setting .panel-body > .row").each(function(){
		var block = $(this);
		var total_width = 0;
		var widthClass = $("#detail-setting .panel-body").hasClass('mobile')?'col-mobile':'col-width';
		block.find('.col').removeClass('clear');
		block.find('.col').each(function(){
			total_width = total_width + parseInt($(this).find('.' + widthClass).eq(0).val());
			if (total_width > 100){
				$(this).addClass('clear');
				total_width = parseInt($(this).find('.' + widthClass).eq(0).val())
			}
		});
	});
}