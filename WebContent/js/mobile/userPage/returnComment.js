
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
	
	document.commentFrm.action="UserPage.do?cmd=saveComment";
	document.commentFrm.submit();
}
