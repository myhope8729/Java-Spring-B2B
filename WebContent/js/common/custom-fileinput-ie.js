$(document).ready(function(){
	$(document).on('click', '.fileinput .fileinput-exists', function(e){
		$this = $(e.target);
		$fileinput = $this.closest('.fileinput');
		$fileinput.find('.thumbnail').html('');
		$fileinput.removeClass('fileinput-exists').addClass('fileinput-new');
		var tagName = $fileinput.find('input[type="file"]').attr('name');
		var tagId = $fileinput.find('input[type="file"]').attr('id');
		$fileinput.find('input[type="file"]').attr('name', tagName + "_temp");
		$fileinput.find('input[type="file"]').val('');
		$fileinput.find('.btn-file').append('<input type="hidden" name="' + tagName + '"/>');
	});
	$(document).on('change', '.fileinput input[type="file"]', function(e){
		$this = $(e.target);
		$fileinput = $this.closest('.fileinput');
		var hiddenTag = $fileinput.find('input[type="hidden"]');
		if (hiddenTag.length > 0){
			var tagId = $fileinput.find('input[type="hidden"]').attr('id');
			var tagName = $fileinput.find('input[type="hidden"]').attr('name');
			$fileinput.find('input[type="hidden"]').remove();
			$this.attr('name', tagName);
		}
		
		$fileinput.find('.thumbnail').html('');
		var files = e.target.files === undefined ? (e.target && e.target.value ? [{ name: e.target.value.replace(/^.+\\/, '')}] : []) : e.target.files;
		if (files.length === 0) {
	      return;
	    }
		$fileinput.removeClass('fileinput-new').addClass('fileinput-exists');
	    var file = files[0];
	    $fileinput.find('.thumbnail').append('<div class="fileinput-filename">' + file.name + '</div>');
	});
});