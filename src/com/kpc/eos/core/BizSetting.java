/**
 * Filename		: BizSetting.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core;


/**
 * Filename		: BizSetting.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class BizSetting
{
	//Business setting code
	public static final String COPYMARK_CODE 		= "1006";							// 复制审批流程
	public static final String ARRIVAL_DATE_REQUIRED = "0401";							// 订货单到货日期必输
	public static final String ARRIVAL_DATE_OFFSET = "0402";							// 订货单默认到货日期
	public static final String JS = "0403";												// 订货单要求整件下单
	public static final String ITEM_TYPE 			= "0411";							// 录入商品类别
			
	public static final String THIRD_VENDOR_MARK 	= "1007";							// 是否第三方供货
	public static final String THIRD_VENDOR_DISPLAY = "0418";							// 显示第三方供货
	public static final String ITEM_DETAIL_MODE 	= "0306";							// 点击商品图片后
	
	public static final String ORDER_BILL_END		= "0408";							// 订货单完成后处理优惠
	
	public static final String SPLIT_ORDER_PROC_IN_SUBMISSION = "0419";					// 提交单据时分发订单
		
	public static final String BRANDMARK = "5001";										// 是否按品牌管理商品 
	
	public static final String ARRIVAL_DATE_REQUIRED_IN_SALE 	= "0501";				// 销售单日期必输 
	public static final String ARRIVAL_DATE_OFFSET_IN_SALE 		= "0502";				// 销售单默认到货日期
	public static final String JS_IN_SALE 						= "0503";				// 销售单要求整件下单
	public static final String AUTO_PRICE_BY_BILL				= "0601";				// 调价时更新未完成订货单据销售价格
	public static final String PRICE_BILL_BY_RECEIPT			= "0602";				// 根据入库单据调整销售价格
	
	public static final String SUPPLY_STATISTIC_SALE_PRICE		= "0801";				// 供货统计打印销售价格
	
	public static final String BILL_PRINT_FORMAT				= "0404";				// 订货单据打印格式
	public static final String BILL_PRINT_BZ					= "0405";				// 按包装计算商品体积和重量
	public static final String BILL_PRINT_QTY_MARK				= "0406";				// 同时打印订货数量和配送数量
	public static final String BILL_PRINT_COL_CNT				= "0420";				// 单据打印列数
	public static final String BILL_PRINT_NOTE_MARK				= "0421";				// 单据打印备注列
	public static final String ITEM_VOLUMN_UNIT					= "0305";				// 每包装体积单位
	public static final String ITEM_WEIGHT_UNIT					= "0304";				// 每包装重量单位
	
	public static final String USERPAGE_QRCODE					= "0101";				// 用户注册时录入邀请码
		
	public static final String SUPPLY_STATISTIC_PRICE_Y		= "SYSD00000000050";
	public static final String SUPPLY_STATISTIC_PRICE_N		= "SYSD00000000051";
	
	public static final String ITEM_DETAIL_MODE_SMALL 		= "SYSD00000000010";		// 显示商品详情
	public static final String ITEM_DETAIL_MODE_BIG_IMG 	= "SYSD00000000011";		// 显示大图
	
	public static final String AUTO_PRICE_BY_BILL_Y			= "SYSD00000000046";
	public static final String AUTO_PRICE_BY_BILL_N			= "SYSD00000000047";	
	public static final String PRICE_BILL_BY_RECEIPT_Y		= "SYSD00000000048";
	public static final String PRICE_BILL_BY_RECEIPT_N		= "SYSD00000000049";		
	
	public static final String JS_Y 					 	= "SYSD00000000017";
	public static final String JS_N						 	= "SYSD00000000016";
	
	public static final String JS_IN_SALE_Y 				= "SYSD00000000043";		// 销售单要求整件下单 Yes 
	public static final String JS_IN_SALE_N 				= "SYSD00000000042";		// 销售单要求整件下单 No 
	
	
	public static final String PRINT_PAPER_LARGE 			= "SYSD00000000018"; 
	public static final String PRINT_PAPER_SMALL 			= "SYSD00000000019";
	
	public static final String ITEM_TYPE_Y 					= "SYSD00000000033";
	public static final String ITEM_TYPE_N 					= "SYSD00000000032";
	
	public static final String THIRD_VENDOR_DISPLAY_Y 		= "SYSD00000000034";
	public static final String THIRD_VENDOR_DISPLAY_N 		= "SYSD00000000035";
	
	public static final String SPLIT_ORDER_PROC_IN_SUBMISSION_Y = "SYSD00000000036";
	public static final String SPLIT_ORDER_PROC_IN_SUBMISSION_N = "SYSD00000000037";
	
	public static final String COPYMARK_VALUE_Y				= "SYSD00000000058";
	public static final String COPYMARK_VALUE_N 			= "SYSD00000000059";
	
	public static final String THIRD_VENDOR_MARK_Y 			= "SYSD00000000060";
	public static final String THIRD_VENDOR_MARK_N 			= "SYSD00000000061";
	
	public static final String BRANDMARK_Y 					= "SYSD00000000074";
	public static final String BRANDMARK_N 					= "SYSD00000000075";
	
	public static final String ARRIVAL_DATE_REQUIRED_Y 		= "SYSD00000000014";
	public static final String ARRIVAL_DATE_REQUIRED_N 		= "SYSD00000000015";
	
	public static final String ARRIVAL_DATE_REQUIRED_IN_SALE_Y 		= "SYSD00000000041";
	public static final String ARRIVAL_DATE_REQUIRED_IN_SALE_N 		= "SYSD00000000040";
	
	public static final String ORDER_BILL_END_Y				= "SYSD00000000027";
	public static final String ORDER_BILL_END_N				= "SYSD00000000026";
	
	public static final String ITEMSHOW_CODE 				= "0306";				// 点击商品图片后
	public static final String ITEMSHOW_DETAIL 				= "SYSD00000000010";	// 显示商品详情
	public static final String ITEMSHOW_BIG 				= "SYSD00000000011";	// 显示大图	
	
	public static final String BILL_PRINT_LARGE				= "SYSD00000000018";	// 大张多联纸
	public static final String BILL_PRINT_SMALL				= "SYSD00000000019";	// 小张多联纸
	
	public static final String BILL_PRINT_QTY_MARK_Y		= "SYSD00000000022";
	public static final String BILL_PRINT_QTY_MARK_N		= "SYSD00000000023";
	
	public static final String BILL_PRINT_NOTE_Y			= "SYSD00000000038";
	public static final String BILL_PRINT_NOTE_N			= "SYSD00000000039";
	
	public static final String BILL_PRINT_BZ_Y				= "SYSD00000000021";
	public static final String BILL_PRINT_BZ_N				= "SYSD00000000020";
	
	public static final String USERPAGE_QRCODE_Y			= "SYSD00000000001";
	public static final String USERPAGE_QRCODE_N			= "SYSD00000000002";
	public static final String USERPAGE_QRCODE_R			= "SYSD00000000003";
}
