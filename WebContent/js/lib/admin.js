document.title=""; // title

//tab memu
function tab_mnu02(no,total,lname) {
	if (no == "") no = "01";

	for(var i=1;i <= total;i++){
	var tlayer = eval("document.getElementById('"+lname+"0"+i+"')");
	tlayer.style.display = 'none';
	}
	
	eval("document.getElementById('"+lname+no+"').style.display = 'block';");
}
