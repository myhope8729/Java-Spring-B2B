$(document).ready(function(){
	var textData = "";
	$("#addPictureRow").click(function(){
		var currentLineNo = 1 + parseInt($("#currentLineNo").val());
		$(".item_content .panel-body").append('<div class="image_row well fade" id="line_' + currentLineNo + '"><button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button></div>');
		for (i=0; i<4; i++){
			var template = '<div class="col-md-3">\
				<div class="row">\
					<div class="fileinput fileinput-new col-md-12" data-provides="fileinput">\
						<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">\
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
		$("#currentLineNo").val(currentLineNo);
		$('html, body').animate({scrollTop: 10000}, 1000);
	});
	
	var tinyMCEOption = {
	    // General options
	    mode : "textareas",
	    theme : "advanced",
	    language: "zh",
	    plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,iespell,inlinepopups,preview,media,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,wordcount,advlist",
	    
	    // Theme options
	    theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
	    theme_advanced_buttons2 : "bullist,numlist,|,outdent,indent,|,hr,|,forecolor,backcolor,|,link,unlink,image,|,fullscreen",
	    theme_advanced_buttons3 : "",
	    
	    //theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak,restoredraft",
	    theme_advanced_toolbar_location : "top",
	    theme_advanced_toolbar_align : "left",
	    theme_advanced_statusbar_location : "bottom",
	    theme_advanced_resizing : true,

	    // Example content CSS (should be your site CSS)
	    content_css : "/js/lib/plugins/tiny_mce/themes/advanced/skins/default/content.css"

	    // Drop lists for link/image/media/template dialogs
	    /*template_external_list_url : "lists/template_list.js",
	    external_image_list_url : "lists/image_list.js",
	    media_external_list_url : "lists/media_list.js",*/
	    
	    //external_link_list_url : "/js/lib/plugins/tiny_mce/themes/advanced/js/link.js",
	    //external_image_list_url : "/js/lib/plugins/tiny_mce/themes/advanced/js/image.js",
	};
	tinyMCE.init(tinyMCEOption);
	jQuery("#textModal").on('shown.bs.modal', function(e){
		tinyMCE.get(0).setContent(textData, {format:'html'});
		textData = "";
	});
	
	$("#addTextRow").click(function(){
		$('#textModal').modal();
	});
	
	$(document).on('click', ".editText", function(){
		$("#addState").val($(this).closest(".well").attr("id"));
		textData = $(this).closest(".well").find(".text_content").html();
		$('#textModal').modal();
	});
	
	$(document).on('click', '.well .close', function(){
		$(this).closest('.well').remove();
	});
	
	$("#textSave").click(function(){
		if ($("#addState").val() == "add"){
			// Add row
			textData = tinyMCE.get(0).getContent();
			var currentLineNo = 1 + parseInt($("#currentLineNo").val());
			var template = '<div class="text_row well fade in" id="line_' + currentLineNo + '">\
				<button type="button" class="editText"><i class="fa fa-pencil"></i></button>\
				<button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button>\
				<div class="text_content">' + textData + 
				'</div>\
				</div>';
			$(".item_content .panel-body").append(template);
			$('html, body').animate({scrollTop: 10000}, 1000);
			$('#textModal').modal('hide');
			tinyMCE.get(0).setContent("", {format : 'raw'});
			textData = "";
			$("#currentLineNo").val(currentLineNo);
		}else{
			//edit row
			textData = tinyMCE.get(0).getContent();
			var elemId = $("#addState").val();
			$("#" + elemId).find(".text_content").html(textData);
			$('#textModal').modal('hide');
			tinyMCE.get(0).setContent("", {format:'raw'});
			textData = "";
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
				$("#content" + (i + 1)).val($(this).find('.text_content').html());
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
		window.open("UserItem.do?cmd=viewItemPage&itemId=" + itemId + "&hostUserId=" + hostId + "&isPreview=true");
	}
});
