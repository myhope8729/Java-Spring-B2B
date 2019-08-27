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
	var currentValue = 0;
	$(document).on('click', '.col-mobile, .col-mobile', function(e){
		var $this = $(e.target);
		$this.removeAttr('readonly');
		$this.focus();
		$this.select();
		currentValue = $this.val();
	});
	$(document).on('blur', '.col-mobile, .col-mobile', function(e){
		var $this = $(e.target);
		var widthData = "data-mobile";
		var block = $this.closest('.row');
		var temp = $this.closest('.col').attr(widthData);
		$this.closest('.col').attr(widthData, $this.val());
		var flag = true;
		if ($this.val() < 33.3 && widthData == 'data-mobile'){
			KptApi.showError('最小宽度是10%在PC端，33.3%在手机端。');
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
		var data = {rowId:rowNum, colId:colNum, width_mob :$this.val()};
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
	});
	$(document).on('click', '.del-col', function(e){
		var $this = $(e.target);
		var block = $this.closest('.row');
		
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
				$this.closest('.well').find('.row_container').append(
					"<div class='col' data-row='" + newRowNum + "' data-col='" + newColNum + "' data-width='20' data-mobile='33.3' style='width:33.3%'>" +
						"<div class='well'>" +
							"<div class='col_header'>" +
								"<span class='ftR del-col'><i class='fa fa-times'></i></span>" +
								"<span class='ftR add-cell'><i class='fa fa-plus'></i></span>" +
								"<input class='col-mobile ftR' name='col-mobile' value='33.3'>" +
							"</div>" +
							"<div class='col_container'>" +
								"<div class='cell_row' data-row='" + newRowNum + "' data-col='" + newColNum + "' data-cell='1'>" +
									"<div class='del-cell'><i class='fa fa-times'></i></div>" +
									"<div class='cell_content'><a class='mg0-auto ui-link ui-btn ui-btn-a ui-icon-plus ui-btn-icon-notext ui-shadow ui-corner-all ui-mini' data-theme='a' data-role='button' data-icon='plus' data-mini='true' data-iconpos='notext' href='ConfigPage.do?cmd=configPageDetail&detailId=" + data.detailId + "'>添加+</a></div>" +
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
							"<div class='cell_content'><a class='mg0-auto ui-link ui-btn ui-btn-a ui-icon-plus ui-btn-icon-notext ui-shadow ui-corner-all ui-mini' data-theme='a' data-role='button' data-icon='plus' data-mini='true' data-iconpos='notext' href='ConfigPage.do?cmd=configPageDetail&detailId=" + data.detailId + "' class='cell_edit'>添加+</a></div>" +
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
		var widthClass = 'col-mobile';
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