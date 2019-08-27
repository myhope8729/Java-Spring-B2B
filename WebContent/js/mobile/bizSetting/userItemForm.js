	$('.fileinput').on('change.bs.fileinput', function(){
		$(this).find('.fileinput-preview').height($(this).find('.fileinput-preview img').height());
	});
	
	$('.fileinput').on('clear.bs.fileinput', function(){
		$(this).find('.fileinput-preview').height("auto");
	});
