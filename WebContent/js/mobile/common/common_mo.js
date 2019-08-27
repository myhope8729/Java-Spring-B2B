$.mobile.ajaxEnabled = false;
var result = { SUCCESS : 0, FAIL : -1 };
var resultStr = { SUCCESS : "0", FAIL : "-1" };

function contextUri(uri) {
	if (_contextPath == null) {
		_contextPath	= "";
	}

	if (uri == null || uri == "") {
		return _contextPath;
	}
	if (uri.charAt(0) != "/") {
		return _contextPath + "/" + uri;
	} else {
		return _contextPath + uri;
	}
}

$.extend({
		defaultAjaxOption : {
			type		: 'POST',
			data 		: '',
			dataType	: 'json',
			onsubmit	: true,
			waitId      : 'body',
			showWait	: true
		},
		mvcAjax	: function (o) {
			var	tform;
			var target;		//result target

			// merge option.
			var ajaxOption = $.extend({}, jQuery.ajaxSettings, jQuery.defaultAjaxOption);
			if (o) {
				o	= $.extend({}, ajaxOption, o);
			}
			
			if (o.form) {

				if (typeof(o.form) == "string") {
					tform	= $("form[name=" + o.form + "]:first");
				} else {
					tform	= $(o.form);
				}
				if (!tform || tform.length == 0) {
					KptApi.showError("Can not find a MVC Ajax runner form " + o.form);
					return null;
				}
				o.url	= tform.attr("action");
				o.data	= tform.serialize();

				var onsubmit	= tform.attr("onsubmit");
				if (o.onsubmit == true && onsubmit != null && jQuery.isFunction(onsubmit) && !onsubmit()) {
					return null;
				}
				o.type		= tform.attr("method");
			}
			
			if (o.target) {
				target	= (typeof(o.target) == "string")? $("#" + o.target) : $(o.target);
			}
			
			// callback function wrapping
			var beforeSend	= o.beforeSend;
			var success		= o.success;
			var error		= o.error;
			
			o.beforeSend	= function(xhr) {
				if (beforeSend && !beforeSend(xhr))
					return false;
				
				return true;
			};

			o.success		= function(data, ts) {
				if (target && data)
					target.html(data);

				if (success)
					success(data, ts);
			};
			
			o.error			= function (xhr, ts, et, ex) {
				if(xhr.status == 200){
					KptApi.showError("no permission.");
				} else
					KptApi.showError("Ajax Request " + ts + " : " + xhr.status + " " + xhr.statusText);
				
				if (error)
					error(xhr, ts, et);
			};
			
			
			//alert(o.showWait);
			if(o.showWait == true){
				/*o.global = true;
				var loadingDiv = $(o.waitId);
				loadingDiv.height($(document).height());
				$(document)
				.ajaxStart(function(){
					loadingDiv.mask("Processing, please wait!");
				})
				.ajaxStop(function(){
					loadingDiv.unmask();
				});*/
				o.global = true;
				$(document)
					.ajaxStart(function(){
						KptApi.blockUI();
					})
					.ajaxStop(function(){
						KptApi.unblockUI();
					});
			}else{
				o.global = false;
				
			}
			
			return $.ajax(o);
		},
		uploadAjax : function(frm, o) {
			
			// merge a option.
			var ajaxOption = $.extend({}, jQuery.ajaxSettings, jQuery.defaultAjaxOption);
			if (o) {
				o	= $.extend({}, ajaxOption, o);
			}
			
			var target;
			var targetName = frm.attr("target");
			
			if (typeof(targetName) == "string") {
				target	= $("iframe[name=" + targetName + "]:first");
			} else {
				target	= $("#" + targetName);
			}
			if (target.length == 0) {
				target = $("<iframe>").createElement({ id : targetName, name : targetName, style : "display:none"}).appendTo("body");
			}
			
			function uploadCallback() {
				var data, doc;
				var xhr = {};
				
				var target = document.getElementById(targetName);
				doc = target.contentWindow ? target.contentWindow.document : target.contentDocument ? target.contentDocument : target.document;
				var isXml = o.dataType == 'xml' || $.isXMLDoc(doc);
				if (!isXml && (doc.body == null || doc.body.innerHTML == '')) {
					return;
				}
				
				xhr.responseText = doc.body ? doc.body.innerHTML : null;
				xhr.responseXML = doc.XMLDocument ? doc.XMLDocument : doc;
				xhr.getResponseHeader = function(header) {
					var headers = {
						'content-type' : o.dataType
					};
					return headers[header];
				};
				
			    var data = (o.dataType == "xml" || !o.dataType) ? xhr.responseXML : xhr.responseText;
				if (o.dataType == 'json' || o.dataType == 'script') {
					var ta = $('textarea', doc)[0];
					if (ta)
						xhr.responseText = ta.value;
					else {
						var pre = $('pre', doc)[0];
						if (pre)
							xhr.responseText = pre.innerHTML;
					}			  
				} else if (o.dataType == 'xml' && !xhr.responseXML && xhr.responseText != null) {
					xhr.responseText = doc.body ? doc.body.innerHTML : null;
					xhr.responseXML = toXml(xhr.responseText);
				}
				data = httpData(xhr, o.dataType);
				
			    if (o.success) {
			    	$(target).unbind("load");
			        o.success(data, "success");
			    }
			    
				if (o.error)
					o.error(data, "error");

			    if (o.global)
			        jQuery.event.trigger( "ajaxSuccess", [xhr, o] );
			}
			
			var loadingDiv = $(o.waitId);
			loadingDiv
				.ajaxStart(function(){
					loadingDiv.mask("Processing, please wait!");
				})
				.ajaxStop(function(){
					loadingDiv.unmask();
			});
			target.load(uploadCallback);
			frm.submit();
			
		}
});

function httpData( xhr, type, s ) {
	var ct = xhr.getResponseHeader("content-type") || "",
		xml = type === "xml" || !type && ct.indexOf("xml") >= 0,
		data = xml ? xhr.responseXML : xhr.responseText;

	if ( xml && data.documentElement.nodeName === "parsererror" ) {
		jQuery.error( "parsererror" );
	}

	// Allow a pre-filtering function to sanitize the response
	// s is checked to keep backwards compatibility
	if ( s && s.dataFilter ) {
		data = s.dataFilter( data, type );
	}

	// The filter can actually parse the response
	if ( typeof data === "string" ) {
		// Get the JavaScript object, if JSON is used.
		if ( type === "json" || !type && ct.indexOf("json") >= 0 ) {
			data = jQuery.parseJSON( data );

		// If the type is "script", eval it in global context
		} else if ( type === "script" || !type && ct.indexOf("javascript") >= 0 ) {
			jQuery.globalEval( data );
		}
	}

	return data;
};

$.fn.serializeJSON = function() { 
	var o = new Array();
    var a = this.serializeArray(); 
    $.each(a, function() {
    	o[this.name] = this.value || '';
    }); 
    return o; 
}; 

$.fn.createElement = function(o) {
	if (o.html) {
		if (typeof o.html == 'object') {
			this.append(o.html);
		} else {
			this.html(o.html);
		}
	}
	if (o.parent && typeof o.parent == 'object') {
		o.parent.append(this);
	}
	if (typeof o.child == 'object') {
		this.append(o.child);
	}
	
	delete o.html;
	delete o.parent;
	this.attr(o);
	
	return this;
};

$.fn.appendParseFields = function(data) {
	if (data == null)
		return;
	
	var params = data.substring(data.indexOf("?")+1);
	
	var pairs = params.split("&");
	for (var i = 0; i < pairs.length; i++) {  
		var keyValuePair = pairs[i].split("=");
		if (keyValuePair.length > 1) {
			this.append("<input type='hidden' name='" + keyValuePair[0] + "' value='" + decodeURIComponent(keyValuePair[1]) + "'>");
		}
	}
};

function getRowId(selectedIds, gridName, attrName) {
	selectedIds = jQuery.map(selectedIds, function(id, index){
		if (id === undefined) 
			return;
	
		var row = jQuery(gridName).jqGrid('getRowData', id);
		return eval("row." + attrName);
	});
	selectedIds = uniqueArray(selectedIds);
	return selectedIds;
}

function getRowId2(selectedIds, gridName, attrName) {
	selectedIds = jQuery.map(selectedIds, function(id, index){
		if (id === undefined) 
			return;
	
		var row = jQuery(gridName).jqGrid('getRowData', id);
		return eval("row." + attrName);
	});
	return selectedIds;
}

function uniqueArray(arr) {
	arr = arr.sort();
	for ( var i = 1; i < arr.length; i++ ) {
		if ( arr[i] === arr[i-1] ) {
			arr.splice(i--, 1);
		}
	}
	return arr;
}


Transitions = {
	defaultOption : {
		url 		: null,
		form		: null,
		data 		: null,
		grid		: null
	},
	load: function(o) {
		if (o == null || o.url == null)
			return null;

		o = $.extend({}, this.defaultOption, o);
		
		var tform = $("<form>").attr({ id: 'transitionForm', method: 'post', style: 'display:none' });
		$("body").append(tform);
		
		tform.attr("action", o.url);
		if (o.form != null){
			var dform;
			if (typeof(o.form) == "string") {
				dform	= $("form[name=" + o.form +"]");
			} else {
				dform	= $(o.form);
			}
			$(":input", dform).each(function() {
				var c = $(this).clone();
				$(c).val($(this).val());
				tform.append(c);
			});
		}
		
		if (o.data != null)
			tform.appendParseFields(o.data);
		
		if (o.grid != null) {
//			var sord = jQuery("#" + o.grid).jqGrid("getGridParam", "sortorder");
//			tform.append($('<input>').attr({ type: "hidden", name: "page.sord", value: sord }));
//			var sidx = jQuery("#" + o.grid).jqGrid("getGridParam", "sortname");
//			tform.append($('<input>').attr({ type: "hidden", name: "page.sidx", value: sidx }));
			
			var page = jQuery("#" + o.grid).jqGrid("getGridParam", "page");
			tform.append($('<input>').attr({ type: "hidden", name: "page.page", value: page }));
		}

		tform.append($('<input>').attr({ type: "hidden", name: "localFullUrl", value: location.href }));
		tform.submit();
	}
};

/**
 * logout
 */
function logout() {
	/*KptApi.confirm("Are you sure you want to logout?", function(){
		*/window.location.href = "Login.do?cmd=logout";
	/*});*/
}

/**
 * javascript load
 */
function importJS(src) {
	var js = $("<script>").attr({type: "text/javascript", src: src});
	$("head").append(js);
}

/**
 * move previous
 */
function previous(frm) {
	var pform;
	if (frm == null)
		frm = "previousFrm";

	if (typeof(frm) == "string") {
		pform	= $("form[name=" + frm + "]:first");
	} else {
		pform	= $(frm);
	}
	pform.submit();
}

function removeTag(str){
	   
	str = str.replace(/<(\/?)p>/gi,""); 
	str = str.replace(/(<br>)|(<br \/>)/gi,""); 
	str = str.replace(/<head>(.*?)<(\/?)head>/gi,""); 
	str = str.replace(/<style>(.*?)<(\/?)style>/gi,""); 
	str = str.replace(/<(\/?)body>/gi,""); 
	
	return str;
}


/**
 * Setting Cookie
 * @param name
 * @param value
 * @param expiredays
 * @return
 */
function setCookie(name, value, expiredays) {
	var today = new Date();
	today.setDate(today.getDate() + expiredays);
	document.cookie = name + "=" + escape(value) + "; path=/; expires=" + today.toGMTString() + ";";
}

/**
 * search Cookie
 * @param key
 * @return
 */
function getCookie(key) {
	var cook = document.cookie + ";";
	var idx = cook.indexOf(key, 0);
	var val = "";
	
	if (idx != -1) {
		cook = cook.substring(idx, cook.length);
		begin = cook.indexOf("=", 0) + 1;
		end = cook.indexOf(";", begin);
		val = unescape( cook.substring(begin, end) );
	}
	
	return val;
}

/**
 * calculate width as ascii is width 1, korean is width 2.
 * @param text
 * @returns width
 */
function textWidth(text) {
	if (!text){
		return 0;
	}
	len = 0;
	for(i=0; i<text.length;i++) {
		len += text.charCodeAt(i) < 128 ? 1 : 2;
	}
	return len;
}

/**
 * calculate width as ascii is 1 byte, korean is width 3 byte.
 * @param text
 * @returns bytes of string
 */
function textByteLength(text) {
	if (!text){
		return 0;
	}
	len = 0;
	for(i=0; i<text.length;i++) {
		len += text.charCodeAt(i) < 128 ? 1 : 3;
	}
	return len;
}

// Replace All
function replaceAll(str, searchStr, replaceStr) {
	while (str.indexOf(searchStr) != -1) {
		str = str.replace(searchStr, replaceStr);
	}
	return str;
}

function validateDateTerm(from, to){
	var toDate = function(txt){
		var re = /\d\d\d\d\.\d+\.\d+/;
		var m = re.exec(txt);
		if (!m) {
			return null;
		}
		
		return new Date(txt.replace(/\./g, "/"));
	};
	var fromDate = toDate($("#"+from).val());
	var toDate = toDate($("#"+to).val());
	if (fromDate == null || toDate == null){
		return false;
	}
	return toDate-fromDate >= 0 ;
}

function checkSpecial(val){
    var strobj = val;
     re = /[~!@\#$%<>^&*\()\-=+_\']/gi;
     if(re.test(strobj)){
  	   return false;
     }
     
     return true;
}
	 

function isNumeric(value) {
	return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(value);
}

function digit(value, digit) {
	if (value=='')
		return value;
	var minus = 0 ;
	var s=1;
	if (digit > 0) {
		s = Math.pow(10,digit);
	}
	if (value<0)
	{
		value = Math.abs(value);
		minus = 1; 
	}
	value = Math.round(value * s)/s;
	var intVal = Math.floor(value);
	leaveValue = (value%1);
	
	leaveValuePro = Math.round((value%1)*1000)/1000;
	
	if(leaveValuePro - leaveValue > 0.00001){
		//alert(intVal + leaveValuePro);
		if (minus == 1) {
			return  0 - intVal - leaveValuePro;
		}
		else {
			return intVal + leaveValuePro;
		}
	}
	/*
	var numbers = (value + "").split('.');
	
	if(numbers.length < 2){
		
	}else{
		if(numbers[1] * 1 == 0){
			return numbers[0];
		}else{
			var tail = numbers[1] + "";
			for(var i = tail.length - 1; i >= 0; i--){
				//alert("one item : " + tail.substring(i, i + 1));
				if((tail.substring(i, i + 1) + 0)*1 > 0){
					tail = tail.substring(0, i+1);
					//alert("tail:index" + i);
					break;
				}
			}
			//alert("result : " + (numbers[0] + "." + tail)*1);
			return (numbers[0] + "." + tail)*1;
			//return (numbers[0] + "." + (numbers[1]*1))*1;
		}
	}*/
	if (minus == 1)
	{
		return 0 - value;
	}
	return value;
}
function round(value, digit){
	if (value=='')
		return value;
	var minus = 0 ;
	var s=1;
	if (digit > 0) {
		s = Math.pow(10,digit);
	}
	if (value<0)
	{
		value = Math.abs(value);
		minus = 1; 
	}
	value = Math.round(value * s)/s;
	
	var numbers = (value + "").split('.');
	var intVal = value + "";
	var decimal = "";
	if (numbers.length < 2){
	}
	else {
		intVal = numbers[0]+"";
		decimal = numbers[1]+ "";
	}
	for (var i = 0; i < digit; i++){
		if (decimal.substr(i)=="")
			decimal += "0";
	}
	if (decimal.length > 0) {
		value = intVal + "." + decimal ;
	} else {
		value = intVal;
	}
	if (minus == 1)
	{
		return "-" + value;
	}
	return value;
}

function calc(charFormula){

	parIndexs = new Array();
	var idx = 0;
	var sizeFor = charFormula.length;
	var result;
	for (i = 0;i < sizeFor;i ++){
		if (charFormula[i] == '('){
			parIndexs[idx] = i;
			idx ++;
		}
		else if (charFormula[i] == ')'){
			idx -- ;
			var subFormula = charFormula.substring(parIndexs[idx] + 1 ,i);
			result = calcExpress(subFormula);
			subFormula = "(" + subFormula + ")";
			charFormula = charFormula.replace(subFormula, result);
			sizeFor = charFormula.length;
			if (idx == 0){
				i = 0;
			}
			else{
				
				i = parIndexs[idx-1];
			}
		}
	}
	result = calcExpress(charFormula);
	return result;
}
function calcExpress(formula) {
	for (i=0;i<formula.length ;i++ )
		{
			cc = formula[i];
			if (cc=="^")
			{
				for (j=i-1; j>=0; j--)
				{
					if (formula[j] == "+" || formula[j] == "-" || formula[j] == "*" || formula[j] == "/" || formula[j] == "^" || j == 0)
					{
						if (j == 0)
						{
							j = -1;
						}
						if (j == i-1)
						{
							j = kkkkkkk;
						}
						val1 = formula.substring(j+1,i);
						
						break;
					}
				}
				for (j=i+1; j<formula.length; j++)
				{
					if (formula[j] == "+" || formula[j] == "-" || formula[j] == "*" || formula[j] == "/" || formula[j] == "^" || j == formula.length-1)
					{
						if (j==i+1 && formula[j] == "-")
						{
							continue;
						}
						if (j==i+1 && formula[j] != "-")
						{
							j = mmmmmmmm;
						}
						if (j == formula.length - 1)
						{
							j = formula.length;
						}
						val2 = formula.substring(i+1,j);
						
						break;
					}

				}
				originstr=val1+"^"+val2;
				newstr = 'Math.pow("'+val1+'","'+val2+'")';
				formula = formula.replace(originstr, newstr);
				
			}
		}
		r = eval(formula);
		return r;
}
//jqGrid setScrollHeight 
function SetJQGridScrollTop(gridId, rowIndex){
	jQuery("#gview_" + gridId + " .ui-jqgrid-bdiv").scrollTop(22 * (rowIndex - 1));
}

function openPrdCostListPageWithReturnPage(prdId, cUrl, mUrl, searchFrmName) {
	if (prdId == null)
		return;
	
	$("#" + searchFrmName + " #prdId").val(prdId);
	$("#" + searchFrmName + " #returnPage1").val(cUrl);
	$("#" + searchFrmName + " #returnPage2").val(mUrl);
	Transitions.load({
		url : "Cost.do?cmd=prdCostForm3",
		data : $('#' + searchFrmName).serialize()
	});
}


function funcImg(cellvalue, options, rowObject){
	if (cellvalue != "")
		return "<img src='/uploaded/useritem/" + cellvalue + "' width='35px' height='35px' />";
	else
		return "<img src='/uploaded/useritem/noImage_140x140.gif' width='35px' height='35px' />";
}

function removeEndZero(val)
{
	var val = "" + val;
	
	return val;
}


function getJsDisplay(qty, cntInPackage, isArray)
{
	var packageCnt = 0;
	var balCnt = 0;
	var jsDisplay = " ";
	var qtyNew = 0;	
	var matched = false;
	
	if (cntInPackage == 0) {
		
	} else {
		qty *= 1000;
		cntInPackage *= 1000; 
		
		packageCnt = Math.floor( qty/cntInPackage);
		
		var rem = (qty) % (cntInPackage);
		
		balCnt = rem;
		
		qtyNew = round(qty/cntInPackage, 3);
		if (packageCnt > 0){
			jsDisplay = packageCnt +  "件";
		}
		if (balCnt > 0){
			if (packageCnt == 0) jsDisplay = round(balCnt/1000, 3);
			else jsDisplay += "+" + round(balCnt/1000, 3);
		} else {
			matched = true;
		}	
	}
	
	if (isArray == true) {
		return {
			jsDisplay : jsDisplay,
			qtyNew : qtyNew,
			matched: matched,
			existPackage : packageCnt > 0
		};
	} else {
		return jsDisplay;
	}
}

$(document).ready(function() {
	
	$("form.admin-form").validate( $.extend(false, validateDefaultOption2, {
		submitHandler: function(form) {
			validateDefaultOption2.submitHandler(form);
		},
		onSuccess: function(data) {
			KptApi.showMsg(data.result.resultMsg);
		},
		onFail: function(data) {
			KptApi.showError(data.result.resultMsg);
		}
	}));
	
	$(".btnReset").click(function(e){
		var formObj = $(this).parents("form:first");
		formObj.find("input[type=text]").val("");
		formObj.find("select").val("");
		formObj.find("select").selectmenu("refresh");
	});
	
	$('.date-picker').datepicker({
        orientation: "left",
        autoclose: true,
        todayBtn: true,
        weekStart: 7,
		todayHighlight: true,
		language: 'zh-CN'
    });
	
	/** Store Menu **/
	$('.store-menu .submenu').on('click', function(e){
		if ($(e.target).hasClass("fa")) {
	        $(this)
	            .parent('li')
	            .toggleClass('open');
		}
    });
	
	var img_popup_option = {
			autoResize  : true,
			autoCenter  : true,
			fitToView   : true,
			aspectRatio : true
	};
	if (jQuery().fancybox) {
	    $('.fancybox-pic').fancybox(img_popup_option);
	}
	
});
