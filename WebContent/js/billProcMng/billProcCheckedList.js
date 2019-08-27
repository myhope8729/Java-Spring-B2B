$(document).ready(function(){
	CommonGrid.init('grid', {
		url: "BillProc.do?cmd=billProcCheckedGridAjax",
	    postData: $("#billProcCheckedSearchForm").serialize(),
	    datatype:'json',
	    colModel:[
              {name:'billProcId',	label:'billProcId',		key:true,	hidden:true},
		      {name:'billId',		label: '单据类别<br/>单据编号',		sortable:false,		width:180, 		align:'left',	index:'bill_type, bill_id',
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var rtn = rowObject.billHead.billTypeName;
		    		  if ((rowObject.billHead.bnoUser || '') != '') rtn += '<br/>' + rowObject.billHead.bnoUser;
		    		  else rtn += '<br/>' + cellValue;
		    		  return rtn;
		    	  }
		      },
              {name:'procPos', 		label:'业务单位',					sortable:false,		width:160,		align:'left', 
		    	  formatter:function(cellValue, opiotns, rowObject){
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
		      {name:'payTypeName',	label: '付款方式<br/>付款类别',		sortable:false,	width:120, 	align:'left',		index:'paytype_name',
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
		    				  rtn += '<br/>' + paymentType;
		    			  }
		    			  if (rowObject.billHead.weixinYn == 'Y')
	    				  {
		    				  rtn += '<br/>' + rowObject.billHead.payStateName;
	    				  }
		    			  return rtn;
	    			  }
		    	  }
		      },
		      {name:'create_date',	label: '业务日期',					sortable:false,	width:120, 	align:'center', 
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
		    		  else if ((rowObject.billHead.billType == 'DT0004') || (rowObject.billHead.billType == 'DT0008'))
		    		  {
		    			  rtn = '送货日期';
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
		    		  if ((rowObject.billHead.arriveDate || '') == '') rtn += '无';
		    		  else rtn += rowObject.billHead.arriveDate;
		    		  
		    		  return rtn;
		    	  }
		      },
		      {name:'note',			label: '备注',						sortable:false,	width:180, 	align:'left', index:'note', 
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var note = rowObject.billHead.note;
		    		  if ((note == undefined) || (note == '')) return '';
		    		  else return note;
		    	  }
		      },
		      {name:'stateName',	label: '单据状态',					sortable:false,	width:100, 	align:'center', index:'state', 
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var state = rowObject.billHead.stateName;
		    		  if ((state == undefined) || (state == '')) return '';
		    		  else return state;
		    	  }
		      },
		      {name:'bill_proc',	label: '处理类别<br/>处理人',		sortable:false,	width:180, 	align:'left',
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  if (rowObject.billHead.state == 'WS0002') return '';
		    		  return (rowObject.billHead.billProc || '') + '<br/>' + (rowObject.billHead.procMan || '');
		    	  }
		      },
		      {name:'control',		label: '操作',						sortable:false,	width:100, 	align:'center',
		    	  formatter:function(cellValue, opiotns, rowObject){
		    		  var rtn = '';
		    		  rtn = '<a href="BillProc.do?cmd=billProcCheckedForm&billProcId='+rowObject.billProcId+'" target="_blank">详细</a>';
		    		  
		    		  if ((rowObject.billHead.billType == 'DT0004') || (rowObject.billHead.billType == 'DT0008')){
		    			  rtn += '<a class="mgl10" target="_blank" href="BillProc.do?cmd=billProcPrintForm&billId='+rowObject.billId+'">打印</a>';
		    		  }
		    		  return rtn;
		    	  }
		      }
        ],
		sortname: 'b.update_date',
		sortorder : 'desc',
		rowNum :  gridPager.rowNum,
		page :  gridPager.page,
		pager: jQuery('#gridpager')
	});
});

function reloadGrid(){
	jQuery('#grid').jqGrid('setGridParam',{
		postData: $('#billProcCheckedSearchForm').serialize()
	}).trigger('reloadGrid');
}