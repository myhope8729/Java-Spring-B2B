var toastr_options = {
    "closeButton": true,
    "debug": false,
    "positionClass": "toast-top-center",
    "onclick": null,
    "showDuration": "1000",
    "hideDuration": "1000",
    "timeOut": "4000",
    "extendedTimeOut": "2000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

var KptApi = function () {
	// IE mode
    var isRTL = false;
    var isIE8 = false;
    var isIE9 = false;
    var isIE10 = false;

    var resizeHandlers = [];
	
    var assetsPath = '../../';
    var globalImgPath = assetsPath + 'images/';
    
    // initializes main settings
    var handleInit = function() {

        if ($('body').css('direction') === 'rtl') {
            isRTL = true;
        }

        isIE8 = !!navigator.userAgent.match(/MSIE 8.0/);
        isIE9 = !!navigator.userAgent.match(/MSIE 9.0/);
        isIE10 = !!navigator.userAgent.match(/MSIE 10.0/);
        
        if (isIE10 || isIE9 || isIE8) {
            $('html').addClass('ie'); // detect IE version
        }
        if (isIE8) {
        	$('html').addClass('ie8'); // detect IE8 version
        }
        if (isIE9) {
        	$('html').addClass('ie9'); // detect IE9 version
        }
        
        if (isIE8 || isIE9) { // ie8 & ie9
            // this is html5 placeholder fix for inputs, inputs with placeholder-no-fix class will be skipped(e.g: we need this for password fields)
            $('input[placeholder]:not(.placeholder-no-fix), textarea[placeholder]:not(.placeholder-no-fix)').each(function() {
                var input = $(this);

                if (input.val() === '' && input.attr("placeholder") !== '') {
                    input.addClass("placeholder").val(input.attr('placeholder'));
                }

                input.focus(function() {
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                });

                input.blur(function() {
                    if (input.val() === '' || input.val() == input.attr('placeholder')) {
                        input.val(input.attr('placeholder'));
                    }
                });
            });
        }
    };
    
    var _runResizeHandlers = function() {
        // reinitialize other subscribed elements
        for (var i = 0; i < resizeHandlers.length; i++) {
            var each = resizeHandlers[i];
            each.call();
        }
    };

    // handle the layout reinitialization on window resize
    var handleOnResize = function() {
        var resize;
        if (isIE8) {
            var currheight;
            $(window).resize(function() {
                if (currheight == document.documentElement.clientHeight) {
                    return; //quite event since only body resized not window.
                }
                if (resize) {
                    clearTimeout(resize);
                }
                resize = setTimeout(function() {
                    _runResizeHandlers();
                }, 50); // wait 50ms until window resize finishes.                
                currheight = document.documentElement.clientHeight; // store last body client height
            });
        } else {
            $(window).resize(function() {
                if (resize) {
                    clearTimeout(resize);
                }
                resize = setTimeout(function() {
                    _runResizeHandlers();
                }, 50); // wait 50ms until window resize finishes.
            });
        }
    };
    
    return {
    	// check IE8 mode
        isIE8: function() {
            return isIE8;
        },

        // check IE9 mode
        isIE9: function() {
            return isIE9;
        },
    	init: function() {
        	handleInit(); // initialize core variables
            handleOnResize(); // set and handle responsive  
        },  
        blockUI2: function (targetObj, iconOnly){
            this.blockUI({target:targetObj, iconOnly: iconOnly, boxed:true});
        },
        blockUI: function (options) {  
            var options = $.extend(true, {boxed: true}, options);
            
            var html = '';
            if (options.iconOnly) {
                html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><img src="' + this.getGlobalImgPath() + 'loading-spinner-grey.gif" align=""></div>';
            } else if (options.textOnly) {
                html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><span>&nbsp;&nbsp;' + (options.message ? options.message : '处理中...') + '</span></div>';
            } else {
                html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '')+'"><img src="' + this.getGlobalImgPath() + 'loading-spinner-grey.gif" align=""><span>&nbsp;&nbsp;' + (options.message ? options.message : '处理中...') + '</span></div>';
            }

            if (options.target) { // element blocking
                var el = $(options.target);
                if (el.height() <= ($(window).height())) {
                    options.cenrerY = true;
                }
                el.block({
                    message: html,
                    baseZ: options.zIndex ? options.zIndex : 1000,
                    centerY: options.cenrerY != undefined ? options.cenrerY : false,
                    css: {
                        top: '10%',
                        border: '0',
                        padding: '0',
                        backgroundColor: 'none'
                    },
                    overlayCSS: {
                        backgroundColor: options.overlayColor ? options.overlayColor : '#000',
                        opacity: options.boxed ? 0.05 : 0.1,
                        cursor: 'wait'
                    }
                });
            } else { // page blocking
                $.blockUI({
                    message: html,
                    baseZ: options.zIndex ? options.zIndex : 1000,
                    css: {
                        border: '0',
                        padding: '0',
                        backgroundColor: 'none'
                    },
                    overlayCSS: {
                        backgroundColor: options.overlayColor ? options.overlayColor : '#000',
                        opacity: options.boxed ? 0.05 : 0.1,
                        cursor: 'wait'
                    }
                });
            }
        },

        // wrMetronicer function to  un-block element(finish loading)
        unblockUI: function (target) {
            if (target) {
                $(target).unblock({
                    onUnblock: function () {
                        $(target).css('position', '');
                        $(target).css('zoom', '');
                    }
                });
            } else {
                $.unblockUI();
            }
        },

        getGlobalImgPath: function () {
            return globalImgPath;
        },

        confirm: function(msg, okCallback, cancelCallback ){
            bootbox.dialog({
                message: msg,
                title: "确认",
                buttons: {
                    main: {
                        label: "是",
                        className: "blue",
                        callback: okCallback
                    },
                    danger: {
                        label: "否",
                        className: "default",
                        callback: cancelCallback
                    }
                }
            });
        },
        confirmDel: function(msg, okCallback, cancelCallback ){
            bootbox.dialog({
                message: msg,
                title: "删除确认",
                buttons: {
                    main: {
                        label: "是",
                        className: "blue",
                        callback: okCallback
                    },
                    danger: {
                        label: "否",
                        className: "default",
                        callback: cancelCallback
                    }
                }
            });
        },
        alert: function(msg, okCallback) {
            bootbox.dialog({
                message: msg,
                title: "提示",
                buttons: {
                    main: {
                        label: "确 认",
                        className: "blue",
                        callback: okCallback
                    }
                }
            });
        },
        
        getViewPort: function () {
            var e = window, a = 'inner';
            if (!('innerWidth' in window)) {
                a = 'client';
                e = document.documentElement || document.body;
            }
            return {
                width: e[a + 'Width'],
                height: e[a + 'Height']
            }
        },
        
        scrollTo: function (el, offeset) {
            var pos = (el && el.size() > 0) ? el.offset().top : 0;

             if (el) {
                if ($('body').hasClass('page-header-fixed')) { 
                    pos = pos - $('#banner').height(); 
                }            
                pos = pos + (offeset ? offeset : -1 * el.height());
            }

            $('html,body').animate({
                scrollTop: pos
            }, 'slow');
        }, 
        addResizeHandler: function(func) {
            resizeHandlers.push(func);
        },

        //public functon to call _runresizeHandlers
        runResizeHandlers: function() {
            _runResizeHandlers();
        },
        showMsg: function(str, clear) {
        	if (clear != 0) {
        		KptApi.clearMsg();
        	}
            toastr.options = toastr_options;
            toastr['success']("", str);   
        },
        showWarning: function(str, clear) {
        	if (clear != 0) {
        		KptApi.clearMsg();
        	}
            toastr.options = toastr_options;
            toastr['warning']("", str);   
        },
        showError: function(str, clear) {
        	if (clear != 0) {
        		KptApi.clearMsg();
        	}
            toastr.options = toastr_options;
            toastr['error']("", str);   
        },
        showInfo: function(str, clear) {
        	if (clear != 0) {
        		KptApi.clearMsg();
        	}
            toastr.options = toastr_options;
            toastr['info']("", str);   
        },
        clearMsg: function() {
            //toastr.clear();
        	$("#toast-container").remove();
        }
    };
}();

$(document).ready(function(){
    if ("undefined" != typeof toastr)
    	toastr.options = toastr_options;
    
    // checking error message
    var list = $("#alert_message > div.alert");
    if (list.length>0) {
    	list.each(function(ind, obj1){
    		var obj = $(obj1);
    		cls = 'success';
            if (obj.hasClass('alert-danger')) {
                cls = "error";
            } else if (obj.hasClass('alert-info')) {
                cls = "info";
            } else if (obj.hasClass('alert-warning')) {
            	cls = "warning";
            }
            
            toastr[cls]("", obj.html());
    	});
    }
    
    // layout
    KptApi.init();
    handleResize ();
    KptApi.addResizeHandler(handleResize); // reinitialize content height on window resize 
    
    $(".addr-info").click(function(){
    	if ( $(this).parent().parent().hasClass("ui-collapsible-collapsed")) {
    		KptApi.scrollTo($("#dummy"), 0);
    	}
    });
    
    
    if (KptApi.isIE8() || KptApi.isIE9()) {
    	var list = KptApi.getViewPort();
    	var width = list['width'];
    	
    	var sizes = [768, 992, 1200];
    	var styles = [
    	              ".col-sm-1,.col-sm-10,.col-sm-11,.col-sm-12,.col-sm-2,.col-sm-3,.col-sm-4,.col-sm-5,.col-sm-6,.col-sm-7,.col-sm-8,.col-sm-9{float:left}.col-sm-12{width:100%}.col-sm-11{width:91.66666667%}.col-sm-10{width:83.33333333%}.col-sm-9{width:75%}.col-sm-8{width:66.66666667%}.col-sm-7{width:58.33333333%}.col-sm-6{width:50%}.col-sm-5{width:41.66666667%}.col-sm-4{width:33.33333333%}.col-sm-3{width:25%}.col-sm-2{width:16.66666667%}.col-sm-1{width:8.33333333%}.col-sm-pull-12{right:100%}.col-sm-pull-11{right:91.66666667%}.col-sm-pull-10{right:83.33333333%}.col-sm-pull-9{right:75%}.col-sm-pull-8{right:66.66666667%}.col-sm-pull-7{right:58.33333333%}.col-sm-pull-6{right:50%}.col-sm-pull-5{right:41.66666667%}.col-sm-pull-4{right:33.33333333%}.col-sm-pull-3{right:25%}.col-sm-pull-2{right:16.66666667%}.col-sm-pull-1{right:8.33333333%}.col-sm-pull-0{right:auto}.col-sm-push-12{left:100%}.col-sm-push-11{left:91.66666667%}.col-sm-push-10{left:83.33333333%}.col-sm-push-9{left:75%}.col-sm-push-8{left:66.66666667%}.col-sm-push-7{left:58.33333333%}.col-sm-push-6{left:50%}.col-sm-push-5{left:41.66666667%}.col-sm-push-4{left:33.33333333%}.col-sm-push-3{left:25%}.col-sm-push-2{left:16.66666667%}.col-sm-push-1{left:8.33333333%}.col-sm-push-0{left:auto}.col-sm-offset-12{margin-left:100%}.col-sm-offset-11{margin-left:91.66666667%}.col-sm-offset-10{margin-left:83.33333333%}.col-sm-offset-9{margin-left:75%}.col-sm-offset-8{margin-left:66.66666667%}.col-sm-offset-7{margin-left:58.33333333%}.col-sm-offset-6{margin-left:50%}.col-sm-offset-5{margin-left:41.66666667%}.col-sm-offset-4{margin-left:33.33333333%}.col-sm-offset-3{margin-left:25%}.col-sm-offset-2{margin-left:16.66666667%}.col-sm-offset-1{margin-left:8.33333333%}.col-sm-offset-0{margin-left:0}.container {width: 750px;}"
    	              + ".form-horizontal .control-label {padding-top: 7px;margin-bottom: 0;text-align: right;}.form-inline .form-control {display: inline-block;width: auto;vertical-align: middle;}"
    	              + ".modal-dialog{width:600px}"
    	              + ".bootbox .modal-dialog {width: 400px;margin: 30px auto}"
    	              ,
    	              
    	              ".col-md-1,.col-md-10,.col-md-11,.col-md-12,.col-md-2,.col-md-3,.col-md-4,.col-md-5,.col-md-6,.col-md-7,.col-md-8,.col-md-9{float:left}.col-md-12{width:100%}.col-md-11{width:91.66666667%}.col-md-10{width:83.33333333%}.col-md-9{width:75%}.col-md-8{width:66.66666667%}.col-md-7{width:58.33333333%}.col-md-6{width:50%}.col-md-5{width:41.66666667%}.col-md-4{width:33.33333333%}.col-md-3{width:25%}.col-md-2{width:16.66666667%}.col-md-1{width:8.33333333%}.col-md-pull-12{right:100%}.col-md-pull-11{right:91.66666667%}.col-md-pull-10{right:83.33333333%}.col-md-pull-9{right:75%}.col-md-pull-8{right:66.66666667%}.col-md-pull-7{right:58.33333333%}.col-md-pull-6{right:50%}.col-md-pull-5{right:41.66666667%}.col-md-pull-4{right:33.33333333%}.col-md-pull-3{right:25%}.col-md-pull-2{right:16.66666667%}.col-md-pull-1{right:8.33333333%}.col-md-pull-0{right:auto}.col-md-push-12{left:100%}.col-md-push-11{left:91.66666667%}.col-md-push-10{left:83.33333333%}.col-md-push-9{left:75%}.col-md-push-8{left:66.66666667%}.col-md-push-7{left:58.33333333%}.col-md-push-6{left:50%}.col-md-push-5{left:41.66666667%}.col-md-push-4{left:33.33333333%}.col-md-push-3{left:25%}.col-md-push-2{left:16.66666667%}.col-md-push-1{left:8.33333333%}.col-md-push-0{left:auto}.col-md-offset-12{margin-left:100%}.col-md-offset-11{margin-left:91.66666667%}.col-md-offset-10{margin-left:83.33333333%}.col-md-offset-9{margin-left:75%}.col-md-offset-8{margin-left:66.66666667%}.col-md-offset-7{margin-left:58.33333333%}.col-md-offset-6{margin-left:50%}.col-md-offset-5{margin-left:41.66666667%}.col-md-offset-4{margin-left:33.33333333%}.col-md-offset-3{margin-left:25%}.col-md-offset-2{margin-left:16.66666667%}.col-md-offset-1{margin-left:8.33333333%}.col-md-offset-0{margin-left:0}.container {width: 970px;}"
    	              + ".modal-dialog{width:900px;}",
    	              
    	              ".col-lg-1,.col-lg-10,.col-lg-11,.col-lg-12,.col-lg-2,.col-lg-3,.col-lg-4,.col-lg-5,.col-lg-6,.col-lg-7,.col-lg-8,.col-lg-9{float:left}.col-lg-12{width:100%}.col-lg-11{width:91.66666667%}.col-lg-10{width:83.33333333%}.col-lg-9{width:75%}.col-lg-8{width:66.66666667%}.col-lg-7{width:58.33333333%}.col-lg-6{width:50%}.col-lg-5{width:41.66666667%}.col-lg-4{width:33.33333333%}.col-lg-3{width:25%}.col-lg-2{width:16.66666667%}.col-lg-1{width:8.33333333%}.col-lg-pull-12{right:100%}.col-lg-pull-11{right:91.66666667%}.col-lg-pull-10{right:83.33333333%}.col-lg-pull-9{right:75%}.col-lg-pull-8{right:66.66666667%}.col-lg-pull-7{right:58.33333333%}.col-lg-pull-6{right:50%}.col-lg-pull-5{right:41.66666667%}.col-lg-pull-4{right:33.33333333%}.col-lg-pull-3{right:25%}.col-lg-pull-2{right:16.66666667%}.col-lg-pull-1{right:8.33333333%}.col-lg-pull-0{right:auto}.col-lg-push-12{left:100%}.col-lg-push-11{left:91.66666667%}.col-lg-push-10{left:83.33333333%}.col-lg-push-9{left:75%}.col-lg-push-8{left:66.66666667%}.col-lg-push-7{left:58.33333333%}.col-lg-push-6{left:50%}.col-lg-push-5{left:41.66666667%}.col-lg-push-4{left:33.33333333%}.col-lg-push-3{left:25%}.col-lg-push-2{left:16.66666667%}.col-lg-push-1{left:8.33333333%}.col-lg-push-0{left:auto}.col-lg-offset-12{margin-left:100%}.col-lg-offset-11{margin-left:91.66666667%}.col-lg-offset-10{margin-left:83.33333333%}.col-lg-offset-9{margin-left:75%}.col-lg-offset-8{margin-left:66.66666667%}.col-lg-offset-7{margin-left:58.33333333%}.col-lg-offset-6{margin-left:50%}.col-lg-offset-5{margin-left:41.66666667%}.col-lg-offset-4{margin-left:33.33333333%}.col-lg-offset-3{margin-left:25%}.col-lg-offset-2{margin-left:16.66666667%}.col-lg-offset-1{margin-left:8.33333333%}.col-lg-offset-0{margin-left:0}.col-lg-mgt20 { margin-top:25px;}.container {width: 1200px;}"
    	              + "",
    	              ];
    	var sobj = "<style>";
    	
    	for (var i=0; i<sizes.length; i++) {
    		if (sizes[i] <= width) {
    			sobj += (styles[i]);
    		}
    	}
    	
    	
    	$("head").append(sobj + "</style>");
    }
    
    
});


function handleResize ()
{
	// if mobile?
	if ($("#left-panel").length > 0){
		//var h1 = $(".content-wrapper").outerHeight(true);
		/*if ($(".p-shopcart").length < 1){
			
			var hFooter = $(".footer_wrapper").outerHeight(true);
			
			var hHeader = $(".ui-header").outerHeight(true);
			if ($(".ui-header").css("position") == "fixed") {
				hFooter += hHeader;
			}
			var hMain = $(".ui-content").outerHeight(false);
			var hTotal = hHeader + hMain;
			
			var screenHeight = KptApi.getViewPort().height;
			
			if (hTotal + hFooter < screenHeight) {
				$(".content-wrapper").css("min-height", (screenHeight - hFooter) + "px");
			}
		} */
	}else {
		var width = KptApi.getViewPort().width;
		var gobj = $("table[id$='grid'], table[id$='Grid'], table#gridContent");
		
		if (gobj.length > 0)
		{
			gobj.each(function(ind, obj){
				var gWidth = 0;
				if (KptApi.isIE8() || KptApi.isIE9()) {
					var t = $(obj).closest("div");
					// gWidth = t.outerWidth();
					// $(obj).setGridWidth(gWidth, true);
				} else {
					var t = $(obj).closest("div.ui-jqgrid").parents("div:first");
					gWidth = t.width();
					$(obj).setGridWidth(gWidth, true);
				}
			});
		}
	}
}

