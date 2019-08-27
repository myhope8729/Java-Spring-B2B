CommonGrid.init('grid', {
	url: "BillProc.do?cmd=billProcUncheckedGridAjax",
    postData: $("#billProcUncheckedSearchForm").serializeJSON(),
    datatype:'json',
    colModel:[
          {name:'billProcId',	label:'billProcId',			key:true,		hidden:true},
	      {name:'billId',		label: 'billId',			hidden:true,	sortable:true,	width:200, 	align:'left',	index:'bill_type, bill_id' },
	      {name:'procPos',		label: '单据类别<br/>业务单位',				sortable:false,	align:'left',
	    	  formatter:function(cellValue, options, rowObject){
	    		  if (rowObject.billHead.billType == 'DT0003'){
	    			  return rowObject.billHead.hostUserName + '<br/>《' + rowObject.billHead.custUserName + '》';
	    		  }else if (rowObject.billHead.billType == 'DT0005'){
	    			  return rowObject.billHead.hostUserName + '<br/>' + rowObject.billHead.custUserName;
	    		  }else if (rowObject.billHead.billType == 'DT0006'){
	    			  return '客户名称' + '<br/>' + rowObject.billHead.custUserName;
	    		  }else if (rowObject.billHead.billType == 'DT0001'){
	    			  if (rowObject.billHead.custUserId == $("#userId").val()){
	    				  return '供货方' + '<br/>' + rowObject.billHead.hostUserName;
	    			  }else{
	    				  return '订货方' + '<br/>' + rowObject.billHead.custUserName;
	    			  }
	    		  }else if (rowObject.billHead.billType == 'DT0008'){
	    			  return '客户名称' + '<br/>' + rowObject.billHead.custUserName;
	    		  }else if (rowObject.billHead.billType == 'DT0002'){
	    			  return '供货方' + '<br/>' + rowObject.billHead.hostUserName;
	    		  }else if (rowObject.billHead.billType == 'DT0004'){
	    			  if (rowObject.billHead.custUserId == $("#userId").val()){
	    				  return '供货方' + '<br/>' + rowObject.billHead.hostUserName;
	    			  }else{
	    				  return '订货方' + '<br/>' + rowObject.billHead.custUserName;
	    			  }
	    		  }else if (rowObject.billHead.billType == 'DT0007'){
	    			  if (rowObject.billHead.custUserId == $("#userId").val()){
	    				  return '供货方' + '<br/>' + rowObject.billHead.hostUserName;
	    			  }else{
	    				  return '订货方' + '<br/>' + rowObject.billHead.custUserName;
	    			  }
	    		  }else if (rowObject.billHead.billType == 'DT0011'){
	    			  if (rowObject.billHead.custUserId == $("#userId").val()){
	    				  return '供货方' + '<br/>' + rowObject.billHead.hostUserName;
	    			  }else{
	    				  return '订货方' + '<br/>' + rowObject.billHead.custUserName;
	    			  }
	    		  }
	    	  }
	      },
	      {name:'price',		label: '单据金额<br/>制单日期',		sortable:false,		align:'left', index:'tot2',
	    	  formatter:function(cellValue, options, rowObject){
	    		  var rtn = '';
	    		  if (rowObject.billHead.billType == 'DT0004')
    			  {
	    			  if ((rowObject.lineTotal1 || '') != '')
    				  {
	    				  if (rowObject.lineTotal1 != rowObject.lineTotal2)
	    				  {
	    					  rtn = "<font color='#808080'>(" + round(rowObject.lineTotal1, 2) + ") </font>" + round((rowObject.lineTotal2 || ''), 2);
	    				  }
	    				  else
	    				  {
	    					  rtn = round((rowObject.lineTotal2 || ''), 2);
	    				  }
    				  }
	    			  else
	    			  {
	    				  rtn = '';
	    			  }
    			  }
	    		  else
	    		  {
	    			  if ((rowObject.billHead.totalAmt || '') != '')
    				  {
	    				  if (rowObject.billHead.total2 != rowObject.billHead.totalAmt)
	    				  {
	    					  rtn = "<font color='#808080'>(" + round(rowObject.billHead.totalAmt, 2) + ") </font>" + round((rowObject.billHead.total2 || ''), 2);
	    				  }
	    				  else
	    				  {
	    					  rtn = round((rowObject.billHead.total2 || ''), 2);
	    				  }
    				  }
	    			  else
	    			  {
	    				  rtn = '';
	    			  }
	    		  }
	    		  
	    		  rtn += '<br/>';
	    		  if ((rowObject.billHead.createDate || '') == '') rtn += '-';
	    		  else rtn += rowObject.billHead.createDate;
	    		  
	    		  return rtn;
	    	  }
	      },
	      {name:'control',		label: '操作',		sortable:false,	align:'center', classes:'gridLink', width:60,
	    	  formatter:function(cellValue, opiotns, rowObject){
	    		  var rtn = '';
	    		  if (rowObject.billHead.billType == 'DT0004'){
	    			  rtn = '<a class="mg0-auto" data-role="button" data-icon="carat-r" data-mini="true" data-iconpos="notext" href="BillProc.do?cmd=billProcForm&billProcId='+rowObject.billProcId+'">详细</a>';
	    		  }
	    		  return rtn;
	    	  }
	      }
	],
	sortname: 'a.update_date',
	sortorder : 'desc',
	rowNum :  gridPager.rowNum,
	page :  gridPager.page,
	pager: jQuery('#gridpager')
});

jQuery("#btnBuyStatistic").click(function(){
	window.location.href = "BillProc.do?cmd=buyStatistic&createDateFrom=" + $("#createDateFrom").val() + "&createDateTo=" +  $("#createDateTo").val();
});


function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#billProcUncheckedSearchForm').serializeJSON()
	}).trigger('reloadGrid');
}
