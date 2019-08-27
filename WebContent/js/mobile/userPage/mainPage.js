$(document).ready(function() {	
	$('.fancybox').fancybox({
        padding : 0,
        openEffect  : 'elastic',
        closeBtn: true
    });
});

function docomment()
{
	if(document.commentFrm.cText.value=="")
	{
		KptApi.showInfo("请输入留言内容。");
		return;
	}

	if (document.commentFrm.cName.value=="")
	{
		KptApi.showInfo("请输入昵称。");
		return;
	}
	
	var cook = document.getElementById("cookieComment").value;
	var userId = document.getElementById("userId").value;
		
	document.commentFrm.action="UserPage.do?cmd=saveComment&cookieId="+cook+"&userId="+userId;
	document.commentFrm.method="post";
	commentFrm.submit();
}
