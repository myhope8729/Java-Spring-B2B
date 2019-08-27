package com.kpc.eos.core;

import com.kpc.eos.core.web.context.ApplicationSetting;

public class Constants {

	//Session key of login user
	public static final String 	SESSION_KEY_LOGIN_USER 	= "loginUser";
	
	public static final String 	SESSION_KEY_SYS_MSG 	= "sysMsg";
	
	public static final String 	SESSION_KEY_SC 			= "scModel";
	public static final int 	SESSION_SC_MODEL_LIMIT 	= 10;
	
	public static final String 	COOKIE_KEY_EOS_CART 	= "eosCart";
	
	public static final int 	US_MAIN_COLOR 	= 0xFFA51E;
	
	//added by RMH
	public static final String SESSION_KEY_CURRENT_MENU 	= "currentMenu";
	
	//added by KUC
	public static final String CONST_MENU_TYPE_USER			= "USERMENU";
	
	//System name
	public static final String REQUEST_KEY_SYSTEMNAME	= "SYSTEMNAME";
	
	public static final String SYSTEM_USER_NO			= "USER00000000000";  
	public static final String SYSTEM_EMPL_NO			= "EMPL00000000000";
	
	//added by RMH, IF current menu page is Main Page after login, sesstion current menu key's value
	public static final String LOGIN_MAIN_PAGE_NO		= "MAIN_PAGE";
	
	//Added by KUC, REMEMBERME Status on login page
	public static final String LOGIN_REMEMBERME_ON		= "on";
	
	
	// JSON RESULT KEY
	public static final String JSON_RESULT_KEY      	= "_result";

	public static final String REQUEST_VIEW_NAME		= "viewName";
	
	public static final String STR_MOBILE				= "mobile";
	
	public static final String REQUEST_WORKBOOK_KEY		= "workbook";
	
	public static final String REQUEST_KEY_RETURN_DATA = "returnData";
	public static final String REQUEST_KEY_RETURN_URL = "returnUrl";
	
	//Image Server URL
	public static final String IMAGE_SERVER_URL = ApplicationSetting.getConfig("imageServer");
	
	public static final String NO_IMAGE_URL = ApplicationSetting.getConfig("imageServer") + "/images/no_image.png";
	public static final String BG_IMAGE_URL = IMAGE_SERVER_URL + "/images/bg_image.png";
	
	public static final String ITEM_NO_S_IMG_NAME = "noImage_60x40.gif";
	public static final String ITEM_NO_M_IMG_NAME = "noImage_300x200.gif";
	public static final String ITEM_NO_IMG_NAME = "noImage_300x200.gif";
	
	// Use YN code
	public static final String CONST_Y = "Y";
	public static final String CONST_N = "N";
	
	public static final String CONST_Y_STR = "是";
	public static final String CONST_N_STR = "否";
	
	// State code
	public static final String CONST_STATE_Y = "ST0001";
	public static final String CONST_STATE_N = "ST0002";
	
	// State code Name
	public static final String CONST_STATE_Y_STR = "正常";
	public static final String CONST_STATE_N_STR = "禁用";
	
	//Business data code
	public static final String COSNT_BIZDATA_PICKGROUP_CODE = "BD0002";
	public static final String COSNT_BIZDATA_ITEMGROUP_CODE = "BD0003";
	public static final String CONST_BIZDATA_ITEM_TYPE_CODE = "BD0004";	
	public static final String CONST_BIZDATA_ITEM_BRAND 	= "BD0007";	
	public static final String BIZDATA_FILE_TYPE 			= "BD0009";		// 文档类别
	public static final String CONST_BIZDATA_CAR_CODE 		= "BD0011";
	public static final String BIZDATA_PREPAY				= "BD0013";
	public static final String CONST_BIZDATA_ORDER_ENV		= "BD0014";
	
	//Item property code
	public static final String CONST_ITEM_NUM_CODE 				= "PT0001";
	public static final String CONST_ITEM_PRICE_CODE 			= "PT0002";
	public static final String CONST_ITEM_TYPE1_CODE 			= "PT0003";
	public static final String CONST_ITEM_TYPE2_CODE 			= "PT0004";
	public static final String CONST_ITEM_PACKAGE_MARK_CODE 	= "PT0005";
	public static final String CONST_ITEM_NAME_CODE 			= "PT0006";
	public static final String CONST_ITEM_SALE_UNIT_CODE 		= "PT0007";
	public static final String CONST_ITEM_STANDARD_CODE 		= "PT0008";
	public static final String CONST_ITEM_MANUFACTURER_CODE 	= "PT0009";
	
	
	// Order data code
	public static final String CONST_BILL_TYPE_CUSTPAY  = "DT0001";
	public static final String CONST_BILL_TYPE_RUKU 	= "DT0002";
	public static final String CONST_BILL_TYPE_NEWS 	= "DT0003";
	public static final String CONST_BILL_TYPE_DING 	= "DT0004";
	public static final String CONST_BILL_TYPE_PRICE 	= "DT0005";
	public static final String CONST_BILL_TYPE_PAYMENT 	= "DT0006";
	public static final String CONST_BILL_TYPE_TUI 		= "DT0007";
	public static final String CONST_BILL_TYPE_SALE 	= "DT0008";
	public static final String CONST_BILL_TYPE_POS		= "DT0009";
	public static final String CONST_BILL_TYPE_NOTICE	= "DT0010";
	public static final String CONST_BILL_TYPE_LOSS 	= "DT0011";
	
	public static final String BILL_PAY_STATE_PAID 		= "MT0001";		// 已付款
	public static final String BILL_PAY_STATE_NOT_PAID 	= "MT0002";		// 未付
	
	
	// workflow status : applied to  eos_bill_head_new
	public static final String BILL_STATE_IN_PROCESS 			= "WS0001";
	public static final String BILL_STATE_COMPLETED 			= "WS0002";
	public static final String BILL_STATE_RETURNED 				= "WS0003";
	public static final String BILL_STATE_DRAFT 				= "WS0004";
	public static final String BILL_STATE_IN_PROCESS_BY_SELLER 	= "WS0005";
	
	// bill proc dat id
	public static final String PROC_DAT_ID_DEFAULT		= "0";
	public static final String PROC_SEQ_NO_ZERO			= "0";
	public static final String PROC_SEQ_NO_DEFAULT		= "-1";
	
	// bill proc type
	public static final String PROC_TYPE_SUBMIT_BILL	= "WF0001";			// 提交单据
	public static final String PROC_TYPE_SELLER_FLOW	= "WF0002";			// 供货流程
	public static final String PROC_TYPE_SALE_FLOW		= "WF0003";			// 销售流程
	public static final String PROC_TYPE_RECEIPT_FLOW	= "WF0004";			// 入库流程
	public static final String PROC_TYPE_PRICE_FLOW		= "WF0005";			// 调价流程
	public static final String PROC_TYPE_PAYMENT_FLOW	= "WF0006";			// 货款流程
	
	// bill proc name
	public static final String PROC_NAME_SALE_APPROVE		= "BP0001";		// 销售经理审批	sale approve of the boss.
	public static final String PROC_NAME_SALE_SUBMIT		= "BP0002";		// 提交销售单	
	public static final String PROC_NAME_ORDER_SUBMIT		= "BP0003";		// 提交订货单	
	public static final String PROC_NAME_ORDER_ACCEPT		= "BP0004";		// 接单	
	public static final String PROC_NAME_BUYER_APPROVE		= "BP0005";		// 采购员审批
	public static final String PROC_NAME_RETURNED_SUBMIT	= "BP0006";		// 提交退货单
	public static final String PROC_NAME_SALE_PROCESS		= "BP0007";		// 销售处理
	public static final String PROC_NAME_ORDER_PROCESS		= "BP0008";		// 订单处理
	public static final String PROC_NAME_BUYER_REPORT		= "BP0009";		// 采购汇总  Nameing is not good. Should be changed later.
	public static final String PROC_NAME_BILL_SUBMIT		= "BP0010";		// 提交入库单
	public static final String PROC_NAME_PAYMENT_BILL_SUBMIT= "BP0011";		// 提交货款单
	
	// bill proc status
	public static final String PROC_STATUS_IN_PROCESS 	= "PS0001";			// 待处理
	public static final String PROC_STATUS_COMPLETED 	= "PS0002";			// 完成
	public static final String PROC_STATUS_RETURNED 	= "PS0003";			// 退回
	
	
	// workflow types
	public static final String WF_TYPE_ORDER_ACCEPT 	= "BT0005";			// 订货受理
	public static final String WF_TYPE_ORDER_REQUEST 	= "BT0006";			// 订货申请
	public static final String WF_TYPE_PRICE		 	= "BT0007";			// 调价业务
	public static final String WF_TYPE_PAYMENT		 	= "BT0008";			// 货款业务
	public static final String WF_TYPE_NEWS 			= "BT0011";			// 通知发布
	public static final String WF_TYPE_RECEIPT			= "BT0012";
	public static final String WF_TYPE_SALE 			= "BT0013";			// 销售业务
	public static final String WF_TYPE_SALE_CORP 		= "BT0014";			// 销售合同
	
	
	
	public static final String ITEM_TYPE_BY_GUEST 	= "IT0001";			// 客户上传
	public static final String ITEM_TYPE_BY_MOBILE 	= "IT0002";			// 手机下单
	public static final String ITEM_TYPE_WANG_LU 	= "IT0003";			// 网络下单
	
	
	public static final String INFO_ITEM_TYPE_TEXT 	= "PI0002";			// 文字
	public static final String INFO_ITEM_TYPE_IMG 	= "PI0003";			// 图片
	
	
	// url type link
	public static final String CONST_URL_TYPE_NOLINK	= "UR0001";			// 无链接
	public static final String CONST_URL_TYPE_NETWORK	= "UR0002";			// 其他网页
	public static final String CONST_URL_TYPE_PRODUCT	= "UR0003";			// 商品组
	
	// page detail 
	public static final String PAGE_DETAIL_TYPE_NOSHOW	= "PI0001";			// 不显示
	public static final String PAGE_DETAIL_TYPE_TEXT	= "PI0002";			// 文字
	public static final String PAGE_DETAIL_TYPE_IMAGE	= "PI0003";			// 图片
	public static final String PAGE_DETAIL_TYPE_PRODUCT	= "PI0004";			// 
	public static final String PAGE_DETAIL_TYPE_NEWS	= "PI0005";			// 栏目信息
	
	// comment type
	public static final String COMMENT_TYPE_HOMEPAGE	= "CT0001";			// 首页评论
	public static final String COMMENT_TYPE_PRODUCT		= "CT0002";			// 商品评论	
	
	public static final String CONST_RMB_LIST			= "分角元拾佰仟万拾佰仟亿拾佰仟万";
	public static final String CONST_NUM_LIST			= "零壹贰叁肆伍陆柒捌玖";
	public static final String CONST_RMB_EXCEED			= "超出范围的人民币值";
	
	
	public static final String UK_BUYER					= "UK0001";
	public static final String UK_SELLER				= "UK0002";
	public static final String UK_BUYER_SELLER			= "UK0003";
	public static final String UK_ADMIN					= "UK0004";
	
	public static final String RESULT_SUCCESS			= "SUCCESS";
	public static final String RESULT_FAILURE			= "FAILURE";
	
	public static final String SYS_MSG_ITEM_TPL = "<div class='alert alert-%s'>%s</div>";
	
	
}
