$(document).ready(function(){
	CommonGrid.init('grid', {
		url: "BillProc.do?cmd=billProcUncheckedGridAjax",
	    postData: $("#billProcUncheckedSearchForm").serialize(),
	    datatype:'json',
	    colModel:[
              {name:'billProcId',	label:'billProcId',		key:true,	hidden:true},
		      {name:'billId',		label: '单据类别<br/>单据编号',		sortable:true,	width:170, 	align:'left',	index:'bill_id',
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var rtn = rowObject.billHead.billTypeName;
		    		  if ((rowObject.billHead.bnoUser || '') != '') rtn += '<br/>' + rowObject.billHead.bnoUser;
		    		  else rtn += '<br/>' + cellValue;
		    		  
		    		  return rtn;
		    	  }
		      },
		      {name:'procPos',		label: '业务单位',					sortable:false,	width:180, 	align:'left',
		    	  formatter:function(cellValue, options, rowObject){
		    		  if (rowObject.billHead.billType == 'DT0010')
	    			  {
		    			  return rowObject.billHead.hostUserName + '<br/> 《' + rowObject.billHead.custUserName + "》";
	    			  }
		    		  else if ((rowObject.billHead.billType == 'DT0003') || (rowObject.billHead.billType == 'DT0005'))
	    			  {
		    			  return rowObject.billHead.hostUserName + '<br/>' + rowObject.billHead.custUserName;
	    			  }
		    		  else if ((rowObject.billHead.billType == 'DT0006') || (rowObject.billHead.billType == 'DT0008'))
	    			  {
		    			  return '客户名称' + '<br/>' + rowObject.billHead.custUserName;
	    			  }
		    		  else if (rowObject.billHead.billType == 'DT0002')
	    			  {
		    			  return '供货方' + '<br/>' + rowObject.billHead.hostUserName;
	    			  }
		    		  else if ((rowObject.billHead.billType == 'DT0001') || (rowObject.billHead.billType == 'DT0004') || (rowObject.billHead.billType == 'DT0007') || (rowObject.billHead.billType == 'DT0011'))
		    		  {
		    			  if (rowObject.billHead.custUserId == $("#userId").val())
		    			  {
		    				  return '供货方' + '<br/>' + rowObject.billHead.hostUserName;
		    			  }
		    			  else
		    			  {
		    				  return '订货方' + '<br/>' + rowObject.billHead.custUserName;
		    			  }
		    		  }
		    	  }
		      },
		      {name:'payTypeName',	label: '付款方式<br/>付款类别',		sortable:true,	width:150, 	align:'left',		index:'paytype_name',
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  if ((rowObject.billHead.billType == 'DT0010') || (rowObject.billHead.billType == 'DT0005'))
	    			  {
		    			  return '-';
	    			  }
		    		  else
	    			  {
		    			  var rtn = (rowObject.billHead.paytypeName) || '-';
		    			  var paymentType = rowObject.billHead.paymentType || '';
		    			  
		    			  if (paymentType != '')
		    			  {
		    				  rtn += '<br/>' + (paymentType || '');
		    			  }
		    			  if (rowObject.billHead.weixinYn == 'Y')
	    				  {
		    				  rtn += '<br/>' + (rowObject.billHead.payStateName || '');
	    				  }
		    			  return rtn;
	    			  }
		    	  }
		      },
		      {name:'price',		label: '单据金额(元)',				sortable:false,	width:120, 	align:'right', index:'tot2',
		    	  formatter:function(cellValue, options, rowObject){
		    		  if (rowObject.billHead.billType == 'DT0004')
	    			  {
		    			  if ((rowObject.lineTotal1 || '') != '')
	    				  {
		    				  if (rowObject.lineTotal1 != rowObject.lineTotal2)
		    				  {
		    					  return "<font color='#808080'>(" + round(rowObject.lineTotal1, 2) + ") </font>" + round((rowObject.lineTotal2 || ''), 2);
		    				  }
		    				  else
		    				  {
		    					  return round((rowObject.lineTotal2 || ''), 2);
		    				  }
	    				  }
		    			  else
		    			  {
		    				  return '';
		    			  }
	    			  }
		    		  else
		    		  {
		    			  if ((rowObject.billHead.totalAmt || '') != '')
	    				  {
		    				  if (rowObject.billHead.total2 != rowObject.billHead.totalAmt)
		    				  {
		    					  return "<font color='#808080'>(" + round(rowObject.billHead.totalAmt, 2) + ") </font>" + round((rowObject.billHead.total2 || ''), 2);
		    				  }
		    				  else
		    				  {
		    					  return round((rowObject.billHead.total2 || ''), 2);
		    				  }
	    				  }
		    			  else
		    			  {
		    				  return '';
		    			  }
		    		  }
		    		  
		    		  return '';
		    	  }
		      },
		      {name:'processor',	label: '处理类别<br/>处理人',		sortable:true,	width:200, 	align:'left', index:'bill_proc_name'},
		      {name:'create_date',	label: '业务日期',					sortable:true,	width:120, 	align:'center', 
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var rtn = '';
		    		  if (rowObject.billHead.billType == 'DT0006')
	    			  {
		    			  rtn = '到账日期';
	    			  }
		    		  else if (rowObject.billHead.billType == 'DT0001')
		    		  {
		    			  rtn = '付款日期';
		    		  }
		    		  else if (rowObject.billHead.billType == 'DT0004')
		    		  {
		    			  rtn = '制单日期';
		    		  }
		    		  else if (rowObject.billHead.billType == 'DT0008')
		    		  {
		    			  rtn = '制单日期';
		    		  }
		    		  else if (rowObject.billHead.billType == 'DT0002')
		    		  {
		    			  rtn = '入库日期';
		    		  }
		    		  else if (rowObject.billHead.billType == 'DT0007')
		    		  {
		    			  rtn = '退货日期';
		    		  }
		    		  else if (rowObject.billHead.billType == 'DT0011')
		    		  {
		    			  rtn = '报损日期';
		    		  }
		    		  else if (rowObject.billHead.billType == 'DT0010')
		    		  {
		    			  rtn = '发布日期';
		    		  }
		    		  else if (rowObject.billHead.billType == 'DT0005')
		    		  {
		    			  rtn = '调价日期';
		    		  }
		    		  rtn += '<br/>';
		    		  if ((rowObject.billHead.createDate || '') == '') rtn += '-';
		    		  else rtn += rowObject.billHead.createDate;
		    		  
		    		  return rtn;
		    	  }
		      },
		      {name:'note',			label: '备注',						sortable:true,	width:150, 	align:'left', index:'note', 
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var note = rowObject.billHead.note;
		    		  if ((note == undefined) || (note == '')) return '';
		    		  else return note;
		    	  }
		      },
		      {name:'control',		label: '操作',						sortable:false,	width:100, 	align:'center',
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var rtn = '';
		    		  rtn = '<a href="BillProc.do?cmd=billProcForm&billProcId='+rowObject.billProcId+'">详细</a>';
		    		  
		    		  if ((rowObject.billHead.billType == 'DT0004') || (rowObject.billHead.billType == 'DT0008')){
		    			  rtn += '<a class="mgl10" target="_blank" href="BillProc.do?cmd=billProcPrintForm&billId='+rowObject.billId+'">打印</a>';
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
	
	jQuery("#btnSupplyStatistics").click(function(){
		window.location.href = "BillProc.do?cmd=supplyStatistic&createDateFrom=" + $("#createDateFrom").val() + "&createDateTo=" +  $("#createDateTo").val();
	});
	
	jQuery("#btnBuyStatistic").click(function(){
		window.location.href = "BillProc.do?cmd=buyStatistic&createDateFrom=" + $("#createDateFrom").val() + "&createDateTo=" +  $("#createDateTo").val();
	});
	
	jQuery("#btnDistributeStatistic").click(function(){
		window.location.href = "BillProc.do?cmd=distributeStatistic&createDateFrom=" + $("#createDateFrom").val() + "&createDateTo=" +  $("#createDateTo").val();
	});
	
	jQuery("#btnDistributeConfirm").click(function(){
		window.location.href = "BillProc.do?cmd=distributeConfirm&createDateFrom=" + $("#createDateFrom").val() + "&createDateTo=" +  $("#createDateTo").val();
	});
});

function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#billProcUncheckedSearchForm').serialize()
	}).trigger('reloadGrid');
}
