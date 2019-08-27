CommonGrid.init('paymentGrid', {
    datatype:"local",
    data: detailList || [],
    colModel:[
   	       {name:'paytypeName', 	label:'预付款名称',		width:'250', 	align:'center',		sortable:false, formatter: function(cellValue, option, rowObject){
   	    	   return (cellValue == '')? rowObject.paytype1: cellValue;
   	       }},
   	       {name:'paytype2', 		label:'款项名称',		width:'250',	align:'center',		sortable:false  },
   	       {name:'tot2',			label:'收款金额(元)',	width:'200',	align:'center',		sortable:false, 
   	    	   formatter: function(cellValue, option, rowObject){
   	    		   return billTotalAmt;
   	       }},
   	       {name:'tot', 			label:'款项金额(元)',	width:'100',	align:'center',		sortable:false	},
   	]
});
