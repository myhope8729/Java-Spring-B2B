$(document).ready(function(){
	$("#crop_target").Jcrop({
		aspectRatio : parseInt(minWidth) / parseInt(minHeight),
		bgOpacity	: 0.3,
		onSelect	: function(c){
			$('#cropX').val(parseInt(c.x));
		    $('#cropY').val(parseInt(c.y));
		    $('#cropW').val(parseInt(c.w));
		    $('#cropH').val(parseInt(c.h));
		},
		onRelease 	: function(){
			$('#cropX').val(0);
		    $('#cropY').val(0);
		    $('#cropW').val(0);
		    $('#cropH').val(0);
		}
	}, function(){
		api = this;
		api.setSelect([0,0, 140, 140]);
		api.setOptions({bgFade:true});
	});
	$('.fileinput').on('change.bs.fileinput', function(){
		$(this).find('.fileinput-preview').height($(this).find('.fileinput-preview img').height());
		var api;
		$(this).find('.fileinput-preview img').Jcrop({
			aspectRatio : parseInt(minWidth) / parseInt(minHeight),
			bgOpacity	: 0.3,
			onSelect	: function(c){
				$('#cropX').val(parseInt(c.x));
			    $('#cropY').val(parseInt(c.y));
			    $('#cropW').val(parseInt(c.w));
			    $('#cropH').val(parseInt(c.h));
			},
			onRelease 	: function(){
				$('#cropX').val(0);
			    $('#cropY').val(0);
			    $('#cropW').val(0);
			    $('#cropH').val(0);
			}
		}, function(){
			api = this;
			api.setSelect([0,0, 140, 140]);
			api.setOptions({bgFade:true});
		});
		
	});
	
	$('.fileinput').on('clear.bs.fileinput', function(){
		$(this).find('.fileinput-preview').height("auto");
	});
	
	
});