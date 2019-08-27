	$("#addPictureRow").click(function(){
		var currentLineNo = 1 + parseInt($("#currentLineNo").val());
		$(".item_content .panel-body").append('<div class="image_row well fade" id="line_' + currentLineNo + '"><button type="button" data-mini="true" data-theme="b" data-role="button" class="close"  data-icon="delete" data-iconpos="notext" data-dismiss="alert"></button></div>');
		for (i=0; i<4; i++){
			var template = '<div class="col-xs-6">\
				<div class="row">\
					<div class="fileinput fileinput-new col-xs-12 pd5" data-provides="fileinput">\
						<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 100px;">\
						</div>\
						<img src="/uploaded/itemwidget/noImage_300x200.gif"  class="noImage fileinput-new" data-trigger="fileinput"/>\
						<div>\
							<span class="default btn-file">\
							<span class="fileinput-new btn btn-primary">选择图片</span>\
							<span class="fileinput-exists btn btn-info">变更</span>\
							<input type="file" name="imgFile[' + currentLineNo + ']['+ (i) + ']" id="imgFile' + currentLineNo + '_' + (i) + '">\
							</span>\
							<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>\
						</div>\
					</div>\
				</div>\
			</div>';
			$(".item_content .panel-body").find(".image_row:last-child").append(template);
		}
		$(".item_content .panel-body").find(".image_row:last-child").addClass("in");
		$(".item_content .panel-body").enhanceWithin();
		$("#currentLineNo").val(currentLineNo);
		$('html, body').animate({scrollTop: 10000}, 1000);
	});
	
	$("#addTextRow").click(function(){
		$('#textModal').modal();
	});
	
	$(document).on('click', ".editText", function(){
		$("#addState").val($(this).closest(".well").attr("id"));
		var textData = $(this).closest(".well").find(".text_content").html();
		textData = textData.replace(/<br>/gi, "\n");
		$("#text-editor").val(textData);
		$('#textModal').modal();
	});
	
	$(document).on('click', '.well .close', function(){
		$(this).closest('.well').remove();
	});
	
	$("#textSave").click(function(){
		if ($("#addState").val() == "add"){
			// Add row
			var textData = $("#text-editor").val();
			textData = textData.replace(/\n/gi, "<br/>");
			var currentLineNo = 1 + parseInt($("#currentLineNo").val());
			var template = '<div class="text_row well fade in" id="line_' + currentLineNo + '">\
				<button type="button" data-mini="true" data-theme="b"  data-icon="edit" data-role="button" data-iconpos="notext"  class="editText"></button>\
				<button type="button" data-mini="true" data-theme="b" class="close" data-role="button" data-icon="delete" data-iconpos="notext" data-dismiss="alert"></button>\
				<div class="text_content">' + textData + 
				'</div>\
				</div>';
			$(".item_content .panel-body").append(template);
			$(".item_content .panel-body").enhanceWithin();
			$('html, body').animate({scrollTop: 10000}, 1000);
			$('#textModal').modal('hide');
			$("#text-editor").val("");
			$("#currentLineNo").val(currentLineNo);
		}else{
			//edit row
			var textData = $("#text-editor").val();
			textData = textData.replace(/\n/gi, "<br/>");
			var elemId = $("#addState").val();
			$("#" + elemId).find(".text_content").html(textData);
			$('#textModal').modal('hide');
			$("#text-editor").val("");
			$("#addState").val("add");
		}
	});
	
	$("#saveItemPage").click(function(){
		var data = new Array();
		$(".well").each(function(i){
			var lineId = parseInt($(this).attr("id").substr(5));
			$(this).append('<input type="hidden" name="line[]" value="' + lineId + '"/>');
			
			if ($(this).hasClass('text_row')){
				$(this).append('<textarea name="content[' + (i + 1) + ']" class="hiddenData" id="content'+ (i + 1) + '"></textarea>');
				$("#content" + (i + 1)).html($(this).find('.text_content').html());
				$(this).append('<input type="hidden" name="type[]" value="WT0002"/>');
			}else{
				$(this).append('<input type="hidden" name="type[]" value="WT0003"/>');
			}
		});
		$("#itemPageForm").submit();
	});
	
	$("#previewItemPage").click(function(){
		$("#isPreview").val("true");
		$("#saveItemPage").trigger("click");
	});
	
	if (preview){
		var itemId = $("#itemId").val();
		location.href="UserItem.do?cmd=viewItemPage&itemId=" + itemId + "&hostUserId=" + hostId + "&isPreview=true";
	}
