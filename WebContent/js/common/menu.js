var timeout     = 400;
var closetimer	= 0;
var menuitem    = 0;

function openJsmenu() {
	cancelTimerJsmenu();
	closeJsmenu();
	menuitem = $(this).find('div').eq(0).css('visibility', 'visible');
}

function closeJsmenu() {
	if (menuitem) 
		menuitem.css('visibility', 'hidden');
}

function timerJsmenu() {
	closetimer = window.setTimeout(closeJsmenu, timeout);
}

function cancelTimerJsmenu() {
	if (closetimer) {
		window.clearTimeout(closetimer);
		closetimer = null;
	}
}

$(document).ready(function(){
	//$('#jsmenu > li').bind('mouseover', openJsmenu);
	//$('#jsmenu > li').bind('mouseout',  timerJsmenu);
});
