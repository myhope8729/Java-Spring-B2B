CommonGrid.init('grid', {
    url:'UserMenu.do?cmd=userMenuGridAjax',
    datatype: "json",
    colModel: [
               	{ label: 'id', 			name: 'id', 				key:true,	hidden:true },
               	{ label: 'userId', 		name: 'userId', 			hidden:true },
   				{ label: 'Menu Name', 	name: 'menuName', 			sortable: false, 	width: 150	},
   				{ label: 'Menu URL', 	name: 'menuUrl', 			 width: 300	},
   				{ label: 'State',			name:'stateName', 			sortable: false,  sortable: false,	width:100, 	align:'center'}
           ],
	rowNum: -1,
	pager: false,
	rownumbers:false
});

