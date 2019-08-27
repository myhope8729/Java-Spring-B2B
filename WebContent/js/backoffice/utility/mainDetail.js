//file upload
var fileSeq = 0;
function setFileOld(originalFullName, mediaNo, targetArea, fieldName, fieldSeq){
	var divObj = $('<div>').createElement({ id: fieldName + fieldSeq, parent : $("#" + targetArea) });
	$("<a>").createElement({ href: "javascript:doDownload('" + mediaNo + "');", html : originalFullName, parent : divObj });
	$('<input type=hidden>').createElement({ name: "mediaNo", value: mediaNo, parent: divObj });
	fileSeq++;
}